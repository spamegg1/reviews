
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;
import org.junit.*;
import static org.junit.Assert.*;

@Points("03-11")
public class IndexOfSmallestTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void test() {
        int[][] inputs = {{72, 2, 8, 8, 11, 9999}, {72, 44, 8, 8, 11, 9999}, {51, 22, -11, -140, -18, 9999}, {1, 7, 9999}, {3, 2, 9999}, {-3, -2, -141, 22, 22, 7, 9999}};

        for (int i = 0; i < inputs.length; i++) {
            check(inputs[i]);
        }
    }

    private void check(int... numbers) {
        int oldOut = io.getSysOut().length();

        Set<Integer> indices = new HashSet<>();

        String in = "";
        int pienin = numbers[0];
        for (int i = 0; i < numbers.length - 1; i++) {
            in += numbers[i] + "\n";
            if (pienin > numbers[i]) {
                pienin = numbers[i];
                indices.clear();
                indices.add(i);
            }
        }

        in += "9999\n";

        io.setSysIn(in);
        callMain(IndexOfSmallest.class);
        String out = io.getSysOut().substring(oldOut);

        assertTrue("you're not printing anything!", out.length() > 0);

        assertTrue("The smallest number should be printed in the format: \"Smallest number: " + pienin + "\", where " + pienin + " is the smallest number.", out.contains("number: " + pienin));

        for (Integer index : indices) {
            assertTrue("All the indices of the smallest number should be printed. When the input was:\n" + in + "\nThe output was:\n" + out, out.contains("" + index));
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
}
