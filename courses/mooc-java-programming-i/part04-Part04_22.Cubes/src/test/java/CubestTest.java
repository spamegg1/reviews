
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.*;
import static org.junit.Assert.*;

@Points("04-22")
public class CubestTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void test() {
        String[][] inputs = {{"8", "3", "123", "end"}, {"9", "end"}, {"16", "32", "end"}};

        for (int i = 0; i < inputs.length; i++) {
            check(inputs[i]);
        }
    }

    private void check(String... input) {
        int oldOut = io.getSysOut().length();

        List<Integer> expected = new ArrayList<>();
        String in = "";
        for (int i = 0; i < input.length; i++) {
            try {
                int number = Integer.valueOf(input[i]);
                expected.add(number * number * number);
            } catch (Exception e) {

            }

            in += input[i] + "\n";
        }

        io.setSysIn(in);
        callMain(Cubes.class);
        String out = io.getSysOut().substring(oldOut);

        assertTrue("When the input is:\n" + in + "\nyou are not printing anything!", out.length() > 0);

        String[] prints = takePrints(out);
        for (String print : prints) {
            int number = -1;
            try {
                number = Integer.valueOf(print);
            } catch (NumberFormatException e) {
                continue;
            }

            if (!expected.contains(number)) {
                fail("Input:\n" + in + "\nWas not expecting \"" + number + "\" to be printed, but it was.\nThe output was:\n" + out);
            }

            expected.remove(Integer.valueOf(number));
        }

        if (!expected.isEmpty()) {
            for (Integer expectedNumber : expected) {
                fail("Input:\n" + in + "\n\n Expected number: \"" + expectedNumber + "\", the output was: \"" + out + "\"\n");
            }

        }
    }

    private void callMain(Class kl) {
        try {
            kl = ReflectionUtils.newInstanceOfClass(kl);
            String[] t = null;
            String x[] = new String[0];
            Method m = ReflectionUtils.requireMethod(kl, "main", x.getClass());
            ReflectionUtils.invokeMethod(Void.TYPE, m, null, (Object) x);
        } catch (NoSuchElementException e) {
            fail("Your program tried to read too much input. Be sure to use the nextLine() method to read input!");
        } catch (Throwable e) {
            fail("public static void main(String[] args) method of the " + kl + " class has disappeared "
                    + "or something else unexpected occurred, more information: " + e);
        }
    }

    private static String[] takePrints(String str) {
        return str.split("\\s+");
    }
}
