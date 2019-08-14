package section4;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingBy;

public class P03PredefinedLoops {
    public static void main(String[] args) {
        new P03PredefinedLoops().demo();
    }

    private void demo() {
        int n = 4;
        int factorial = IntStream.rangeClosed(1, n).reduce(1, (fact, x) -> fact * x);
        System.out.println("factorial = " + factorial);
        System.out.println();

        Stream<Integer> fibonacci = Stream.generate(new Supplier<>() {
            int a = 0;
            int b = 1;

            @Override
            public Integer get() {
                int fib = a + b;
                a = b;
                b = fib;
                return a;
            }
        });
        Stream<Integer> until30 = fibonacci.takeWhile(x -> x < 30);
        System.out.println("Fibonacci until 30: ");
        until30.forEach(System.out::println);
        System.out.println();

        var personStream = Stream.of(
                new Person("s1", "c1", "Joe"),
                new Person("s1", "c2", "Jane"),
                new Person("s2", "c1", "Jenny")
        );
        Map<String, Map<String, List<Person>>> peopleByStateAndCity =
                personStream.collect(groupingBy(Person::getState, groupingBy(Person::getCity)));
        System.out.println("peopleByStateAndCity = " + peopleByStateAndCity);
    }

    static class Person {
        private final String state;
        private final String city;
        private final String name;

        Person(String state, String city, String name) {
            this.state = state;
            this.city = city;
            this.name = name;
        }

        public String getState() {
            return state;
        }

        public String getCity() {
            return city;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "" + state +
                    "," + city +
                    "," + name +
                    '}';
        }
    }
}
