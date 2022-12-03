
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import java.lang.reflect.Method;
import java.util.Arrays;
import org.junit.*;
import static org.junit.Assert.*;

@Points("02-09")
public class NumberOfNegativeNumbersTest {

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
        test(1, 6, 4, 5, 0);
    }

    public void test(int... numbers) {
        int oldOut = io.getSysOut().length();

        String input = "";
        for (int i = 0; i < numbers.length; i++) {
            input += numbers[i] + "\n";
        }

        io.setSysIn(input);
        callMain(NumberOfNegativeNumbers.class);
        String out = io.getSysOut().substring(oldOut);

        int promptCount = out.trim().split("Give a number:").length - 1;
        int promtExpected = input.split("\n").length;

        assertEquals("When input was:\n" + input + "\n\"Give a number\" text was expected to appear " + promtExpected + " times. Now it appeared " + promptCount + " times.", promtExpected, promptCount);

        int negatives = (int) Arrays.stream(numbers).filter(num -> num < 0).count();
        
        String expected = "Number of negative numbers: " + negatives;
        assertTrue("When input was:\n" + input + "\nProgram should have contained \"" + expected + "\". Now the output was " + out, out.contains(expected));

        for (int i = 0; i < 10; i++) {
            if (i == negatives) {
                continue;
            }

            String notExpected = "Number of negative numbers: " + i;
            assertFalse("When input was:\n" + input + "\nThe program should not have contained \"" + notExpected + "\". Now the output was " + out, out.contains(notExpected));
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
      fail("Something strange happened. It may be that '" + kl + "'class's public static void main(String[] args) method is missing \n"
                    + "or your program crashed due to an exception. More information " + e);
        }
    }

}
