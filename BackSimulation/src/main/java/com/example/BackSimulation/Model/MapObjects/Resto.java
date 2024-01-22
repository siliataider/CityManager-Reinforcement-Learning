package com.example.BackSimulation.Model.MapObjects;

import com.example.BackSimulation.Model.Enums.Stats;
import com.example.BackSimulation.Model.MouvableObject.CoordBigDecimal;

import java.awt.*;

public class Resto extends Building {
    public Resto(CoordBigDecimal coords, int nutriments, int cost, int openTime, int closeTime){
        super(coords,openTime,closeTime);
        Transaction transaction = new Transaction(Stats.Food,nutriments,Stats.Money,cost);
        setTransaction(transaction);
    }

    public Resto(CoordBigDecimal coords, int openTime,int closeTime){
        super(coords,openTime,closeTime);
        Transaction transaction = new Transaction(Stats.Food,10,Stats.Money,10);
        setTransaction(transaction);
    }
}
