package com.example.ct60a2411_8;

import java.util.ArrayList;
import java.util.Scanner;

public class BottleDispenser {

    private int bottleSelection;
    // The array for the Bottle-objects
//private Bottle[] bottle_array;
    public ArrayList<Bottle> myBottles = new ArrayList<>();
    private double money;

    public static BottleDispenser myBD = new BottleDispenser(); // Singleton!!!

    public static BottleDispenser getInstance() {
        return myBD;
    } // Singleton!!!

    private BottleDispenser() {
        bottleSelection = 5;
        money = 0.00d;

        Bottle myBottle;

        // Add Bottle-objects into the arraylist
        for(int i = 0; i< bottleSelection; i++) {
            myBottle = new Bottle(i);
            myBottles.add(myBottle);
        }
    }

    public void addMoney(double moneyIn) {
        money += moneyIn;
        System.out.println("Klink! Added more money!");
    }

    public double getMoney() {
        return money;
    }

    public String getName( int beverageType) {
        Bottle selectedBottle = myBottles.get(beverageType);
        return selectedBottle.getName();
    }

    public double getSize( int beverageType) {
        Bottle selectedBottle = myBottles.get(beverageType);
        return selectedBottle.getSize();
    }

    public double getPrice( int beverageType) {
        Bottle selectedBottle = myBottles.get(beverageType);
        return selectedBottle.getPrice();
    }


    public int buyBottle( int beverageType ) {
        Bottle selectedBottle;

        selectedBottle = myBottles.get( beverageType );

        if ( money < selectedBottle.getPrice()) {
            System.out.println("Add money first!");
            return -1;
        }
        int remaining = selectedBottle.getAmount();
        if ( remaining > 0) {
            money -= selectedBottle.getPrice();
            selectedBottle.decreaseAmount();
            System.out.println("KACHUNK! " + selectedBottle.getName() + " came out of the dispenser!");
            return remaining;
        }
        else
            System.out.println("Klink klink. Money came out!");
        return 0;
    }

    public void returnMoney() {
        money = 0;
    }

    public void showMenu() {
        System.out.println("\n*** BOTTLE DISPENSER ***");
        System.out.println("1) Add money to the machine");
        System.out.println("2) Buy a bottle");
        System.out.println("3) Take money out");
        System.out.println("4) List bottles in the dispenser");
        System.out.println("0) End");
        System.out.print("Your choice: ");
    }

    public int getAction() {
        int action;

        Scanner scan;
        scan = new Scanner(System.in);
        action = scan.nextInt();
        return action;
    }

    public void showChoiceOfProducts() {
        int order;
        Bottle choices;

        for(int i = 0; i < bottleSelection; i++) {
            choices = myBottles.get(i);
            order = 1 + i;
            System.out.println(order + ". Name: " + choices.getName() + "\n\tSize: " + choices.getSize() + "\n\tPrice: " + choices.getPrice() + "\n\tAmount: " + choices.getAmount());
        }
    }

    public int removeBottleFromDispenser(int bottleOrder ) {
        int status = 0;

        myBottles.remove(bottleOrder);

        return status;
    }
}
