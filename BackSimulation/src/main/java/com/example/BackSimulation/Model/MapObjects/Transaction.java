package com.example.BackSimulation.Model.MapObjects;

import com.example.BackSimulation.Model.Enums.Stats;

import java.util.ArrayList;

public class Transaction {
    private ArrayList<Stats> taken;
    private ArrayList<Integer> takenQuantities;
    private ArrayList<Stats> given;
    private ArrayList<Integer> givenQuantities;

    public Transaction(){
        taken = new ArrayList<Stats>();
        takenQuantities = new ArrayList<Integer>();
        given = new ArrayList<Stats>();
        givenQuantities = new ArrayList<Integer>();
    }

    public Transaction(Stats singleGiven, int givenQuantity, Stats singleTaken, int takenQuantity){
        ArrayList<Stats> takenList = new ArrayList<Stats>();
        takenList.add(singleTaken);
        ArrayList<Integer> takenQuantityList = new ArrayList<Integer>();
        takenQuantityList.add(takenQuantity);
        ArrayList<Stats> givenList = new ArrayList<Stats>();
        givenList.add(singleGiven);
        ArrayList<Integer> givenQuantityList = new ArrayList<Integer>();
        givenQuantityList.add(givenQuantity);

        taken = takenList;
        takenQuantities = takenQuantityList;
        given = givenList;
        givenQuantities = givenQuantityList;
    }

    public Transaction(ArrayList<Stats> taken, ArrayList<Integer> takenQuantities,
                       ArrayList<Stats> given, ArrayList<Integer> givenQuantities) {
        this.taken = taken;
        this.takenQuantities = takenQuantities;
        this.given = given;
        this.givenQuantities = givenQuantities;
    }

    public ArrayList<Stats> getTaken() {
        return taken;
    }

    public void setTaken(ArrayList<Stats> taken) {
        this.taken = taken;
    }

    public ArrayList<Integer> getTakenQuantities() {
        return takenQuantities;
    }

    public void setTakenQuantities(ArrayList<Integer> takenQuantities) {
        this.takenQuantities = takenQuantities;
    }

    public ArrayList<Stats> getGiven() {
        return given;
    }

    public void setGiven(ArrayList<Stats> given) {
        this.given = given;
    }

    public ArrayList<Integer> getGivenQuantities() {
        return givenQuantities;
    }

    public void setGivenQuantities(ArrayList<Integer> givenQuantities) {
        this.givenQuantities = givenQuantities;
    }

    public String toJSONString() {
        String takenJSON="{";
        for(int i=0;i<taken.size();i++){
            if(i>0){
                takenJSON+=",";
            }
            takenJSON+=taken.get(i)+":"+takenQuantities.get(i);
        }
        takenJSON+="}";

        String givenJSON="{";
        for(int i=0;i<given.size();i++){
            if(i>0){
                givenJSON+=",";
            }
            givenJSON+=given.get(i)+":"+givenQuantities.get(i);
        }
        givenJSON+="}";

        String ret =
                "{"
                +"taken:"+takenJSON+","
                +"given:"+givenJSON
                +"}";

        return ret;
    }
}
