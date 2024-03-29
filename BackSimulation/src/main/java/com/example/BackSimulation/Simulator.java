package com.example.BackSimulation;

import com.corundumstudio.socketio.*;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.example.BackSimulation.DTO.*;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.internal.LinkedTreeMap;
import org.java_websocket.handshake.ServerHandshake;
import org.springframework.stereotype.Service;
import org.java_websocket.client.WebSocketClient;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class Simulator {

    private WebSocketClient pythonWebSocketClient;

    private boolean go = false;
    private int maxWaitTime = 1000;
    private int waitTime;
    private long timeVariable;

    private SocketIOServer server = initServer();
    private Simulation simulation = new Simulation();

    public Simulator() {
        // Initialisez le client WebSocket Python dans le constructeur
        initPythonWebSocketClient();
        waitTime = maxWaitTime;
    }

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

        newServer.addConnectListener(new ConnectListener() {
            @Override
            public void onConnect(SocketIOClient client) {
                System.out.println("Client connected: " + client.getSessionId());
                System.out.println(newServer.getAllClients());
            }
        });

        newServer.addDisconnectListener(new DisconnectListener() {
            @Override
            public void onDisconnect(SocketIOClient client) {
                System.out.println("Client disconnected: " + client.getSessionId());
                System.out.println("Stopping simulation...");
                stopSimulation();
            }
        });
        addListeners(newServer);

        newServer.start(); // Start serveur
        go = true;
        return newServer;

    }
    private void addListeners(SocketIOServer rawServer){
        rawServer.addEventListener("build", String.class, new DataListener<String>() {
            @Override
            public void onData(SocketIOClient client, String data, AckRequest ackRequest) {
                Gson gson = new Gson();
                BuildingDTO building = gson.fromJson(data, BuildingDTO.class);
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


        rawServer.addEventListener("start", String.class, new DataListener<String>() {
            @Override
            public void onData(SocketIOClient client, String data, AckRequest ackRequest) {
                try{
                    System.out.println(data);
                    Gson gson = new Gson();
                    StartDTO start = gson.fromJson(data, StartDTO.class);
                    System.out.println("OK");
                    startSimulation(start);
                }
                catch(Exception e){
                    server.getBroadcastOperations().sendEvent("start",
                            "{"
                                    +"\"response\": \"notok\","
                                    +"\"message\": " +"\"error starting: "+e+"\""
                                    +"}");
                }
            }
        });

        rawServer.addEventListener("speed", String.class, new DataListener<String>() {
            @Override
            public void onData(SocketIOClient client, String data, AckRequest ackRequest) {
                try{
                    Gson gson = new Gson();
                    SpeedDTO speed = gson.fromJson(data, SpeedDTO.class);
                    Double newTime = (1-speed.getSpeed()) * maxWaitTime;
                    waitTime = newTime.intValue();
                    server.getBroadcastOperations().sendEvent("speed",
                            "{"
                                    +"\"response\": \"ok\","
                                    +"\"message\": " +"\"Speed Changed\""
                                    +"}");

                }
                catch(Exception e){
                    server.getBroadcastOperations().sendEvent("speed",
                            "{"
                                    +"\"response\": \"notok\","
                                    +"\"message\": " +"\"error changing speed: "+e+"\""
                                    +"}");
                }
            }
        });

        rawServer.addEventListener("weather", String.class, new DataListener<String>() {
            @Override
            public void onData(SocketIOClient client, String data, AckRequest ackRequest) {
                try{
                    Gson gson = new Gson();
                    WeatherDTO weather = gson.fromJson(data, WeatherDTO.class);
                    simulation.getWeatherManager().changeWeather();
                    server.getBroadcastOperations().sendEvent("weather",
                            "{"
                                    +"\"response\": \"ok\","
                                    +"\"weather\": \"" +simulation.getWeatherManager().toJSONString()+"\""
                                    +"}");

                }
                catch(Exception e){
                    server.getBroadcastOperations().sendEvent("weather",
                            "{"
                                    +"\"response\": \"notok\","
                                    +"\"message\": " +"\"error changing weather: "+e+"\""
                                    +"}");
                }
            }
        });

        rawServer.addEventListener("saveAgent", String.class, new DataListener<String>() {
            @Override
            public void onData(SocketIOClient client, String data, AckRequest ackRequest) {
                try{
                    Gson gson = new Gson();
                    JsonObject saveAgent = gson.fromJson(data, JsonObject.class);

                    server.getBroadcastOperations().sendEvent("saveAgent",
                            "{"
                                    +"\"response\": \"ok\","
                                    +"\"message\": " +"\"Saving Agent model...\""
                                    +"}");

                    sendMessageToPython("{" +
                            "\"event\": \"saveAgent\"," +
                            "\"data\": {" +
                            "\"id\": " + saveAgent.get("id").getAsInt() + "," +
                            "\"filename\": " + "\"" + saveAgent.get("filename").getAsString() +"\"" +
                            "}" +
                            "}"
                    );
                }
                catch(Exception e){
                    server.getBroadcastOperations().sendEvent("saveAgent",
                            "{"
                                    +"\"response\": notok,"
                                    +"\"message\": " +"\"error stopping: "+e+"\""
                                    +"}");
                }
            }
        });



    }

    private void initPythonWebSocketClient() {
        try {
            //pythonWebSocketClient = new WebSocketClient(new URI("wss://citymanagerpython.onrender.com")) {
            pythonWebSocketClient = new WebSocketClient(new URI("ws://localhost:8765")) {
                @Override
                public void onOpen(ServerHandshake handshakedata) {
                    System.out.println("Connected to Python WebSocket server");
                    // Vous pouvez envoyer des messages au serveur ici si nécessaire
                }

                @Override
                public void onMessage(String message) {
                    Gson gson = new Gson();
                    JsonObject pythonResponse = gson.fromJson(message, JsonObject.class);

                    if (Objects.equals(pythonResponse.get("event").getAsString(), "saveAgent")) {
                        saveAgentData(message);
                    } else {
                        readAgentData(message);
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
    private void readAgentData(String message) {

        long responseTime = System.currentTimeMillis() - timeVariable;
        if(responseTime < waitTime){
            try {
                long sleep = waitTime - responseTime;
                System.out.println(sleep);
                if (sleep<=0) {
                    sleep = 0;
                }
                Thread.sleep(sleep);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }


        System.out.println("Received message from Python server: " + message);
        Gson gsonAgent = new Gson();
        AgentListDTO agentListDTO = gsonAgent.fromJson(message, AgentListDTO.class);
        ArrayList<LinkedTreeMap> agentListRaw = agentListDTO.getData().get("agentList");

        ArrayList<AgentDTO> agentList = new ArrayList<AgentDTO>();

        for(int i = 0; i<agentListRaw.size(); i++) {
            Double weirdId = (Double) agentListRaw.get(i).get("id");
            int id = weirdId.intValue();
            String action = (String) agentListRaw.get(i).get("action");
            String algo = (String) agentListRaw.get(i).get("algo");
            LinkedTreeMap<String,Double> state = (LinkedTreeMap<String, Double>) agentListRaw.get(i).get("state");
            List<Double> rewardMoyen = (List<Double>) agentListRaw.get(i).get("reward_moyen");
            Double lifePoint = (Double) agentListRaw.get(i).get("life_point");
            agentList.add(new AgentDTO(id,action,algo,state, rewardMoyen, lifePoint));
        }

        try {
            if(go){
                cycle(agentList);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void saveAgentData(String message) {

    }

    private void startSimulation(StartDTO start) throws Exception {
        boolean verified = simulation.verify(start.getnAgents());
        System.out.println("nAgents: "+start.getnAgents()+"." + verified);
        if(verified){
            server.removeAllListeners("build");
            server.removeAllListeners("start");

            server.addEventListener("stop", String.class, new DataListener<String>() {
                @Override
                public void onData(SocketIOClient client, String data, AckRequest ackRequest) {
                    try{
                        server.getBroadcastOperations().sendEvent("stop",
                                "{"
                                        +"\"response\": \"ok\","
                                        +"\"message\": " +"\"Stopping simulation...\""
                                        +"}");
                        go = false;
                        stopSimulation();

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

            go = true;

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
                    "\"explorationRateDecay\": " + start.getexplorationRateDecay() + "," +
                    "\"maxTimeStep\": " + start.getmaxTimeStep() + "," +
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

    private void cycle(ArrayList<AgentDTO> agentList) throws InterruptedException {
        simulation.getMapObjectManager().updateAgentList(agentList);
        server.getBroadcastOperations().sendEvent("updateAgent","{" +
                "\"agentList\": " + simulation.getMapObjectManager().agentsToJSONString() + "," +
                "\"weather\": " + simulation.getWeatherManager().getWeather().getValue() +"," +
                "\"timestamp\": " + simulation.getTimeManager().getCurrentTick() +
                "}");
        while (simulation.getMapObjectManager().moveAgents()){

            server.getBroadcastOperations().sendEvent("updateAgent","{" +
                    "\"agentList\": " + simulation.getMapObjectManager().agentsToJSONString() + "," +
                    "\"weather\": " + simulation.getWeatherManager().getWeather().getValue() +"," +
                    "\"timestamp\": " + simulation.getTimeManager().getCurrentTick() +
                    "}");

            Thread.sleep(waitTime/100);
        }

        simulation.getTimeManager().advance();

        sendMessageToPython("{" +
                "\"event\": \"updateAgent\"," +
                "\"data\": {" +
                "\"timestamp\": " + simulation.getTimeManager().getCurrentTick() +"," +
                "\"weather\": " + simulation.getWeatherManager().getWeather().getValue() + "," +
                "\"tick\": " + simulation.getTimeManager().getTotalTicks() +
                "}" +
                "}"
        );

        timeVariable = System.currentTimeMillis();
    }

    private void stopSimulation(){
        server.removeAllListeners("stop");
        addListeners(server);
        simulation = new Simulation();
    }
}
