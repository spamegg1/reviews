
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Field;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

@Points("06-05.1")
public class A_GiftTest {

    String klassName = "Gift";
    Reflex.ClassRef<Object> klass;

    @Before
    public void setup() {
        klass = Reflex.reflect(klassName);
    }

    @Test
    public void classIsPublic() {
        assertTrue("The class " + klassName + " must be public, i.e. it should defined as \npublic class " + klassName + " {...\n}", klass.isPublic());
    }

    @Test
    public void noExtraVariables() {
        sanityCheck(klassName, 2, "the object variables for name and weight");
    }

    @Test
    public void testConstructor() throws Throwable {
        Reflex.MethodRef2<Object, Object, String, Integer> ctor = klass.constructor().taking(String.class, int.class).withNiceError();
        assertTrue("Define a public constructor for the class " + klassName + ": public " + klassName + "(String name, int weight)", ctor.isPublic());
        String e = "the code that caused the error: new Gift(\"Phone\", 1);";
        ctor.withNiceError(e).invoke("Phone", 1);
    }

    public Object create(String name, int weight) throws Throwable {
        Reflex.MethodRef2<Object, Object, String, Integer> ctor = klass.constructor().taking(String.class, int.class).withNiceError();
        return ctor.invoke(name, weight);
    }

    @Test
    public void giftGetNameMethod() throws Throwable {
        String method = "getName";
        Reflex.ClassRef<Object> productClass = Reflex.reflect(klassName);

        Object object = create("Book", 2);

        assertTrue("implement a method public String " + method + "() for the class " + klassName, productClass.method(object, method)
                .returning(String.class).takingNoParams().isPublic());


        String e = "\nThe code that caused the error: Gift g = new Gift(\"Book\", 2); "
                + "g.getName();";

        assertEquals("Check the code: Gift g = new Gift(\"Book\", 2); "
                + "g.getName();", "Book", productClass.method(object, method)
                .returning(String.class).takingNoParams().withNiceError(e).invoke());

        object = create("Car", 800);


        e = "\nThe code that caused the error: Gift g = new Gift(\"Car\", 800); "
                + "g.getName();";

        assertEquals("Check the code: Gift g = new Gift(\"Car\", 800);  "
                + "g.getName();", "Car", productClass.method(object, method)
                .returning(String.class).takingNoParams().withNiceError(e).invoke());
    }

    @Test
    public void giftGetWeightMethod() throws Throwable {
        String method = "getWeight";
        Reflex.ClassRef<Object> productClass = Reflex.reflect(klassName);

        Object object = create("Book", 2);

        assertTrue("implement a method public int " + method + "() for the class " + klassName, productClass.method(object, method)
                .returning(int.class).takingNoParams().isPublic());

        String e = "\nThe code that caused the error: Gift g = new Gift(\"Book\", 2); "
                + "g.getWeight();";

        assertEquals("Check the code: Gift g = new Gift(\"Book\", 2); "
                + "g.getWeight();", 2, (int)productClass.method(object, method)
                .returning(int.class).takingNoParams().withNiceError(e).invoke());

        object = create("Car", 800);

        e = "\nThe code that caused the error: Gift g = new Gift(\"Car\", 800); "
                + "g.getWeight();";

        assertEquals("Check the code: Gift g = new Gift(\"Car\", 800);  "
                + "g.getWeight();", 800, (int)productClass.method(object, method)
                .returning(int.class).takingNoParams().withNiceError(e).invoke());
    }


    private boolean includes(String returned, String... expectedValues) {
        for (String value : expectedValues) {
            if (!returned.contains(value)) {
                return false;
            }
        }

        return true;
    }

    private void sanityCheck(String klassName, int n, String m) throws SecurityException {
        Field[] fields = ReflectionUtils.findClass(klassName).getDeclaredFields();

        for (Field field : fields) {
            assertFalse("you don't need \"static variables\", remove the variable " + fieldName(field.toString(), klassName) + " from the class" + klassName, field.toString().contains("static") && !field.toString().contains("final"));
            assertTrue("the visibility of all object variables for the class should be private. For the class " + klassName + " change the following: " + fieldName(field.toString(), klassName), field.toString().contains("private"));
        }

        if (fields.length > 1) {
            int var = 0;
            for (Field field : fields) {
                if (!field.toString().contains("final")) {
                    var++;
                }
            }
            assertTrue("the class " + klassName + " doesn't need any other variables than " + m + ", remove the extras", var <= n);
        }
    }

    private String fieldName(String toString, String klassName) {
        return toString.replace(klassName + ".", "").replace("java.lang.", "").replace("java.util.", "");
    }
}
