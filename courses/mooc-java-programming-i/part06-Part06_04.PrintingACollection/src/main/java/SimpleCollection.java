
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

    @Override
    public String toString() {
        int count = elements.size();

        if (count == 0) {
            return "The collection " + name + " is empty.";
        }

        if (count == 1) {
            return "The collection " + name + " has 1 element:\n" + elements.get(0);
        }

        String result = "";
        result += "The collection " + name + " has " + count + " elements:";
        for (String element : elements) {
            result += "\n" + element;
        }

        return result;
    }

}
