
import java.util.Scanner;

import javax.xml.crypto.Data;

public class mainProgram {

    public static void main(String[] args) {
        // NB! Do not create other scanner objects than the one below
        // if and when you create other classes, pass the scanner to them
        // as a parameter

        Scanner scanner = new Scanner(System.in);
        Database database = new Database();

        while (true) {
            System.out.print("? ");
            String command = scanner.nextLine();

            if (command.equals("Quit")) {
                break;
            }

            if (command.equals("Add")) {
                System.out.print("Name: ");
                String name = scanner.nextLine();

                System.out.print("Name in Latin: ");
                String latinName = scanner.nextLine();

                database.add(new Bird(name, latinName));
            }

            if (command.equals("Observation")) {
                System.out.print("Bird? ");
                String name = scanner.nextLine();
                database.observeBird(name);
            }

            if (command.equals("All")) {
                System.out.println(database.all());
            }

            if (command.equals("One")) {
                System.out.print("Bird? ");
                String name = scanner.nextLine();
                database.one(name);
            }
        }
    }

}
