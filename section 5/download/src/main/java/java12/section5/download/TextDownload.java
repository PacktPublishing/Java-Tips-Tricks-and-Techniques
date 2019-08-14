package java12.section5.download;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Path;

public class TextDownload {

    public Path download(URI uri) throws IOException, InterruptedException {
        Path tempFile = File.createTempFile("TextDownloadFile", ".txt").toPath();
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder().uri(uri).build();
        client.send(request, responseInfo -> {
            HttpResponse.BodyHandler<Path> handler = HttpResponse.BodyHandlers.ofFile(tempFile);
            return handler.apply(responseInfo);
        });
        return tempFile;
    }
}
