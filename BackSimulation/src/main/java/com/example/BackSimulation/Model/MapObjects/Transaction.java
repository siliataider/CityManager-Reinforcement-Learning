package com.example.BackSimulation.Model.MapObjects;

import com.example.BackSimulation.Model.Enums.Stats;

import java.util.ArrayList;

public class Transaction {
    private ArrayList<Stats> taken;
    private ArrayList<Double> takenQuantities;
    private ArrayList<Stats> given;
    private ArrayList<Double> givenQuantities;

    public Transaction(){
        taken = new ArrayList<Stats>();
        takenQuantities = new ArrayList<Double>();
        given = new ArrayList<Stats>();
        givenQuantities = new ArrayList<Double>();
    }

    public Transaction(Stats singleGiven, double givenQuantity, Stats singleTaken, double takenQuantity){
        ArrayList<Stats> takenList = new ArrayList<Stats>();
        takenList.add(singleTaken);
        ArrayList<Double> takenQuantityList = new ArrayList<Double>();
        takenQuantityList.add(takenQuantity);
        ArrayList<Stats> givenList = new ArrayList<Stats>();
        givenList.add(singleGiven);
        ArrayList<Double> givenQuantityList = new ArrayList<Double>();
        givenQuantityList.add(givenQuantity);

        taken = takenList;
        takenQuantities = takenQuantityList;
        given = givenList;
        givenQuantities = givenQuantityList;
    }

    public Transaction(ArrayList<Stats> taken, ArrayList<Double> takenQuantities,
                       ArrayList<Stats> given, ArrayList<Double> givenQuantities) {
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

    public ArrayList<Double> getTakenQuantities() {
        return takenQuantities;
    }

    public void setTakenQuantities(ArrayList<Double> takenQuantities) {
        this.takenQuantities = takenQuantities;
    }

    public ArrayList<Stats> getGiven() {
        return given;
    }

    public void setGiven(ArrayList<Stats> given) {
        this.given = given;
    }

    public ArrayList<Double> getGivenQuantities() {
        return givenQuantities;
    }

    public void setGivenQuantities(ArrayList<Double> givenQuantities) {
        this.givenQuantities = givenQuantities;
    }

    public String toJSONString() {
        String takenJSON="{";
        for(int i=0;i<taken.size();i++){
            if(i>0){
                takenJSON+=",";
            }
            takenJSON+="\""+taken.get(i)+"\""+":"+takenQuantities.get(i);
        }
        takenJSON+="}";

        String givenJSON="{";
        for(int i=0;i<given.size();i++){
            if(i>0){
                givenJSON+=",";
            }
            givenJSON+="\""+given.get(i)+"\""+":"+givenQuantities.get(i);
        }
        givenJSON+="}";

        String ret =
                "{"
                +"\"taken\":"+takenJSON+","
                +"\"given\":"+givenJSON
                +"}";

        return ret;
    }
}
