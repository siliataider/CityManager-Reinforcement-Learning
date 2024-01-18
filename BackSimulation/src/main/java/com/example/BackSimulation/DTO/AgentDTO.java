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
    private List<Double> rewardmoyen;

    public AgentDTO(int id, String action, String algo, LinkedTreeMap<String,Double> state, List<Double> rewardmoyen) {
        this.id = id;
        this.action = action;
        this.algo = algo;
        this.state = state;
        this.rewardmoyen = rewardmoyen;
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

    public List<Double> getRewardmoyen() {
        return rewardmoyen;
    }

    @Override
    public String toString() {
        return "AgentDTO{" +
                "id=" + id +
                ", action='" + action + '\'' +
                '}';
    }


}
