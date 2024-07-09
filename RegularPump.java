public class RegularPump extends Pump{
    public final FuelType pumpType = FuelType.REGULAR; 
    private int ppl = 1000; //price per liter

    //getter
    public int getppl(){
        return this.ppl;
    }

    public FuelType getPumpType(){
        return this.pumpType;
    }

    
}
