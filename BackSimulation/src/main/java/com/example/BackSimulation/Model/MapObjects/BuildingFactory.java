package com.example.BackSimulation.Model.MapObjects;

import java.util.ArrayList;

public class BuildingFactory {

    private int idCounter;
    private ArrayList<Building> buildings;

    public BuildingFactory() {
        this.idCounter = 0;
        buildings = new ArrayList<Building>();
    }

    public ArrayList<Building> getBuildings() {
        return buildings;
    }

    private int getIdCounter() {
        return idCounter;
    }

    private void setIdCounter(int idCounter) {
        this.idCounter = idCounter;
    }

    public void build(Building building){
        setIdCounter(getIdCounter()+1);
        building.setId(getIdCounter());

    }

    public void unbuild(int id){
        for (int i = 0; i < buildings.size(); i++){
            if(buildings.get(i).getId() == id){
                buildings.remove(i);
            }
        }
    }
}
