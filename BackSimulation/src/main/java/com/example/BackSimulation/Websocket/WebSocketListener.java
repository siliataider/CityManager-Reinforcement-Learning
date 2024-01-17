package com.example.BackSimulation.Websocket;

import org.springframework.boot.autoconfigure.jms.JmsProperties;

public interface WebSocketListener {
    public void run(String agents);
}
