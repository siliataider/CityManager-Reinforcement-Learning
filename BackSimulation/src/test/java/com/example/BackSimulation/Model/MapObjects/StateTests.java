package com.example.BackSimulation.Model.MapObjects;

import com.google.gson.internal.LinkedTreeMap;
import org.json.JSONObject;
import org.junit.Test;

import static org.assertj.core.api.FactoryBasedNavigableListAssert.assertThat;
import static org.junit.Assert.assertTrue;

public class StateTests {

    State underTest = new State();
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
