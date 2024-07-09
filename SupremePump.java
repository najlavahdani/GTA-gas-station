public class SupremePump extends Pump {
    public final FuelType pumpType = FuelType.SUPREME;
    private int ppl = 1500; //price per liter

    //getter
    public int getppl(){
        return this.ppl;
    }

    public FuelType getPumpType(){
        return this.pumpType;
    }
}
