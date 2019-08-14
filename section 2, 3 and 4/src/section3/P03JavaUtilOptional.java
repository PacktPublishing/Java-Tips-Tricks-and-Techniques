package section3;

import java.util.Optional;
import java.util.stream.Stream;

public class P03JavaUtilOptional {

    public static void main(String[] args) {
        new P03JavaUtilOptional().demo();

    }

    private void demo() {
        var nothing = Optional.empty();
        var something = Optional.of("something");
        var somethingElse = Optional.of("something else");

        nothing.ifPresent(x -> System.out.println("will not get called"));
        something.ifPresent(x -> System.out.println("got: " + x));

        nothing.ifPresentOrElse(x -> System.out.println("will not get called"), () -> System.out.println("Will run"));
        something.ifPresentOrElse(x -> System.out.println("Got again: " + x), () -> System.out.println("Will not run"));

        System.out.println("nothing.isEmpty() = " + nothing.isEmpty());
        System.out.println("something.isPresent() = " + something.isPresent());

        System.out.println("nothing.or(()-> somethingElse) = " + nothing.or(() -> somethingElse));
        System.out.println("nothing.orElseGet(56) = " + nothing.orElseGet(() -> 56));
        System.out.println("nothing.orElse(42) = " + nothing.orElse(42));

        System.out.println("something.or(()-> somethingElse) = " + something.or(() -> somethingElse));

        Stream<Object> emptyStream = nothing.stream();
        Stream<String> someStream = something.stream();

        System.out.println("emptyStream.count() = " + emptyStream.count());
        System.out.println("someStream.count() = " + someStream.count());

        var stillNothing = nothing.filter(x -> true);
        System.out.println("stillNothing = " + stillNothing);

        var a = something.filter(x -> true);
        var b = something.filter(x -> false);
        System.out.println("a = " + a);
        System.out.println("b = " + b);

        something.orElseThrow(() -> new RuntimeException("Will not happen"));
        nothing.orElseThrow(() -> new RuntimeException("Will happen"));
    }
}
