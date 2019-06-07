package section3;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.function.BiPredicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class P05JavaStreamAndFilesFind {

    public static void main(String[] args) {
        var p05JavaStreamAndFilesFind = new P05JavaStreamAndFilesFind();
        p05JavaStreamAndFilesFind.demoStreams();
        p05JavaStreamAndFilesFind.demoFilesFind();
    }

    private void demoStreams() {
        var words = Stream.of("a", "an", "before", "being", "cannot", "death", "do", "first", "great");
        words.filter(x -> x.length() > 4).forEach(System.out::println);

        Stream.generate(new Supplier<Integer>() {

            Integer state = 1;

            @Override
            public Integer get() {
                Integer value = state;
                state += 1;
                return value;
            }
        }).limit(10).forEach(System.out::println);
    }

    private void demoFilesFind() {
        var currentDir = Path.of(System.getProperty("user.dir"));
        try {
            Files.find(currentDir, 6, new BiPredicate<Path, BasicFileAttributes>() {
                @Override
                public boolean test(Path path, BasicFileAttributes basicFileAttributes) {
                    return path.toString().contains("$"); // search for inner classes and lambdas
                }
            }).map(Path::getFileName).forEach(System.out::println);
        } catch (Throwable e) {
            System.err.println(e);
        }
    }
}
