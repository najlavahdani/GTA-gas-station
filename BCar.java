public class BCar extends Car{
    //fields
    public final int fuelCapacity = 70; //zarfiyat bak mashin
    public final CarType carType = CarType.B;
    private final FuelType carFuelType = FuelType.SUPREME;
    private int carFuelTank;
    
    
    //constructor
    public BCar(int money, int fuelLiter){
        super(money);
        setCarFuelTank(fuelLiter);
    } 

    
    //setter
    public void setCarFuelTank(int literFuel){
        try { 
            if(literFuel <= this.fuelCapacity && literFuel>=0){  
                this.carFuelTank = literFuel; 
            }else{ //fuel dakhel bak nabayad bishtar az capacity bashe
                throw new UnacceptableValueException();
            }  
        } catch(UnacceptableValueException uve){
            System.out.println("the fuel in the tank can't be more than the capacity: "+ this.fuelCapacity +"\n Try Again: ");
            int newFuel = input.nextInt();
            setCarFuelTank(newFuel);
        }
    }

   
    //getter
    public FuelType getCarFuelType(){
        return this.carFuelType;
    }

    public int getCarFuelTank(){
        return this.carFuelTank;
    }


    //methods
    public void addToCarFuelTank(int liters){ //fuel be bak mashin ezafe mishe
        this.carFuelTank = this.carFuelTank + liters;  
    }

    public int carFuelWanted(int ppl) throws NotEnoughMoneyException{ //meghdar fuel ke mitoone az pump begire batavajoh be capacity o walletesh ro barmigadoone
        //ppl is price per liter
        int liters = this.calcFuelLitersToBeFull(); //fuel morede niyas=z baraye por shodan bakesh
        if(this.isHaveEnoughMoney(ppl, liters)){ //poole kafi baraye por kardan bakesh dare
            return liters;
        }else{ //poole kafi nadare ke por beshe
            return calcMaxLitersCanTake(ppl);
        }
    }

    private boolean isHaveEnoughMoney(int ppl, int liters){ //ba tavajoh m=be mojodi kife pool mige ke mitoone enghad fuel begire ya na
        int price = liters * ppl;
        if(price <= this.carWallet){
            return true;
        }else{
            return false;
        }

    }


    private int calcMaxLitersCanTake(int ppl) throws NotEnoughMoneyException{ //max meghdar fuel ke mitoone ba tavajoh be walletesh begire
        if(this.carWallet>=ppl){ //pool dare hadeaghal yek litr begire
            return this.carWallet / ppl;
        }else{ //pool bara 1 liter ham nadare
            throw new NotEnoughMoneyException();
        }
    }

    private int calcFuelLitersToBeFull(){ //baraye por shodan bak cheghad fuel mikhad?
        return this.fuelCapacity - this.carFuelTank;
    }
    
}
