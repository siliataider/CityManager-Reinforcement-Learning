package com.example.BackSimulation;

import com.example.BackSimulation.DTO.AgentDTO;
import com.example.BackSimulation.DTO.BuildingDTO;
import com.example.BackSimulation.Model.MouvableObject.CoordBigDecimal;
import com.google.gson.internal.LinkedTreeMap;
import org.json.JSONObject;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SimulationTests {

    @Test
    public void ItShouldAddMapObjectsAndBeConvertedToJSONString(){
        boolean isValidJSON = true;

        Simulation underTest = new Simulation();

        BuildingDTO building1 = new BuildingDTO("work",100,100,2,12,16);
        building1.setPosition(new CoordBigDecimal(100,100));
        BuildingDTO building2 = new BuildingDTO("home",150,100,2,0,0);
        building2.setPosition(new CoordBigDecimal(150,100));
        BuildingDTO building3 = new BuildingDTO("resto",200,100,2,18,23);
        building3.setPosition(new CoordBigDecimal(200,100));
        underTest.getMapObjectManager().build(building1);
        underTest.getMapObjectManager().build(building2);
        underTest.getMapObjectManager().build(building3);

        ArrayList<AgentDTO> agentDTOS = new ArrayList<AgentDTO>();
        LinkedTreeMap<String, Double> state = new LinkedTreeMap<String,Double>();
        state.put("Hunger",1.0);
        state.put("Money",1.0);
        state.put("Work",1.0);
        AgentDTO agent1 = new AgentDTO(0,null,"QL", state, new ArrayList<Double>(), 0.5);
        AgentDTO agent2 = new AgentDTO(1,"Home","DQL", state, new ArrayList<Double>(), 0.5);
        agentDTOS.add(agent1);
        agentDTOS.add(agent2);
        underTest.getMapObjectManager().updateAgentList(agentDTOS);

        String underTestString = underTest.toJSONString();

        try{
            new JSONObject(underTestString);
        } catch(Exception e){
            isValidJSON = false;
        }

        assertTrue(isValidJSON);

    }

    @Test
    public void ItShouldVerifyASimulationIsValid(){

        Simulation underTest = new Simulation();

        assertFalse(underTest.verify(0));
        assertFalse(underTest.verify(5));

        BuildingDTO building1 = new BuildingDTO("work",100,100,2,12,16);
        building1.setPosition(new CoordBigDecimal(100,100));
        BuildingDTO building2 = new BuildingDTO("home",150,100,2,0,0);
        building2.setPosition(new CoordBigDecimal(150,100));
        BuildingDTO building3 = new BuildingDTO("resto",200,100,2,18,23);
        building3.setPosition(new CoordBigDecimal(200,100));

        underTest.getMapObjectManager().build(building1);
        underTest.getMapObjectManager().build(building2);

        assertFalse(underTest.verify(0));
        assertFalse(underTest.verify(5));

        underTest.getMapObjectManager().build(building3);

        assertFalse(underTest.verify(0));



        assertTrue(underTest.verify(5));
    }


}
