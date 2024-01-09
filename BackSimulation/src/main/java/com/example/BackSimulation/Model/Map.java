package com.example.BackSimulation.Model;

import com.example.BackSimulation.Model.MapObjects.Agent;
import com.example.BackSimulation.Model.MapObjects.Building;

import java.awt.*;
import java.util.ArrayList;

public class Map {
    private Point size;
    private BuildingManager buildingManager = new BuildingManager();
    private ArrayList<Building> buildings;
    private ArrayList<Agent> agents = new ArrayList<Agent>();

    public Map(Point size){
        this.size = size;
        buildings = buildingManager.getBuildings();
    }

    public ArrayList<Building> getBuildings() {
        return buildings;
    }

    private void setBuildings(ArrayList<Building> buildings) {
        this.buildings = buildings;
    }

    public void addBuilding(Building building){
        buildingManager.build(building);
        setBuildings(buildingManager.getBuildings());
        /*System.out.println("AYO");
        System.out.println(buildingManager.getBuildings());
        System.out.println(getBuildings());
        System.out.println("OYA");*/

    }

    public void removeBuilding(int id){
        buildingManager.unbuild(id);
        setBuildings(buildingManager.getBuildings());
    }
}
