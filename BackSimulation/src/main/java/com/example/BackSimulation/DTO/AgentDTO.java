package com.example.BackSimulation.DTO;

import org.springframework.lang.Nullable;

public class AgentDTO {
    private int id;
    @Nullable
    private String action;

    public AgentDTO(int id, String action) {
        this.id = id;
        this.action = action;
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

    @Override
    public String toString() {
        return "AgentDTO{" +
                "id=" + id +
                ", action='" + action + '\'' +
                '}';
    }
}
