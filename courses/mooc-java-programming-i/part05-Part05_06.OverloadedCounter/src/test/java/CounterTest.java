
import fi.helsinki.cs.tmc.edutestutils.Points;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class CounterTest {

    String name = "Counter";

    Class l;

    MethodSignature value = new MethodSignature(Integer.TYPE, "value");
    MethodSignature increase = new MethodSignature(Void.TYPE, "increase");
    MethodSignature decrease = new MethodSignature(Void.TYPE, "decrease");

    MethodSignature increase2 = new MethodSignature(Void.TYPE, "increase", Integer.TYPE);
    MethodSignature decrease2 = new MethodSignature(Void.TYPE, "decrease", Integer.TYPE);

    ConstructorSignature ci = new ConstructorSignature(Integer.TYPE);
    ConstructorSignature c = new ConstructorSignature();

    @Before
    public void findClass() {
        l = Utils.getClass(name);
    }

    @Points("05-06.1")
    @Test
    public void testConstructors() {
        ci.invokeIn(l, 10);
        ci.invokeIn(l, 2);
    }

    @Points("05-06.1")
    @Test
    public void testValue() {

        Object o = ci.invokeIn(l, 10);
        Integer i = -9000;
        i = (Integer) value.invokeIn(l, o);
        assertEquals("Wrong value returned. Try:\nCounter c = new Counter(10);\nSystem.out.println(c.value());\n",
                10, (int) i);

        o = ci.invokeIn(l, 2);
        i = (Integer) value.invokeIn(l, o);
        assertEquals("Wrong value returned. Try:\nCounter c = new Counter(2);\nSystem.out.println(c.value());\n",
                2, (int) i);

    }

    @Points("05-06.1")
    @Test
    public void testIncrease() {

        Object o = ci.invokeIn(l, 5);
        increase.invokeIn(l, o);
        int i = (Integer) value.invokeIn(l, o);
        assertEquals("Wrong value returned. Try:\nCounter c = new Counter(5);\nc.increase();\nSystem.out.println(c.value());\n",
                6, (int) i);

        increase.invokeIn(l, o);
        i = (Integer) value.invokeIn(l, o);
        assertEquals("Wrong value returned. Try:\nCounter c = new Counter(5);\nc.increase();\nc.increase();\nSystem.out.println(c.value());\n",
                7, (int) i);

    }

    @Points("05-06.1")
    @Test
    public void testDecrease() {

        Object o = ci.invokeIn(l, 900);
        decrease.invokeIn(l, o);
        int i = (Integer) value.invokeIn(l, o);
        assertEquals("Wrong value returned. Try:\nCounter c = new Counter(900);\nc.decrease();\nSystem.out.println(c.value());\n",
                899, (int) i);

        decrease.invokeIn(l, o);
        i = (Integer) value.invokeIn(l, o);
        assertEquals("Wrong value returned. Try:\nCounter c = new Counter(900);\nc.decrease();\nc.decrease();\nSystem.out.println(c.value());\n",
                898, (int) i);
    }

    @Points("05-06.1")
    @Test
    public void testDecreaseNoChecks() {
        Object o = ci.invokeIn(l, 2);
        decrease.invokeIn(l, o);
        int i = (Integer) value.invokeIn(l, o);
        assertEquals("Wrong value returned. Try:\nCounter c = new Counter(2);\nc.decrease();\nSystem.out.println(c.value());\n",
                1, (int) i);

        decrease.invokeIn(l, o);
        i = (Integer) value.invokeIn(l, o);
        assertEquals("Wrong value returned. Try:\nCounter c = new Counter(2);\nc.decrease();\nc.decrease();\nSystem.out.println(c.value());\n",
                0, (int) i);

        decrease.invokeIn(l, o);
        i = (Integer) value.invokeIn(l, o);
        assertEquals("Wrong value returned. Try:\nCounter c = new Counter(2);\nc.decrease();\nc.decrease();\nc.decrease();\nSystem.out.println(c.value());\n",
                -1, (int) i);

    }
    
    @Points("05-06.1")
    @Test
    public void testConstructor() {

        Object o = ci.invokeIn(l, 11);
        int i = (Integer) value.invokeIn(l, o);
        assertEquals("Wrong value returned. Try:\nCounter c = new Counter(11);\nSystem.out.println(c.value());\n",
                11, i);

        o = c.invokeIn(l);
        i = (Integer) value.invokeIn(l, o);
        assertEquals("Wrong value returned. Try:\nCounter c = new Counter();\nSystem.out.println(c.value());\n",
                0, i);

    }

    @Points("05-06.2")
    @Test
    public void testIncreaseWithParameters() {

        Object o = ci.invokeIn(l, 5);
        increase2.invokeIn(l, o, 2);
        int i = (Integer) value.invokeIn(l, o);
        assertEquals("Wrong value returned. Try:\nCounter c = new Counter(5);\nc.increase(2);\nSystem.out.println(c.value());\n",
                7, (int) i);

        increase2.invokeIn(l, o, 4);
        i = (Integer) value.invokeIn(l, o);
        assertEquals("Wrong value returned. Try:\n"
                + "Counter c = new Counter(5);\nc.increase(2);\nc.increase(4);\nSystem.out.println(c.value());\n",
                11, (int) i);
    }

    @Points("05-06.2")
    @Test
    public void testIncreaseWithNegativeParameter() {

        Object o = ci.invokeIn(l, 5);
        increase2.invokeIn(l, o, -2);
        int i = (Integer) value.invokeIn(l, o);
        assertEquals("Increase method with a negative parameter should not change the value of the counter "
                + "Try:\nCounter c = new Counter(5);\nc.increase(-2);\nSystem.out.println(c.value());\n",
                5, (int) i);
    }

    @Points("05-06.2")
    @Test
    public void testDecreaseWithParameter() {

        Object o = ci.invokeIn(l, 900);
        decrease2.invokeIn(l, o, 7);
        int i = (Integer) value.invokeIn(l, o);
        assertEquals("Wrong value returned. Try:\nCounter c = new Counter(900);\nc.decrease(7);\nSystem.out.println(c.value());\n",
                893, (int) i);

        decrease2.invokeIn(l, o, 3);
        i = (Integer) value.invokeIn(l, o);
        assertEquals("Wrong value returned. Try:\nCounter c = new Counter(900);\nc.decrease(7);\nc.decrease(3);\nSystem.out.println(c.value());\n",
                890, (int) i);
    }

    @Points("05-06.2")
    @Test
    public void testDecreaseWithNegativeParameter() {

        Object o = ci.invokeIn(l, 900);
        decrease2.invokeIn(l, o, -55);
        int i = (Integer) value.invokeIn(l, o);
        assertEquals("Decrease method with a negative parameter should not change the value of the counter "
                + " Try:\nCounter c = new Counter(900);\nc.decrease(-55);\nSystem.out.println(c.value());\n",
                900, (int) i);

    }

    @Points("05-06.2")
    @Test
    public void testDecreaseWithParameterNoCheck() {
        Object o = ci.invokeIn(l, 2);
        decrease2.invokeIn(l, o, 5);
        int i = (Integer) value.invokeIn(l, o);
        assertEquals("Wrong value returned. Try:\nCounter c = new Counter(2);\nc.decrease(5);\nSystem.out.println(c.value());\n",
                -3, (int) i);
    }
}
