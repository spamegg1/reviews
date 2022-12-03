
import java.util.ArrayList;
import java.util.Scanner;

public class PersonalDetails {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String longestName = "";
        int longest = 0;
        double total = 0.0;
        double count = 0.0;

        while (true) {
            String nameYear = scanner.nextLine();
            if (nameYear.equals("")) {
                break;
            }

            String[] nameAndYear = nameYear.split(",");
            String name = nameAndYear[0];
            int year = Integer.valueOf(nameAndYear[1]);
            int nameLength = name.length();

            total += year * 1.0;
            count += 1.0;

            if (nameLength > longest) {
                longest = nameLength;
                longestName = name;
            }
        }
        System.out.println("Longest name: " + longestName);
        System.out.println("Average of the birth years: " + (total / count));
    }
}
