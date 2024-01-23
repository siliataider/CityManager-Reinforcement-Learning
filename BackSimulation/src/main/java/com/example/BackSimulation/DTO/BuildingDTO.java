package com.example.BackSimulation.DTO;


import com.example.BackSimulation.Model.Enums.BuildingType;
import com.example.BackSimulation.Model.MouvableObject.CoordBigDecimal;

public class BuildingDTO {
    private BuildingType type;

    private CoordBigDecimal position;

    private int size;
    private int openTime;
    private int closeTime;

    public BuildingDTO(String type, int x, int y, int size, int openTime, int closeTime) {
        this.type = BuildingType.valueOf(type);
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

    public CoordBigDecimal getPosition() {
        return position;
    }

    public int getSize() { return size; }

    public void setPosition(CoordBigDecimal position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "BuildingDTO{" +
                "type=" + type +
                ", coords=" + position +
                ", size=" + size +
                ", openTime=" + openTime +
                ", closeTime=" + closeTime +
                '}';
    }

    public CoordBigDecimal getCoords() {
        return this.position;
    }
}


