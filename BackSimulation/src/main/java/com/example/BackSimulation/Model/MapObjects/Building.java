package com.example.BackSimulation.Model.MapObjects;

import com.example.BackSimulation.Model.MouvableObject.CoordBigDecimal;

import java.awt.*;
import java.math.BigDecimal;

public class Building extends MapObject{
    private int openTime;
    private int closeTime;
    private Transaction transaction;

    public Building(int id, CoordBigDecimal coords) {
        super(id, coords);
    }

    public Building(CoordBigDecimal coords) {
        super(0, coords);
    }

    public Building(CoordBigDecimal coords, int openTime, int closeTime) {
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

    public CoordBigDecimal getCoords(){
        return this.coords;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName()+"; ("+getCoords() + ")";
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
