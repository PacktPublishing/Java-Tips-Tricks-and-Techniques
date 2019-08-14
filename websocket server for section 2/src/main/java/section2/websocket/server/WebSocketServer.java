package section2.websocket.server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.*;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

import java.io.IOException;

public class WebSocketServer {

    public static void main(String[] args) throws Exception {
        Server server = new Server(8080);
        var wsHandler = new org.eclipse.jetty.websocket.server.WebSocketHandler() {
            @Override
            public void configure(WebSocketServletFactory factory) {
                factory.register(WebSocketHandler.class);
            }
        };
        server.setHandler(wsHandler);
        server.start();
        server.join();
    }

    @WebSocket
    public static class WebSocketHandler {

        @OnWebSocketConnect
        public void onConnect(Session session) {
            System.out.println("Connect: " + session.getRemoteAddress().getAddress());
            try {
                session.getRemote().sendString("Hello from jetty");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @OnWebSocketClose
        public void onClose(int statusCode, String reason) {
            System.out.println("Close: statusCode=" + statusCode + ", reason=" + reason);
        }

        @OnWebSocketError
        public void onError(Throwable t) {
            System.out.println("Error: " + t.getMessage());
        }

        @OnWebSocketMessage
        public void onMessage(String message) {
            System.out.println("Message: " + message);
        }
    }
}

