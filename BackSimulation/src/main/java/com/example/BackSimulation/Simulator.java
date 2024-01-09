package com.example.BackSimulation;

import com.example.BackSimulation.Model.Map;
import com.example.BackSimulation.Model.TimeManager;
import com.example.BackSimulation.Model.WeatherManager;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.scheduling.annotation.Scheduled;

import java.awt.*;

public class Simulator {
    private Map map = new Map(new Point(50,50));
    private TimeManager timeManager = new TimeManager();
    private WeatherManager weatherManager = new WeatherManager();

    @Scheduled(fixedRate = 2000)
    private void Cycle(){

    }


}
