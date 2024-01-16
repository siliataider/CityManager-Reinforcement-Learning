package com.example.BackSimulation.DTO;

import com.google.gson.internal.LinkedTreeMap;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class AgentListDTO {
    private String event;
    private LinkedTreeMap<String,ArrayList> data;

    public String getEvent() {
        return event;
    }

    public LinkedTreeMap<String,ArrayList> getData() {
        return data;
    }
}
