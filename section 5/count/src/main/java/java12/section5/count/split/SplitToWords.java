package java12.section5.count.split;

import java.util.Arrays;
import java.util.stream.Stream;

public class SplitToWords {

    public Stream<String> split(Stream<String> lines) {
        return lines
                .flatMap(line -> Arrays.stream(line.split(" "))
                        .map(
                                w -> w.trim()
                                        .replace(",", "")
                                        .replace(".", "")
                        )
                );
    }
}
