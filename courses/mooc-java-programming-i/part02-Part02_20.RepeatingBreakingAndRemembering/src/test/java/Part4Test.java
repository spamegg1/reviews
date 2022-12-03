import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import java.lang.reflect.Method;
import java.util.NoSuchElementException;
import org.junit.*;
import static org.junit.Assert.*;

@Points("02-20.4")
public class Part4Test {
    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void test() {
        int[][] input = {
            {1, -1, 1},
            {2, 6, -1, 4},
            {2, 6, 5, 7, -1, 5}, 
            {6, 1, 4, 7, 4, 8, -1, 5}
        };

        for (int i = 0; i < input.length; i++) {
            check(input[i], "");
        }
    }

    @Test
    public void test2() {
        int[] input = {2, 5, -1, 0};
        int oldOut = io.getSysOut().length();
        io.setSysIn(stringify(input));
        callMain(RepeatingBreakingAndRemembering.class);
        String out = io.getSysOut().substring(oldOut);

        String errorMsg = "With the input " + stringifyInBetween(input)
                + " the output should be \"Average: 3.5\", but you printed " +
                line(out, "verage");
        assertTrue("you're not printing anything!", out.length() > 0);
        assertTrue(errorMsg, out.contains("3.5"));
    }

    private void check(int[] input, String str) {
        int oldOut = io.getSysOut().length();
        io.setSysIn(stringify(input));
        callMain(RepeatingBreakingAndRemembering.class);
        String out = io.getSysOut().substring(oldOut);
        int expected = result(input);

        String errorMsg = "With the input " + stringifyInBetween(input)
                + " the output should be \"" + str + ": " + expected +
                ".0\", but you printed " + line(out, "verage");
        assertTrue("you're not printing anything!", out.length() > 0);
        assertTrue(errorMsg, out.contains(expected+".0"));
    }

    private void callMain(Class kl) {
        try {
            kl = ReflectionUtils.newInstanceOfClass(kl);
            String[] t = null;
            String x[] = new String[0];
            Method m = ReflectionUtils.requireMethod(kl, "main", x.getClass());
            ReflectionUtils.invokeMethod(Void.TYPE, m, null, (Object) x);
        } catch (NoSuchElementException e) {
            fail("remember to quit when the user gives -1");
        } catch (Throwable e) {
            fail("unexpected error, are you sure you aren't dividing by zero?");
        }
    }

    private String stringify(int[] array) {
        String str = "";
        for (int i = 0; i < array.length - 1; i++) {
            str += array[i] + "\n";

        }

        return str;
    }

    private String stringifyInBetween(int[] array) {
        String str = "";
        for (int i = 0; i < array.length - 1; i++) {
            str += array[i] + " ";
        }

        return str;
    }

    private int result(int[] input) {
        return input[input.length - 1];
    }

    private String line(String out, String str) {
        for (String line : out.split("\n")) {
            if (line.toLowerCase().contains(str.toLowerCase())) {
                return line;
            }
        }

        fail("Your program should print the average of the numbers");
        return "";
    }
}
