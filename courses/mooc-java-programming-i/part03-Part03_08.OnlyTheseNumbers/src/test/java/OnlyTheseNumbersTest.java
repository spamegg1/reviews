
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.*;
import static org.junit.Assert.*;

@Points("03-08")
public class OnlyTheseNumbersTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void test() {
        String[][] input = {{"3", "2", "1", "4", "7", "-1", "0", "1"}, {"3", "9", "2", "8", "-1", "2", "3"}};

        for (int i = 0; i < input.length; i++) {
            check(input[i]);
        }
    }

    private void check(String... input) {
        int oldOut = io.getSysOut().length();

        int lower = Integer.valueOf(input[input.length - 2]);
        int upper = Integer.valueOf(input[input.length - 1]);

        String in = "";
        List<Integer> numbers = new ArrayList<>();
        for (int i = 0; i < input.length; i++) {
            in += input[i] + "\n";

            if (i < input.length - 2) {
                numbers.add(Integer.valueOf(input[i]));
            }
        }

        io.setSysIn(in);
        callMain(OnlyTheseNumbers.class);
        String out = io.getSysOut().substring(oldOut);

        assertTrue("you're not printing anything!", out.length() > 0);

        for (int i = lower; i <= upper; i++) {
            if (!out.contains("" + numbers.get(i))) {
                fail("Input:\n" + in + "\nOutput was expected to contain \"" + numbers.get(i) + "\", but it wasn't printed.\noutput was:\n" + out);
            }
        }

        NEXT:
        for (int i = 0; i < 50; i++) {

            for (int index = lower; index <= upper; index++) {
                if (numbers.get(index) == i) {
                    continue NEXT;
                }
            }

            if (out.contains("" + i)) {
                fail("Input:\n" + in + "\nOutput wasn't expected to contain number\"" + i + "\", but it was printed.\nOutput was:\n" + out);
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
