import java.util.ArrayList;

public class Room {
    private ArrayList<Person> persons;

    public Room() {
        persons = new ArrayList<>();
    }

    public void add(Person person) {
        persons.add(person);
    }

    public boolean isEmpty() {
        return persons.isEmpty();
    }

    public ArrayList<Person> getPersons() {
        return persons;
    }

    public Person shortest() {
        if (persons.isEmpty()) {
            return null;
        }

        Person shortestPerson = persons.get(0);
        int minHeight = shortestPerson.getHeight();

        for (Person p : persons) {
            if (p.getHeight() < minHeight) {
                minHeight = p.getHeight();
                shortestPerson = p;
            }
        }

        return shortestPerson;
    }

    public Person take() {
        Person p = shortest();
        persons.remove(p);
        return p;
    }
}
