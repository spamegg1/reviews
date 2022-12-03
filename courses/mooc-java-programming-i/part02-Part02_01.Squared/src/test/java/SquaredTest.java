
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import java.lang.reflect.Method;
import org.junit.*;
import static org.junit.Assert.*;

@Points("02-01")
public class SquaredTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void fourSquared() {
        test("4\n", "16", "36");
    }

    @Test
    public void sixSquared() {
        test("6\n", "36", "16");
    }

    public void test(String input, String expected, String... unexpected) {

        int oldOut = io.getSysOut().length();
        io.setSysIn(input);
        callMain(Squared.class);
        String out = io.getSysOut().substring(oldOut);

        assertTrue("When the input was " + input + ", the expected output was:\n" + expected + "\nThe output could not be found.", out.contains(expected));
        for (String notExpected : unexpected) {
            assertFalse("When the input was  " + input + ", the output should not contain:\n" + notExpected + "", out.contains(notExpected));
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
            fail("Something strange happened. It may be that " + kl + "-class's public static void main(String[] args) -method is missing \n"
                    + "or your program crashed due to an exception. More information " + e);
        }
    }

}
