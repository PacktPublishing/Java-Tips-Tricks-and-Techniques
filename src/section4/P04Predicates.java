package section4;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@SuppressWarnings("WeakerAccess")
public class P04Predicates {

    public static void main(String[] args) {
        new P04Predicates().demo();
    }

    private void demo() {
        Db db = new Db(List.of(
                new WebPage("https://www.pear.shop/lae/epad/", "epad - pear", "Explore the " +
                        "newest epad. Featuring epad Pro in two sizes, epad Air, epad, and epad " +
                        "mini. Visit the pear site to learn, buy, and get support."),
                new WebPage("https://www.jam.shop/", "Jam: ebook, epad, ephone, and pear TV " +
                        "management", "Jamf Pro is comprehensive enterprise management software " +
                        "for the pear platform, simplifying IT management for ebook, epad, ephone" +
                        " and pear TV."),
                new WebPage("https://www.pear.shop/ca/ephone/compare/", "ephone - Compare Models " +
                        "- pear", "Compare features and technical specifications for all ephone " +
                        "models, including ephone X, ephone R and more.")
        ));
        var result = db.search(
                (contains("epad").or(contains("ephone")))
                        .and(contains("pear"))
                        .and(Predicate.not(urlContains("jam.shop")))
        );
        System.out.println("Search:\n(epad OR ephone) AND pear -site:jam.shop");
        System.out.println("\n\nResult:\n" + result);
    }

    Predicate<WebPage> contains(String s) {
        return textContains(s).or(titleContains(s));
    }

    Predicate<WebPage> urlContains(String s) {
        return wepPage -> wepPage.url.toLowerCase().contains(s.toLowerCase());
    }

    Predicate<WebPage> titleContains(String s) {
        return wepPage -> wepPage.title.toLowerCase().contains(s.toLowerCase());
    }

    Predicate<WebPage> textContains(String s) {
        return wepPage -> wepPage.text.toLowerCase().contains(s.toLowerCase());
    }
}

class Db {

    private final List<WebPage> webPages;

    Db(List<WebPage> db) {
        this.webPages = new ArrayList<>(db);
    }

    List<WebPage> search(Predicate<? super WebPage> predicate) {
        return webPages.stream()
                .filter(predicate)
                .collect(Collectors.toList());
    }
}

class WebPage {
    final String url;
    final String title;
    final String text;

    WebPage(String url, String title, String text) {
        this.url = url;
        this.title = title;
        this.text = text;
    }

    @Override
    public String toString() {
        return "\n" + title + "\n"
                + url + "\n"
                + text + "\n";
    }
}
