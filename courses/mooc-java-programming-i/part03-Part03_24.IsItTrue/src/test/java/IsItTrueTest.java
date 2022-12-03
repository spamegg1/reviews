
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import java.lang.reflect.Method;
import java.util.NoSuchElementException;
import org.junit.*;
import static org.junit.Assert.*;

@Points("03-24")
public class IsItTrueTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void correctOnesGo() {
        String word = "true";

        int oldOut = io.getSysOut().length();
        io.setSysIn(word + "\n");
        callMain(IsItTrue.class);
        String out = io.getSysOut().substring(oldOut);

        assertTrue("you're not printing anything!", out.length() > 0);

        assertTrue("When the input was: \"" + word + "\" you printed \"" + out + "\" while the output should have been \"You got it right!\". Remember that you can't compare strings with == but you need to use equals!", out.toLowerCase().contains("ight"));
        assertTrue("When the input was: \"" + word + "\" you printed \"" + out + "\" while the output should have been \"You got it right!\". Remember that you can't compare strings with == but you need to use equals!", !(out.toLowerCase().contains("ry") && out.toLowerCase().contains("gain")));
    }

    @Test
    public void unsuitableOnedDontGo() {
        String[] words = {
            "secret",
            "potty",
            "tha-trueth",
            "trueish",
            "ahaa"
        };

        for (String word : words) {
            notPassing(word);
        }
    }

    private void notPassing(String word) {
        int oldOut = io.getSysOut().length();
        io.setSysIn(word + "\n");
        callMain(IsItTrue.class);
        String out = io.getSysOut().substring(oldOut);

        assertTrue("you're not printing anything!", out.length() > 0);

        assertTrue("When the input was: \"" + word + "\" you printed \"" + out + "\" while the output should have been \"Try again!\". Remember that you can't compare strings with == but you need to use equals!", !out.toLowerCase().contains("ight"));
        assertTrue("When the input was: \"" + word + "\" you printed \"" + out + "\" while the output should have been \"Try again!\". Remember that you can't compare strings with == but you need to use equals!", (out.toLowerCase().contains("ry") || out.toLowerCase().contains("gain")));
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
            fail("Something unexpected happened. The public static void main(String[] args) method of " + kl + "-class has disappeared \n"
            + "or something unexpected happened. More info: " + e);
        }
    }
}
