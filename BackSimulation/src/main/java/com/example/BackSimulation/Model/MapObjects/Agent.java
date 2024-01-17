package com.example.BackSimulation.Model.MapObjects;

import java.awt.*;

public class Agent extends MapObject{

    private State state;

    public Agent(int id, Point coords) {
        super(id, coords);
        state = new State();
    }

    public Agent(int id, Point coords, State state) {
        super(id, coords);
        this.state = state;
    }

    @Override
    public String toString() {
        return "Agent{" +
                "id=" + getId() +
                ", coords=" + getCoords() +
                "state=" + state +
                '}';
    }
}
