package java12.section5.present;

import java12.section5.count.result.WordCountResultNotParallel;
import java12.section5.count.result.WordCountResultParallel;
import java12.section5.count.result.WordCountResultThreadPool;
import java12.section5.download.TextDownload;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainParallel {

    private static long start = -1;
    private static String startedActivity = "";

    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException, ExecutionException {
        time("TextDownload");
        var uri = new URI("https://raw.githubusercontent.com/manum/mr-cassandra/master/mr_cassandra/all-shakespeare.txt");
        Path file = new TextDownload().download(uri);
        List<String> lines = Files.readAllLines(file);
        int repeat;
        if (args.length > 0)
            repeat = Integer.valueOf(args[0]);
        else
            repeat = 1;
        for (int i = 0; i < repeat; i++) {
            lines.addAll(lines);
        }
        int limit = 500 * (1 + repeat);

        time("Warm up WordCountResultThreadPool");
        new WordCountResultThreadPool().count(lines, limit);
        time("Warm up WordCountResultThreadPool");
        new WordCountResultThreadPool().count(lines, limit);
        time("Warm up WordCountResultThreadPool");
        new WordCountResultThreadPool().count(lines, limit);
        time("WordCountResultThreadPool");
        List<String> resultPool = new WordCountResultThreadPool().count(lines, limit);

        time("Warm up WordCountResultParallel");
        System.out.println();
        new WordCountResultParallel().count(lines, limit);
        time("Warm up WordCountResultParallel");
        new WordCountResultParallel().count(lines, limit);
        time("Warm up WordCountResultParallel");
        new WordCountResultParallel().count(lines, limit);
        time("WordCountResultParallel");
        List<String> resultPar = new WordCountResultParallel().count(lines, limit);

        time("Warm up WordCountResultNotParallel");
        System.out.println();
        new WordCountResultNotParallel().count(lines, limit);
        time("Warm up WordCountResultNotParallel");
        new WordCountResultNotParallel().count(lines, limit);
        time("Warm up WordCountResultNotParallel");
        new WordCountResultNotParallel().count(lines, limit);
        time("WordCountResultNotParallel");
        List<String> result = new WordCountResultNotParallel().count(lines, limit);
        time("");
        System.out.println();

        System.out.println("The words below can be found more than " + 500 + " times ");
        System.out.println("in The Complete Works of William Shakespeare");
        System.out.println();
        System.out.print("WordCountResult:           ");
        System.out.println(result);
        System.out.print("WordCountResultParallel:   ");
        System.out.println(resultPar);
        System.out.print("WordCountResultThreadPool: ");
        System.out.println(resultPool);
    }

    private static void time(String activity) {
        if (start < 0) {
            System.out.println(activity + " started");
            start = System.currentTimeMillis();
            startedActivity = activity;
        } else {
            long end = System.currentTimeMillis();
            System.out.println(
                    startedActivity + " ended in " + (end - start) + " milliseconds"
            );
            start = end;
            startedActivity = activity;
        }
    }
}
