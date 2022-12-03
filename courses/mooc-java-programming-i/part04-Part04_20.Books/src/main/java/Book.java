public class Book {
    private String title;
    private int pages;
    private int year;

    public Book(String title, int pages, int year) {
        this.title = title;
        this.pages = pages;
        this.year = year;
    }

    public String name() {
        return title;
    }

    @Override
    public String toString() {
        return title + ", " + pages + " pages, " + year;
    }
}
