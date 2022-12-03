
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import java.lang.reflect.Method;
import org.junit.*;
import static org.junit.Assert.*;

@Points("02-03")
public class AbsoluteValueTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void absoluteValueOfNegativeThree() {
        test("-3\n", "3", "-3", "1");
    }

    @Test
    public void absoluteValueOfSix() {
        test("6\n", "6", "-6", "-3", "3");
    }

    public void test(String input, String expected, String... unexpected) {

        int oldOut = io.getSysOut().length();
        io.setSysIn(input);
        callMain(AbsoluteValue.class);
        String out = io.getSysOut().substring(oldOut);

        assertTrue("When the input was " + input + ", the expected output was:\n" + expected + "\nThe output could not be found.", out.contains(expected));
        for (String eiOdotettu : unexpected) {
            assertFalse("When the input was " + input + ", the output should not contain:\n" + eiOdotettu + "", out.contains(eiOdotettu));
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
            fail("Something strange happened. It may be that " + kl + "-class's public static void main(String[] args) -method is missing\n"
                    + "or your program crashed due to an exception. More information " + e);
        }
    }
}
