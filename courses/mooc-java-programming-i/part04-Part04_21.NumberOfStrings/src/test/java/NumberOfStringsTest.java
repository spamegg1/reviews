
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import java.lang.reflect.Method;
import java.util.NoSuchElementException;
import org.junit.*;
import static org.junit.Assert.*;

@Points("04-21")
public class NumberOfStringsTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void test() {
        String[][] inputs = {{"Tony", "Bella", "Alexios", "Mary", "end",  "4"}, {"Bella", "Alexios", "Mary", "end", "3"}};

        for (int i = 0; i < inputs.length; i++) {
            check(inputs[i]);
        }
    }

    private void check(String... strings) {
        int oldOut = io.getSysOut().length();

        String in = "";
        for (int i = 0; i < strings.length - 1; i++) {
            in += strings[i] + "\n";
        }

        io.setSysIn(in);
        callMain(NumberOfStrings.class);
        String out = io.getSysOut().substring(oldOut);

        assertTrue("you aren't printing anything!", out.length() > 0);

        String response = takeFromEnd(out);
        String expectedResponse = strings[strings.length - 1];

        for (int i = 0; i < strings.length - 1; i++) {
            String name = strings[i];
            if (name.equals(expectedResponse)) {
                continue;
            }

            if (name.equals("")) {
                continue;
            }

            if (out.contains(name)) {
                fail("Input:\n" + in + "\nWasn't expecting \"" + name + "\" to be printed, bnut it was.\nThe whole output was:\n" + out);
            }
        }

        String errorMessage = "Input:\n" + in + "\n\n Expected: \"" + expectedResponse + "\", you printed: \"" + response + "\"\n";
        assertEquals(errorMessage, expectedResponse, response);
    }

    private void callMain(Class kl) {
        try {
            kl = ReflectionUtils.newInstanceOfClass(kl);
            String[] t = null;
            String x[] = new String[0];
            Method m = ReflectionUtils.requireMethod(kl, "main", x.getClass());
            ReflectionUtils.invokeMethod(Void.TYPE, m, null, (Object) x);
        } catch (NoSuchElementException e) {
            fail("Your program tried to read too much input. Be sure to use the nextLine() method for reading input!");
        } catch (Throwable e) {
            fail("public static void main(String[] args) method of the " + kl + " class has disappeared "
                    + "or something else unexpected occurred, more information " + e);
        }
    }

    private static String takeFromEnd(String inputStr) {
        String[] pieces = inputStr.split("\\s+");
        return pieces[pieces.length - 1];
    }
}
