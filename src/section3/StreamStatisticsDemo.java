package section3;

import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.NoSuchElementException;


public class StreamStatisticsDemo {

    private static class Kid {
        private int age;

        private Kid(String name, int age) {
            this.age = age;
        }

        private int getAge() {
            return age;
        }
    }

    public static void main(String... args) {

        List<Kid> list = Arrays.asList(
                new Kid("Little Richard", 8),
                new Kid("Elvis King", 8),
                new Kid("Billy Bob", 7),
                new Kid("John Doe", 7),
                new Kid("Kevin Smith", 8),
                new Kid("Dennis Mitchell", 6)
        );

        // own implementation:

        int min = list.stream()
                .mapToInt(Kid::getAge)
                .min()
                .orElseThrow(NoSuchElementException::new);

        int max = list.stream()
                .mapToInt(Kid::getAge)
                .max()
                .orElseThrow(NoSuchElementException::new);

        System.out.println("Using our own implementation:" +
                " Youngest: " + min + " Eldest: " + max);

        //summaryStatistics
        IntSummaryStatistics statistics = list.stream()
                .mapToInt(Kid::getAge)
                .summaryStatistics();

        //min and max
        int minSummary = statistics.getMin();
        int maxSummary = statistics.getMax();

        //other statistics
        double avgSummary = statistics.getAverage();
        long countSummary = statistics.getCount();
        long sumSummary = statistics.getSum();

        System.out.println("Using IntSummaryStatistics:" +
                " Youngest: " + minSummary + " Eldest: " + maxSummary);

        System.out.println("Other Statistics:" +
                " Average age: " + avgSummary +
                " Number of classmates: " + countSummary +
                " Sum of ages: " + sumSummary);
    }
}
