import java.util.ArrayList;

public class SimpleCollection {

    private String name;
    private ArrayList<String> elements;

    public SimpleCollection(String name) {
        this.name = name;
        this.elements = new ArrayList<>();
    }

    public void add(String element) {
        this.elements.add(element);
    }

    public ArrayList<String> getElements() {
        return this.elements;
    }

    public String longest() {
        if (elements.isEmpty()) {
            return null;
        }

        int maxLength = 0;
        String longestString = "";

        for (String s : elements) {
            if (s.length() > maxLength) {
                maxLength = s.length();
                longestString = s;
            }
        }

        return longestString;
    }
}
