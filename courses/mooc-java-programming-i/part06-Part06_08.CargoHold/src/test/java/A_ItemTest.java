
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Field;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

@Points("06-08.1")
public class A_ItemTest {

    String klassName = "Item";
    Reflex.ClassRef<Object> klass;

    @Before
    public void setup() {
        klass = Reflex.reflect(klassName);
    }

    @Test
    public void classIsPublic() {
        assertTrue("Class " + klassName + " has to be public, declared \npublic class " + klassName + " {...\n}", klass.isPublic());
    }

    @Test
    public void noExtraVariables() {
        sanitatryCheck(klassName, 2, "object variables for name and weight");
    }

    @Test
    public void testConstructor() throws Throwable {
        Reflex.MethodRef2<Object, Object, String, Integer> ctor = klass.constructor().taking(String.class, int.class).withNiceError();
        assertTrue("Create a public constructor for class " + klassName + " : public " + klassName + "(String name, int weight)", ctor.isPublic());
        String v = "error caused by new Item(\"Phone\", 1);";
        ctor.withNiceError(v).invoke("Phone", 1);
    }

    public Object create(String name, int weight) throws Throwable {
        Reflex.MethodRef2<Object, Object, String, Integer> ctor = klass.constructor().taking(String.class, int.class).withNiceError();
        return ctor.invoke(name, weight);
    }

    @Test
    public void itemGetName() throws Throwable {
        String methodToTest = "getName";
        Reflex.ClassRef<Object> tuoteClass = Reflex.reflect(klassName);

        Object item = create("Book", 2);

        assertTrue("In class " + klassName + " create method public String " + methodToTest + "() ", tuoteClass.method(item, methodToTest)
                .returning(String.class).takingNoParams().isPublic());


        String v = "\nError in code Item t = new Item(\"Book\", 2); "
                + "t.getName();";

        assertEquals("Check code Item t = new Item(\"Book\", 2); "
                + "t.getName();", "Book", tuoteClass.method(item, methodToTest)
                .returning(String.class).takingNoParams().withNiceError(v).invoke());

        item = create("Car", 800);


        v = "\nError in code Item t = new Item(\"Car\", 800); "
                + "t.getName();";

        assertEquals("Check code Item t = new Item(\"Car\", 800);  "
                + "t.getName();", "Car", tuoteClass.method(item, methodToTest)
                .returning(String.class).takingNoParams().withNiceError(v).invoke());
    }

    @Test
    public void itemGetWeight() throws Throwable {
        String methodToTest = "getWeight";
        Reflex.ClassRef<Object> itemClass = Reflex.reflect(klassName);

        Object item = create("Book", 2);

        assertTrue("In class " + klassName + " create method public int " + methodToTest + "() ", itemClass.method(item, methodToTest)
                .returning(int.class).takingNoParams().isPublic());

        String v = "\nError in code Item t = new Item(\"Book\", 2); "
                + "t.getWeight();";

        assertEquals("Check code Item t = new Item(\"Book\", 2); "
                + "t.getWeight();", 2, (int)itemClass.method(item, methodToTest)
                .returning(int.class).takingNoParams().withNiceError(v).invoke());

        item = create("Car", 800);

        v = "\nError in code Item t = new Item(\"Car\", 800); "
                + "t.getWeight();";

        assertEquals("Check code Item t = new Item(\"Car\", 800);  "
                + "t.getWeight();", 800, (int)itemClass.method(item, methodToTest)
                .returning(int.class).takingNoParams().withNiceError(v).invoke());
    }

    @Test
    public void itemToString() throws Throwable {
        Object item = create("Book", 2);

        assertFalse("Create a toString method according to the exercise guidelines",item.toString().contains("@"));

        assertTrue("Check that the toString method works as expected. \n"
                + "Item t = new Item(\"book\", 2); t.toString();  \n"+item.toString(),contains(item.toString(), "Book", "2", "kg"));
    }

    private boolean contains(String returnValue, String... expectedValues) {
        for (String value : expectedValues) {
            if (!returnValue.contains(value)) {
                return false;
            }
        }

        return true;
    }

    private void sanitatryCheck(String klassName, int n, String m) throws SecurityException {
        Field[] fields = ReflectionUtils.findClass(klassName).getDeclaredFields();

        for (Field field : fields) {
            assertFalse("You do not need \"static variables\". Remove from class" + klassName + " variables " + field(field.toString(), klassName), field.toString().contains("static") && !field.toString().contains("final"));
            assertTrue("All object variables of a class should be private, but we found from class " + klassName + " public variables: " + field(field.toString(), klassName), field.toString().contains("private"));
        }

        if (fields.length > 1) {
            int var = 0;
            for (Field field : fields) {
                if (!field.toString().contains("final")) {
                    var++;
                }
            }
            assertTrue( klassName + "- class only needs " + m + "variables, remove extra ones!", var <= n);
        }
    }

    private String field(String toString, String klassName) {
        return toString.replace(klassName + ".", "").replace("java.lang.", "").replace("java.util.", "");
    }
}
