package com.example.BackSimulation.DTO;

public class StartDTO {
    private int nAgents;

    private int explorationRateDecay;
    private int maxTimeStep;

    public int getnAgents() {
        return nAgents;
    }
    public int getexplorationRateDecay() {
        return explorationRateDecay;
    }
    public int getmaxTimeStep() {
        return maxTimeStep;
    }
}
