package com.example.BackSimulation.Model;

import com.example.BackSimulation.Model.Enums.Weather;

import java.util.ArrayList;
import java.util.Random;

public class WeatherManager {
    private Weather weather = Weather.Sunny;
    private double changeRate = 1.0;

    public Weather getWeather(){
        return this.weather;
    }
    public void setWeather(Weather weather) {
        this.weather = weather;
    }
    public void setChangeRate(double changeRate){
        this.changeRate = changeRate;
    }
    public WeatherManager() {
    }
    public void changeWeather(){
        //Random r = new Random();
        //double selector = r.nextDouble();

        //if(changeRate>=selector){
            setWeather(rollWeather());
        //}
    }

    private Weather rollWeather(){
        if (getWeather() == Weather.Sunny){
            return Weather.Rainy;
        }
        return Weather.Sunny;
    }

    /*private Weather rollWeather(){
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

    }*/

    public String toJSONString() {
        String ret =
                weather.toString();

        return ret;
    }

}
