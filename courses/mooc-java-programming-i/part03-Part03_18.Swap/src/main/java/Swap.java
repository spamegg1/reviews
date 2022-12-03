
import java.util.Scanner;

public class Swap {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[] array = new int[5];
        array[0] = 1;
        array[1] = 3;
        array[2] = 5;
        array[3] = 7;
        array[4] = 9;

        int index = 0;
        while (index < array.length) {
            System.out.println(array[index]);
            index++;
        }
        System.out.println("");

        // Implement here
        // asking for the two indices
        // and then swapping them
        System.out.println("Give two indices to swap:");
        int index1 = Integer.valueOf(scanner.nextLine());
        int index2 = Integer.valueOf(scanner.nextLine());

        int helper = array[index1];
        array[index1] = array[index2];
        array[index2] = helper;

        System.out.println("");
        index = 0;
        while (index < array.length) {
            System.out.println(array[index]);
            index++;
        }
    }

}
