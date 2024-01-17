package com.example.BackSimulation;

import com.corundumstudio.socketio.*;
import com.corundumstudio.socketio.listener.DataListener;
import com.example.BackSimulation.DTO.AgentDTO;
import com.example.BackSimulation.DTO.AgentListDTO;
import com.example.BackSimulation.DTO.BuildingDTO;
import com.example.BackSimulation.DTO.StartDTO;
import com.example.BackSimulation.Model.MapObjects.Agent;
import com.example.BackSimulation.Websocket.PythonWebSocketHandler;
import com.example.BackSimulation.Websocket.WebSocketListener;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.net.http.WebSocket;
import java.util.ArrayList;

@Service
public class Simulator implements WebSocketListener {

    private final PythonWebSocketHandler webSocketHandler;

    public Simulator(PythonWebSocketHandler webSocketHandler){
        this.webSocketHandler = webSocketHandler;

        this.webSocketHandler.setListener(this);

    }
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

        newServer.start(); // Start serveur
        return newServer;

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

            webSocketHandler.broadcastMessage("{" +
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
                    webSocketHandler.broadcastMessage("{" +
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

    public void cycle(ArrayList<AgentDTO> agentList){
        simulation.getMapObjectManager().setAgents(agentList);
        System.out.println(simulation.getMapObjectManager().getAgents());
    }

    @Override
    public void run(String agents) {
        Gson gson = new Gson();
        AgentListDTO agentListDTO = gson.fromJson(agents, AgentListDTO.class);
        ArrayList<LinkedTreeMap> agentListRaw = agentListDTO.getData().get("agentList");

        ArrayList<AgentDTO> agentList = new ArrayList<AgentDTO>();

        for(int i = 0; i<agentListRaw.size(); i++) {
            Double weirdId = (Double) agentListRaw.get(i).get("id");
            int id = weirdId.intValue();
            String action = (String) agentListRaw.get(i).get("action");
            LinkedTreeMap<String,Double> state = (LinkedTreeMap<String, Double>) agentListRaw.get(i).get("state");
            agentList.add(new AgentDTO(id,action,state));
        }

        cycle(agentList);
    }
}
