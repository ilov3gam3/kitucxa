import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@ServerEndpoint("/my-websocket")
public class MyWebSocket {
    private static Set<Session> sessions = new HashSet<>();

    @OnOpen
    public void onOpen(Session session) {
        sessions.add(session);
        System.out.println("New connection opened. Session ID: " + session.getId());
    }
    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        sessions.remove(session);
        System.out.println("Connection closed. Session ID: " + session.getId() + ", Close Reason: " + closeReason.getReasonPhrase());
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        System.out.println("Error occurred on session ID: " + session.getId());
        throwable.printStackTrace();
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("Received message from session ID " + session.getId() + ": " + message);
        broadcast(message);
    }

    private void broadcast(String message) {
        for (Session session : sessions) {
            if (session.isOpen()) {
                try {
                    session.getBasicRemote().sendText(message);
                } catch (IOException e) {
                    System.err.println("Error broadcasting message to session ID: " + session.getId());
                    e.printStackTrace();
                }
            }
        }
    }
}
