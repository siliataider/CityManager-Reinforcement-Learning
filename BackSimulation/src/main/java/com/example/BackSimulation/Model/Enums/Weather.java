package com.example.BackSimulation.Model.Enums;

public enum Weather {
    Sunny(0.3, 0),
    Rainy(0.25,1),
    Cloudy(0.2,2),
    Fog(0.05,3),
    Windy(0.1,4),
    Storm(0.1,5);

    private double chance;
    private int value;
    private Weather(double chance, int value){
        this.chance = chance;
        this.value = value;
    }
    public double getChance(){
        return this.chance;
    }
    public int getValue() { return value; }
}
