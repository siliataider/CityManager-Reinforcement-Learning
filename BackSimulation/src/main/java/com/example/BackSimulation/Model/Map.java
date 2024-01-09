package com.example.BackSimulation.Model;

import com.example.BackSimulation.Model.MapObjects.Agent;
import com.example.BackSimulation.Model.MapObjects.Building;
import com.example.BackSimulation.Model.MapObjects.BuildingFactory;

import java.awt.*;
import java.util.ArrayList;

public class Map {
    private Point size;
    private BuildingFactory factory;
    private ArrayList<Building> buildings;
    private ArrayList<Agent> agents;

    public Map(Point size){
        this.size = size;
        factory = new BuildingFactory();
        buildings = factory.getBuildings();
        agents = new ArrayList<Agent>();
    }
}
