
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.*;
import static org.junit.Assert.*;

@Points("03-07")
public class RememberTheseNumbersTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void test() {
        String[][] inputs = {{"3", "2", "1", "4", "7", "-1"}, {"3", "9", "2", "8", "-1"}};

        for (int i = 0; i < inputs.length; i++) {
            check(inputs[i]);
        }
    }

    private void check(String... input) {
        int oldOut = io.getSysOut().length();

        String in = "";
        List<Integer> numbers = new ArrayList<>();
        for (int i = 0; i < input.length; i++) {
            in += input[i] + "\n";
            numbers.add(Integer.valueOf(input[i]));
        }

        io.setSysIn(in);
        callMain(RememberTheseNumbers.class);
        String out = io.getSysOut().substring(oldOut);

        assertTrue("you're not printing anything!", out.length() > 0);

        for (int i = 0; i < 50; i++) {
            if (numbers.contains(i) && !out.contains("" + i)) {
                fail("Input:\n" + in + "\nThis number was not expected: \"" + i + "\", but it got printed.\nOutput was:\n" + out);
            }

            if (!numbers.contains(i) && out.contains("" + i)) {
                fail("Input:\n" + in + "\nThis number was not expected: \"" + i + "\", but it got printed.\nOutput was:\n" + out);
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
            fail("Your program tried to read too much input. Remember to use nextLine() method to read!");
        } catch (Throwable e) {
            fail("Something unexpected happened. The public static void main(String[] args) method of '" + kl + "' class has disappeared \n"
            + "or something unexpected happened. More info: " + e);
        }
    }

    private static String[] getLastWords(String inputStr) {
        String[] parts = inputStr.split("\\s+");
        return new String[]{parts[parts.length - 2], parts[parts.length - 1]};
    }
}
