import java.util.ArrayList;

public class Stack {
    private ArrayList<String> strings;

    public Stack() {
        strings = new ArrayList<>();
    }

    public boolean isEmpty() {
        return strings.isEmpty();
    }

    public void add(String value) {
        strings.add(value);
    }

    public ArrayList<String> values() {
        return strings;
    }

    public String take() {
        return strings.remove(strings.size() - 1);
    }
}
