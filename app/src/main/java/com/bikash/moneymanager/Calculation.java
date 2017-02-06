package com.bikash.moneymanager;

/**
 * Created by bikash on 9/28/16.
 */
public class Calculation {

    public static Double sumofFixed(){
        Double total = 0.0;

        for(int i=0; i<MainActivity.fixedCostArrayList.size(); i++){
            total += MainActivity.fixedCostArrayList.get(i).getCost();
        }

        return  total;
    }

    public static Double sumofExtra(){
        Double total = 0.0;

        for(int i=0; i<MainActivity.extraSpentArrayList.size(); i++){
            total += MainActivity.extraSpentArrayList.get(i).getCost();
        }


        return  total;
    }
}
