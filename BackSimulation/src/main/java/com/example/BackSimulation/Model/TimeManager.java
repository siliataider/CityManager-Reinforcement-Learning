package com.example.BackSimulation.Model;

public class TimeManager {
    private int currentTick = 1;
    private int currentDay = 1;
    private int ticksPerDay = 10;


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
        if(getCurrentTick()>getTicksPerDay()){
            setCurrentTick(1);
            setCurrentDay(getCurrentDay()+1);
        }
    }
}
