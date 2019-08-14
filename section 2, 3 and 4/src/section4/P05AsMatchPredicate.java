package section4;

import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class P05AsMatchPredicate {
    public static void main(String[] args) {
        new P05AsMatchPredicate().demo();
    }

    private void demo() {

        System.err.println("asPredicate: with found string 'cat'");

        Predicate<String> stringPredicate = Pattern.compile("cat").asPredicate();
        Stream.of("cat", "dog", "Felis catus").filter(stringPredicate).forEach(System.out::println);

        System.err.println("asMatchPredicate: matches string 'cat'");

        Predicate<String> stringMatchPredicate = Pattern.compile("cat").asMatchPredicate();
        Stream.of("cat", "dog", "Felis catus").filter(stringMatchPredicate).forEach(System.out::println);
    }
}
