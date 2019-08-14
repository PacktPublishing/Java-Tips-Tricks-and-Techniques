package section2;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Path;

public class P05HttpClient {

    public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException {
        Path destinationPath;
        destinationPath = new File("out", "shakespeare.txt").toPath();
        URI sourceLocation = new URI("https://raw.githubusercontent.com/manum/mr-cassandra/master/mr_cassandra/all-shakespeare.txt");

        new P05HttpClient().downloadFile(sourceLocation, destinationPath);
    }

    private void downloadFile(URI sourceLocation, Path destinationPath) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder().uri(sourceLocation).build();

        client.send(request, responseInfo -> {
            HttpResponse.BodyHandler<Path> handler = HttpResponse.BodyHandlers.ofFile(destinationPath);
            return handler.apply(responseInfo);
        });
    }
}
