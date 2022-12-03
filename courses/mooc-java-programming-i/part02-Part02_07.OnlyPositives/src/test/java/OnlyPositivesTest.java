import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.*;
import static org.junit.Assert.*;

@Points("02-07")
public class OnlyPositivesTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test(timeout = 1000)
    public void test1() {
        test(5, 4, -3, 1, 0);
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
        callMain(OnlyPositives.class);
        String out = io.getSysOut().substring(oldOut);

        int promptCount = out.trim().split("Give a number:*").length;
        int promtExpected = input.split("\n").length;

        assertEquals("When input was:\n" + input + "\n\"Give a number\" prompt was expected to appear " + promtExpected + " times. Now it appeared " + promptCount + " times.", promtExpected, promptCount);

        int unsuitableCount = out.trim().split("Unsuitable number").length - 1;
        int unsuitableExpected = (int) Arrays.stream(numbers).filter(number -> number < 0).count();

        assertEquals("When the input was:\n" + input + "\n\"Unsuitable number\" text should appear " + unsuitableExpected + " times. Now it appeared " + unsuitableCount + " times.", unsuitableExpected, unsuitableCount);

        List<Integer> numbersNotThere = new ArrayList<>(IntStream.range(2, 10).mapToObj(i -> i * i).collect(Collectors.toList()));

        for (int i = 0; i < numbers.length; i++) {
            if (numbers[i] <= 0) {
                continue;
            }

            int mult = numbers[i] * numbers[i];

            numbersNotThere.remove(new Integer(mult));

            assertTrue("When the input was:\n" + input + "\nThe output should have " + mult + ". Now it didn't. Output:\n" + out, out.contains("" + mult));
        }

        for (Integer number : numbersNotThere) {
            assertFalse("When the input was:\n" + input + "\nThe output should not contain " + number + ". Now it did. Output:\n" + out, out.contains("" + number));
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
            fail("Something strange happened. It may be that '" + kl + "' class's public static void main(String[] args) method is missing \n"
                    + "or your program crashed due to an exception. More information " + e);
        }
    }

}
