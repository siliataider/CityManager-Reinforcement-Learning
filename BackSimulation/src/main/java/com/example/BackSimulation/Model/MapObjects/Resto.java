package com.example.BackSimulation.Model.MapObjects;

import com.example.BackSimulation.Model.Enums.Stats;

import java.awt.*;
import java.util.ArrayList;

public class Resto extends Building {
    public Resto(Point coords,int nutriments, int cost,int openTime,int closeTime){
        super(coords,openTime,closeTime);
        Transaction transaction = new Transaction(Stats.Food,nutriments,Stats.Money,cost);
        setTransaction(transaction);
    }
}
