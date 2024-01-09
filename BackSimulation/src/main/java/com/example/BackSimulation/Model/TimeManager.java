package com.example.BackSimulation.Model;

public class TimeManager {
    private int currentTick;
    private int currentDay;
    private int ticksPerDay = 10;


    public TimeManager() {
        currentTick=0;
        currentDay=0;
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

    @Override
    public String toString() {
        return "Day "+getCurrentDay()+"; Tick "+getCurrentTick();
    }
}
