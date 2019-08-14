package section3;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class P01AvoidNullPointerExceptions {

    private Db db;

    public static void main(String[] args) {
        new P01AvoidNullPointerExceptions().demo();
    }

    private void demo() {
        createDemoDb();
        avoidNullPointerExceptions();
    }

    private void avoidNullPointerExceptions() {
        Optional<Costume> joeSelected = select("Long jacket");
        System.out.println("joeSelected = " + joeSelected.map(Costume::getDescription).orElse("nothing"));

        Optional<Costume> janeSelected = select("Long costume");
        System.out.println("janeSelected = " + janeSelected.map(Costume::getDescription).orElse("nothing"));
    }

    private Optional<Costume> select(String subString) {
        List<Costume> searchResult = db.search(subString);
        return searchResult.stream().findFirst();
    }

    private void createDemoDb() {
        db = new Db(List.of(
                new Costume("Snow White yellow skirt white blouse"),
                new Costume("Short skirt costume with long jacket"),
                new Costume("Knight with helmet and sword "),
                new Costume("Blue Jeans with long jacket")
        ));
    }

    static class Db {

        private final List<Costume> costumes;

        Db(List<Costume> db) {
            this.costumes = new ArrayList<>(db);
        }

        List<Costume> search(String term) {
            var result = new ArrayList<Costume>();
            for (var costume : costumes) {
                if (costume.getDescription().toLowerCase().contains(term.toLowerCase()))
                    result.add(costume);
            }
            return result;
        }
    }

    static class Costume {
        private final String description;

        Costume(String description) {
            this.description = description;
        }

        String getDescription() {
            return description;
        }
    }
}
