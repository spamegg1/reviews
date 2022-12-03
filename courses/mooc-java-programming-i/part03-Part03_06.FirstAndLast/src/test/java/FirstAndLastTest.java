
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import java.lang.reflect.Method;
import java.util.NoSuchElementException;
import org.junit.*;
import static org.junit.Assert.*;

@Points("03-06")
public class FirstAndLastTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void test() {
        String[][] inputs = {{"Thomas", "Elizabeth", "Axel", "Mary", "", "Thomas", "Mary"}, {"Elizabeth", "Axel", "Mary", "", "Elizabeth", "Mary"}, {"First", "Second", "Third", "Fourth", "Fifth", "Sixth", "Seventh", "", "First", "Seventh"}};

        for (int i = 0; i < inputs.length; i++) {
            check(inputs[i]);
        }
    }

    private void check(String... strings) {
        int oldOut = io.getSysOut().length();

        String in = "";
        for (int i = 0; i < strings.length - 2; i++) {
            in += strings[i] + "\n";
        }

        io.setSysIn(in);
        callMain(FirstAndLast.class);
        String out = io.getSysOut().substring(oldOut);

        assertTrue("you're not printing anything!", out.length() > 0);

        String[] result = getLastWords(out);

        String expected1 = strings[strings.length - 2];
        String expected2 = strings[strings.length - 1];

        for (int i = 0; i < strings.length - 1; i++) {
            String name = strings[i];
            if (name.equals(expected1) || name.equals(expected2)) {
                continue;
            }

            if (name.equals("")) {
                continue;
            }

            if (out.contains(name)) {
                fail("Input:\n" + in + "\nThe output was not expected to be \"" + name + "\".\noutput was:\n" + out);
            }
        }

        String virheIlm = "Input:\n" + in + "\n\n Expected:\n" + expected1 + "\n" + expected2 + "\nyou printed: \"" + result + "\"\n";
        assertEquals(virheIlm, expected1, result[0]);
        assertEquals(virheIlm, expected2, result[1]);
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
