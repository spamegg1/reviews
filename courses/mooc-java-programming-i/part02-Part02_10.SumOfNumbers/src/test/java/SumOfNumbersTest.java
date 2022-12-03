
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import java.lang.reflect.Method;
import java.util.Arrays;
import org.junit.*;
import static org.junit.Assert.*;

@Points("02-10")
public class SumOfNumbersTest {

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
        callMain(SumOfNumbers.class);
        String out = io.getSysOut().substring(oldOut);

        int prompt = out.trim().split("Give a number:").length - 1;
        int promptExpected = input.split("\n").length;

        assertEquals("When the input was:\n" + input + "\n\"Give a number:\" text should appear a total of " + promptExpected + " times. Now the count was  " + prompt, promptExpected, prompt);

        int sum = (int) Arrays.stream(numbers).sum();

        String expected = "Sum of the numbers: " + sum;
        assertTrue("When the input was:\n" + input + "\nThe print should have contained \"" + expected + "\".\nNow the print was" + out, out.contains(expected));

        for (int i = 0; i < 100; i++) {
            if (i == sum) {
                continue;
            }

            String notExpected = "Sum of the numbers: " + i;
            assertFalse("When the input was:\n" + input + "\nThe program should not have the input \"" + notExpected + "\".\nNow the print was" + out, out.contains(notExpected));
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
      fail("Something strange happened. It may be that " + kl + " class's 'public static void main(String[] args)' method is missing \n"
                    + "or your program crashed due to an exception. More information " + e);
        }
    }

}
