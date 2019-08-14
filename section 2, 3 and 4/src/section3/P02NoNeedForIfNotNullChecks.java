package section3;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class P02NoNeedForIfNotNullChecks {

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private Db db;

    public static void main(String[] args) throws ParseException {
        new P02NoNeedForIfNotNullChecks().demo();
    }

    private void demo() throws ParseException {
        createDemoDb();

        List<Costume> searchResult1 = db.search("Long jacket");
        Optional<Costume> joeSelected = searchResult1.stream().findFirst();
        System.out.println("joeSelected = " + joeSelected.map(Costume::getDescription).orElse("nothing"));

        List<Costume> searchResult2 = db.search(
                "Long jacket",
                dateFormat.parse("2019-03-18"), // Jane needs the costume from this date
                dateFormat.parse("2019-03-24")  // to this date
        );
        Optional<Costume> janeSelected = searchResult2.stream().findFirst();
        System.out.println("janeSelected = " + janeSelected.map(Costume::getDescription).orElse("nothing"));
    }

    private void createDemoDb() throws ParseException {
        DateRange reserved = new DateRange(
                dateFormat.parse("2019-03-20"),
                dateFormat.parse("2019-03-22")
        );

        db = new Db(List.of(
                new Costume("Snow White yellow skirt white blouse", null),
                new Costume("Short skirt costume with long jacket", reserved),
                new Costume("Knight with helmet and sword ", null),
                new Costume("Blue Jeans with long jacket", null)
        ));
    }

    static class Db {

        private final List<Costume> costumes;

        Db(List<Costume> db) {
            this.costumes = new ArrayList<>(db);
        }

        List<Costume> search(String term, Date from, Date to) {
            DateRange range = new DateRange(from, to);
            var result = new ArrayList<Costume>();
            for (var costume : search(term)) {
                if (DateRange.notOverlap(range, costume.getReserved()))
                    result.add(costume);
            }
            return result;
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
        private final DateRange reserved;

        Costume(String description, DateRange reserved) {
            this.description = description;
            this.reserved = reserved;
        }

        String getDescription() {
            return description;
        }

        DateRange getReserved() {
            return reserved;
        }
    }

    static class DateRange {
        final Date from;
        final Date to;

        DateRange(Date from, Date to) {
            this.from = from;
            this.to = to;
        }

        static boolean notOverlap(DateRange range1, DateRange range2) {
            return !overlap(range1, range2);
        }

        static boolean overlap(DateRange range1, DateRange range2) {
            return Optional.ofNullable(range2).flatMap(r ->
                    Optional.ofNullable(r.from).flatMap(rf ->
                            Optional.ofNullable(r.to).map(rt ->
                                    contains(range1, rf) || contains(range1, rt)
                            )
                    )
            ).orElse(false);
        }

        static boolean contains(DateRange range, Date date) {
            return Optional.ofNullable(range).flatMap(r ->
                    Optional.ofNullable(r.to).flatMap(t ->
                            Optional.ofNullable(r.from).map(f ->
                                    date.compareTo(t) <= 0 && f.compareTo(date) <= 0
                            )
                    )
            ).orElse(false);
        }
    }
}
