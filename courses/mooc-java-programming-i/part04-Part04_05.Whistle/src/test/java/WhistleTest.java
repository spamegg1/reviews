
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.MockInOut;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Field;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

@Points("04-05")
public class WhistleTest {

    Reflex.ClassRef<Object> classInstance;
    String className = "Whistle";

    @Before
    public void findTheClass() {
        classInstance = Reflex.reflect(className);
    }

    @Test
    public void classIsPublic() {
        assertTrue("Class" + className + " must be public, so it must be declared \npublic class " + className + " {...\n}", classInstance.isPublic());
    }

    @Test
    public void testConstructor() throws Throwable {
        Reflex.MethodRef1<Object, Object, String> cc = classInstance.constructor().taking(String.class).withNiceError();
        assertTrue("For the class " + className + " make a public constructor: public " + className + "(String whistleSound)", cc.isPublic());
        cc.invoke("Kvaak");
    }

    @Test
    public void noUnneseccaryVariables() {
        sanitaryCheck();
    }

    @Test
    public void hasMethod() throws Throwable {
        Reflex.ClassRef<Object> whistleClass = Reflex.reflect(className);
        Object whistle = whistleClass.constructor().taking(String.class).invoke("Peef");

        try {
            whistleClass.method(whistle, "sound")
                    .returningVoid()
                    .takingNoParams().invoke();
        } catch (AssertionError ae) {
            fail("Errr: " + ae + "\n for class " + className + " add method public void sound()");
        }

        assertTrue("The method sound must be public, so declared public void sound()", whistleClass.method(whistle, "sound")
                .returningVoid()
                .takingNoParams().isPublic());
    }

    @Test
    public void testSound() throws Throwable {
        MockInOut mio = new MockInOut("");

        Object whistle = classInstance.constructor().taking(String.class).invoke("Peef");
        classInstance.method(whistle, "sound").returningVoid().takingNoParams().invoke();

        String out = mio.getOutput();

        mio.close();

        assertTrue("Method sound did not print the whistle sound!", out.contains("Peef"));
    }
    
    @Test
    public void testSound2() throws Throwable {
        MockInOut mio = new MockInOut("");

        Object whistle = classInstance.constructor().taking(String.class).invoke("Bleergh");
        classInstance.method(whistle, "sound").returningVoid().takingNoParams().invoke();

        String out = mio.getOutput();

        mio.close();

        assertTrue("Method sound did not print the whistle sound!", out.contains("Bleergh"));
    }

    @Test
    public void testSound3() throws Throwable {
        MockInOut mio = new MockInOut("");

        Object whistle = classInstance.constructor().taking(String.class).invoke("Bleergh");

        String out = mio.getOutput();

        mio.close();

        assertFalse("Printing should not happen in the constructor!", out.contains("Bleergh"));
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
