
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import java.lang.reflect.Method;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.*;
import static org.junit.Assert.*;

@Points("03-04")
public class ListSizeTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void test() {
        String[][] inputs = {{"Thomas", "Elizabeth", "Axel", "Mary", ""}, {"Elisabeth", "Axel", "Mary", ""}, {"First", "Second", "Third", "Fourth", "Fifth", "Sixth", "Seventh", ""}};

        for (int i = 0; i < inputs.length; i++) {
            check(inputs[i]);
        }
    }

    private void check(String... strings) {
        int oldOut = io.getSysOut().length();

        String in = "";
        for (int i = 0; i < strings.length; i++) {
            in += strings[i] + "\n";
        }

        io.setSysIn(in);
        callMain(ListSize.class);
        String out = io.getSysOut().substring(oldOut);

        assertTrue("you're not printing anything!", out.length() > 0);

        int result = lastNumber(out);
        int expectedResult = strings.length - 1;

        String errorMsg = "Input:\n" + in + "\n\n Expected: \"" + expectedResult + "\", you printed: \"" + result + "\"\n";
        assertEquals(errorMsg, expectedResult, result);
    }

    private void callMain(Class kl) {
        try {
            kl = ReflectionUtils.newInstanceOfClass(kl);
            String[] t = null;
            String x[] = new String[0];
            Method m = ReflectionUtils.requireMethod(kl, "main", x.getClass());
            ReflectionUtils.invokeMethod(Void.TYPE, m, null, (Object) x);
        } catch (NoSuchElementException e) {
            fail("Your program tried to read too much input. Remember to use nextLine()-method to read!");
        } catch (Throwable e) {
            fail("Something unexpected happened. The public static void main(String[] args) method of '" + kl + "' class has disappeared \n"
            + "or something unexpected happened. More info: " + e);
        }
    }

    private static int lastNumber(String inputStr) {

        String patternStr = "(?s).*?(\\d+)\\s*$";

        Matcher matcher = Pattern.compile(patternStr).matcher(inputStr);

        assertTrue("The output should be of the type \"In total: num\", where num is the number of the input. Now you printed:\n" + inputStr, matcher.find());

        int number = Integer.valueOf(matcher.group(1));
        return number;
    }
}
