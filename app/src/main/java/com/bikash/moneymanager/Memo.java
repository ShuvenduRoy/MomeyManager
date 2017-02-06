package com.bikash.moneymanager;

/**
 * Created by bikash on 9/28/16.
 */
public class Memo {

    private String memoName;
    private  Double cost;

    Memo(){

    }

    Memo(String mn, Double c){
        this.memoName = mn;
        this.cost = c;
    }

    String getMemoName(){
        return this.memoName;
    }

    Double getCost(){
        return this.cost;
    }

    void setMemoName(String s){
        this.memoName = s;
    }

    void setCost(Double c){
        this.cost = c;
    }

}
