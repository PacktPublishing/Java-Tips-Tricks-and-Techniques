package section4;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class P02DefectiveLoops {
    public static void main(String[] args) {
        new P02DefectiveLoops().demo();
    }

    private void demo() {
        int n = 4;
        int factorial = 1;
        while (n > 1) {
            factorial *= n;
            --n;
        }
        System.out.println("factorial = " + factorial);
        System.out.println();

        System.out.println("Fibonacci until 30: ");
        int a = 0;
        int b = 1;
        while (b < 30) {
            int fibonacci = a + b;
            a = b;
            b = fibonacci;
            System.out.println(a);
        }
        System.out.println();

        var people = List.of(
                new Person("s1", "c1", "Joe"),
                new Person("s1", "c2", "Jane"),
                new Person("s2", "c1", "Jenny")
        );
        var peopleByStateAndCity = new HashMap<String, Map<String, List<Person>>>();
        for (Person person : people) {
            peopleByStateAndCity
                    .computeIfAbsent(person.getState(), state -> new HashMap<>())
                    .computeIfAbsent(person.getCity(), city -> new ArrayList<>())
                    .add(person);
        }
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
