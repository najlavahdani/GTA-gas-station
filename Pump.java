public abstract class Pump {
    //fields
    private int storage = 500;
    private int fuelPump = 150;
    

    //setters
    public void setStorage(int f){
        this.storage = f;
    }

    public void setFuelPump(int f){
        this.fuelPump = f;
    }

    //getters
    public int getStorage(){
        return this.storage;
    }

    public int getFuelPump(){
        return this.fuelPump;
    }


    //methods
    public void checkPump(int liters) throws PumpIsEmptyException{
        if(isEnoughFuelInPump(liters) == false){
            if(this.fuelPump == 0 && this.storage == 0){
                throw new PumpIsEmptyException(); 
            }else if(this.fuelPump == 0 && this.storage != 0){
                fillPumpFromStorage();
            }
        }
    }
    //voroodish meghdar fuel morede niyaze
    public int refuelingPump(int liter) throws PumpIsEmptyException{ //bishtarin  meghdar fuel nesbat be meghdare khaste shode(liters) ke mitoone erae bede ro  az pump kam mikone o barmigardoone        
        checkPump(liter);
        if(isEnoughFuelInPump(liter)){ //pump fuel kafi barash dare
            this.fuelPump = this.fuelPump - liter;  //meghdare morede nazar ro azash kam kardim
            return liter; // be andaze liter mitoone fuel bede
        }else{ //pump fuel kafi barash nadare      
            int availableFuel = this.fuelPump;
            this.fuelPump = 0;
            return availableFuel;
        }
        
    }


    private boolean isEnoughFuelInPump(int amount){ //true: pump meghdari ke niyaz darim ro dare | false: nadare 
        if(this.fuelPump >= amount){
            return true;
        }else{
            return false;
        }
    }

    
    private void fillPumpFromStorage(){ //pump ro ba meghdari ke az storage gerefte por mikone
        try {
            throw new RefillPumpFromStorageException();
        } catch (RefillPumpFromStorageException rpfse) {
            System.out.println("-- the pump is refilled from the storage");
        }finally{
            int fuelFromStorage = maxFuelFromStorageToPump(calcPumpLiterNeed()); //max fuel ke storage mide ro mirize to fuelFromStorage
            this.fuelPump = this.fuelPump + fuelFromStorage;
        }
            
    }
    
    private boolean isStorageHasEnoughFuel(int liter){
        if(this.storage>=liter){ //storage be andaze niyaz fuel dare
            return true;
        }else{
            return false;
        }
    }

    private int calcPumpLiterNeed(){ //hesab mikone pump baraye por shodan cheghad fuel mikhad
        return (150 - this.fuelPump);
    }

    //meghdar sookhti ke bayad az storage begire be onvane voroodi migire
    private int maxFuelFromStorageToPump(int literNeed){ //max meghdar sookhti ke mitoone az storage bede be pump ro hesab mikone
        if(isStorageHasEnoughFuel(literNeed)){ //storage be andaze kafi ya bishtar fuel dare
            this.storage = this.storage - literNeed; ///mohasebe meghdare jadid storage
            return literNeed;
        }else{ //storage be andaze kafi fuel nadare ya khaliye
            try {
                if(this.storage > 0){ //storage fuel dare ama na oonghad ke ma mikhaym
                    int literHas = this.storage;
                    this.storage = 0;
                    return literHas;
                }else{ //storage khaliye
                    throw new StorageIsEmptyException();
                }
            } catch (StorageIsEmptyException siee) {
                return 0;
            }
        }
        
    }

    protected abstract int getppl();

    protected abstract FuelType getPumpType();

    
}
