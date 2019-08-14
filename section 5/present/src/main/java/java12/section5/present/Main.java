package java12.section5.present;

import java12.section5.count.result.WordCountResult;
import java12.section5.download.TextDownload;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.List;

public class Main {

    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {
        var uri = new URI("https://raw.githubusercontent.com/manum/mr-cassandra/master/mr_cassandra/all-shakespeare.txt");
        Path file = new TextDownload().download(uri);
        int limit = 500;
        List<String> result = new WordCountResult().count(file, limit);
        System.out.println();
        System.out.println("The words below can be found more than " + limit + " times ");
        System.out.println("in The Complete Works of William Shakespeare");
        System.out.println();
        System.out.println(result);
    }
}
