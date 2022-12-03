import java.util.ArrayList;

public class TodoList {
    private ArrayList<String> todoList;

    public TodoList() {
        todoList = new ArrayList<>();
    }

    public void add(String task) {
        todoList.add(task);
    }

    public void print() {
        for (int i = 0; i < todoList.size(); i++) {
            System.out.println((i + 1) + ": " + todoList.get(i));
        }
    }

    public void remove(int number) {
        todoList.remove(number - 1);
    }
}
