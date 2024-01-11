package com.example.BackSimulation.Model.MapObjects;

import java.awt.*;

public class Agent extends MapObject{

    public Agent(int id, Point coords, Object affichage) {
        super(id, coords, new Point(1,1), affichage);
    }
}
