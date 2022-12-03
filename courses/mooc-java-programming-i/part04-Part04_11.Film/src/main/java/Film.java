public class Film {
    private String name;
    private int ageRating;

    public Film(String filmName, int filmAgeRating) {
        name = filmName;
        ageRating = filmAgeRating;
    }

    public String name() {
        return name;
    }

    public int ageRating() {
        return ageRating;
    }

}
