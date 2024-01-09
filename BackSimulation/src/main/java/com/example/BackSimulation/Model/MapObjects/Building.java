package com.example.BackSimulation.Model.MapObjects;

import com.example.BackSimulation.Model.Transaction;

import java.awt.*;

public class Building extends MapObject{
    private int openTime;
    private int closeTime;
    private Transaction transaction;

    public Building(int id, Point coords, Point size, Object affichage) {
        super(id, coords, size, affichage);
    }

    public Building(Point coords) {
        super(0, coords, new Point(2,2), null);
    }

    @Override
    public String toString() {
        return this.getClass().getName()+"; "+getCoords();
    }
}
