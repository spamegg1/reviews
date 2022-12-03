
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Field;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

@Points("04-14")
public class MultiplierTest {

    Reflex.ClassRef<Object> klass;
    String klassName = "Multiplier";

    @Before
    public void fetchClass() {
        klass = Reflex.reflect(klassName);
    }

    @Test
    public void classIsPublic() {
        assertTrue("Class " + klassName + " must be public, so it must be defined as\npublic class " + klassName + " {...\n}", klass.isPublic());
    }


    @Test
    public void testConstructor() throws Throwable {
        Reflex.MethodRef1<Object, Object, Integer> ctor = klass.constructor().taking(int.class).withNiceError();
        assertTrue("Define for the class " + klassName + " a public constructor: public " + klassName + "(int number)", ctor.isPublic());
        ctor.invoke(4);
    }

    @Test
    public void methodExists() throws Throwable {
        String method = "multiply";

        Reflex.ClassRef<Object> multiplierClass = Reflex.reflect(klassName);
        Object object = multiplierClass.constructor().taking(int.class).invoke(4);

        assertTrue("create for the class " + klassName + " the method 'public int " + method + "(int number)' ", multiplierClass.method(object, method)
                .returning(int.class).taking(int.class).isPublic());

        String e = "\nThe code that caused the error Multiplier m = new Multiplier(4); m.multiply(2);";

        multiplierClass.method(object, method)
                .returning(int.class).taking(int.class).withNiceError(e).invoke(2);

    }

    @Test
    public void testMethod() throws Throwable {
        String method = "multiply";

        Object o1 = klass.constructor().taking(int.class).invoke(4);
        Object o2 = klass.constructor().taking(int.class).invoke(1);
        Object o3 = klass.constructor().taking(int.class).invoke(7);

        int out = klass.method(o1, method).returning(int.class).taking(int.class).invoke(2);
        int out2 = klass.method(o2, method).returning(int.class).taking(int.class).invoke(3);
        int out3 = klass.method(o3, method).returning(int.class).taking(int.class).invoke(8);

        assertEquals("You returned a wrong value when multiply(2) was called on Multiplier(4)", 8, out);
        assertEquals("You returned a wrong value when multiply(3) was called on Multiplier(1)", 3, out2);
        assertEquals("You returned a wrong value when multiply(8) was called on Multiplier(7)", 56, out3);
    }
    
    @Test
    public void noExtraVariables(){
        saniteettitarkastus(1, "an object variable that remembers the number to be multiplied");
    }

    private void saniteettitarkastus(int n, String m) throws SecurityException {
        Field[] fields = ReflectionUtils.findClass(klassName).getDeclaredFields();

        for (Field field : fields) {
            assertFalse("you don't need \"static variables\", delete " + field(field.toString()), field.toString().contains("static") && !field.toString().contains("final"));
            assertTrue("the visibility for all the object variables of the class should be private, but the following was found: " + field(field.toString()), field.toString().contains("private"));
        }

        if (fields.length > 1) {
            int var = 0;
            for (Field field : fields) {
                if (!field.toString().contains("final")) {
                    var++;
                }
            }
            assertTrue("the class " + klassName + "only needs " + m + ", remove extra fields", var <= n);
        }
    }

    private String field(String toString) {
        return toString.replace(klassName + ".", "");
    }
}
