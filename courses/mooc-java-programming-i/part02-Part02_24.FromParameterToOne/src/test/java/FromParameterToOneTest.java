
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Method;
import org.junit.*;
import static org.junit.Assert.*;

@Points("02-24")
public class FromParameterToOneTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test(timeout = 1000)
    public void test1() throws Throwable {
        int oldOut = io.getSysOut().length();
        Reflex.reflect(FromParameterToOne.class).staticMethod("printFromNumberToOne").returningVoid().taking(int.class).invoke(2);
        String out = io.getSysOut().substring(oldOut);
        verifyOutputOrder(out, "2\n", "2\n1\n", "\\s*2\\s*1\\s*", "0", "3");
    }

    @Test(timeout = 1000)
    public void test2() throws Throwable {
        int oldOut = io.getSysOut().length();
        Reflex.reflect(FromParameterToOne.class).staticMethod("printFromNumberToOne").returningVoid().taking(int.class).invoke(5);
        String out = io.getSysOut().substring(oldOut);
        verifyOutputOrder(out, "6\n", "5\n4\n3\n2\n1\n", "\\s*5\\s*4\\s*3\\s*2\\s*1\\s*", "0", "6");
    }

    public void test(String input, String expected, String... notExpected) {

        int oldOut = io.getSysOut().length();
        io.setSysIn(input);
        callMain(FromParameterToOne.class);
        String out = io.getSysOut().substring(oldOut);

        assertTrue("When the input was:\n" + input + ", the output was expected to be:\n" + expected + "\nNow the output was:\n" + out, out.contains(expected));
        for (String unexpected : notExpected) {
            assertFalse("When the input was:\n" + input + ", the output was not expected to contain:\n" + unexpected + "", out.contains(unexpected));
        }
    }

    public void testOutputOrder(String input, String expectationExplanation, String expectedRegex, String... notExpected) {

        int oldOut = io.getSysOut().length();
        io.setSysIn(input);
        callMain(FromParameterToOne.class);
        String out = io.getSysOut().substring(oldOut);

        assertTrue("When the input was:\n" + input + ", the output was expected to be:\n" + expectationExplanation + "\nNow the output was:\n" + out, out.matches(expectedRegex));
        for (String unexpected : notExpected) {
            assertFalse("When the input was:\n" + input + ", the output was not expected to contain:\n" + unexpected + "", out.contains(unexpected));
        }
    }

    public void verifyOutputOrder(String output, String input, String explanation, String odotettuRegex, String... notExpected) {

        assertTrue("When the input was:\n" + input + ", the output was expected to be:\n" + explanation + "\nNow the output was:\n" + output, output.matches(odotettuRegex));
        for (String unexpected : notExpected) {
            assertFalse("When the input was:\n" + input + ", the output was not expected to contain:\n" + unexpected + "", output.contains(unexpected));
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
