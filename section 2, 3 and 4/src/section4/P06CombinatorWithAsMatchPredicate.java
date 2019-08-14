package section4;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import static section4.Parser.par;
import static section4.Parser.parse;

public class P06CombinatorWithAsMatchPredicate {

    public static void main(String[] args) throws IOException {
        new P06CombinatorWithAsMatchPredicate().demo();
    }

    @SuppressWarnings("UnnecessaryLocalVariable")
    private void demo() throws IOException {

        var numbers = new Parser("\\d+");
        var intNumber = numbers;

        System.out.println("1 intNumber = " + parse(intNumber, "1"));
        System.out.println("4.6 intNumber = " + parse(intNumber, "4.6"));

        var floatNumber =
                numbers.and(par("\\.")).and(numbers);

        var eFloatNumber =
                floatNumber.and(par("[eE]")).and(numbers);

        System.out.println("1.2 floatNumber = " + parse(floatNumber, "1.2"));
        System.out.println("5.13E14 eFloatNumber = " + parse(eFloatNumber, "5.13E14"));
        System.out.println("5.13F14 eFloatNumber = " + parse(eFloatNumber, "5.13F14"));
    }
}

class Parser implements Predicate<Reader> {

    private static final int EOS = -1; // End Of Stream
    private final Predicate<String> predicate;

    private char[] buffer = new char[11];

    Parser(String regex) {
        predicate = Pattern.compile(regex).asMatchPredicate();
    }

    static Predicate<Reader> par(String regex) {
        return new Parser(regex);
    }

    static boolean parse(Predicate<Reader> p, String s) throws IOException {
        StringReader reader = new StringReader(s);
        return p.test(reader) && reader.read() == EOS;
    }

    public boolean test(Reader reader) {
        try {
            var found = false;
            reader.mark(10);
            for (int i = 0; i < 10; i++) {
                int read = reader.read(buffer, i, 1);
                String s = new String(buffer, 0, i + 1);
                if (read == 1 && predicate.test(s)) {
                    reader.mark(10);
                    found = true;
                } else {
                    reader.reset();
                }
            }
            return found;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}