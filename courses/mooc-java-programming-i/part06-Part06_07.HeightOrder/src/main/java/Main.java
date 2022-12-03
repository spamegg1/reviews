
public class Main {

    public static void main(String[] args) {
        // Test your class here
        Room room = new Room();
        System.out.println("Empty room? " + room.isEmpty());
        room.add(new Person("Lea", 183));
        room.add(new Person("Kenya", 182));
        room.add(new Person("Auli", 186));
        room.add(new Person("Nina", 172));
        room.add(new Person("Terhi", 185));
        System.out.println("Empty room? " + room.isEmpty());

        System.out.println("");
        for (Person person : room.getPersons()) {
            System.out.println(person);
        }

        Room room2 = new Room();
        System.out.println("Shortest: " + room2.shortest());
        System.out.println("Empty room? " + room2.isEmpty());
        room2.add(new Person("Lea", 183));
        room2.add(new Person("Kenya", 182));
        room2.add(new Person("Auli", 186));
        room2.add(new Person("Nina", 172));
        room2.add(new Person("Terhi", 185));
        System.out.println("Empty room? " + room2.isEmpty());

        System.out.println("");
        for (Person person : room2.getPersons()) {
            System.out.println(person);
        }

        System.out.println();
        System.out.println("Shortest: " + room2.shortest());
        System.out.println("");
        for (Person person : room2.getPersons()) {
            System.out.println(person);
        }


        Room room3 = new Room();
        room3.add(new Person("Lea", 183));
        room3.add(new Person("Kenya", 182));
        room3.add(new Person("Auli", 186));
        room3.add(new Person("Nina", 172));
        room3.add(new Person("Terhi", 185));

        System.out.println("");
        for (Person person : room3.getPersons()) {
            System.out.println(person);
        }

        System.out.println();
        System.out.println("Shortest: " + room3.take());
        System.out.println("");
        for (Person person : room3.getPersons()) {
            System.out.println(person);
        }
    }
}
