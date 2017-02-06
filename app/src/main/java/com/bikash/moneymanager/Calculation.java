package com.bikash.moneymanager;

/**
 * Created by bikash on 9/28/16.
 */
public class Calculation {

    public static Double sumofFixed(){
        Double total = 0.0;

        for(int i = 0; i<MainActivity.incomeArrayList.size(); i++){
            total += MainActivity.incomeArrayList.get(i).getCost();
        }

        return  total;
    }

    public static Double sumofExtra(){
        Double total = 0.0;

        for(int i = 0; i<MainActivity.spentArrayList.size(); i++){
            total += MainActivity.spentArrayList.get(i).getCost();
        }


        return  total;
    }
}
