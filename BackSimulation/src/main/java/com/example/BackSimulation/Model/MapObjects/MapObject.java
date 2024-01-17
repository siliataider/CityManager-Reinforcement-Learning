package com.example.BackSimulation.Model.MapObjects;
import java.awt.Point;

public abstract class MapObject {
    private int id;
    private Point coords;
    private Point size = new Point(1,1);
    private Object affichage = null;

    public MapObject(int id, Point coords) {
        this.id = id;
        this.coords = coords;
    }

    public Point getCoords() {
        return coords;
    }

    public void setCoords(Point coords) {
        this.coords = coords;
    }

    public int getId(){
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "MapObject{" +
                "id=" + id +
                ", coords=" + coords +
                '}';
    }
}
