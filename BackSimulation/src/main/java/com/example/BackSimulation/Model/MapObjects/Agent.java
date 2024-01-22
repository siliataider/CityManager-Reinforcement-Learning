package com.example.BackSimulation.Model.MapObjects;

import com.example.BackSimulation.Model.MouvableObject.CoordBigDecimal;
import com.example.BackSimulation.Model.MouvableObject.MouvableAgent;

import java.awt.*;
import java.util.List;

public class Agent extends MouvableAgent {
    private State state;

    private String color;

    private List<Double> rewardMoyen;
    private Double lifePoint;

    public Agent(int id, CoordBigDecimal coords) {
        super(id, coords);
        state = new State();
        color = "red";
    }

    public Agent(int id, CoordBigDecimal coords, State state, String algo, List<Double> rewardMoyen, Double lifePoint) {
        super(id, coords);
        this.state = state;
        if(algo.equals("QL")){
            color = "red";
        }
        else if(algo.equals("DQL")){
            color = "blue";
        }

        this.rewardMoyen = rewardMoyen;
        this.lifePoint = lifePoint;
    }

    public Agent() {
        super(0 , new CoordBigDecimal(1,1));
        state = new State();
        color = "red";
    }

    public void setState(State state) {
        this.state = state;
    }
    public void setRewardMoyen(List<Double> rewardMoyen) {
        this.rewardMoyen = rewardMoyen;
    }

    public void setLifePoint(Double lifePoint) {
        this.lifePoint = lifePoint;
        if (lifePoint < 0.20 ) {
            this.color = "black";
        }
    }
    public Double getLifePoint() {
        return this.lifePoint;
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
                "\"position\":" + this.coords + "," +
                "\"lifePoint\":" + this.lifePoint + "," +
                "\"rewardMoyen\": " + rewardMoyen +
                "}";
        return ret;
    }
}
