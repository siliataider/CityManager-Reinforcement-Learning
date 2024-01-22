package com.example.BackSimulation.Model.MapObjects;
import com.example.BackSimulation.Model.MouvableObject.CoordBigDecimal;

import java.awt.Point;

public abstract class MapObject {
    private int id;
    public CoordBigDecimal coords;

    public MapObject(int id, CoordBigDecimal coords) {
        this.id = id;
        this.coords = new CoordBigDecimal(coords.lng.doubleValue(), coords.lat.doubleValue());
    }

    public CoordBigDecimal getCoords() {
        return coords;
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
