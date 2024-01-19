package com.example.BackSimulation.Model.MapObjects;

import java.awt.*;

public class Building extends MapObject{
    private int openTime;
    private int closeTime;
    private Transaction transaction;

    public Building(int id, Point coords) {
        super(id, coords);
    }

    public Building(Point coords) {
        super(0, coords);
    }

    public Building(Point coords, int openTime, int closeTime) {
        super(0,coords);
        this.openTime = openTime;
        this.closeTime = closeTime;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public void setTimes(int openTime,int closeTime){
        this.openTime=openTime;
        this.closeTime=closeTime;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName()+"; ("+getCoords().getX()+","+getCoords().getY()+")";
    }

    public String toJSONString() {
        String ret =
                "{"
                +"\"type\":"+getClass().getSimpleName()+","
                +"\"openTime\":"+openTime+","
                +"\"closeTime\":"+closeTime+","
                +"\"transaction\":"+transaction.toJSONString()
                +"}";

        return ret;
    }
}
