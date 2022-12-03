
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Random;
import org.junit.*;
import static org.junit.Assert.*;

public class HealthStationTest {

    HealthStation station;
    Person peter;
    Random rand = new Random();

    Reflex.ClassRef<Object> klass;
    String klassName = "HealthStation";

    @Before
    public void findClass() {
        klass = Reflex.reflect(klassName);
    }

    @Before
    public void setUp() {
        station = new HealthStation();
        peter = new Person("Peter", 33, 175, 78);
    }

    @Points("05-09.1")
    @Test
    public void noExtraVariables1() {
        sanityCheck();
    }

    @Test
    @Points("05-09.1")
    public void canWeigh() {
        assertEquals("check the code: station = HealthStation(); "
                + "p = new Person(\"Peter\", 33, 175, 78); "
                + "System.out.println( station.weigh(p) )", peter.getWeight(), station.weigh(peter));

        for (int i = 0; i < 5; i++) {
            int weight = 60 + rand.nextInt(60);
            Person ethan = new Person("Ethan", 45, 181, weight);

            assertEquals("check the code: station = HealthStation(); "
                    + "p = new Person(\"Ethan\", 45, 181, " + weight + "); "
                    + "System.out.println( station.weigh(p) )", ethan.getWeight(), station.weigh(ethan));
        }
    }

    @Points("05-09.2")
    @Test
    public void noExtraVariables2() {
        sanityCheck();
    }

    @Points("05-09.2")
    @Test
    public void aMethodCalledFeedExists() throws Throwable {
        String method = "feed";

        Person p = new Person("Peter", 20, 175, 85);
        HealthStation s = new HealthStation();

        assertTrue("implement a method public void " + method + "(Person p) for the class " + klassName,
                klass.method(s, method).returningVoid().taking(Person.class).isPublic());

        String e = "\nThe code that caused the error "
                + "s = new HealthStation; p = new Person(\"Peter\", 20, 175, 85); s.weigh(p);";

        klass.method(s, method).returningVoid().taking(Person.class).withNiceError(e).invoke(p);
    }

    @Test
    @Points("05-09.2")
    public void canFeed() {
        int expected = peter.getWeight() + 1;
        feed(station, peter);

        assertEquals("Feeding should increase the weight of the person by one kilo. Check the code: \n"
                + "station = HealthStation(); "
                + "p = new Person(\"Peter\", 33, 175, 78); "
                + "station.feed(p); "
                + "System.out.println( p.getPaino() )",
                expected, peter.getWeight());

        assertEquals("Feeding should increase the weight of the person by one kilo. Check the code: \n"
                + "station = HealthStation(); "
                + "p = new Person(\"Peter\", 33, 175, 78); "
                + "station.feed(p); "
                + "System.out.println( station.weigh(p) )",
                expected, station.weigh(peter));

        feed(station, peter);
        feed(station, peter);
        feed(station, peter);
        feed(station, peter);

        assertEquals("Feeding should increase the weight of the person by one kilo. Check the code: \n"
                + "station = HealthStation(); "
                + "peter = new Person(\"Peter\", 33, 175, 78); "
                + "station.feed(peter); "
                + "station.feed(peter); "
                + "station.feed(peter); "
                + "station.feed(peter); "
                + "station.feed(peter); "
                + "System.out.println( peter.getPaino() )",
                expected + 4, peter.getWeight());
    }

    @Points("05-09.3")
    @Test
    public void noExtraVariables3() {
        sanityCheck();
    }

    @Test
    @Points("05-09.3")
    public void aMethodCalledWeighingsExists() throws Throwable {
        String method = "weighings";

        HealthStation s = new HealthStation();
        
        assertTrue("implement a method public int " + method + "() for the class " + klassName,
                klass.method(s, method).returning(int.class).takingNoParams().isPublic());

        String e = "\nThe code that caused the error: "
                + "s = new HealthStation; s.weighings();";

        klass.method(s, method).returning(int.class).takingNoParams().withNiceError(e).invoke();
    }

    @Test
    @Points("05-09.3")
    public void numberOfWeighingsInMemory() {
        assertEquals("Does the method weighings() work as intended? Initially no one has been weighed! Try out the code "
                + "station = HealthStation(); "
                + "System.out.println( station.weighings() )",
                0, weighings(station));

        station.weigh(peter);
        assertEquals("Does the method weighings() work as intended? The method should tell how many times the method weigh() has been called "
                + "Try out the code\n"
                + "station = HealthStation(); "
                + "p1 = new Person(\"Peter\", 33, 175, 78); "
                + "station.weigh(p1);"
                + "System.out.println( station.weighings() )",
                1, weighings(station));

        Person ethan = new Person("Ethan", 0, 52, 4);
        station.weigh(ethan);
        assertEquals("Does the method weighings() work as intended? The method should tell how many times the method weigh() has been called "
                + "Try out the code\n"
                + "station = HealthStation(); "
                + "p1 = new Person(\"Peter\", 33, 175, 78); "
                + "p2 = new Person(\"Ethan\", 0, 52, 4); "
                + "station.weigh(p1);"
                + "station.weigh(p2);"
                + "System.out.println( station.weighings() )",
                2, weighings(station));

        station.weigh(ethan);
        station.weigh(peter);
        station.weigh(peter);
        assertEquals("Does the method weighings() work as intended? The method should tell how many times the method weigh() has been called "
                + "Try out the code\n"
                + "station = HealthStation(); "
                + "p1 = new Person(\"Peter\", 33, 175, 78); "
                + "p2 = new Person(\"Ethan\", 0, 52, 4); "
                + "station.weigh(p1);"
                + "station.weigh(p2);"
                + "station.weigh(p2);"
                + "station.weigh(p1);"
                + "station.weigh(p1);"
                + "System.out.println( station.weighings() )",
                5, weighings(station));
    }

    String nameOfTheClass = "HealthStation";

    private void feed(Object station, Person hlo) {
        try {
            Method feed = ReflectionUtils.requireMethod(HealthStation.class, "feed", Person.class);
            ReflectionUtils.invokeMethod(void.class, feed, station, hlo);
        } catch (Throwable t) {
        }
    }

    private int weighings(Object station) {
        try {
            Method weighings = ReflectionUtils.requireMethod(HealthStation.class, "weighings");
            return ReflectionUtils.invokeMethod(int.class, weighings, station);
        } catch (Throwable t) {
        }
        return -1;
    }

    private void sanityCheck() throws SecurityException {
        String nameOfTheClass = "HealthStation";

        Field[] fields = ReflectionUtils.findClass(nameOfTheClass).getDeclaredFields();

        for (Field field : fields) {
            assertFalse("you don't need \"static variables\", delete " + fieldName(field.toString()), field.toString().contains("static") && !field.toString().contains("final"));
            assertTrue("visibility of every object variable of the class must be private, change " + fieldName(field.toString()), field.toString().contains("private"));
        }

        if (fields.length > 1) {
            int var = 0;
            for (Field field : fields) {
                if (!field.toString().contains("final")) {
                    var++;
                }
            }
            assertTrue("The class " + nameOfTheClass + " only need the object variable that remembers the number of weighings. Delete unnecessary variables.", var < 2);
        }
    }

    private String fieldName(String toString) {
        return toString.replace(nameOfTheClass + ".", "");
    }
}
