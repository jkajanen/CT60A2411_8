package com.example.ct60a2411_8;

/**
 *
 * @author juhak
 */
public class Bottle {
    private String defName;
    private String defManufacturer;
    private double defTotal_energy;
    private double defSize;
    private double defPrice;
    private String strPrice;
    private int numberOfBottles;

    public Bottle(){
        defName = " ";
        defManufacturer = " ";
        defTotal_energy = 0.0d;
        defSize = 0.0d;
        defPrice = 0.0d;
        numberOfBottles = 0;
    }

    public Bottle( int beverageType ) {
        switch( beverageType ) {
            case 0:
                defName = "Pepsi Max";
                defManufacturer = "Pepsi";
                defTotal_energy = 0.3f;
                defSize = 0.5;
                defPrice = 1.80;
                numberOfBottles = 3;
                break;
            case 1:
                defName = "Pepsi Max";
                defManufacturer = "Pepsi";
                defTotal_energy = 0.9f;
                defSize = 1.5;
                defPrice = 2.2;
                numberOfBottles = 3;
                break;
            case 2:
                defName = "Coca-Cola Zero";
                defManufacturer = "Coca-Cola Company";
                defTotal_energy = 0.3f;
                defSize = 0.5;
                defPrice = 2.0;
                numberOfBottles = 5;
                break;
            case 3:
                defName = "Coca-Cola Zero";
                defManufacturer = "Coca-Cola Company";
                defTotal_energy = 0.9f;
                defSize = 1.5;
                defPrice = 2.5;
                numberOfBottles = 3;
                break;
            case 4:
                defName = "Fanta";
                defManufacturer = "Coca-Cola Company";
                defTotal_energy = 0.3f;
                defSize = 0.5;
                defPrice = 1.95;
                numberOfBottles = 5;
                break;
            default:
                defName = "Pepsi Max";
                defManufacturer = "Pepsi";
                defTotal_energy = 0.3f;
                defSize = 0.5;
                defPrice = 1.80;
                numberOfBottles = 5;
                break;
        }
    }

    public Bottle(String nameIn){
        defName = nameIn;
    }

    public Bottle(String nameIn, String manuf, float totE){
        defName = nameIn;
        defManufacturer = manuf;
        defTotal_energy = totE;
    }

    public String getName(){
        return defName;
    }

    public String getManufacturer(){
        return defManufacturer;
    }

    public double getEnergy(){
        return defTotal_energy;
    }

    public double getSize(){
        return defSize;
    }

    public double getPrice(){
        return defPrice;
    }

    public int getAmount() {
        return numberOfBottles;
    }

    public void decreaseAmount() {
        numberOfBottles--;
    }

    public void setName(String name){
        defName = name;
    }

    public void setSize(double size){
        defSize=size;
    }

    public void setPrice(double price){
        defPrice = price;
    }
    /**
     * Pay attention here, you have to override the toString method as the
     * ArrayAdapter will reads the toString of the given object for the name
     *
     * @return contact_name
     */
    @Override
    public String toString() {
        return defName;
    }

}