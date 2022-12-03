
import java.util.Scanner;

public class Cubes {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String word = scanner.nextLine();
            if (word.equals("end")) {
                break;
            }

            int num = Integer.valueOf(word);
            System.out.println(num * num * num);
        }
    }
}
