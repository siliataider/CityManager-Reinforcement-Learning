package org.example;

import models.Agent;
import models.Building;

public class Main {

    public static void main(String[] args) {

        Agent agent = new Agent(8.681495F,49.41461F);
        Agent agentB = new Agent(8.687872F,49.420318F);

        Building building = new Building(8.687872F,49.420318F);

        agent.setGoal(building);
        System.out.println("Api Call done");

        while (!agent.hasArrived()){

            agent.setMoveToGoal();
            System.out.println(agent);

            agent.setObstacle(agentB);
            System.out.println(agent);

            agent.move();
            System.out.println(agent);

            System.out.println("========================");
        }
    }
}