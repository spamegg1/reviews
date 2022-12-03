
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import java.lang.reflect.Method;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.*;
import static org.junit.Assert.*;

@Points("03-12")
public class SumOfAListTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void test() {
        int[][] inputs = {
            {3, 6, 9, 12, 15, -1},
            {1, -1},
            {9, -1},
            {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, -1}
        };

        for (int[] input : inputs) {
            check(input);
        }
    }

    private void check(int[] inputNumbers) {
        int oldOut = io.getSysOut().length();
        String input = "";
        int sum = 0;
        for (int num : inputNumbers) {
            if (num != -1) {
                sum += num;
            }

            input += num + "\n";
        }

        io.setSysIn(input);
        callMain(SumOfAList.class);
        String out = io.getSysOut().substring(oldOut);

        int result = getLastNumber(out);

        input = input.replaceAll("\n", " ").trim();
        input = input.replaceAll(" ", " + ").trim();
        String errorMsg = "the sum " + input + " should be " + sum + " you printed \"" + result + "\"";
        assertTrue("you're not printing anything!", out.length() > 0);
        assertEquals(errorMsg, sum, result);
    }

    private void callMain(Class kl) {
        try {
            kl = ReflectionUtils.newInstanceOfClass(kl);
            String x[] = new String[0];
            Method m = ReflectionUtils.requireMethod(kl, "main", x.getClass());
            ReflectionUtils.invokeMethod(Void.TYPE, m, null, (Object) x);
        } catch (NoSuchElementException e) {
            fail("Make sure the prompting stops when the user gives a zero.");
        } catch (Throwable e) {
            fail(kl + " class public static void main(String[] args) -method has disappeared!");
        }
    }

    private static int getLastNumber(String inputStr) {
        String patternStr = "(?s).*?(\\d+)\\s*$";

        Matcher matcher = Pattern.compile(patternStr).matcher(inputStr);

        assertTrue("The last line of the output should be of the type \"Sum: 10\"", matcher.find());

        int number = Integer.valueOf(matcher.group(1));
        return number;
    }
}
