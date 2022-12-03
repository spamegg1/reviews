
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import java.lang.reflect.Method;
import java.util.NoSuchElementException;
import org.junit.*;
import static org.junit.Assert.*;

@Points("03-01")
public class ThirdElementTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void test() {
        String[][] input = {{"Tom", "Emma", "Alex", "Mary", "", "Alex"}, {"Emma", "Alex", "Mary", "", "Mary"}};

        for (int i = 0; i < input.length; i++) {
            check(input[i]);
        }
    }

    private void check(String... strings) {
        int oldOut = io.getSysOut().length();

        String in = "";
        for (int i = 0; i < strings.length - 1; i++) {
            in += strings[i] + "\n";
        }

        io.setSysIn(in);
        callMain(ThirdElement.class);
        String out = io.getSysOut().substring(oldOut);

        assertTrue("you're not printing anything!", out.length() > 0);

        String ans = getLastWord(out);
        String expectedAns = strings[strings.length - 1];

        for (int i = 0; i < strings.length - 1; i++) {
            String name = strings[i];
            if (name.equals(expectedAns)) {
                continue;
            }

            if (name.equals("")) {
                continue;
            }

            if (out.contains(name)) {
                fail("Input:\n" + in + "\n the following output was not expected \"" + name + "\", but it got printed.\nThe output was:\n" + out);
            }
        }

        String virheIlm = "Input:\n" + in + "\n\n Expected output: \"" + expectedAns + "\", you printed: \"" + ans + "\"\n";
        assertEquals(virheIlm, expectedAns, ans);
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

    private static String getLastWord(String inputStr) {
        String[] parts = inputStr.split("\\s+");
        return parts[parts.length - 1];
    }
}
