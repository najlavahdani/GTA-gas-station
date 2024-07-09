public class Euro4Pump extends Pump{
    public final FuelType pumpType = FuelType.EURO4;
    private int ppl = 2000; //price per liter

    //getter
    public int getppl(){
        return this.ppl;
    }

    public FuelType getPumpType(){
        return this.pumpType;
    }
}
