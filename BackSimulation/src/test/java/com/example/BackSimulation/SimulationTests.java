package com.example.BackSimulation;

import com.example.BackSimulation.DTO.AgentDTO;
import com.example.BackSimulation.DTO.BuildingDTO;
import com.example.BackSimulation.Model.Enums.BuildingType;
import com.example.BackSimulation.Model.MapObjects.Agent;
import com.example.BackSimulation.Model.MapObjects.State;
import com.google.gson.internal.LinkedTreeMap;
import org.json.JSONObject;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertTrue;

public class SimulationTests {

    @Test
    public void ItShouldAddMapObjectsAndBeConvertedToJSONString(){
        boolean isValidJSON = true;

        Simulation underTest = new Simulation();

        ArrayList<AgentDTO> agentDTOS = new ArrayList<AgentDTO>();
        LinkedTreeMap<String, Double> state = new LinkedTreeMap<String,Double>();
        state.put("Hunger",1.0);
        state.put("Money",1.0);
        state.put("Work",1.0);
        AgentDTO agent1 = new AgentDTO(0,null,"QL", state, new ArrayList<Double>());
        AgentDTO agent2 = new AgentDTO(1,"Home","DQL", state, new ArrayList<Double>());
        agentDTOS.add(agent1);
        agentDTOS.add(agent2);
        underTest.getMapObjectManager().setAgents(agentDTOS);

        BuildingDTO building1 = new BuildingDTO("work",100,100,2,12,16);
        BuildingDTO building2 = new BuildingDTO("home",150,100,2,0,0);
        BuildingDTO building3 = new BuildingDTO("resto",200,100,2,18,23);
        underTest.getMapObjectManager().build(building1);
        underTest.getMapObjectManager().build(building2);
        underTest.getMapObjectManager().build(building3);

        String underTestString = underTest.toJSONString();

        try{
            new JSONObject(underTestString);
        } catch(Exception e){
            isValidJSON = false;
        }

        assertTrue(isValidJSON);

    }


}
