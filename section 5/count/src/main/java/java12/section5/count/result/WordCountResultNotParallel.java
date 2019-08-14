package java12.section5.count.result;

import java12.section5.count.split.SplitToWords;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class WordCountResultNotParallel {

    private BiFunction<Map<String, Integer>, String, Map<String, Integer>> acc = (Map<String, Integer> map, String word) -> {
        String key = word.toLowerCase();
        var count = map.get(key);
        map.put(key, count == null ? 0 : count + 1);
        return map;
    };
    private BinaryOperator<Map<String, Integer>> combiner = (a, b) -> {
        a.putAll(b);
        return a;
    };

    @SuppressWarnings("UnnecessaryLocalVariable")
    public List<String> count(List<String> lines, int limit) throws IOException {
        Stream<String> words = new SplitToWords().split(lines.stream());
        Map<String, Integer> m = words.reduce(new HashMap<>(), acc, combiner);
        Stream<String> result = m.entrySet().stream()
                .filter((e) -> e.getValue() > limit && e.getKey().length() > 4 && !e.getKey().startsWith("["))
                .map(Map.Entry::getKey).sorted(String::compareToIgnoreCase);
        return result.collect(Collectors.toList());
    }
}
