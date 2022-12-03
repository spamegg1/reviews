
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Method;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Rule;
import org.junit.Test;

@Points("02-25")
public class DivisionTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test(timeout = 1000)
    public void test1() throws Throwable {
        int oldOut = io.getSysOut().length();
        Reflex.reflect(Division.class).staticMethod("division").returningVoid().taking(int.class, int.class).invoke(2, 1);
        String out = io.getSysOut().substring(oldOut);
        checkOutput(out, "2, 1", "2.0", "0.5", "1.0", "3.5");
    }

    @Test(timeout = 1000)
    public void test2() throws Throwable {
        int oldOut = io.getSysOut().length();
        Reflex.reflect(Division.class).staticMethod("division").returningVoid().taking(int.class, int.class).invoke(7, 2);
        String out = io.getSysOut().substring(oldOut);
        checkOutput(out, "7, 2", "3.5", "3.0", "4.0", "2.0");
    }


    public void checkOutput(String out, String input, String expected, String... notExpected) {

        assertTrue("When the input was:\n" + input + ", output was expected to be:\n" + expected + "\nNow output was:\n" + out, out.contains(expected));
        for (String unexpected : notExpected) {
            assertFalse("When the input was:\n" + input + ", output was not expected to contain:\n" + unexpected + "", out.contains(unexpected));
        }
    }

    private void callMain(Class kl) {
        try {
            kl = ReflectionUtils.newInstanceOfClass(kl);
            String[] t = null;
            String x[] = new String[0];
            Method m = ReflectionUtils.requireMethod(kl, "main", x.getClass());
            ReflectionUtils.invokeMethod(Void.TYPE, m, null, (Object) x);
        } catch (Throwable e) {
            fail("Something unexpected happened. The public static void main(String[] args) method of '" + kl + "' class has disappeared \n"
                    + "or your program crashed because of an exception. More info: " + e);
        }
    }
}
