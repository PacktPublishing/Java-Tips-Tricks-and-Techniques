package section2;

import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.function.Consumer;

public class P02LocalVarSyntaxForLambdaParameters {

    public static void main(String[] args) {
        var url = "https://raw.githubusercontent.com/manum/mr-cassandra/master/mr_cassandra/all-shakespeare.txt";

        callWordCountService(url, (@NotNull var map) -> {

            System.out.println("The result is = " + map);
        });
    }

    // Please see the real Word Count Service implementation in Section 5 of this video course
    private static void callWordCountService(String url, Consumer<Map<String, Integer>> callback) {
        callback.accept(
                Map.of("death", 1305, "great", 782, "queen", 726)
        );
    }
}
