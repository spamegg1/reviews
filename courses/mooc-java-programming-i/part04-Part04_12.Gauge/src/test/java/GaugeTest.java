
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Field;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

@Points("04-12")
public class GaugeTest {

    Reflex.ClassRef<Object> klass;
    String klassName = "Gauge";

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
        Reflex.MethodRef0<Object, Object> cc = klass.constructor().takingNoParams().withNiceError();
        assertTrue("Define a public constructor for class " + klassName + ", like so: public " + klassName + "()", cc.isPublic());
        cc.invoke();
    }

    @Test
    public void noExtraVariables() {
        sanityCheck();
    }

    @Test
    public void testIncrease() throws Throwable {
        Reflex.ClassRef<Object> klass = Reflex.reflect(klassName);
        Object instance = klass.constructor().takingNoParams().invoke();

        try {
            klass.method(instance, "increase")
                    .returningVoid()
                    .takingNoParams().invoke();
        } catch (AssertionError ae) {
            fail("Error: " + ae + "\n so give the class " + klassName + " a method 'public void increase()'");
        }

        assertTrue("The method 'increase' must be public, so it has to be defined as 'public void increase()'", klass.method(instance, "increase")
                .returningVoid()
                .takingNoParams().isPublic());
    }

    @Test
    public void testDecrease() throws Throwable {
        Reflex.ClassRef<Object> klass = Reflex.reflect(klassName);
        Object instance = klass.constructor().takingNoParams().invoke();

        try {
            klass.method(instance, "decrease")
                    .returningVoid()
                    .takingNoParams().invoke();
        } catch (AssertionError ae) {
            fail("Error: " + ae + "\n so give the class " + klassName + " a method 'public void decrease()'");
        }

        assertTrue("The method 'decrease' must be public, so it has to be defined as 'public void decrease()'", klass.method(instance, "decrease")
                .returningVoid()
                .takingNoParams().isPublic());
    }

    @Test
    public void testValue() throws Throwable {
        Reflex.ClassRef<Object> klass = Reflex.reflect(klassName);
        Object instance = klass.constructor().takingNoParams().invoke();

        try {
            klass.method(instance, "value")
                    .returning(int.class)
                    .takingNoParams().invoke();
        } catch (AssertionError ae) {
            fail("Error: " + ae + "\n so give the class " + klassName + " a method 'public int value()'");
        }

        assertTrue("The method 'value' must be public, so it has to be defined as 'public int value()'", klass.method(instance, "value")
                .returning(int.class)
                .takingNoParams().isPublic());

        int v = klass.method(instance, "value")
                .returning(int.class)
                .takingNoParams().invokeOn(instance);

        assertTrue("Value must be 0 in the beginning. Try it out:\nGauge g = new Gauge();\nSystem.out.println(g.value());\nNow the output was " + v, v == 0);
    }

    @Test
    public void testLowerLimit() throws Throwable {
        Reflex.ClassRef<Object> klass = Reflex.reflect(klassName);
        Object instance = klass.constructor().takingNoParams().invoke();

        try {
            klass.method(instance, "value")
                    .returning(int.class)
                    .takingNoParams().invoke();
        } catch (AssertionError ae) {
            fail("Error: " + ae + "\n so give the class " + klassName + " a method 'public int value()'");
        }

        assertTrue("The method 'value' must be public, so it has to be defined as 'public int value()'", klass.method(instance, "value")
                .returning(int.class)
                .takingNoParams().isPublic());

        for (int i = 0; i < 10; i++) {
            try {
                klass.method(instance, "decrease")
                        .returningVoid()
                        .takingNoParams()
                        .invokeOn(instance);
            } catch (Throwable t) {
                fail("Error when the method 'decrease()' was called. The error: " + t.getMessage());
            }
        }

        int v = klass.method(instance, "value")
                .returning(int.class)
                .takingNoParams().invokeOn(instance);

        assertFalse("Calling the method 'decrease' should not set the value to be negative. Try it out:\nGauge g = new Gauge();\n//call method 'decrease' 10 times\nSystem.out.println(g.value());\nThe output was " + v, v < 0);
        assertFalse("Calling the method 'decrease' should not increase the value. Try it out:\nGauge g = new Gauge();\n//call method 'decrease' 10 times\nSystem.out.println(g.value());\nThe output was " + v, v > 0);
    }

    @Test
    public void testUpperLimit() throws Throwable {
        Reflex.ClassRef<Object> klass = Reflex.reflect(klassName);
        Object instance = klass.constructor().takingNoParams().invoke();

        try {
            klass.method(instance, "value")
                    .returning(int.class)
                    .takingNoParams().invoke();
        } catch (AssertionError ae) {
            fail("Error: " + ae + "\n so give the class " + klassName + " a method 'public int value()'");
        }

        assertTrue("The method 'value' must be public, so it has to be defined as 'public int value()'", klass.method(instance, "value")
                .returning(int.class)
                .takingNoParams().isPublic());

        for (int i = 0; i < 10; i++) {
            try {
                klass.method(instance, "increase")
                        .returningVoid()
                        .takingNoParams()
                        .invokeOn(instance);
            } catch (Throwable t) {
                fail("Error when the method 'decrease()' was called. The erorr: " + t.getMessage());
            }
        }

        int v = klass.method(instance, "value")
                .returning(int.class)
                .takingNoParams().invokeOn(instance);

        assertFalse("Calling the method 'increase' should not set the value to be greater than five. Try it out:\nGauge g = new Gauge();\n//call method 'decrease' 10 times\nSystem.out.println(g.value());\nThe output was " + v, v > 5);
        assertFalse("Calling the method 'increase' should not decrease the value. Try it out:\nGauge g = new Gauge();\n//call method 'decrease' 10 times\nSystem.out.println(g.value());\nThe output was " + v, v < 5);
    }

    @Test
    public void testFull() throws Throwable {
        Reflex.ClassRef<Object> klass = Reflex.reflect(klassName);
        Object instance = klass.constructor().takingNoParams().invoke();

        try {
            klass.method(instance, "full")
                    .returning(boolean.class)
                    .takingNoParams().invokeOn(instance);
        } catch (AssertionError ae) {
            fail("Error: " + ae + "\n so give the class " + klassName + " a method 'public boolean full()'");
        }

        assertTrue("The method 'full' must be public, so it has to be defined as 'public boolean full()'", klass.method(instance, "full")
                .returning(boolean.class)
                .takingNoParams().isPublic());
    }

    @Test
    public void testIncreasingInSteps() throws Throwable {
        Reflex.ClassRef<Object> klass = Reflex.reflect(klassName);
        Object instance = klass.constructor().takingNoParams().invoke();

        try {
            klass.method(instance, "value")
                    .returning(int.class)
                    .takingNoParams().invoke();
        } catch (AssertionError ae) {
            fail("Error: " + ae + "\n so give the class " + klassName + " a method 'public int value()'");
        }

        assertTrue("The method 'value' must be public, so it has to be defined as 'public int value()'", klass.method(instance, "value")
                .returning(int.class)
                .takingNoParams().isPublic());

        for (int i = 1; i <= 5; i++) {
            try {
                klass.method(instance, "increase")
                        .returningVoid()
                        .takingNoParams()
                        .invokeOn(instance);
            } catch (Throwable t) {
                fail("Error when the method 'increase' was called. Error: " + t.getMessage());
            }

            int v = klass.method(instance, "value")
                    .returning(int.class)
                    .takingNoParams().invokeOn(instance);

            assertTrue("When the method 'increase()' has been called " + i + " times, the value should be " + i + ". Now the value was " + v, v == i);
        }
    }

    @Test
    public void testValue2() throws Throwable {
        Reflex.ClassRef<Object> klass = Reflex.reflect(klassName);
        Object instance = klass.constructor().takingNoParams().invoke();

        try {
            klass.method(instance, "value")
                    .returning(int.class)
                    .takingNoParams().invoke();
        } catch (AssertionError ae) {
            fail("Error: " + ae + "\n so give the class " + klassName + " a method 'public int value()'");
        }

        assertTrue("The method 'value' must be public, so it has to be defined as 'public int value()'", klass.method(instance, "value")
                .returning(int.class)
                .takingNoParams().isPublic());

        for (int i = 1; i <= 5; i++) {
            try {
                klass.method(instance, "increase")
                        .returningVoid()
                        .takingNoParams()
                        .invokeOn(instance);
            } catch (Throwable t) {
                fail("Error when the method 'increase' was called. Error: " + t.getMessage());
            }

            int v = klass.method(instance, "value")
                    .returning(int.class)
                    .takingNoParams().invokeOn(instance);

            assertTrue("When the method 'increase()' has been called " + i + " times, the value should be " + i + ". Now the value was " + v, v == i);

            boolean isFull = klass.method(instance, "full")
                    .returning(boolean.class)
                    .takingNoParams().invokeOn(instance);

            if (i < 5) {
                assertFalse("When the method 'increase' has been called " + i + " times, the method 'full' should not return true.", isFull);
            } else {
                assertTrue("When the method 'increase' has been called " + i + " times, the method 'full' should return true.", isFull);
            }
        }
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
            assertTrue("The class " + klassName + " only needs 'value' as object variable. Delete unnecessary variables.", var < 2);
        }
    }

    private String fieldName(String toString) {
        return toString.replace(klassName + ".", "");
    }
}
