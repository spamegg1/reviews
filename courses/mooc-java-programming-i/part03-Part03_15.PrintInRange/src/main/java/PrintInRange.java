
import java.util.ArrayList;

public class PrintInRange {

    public static void main(String[] args) {
        // Try your method here
    }
    public static void printNumbersInRange(
        ArrayList<Integer> numbers,
        int lowerLimit,
        int upperLimit
    ) {
        System.out.println(
            "The numbers in the range [" + lowerLimit + ", " + upperLimit + "]"
        );
        for (int number : numbers) {
            if (lowerLimit <= number && number <= upperLimit) {
                System.out.println(number);
            }
        }
    }
}
