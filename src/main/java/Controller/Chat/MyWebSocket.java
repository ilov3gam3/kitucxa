package Controller.Chat;

import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@ServerEndpoint("/my-websocket")
public class MyWebSocket {
    private static Map<String, Set<Session>> channels = new HashMap<>();

    @OnOpen
    public void onOpen(Session session) {
        System.out.println("New connection opened. Session ID: " + session.getId());
    }

    @OnClose
    public void onClose(Session session) {
        System.out.println("Connection closed. Session ID: " + session.getId());
        removeSessionFromChannels(session);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        System.out.println("Error occurred on session ID: " + session.getId());
        throwable.printStackTrace();
        removeSessionFromChannels(session);
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("Received message from session ID " + session.getId() + ": " + message);
        processMessage(message, session);
    }

    public void processMessage(String message, Session session) {
        // Assuming message format: "subscribe:channel1"
        if (message.startsWith("subscribe:")) {
            String channel = message.substring(10);
            addSessionToChannel(session, channel);
        } else if (message.startsWith("chat:")) {
            String[] parts = message.split(":", 3);
            if (parts.length == 3) {
                String channel = parts[1];
                String chatMessage = parts[2];
                broadcastToChannel(channel, chatMessage);
            }
        } else {
            // Handle other types of messages
        }
    }

    public void addSessionToChannel(Session session, String channel) {
        channels.computeIfAbsent(channel, k -> new HashSet<>()).add(session);
        System.out.println("Session ID " + session.getId() + " subscribed to channel: " + channel);
    }

    public void removeSessionFromChannels(Session session) {
        channels.values().forEach(sessions -> sessions.remove(session));
    }

    public static void broadcastToChannel(String channel, String message) {
        System.out.println("broadcast to " + channel);
        Set<Session> channelSessions = channels.get(channel);
        if (channelSessions != null) {
            channelSessions.forEach(session -> {
                if (session.isOpen()) {
                    try {
                        session.getBasicRemote().sendText(message);//gửi message tới channel
                    } catch (IOException e) {
                        System.err.println("Error broadcasting message to session ID: " + session.getId());
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
