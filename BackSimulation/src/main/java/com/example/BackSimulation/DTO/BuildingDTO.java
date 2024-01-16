package com.example.BackSimulation.DTO;

import com.example.BackSimulation.Model.Enums.BuildingType;
import com.example.BackSimulation.Model.MapObjects.Transaction;

import java.awt.*;

public class BuildingDTO {
    private BuildingType type;
    private Point coords;
    private int openTime;
    private int closeTime;

    public BuildingDTO(String type, int x, int y, int openTime, int closeTime) {
        this.type = BuildingType.valueOf(type);
        this.coords = new Point(x, y);
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
}


