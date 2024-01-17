package com.example.BackSimulation.DTO;

import com.google.gson.internal.LinkedTreeMap;
import org.springframework.lang.Nullable;

public class AgentDTO {
    private int id;
    @Nullable
    private String action;

    private LinkedTreeMap<String,Double> state;

    public AgentDTO(int id, String action, LinkedTreeMap<String,Double> state) {
        this.id = id;
        this.action = action;
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public LinkedTreeMap<String, Double> getState() {
        return state;
    }

    @Override
    public String toString() {
        return "AgentDTO{" +
                "id=" + id +
                ", action='" + action + '\'' +
                '}';
    }
}
