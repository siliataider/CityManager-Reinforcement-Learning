package com.example.BackSimulation.Model;

import com.example.BackSimulation.DTO.AgentDTO;
import com.example.BackSimulation.DTO.BuildingDTO;
import com.example.BackSimulation.Model.Enums.BuildingType;
import com.example.BackSimulation.Model.MapObjects.*;

import java.awt.*;
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

    public void setAgents(ArrayList<AgentDTO> agentDTOList) {
        ArrayList<Agent> trueAgents = new ArrayList<Agent>();

        for(int i = 0; i<agentDTOList.size(); i++){
            Point coords = getByType("Home").getCoords();
            if(agentDTOList.get(i).getAction() != null){
                System.out.println("c'est pas null");
                coords = getByType(agentDTOList.get(i).getAction()).getCoords();
            }
            trueAgents.add(new Agent(agentDTOList.get(i).getId(),coords));
        }
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

    public void build(BuildingDTO building){
        setIdCounter(getIdCounter()+1);
        switch(building.getType()){
            case work :
                Work work = new Work(building.getCoords(),building.getOpenTime(),building.getCloseTime());
                buildings.add(work);
                break;
            case home :
                Home home = new Home(building.getCoords(),building.getOpenTime(),building.getCloseTime());
                buildings.add(home);
                break;
            case resto :
                Resto resto = new Resto(building.getCoords(),building.getOpenTime(),building.getCloseTime());
                buildings.add(resto);
                break;
        }

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

    public Building getByType(String type){
        for(int i = 0; i < buildings.size(); i++){
            if(buildings.get(i).getClass().getSimpleName() == "type"){
                return buildings.get(i);
            }
        }
        return null;
    }

    public String toJSONString() {
        String buildingsJSON = "[";
        for(int i=0;i<buildings.size();i++){
            if(i>0){
                buildingsJSON+=",";
            }
            buildingsJSON+=buildings.get(i).toJSONString();
        }
        buildingsJSON+="]";

        String ret =
                "{"
                +"buildings:"+buildingsJSON
                +"}";

        return ret;
    }
}
