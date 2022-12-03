
public class Main {
    public static void main(String[] args) {
        // You can use the main to test your classes!
        Item book = new Item("The lord of the rings", 2);
        Item phone = new Item("Nokia 3210", 1);

        System.out.println("The book's name: " + book.getName());
        System.out.println("The book's weight: " + book.getWeight());

        System.out.println("Book: " + book);
        System.out.println("Phone: " + phone);


        Item brick = new Item("brick", 4);

        Suitcase suitcase2 = new Suitcase(5);
        System.out.println(suitcase2);

        suitcase2.addItem(book);
        System.out.println(suitcase2);

        suitcase2.addItem(phone);
        System.out.println(suitcase2);

        suitcase2.addItem(brick);
        System.out.println(suitcase2);


        Suitcase suitcase3 = new Suitcase(10);
        suitcase3.addItem(book);
        suitcase3.addItem(phone);
        suitcase3.addItem(brick);

        System.out.println("The suitcase contains the following items:");
        suitcase3.printItems();
        System.out.println("Total weight: " + suitcase3.totalWeight() + " kg");


        Suitcase suitcase4 = new Suitcase(10);
        suitcase4.addItem(book);
        suitcase4.addItem(phone);
        suitcase4.addItem(brick);

        Item heaviest = suitcase4.heaviestItem();
        System.out.println("Heaviest item: " + heaviest);


        Suitcase adasCase = new Suitcase(10);
        adasCase.addItem(book);
        adasCase.addItem(phone);

        Suitcase pekkasCase = new Suitcase(10);
        pekkasCase.addItem(brick);

        Hold hold = new Hold(1000);
        hold.addSuitcase(adasCase);
        hold.addSuitcase(pekkasCase);

        System.out.println(hold);



        Hold hold2 = new Hold(1000);
        hold2.addSuitcase(adasCase);
        hold2.addSuitcase(pekkasCase);

        System.out.println("The suitcases in the hold contain the following items:");
        hold2.printItems();
    }
}
