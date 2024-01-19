package com.example.BackSimulation.Model.MapObjects;

import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;

public class State {
    private Double hunger;
    private Double energy;
    private Double money;

    public State(Double hunger, Double energy, Double money) {
        this.hunger = hunger;
        this.energy = energy;
        this.money = money;
    }

    public State() {
        hunger = 1.0;
        energy = 1.0;
        money = 1.0;
    }

    public State(LinkedTreeMap<String,Double> pseudoState){
        hunger = pseudoState.get("hunger");
        energy = pseudoState.get("energy");
        money = pseudoState.get("money");
    }

    public Double getHunger() {
        return hunger;
    }

    public void setHunger(Double hunger) {
        this.hunger = hunger;
    }

    public Double getEnergy() {
        return energy;
    }

    public void setEnergy(Double energy) {
        this.energy = energy;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    @Override
    public String toString() {
        return "State{" +
                "hunger=" + hunger +
                ", energy=" + energy +
                ", money=" + money +
                '}';
    }

    public String toJSONString(){
        String ret = "{\"hunger\": " + hunger + "," +
                "\"energy\": " + energy + "," +
                "\"money\": "+ money +
                "}";
        return ret;
    }
}
