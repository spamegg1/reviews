
import java.util.ArrayList;
import java.util.Scanner;

public class PersonalInformationCollection {

    public static void main(String[] args) {
        // implement here your program that uses the PersonalInformation class

        ArrayList<PersonalInformation> infoCollection = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String first = scanner.nextLine();
            if (first.equals("")) {
                break;
            }

            String last = scanner.nextLine();
            String id = scanner.nextLine();

            infoCollection.add(new PersonalInformation(first, last, id));
        }

        for (PersonalInformation p : infoCollection) {
            System.out.println(p.getFirstName() + " " + p.getLastName());
        }
    }
}
