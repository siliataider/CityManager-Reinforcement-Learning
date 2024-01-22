package com.example.BackSimulation.Model;

import com.example.BackSimulation.DTO.AgentDTO;
import com.example.BackSimulation.DTO.BuildingDTO;
import com.example.BackSimulation.Model.MapObjects.*;
import com.example.BackSimulation.Model.MouvableObject.CoordBigDecimal;
import com.example.BackSimulation.Model.MouvableObject.MouvableAgent;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MapObjectManager {

    private int idCounter;
    private ArrayList<Building> buildings;
    private List<Agent> agents;

    public MapObjectManager() {
        this.idCounter = 0;
        buildings = new ArrayList<Building>();
    }

    public ArrayList<Building> getBuildings() {
        return buildings;
    }

    public List<Agent> getAgents() {
        return agents;
    }

    private void initAgents (List<AgentDTO> agentDTOList ){
        List<Agent> trueAgents = new ArrayList<>();

        for(int i = 0; i<agentDTOList.size(); i++){
            // Agents always start from home :
            CoordBigDecimal coords = getByType("Home").getCoords();

            String algo = agentDTOList.get(i).getAlgo();
            State state = new State(agentDTOList.get(i).getState());
            List<Double> rewardMoyen = agentDTOList.get(i).getRewardMoyen();
            trueAgents.add(new Agent(agentDTOList.get(i).getId(), coords, state, algo, rewardMoyen));
        }

        agents = trueAgents;
    }

    /**
     * Update agents according to the info contained in the DTO
     * @param agentDTOList
     */
    public void updateAgentList(ArrayList<AgentDTO> agentDTOList) {
        if (this.agents == null){
            System.out.println("INIT");
            this.initAgents(agentDTOList);
        }else{
            for(AgentDTO agentDTO : agentDTOList){
                State state = new State(agentDTO.getState());
                List<Double> rewardMoyen = agentDTO.getRewardMoyen();
                Building building = getByType(agentDTO.getAction());
                Agent agent = this.agents.stream()
                        .filter( x -> x.getId() == agentDTO.getId())
                        .findFirst()
                        .get();

                agent.setState(state);
                agent.setRewardMoyen(rewardMoyen);
                System.out.println("Setting goal");
                agent.setGoal(building);
        }
        }
    }

    /**
     * Manage the agents movements
     */
    public boolean moveAgents(){
        List<MouvableAgent> agentToMove = this.agents.stream()
                .filter( element -> !element.hasArrived())
                .collect(Collectors.toList());

        for (MouvableAgent mouvableAgent : agentToMove){
            if (!mouvableAgent.hasArrived()){
                mouvableAgent.setMoveToGoal();

                List<MouvableAgent> toCloseAgents = agentToMove.stream()
                        .filter( element -> element.getId() != mouvableAgent.getId() && !element.coords.equals(mouvableAgent.coords) )
                        .toList();

                for (MouvableAgent closeAgent : toCloseAgents){
                    mouvableAgent.setObstacle(closeAgent);
                }
                mouvableAgent.move();
            }

        }
        return !agentToMove.isEmpty();

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
        System.out.println(this.buildings);
        System.out.println(type);

        Building building = this.buildings.stream()
                .filter( build -> build.getClass().getSimpleName().equals(type))
                .findFirst()
                .get();
        System.out.println(building);
        return building;

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
