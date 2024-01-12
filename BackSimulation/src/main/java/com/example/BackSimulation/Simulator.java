package com.example.BackSimulation;

import com.example.BackSimulation.Model.*;
import com.example.BackSimulation.Model.MapObjects.Work;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.awt.*;

@Service
public class Simulator {
    private Point mapSize = new Point(50,50);
    private TimeManager timeManager = new TimeManager();
    private WeatherManager weatherManager = new WeatherManager();

    private MapObjectManager mapObjectManager = new MapObjectManager();

    @Scheduled(fixedRate = 5000)
    private void Cycle(){
        mapObjectManager.build(new Work(new Point(5,5),1800,45,9,17));
        System.out.println(toJSONString());
        timeManager.advance();

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
