package section4;

import java.util.ArrayList;
import java.util.List;

public class P01CopyOf {
    public static void main(String[] args) {
        new P01CopyOf().demo();
    }

    private void demo() {
        ArrayList<String> fields = new ArrayList<>() {{
            add("Author");
            add("Subject");
            add("Content");
        }};
        ArrayList<String> criteria = new ArrayList<>() {{
            add("haoyi");
            add("mill");
        }};
        // var found = findFirst(fields, criteria);
        var found = findFirst(List.copyOf(fields), criteria);
        for (int i = 0; i < found.size(); ++i) {
            System.out.println(fields.get(i) + ": " + found.get(i));
        }
    }

    // Suppose this method is implemented inside a jar file,
    // and we do not have it's source code.
    private List<String> findFirst(List<String> fields, List<String> criteria) {
        int used = criteria.size();
        while (fields.size() > used) {
            fields.remove(fields.size() - 1);
        }
        // iterate fields and criteria parallel than
        return List.of(
                "Lee Haoyi",
                "Mill - Better Scala Builds",
                "Mill is a new build tool for Scala: it compiles your ..."
        );
    }
}
