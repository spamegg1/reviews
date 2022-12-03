import java.util.Scanner;

public class Main {

    public static String repeat(String str, int count) {
        String result = "";
        for (int i = 0; i < count; i++) {
            result += str;
        }
        return result;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        double total = 0.0;
        double count = 0.0;
        double passing = 0.0;
        double passingCount = 0.0;
        int zeros = 0;
        int ones = 0;
        int twos = 0;
        int threes = 0;
        int fours = 0;
        int fives = 0;

        System.out.println("Enter point totals, -1 stops:");

        // Write your program here -- consider breaking the program into
        // multiple classes.
        while (true) {
            int number = Integer.valueOf(scanner.nextLine());
            if (number == -1) {
                break;
            }
            if (number >= 0 && number <= 100) {
                if (number < 50) {
                    zeros++;
                } else if (number < 60){
                    ones++;
                } else if (number < 70){
                    twos++;
                } else if (number < 80){
                    threes++;
                } else if (number < 90){
                    fours++;
                } else {
                    fives++;
                }
                if (number >= 50) {
                    passing += number;
                    passingCount++;
                }
                total += number;
                count++;
            }
        }

        if (count == 0) {
            System.out.println("Point average (all): 0.0");
        } else {
            double average = total / count;
            System.out.println("Point average (all): " + average);

            double passingAverage = passing / passingCount;
            System.out.println("Point average (passing): " + passingAverage);

            double passPercentage = 100 * passingCount / count;
            System.out.println("Pass percentage: " + passPercentage);

            System.out.println("5: " + repeat("*", fives));
            System.out.println("4: " + repeat("*", fours));
            System.out.println("3: " + repeat("*", threes));
            System.out.println("2: " + repeat("*", twos));
            System.out.println("1: " + repeat("*", ones));
            System.out.println("0: " + repeat("*", zeros));
        }
    }
}
