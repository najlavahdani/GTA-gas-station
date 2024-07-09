import java.util.Scanner;
public abstract class Car {
    //fields
    protected int carWallet;
    protected Scanner input = new Scanner(System.in);

    //constructor
    public Car(int money){
        setWallet(money);
    }

    //methods
    public int withdrawFromTheWallet(int liters, int ppl){ //pool fuel ke bardashte az kife poolesh kam mishe
        int price = ppl * liters;
        this.carWallet = this.carWallet - price;
        return price;
    }



    //setter
    public void setWallet(int money){
        try {
            if(money >= 0){
                this.carWallet = money;
            }else{ //meghdar mojodi kifepool  nabayad manfi bashe
                throw new UnacceptableValueException();
            }
        } catch(UnacceptableValueException uve){
            System.out.println("The wallet can't be negative! just try again: ");
            int newVal = input.nextInt();
            setWallet(newVal);
        }
    }

    //getter
    public int getWallet(){
        return this.carWallet;
    }

    protected abstract int getCarFuelTank();

    protected abstract FuelType getCarFuelType();

    protected abstract int carFuelWanted(int getppl) throws NotEnoughMoneyException;

    protected abstract void addToCarFuelTank(int liters);



    



}
