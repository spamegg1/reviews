
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import java.lang.reflect.Method;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.*;
import static org.junit.Assert.*;

@Points("03-13")
public class AverageOfAListTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void test() {
        int[][] inputs = {{5, 22, -11, -140, -18}, {1}, {3, 2, 1}, {-3, -2, -141}};

        for (int i = 0; i < inputs.length; i++) {
            check(inputs[i]);
        }
    }

    private void check(int... numbers) {
        int oldOut = io.getSysOut().length();

        String in = "";
        int sum = 0;
        for (int i = 0; i < numbers.length; i++) {
            in += numbers[i] + "\n";
            sum += numbers[i];
        }
        
        in += "-1\n";
        

        io.setSysIn(in);
        callMain(AverageOfAList.class);
        String out = io.getSysOut().substring(oldOut);

        assertTrue("you're not printing anything!", out.length() > 0);

        double result = getLastNumber(out);
        double expectedResult = (1.0 * sum / numbers.length);
        
        String errorMsg = "Input:\n" + in + "\n\n Expected: \"" + expectedResult + "\", you printed: \"" + result + "\"\n";
        assertEquals(errorMsg, expectedResult, result, 0.001);
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

    private static double getLastNumber(String inputStr) {

        String patternStr = "(?s).*?(-?\\d+\\.\\d+)\\s*$";

        Matcher matcher = Pattern.compile(patternStr).matcher(inputStr);

        assertTrue("The output should be of the type \"Average: -12.0\". Now you printed: " + inputStr, matcher.find());

        double number = Double.parseDouble(matcher.group(1));
        return number;
    }
}
