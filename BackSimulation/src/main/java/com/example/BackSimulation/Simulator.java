package com.example.BackSimulation;

import com.corundumstudio.socketio.*;
import com.corundumstudio.socketio.listener.DataListener;
import com.example.BackSimulation.DTO.BuildingDTO;
import com.example.BackSimulation.Model.MapObjects.Building;
import com.example.BackSimulation.Model.MapObjects.Work;
import com.google.gson.Gson;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.autoconfigure.info.ProjectInfoProperties;

import java.awt.*;

public class Simulator {

    private SocketIOServer server = initServer();
    private Simulation simulation = new Simulation();

    private SocketIOServer initServer(){

        // [VICK] This config needs to go somwere else :
        // [VICK] Use JAVA sdk 11 ! It doesn't work with 17 !
        // SOCKET IO CONFIG :
        Configuration config = new Configuration();
        config.setHostname("localhost");
        config.setPort(5050);

        // SEE : https://github.com/mrniko/netty-socketio/issues/254
        SocketConfig socketConfig = new SocketConfig();
        socketConfig.setReuseAddress(true); // The port will stay bussy after the end of the execution othewise

        config.setSocketConfig(socketConfig);

        SocketIOServer newServer = new SocketIOServer(config);

        // LISTENER WHERE EVENT IS RECIVED
        newServer.addEventListener("build", String.class, new DataListener<String>() {
            @Override
            public void onData(SocketIOClient client, String data, AckRequest ackRequest) {
                Gson gson = new Gson();
                BuildingDTO building = gson.fromJson(data, BuildingDTO.class);
                try{simulation.getMapObjectManager().build(building);}
                catch(Exception e){
                    server.getBroadcastOperations().sendEvent("eventFromBack", "Error building: "+e);
                }
            }
        });

        newServer.addEventListener("start", String.class, new DataListener<String>() {
            @Override
            public void onData(SocketIOClient client, String data, AckRequest ackRequest) {
                //START SIMULATION
            }
        });

        newServer.start(); // Start serveur

        return newServer;
    }


}
