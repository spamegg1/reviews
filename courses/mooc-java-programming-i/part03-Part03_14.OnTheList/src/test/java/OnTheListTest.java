
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import java.lang.reflect.Method;
import java.util.NoSuchElementException;
import org.junit.*;
import static org.junit.Assert.*;

@Points("03-14")
public class OnTheListTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void test() {
        String[][] inputs = {{"Thomas", "Elizabeth", "Alex", "Mary", ""}, {"Elizabeth", "Alex", "Mary", ""}, {"First", "Second", "Third", "Fourth", "Fifth", "Sixth", "Seventh", ""}};

        for (int i = 0; i < inputs.length; i++) {
            check("Nonexistent", inputs[i]);
            check("Elizabeth", inputs[i]);
            check("Mary", inputs[i]);
        }
    }

    private void check(String searching, String... strings) {
        int oldOut = io.getSysOut().length();

        String in = "";
        boolean found = false;
        for (int i = 0; i < strings.length; i++) {
            in += strings[i] + "\n";
            if (strings[i].equals(searching)) {
                found = true;
            }
        }
        
        in += searching + "\n";

        io.setSysIn(in);
        callMain(OnTheList.class);
        String out = io.getSysOut().substring(oldOut);

        assertTrue("you aren't printing anything!", out.length() > 0);

        if (found) {
            assertTrue("When the person is found, the output should contain the name.\nFor example:. \"Thomas was found!\".\nNow the output was:\n" + out, out.contains(searching));
            assertTrue("When the person is found, the output should say \"found\".\nFor example \"Thomas was found!\".\nNow the output was:\n" + out, out.contains("was found"));
            assertFalse("When the person is found, the output should not contain \"was not found\".\nFor example \"Arto was not found!\".\nNow the output was:\n" + out, out.contains("not"));

        } else {
            assertTrue("When the person is not found, the output should contain the name.\nFor example \"Thomas was not found!\".\nNow the output was:\n" + out, out.contains(searching));
            assertFalse("When the person is not found, the output should not contain \"was found\".\nNow the output was:\n" + out, out.contains("was f"));
            assertTrue("When the person is not found, the output should contain \"was not found\".\nFor example \"Arto was not found!\".\nNow the output was:\n" + out, out.contains("not"));
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
