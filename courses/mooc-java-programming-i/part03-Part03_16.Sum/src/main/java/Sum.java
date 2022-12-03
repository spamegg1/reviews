
import java.util.ArrayList;

public class Sum {

    public static void main(String[] args) {
        // Try your method here
    }

    public static int sum(ArrayList<Integer> numbers) {
        int total = 0;
        for (int number : numbers) {
            total += number;
        }
        return total;
    }
}
