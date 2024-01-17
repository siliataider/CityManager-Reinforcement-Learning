package com.example.BackSimulation;

import com.corundumstudio.socketio.*;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.example.BackSimulation.DTO.AgentDTO;
import com.example.BackSimulation.DTO.AgentListDTO;
import com.example.BackSimulation.DTO.BuildingDTO;
import com.example.BackSimulation.DTO.StartDTO;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import org.java_websocket.handshake.ServerHandshake;
import org.springframework.stereotype.Service;
import org.java_websocket.client.WebSocketClient;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.awt.*;

@Service
public class Simulator {

    private WebSocketClient pythonWebSocketClient;

    public Simulator() {
        // Initialisez le client WebSocket Python dans le constructeur
        initPythonWebSocketClient();
    }

    private SocketIOServer server = initServer();
    private Simulation simulation = new Simulation();

    private SocketIOServer initServer(){
        System.out.println("initServer");
        // [VICK] This config needs to go somwere else :
        // SOCKET IO CONFIG :
        Configuration config = new Configuration();
        config.setPort(5050);
        //config.setOrigin("*"); // Permettre toutes les origines (à restreindre en production)

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
                building.setCoords(new Point(building.getX(),building.getY()));
                try{
                    simulation.getMapObjectManager().build(building);
                    server.getBroadcastOperations().sendEvent("build",
                            "{" +
                                    "\"response\": \"ok\","+
                                    "\"message\": \"Successfully built!\"" +
                                    "}");
                }
                catch(Exception e){
                    server.getBroadcastOperations().sendEvent("build",
                            "{" +
                                    "\"response\": \"notok\","
                                    +"\"message\": " +"\"error building: "+e+"\""
                                    +"}");
                }
            }
        });
        newServer.addEventListener("start", String.class, new DataListener<String>() {
            @Override
            public void onData(SocketIOClient client, String data, AckRequest ackRequest) {
                try{
                    Gson gson = new Gson();
                    StartDTO start = gson.fromJson(data, StartDTO.class);
                    startSimulation(start);
                }
                catch(Exception e){
                    server.getBroadcastOperations().sendEvent("start",
                            "{"
                                    +"\"response\": notok,"
                                    +"\"message\": " +"\"error building: "+e+"\""
                                    +"}");
                }
            }
        });

        newServer.addConnectListener(new ConnectListener() {
            @Override
            public void onConnect(SocketIOClient client) {
                System.out.println("Client connected: " + client.getSessionId());
                System.out.println(newServer.getAllClients());

                // Logique supplémentaire à exécuter lors de la connexion...

                // Vous pouvez également envoyer un message au client s'il est nécessaire
                client.sendEvent("connectionResponse", "Welcome to the server!");
            }
        });

        System.out.println("newserver");
        System.out.println(newServer);
        System.out.println(newServer.getConfiguration().getHostname());
        System.out.println(newServer.getConfiguration().getOrigin());
        System.out.println(newServer.getConfiguration().getPort());
        System.out.println(newServer.getConfiguration());
        System.out.println(newServer.getAllClients());

        newServer.start(); // Start serveur
        return newServer;

    }
    private void initPythonWebSocketClient() {
        try {
            pythonWebSocketClient = new WebSocketClient(new URI("wss://citymanagerpython.onrender.com")) {
                @Override
                public void onOpen(ServerHandshake handshakedata) {
                    System.out.println("Connected to Python WebSocket server");
                    // Vous pouvez envoyer des messages au serveur ici si nécessaire
                }

                @Override
                public void onMessage(String message) {
                    System.out.println("Received message from Python server: " + message);
                    Gson gson = new Gson();
                    AgentListDTO agentListDTO = gson.fromJson(message, AgentListDTO.class);
                    ArrayList<LinkedTreeMap> agentListRaw = agentListDTO.getData().get("agentList");

                    ArrayList<AgentDTO> agentList = new ArrayList<AgentDTO>();

                    for(int i = 0; i<agentListRaw.size(); i++) {
                        Double weirdId = (Double) agentListRaw.get(i).get("id");
                        int id = weirdId.intValue();
                        String action = (String) agentListRaw.get(i).get("action");
                        LinkedTreeMap<String,Double> state = (LinkedTreeMap<String, Double>) agentListRaw.get(i).get("state");
                        agentList.add(new AgentDTO(id,action,state));
                    }

                    try {
                        cycle(agentList);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    System.out.println("Connection closed with code " + code + " and reason: " + reason);
                }

                @Override
                public void onError(Exception ex) {
                    System.out.println("Error in WebSocket connection:");
                    ex.printStackTrace();
                }
            };

            pythonWebSocketClient.connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private void startSimulation(StartDTO start) throws Exception {
        boolean verified = simulation.verify(start.getnAgents());
        System.out.println("nAgents: "+start.getnAgents()+"." + verified);
        if(verified){
            server.removeAllListeners("build");
            server.removeAllListeners("start");

            server.getBroadcastOperations().sendEvent("start",
                    "{"
                            +"\"response\":\"ok\","
                            +"\"message\": \"Starting...\","
                            +"\"weather\": " +simulation.getWeatherManager().getWeather().getValue()+","
                            +"\"timestamp\": " + simulation.getTimeManager().getCurrentTick()
                            +"}");

            sendMessageToPython("{" +
                    "\"event\": \"createAgent\"," +
                    "\"data\": {" +
                    "\"nbAgent\": " + start.getnAgents() + "," +
                    "\"timestamp\": " + simulation.getTimeManager().getCurrentTick() +"," +
                    "\"weather\": " + simulation.getWeatherManager().getWeather().getValue() +
                    "}" +
                    "}"
            );

        }
        else{
            server.getBroadcastOperations().sendEvent("start",
                    "{"
                            +"\"response\": \"notok\","
                            +"\"message\": " +"\"error building: Conditions aren't met.\""
                            +"}");
        }

        server.addEventListener("stop", String.class, new DataListener<String>() {
            @Override
            public void onData(SocketIOClient client, String data, AckRequest ackRequest) {
                try{
                    sendMessageToPython("{" +
                            "\"event\": \"stop\"," +
                            "}"
                    );
                    server.getBroadcastOperations().sendEvent("stop",
                            "{"
                                    +"\"response\": ok,"
                                    +"\"message\": " +"\"Stopping simulation...\""
                                    +"}");
                }
                catch(Exception e){
                    server.getBroadcastOperations().sendEvent("stop",
                            "{"
                                    +"\"response\": notok,"
                                    +"\"message\": " +"\"error stopping: "+e+"\""
                                    +"}");
                }
            }
        });


    }

    // Méthode pour envoyer des messages au serveur Python
    private void sendMessageToPython(String message) {
        if (pythonWebSocketClient != null && pythonWebSocketClient.isOpen()) {
            pythonWebSocketClient.send(message);
        }
    }

    // Méthode pour fermer la connexion WebSocket avec le serveur Python
    private void closePythonWebSocket() {
        if (pythonWebSocketClient != null && pythonWebSocketClient.isOpen()) {
            pythonWebSocketClient.close();
        }
    }

    public void cycle(ArrayList<AgentDTO> agentList) throws InterruptedException {
        simulation.getMapObjectManager().setAgents(agentList);
        System.out.println(simulation.getMapObjectManager().getAgents());

        server.getBroadcastOperations().sendEvent("updateAgent","{" +
                "\"agentList\": " + simulation.getMapObjectManager().agentsToJSONString() + "," +
                "\"weather\": " + simulation.getWeatherManager().getWeather().getValue() +"," +
                "\"timestamp\": " + simulation.getTimeManager().getCurrentTick() +
                "}");

        simulation.getTimeManager().advance();

        //Thread.sleep(1000);
        System.out.println("rebelotte");

        sendMessageToPython("{" +
                "\"event\": \"updateAgent\"," +
                "\"data\": {" +
                "\"timestamp\": " + simulation.getTimeManager().getCurrentTick() +"," +
                "\"weather\": " + simulation.getWeatherManager().getWeather().getValue() + "," +
                "\"tick\": " + simulation.getTimeManager().getTotalTicks() +
                "}" +
                "}"
        );


    }
}
