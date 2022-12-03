
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import java.lang.reflect.Method;
import org.junit.*;
import static org.junit.Assert.*;

@Points("02-15")
public class CountingToHundredTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test(timeout = 1000)
    public void test1() {
        verifyOrderOfOutput("99\n", "99\n100\n", "\\s*99\\s*100\\s*", "98", "101");
    }

    @Test(timeout = 1000)
    public void test2() {
        verifyOrderOfOutput("-3\n", "3-\n-2\n...many numbers...\n98\n99\n100\n", "\\s*-3\\s*-2\\s*-1\\s*[0-98\\s]*\\s*99\\s*100\\s*", "-4", "101");
    }

    public void test(String input, String expected, String... notExpected) {

        int oldOut = io.getSysOut().length();
        io.setSysIn(input);
        callMain(CountingToHundredTest.class);
        String out = io.getSysOut().substring(oldOut);

        assertTrue("When input was:\n" + input + ", the following output was expected:\n" + expected + "\nNow the output was:\n" + out, out.contains(expected));
        for (String unexpected : notExpected) {
            assertFalse("When input was:\n" + input + ", the output wasn't expected to contain:\n" + unexpected + "", out.contains(unexpected));
        }
    }

    public void verifyOrderOfOutput(String input, String expectationExplanation, String expectedRegex, String... notExpected) {

        int oldOut = io.getSysOut().length();
        io.setSysIn(input);
        callMain(CountingToHundred.class);
        String out = io.getSysOut().substring(oldOut);

        assertTrue("When input was:\n" + input + ", the expected output was:\n" + expectationExplanation + "\nNow the output was:\n" + out, out.matches(expectedRegex));
        for (String unexpected : notExpected) {
            assertFalse("When input was:\n" + input + ", the output wasn't expected to contain:\n" + unexpected + "", out.contains(unexpected));
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
