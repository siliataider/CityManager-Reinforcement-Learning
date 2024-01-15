package com.example.BackSimulation.Websocket;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.socket.CloseStatus;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class PythonWebSocketHandler extends TextWebSocketHandler {

    // Utilisez un ensemble pour stocker les sessions des clients connectés
    private final Set<WebSocketSession> sessions = new CopyOnWriteArraySet<>();

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // Gérez les messages reçus ici
        System.out.println("Message reçu: " + message.getPayload());

        // Traitez le message et envoyez une réponse
        String responseMessage = processMessage(message.getPayload());
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // Ajoutez la session du client à l'ensemble lorsqu'une connexion est établie
        sessions.add(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // Supprimez la session du client de l'ensemble lorsqu'il se déconnecte
        sessions.remove(session);
    }

    // Méthode pour envoyer un message à tous les clients connectés
    public void broadcastMessage(String message) throws Exception {
        for (WebSocketSession session : sessions) {
            sendMessageToClient(session, message);
        }
    }
    // Méthode pour envoyer un message au client
    public void sendMessageToClient(WebSocketSession session, String message) throws Exception {
        session.sendMessage(new TextMessage(message));
    }

    private String processMessage(String message) {
        // Votre logique de traitement ici
        return "Traitement terminé. Réponse du serveur.";
    }

}
