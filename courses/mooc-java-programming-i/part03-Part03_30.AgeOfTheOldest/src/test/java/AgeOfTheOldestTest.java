
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import java.lang.reflect.Method;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.UUID;
import org.junit.*;
import static org.junit.Assert.*;

@Points("03-30")
public class AgeOfTheOldestTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void test1() {
        String input = "barry,2\nmathew,1\nhelen,5\n\n";
        io.setSysIn(input);
        String oldOut = io.getSysOut();
        callMain(AgeOfTheOldest.class);

        String out = io.getSysOut().replace(oldOut, "").trim();
        assertTrue("When input was:\n " + input + ", output was expected to end with number \"5\". Now the output was: " + out, out.endsWith("5"));
    }

    @Test
    public void test2() {
        String input = "sibylla,6\nsalomon,2\nAlbert,1\n\n";
        io.setSysIn(input);
        String oldOut = io.getSysOut();
        callMain(AgeOfTheOldest.class);

        String out = io.getSysOut().replace(oldOut, "").trim();
        assertTrue("When input was:\n " + input + ", output was expected to end with number \"6\". Now the output was: " + out, out.endsWith("6"));
    }

    @Test
    public void test3() {
        randomInputTest();
    }

    @Test
    public void test4() {
        randomInputTest();
    }

    private void randomInputTest() {
        Random rnd = new Random();
        String input = "";
        int oldest = -1;
        for (int i = 0; i < 10; i++) {
            int age = rnd.nextInt(100);
            if (age > oldest) {
                oldest = age;
            }
            input += UUID.randomUUID().toString().substring(0, 4) + "," + age + "\n";
        }
        input += "\n";

        io.setSysIn(input);
        String oldOut = io.getSysOut();
        callMain(AgeOfTheOldest.class);

        String out = io.getSysOut().replace(oldOut, "").trim();
        assertTrue("When input was:\n " + input + ", output was expected to end with number \"" + oldest + "\". Now the output was: " + out, out.endsWith("" + oldest));
    }

    private void callMain(Class kl) {
        try {
            kl = ReflectionUtils.newInstanceOfClass(kl);
            String[] t = null;
            String x[] = new String[0];
            Method m = ReflectionUtils.requireMethod(kl, "main", x.getClass());
            ReflectionUtils.invokeMethod(Void.TYPE, m, null, (Object) x);
        } catch (NoSuchElementException e) {
            fail("Are you using nextLine() method to get input?");
        } catch (Throwable e) {
            fail("Something unexpected happened. The public static void main(String[] args) method of " + kl + " class has disappeared \n"
            + "or your program crashed due to an exception. More info: " + e);
        }
    }
}
