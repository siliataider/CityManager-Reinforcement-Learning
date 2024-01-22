package com.example.BackSimulation.DTO;

import com.google.gson.internal.LinkedTreeMap;
import org.springframework.lang.Nullable;

import java.util.ArrayList;
import java.util.List;

public class AgentDTO {
    private int id;
    @Nullable
    private String action;

    private String algo;

    private LinkedTreeMap<String,Double> state;
    private List<Double> rewardMoyen;

    private Double lifePoint;

    public AgentDTO(int id, String action, String algo, LinkedTreeMap<String,Double> state, List<Double> rewardMoyen, Double lifePoint) {
        this.id = id;
        this.action = action;
        this.algo = algo;
        this.state = state;
        this.rewardMoyen = rewardMoyen;
        this.lifePoint = lifePoint;
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

    public String getAlgo() {
        return algo;
    }

    public LinkedTreeMap<String, Double> getState() {
        return state;
    }

    public List<Double> getRewardMoyen() {
        return rewardMoyen;
    }

    public Double getLifePoint() {return lifePoint;}

    @Override
    public String toString() {
        return "AgentDTO{" +
                "id=" + id +
                ", action='" + action + '\'' +
                '}';
    }


}
