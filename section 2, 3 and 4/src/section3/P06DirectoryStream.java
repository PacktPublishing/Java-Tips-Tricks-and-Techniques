package section3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class P06DirectoryStream {

    public static void main(String[] args) throws IOException {
        new P06DirectoryStream().demoWalk("section");
    }

    private void demoWalk(String what) throws IOException {
        String project = System.getProperty("user.dir");
        Path src = Path.of(project, "src");
        try (Stream<Path> directoryStream = Files.walk(src, 6)) {
            directoryStream.filter(this::isFile).flatMap(this::lines).filter(new Contains(what)::happens).forEach(System.out::println);
        }
    }

    private boolean isFile(Path path) {
        return path.toFile().isFile();
    }

    private Stream<String> lines(Path path) {
        try {
            return Files.lines(path);
        } catch (IOException e) {
            e.printStackTrace();
            return Stream.empty();
        }
    }

    class Contains {
        final String what;

        Contains(String what) {
            this.what = what;
        }

        boolean happens(String line) {
            return line.toLowerCase().contains(what.toLowerCase());
        }
    }
}
