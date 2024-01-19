package com.example.BackSimulation.Model;

import com.example.BackSimulation.DTO.AgentDTO;
import com.example.BackSimulation.DTO.BuildingDTO;
import com.example.BackSimulation.Model.Enums.BuildingType;
import com.example.BackSimulation.Model.MapObjects.*;
import com.example.BackSimulation.Model.MouvableObject.CoordBigDecimal;

import javax.naming.ldap.Control;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

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

    public ArrayList<Agent> getAgents() {
        return agents;
    }

    public void setAgents(ArrayList<AgentDTO> agentDTOList) {
        ArrayList<Agent> trueAgents = new ArrayList<Agent>();

        for(int i = 0; i<agentDTOList.size(); i++){
            CoordBigDecimal coords = getByType(agentDTOList.get(i).getAction()).getCoords();
            if(agentDTOList.get(i).getAction() == null){
                coords = getByType("Home").getCoords();
            }
            String algo = agentDTOList.get(i).getAlgo();
            State state = new State(agentDTOList.get(i).getState());
            List<Double> rewardMoyen = agentDTOList.get(i).getRewardmoyen();
            trueAgents.add(new Agent(agentDTOList.get(i).getId(), coords, state, algo, rewardMoyen));
        }

        agents = trueAgents;
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
        for (int i = 0; i < this.buildings.size(); i++){
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
            if(buildings.get(i).getClass().getSimpleName().equals(type)){
                return buildings.get(i);
            }
        }
        return new Building(new CoordBigDecimal(1,1));
    }

    public String agentsToJSONString() {
        String agentsJSON = "[";
        for(int i=0;i<agents.size();i++){
            if(i>0){
                agentsJSON+=",";
            }
            agentsJSON+=agents.get(i).toJSONString();
        }
        agentsJSON+="]";

        return agentsJSON;
    }

    public String buildingsToJSONString(){
        String buildingsJSON = "[";
        for(int i=0;i<buildings.size();i++){
            if(i>0){
                buildingsJSON+=",";
            }
            buildingsJSON+=buildings.get(i).toJSONString();
        }
        buildingsJSON+="]";

        return buildingsJSON;
    }

    public String toJSONString(){
        return "{\"agents\": " + agentsToJSONString()+"," +
                "\"buildings\": " + buildingsToJSONString() +
                "}";
    }
}
