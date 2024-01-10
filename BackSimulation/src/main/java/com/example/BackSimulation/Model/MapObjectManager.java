package com.example.BackSimulation.Model;

import com.example.BackSimulation.Model.MapObjects.Agent;
import com.example.BackSimulation.Model.MapObjects.Building;

import java.util.ArrayList;

public class MapObjectManager {

    private int idCounter;
    private ArrayList<Building> buildings;
    private ArrayList<Agent> agents;

    public MapObjectManager() {
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
        buildings.add(building);

    }

    public void spawn(Agent agent){
        setIdCounter(getIdCounter()+1);
        agent.setId(getIdCounter());
        agents.add(agent);

    }
    public void unbuild(int id){
        for (int i = 0; i < buildings.size(); i++){
            if(buildings.get(i).getId() == id){
                buildings.remove(i);
            }
        }
    }

    public void unspawn(int id){
        for (int i = 0; i < agents.size(); i++){
            if(agents.get(i).getId() == id){
                agents.remove(i);
            }
        }
    }
}