package com.example.BackSimulation;

import com.example.BackSimulation.Model.*;

import java.awt.*;
import java.util.Dictionary;
import java.util.HashMap;

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

    public boolean verify(int nAgents){
        HashMap<String,Boolean> hasBuildings = new HashMap<String,Boolean>();
        hasBuildings.put("Work",false);
        hasBuildings.put("Home",false);
        hasBuildings.put("Resto",false);

        for(int i = 0; i<mapObjectManager.getBuildings().size(); i++){
            hasBuildings.put(mapObjectManager.getBuildings().get(i).getClass().getSimpleName(),true);
        }

        if(nAgents>=1 & hasBuildings.get("Work") & hasBuildings.get("Home") & hasBuildings.get("Resto")){
            return true;
        }
        return false;
    }

    private void cycle(){
        timeManager.advance();
        weatherManager.changeWeather();
        System.out.println("Simulation Progress");
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
