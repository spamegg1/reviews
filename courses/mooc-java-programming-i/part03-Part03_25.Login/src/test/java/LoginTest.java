
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import java.lang.reflect.Method;
import java.util.NoSuchElementException;
import org.junit.*;
import static org.junit.Assert.*;

@Points("03-25")
public class LoginTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void CorrectOnesPass() {
        String[][] loginDetails = {{"alex", "sunshine"}, {"emma", "haskell"}};

        for (String[] pair : loginDetails) {
            passing(pair[0], pair[1]);
        }
    }

    @Test
    public void incorrectOnesNotPassing() {
        String[][] loginDetails = {
            {"arto", "secret"},
            {"alex", ""},
            {"alex", "rainstorm"},
            {"Elina", "haskell"},
            {"emma", "pascal"},
            {"", "haskell"}
        };

        for (String[] pair : loginDetails) {
            notPassing(pair[0], pair[1]);
        }
    }

    private void passing(String k, String s) {
        int oldOut = io.getSysOut().length();
        io.setSysIn(k + "\n" + s + "\n");
        callMain(Login.class);
        String out = io.getSysOut().substring(oldOut);

        assertTrue("you're not printing anything!", out.length() > 0);

        assertTrue("With username: \"" + k + "\" password: \"" + s + "\" you printed \"" + out + "\" while the output should have been \"You have successfully logged in!\". Remember that you can't compare strings with ==, but you need to use equals!", out.toLowerCase().contains("succ"));;
        assertTrue("With username: \"" + k + "\" password: \"" + s + "\" you printed \"" + out + "\" while the output should have been \"You have successfully logged in!\". Remember that you can't compare strings with ==, but you need to use equals!", !out.toLowerCase().contains("orre"));
    }

    private void notPassing(String k, String s) {
        int oldOut = io.getSysOut().length();
        io.setSysIn(k + "\n" + s + "\n");
        callMain(Login.class);
        String out = io.getSysOut().substring(oldOut);

        assertTrue("you're not printing anything!", out.length() > 0);

        assertTrue("With username: \"" + k + "\" password: \"" + s + "\" you printed \"" + out + "\" while the output should have been \"Incorrect username or password!\". Remember that you can't compare strings with ==, but you need to use equals!", !out.toLowerCase().contains("succ"));
        assertTrue("With username: \"" + k + "\" password: \"" + s + "\" you printed \"" + out + "\" while the output should have been \"Incorrect username or password!\". Remember that you can't compare strings with ==, but you need to use equals!", (out.toLowerCase().contains("orre") || out.toLowerCase().contains("assw")));
    }

    private void callMain(Class kl) {
        try {
            kl = ReflectionUtils.newInstanceOfClass(kl);
            String[] t = null;
            String x[] = new String[0];
            Method m = ReflectionUtils.requireMethod(kl, "main", x.getClass());
            ReflectionUtils.invokeMethod(Void.TYPE, m, null, (Object) x);
        } catch (NoSuchElementException e) {
            fail("Are you using nextLine()-method to get input?");
        } catch (Throwable e) {
            fail("Something unexpected happened. The public static void main(String[] args) method of " + kl + "-class has disappeared \n"
            + "or your program crashed due to an exception. More info: " + e);
        }
    }
}
