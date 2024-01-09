package com.example.BackSimulation.Model.MapObjects;
import java.awt.Point;

public abstract class MapObject {
    private int id;
    private Point coords;
    private Point size;
    private Object affichage;

    public MapObject(int id, Point coords, Point size, Object affichage) {
        this.id = id;
        this.coords = coords;
        this.size = size;
        this.affichage = affichage;
    }

    public Point getCoords() {
        return coords;
    }

    public int getId(){
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
