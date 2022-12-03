
import fi.helsinki.cs.tmc.edutestutils.Points;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import org.junit.Test;

@Points("05-13")
public class IdenticalTwinsTest {

    @Test
    public void test1() throws Throwable {
        Person first = new Person("Leevi", new SimpleDate(1, 1, 2017), 70, 10);
        Person second = new Person("Leevi", new SimpleDate(1, 1, 2017), 70, 10);

        assertEquals("After two calls\nnew Person(\"Leevi\", new SimpleDate(1, 1, 2017), 70, 10);\n the created person should be the same.\nTry:\n"
                + "Person first = new Person(\"Leevi\", new SimpleDate(1, 1, 2017), 70, 10);\n"
                + "Person second = new Person(\"Leevi\", new SimpleDate(1, 1, 2017), 70, 10);\n"
                + "System.out.println(first.equals(second));", first, second);
    }

    @Test
    public void test2() throws Throwable {
        Person first = new Person("Leevi", new SimpleDate(1, 1, 2017), 70, 10);
        Person second = new Person("Leena", new SimpleDate(1, 1, 2017), 70, 10);
        
        assertFalse("Two persons with a different name should not be the same. Try:\n"
                + "Henkilo eka = new Henkilo(\"Leevi\", new Paivays(1, 1, 2017), 70, 10);\n"
                + "Henkilo toka = new Henkilo(\"Leena\", new Paivays(1, 1, 2017), 70, 10);\n"
                + "System.out.println(first.equals(second));", first.equals(second));
    }

    @Test
    public void test3() throws Throwable {
        Person first = new Person("Leevi", new SimpleDate(1, 1, 2017), 70, 10);
        Person second = new Person("Leevi", new SimpleDate(2, 1, 2017), 70, 10);

        assertFalse("Two persons with a same name but a different birthday should be the same. Try:\n"
                + "Person first = new Person(\"Leevi\", new SimpleDate(1, 1, 2017), 70, 10);\n"
                + "Person second = new Person(\"Leevi\", new SimpleDate(2, 1, 2017), 70, 10);\n"
                + "System.out.println(first.equals(second));", first.equals(second));
    }

    @Test
    public void test4() throws Throwable {
        Person first = new Person("Leevi", new SimpleDate(1, 1, 2017), 70, 10);
        Person second = new Person("Leevi", new SimpleDate(1, 2, 2017), 70, 10);

        assertFalse("Two persons with a same name but different month of birth should not be the same. Try:\n"
                + "Person first = new Person(\"Leevi\", new SimpleDate(1, 1, 2017), 70, 10);\n"
                + "Person second = new Person(\"Leevi\", new SimpleDate(1, 2, 2017), 70, 10);\n"
                + "System.out.println(first.equals(second));", first.equals(second));
    }

    @Test
    public void test5() throws Throwable {
        Person first = new Person("Leevi", new SimpleDate(1, 1, 2017), 70, 10);
        Person second = new Person("Leevi", new SimpleDate(1, 1, 2018), 70, 10);
        
        assertFalse("Two persons with same name but different year of birth should not be the same. Try:\n"
                + "Person first = new Person(\"Leevi\", new SimpleDate(1, 1, 2017), 70, 10);\n"
                + "Person second = new Person(\"Leevi\", new SimpleDate(1, 1, 2018), 70, 10);\n"
                + "System.out.println(first.equals(second));", first.equals(second));
    }

    @Test
    public void test6() throws Throwable {
        Person first = new Person("Leevi", new SimpleDate(1, 1, 2017), 70, 10);
        Person second = new Person("Leevi", new SimpleDate(1, 1, 2017), 71, 10);

        assertFalse("Two persons with different heights should not be the same. Try:\n"
                + "Person first = new Person(\"Leevi\", new SimpleDate(1, 1, 2017), 70, 10);\n"
                + "Person second = new Person(\"Leevi\", new SimpleDate(1, 1, 2017), 71, 10);\n"
                + "System.out.println(first.equals(second));", first.equals(second));
    }

    @Test
    public void test7() throws Throwable {
        Person first = new Person("Leevi", new SimpleDate(1, 1, 2017), 70, 10);
        Person second = new Person("Leevi", new SimpleDate(1, 1, 2017), 70, 11);

        assertFalse("Two persons with different weight should not be the same. Try:\n"
                + "Person first = new Person(\"Leevi\", new SimpleDate(1, 1, 2017), 70, 10);\n"
                + "Person second = new Person(\"Leevi\", new SimpleDate(1, 1, 2017), 70, 11);\n"
                + "System.out.println(first.equals(second));", first.equals(second));
    }
}
