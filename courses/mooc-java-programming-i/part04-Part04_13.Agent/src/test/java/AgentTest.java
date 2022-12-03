
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.MockInOut;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Field;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

@Points("04-13")
public class AgentTest {

    Reflex.ClassRef<Object> klass;
    String klassName = "Agent";

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
        Reflex.MethodRef2<Object, Object, String, String> cc = klass.constructor().taking(String.class, String.class).withNiceError();
        assertTrue("The class " + klassName + " must have a public constructor: public " + klassName + "(String initFirstname, String initLastName)", cc.isPublic());
        cc.invoke("James", "Pond");
    }

    @Test
    public void noExtraVariables() {
        sanityCheck();
    }

    @Test
    public void printMethodIsRemoved() throws Throwable {
        Reflex.ClassRef<Object> klass = Reflex.reflect(klassName);
        Object instance = klass.constructor().taking(String.class, String.class).invoke("James", "Pond");

        try {
            klass.method(instance, "print")
                    .returningVoid()
                    .takingNoParams().invoke();
            fail("Remove from the class" + klassName + " the method public void print()");
        } catch (AssertionError ae) {
        }
    }

    @Test
    public void testConstructorDoesNotPrintName() throws Throwable {
        MockInOut mio = new MockInOut("");

        Reflex.ClassRef<Object> klass = Reflex.reflect(klassName);
        Object instance = klass.constructor().taking(String.class, String.class).invoke("James", "Pond");

        String out = mio.getOutput();

        mio.close();

        assertTrue("The constructor should print nothing.", !out.contains("James") && !out.contains("Pond"));
    }

    @Test
    public void testToStringDoesNotPrintName() throws Throwable {
        MockInOut mio = new MockInOut("");

        Reflex.ClassRef<Object> klass = Reflex.reflect(klassName);
        Object instance = klass.constructor().taking(String.class, String.class).invoke("James", "Pond");
        instance.toString();

        String out = mio.getOutput();

        mio.close();

        assertTrue("The toString method of the class should print nothing.", !out.contains("James") && !out.contains("Pond"));
    }

    @Test
    public void testToStringReturnsAString() throws Throwable {
        Reflex.ClassRef<Object> klass = Reflex.reflect(klassName);
        Object instance = klass.constructor().taking(String.class, String.class).invoke("James", "Pond");
        String output = instance.toString();

        assertEquals("The string returned by the 'toString' method must be exactly the same as the print of the earlier 'print' method.", "My name is Pond, James Pond", output);
    }

    @Test
    public void testToStringReturnsAString2() throws Throwable {
        Reflex.ClassRef<Object> klass = Reflex.reflect(klassName);
        Object instance = klass.constructor().taking(String.class, String.class).invoke("Grace", "Hopper");
        String output = instance.toString();

        assertEquals("The string returned by the 'toString' method must be exactly the same as the print of the earlier 'print' method.", "My name is Hopper, Grace Hopper", output);
    }

    private void sanityCheck() throws SecurityException {
        Field[] fields = ReflectionUtils.findClass(klassName).getDeclaredFields();

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
            assertTrue("The class " + klassName + " only needs 'firstName' and 'lastName' as object variables. Delete unnecessary variables.", var < 3);
        }
    }

    private String fieldName(String toString) {
        return toString.replace(klassName + ".", "");
    }
}
