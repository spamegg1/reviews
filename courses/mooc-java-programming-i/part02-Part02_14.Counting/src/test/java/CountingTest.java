
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import java.lang.reflect.Method;
import org.junit.*;
import static org.junit.Assert.*;

@Points("02-14")
public class CountingTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test(timeout = 1000)
    public void test1() {
        verifyOrderOfOutput("1\n", "0\n1\n", "\\s*0\\s*1\\s*", "-1", "2");
    }

    @Test(timeout = 1000)
    public void test2() {
        verifyOrderOfOutput("5\n", "0\n1\n2\n3\n4\n5\n", "\\s*0\\s*1\\s*2\\s*3\\s*4\\s*5\\s*", "-1", "6");
    }

    public void test(String input, String expected, String... notExpected) {

        int oldOut = io.getSysOut().length();
        io.setSysIn(input);
        callMain(Counting.class);
        String out = io.getSysOut().substring(oldOut);

        assertTrue("When input was:\n" + input + ", the following output was expected:\n" + expected + "\nNow the output was:\n" + out, out.contains(expected));
        for (String unexpected : notExpected) {
            assertFalse("When input was:\n" + input + ", the output was not expected to contain:\n" + unexpected + "", out.contains(unexpected));
        }
    }

    public void verifyOrderOfOutput(String input, String expectationExplanation, String expectedRegex, String... notExpected) {

        int oldOut = io.getSysOut().length();
        io.setSysIn(input);
        callMain(Counting.class);
        String out = io.getSysOut().substring(oldOut);

        assertTrue("When input was:\n" + input + ", the following output was expected:\n" + expectationExplanation + "\nNow the output was:\n" + out, out.matches(expectedRegex));
        for (String unexpected : notExpected) {
            assertFalse("When input was:\n" + input + ", output wasn't expected to contain:\n" + unexpected + "", out.contains(unexpected));
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
