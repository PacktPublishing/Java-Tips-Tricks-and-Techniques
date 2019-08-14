package section2;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;

public class P06WebSocket {

    public static void main(String[] args) {
        new P06WebSocket().demo();
    }

    private void demo() {
        try {

            WebSocket server = new WebSocketConfig().getWebSocket();
            server.sendText("Hi!", true);

            // statusCode must be between 1000 and 65535 inclusively
            // reason must not be longer then 123 bytes in UTF-8 representation
            server.sendClose(1001, "Bye!");

        } catch (Exception e) {
            if (e.getMessage().contains("WebSocketHandshakeException")) {
                var hex = ((java.net.http.WebSocketHandshakeException) e.getCause()).getResponse();
                System.err.println("Body:\t" + hex.body());
                System.err.println("HTTP request:  " + hex.request());
                System.err.println("HTTP version:  " + hex.version());
                System.err.println("Headers:");
                hex.headers().map().forEach((h, v) -> System.err.println("  " + h + ":  " + v));
                System.err.println("Previous response:  " + hex.previousResponse());
            } else {
                e.printStackTrace();
            }
        }
    }
}

@SuppressWarnings("FieldCanBeLocal")
class WebSocketConfig {
    private final String ENDPOINT = "ws://localhost:8080";
    private final WebSocket server;

    WebSocketConfig() throws InterruptedException, ExecutionException {
        WebSocket.Listener listener = new WebSocketListener();
        CompletableFuture<WebSocket> futureWebSocket;
        futureWebSocket = HttpClient.newHttpClient().newWebSocketBuilder().buildAsync(URI.create(ENDPOINT), listener);
        server = futureWebSocket.get();
    }

    WebSocket getWebSocket() {
        return this.server;
    }
}

class WebSocketListener implements WebSocket.Listener {

    // Additional methods to implement: onBinary, onPing, onPong

    public void onOpen(WebSocket webSocket) {
        webSocket.request(1);
        System.out.println("WebSocket listener opened.");
    }

    public CompletionStage<?> onText(WebSocket webSocket, CharSequence data, boolean last) {
        System.out.println(data);
        webSocket.request(1);
        return new CompletableFuture<String>().completeAsync(() -> "onText() completed.").thenAccept(System.out::println);
    }

    public CompletionStage<?> onClose(WebSocket webSocket, int statusCode, String reason) {
        System.out.println("WebSocket listener closed, statusCode:" + statusCode);
        System.out.println("Cause: " + reason);
        webSocket.sendClose(statusCode, reason);
        return new CompletableFuture<Void>();
    }

    public void onError(WebSocket webSocket, Throwable error) {
        System.out.println("Error: " + error.getCause());
        System.out.println("Message: " + error.getLocalizedMessage());
        webSocket.abort();
    }
}