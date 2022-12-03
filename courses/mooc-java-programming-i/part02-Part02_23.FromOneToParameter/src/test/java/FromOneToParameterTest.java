
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Method;
import org.junit.*;
import static org.junit.Assert.*;

@Points("02-23")
public class FromOneToParameterTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test(timeout = 1000)
    public void test1() throws Throwable {
        int oldOut = io.getSysOut().length();
        Reflex.reflect(FromOneToParameter.class).staticMethod("printUntilNumber").returningVoid().taking(int.class).invoke(2);
        String out = io.getSysOut().substring(oldOut);
        checkOutputOrder(out, "2\n", "1\n2\n", "\\s*1\\s*2\\s*", "0", "3");
    }

    @Test(timeout = 1000)
    public void test2() throws Throwable {
        int oldOut = io.getSysOut().length();
        Reflex.reflect(FromOneToParameter.class).staticMethod("printUntilNumber").returningVoid().taking(int.class).invoke(5);
        String out = io.getSysOut().substring(oldOut);
        checkOutputOrder(out, "6\n", "1\n2\n3\n4\n5\n", "\\s*1\\s*2\\s*3\\s*4\\s*5\\s*", "0", "6");
    }

    public void test(String input, String expected, String... notExpected) {

        int oldOut = io.getSysOut().length();
        io.setSysIn(input);
        callMain(FromOneToParameter.class);
        String out = io.getSysOut().substring(oldOut);

        assertTrue("When the input was:\n" + input + ", expected output was:\n" + expected + "\nNow the output was:\n" + out, out.contains(expected));
        for (String unexpected : notExpected) {
            assertFalse("When the input was:\n" + input + ", the output shouldn't contain:\n" + unexpected + "", out.contains(unexpected));
        }
    }

    public void testPrintingInOrder(String input, String expectationExplanation, String expectedRegex, String... notExpected) {

        int oldOut = io.getSysOut().length();
        io.setSysIn(input);
        callMain(FromOneToParameter.class);
        String out = io.getSysOut().substring(oldOut);

        assertTrue("When the input was:\n" + input + ", the output was expected to be:\n" + expectationExplanation + "\nNow the output was:\n" + out, out.matches(expectedRegex));
        for (String unexpected : notExpected) {
            assertFalse("When the input was:\n" + input + ", the output wasn't expectd to be:\n" + unexpected + "", out.contains(unexpected));
        }
    }

    public void checkOutputOrder(String output, String input, String expectationExplanation, String expectedRegex, String... notExpected) {

        assertTrue("When the input was:\n" + input + ", the output was expectd to be:\n" + expectationExplanation + "\nNow the output was:\n" + output, output.matches(expectedRegex));
        for (String unexpected : notExpected) {
            assertFalse("When the input was:\n" + input + ", the output wasn't expected to contain:\n" + unexpected + "", output.contains(unexpected));
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
