package com.example.BackSimulation.Model.MapObjects;

import java.awt.*;
import java.util.List;

public class Agent extends MapObject{
    private State state;

    private String color;

    private List<Double> rewardMoyen;

    public Agent(int id, Point coords) {
        super(id, coords);
        state = new State();
    }

    public Agent(int id, Point coords, State state, String algo, List<Double> rewardMoyen) {
        super(id, coords);
        this.state = state;
        if(algo.equals("QL")){
            color = "red";
        }
        if(algo.equals("DQL")){
            color = "blue";
        }
        this.rewardMoyen = rewardMoyen;
    }

    @Override
    public String toString() {
        return "Agent{" +
                "id=" + getId() +
                ", coords=" + getCoords() +
                "state=" + state +
                '}';
    }

    public String toJSONString() {
        String ret = "{" +
                "\"id\": " + getId() + "," +
                "\"color\": \"" + color + "\"," +
                "\"state\": " + state.toJSONString() + "," +
                "\"rewardMoyen\": " + rewardMoyen +
                "}";
        return ret;
    }
}
