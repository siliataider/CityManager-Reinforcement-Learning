package com.example.BackSimulation.Model.MapObjects;

import com.example.BackSimulation.Model.Enums.Stats;
import com.example.BackSimulation.Model.MouvableObject.CoordBigDecimal;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Home extends Building{
    public Home(CoordBigDecimal coords, int rest, int openTime, int closeTime){
        super(coords, 0,0);
        Transaction transaction = new Transaction(Stats.Energy,rest,null,0);
        setTransaction(transaction);
    }

    public Home(CoordBigDecimal coords, int openTime, int closeTime){
        super(coords, 0,0);
        Transaction transaction = new Transaction(Stats.Energy,10,null,0);
        setTransaction(transaction);
    }
}
