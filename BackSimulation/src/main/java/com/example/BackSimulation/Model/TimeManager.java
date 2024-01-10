package com.example.BackSimulation.Model;

public class TimeManager {
    private int currentTick = 0;
    private int currentDay = 1;
    private int ticksPerDay = 24;


    public TimeManager() {
    }

    public int getCurrentTick() {
        return currentTick;
    }

    public void setCurrentTick(int currentTick) {
        this.currentTick = currentTick;
    }

    public int getCurrentDay() {
        return currentDay;
    }

    public void setCurrentDay(int currentDay) {
        this.currentDay = currentDay;
    }

    public int getTicksPerDay() {
        return ticksPerDay;
    }

    public void setTicksPerDay(int ticksPerDay) {
        this.ticksPerDay = ticksPerDay;
    }

    public void advance(){
        setCurrentTick(getCurrentTick()+1);
        if(getCurrentTick()>=getTicksPerDay()){
            setCurrentTick(0);
            setCurrentDay(getCurrentDay()+1);
        }
    }
}
