package com.example.BackSimulation.Model.MouvableObject;

import com.example.BackSimulation.Model.MapObjects.Agent;

import java.math.BigDecimal;

public interface Mouvable {
    public void setGoal( Building building );
    public BigDecimal  getDistanceNextMove(BigDecimal dx, BigDecimal dy);
    public void setMoveToGoal();
    public void move();
    public void setObstacle(Agent otherAgent);
    public boolean hasArrived();
}
