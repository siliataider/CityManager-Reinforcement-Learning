package com.example.BackSimulation.Model.Enums;

public enum Weather {
    Sunny(0.3),
    Cloudy(0.2),
    Fog(0.05),
    Windy(0.1),
    Rainy(0.25),
    Storm(0.1);

    private double chance;
    private Weather(double chance){
        this.chance = chance;
    }
    public double getChance(){
        return this.chance;
    }
}
