package section4;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class P06Combinators {

    public static void main(String[] args) {
        new P06Combinators().demo();
    }

    private void demo() {
        Calendar calendar = Calendar.getInstance();
        Date davidDate = calendar.getTime();
        calendar.add(Calendar.HOUR, -1);
        Date robertaDate = calendar.getTime();
        calendar.add(Calendar.HOUR, -1);
        Date jackDate = calendar.getTime();

        List<Blog> blogs = List.of(
                new Blog("Roberta", robertaDate,
                        "Explore the newest epad \n" +
                                "featuring epad Pro in two \n" +
                                "sizes, epad Air, epad, and epad \n" +
                                "mini. Visit the pear site to \n" +
                                "learn, buy, and get support."),
                new Blog("David", davidDate,
                        "Jamf Pro is comprehensive enterprise \n" +
                                "management software for the pear \n" +
                                "platform, simplifying IT management \n" +
                                "for ebook, epad, ephone and pear TV."),
                new Blog("Jack", jackDate,
                        "Compare features and technical \n" +
                                "specifications for all ephone \n" +
                                "models, including ephone X, \n" +
                                "ephone R and more.")
        );
        Function<List<Blog>, List<Blog>> shorten =
                list -> list.stream().map(b -> new Blog(b.author, b.date, b.content.lines().findFirst().get() + " ...")).collect(Collectors.toList());

        Function<List<Blog>, List<Blog>> sortByDate =
                list -> list.stream().sorted((a, b) -> b.date.compareTo(a.date)).collect(Collectors.toList());


        Function<List<Blog>, List<Page>> breakIntoPages =
                Page::breakIntoPages;

        Function<List<Blog>, List<Page>> collapse = shorten.andThen(sortByDate).andThen(breakIntoPages);
        Function<List<Blog>, List<Page>> expand = sortByDate.andThen(breakIntoPages);

        var collapsedPages = collapse.apply(blogs);
        var expandedPages = expand.apply(blogs);

        System.out.println("------ Collapsed pages ------");
        System.out.println();
        printPages(collapsedPages);
        System.out.println();
        System.out.println("------ Expanded pages ------");
        System.out.println();
        printPages(expandedPages);
    }

    private void printPages(List<Page> pages) {
        for (int i = 0; i < pages.size(); i++) {
            Page page =  pages.get(i);
            System.out.println("\nPage "+(i+1)+"\n");
            System.out.println(page);
        }
    }

}

@SuppressWarnings({"WeakerAccess", "PointlessBooleanExpression"})
class Page {
    final static long capacity = 10;
    long size;
    final List<Blog> blogs;

    static List<Page> breakIntoPages(List<Blog> blogs) {

        var pages = new ArrayList<Page>();
        pages.add(new Page());
        for (Blog blog : blogs) {
            if (false == pages.get(pages.size() - 1).add(blog)) {
                ArrayList<Blog> list = new ArrayList<>();
                list.add(blog);
                pages.add(new Page(list));
            }
        }

        return pages;
    }

    boolean add(Blog blog) {
        if (size + blog.content.lines().count() <= capacity) {
            size += blog.content.lines().count();
            blogs.add(blog);
            return true;
        } else {
            return false;
        }
    }

    private Page() {
        size = 0;
        blogs = new ArrayList<>();
    }

    Page(List<Blog> blogs) {
        this.blogs = blogs;
        size = blogs.stream().mapToLong(b -> b.content.lines().count()).sum();
    }

    @Override
    public String toString() {
        return "" + blogs.stream().map(Objects::toString).collect(Collectors.joining("\n"))
                + "\n";
    }
}

class Blog {
    final String author;
    final Date date;
    final String content;

    Blog(String author, Date date, String content) {
        this.author = author;
        this.date = date;
        this.content = content;
    }

    @Override
    public String toString() {
        return "\n" + content + "\n" + author.toUpperCase() + ", " + date;
    }
}

class ShortenedBlog extends Blog {

    ShortenedBlog(Blog b) {
        super(b.author, b.date, b.content.lines().findFirst().get() + " ...");
    }
}