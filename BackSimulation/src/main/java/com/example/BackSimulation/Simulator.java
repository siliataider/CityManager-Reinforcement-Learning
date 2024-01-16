package com.example.BackSimulation;

import com.corundumstudio.socketio.*;
import com.corundumstudio.socketio.listener.DataListener;
import com.example.BackSimulation.DTO.BuildingDTO;
import com.example.BackSimulation.Websocket.PythonWebSocketHandler;
import com.google.gson.Gson;

public class Simulator {

    private final PythonWebSocketHandler webSocketHandler = new PythonWebSocketHandler();

    private SocketIOServer server = initServer();
    private Simulation simulation = new Simulation();

    private SocketIOServer initServer(){
        // [VICK] This config needs to go somwere else :
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
                try{
                    simulation.getMapObjectManager().build(building);
                    server.getBroadcastOperations().sendEvent("build",
                            "{" +
                                    "response: ok," +
                                    "message: Successfully built!" +
                                    "}");
                }
                catch(Exception e){
                    server.getBroadcastOperations().sendEvent("build",
                            "{" +
                                    "response: notok,"
                                    +"message: " +"error building: "+e
                                    +"}");
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

    public String sendDataToPython() {
        String res = "{" +
                "\"action\": 0, " +
                "\"agents\": " + "[" +
                "{" +
                "\"agent_id\": 0, " +
                "\"weather\":0, " +
                "\"timestamp\": 8, " +
                "\"hunger\": 0.5, " +
                "\"energy\": 0.5, " +
                "\"money\": 0.5 " +
                "}" +
                "]" +
                "}";
        return res;
    }

}
