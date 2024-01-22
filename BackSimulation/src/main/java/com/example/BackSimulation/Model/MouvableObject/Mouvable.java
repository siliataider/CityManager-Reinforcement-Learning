package com.example.BackSimulation.Model.MouvableObject;

import com.example.BackSimulation.Model.MapObjects.Building;
import com.example.BackSimulation.Model.MapObjects.MapObject;

import java.util.List;

/**
 * Implementent to get all methodes that allow an object to mov
 * The object is able to calculate the movement, edit it to avoid some obstacles
 * then move
 */
public interface Mouvable {
    /**
     * Building to go to
     * @param building
     */
    public void setGoal( Building building );

    public void setGoal(List<Building> buildings);

    /**
     * save the next step to move to.
     * It doesen't make the object move !
     */
    public void setMoveToGoal();

    /**
     * Make the object move acording to the prpered movement
     */
    public void move();

    /**
     * Some perturbation can be added to the prepared movement
     * @param otherAgent
     */
    public void setObstacle(MapObject otherAgent);

    /**
     * true if the object has arived to the fixed goal,
     * false if not
     */
    public boolean hasArrived();
}
