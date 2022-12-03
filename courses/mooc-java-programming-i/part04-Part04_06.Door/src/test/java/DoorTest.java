
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.MockInOut;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Field;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

@Points("04-06")
public class DoorTest {

    Reflex.ClassRef<Object> klass;
    String className = "Door";

    @Before
    public void findClass() {
        klass = Reflex.reflect(className);
    }

    @Test
    public void classIsPublic() {
        assertTrue("Class" + className + " must be public, so it must be declared \npublic class " + className + " {...\n}", klass.isPublic());
    }

    @Test
     public void noUnneseccaryVariables() {
        sanitaryCheck();
    }
     
    @Test
    public void hasMethod() throws Throwable {
        Reflex.ClassRef<Object> doorClass = Reflex.reflect(className);
        Object door = doorClass.constructor().takingNoParams().invoke();

        try {
            doorClass.method(door, "knock")
                    .returningVoid()
                    .takingNoParams().invoke();
        } catch (AssertionError ae) {
            fail("Error: " + ae + "\n In class  " + className + " add method public void knock()");
        }

        assertTrue("Method knock must be public, declared public void knock()", doorClass.method(door, "knock")
                .returningVoid()
                .takingNoParams().isPublic());
    }

    @Test
    public void testKnock() throws Throwable {
        MockInOut mio = new MockInOut("");

        Reflex.ClassRef<Object> doorClass = Reflex.reflect(className);
        Object door = doorClass.constructor().takingNoParams().invoke();
        klass.method(door, "knock").returningVoid().takingNoParams().invoke();

        String out = mio.getOutput();

        mio.close();

        assertTrue("You did not print 'Who's there?' from the method knock!", out.contains("Who's there?"));
    }

    @Test
    public void testKnock2() throws Throwable {
        MockInOut mio = new MockInOut("");

        Reflex.ClassRef<Object> doorClass = Reflex.reflect(className);
        Object door = doorClass.constructor().takingNoParams().invoke();

        String out = mio.getOutput();

        mio.close();

        assertFalse("Constructor should not print anything!", out.contains("Who's there?"));
    }

    private void sanitaryCheck() throws SecurityException {
        Field[] classObjectFields = ReflectionUtils.findClass(className).getDeclaredFields();

        for (Field field : classObjectFields) {
            assertFalse("You do not need \"static variables\", remove " + classField(field.toString()), field.toString().contains("static") && !field.toString().contains("final"));
            assertTrue("All object variables of the class should be private, change " + classField(field.toString()), field.toString().contains("private"));
        }

        if (classObjectFields.length >= 1) {
            int var = 0;
            for (Field field : classObjectFields) {
                if (!field.toString().contains("final")) {
                    var++;
                }
            }
            assertTrue("The class" + className + " only needs an object variable for the sound, remove the extras ", var < 2);
        }
    }

    private String classField(String toString) {
        return toString.replace(className + ".", "");
    }
}
