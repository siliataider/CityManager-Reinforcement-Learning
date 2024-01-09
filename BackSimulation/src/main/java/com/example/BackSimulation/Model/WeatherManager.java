package com.example.BackSimulation.Model;

import com.example.BackSimulation.Model.Enums.Weather;

import java.util.ArrayList;
import java.util.Random;

public class WeatherManager {
    private Weather weather = Weather.Sunny;
    private double changeRate = 0.2;

    public Weather getWeather(){
        return this.weather;
    }
    public void setWeather(Weather weather) {
        this.weather = weather;
    }


    public WeatherManager() {
    }

    public void changeWeather(){
        Random r = new Random();
        double selector = r.nextDouble();

        if(changeRate>=selector){
            setWeather(rollWeather());
        }
    }

    private Weather rollWeather(){
        Weather[] weathers = Weather.values();

        double total = 1;

        Random r = new Random();
        double selector = r.nextDouble();

        for(int i=0;i<weathers.length;i++){
            total-=weathers[i].getChance();
            if(total<=selector){
                return weathers[i];
            }
        }

        return getWeather();

    }
}
