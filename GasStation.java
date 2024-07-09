import java.time.LocalTime;
import java.util.Scanner;
public class GasStation {
    //fields
    public static Scanner input = new Scanner(System.in);
    public static Car[] carsQueue;
    public static Pump regPump = new RegularPump();
    public static Pump supPump = new SupremePump();
    public static Pump Euro4Pump = new Euro4Pump();
    private static int gasStationWallet = 0; 
    private static int regProvidedFuels = 0; //kol fuel regular ke gas station erae dade
    private static int supProvidedFuels = 0;
    private static int Euro4ProvidedFuels = 0;


    public static int calcRemaningFuel(FuelType type){
        switch (type) {
            case FuelType.REGULAR:
                return ((GasStation.regPump.getStorage()) + (GasStation.regPump.getFuelPump()));
            case FuelType.SUPREME:
                return ((GasStation.supPump.getStorage()) + (GasStation.supPump.getFuelPump()));
            case FuelType.EURO4:
                return ((GasStation.Euro4Pump.getStorage()) + (GasStation.Euro4Pump.getFuelPump()));
            default:
                return 0;
        }
    }

    public static void addToProvidedFuel(int liters, FuelType type){
        switch (type) {
            case FuelType.REGULAR:
                GasStation.regProvidedFuels = GasStation.regProvidedFuels + liters;
                break;

            case FuelType.SUPREME:
                GasStation.supProvidedFuels = GasStation.supProvidedFuels + liters;
                break;
            
            case FuelType.EURO4:
                GasStation.Euro4ProvidedFuels = GasStation.Euro4ProvidedFuels + liters;
                break;
        }
    }

    public static void addToWallet(int money){
        GasStation.gasStationWallet = GasStation.gasStationWallet + money;
    }

    public static void main(String[] args) throws InterruptedException {
        while (true) {
            
        
            System.out.println("Hi, Please Select The Option: \n1.start simulating\n2.show management report\n3.show the latest simulation logs");
            int opt = input.nextInt();
            switch (opt){
                case 1:
                    System.out.println("Hi! Please Enter The Requested Items:");
                    System.out.println("Number Of The Cars: ");
                    int queueLength = input.nextInt();
                    GasStation.carsQueue = new Car[queueLength];
                    for(int i=0; i<queueLength; i++){
                        System.out.println("car number " + (i+1) + " :");
                        System.out.println("The Car Type:(A or B or C)");
                        Character carType = input.next().charAt(0);
                        int fuelTank = 0;
                        int money = 0;
                        switch (carType){
                            case 'A':
                                System.out.println("The fuel tank capacity of this type of car is 80 liters, How many liters does this car have?");
                                fuelTank = input.nextInt();

                                System.out.println("What is the balance in the wallet of the owner of this car?");
                                money = input.nextInt();

                                GasStation.carsQueue[i] = new ACar(money, fuelTank);
                                break;


                            case 'B':
                                System.out.println("The fuel tank capacity of this type of car is 70 liters, How many liters does this car have?");
                                fuelTank = input.nextInt();

                                System.out.println("What is the balance in the wallet of the owner of this car?");
                                money = input.nextInt();

                                GasStation.carsQueue[i] = new BCar(money, fuelTank);
                                break;


                            case 'C':
                                System.out.println("The fuel tank capacity of this type of car is 60 liters, How many liters does this car have?");
                                fuelTank = input.nextInt();

                                System.out.println("What is the balance in the wallet of the owner of this car?");
                                money = input.nextInt();

                                GasStation.carsQueue[i] = new CCar(money, fuelTank);
                                break;
                        }
                    }
                    //ta inja saf mashin ha dakhel gas statione tashkol shod

                    for(int i=0; i<GasStation.carsQueue.length; i++){
                        Car currentCar = GasStation.carsQueue[i];
                        System.out.println("--car with fuel leve " + currentCar.getCarFuelTank() + " is about to start refueling at " + LocalTime.now() + "time!");
                        FuelType cfType = currentCar.getCarFuelType(); //type fuel ke mashin kikhad ke bayad ba type pump match bashe
                        Pump currectPump;
                        boolean isRefueling = false;

                        
                    
                        if(cfType == GasStation.regPump.getPumpType()){ //type fuel mashin regulare, az pump regular fuel migire    
                            try {
                                System.out.println("-- car with fuel level " + currentCar.getCarFuelTank() + " is starting to refueling at " + LocalTime.now() + " time!");
                                currectPump = GasStation.regPump;
                                int liters = 0;
                                liters = currentCar.carFuelWanted(currectPump.getppl()); //meghdar fuel ghabe gereftan tavasote mashin hesab mishe
                                liters = currectPump.refuelingPump(liters); //meghdar fuel ghabele eraee tavasote pump hesab mishe va az pump kam mishe
                                currentCar.addToCarFuelTank(liters); //fuel be bak mashin ezafe mishe
                                int price = currentCar.withdrawFromTheWallet(liters, GasStation.regPump.getppl()); //pool benzin az Wallet mashin kam mishe
                                GasStation.addToWallet(price); //pool be wallet gas station ezaf mishe
                                Thread.sleep((liters / 50)*2000);
                                // gs.sleep(liters); //tavaghof barname baraye benzin zadan
                                System.out.println("-- this car's fuel level after refueling is " + currentCar.getCarFuelTank() + " at " + LocalTime.now());
                                GasStation.addToProvidedFuel(liters , currectPump.getPumpType()); //meghdar liter dade shode tooye pump benzin ezafe mishe
                                isRefueling = true;
                                

                            }catch(NotEnoughMoneyException neme) {
                                System.out.println("-- this car doesn't have enough money!");
                                isRefueling = true; //age pool nadashte bashe niyazi nist baghiye pumpha barash check beshan va log monaseb nist ro badan
                                
                            }catch(PumpIsEmptyException piee){
                                System.out.println("-- this pump is empty, go to another gas station, bye!");
                                isRefueling = true; //age pump monasebesh fuel nadashte bashe niyazi nist baghiye pumpha barash check beshan va log monaseb nist ro badan
                            }
                        }else if(cfType != GasStation.regPump.getPumpType()){ //type regular baraye mashin match nist
                            if(isRefueling == false){ //hanooz pump monasebesh ro peyda nakarde
                                System.out.println("-- this station is not compatible to this car at " + LocalTime.now()+ " time!");
                            }
                        }

                        if(cfType == GasStation.supPump.getPumpType()){
                            try {
                                System.out.println("-- car with fuel level " + currentCar.getCarFuelTank() + " is starting to refueling at " + LocalTime.now() + " time!");
                                currectPump = GasStation.regPump;
                                int liters = 0;
                                liters = currentCar.carFuelWanted(currectPump.getppl()); //meghdar fuel ghabe gereftan tavasote mashin hesab mishe
                                liters = currectPump.refuelingPump(liters); //meghdar fuel ghabele eraee tavasote pump hesab mishe va az pump kam mishe
                                currentCar.addToCarFuelTank(liters); //fuel be bak mashin ezafe mishe
                                int price = currentCar.withdrawFromTheWallet(liters, GasStation.regPump.getppl()); //pool benzin az Wallet mashin kam mishe
                                GasStation.addToWallet(price); //pool be wallet gas station ezaf mishe
                                Thread.sleep((liters / 50)*2000);
                                // gs.sleep(liters); //tavaghof barname baraye benzin zadan
                                System.out.println("-- this car's fuel level after refueling is " + currentCar.getCarFuelTank() + " at " + LocalTime.now());
                                GasStation.addToProvidedFuel(liters , currectPump.getPumpType()); //meghdar liter dade shode tooye pump benzin ezafe mishe
                                isRefueling = true;
                                

                            }catch(NotEnoughMoneyException neme) {
                                System.out.println("-- this car doesn't have enough money!");
                                isRefueling = true;
                                
                            }catch(PumpIsEmptyException piee){
                                System.out.println("-- this pump is empty, go ro another gas station, bye!");
                                isRefueling = true;
                            }
                        }else if(cfType != GasStation.supPump.getPumpType()){
                            if(isRefueling == false){
                                System.out.println("-- this station is not compatible to this car at " + LocalTime.now()+ " time!");
                            }
                        }

                        if(cfType == GasStation.Euro4Pump.getPumpType()){
                            try {
                                System.out.println("-- car with fuel level " + currentCar.getCarFuelTank() + " is starting to refueling at " + LocalTime.now() + " time!");
                                currectPump = GasStation.regPump;
                                int liters = 0;
                                liters = currentCar.carFuelWanted(currectPump.getppl()); //meghdar fuel ghabe gereftan tavasote mashin hesab mishe
                                liters = currectPump.refuelingPump(liters); //meghdar fuel ghabele eraee tavasote pump hesab mishe va az pump kam mishe
                                currentCar.addToCarFuelTank(liters); //fuel be bak mashin ezafe mishe
                                int price = currentCar.withdrawFromTheWallet(liters, GasStation.regPump.getppl()); //pool benzin az Wallet mashin kam mishe
                                GasStation.addToWallet(price); //pool be wallet gas station ezaf mishe
                                Thread.sleep((liters/50)*2000);
                                // gs.sleep(liters); //tavaghof barname baraye benzin zadan
                                System.out.println("-- this car's fuel level after refueling is " + currentCar.getCarFuelTank() + " at " + LocalTime.now());
                                GasStation.addToProvidedFuel(liters , currectPump.getPumpType()); //meghdar liter dade shode tooye pump benzin ezafe mishe
                                isRefueling = true;

                            }catch(NotEnoughMoneyException neme) {
                                System.out.println("-- this car doesn't have enough money!");
                                isRefueling = true;
                                
                            }catch(PumpIsEmptyException piee){
                                System.out.println("-- this pump is empty, go ro another gas station, bye!");
                                isRefueling = true;
                            }
                        }else if(cfType != GasStation.Euro4Pump.getPumpType()){
                            if(isRefueling == false){
                                System.out.println("-- this station is not compatible to this car at " + LocalTime.now()+ " time!");
                            }
                        }
                        
                    }



                case 2: //report mide
                    System.out.println("Sold Regular Fuel: " + GasStation.regProvidedFuels + "\nRemaining Regular Fuel: " + GasStation.calcRemaningFuel(FuelType.REGULAR) + "\n");
                    System.out.println("Sold Supreme Fuel: " + GasStation.supProvidedFuels + "\nRemaining Supreme Fuel: " + GasStation.calcRemaningFuel(FuelType.SUPREME) + "\n");
                    System.out.println("Sold Euro4 Fuel: " + GasStation.Euro4ProvidedFuels + "\nRemaining Euro4 Fuel: " + GasStation.calcRemaningFuel(FuelType.EURO4) + "\n");

                    System.out.println("Total Income: " + GasStation.gasStationWallet + "\n");
                case 3:
                default:
                    break;
            }
        
        }
    }
}
