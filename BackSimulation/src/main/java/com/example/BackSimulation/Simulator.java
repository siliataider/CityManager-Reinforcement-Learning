package com.example.BackSimulation;

import com.example.BackSimulation.Model.Map;
import com.example.BackSimulation.Model.MapObjects.Home;
import com.example.BackSimulation.Model.MapObjects.Resto;
import com.example.BackSimulation.Model.MapObjects.Work;
import com.example.BackSimulation.Model.TimeManager;
import com.example.BackSimulation.Model.WeatherManager;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.awt.*;

@Service
public class Simulator {
    private Map map = new Map(new Point(50,50));
    private TimeManager timeManager = new TimeManager();
    private WeatherManager weatherManager = new WeatherManager();

    @Scheduled(fixedRate = 5000)
    private void Cycle(){
        System.out.println(timeManager.getCurrentTick() + "; " + timeManager.getCurrentDay());
        map.addBuilding(new Work(new Point(5,5)));
        map.addBuilding(new Home(new Point(9,10)));
        map.addBuilding(new Resto(new Point(2,14)));
        map.removeBuilding(2);
        System.out.println(map.getBuildings());
        timeManager.advance();

    }


}
