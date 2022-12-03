
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.MockInOut;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Field;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

@Points("04-09")
public class DebtTest {

    Reflex.ClassRef<Object> klass;
    String klassName = "Debt";

    @Before
    public void findClass() {
        klass = Reflex.reflect(klassName);
    }

    @Test
    public void classIsPublic() {
        assertTrue("Class " + klassName + " should be public. It should be declared \npublic class " + klassName + " {...\n}", klass.isPublic());
    }

    @Test
    public void testConstructor() throws Throwable {
        Reflex.MethodRef2<Object, Object, Double, Double> cc = klass.constructor().taking(double.class, double.class).withNiceError();
        assertTrue("Declare a public constructor in class" + klassName + ": public " + klassName + "(double initialBalance double initialInterestRate)", cc.isPublic());
        cc.invoke(1000.0, 1.05);
    }

    @Test
    public void noExtraVariables() {
        sanitaryCheck();
    }

    @Test
    public void hasMethodPrintBalance() throws Throwable {
        Reflex.ClassRef<Object> debtClass = Reflex.reflect(klassName);
        Object debt = debtClass.constructor().taking(double.class, double.class).invoke(1000.0, 2.0);

        try {
            debtClass.method(debt, "printBalance")
                    .returningVoid()
                    .takingNoParams().invoke();
        } catch (AssertionError ae) {
            fail("Error: " + ae + "\n In class " + klassName + " declare public method public void printBalance()");
        }

        assertTrue("Method printBalance must be public, or declared public void printBalance()", debtClass.method(debt, "printBalance")
                .returningVoid()
                .takingNoParams().isPublic());
    }

    @Test
    public void testPrinting() throws Throwable {
        MockInOut mio = new MockInOut("");

        Object debt = klass.constructor().taking(double.class, double.class).invoke(1000.0, 1.0);
        klass.method(debt, "printBalance").returningVoid().takingNoParams().invoke();

        String out = mio.getOutput();

        mio.close();

        assertTrue("You did not print the balance from method printBalance()! Output was:\n" + out + "\nWhen we did:\nDebt v new Debt(1000.0, 1.0);\nv.printBalance();", out.contains("1000") && !out.contains("1."));
    }

    @Test
    public void testPrinting2() throws Throwable {
        MockInOut mio = new MockInOut("");

        Object debt = klass.constructor().taking(double.class, double.class).invoke(1500.0, 2.0);
        klass.method(debt, "printBalance").returningVoid().takingNoParams().invoke();

        String out = mio.getOutput();

        mio.close();

        assertTrue("You did not print the balance from method printBalance()! Output was:\n" + out + "\nWhen we did:\nDebt v = new Debt(1500.0, 2.0);\nv.printBalance();", out.contains("1500") && !out.contains("2."));
    }

    @Test
    public void testaaTulostus3() throws Throwable {
        MockInOut mio = new MockInOut("");

        Object debt = klass.constructor().taking(double.class, double.class).invoke(1500.0, 2.0);

        String out = mio.getOutput();

        mio.close();

        assertFalse("Do not print the balance from the constructor! Output was:\n" + out + "\nWhen we did:\nDebt v = new Debt(1500.0, 2.0);", out.contains("1500"));
    }

    @Test
    public void hasMethodWaitOneYear() throws Throwable {
        Reflex.ClassRef<Object> debtClass = Reflex.reflect(klassName);
        Object debt = debtClass.constructor().taking(double.class, double.class).invoke(1000.0, 2.0);

        try {
            debtClass.method(debt, "waitOneYear")
                    .returningVoid()
                    .takingNoParams().invoke();
        } catch (AssertionError ae) {
            fail("Error: " + ae + "\n Implement method public void waitOneYear() in class " + klassName );
        }

        assertTrue("Method waitOneYear must be public, or declared public void waitOneYear()", debtClass.method(debt, "waitOneYear")
                .returningVoid()
                .takingNoParams().isPublic());
    }

    @Test
    public void waitOneYearDoesNotPrintAnything() throws Throwable {
        MockInOut mio = new MockInOut("");

        Object debt = klass.constructor().taking(double.class, double.class).invoke(1500.0, 2.0);
        klass.method(debt, "waitOneYear").returningVoid().takingNoParams().invoke();

        String out = mio.getOutput();

        mio.close();

        assertFalse("Do not print the balance in the method waitOneYear!", out.contains("1500"));
        assertFalse("Do not print the balance in the method waitOneYear!", out.contains("3000"));
        assertFalse("Do not print the balance in the method waitOneYear!", out.contains("2"));
    }

    @Test
    public void waitOneYearChangesTheBalance() throws Throwable {
        MockInOut mio = new MockInOut("");

        Object debt = klass.constructor().taking(double.class, double.class).invoke(1500.0, 2.0);
        klass.method(debt, "waitOneYear").returningVoid().takingNoParams().invoke();
        klass.method(debt, "printBalance").returningVoid().takingNoParams().invoke();

        String out = mio.getOutput();

        mio.close();

        double expected = 1500.0 * 2.0;
        assertTrue("The balance should increase when waiting a year. Expected " + expected + " .When we did\nDebt v = new Debt(1500.0, 2.0);\nv.waitOneYear();\nv.printBalance();\nOutput was:\n" + out, out.contains("" + expected));
    }

    @Test
    public void waitOneYearChangesTheBalance2() throws Throwable {
        MockInOut mio = new MockInOut("");

        Object debt = klass.constructor().taking(double.class, double.class).invoke(1500.0, 2.0);
        klass.method(debt, "waitOneYear").returningVoid().takingNoParams().invoke();
        klass.method(debt, "waitOneYear").returningVoid().takingNoParams().invoke();
        klass.method(debt, "printBalance").returningVoid().takingNoParams().invoke();

        String out = mio.getOutput();

        mio.close();

        double expected = 1500.0 * 2.0 * 2.0;
        assertTrue("The balance should increase when waiting a year. Expected " + expected + " .When we did\nDebt v = new Debt(1500.0, 2.0);\nv.waitOneYear();\nv.waitOneYear();\nv.printBalance();\nOutput was:\n" + out, out.contains("" + expected));
    }

    private void sanitaryCheck() throws SecurityException {
        Field[] fields = ReflectionUtils.findClass(klassName).getDeclaredFields();

        for (Field field : fields) {
            assertFalse("you do not need\"static variables\", remove " + oneField(field.toString()), field.toString().contains("static") && !field.toString().contains("final"));
            assertTrue(" All object variables of the Debt class should be private , change " + oneField(field.toString()), field.toString().contains("private"));
        }

        if (fields.length > 1) {
            int var = 0;
            for (Field field : fields) {
                if (!field.toString().contains("final")) {
                    var++;
                }
            }
            assertTrue("The class " + klassName + " needs only two object variables. Remove the extras.", var < 3);
        }
    }

    private String oneField(String toString) {
        return toString.replace(klassName + ".", "");
    }
}
