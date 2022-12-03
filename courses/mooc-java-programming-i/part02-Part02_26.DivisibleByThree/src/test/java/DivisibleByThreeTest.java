
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Method;
import org.junit.*;
import static org.junit.Assert.*;

@Points("02-26")
public class DivisibleByThreeTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test(timeout = 1000)
    public void test1() throws Throwable {
        int oldOut = io.getSysOut().length();
        Reflex.reflect(DivisibleByThree.class).staticMethod("divisibleByThreeInRange").returningVoid().taking(int.class, int.class).invoke(1, 13);
        String out = io.getSysOut().substring(oldOut);
        
        verifyOrderOfOutput(out, "1, 13", "3\n6\n9\n12\n", "\\s*3\\s*6\\s*9\\s*12\\s", "4", "5", "0", "7", "8", "10", "11", "15");
    }

    @Test(timeout = 1000)
    public void test2() throws Throwable {
        int oldOut = io.getSysOut().length();
        Reflex.reflect(DivisibleByThree.class).staticMethod("divisibleByThreeInRange").returningVoid().taking(int.class, int.class).invoke(9, 12);
        String out = io.getSysOut().substring(oldOut);
        
        verifyOrderOfOutput(out, "9, 12", "9\n12\n", "\\s*9\\s*12\\s*", "3", "6", "8", "13", "15");
    }

    public void test(String input, String expected, String... notExpected) {

        int oldOut = io.getSysOut().length();
        io.setSysIn(input);
        callMain(DivisibleByThree.class);
        String out = io.getSysOut().substring(oldOut);

        verifyOutput(out, input, expected, notExpected);
    }

    public void verifyOutput(String out, String input, String expected, String... notExpected) {

        assertTrue("When input was:\n" + input + ", the output was expected to be:\n" + expected + "\nNow the output was:\n" + out, out.contains(expected));
        for (String unexpected : notExpected) {
            assertFalse("When the input was:\n" + input + ", the output was expected to contain:\n" + unexpected + "", out.contains(unexpected));
        }
    }

    public void testOutputInOrder(String input, String expectationExplanation, String expectedRegex, String... notExpected) {

        int oldOut = io.getSysOut().length();
        io.setSysIn(input);
        callMain(DivisibleByThree.class);
        String out = io.getSysOut().substring(oldOut);

        verifyOrderOfOutput(out, input, expectationExplanation, expectedRegex, notExpected);
    }

    public void verifyOrderOfOutput(String output, String input, String expectationExplanation, String expectedRegex, String... notExpected) {

        assertTrue("When the input was:\n" + input + ", the output was expected to be:\n" + expectationExplanation + "\nNow the output was:\n" + output, output.matches(expectedRegex));
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
