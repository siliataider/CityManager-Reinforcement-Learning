package com.example.BackSimulation;

import com.example.BackSimulation.Model.*;
import com.example.BackSimulation.Model.MapObjects.Work;

import jakarta.annotation.PostConstruct;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.corundumstudio.socketio.listener.*;
import com.corundumstudio.socketio.*;
import java.awt.*;
public class Simulation {
    private Point mapSize = new Point(50,50);
    private TimeManager timeManager = new TimeManager();
    private WeatherManager weatherManager = new WeatherManager();

    private MapObjectManager mapObjectManager = new MapObjectManager();

    public TimeManager getTimeManager() {
        return timeManager;
    }

    public WeatherManager getWeatherManager() {
        return weatherManager;
    }

    public MapObjectManager getMapObjectManager() {
        return mapObjectManager;
    }

    private void Cycle(){
        //[VICK] not socket stuff here :
        timeManager.advance();
        weatherManager.changeWeather();
    }

    public String toJSONString(){
        String ret =
                "{"
                +"mapSize:{x:"+mapSize.getX()+",y:"+mapSize.getY()+"},"
                +"time:"+timeManager.toJSONString()+","
                +"weather:"+weatherManager.toJSONString()+","
                +"mapObjects:"+mapObjectManager.toJSONString()
                +"}";

        return ret;
    }


}
