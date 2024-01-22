package com.example.BackSimulation.Model.MapObjects;

import com.example.BackSimulation.Model.Enums.Stats;
import com.example.BackSimulation.Model.MouvableObject.CoordBigDecimal;

import java.awt.*;
import java.util.ArrayList;

public class Work extends Building {
    public Work(CoordBigDecimal coords, int salary, int energy, int openTime, int closeTime){
        super(coords,openTime,closeTime);
        Transaction transaction = new Transaction(Stats.Money,salary,Stats.Energy,energy);
        setTransaction(transaction);
    }

    public Work(CoordBigDecimal coords,int openTime,int closeTime){
        super(coords,openTime,closeTime);
        Transaction transaction = new Transaction(Stats.Money,10,Stats.Energy,10);
        setTransaction(transaction);
    }
}
