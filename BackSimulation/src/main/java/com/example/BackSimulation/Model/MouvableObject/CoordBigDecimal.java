package com.example.BackSimulation.Model.MouvableObject;

import java.math.BigDecimal;

public class CoordBigDecimal {
    public BigDecimal lng;
    public BigDecimal lat;


    public  CoordBigDecimal(BigDecimal lng, BigDecimal lat){
        this.lng = lng;
        this.lat = lat;
    }

    public  CoordBigDecimal(double lng, double lat){
        this.lng = BigDecimal.valueOf(lng);
        this.lat = BigDecimal.valueOf(lat);
    }

    public CoordBigDecimal(int lng, int lat){
        this.lng = BigDecimal.valueOf(lng);
        this.lat = BigDecimal.valueOf(lat);
    }

    public void setZero(){
        this.lng = BigDecimal.valueOf(0);
        this.lat = BigDecimal.valueOf(0);
    }

    @Override
    public String toString() {
        return "{\"lat\":" + this.lat + ","
                + "\"lng\":" + this.lng
                + "}";
    }

    @Override
    public boolean equals(Object obj) {
        CoordBigDecimal other = (CoordBigDecimal) obj;
        return this.lat.equals(other.lat)  && this.lng.equals(other.lng);
    }
}
