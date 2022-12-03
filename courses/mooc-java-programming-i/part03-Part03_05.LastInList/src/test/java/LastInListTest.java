
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import java.lang.reflect.Method;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.*;
import static org.junit.Assert.*;

@Points("03-05")
public class LastInListTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void test() {
        String[][] input = {{"Thomas", "Elizabeth", "Axel", "Mary", "", "Mary"}, {"Elizabeth", "Mary", "Axel", "", "Axel"}, {"First", "Second", "Third", "Fourth", "Fifth", "Sixth", "Seventh", "", "Seventh"}};

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
        callMain(LastInList.class);
        String out = io.getSysOut().substring(oldOut);

        assertTrue("you're not printing anything!", out.length() > 0);

        String[] result = getLastWords(out);

        String lastWord = strings[strings.length - 1];

        for (int i = 0; i < strings.length - 1; i++) {
            String name = strings[i];
            if (name.equals(lastWord)) {
                continue;
            }

            if (name.equals("")) {
                continue;
            }

            if (out.contains(name)) {
                fail("Input:\n" + in + "\noutput was not expected to be \"" + name + "\".\nOutput was:\n" + out);
            }
        }

        String errorMsg = "Input:\n" + in + "\n\n Expected output:\n" + lastWord + "\nyou printed: \"" + result + "\"\n";
        assertEquals(errorMsg, lastWord, result[0]);
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
        String[] palat = inputStr.split("\\s+");
        return new String[]{palat[palat.length - 1]};
    }
}
