
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import java.lang.reflect.Method;
import java.util.Arrays;
import org.junit.*;
import static org.junit.Assert.*;

@Points("02-12")
public class AverageOfNumbersTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test(timeout = 1000)
    public void test1() {
        test(-5, 4, -3, 1, 0);
    }

    @Test(timeout = 1000)
    public void test2() {
        test(-2, 0);
    }

    @Test(timeout = 1000)
    public void test3() {
        test(-2, -3, -1, -4, -5, 0);
    }

    @Test(timeout = 1000)
    public void test4() {
        test(1, 3, 2, 3, 0);
    }

    public void test(int... numbers) {
        int oldOut = io.getSysOut().length();

        String input = "";
        for (int i = 0; i < numbers.length; i++) {
            input += numbers[i] + "\n";
        }

        io.setSysIn(input);
        callMain(AverageOfNumbers.class);
        String out = io.getSysOut().substring(oldOut);

        int inputCount = out.trim().split("Give a number").length - 1;
        int inputExpected = input.split("\n").length;

        assertEquals("When input was:\n" + input + "\n\"Give a number\"-text should appear " + inputExpected + " times. Now it appeared " + inputCount + " times.", inputExpected, inputCount);

        double average = Arrays.stream(numbers).filter(num -> num != 0).average().getAsDouble();

        String expected = "Average of the numbers: " + average;
        assertTrue("When input was:\n" + input + "\nThe output was expected to be \"" + expected + "\".\nNow the output was" + out, out.contains(expected));

        assertTrue("Text \"Average og the numbers \" should only be printed once. Now it appeared " + (out.trim().split("Average of the numbers: ").length - 1) + " times.", out.trim().split("Average of the numbers: ").length - 1 == 1);
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
