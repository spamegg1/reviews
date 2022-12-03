
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.*;
import static org.junit.Assert.*;

public class HeightOrderTest {

    @Test
    @Points("06-07.1")
    public void ClassRoomExists() throws Throwable {
        Reflex.reflect("Room").requirePublic();
        Reflex.reflect("Room").ctor().takingNoParams().requirePublic();
    }

    @Test
    @Points("06-07.1")
    public void roomHasArrayListObjectVariable() throws Throwable {
        Reflex.reflect("Room").requirePublic();
        Class clazz = Reflex.reflect("Room").cls();

        assertEquals("The Room class must only have one object variable. Now there were: " + clazz.getDeclaredFields().length, 1, clazz.getDeclaredFields().length);
        
        Field f = clazz.getDeclaredFields()[0];
        assertEquals("The Room class must have an ArrayList type object as an object variable. Now the type of the object variable was: ", ArrayList.class, f.getType());
    }

    @Test
    @Points("06-07.1")
    public void roomHasMethodAdd() throws Throwable {
        Reflex.reflect("Room").method("add").returningVoid().taking(Person.class).requirePublic();
    }

    @Test
    @Points("06-07.1")
    public void roomHasMethodIsEmpty() throws Throwable {
        Reflex.reflect("Room").method("isEmpty").returning(boolean.class).takingNoParams().requirePublic();
    }

    @Test
    @Points("06-07.1")
    public void roomHasMethodGetPersons() throws Throwable {
        Reflex.reflect("Room").method("getPersons").returning(ArrayList.class).takingNoParams().requirePublic();
    }

    @Test
    @Points("06-07.1")
    public void test1() throws Throwable {
        Object room = Reflex.reflect("Room").ctor().takingNoParams().invoke();
        assertTrue("The newly created room should be empty. Test the code:\nRoom r = new Room();\nSystem.out.println(r.isEmpty());", Reflex.reflect("Room").method("isEmpty").returning(boolean.class).takingNoParams().invokeOn(room));
        Person person = new Person("ada", 168);
        try {
            Reflex.reflect("Room").method("add").returningVoid().taking(Person.class).invokeOn(room, person);
        } catch (Throwable t) {
            fail("An error occurred when adding a person to the room. Test the code:\nRoom r = new Room();\nSystem.out.println(r.isEmpty());\nPerson person = new Person(\"ada\", 168);\nr.add(person);");
        }
        assertFalse("When one person has been added to the room, it should not be empty.Test the code:\nRoom r = new Room();\nSystem.out.println(r.isEmpty());\nPerson person = new Person(\"ada\", 168);\nr.add(person);\nSystem.out.println(r.isEmpty());", Reflex.reflect("Room").method("isEmpty").returning(boolean.class).takingNoParams().invokeOn(room));

        ArrayList<Person> persons = null;
        try {
            persons = Reflex.reflect("Room").method("getPersons").returning(ArrayList.class).takingNoParams().invokeOn(room);
        } catch (Throwable t) {
            fail("An error occurred when the getPersons method was called. Test the code:\nRoom r = new Room();\nSystem.out.println(r.isEmpty());\nPerson person = new Person(\"ada\", 168);\nr.add(person);\nSystem.out.println(r.isEmpty());\nArrayList<Person> persons = r.getPersons();");
        }

        assertNotNull("The reference returned by the getPersons method should never be null. Test the code:\nRoom r = new Room();\nSystem.out.println(r.isEmpty());\nPerson person = new Person(\"ada\", 168);\nr.add(person);\nSystem.out.println(r.isEmpty());\nArrayList<Person> persons = r.getPersons();", persons);
        assertEquals("After adding a person to the room, the list returned by getPersons should contain one person. Test the code:\nRoom r = new Room();\nSystem.out.println(r.isEmpty());\nPerson person = new Person(\"ada\", 168);\nr.add(person);\nSystem.out.println(r.isEmpty());\nArrayList<Person> persons = r.getPersons();\nSystem.out.println(persons);", 1, persons.size());

        Person fromList = persons.get(0);
        assertEquals("The name of the person returned by getPersons should be the same that was added to the list. Test the code:\nRoom r = new Room();\nSystem.out.println(r.isEmpty());\nPerson person = new Person(\"ada\", 168);\nr.add(person);\nSystem.out.println(r.isEmpty());\nArrayList<Person> persons = r.getPersons();\nSystem.out.println(persons);", person.getName(), fromList.getName());
        assertEquals("The height of the person returned by getPersons should be the same that was added to the list. Test the code:\nRoom r = new Room();\nSystem.out.println(r.isEmpty());\nPerson person = new Person(\"ada\", 168);\nr.add(person);\nSystem.out.println(r.isEmpty());\nArrayList<Person> persons = r.getPersons();\nSystem.out.println(persons);", person.getHeight(), fromList.getHeight());
    }

    @Test
    @Points("06-07.2")
    public void hasMethodShortest() throws Throwable {
        Reflex.reflect("Room").method("shortest").returning(Person.class).takingNoParams().requirePublic();
    }

    @Test
    @Points("06-07.2")
    public void methodShortestReturnsNullReferenceWhenRoomEmpty() throws Throwable {
        Object room = Reflex.reflect("Room").ctor().takingNoParams().invoke();
        Person person;
        try {
            person = Reflex.reflect("Room").method("shortest").returning(Person.class).takingNoParams().invokeOn(room);
        } catch (Throwable t) {
            fail("An error occured when shortest method was called. Test the code:\nRoom r = new Room();\nr.shortest();");
            return;
        }

        assertNull("Calling the method 'shortest' on an empty roop should return a null reference. Test the code:\nRoom r = new Room();\nSystem.out.println(r.shortest());", person);
    }

    @Test
    @Points("06-07.2")
    public void methodShortestReturnsShortestPerson1() throws Throwable {
        Reflex.reflect("Room").method("shortest").returning(Person.class).takingNoParams().requirePublic();
        Object room = Reflex.reflect("Room").ctor().takingNoParams().invoke();

        Person first = new Person("first", 1);
        Person second = new Person("second", 2);
        Person third = new Person("third", 3);

        String code = "\nRoom r = new Room();\n"
                + "Person first = new Person(\"first\", 1);\n"
                + "Person second = new Person(\"second\", 2);\n"
                + "Person third = new Person(\"third\", 3);\n\nr.add(first);\nr.add(second);\nr.add(third);";

        try {
            Reflex.reflect("Room").method("add").returningVoid().taking(Person.class).invokeOn(room, first);
            Reflex.reflect("Room").method("add").returningVoid().taking(Person.class).invokeOn(room, second);
            Reflex.reflect("Room").method("add").returningVoid().taking(Person.class).invokeOn(room, third);
        } catch (Throwable t) {
            fail("An error occurred while adding a person to the room. Test the code: " + code);
        }

        code += "\n\nPerson shortestPerson = r.shortest();\nSystem.out.println(shortestPerson);";

        Person shortestPerson;
        try {
            shortestPerson = Reflex.reflect("Room").method("shortest").returning(Person.class).takingNoParams().invokeOn(room);
        } catch (Throwable t) {
            fail("An error occurred while calling the method 'shortest'. Test the code:" + code);
            return;
        }

        assertNotNull("When the room is not empty, the 'shortest' method should not return null. Test the code:" + code, shortestPerson);
        assertEquals("The 'shortest' method should return the shortest person in the room. Test the code:" + code, first, shortestPerson);
    }

    @Test
    @Points("06-07.2")
    public void methodShortestReturnsShortestPerson2() throws Throwable {
        Reflex.reflect("Room").method("shortest").returning(Person.class).takingNoParams().requirePublic();
        Object room = Reflex.reflect("Room").ctor().takingNoParams().invoke();

        Person first = new Person("first", 3);
        Person second = new Person("second", 2);
        Person third = new Person("thrd", 1);

        String code = "\nRoom r = new Room();\n"
                + "Person first = new Person(\"first\", 3);\n"
                + "Person second = new Person(\"second\", 2);\n"
                + "Person third = new Person(\"third\", 1);\n\nr.add(first);\nr.add(second);\nr.add(third);";

        try {
            Reflex.reflect("Room").method("add").returningVoid().taking(Person.class).invokeOn(room, first);
            Reflex.reflect("Room").method("add").returningVoid().taking(Person.class).invokeOn(room, second);
            Reflex.reflect("Room").method("add").returningVoid().taking(Person.class).invokeOn(room, third);
        } catch (Throwable t) {
            fail("An error occurred when adding a person to the room. Test the code:" + code);
        }

        code += "\n\nPerson shortestPerson = r.shortest();\nSystem.out.println(shortestPerson);";

        Person shortestPerson;
        try {
            shortestPerson = Reflex.reflect("Room").method("shortest").returning(Person.class).takingNoParams().invokeOn(room);
        } catch (Throwable t) {
            fail("En error occurred when calling the 'shortest' method. Try the code:" + code);
            return;
        }

        assertNotNull("When the room is not empty, the 'shortest' method should not return null. Test the code:" + code, shortestPerson);
        assertEquals("The method 'shortest should return the shortest person in the room. Test the code:" + code, third, shortestPerson);
    }

    @Test
    @Points("06-07.3")
    public void methodTakeExists() throws Throwable {
        Reflex.reflect("Room").method("take").returning(Person.class).takingNoParams().requirePublic();
    }

    @Test
    @Points("06-07.3")
    public void methodTakeReturnsNullWHenRoomIsEmpty() throws Throwable {
        Object room = Reflex.reflect("Room").ctor().takingNoParams().invoke();
        Person person;
        try {
            person = Reflex.reflect("Room").method("take").returning(Person.class).takingNoParams().invokeOn(room);
        } catch (Throwable t) {
            fail("An error occurred when calling the 'take' method. Try the code:\nRoom r = new Room();\nr.take();");
            return;
        }

        assertNull("Calling 'take' on an empty room should return null reference. Test the code:\nRoom r = new Room();\nSystem.out.println(r.take());", person);
    }

    @Test
    @Points("06-07.3")
    public void methodTakeRetunsShortestPerson1() throws Throwable {
        Reflex.reflect("Room").method("take").returning(Person.class).takingNoParams().requirePublic();
        Object room = Reflex.reflect("Room").ctor().takingNoParams().invoke();

        Person first = new Person("first", 2);
        Person second = new Person("second", 1);
        Person third = new Person("third", 3);

        String code = "\nRoom r = new Room();\n"
                + "Person first = new Person(\"first\", 2);\n"
                + "Person second = new Person(\"second\", 1);\n"
                + "Person third = new Person(\"third\", 3);\n\nr.add(first);\nr.add(second);\nr.add(third);";

        try {
            Reflex.reflect("Room").method("add").returningVoid().taking(Person.class).invokeOn(room, first);
            Reflex.reflect("Room").method("add").returningVoid().taking(Person.class).invokeOn(room, second);
            Reflex.reflect("Room").method("add").returningVoid().taking(Person.class).invokeOn(room, third);
        } catch (Throwable t) {
            fail("An error occurred when adding a person to the room. Test the code:" + code);
        }

        code += "\n\nPerson shortestPerson = r.take();\nSystem.out.println(shortestPerson);";

        Person shortestPerson;
        try {
            shortestPerson = Reflex.reflect("Room").method("take").returning(Person.class).takingNoParams().invokeOn(room);
        } catch (Throwable t) {
            fail("An error occurred when calling the 'take' method. Test the code:" + code);
            return;
        }

        assertNotNull("When the room is not empty, the 'take' method should not return null. Test the code:" + code, shortestPerson);
        assertEquals("The 'take' method should return the shortest person added to the room. Test the code:" + code, second, shortestPerson);

    }

    @Test
    @Points("06-07.3")
    public void methodTakeReturnsShortestPerson2() throws Throwable {
        Reflex.reflect("Room").method("take").returning(Person.class).takingNoParams().requirePublic();
        Object room = Reflex.reflect("Room").ctor().takingNoParams().invoke();

        Person first = new Person("first", 3);
        Person second = new Person("second", 2);
        Person third = new Person("third", 1);

        String code = "\nRoom r = new Room();\n"
                + "Person first = new Person(\"first\", 3);\n"
                + "Person second = new Person(\"second\", 2);\n"
                + "Person third = new Person(\"third\", 1);\n\nr.add(first);\nr.add(second);\nr.add(third);";

        try {
            Reflex.reflect("Room").method("add").returningVoid().taking(Person.class).invokeOn(room, first);
            Reflex.reflect("Room").method("add").returningVoid().taking(Person.class).invokeOn(room, second);
            Reflex.reflect("Room").method("add").returningVoid().taking(Person.class).invokeOn(room, third);
        } catch (Throwable t) {
            fail("An error occurred when adding a person to the room. Test the code:" + code);
        }

        code += "\n\nPerson shortestPerson = r.take();\nSystem.out.println(shortestPerson);";

        Person shortestPerson;
        try {
            shortestPerson = Reflex.reflect("Room").method("take").returning(Person.class).takingNoParams().invokeOn(room);
        } catch (Throwable t) {
            fail("An error occurred when calling the 'take' method. Test the code:" + code);
            return;
        }

        assertNotNull("When the room is not empty, the 'take' method should not return null. Test the code:" + code, shortestPerson);
        assertEquals("The 'take' method should return the shortest person that has been added to the rome. Test the code:" + code, third, shortestPerson);
    }

    @Test
    @Points("06-07.3")
    public void methodTakeRemovesShortestPersonFromRoom() throws Throwable {
        Reflex.reflect("Room").method("take").returning(Person.class).takingNoParams().requirePublic();
        Object room = Reflex.reflect("Room").ctor().takingNoParams().invoke();

        Person first = new Person("first", 1);

        String code = "\nRoom r = new Room();\n"
                + "Person first = new Person(\"first\", 1);\n"
                + "\nr.add(first);";

        try {
            Reflex.reflect("Room").method("add").returningVoid().taking(Person.class).invokeOn(room, first);
        } catch (Throwable t) {
            fail("An error occurred when adding a person to the room. Try the code:" + code);
        }

        code += "\nSystem.out.println(r.isEmpty());";
        assertFalse("When a person has been added to the room, it should not be empty. Test the code:" + code, Reflex.reflect("Room").method("isEmpty").returning(boolean.class).takingNoParams().invokeOn(room));

        code += "\n\nPerson shortestPerson = r.take();\nSystem.out.println(takePerson);";

        Person shortestPerson;
        try {
            shortestPerson = Reflex.reflect("Room").method("take").returning(Person.class).takingNoParams().invokeOn(room);
        } catch (Throwable t) {
            fail("An error occurred when calling the 'take' method. Test the code:" + code);
            return;
        }

        assertNotNull("When the room is not empty, the 'take' method should return a null reference. Test the code:" + code, shortestPerson);
        assertEquals("The correct person was not taken. Test the code:" + code, first, shortestPerson);

        code += "\nSystem.out.println(r.isEmpty());";

        assertTrue("A person was added to the room, and then removed with the 'take' method. The room should be empty. Test the code:" + code, Reflex.reflect("Room").method("isEmpty").returning(boolean.class).takingNoParams().invokeOn(room));
    }

    @Test
    @Points("06-07.3")
    public void methodTakeGivesPersonsInHeightOrder() throws Throwable {
        Reflex.reflect("Room").method("take").returning(Person.class).takingNoParams().requirePublic();
        Object room = Reflex.reflect("Room").ctor().takingNoParams().invoke();
        
        String code = "\nRoom r = new Room();\n"
                + "Person first = new Person(\"first\", 62);\n"
                + "Person second = new Person(\"second\", 59);\n"
                + "Person third = new Person(\"third\", 104);\n"
                + "Person fourth = new Person(\"fourth\", 61);\n"
                + "\nh.lisaa(first);"
                + "\nh.lisaa(second);"
                + "\nh.lisaa(third);"
                + "\nh.lisaa(fourth);\n"
                + "\nwhile(!r.isEmpty()) {\n"
                + "    System.out.println(r.take());\n"
                + "}";

        Person first = new Person("first", 62);
        Person second = new Person("second", 59);
        Person third = new Person("third", 104);
        Person fourth = new Person("fourth", 61);
        
        try {
            Reflex.reflect("Room").method("add").returningVoid().taking(Person.class).invokeOn(room, first);
            Reflex.reflect("Room").method("add").returningVoid().taking(Person.class).invokeOn(room, second);
            Reflex.reflect("Room").method("add").returningVoid().taking(Person.class).invokeOn(room, third);
            Reflex.reflect("Room").method("add").returningVoid().taking(Person.class).invokeOn(room, fourth);
        } catch (Throwable t) {
            fail("Something unexpected occurred. Test the code:" + code);
        }
        
        List<Person> expected = new ArrayList<>(Arrays.asList(second, fourth, first, third));
        
        try {
            while(!Reflex.reflect("Room").method("isEmpty").returning(boolean.class).takingNoParams().invokeOn(room)) {
                Person shortestPerson = Reflex.reflect("Room").method("take").returning(Person.class).takingNoParams().invokeOn(room);
                assertEquals("Something unexpected occurred. Test the code:" + code, shortestPerson, expected.get(0));
                expected.remove(0);
            }
        } catch (Throwable t) {
            fail("Something unexpected occurred. Test the code:" + code);
        }

        assertTrue("Something unexpected occurred. Test the code:" + code, expected.isEmpty());
    }

}
