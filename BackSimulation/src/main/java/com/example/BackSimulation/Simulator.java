package com.example.BackSimulation;

import com.example.BackSimulation.Model.*;
import com.example.BackSimulation.Model.MapObjects.Work;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.corundumstudio.socketio.listener.*;
import com.corundumstudio.socketio.*;

import java.awt.*;
@Service
public class Simulator {
    private Point mapSize = new Point(50,50);
    private TimeManager timeManager = new TimeManager();
    private WeatherManager weatherManager = new WeatherManager();

    private MapObjectManager mapObjectManager = new MapObjectManager();

    @Scheduled(fixedRate = 5000)
    private void Cycle(){


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

        final SocketIOServer server = new SocketIOServer(config);

        // LISTENER WHERE EVENT IS RECIVED
        server.addEventListener("eventFromFront", String.class, new DataListener<String>() {
            @Override
            public void onData(SocketIOClient client, String data, AckRequest ackRequest) {
                // DO SOMTHING HERE !
                System.out.println(data);
            }
        });


        server.start(); // Start serveur

        //[VICK] this needs to be removed :
        // Some sleeps since the cycle is looping :
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


        // Send message
        server.getBroadcastOperations().sendEvent("eventFromBack", "your message ");

        //[VICK] this is probably not usefull in the project
        server.stop(); // Stop serveur

        //[VICK] not socket stuff here :

        System.out.println(timeManager.getCurrentTick() + "; " + timeManager.getCurrentDay());
        mapObjectManager.build(new Work(new Point(5,5),1800,45,9,17));
        System.out.println(mapObjectManager.getBuildings());
        timeManager.advance();
    }


}
