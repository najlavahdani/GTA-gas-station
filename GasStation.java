import java.time.LocalTime;
import java.util.Scanner;
import java.io.*;
public class GasStation {
    //fields
    public static Scanner input = new Scanner(System.in);
    public static Car[] carsQueue;
    public static Pump regPump = new RegularPump();
    public static Pump supPump = new SupremePump();
    public static Pump Euro4Pump = new Euro4Pump();
    private static int gasStationWallet = 0;  //kol daramad 
    private static int providedFuels = 0; //kol fuel erae dade shode
    

    public static void addToLogsFile(String log){
        //har kodoom az log haro tooye file zakhore mikonim
        try {
            FileWriter fileWriter = new FileWriter("./Logs.txt", true);
            BufferedWriter bufferWriter = new BufferedWriter(fileWriter);

            bufferWriter.write(log + "\n");

            bufferWriter.flush();
            bufferWriter.close();
            fileWriter.flush();
            fileWriter.close();

        } catch (IOException e) {
           // TODO: handle exception
        } 
    }

    // public static int calcRemaningFuel(FuelType type){
    //     switch (type) {
    //         case FuelType.REGULAR:
    //             return ((GasStation.regPump.getStorage()) + (GasStation.regPump.getFuelPump()));
    //         case FuelType.SUPREME:
    //             return ((GasStation.supPump.getStorage()) + (GasStation.supPump.getFuelPump()));
    //         case FuelType.EURO4:
    //             return ((GasStation.Euro4Pump.getStorage()) + (GasStation.Euro4Pump.getFuelPump()));
    //         default:
    //             return 0;
    //     }
    // }

    public static void addToProvidedFuel(int liters){
        GasStation.providedFuels = providedFuels + liters;
        saveToFile();
    }

    public static void saveToFile(){
        //mizan sookht bargiri shode va daramad hasel az an ra dar yek file zakhire mikonad
        try {
            FileWriter fileWriter = new FileWriter("./Accounting.txt");
            BufferedWriter bufferWriter = new BufferedWriter(fileWriter);

            bufferWriter.write("The total amount of fuel provided so far is " + GasStation.providedFuels + " Liters, and the total income is " + GasStation.gasStationWallet + " Tomans");

            bufferWriter.flush();
            bufferWriter.close();
            fileWriter.flush();
            fileWriter.close();

        } catch (IOException e) {
            // TODO: handle exception
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
                    String log;
                    System.out.println("Hi! Please Enter The Requested Items:");
                    System.out.println("Number Of The Cars: ");
                    int queueLength = input.nextInt();
                    GasStation.carsQueue = new Car[queueLength];
                    
                    for(int i=0; i<queueLength; i++){  //voroodi migire etelaate har mashin ro va mizarateshoon tooye saf 
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

                    for(int i=0; i<GasStation.carsQueue.length; i++){ //mashin haye tooye saf ro bezin mizane
                        Car currentCar = GasStation.carsQueue[i];
                        log = "car number: " + (i+1); //shomare mashin ro eelam mikone
                        System.out.println(log);
                        GasStation.addToLogsFile(log); //in log ro tooye file save mikone

                        log = "--car with fuel leve " + currentCar.getCarFuelTank() + " is about to start refueling at " + LocalTime.now() + "time!";
                        System.out.println(log);
                        GasStation.addToLogsFile(log);

                        FuelType cfType = currentCar.getCarFuelType(); //type fuel ke mashin kikhad ke bayad ba type pump match bashe
                        Pump currectPump;
                        boolean isRefueling = false;

                        
                    
                        if(cfType == GasStation.regPump.getPumpType()){ //type fuel mashin regulare, az pump regular fuel migire    
                            try {
                                log = "-- car with fuel level " + currentCar.getCarFuelTank() + " is starting to refueling at " + LocalTime.now() + " time!";
                                System.out.println(log);
                                GasStation.addToLogsFile(log); //in log ro tooye file save mikone

                                currectPump = GasStation.regPump;
                                int liters = 0;

                                liters = currentCar.carFuelWanted(currectPump.getppl()); //meghdar fuel ghabe gereftan tavasote mashin hesab mishe
                                liters = currectPump.refuelingPump(liters); //meghdar fuel ghabele eraee tavasote pump hesab mishe va az pump kam mishe
                                currentCar.addToCarFuelTank(liters); //fuel be bak mashin ezafe mishe
                                int price = currentCar.withdrawFromTheWallet(liters, GasStation.regPump.getppl()); //pool benzin az Wallet mashin kam mishe va pooli ke kam shode ro barmogardoone
                                GasStation.addToWallet(price); //pool be wallet gas station ezaf mishe
                                Thread.sleep((liters / 50)*2000); //tavaghof barname baraye benzin zadan
                                
                                log = "-- this car's fuel level after refueling is " + currentCar.getCarFuelTank() + " at " + LocalTime.now();
                                System.out.println(log);
                                GasStation.addToLogsFile(log); //in log ro tooye file save mikone
                                
                                GasStation.addToProvidedFuel(liters); //meghdar liter dade shode tooye pump benzin ezafe mishe
                                isRefueling = true;


                                log = "-- This car left the gas station";
                                System.out.println(log);
                                GasStation.addToLogsFile(log); //in log ro tooye file save mikone

                            }catch(NotEnoughMoneyException neme) {
                                log = "-- this car doesn't have enough money!";
                                System.out.println(log);
                                GasStation.addToLogsFile(log); //in log ro tooye file save mikone

                                isRefueling = true; //age pool nadashte bashe niyazi nist baghiye pumpha barash check beshan va log monaseb nist ro badan
                                
                            }catch(PumpIsEmptyException piee){
                                log = "-- this pump is empty, go to another gas station, bye!";
                                System.out.println(log);
                                GasStation.addToLogsFile(log); //in log ro tooye file save mikone

                                isRefueling = true; //age pump monasebesh fuel nadashte bashe niyazi nist baghiye pumpha barash check beshan va log monaseb nist ro badan
                            }
                        }else if(cfType != GasStation.regPump.getPumpType()){ //type regular baraye mashin match nist
                            if(isRefueling == false){ //hanooz pump monasebesh ro peyda nakarde
                                log = "-- this station is not compatible to this car at " + LocalTime.now()+ " time!";
                                System.out.println(log);
                                GasStation.addToLogsFile(log); //in log ro tooye file save mikone
                            }
                        }

                        if(cfType == GasStation.supPump.getPumpType()){ //age type supreme bashe
                            try {
                                log = "-- car with fuel level " + currentCar.getCarFuelTank() + " is starting to refueling at " + LocalTime.now() + " time!";
                                System.out.println(log);
                                GasStation.addToLogsFile(log); //in log ro tooye file save mikone

                                currectPump = GasStation.regPump;
                                int liters = 0;
                                liters = currentCar.carFuelWanted(currectPump.getppl()); //meghdar fuel ghabe gereftan tavasote mashin hesab mishe
                                liters = currectPump.refuelingPump(liters); //meghdar fuel ghabele eraee tavasote pump hesab mishe va az pump kam mishe
                                currentCar.addToCarFuelTank(liters); //fuel be bak mashin ezafe mishe
                                int price = currentCar.withdrawFromTheWallet(liters, GasStation.supPump.getppl()); //pool benzin az Wallet mashin kam mishe
                                GasStation.addToWallet(price); //pool be wallet gas station ezaf mishe
                                Thread.sleep((liters / 50)*2000);
                                // gs.sleep(liters); //tavaghof barname baraye benzin zadan
                                log = "-- this car's fuel level after refueling is " + currentCar.getCarFuelTank() + " at " + LocalTime.now();
                                System.out.println(log);
                                GasStation.addToLogsFile(log); //in log ro tooye file save mikone
                                
                                GasStation.addToProvidedFuel(liters); //meghdar liter dade shode tooye pump benzin ezafe mishe
                                isRefueling = true;
                                log = "-- This car left the gas station";
                                System.out.println(log);
                                GasStation.addToLogsFile(log); //in log ro tooye file save mikone
                                

                            }catch(NotEnoughMoneyException neme) {
                                log = "-- this car doesn't have enough money!";
                                System.out.println(log);
                                GasStation.addToLogsFile(log); //in log ro tooye file save mikone
                                isRefueling = true;
                                
                            }catch(PumpIsEmptyException piee){
                                log = "-- this pump is empty, go ro another gas station, bye!";
                                System.out.println(log);
                                GasStation.addToLogsFile(log); //in log ro tooye file save mikone
                                isRefueling = true;
                            }
                        }else if(cfType != GasStation.supPump.getPumpType()){ //type supreme baraye mashin match nist
                            if(isRefueling == false){
                                log = "-- this station is not compatible to this car at " + LocalTime.now()+ " time!";
                                System.out.println(log);
                                GasStation.addToLogsFile(log); //in log ro tooye file save mikone
                            }
                        }

                        if(cfType == GasStation.Euro4Pump.getPumpType()){ //typesh Euro4 bashe
                            try {
                                log = "-- car with fuel level " + currentCar.getCarFuelTank() + " is starting to refueling at " + LocalTime.now() + " time!";
                                System.out.println(log);
                                GasStation.addToLogsFile(log); //in log ro tooye file save mikone

                                currectPump = GasStation.regPump;
                                int liters = 0;
                                liters = currentCar.carFuelWanted(currectPump.getppl()); //meghdar fuel ghabe gereftan tavasote mashin hesab mishe
                                liters = currectPump.refuelingPump(liters); //meghdar fuel ghabele eraee tavasote pump hesab mishe va az pump kam mishe
                                currentCar.addToCarFuelTank(liters); //fuel be bak mashin ezafe mishe
                                int price = currentCar.withdrawFromTheWallet(liters, GasStation.Euro4Pump.getppl()); //pool benzin az Wallet mashin kam mishe
                                GasStation.addToWallet(price); //pool be wallet gas station ezaf mishe
                                Thread.sleep((liters/50)*2000);
                                // gs.sleep(liters); //tavaghof barname baraye benzin zadan
                                log = "-- this car's fuel level after refueling is " + currentCar.getCarFuelTank() + " at " + LocalTime.now() + " time!";
                                System.out.println(log);
                                GasStation.addToLogsFile(log); //in log ro tooye file save mikone

                                GasStation.addToProvidedFuel(liters); //meghdar liter dade shode tooye pump benzin ezafe mishe
                                isRefueling = true;
                                log = "-- This car left the gas station";
                                System.out.println(log);
                                GasStation.addToLogsFile(log); //in log ro tooye file save mikone

                            }catch(NotEnoughMoneyException neme) {
                                log = "-- this car doesn't have enough money!";
                                System.out.println(log);
                                GasStation.addToLogsFile(log); //in log ro tooye file save mikone
                                isRefueling = true;
                                
                            }catch(PumpIsEmptyException piee){
                                log = "-- this pump is empty, go ro another gas station, bye!";
                                System.out.println(log);
                                GasStation.addToLogsFile(log); //in log ro tooye file save mikone
                                isRefueling = true;
                            }
                        }else if(cfType != GasStation.Euro4Pump.getPumpType()){ //type euro4 barash match nabashe
                            if(isRefueling == false){
                                log = "-- this station is not compatible to this car at " + LocalTime.now()+ " time!";
                                System.out.println(log);
                                GasStation.addToLogsFile(log); //in log ro tooye file save mikone
                            }
                        }
                        
                    }

                    

                    break;

                case 2: //report mide
                    System.out.println("neshoon nemide!");
                    BufferedReader bufferedReader;
                    FileReader fileReader;
                    //file accounting marboot be hesabdari ro mikhoone o to console print mikone
                    try {
                        fileReader = new FileReader("./Accounting.txt");
                        bufferedReader = new BufferedReader(fileReader);
                        String line;
                        while(( line = bufferedReader.readLine()) != null){
                            System.out.println(line);
                        } 
                        bufferedReader.close();
                        fileReader.close();                        
                    } catch (IOException e) {
                        // TODO: handle exception
                    }

                    break;

                case 3: //file log haro mikhoone o to console print mikone
                    try {
                        fileReader = new FileReader("./Logs.txt");
                        bufferedReader = new BufferedReader(fileReader);
                        String line;
                        while(( line = bufferedReader.readLine()) != null){
                            System.out.println(line);
                        } 
                        bufferedReader.close();
                        fileReader.close();                        
                    } catch (Exception e) {
                        // TODO: handle exception
                    }
                    break;


                default:
                    break;
            }
        
        }
    }
}
