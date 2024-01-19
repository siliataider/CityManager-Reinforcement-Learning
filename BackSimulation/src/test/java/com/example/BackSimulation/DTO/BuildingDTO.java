package com.example.BackSimulation.DTO;

import com.example.BackSimulation.Model.Enums.BuildingType;

import java.awt.*;

public class BuildingDTO {
    private BuildingType type;

    private int x;
    private int y;
    private Point coords;

    private int size;
    private int openTime;
    private int closeTime;

    public BuildingDTO(String type, int x, int y, int size, int openTime, int closeTime) {
        this.type = BuildingType.valueOf(type);
        this.x = x;
        this.y = y;
        this.size = size;
        this.openTime = openTime;
        this.closeTime = closeTime;
    }

    public BuildingType getType() {
        return type;
    }

    public int getOpenTime() {
        return openTime;
    }

    public int getCloseTime() {
        return closeTime;
    }

    public Point getCoords() {
        return coords;
    }

    public int getSize() { return size; }

    public void setCoords(Point coords) {
        this.coords = coords;
    }

    @Override
    public String toString() {
        return "BuildingDTO{" +
                "type=" + type +
                ", x=" + x +
                ", y=" + y +
                ", coords=" + coords +
                ", size=" + size +
                ", openTime=" + openTime +
                ", closeTime=" + closeTime +
                '}';
    }

    public int getX() {
        return x;
    }

    public int getY(){
        return y;
    }
}


