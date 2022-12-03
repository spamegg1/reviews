
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import java.lang.reflect.Method;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.*;
import static org.junit.Assert.*;

@Points("02-18")
public class SumOfASequenceTheSequelTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void test() {
        int[][] input = {{1, 2, 3}, {2, 4, 9}, {3, 6, 18}, {4, 7, 22}};

        for (int i = 0; i < input.length; i++) {
            check(input[i][0], input[i][1], input[i][2]);
        }
    }

    private void check(int first, int last, int expectedResult) {
        int oldOut = io.getSysOut().length();
        io.setSysIn(first + "\n" + last + "\n");
        callMain(SumOfASequenceTheSequel.class);
        String out = io.getSysOut().substring(oldOut);

        assertTrue("you're not printing anything!", out.length() > 0);

        int result = getLastNumber(out);

        String errorMessage = "Sum of " + first + ".." + last + " should be " + expectedResult + " but you printed \"" + out + "\"";
        assertEquals(errorMessage, expectedResult, result);
    }

    private void callMain(Class kl) {
        try {
            kl = ReflectionUtils.newInstanceOfClass(kl);
            String[] t = null;
            String x[] = new String[0];
            Method m = ReflectionUtils.requireMethod(kl, "main", x.getClass());
            ReflectionUtils.invokeMethod(Void.TYPE, m, null, (Object) x);
        } catch (NoSuchElementException e) {
            fail("Your program was trying to read too much user input, remember to use nextLine()!");
        } catch (Throwable e) {
            fail("The void main(String[] args) method of" + kl + "-class has disappeared, "
                    + "or something unexpected happened. More info: " + e);
        }
    }

    private static int getLastNumber(String inputStr) {

        String patternStr = "(?s).*?(\\d+)\\s*$";

        Matcher matcher = Pattern.compile(patternStr).matcher(inputStr);

        assertTrue("Output should be of the type \"The sum is 10\". Now you printed: " + inputStr, matcher.find());

        int number = Integer.valueOf(matcher.group(1));
        return number;
    }
}
