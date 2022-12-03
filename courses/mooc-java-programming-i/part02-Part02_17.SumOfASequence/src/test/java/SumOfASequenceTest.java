
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import java.lang.reflect.Method;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.*;
import static org.junit.Assert.*;

@Points("02-17")
public class SumOfASequenceTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void test() {
        int[][] input = {{3, 6}, {4, 10}, {5, 15}, {10, 55}};

        for (int i = 0; i < input.length; i++) {
            check(input[i][0], input[i][1]);

        }
    }

    private void check(int last, int expectedResult) {
        int first = 1;
        int oldOut = io.getSysOut().length();
        io.setSysIn(last + "\n");
        callMain(SumOfASequence.class);
        String out = io.getSysOut().substring(oldOut);

        int result = getLastNumber(out);

        String errorMessage = "sum of " + first + ".." + last + " should be "
                + expectedResult + ", but you printed \"" + out + "\"";
        assertTrue("you're not printing anything!", out.length() > 0);
        assertEquals(errorMessage, expectedResult, result);

        assertFalse("Hmm.. the sum should be positive, but you printed " + out, out.contains("-" + expectedResult));
    }

    private void callMain(Class kl) {
        try {
            kl = ReflectionUtils.newInstanceOfClass(kl);
            String[] t = null;
            String x[] = new String[0];
            Method m = ReflectionUtils.requireMethod(kl, "main", x.getClass());
            ReflectionUtils.invokeMethod(Void.TYPE, m, null, (Object) x);
        } catch (NoSuchElementException e) {
            fail("remember to read the input with nextLine()\n"
                    + "read the input only once");
        } catch (Throwable e) {
            fail("Something unexpected happened. The public static void main(String[] args) method of '" + kl + "' class has disappeared \n"
                    + "or your program crashed because of an exception. More info: " + e);
        }
    }

    private static int getLastNumber(String inputStr) {
        String patternStr = "(?s).*?(\\d+)\\s*$";

        Matcher matcher = Pattern.compile(patternStr).matcher(inputStr);

        assertTrue("output should be of the type \"The sum is 10\"", matcher.find());

        int number = Integer.valueOf(matcher.group(1));
        return number;
    }
}
