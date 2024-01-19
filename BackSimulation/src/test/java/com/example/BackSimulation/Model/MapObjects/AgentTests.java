package com.example.BackSimulation.Model.MapObjects;

import org.json.JSONObject;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.assertTrue;

public class AgentTests{
    Agent underTest = new Agent();
    @Test
    public void itShouldBeConvertedToValidJSON(){
        boolean isValidJSON = true;

        String underTestString = underTest.toJSONString();

        try{
            new JSONObject(underTestString);
        } catch(Exception e){
            isValidJSON = false;
        }

        assertTrue(isValidJSON);

    }
}
