
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import org.junit.*;
import static org.junit.Assert.*;

@Points("03-31")
public class NameOfTheOldestTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void test1() {
        String input = "lilian,2\nsimeon,1\nvaleria,5\n\n";
        io.setSysIn(input);
        String oldOut = io.getSysOut();
        callMain(NameOfTheOldest.class);

        String out = io.getSysOut().replace(oldOut, "").trim();
        assertTrue("When input was:\n " + input + ", output was expected to end with string \"valeria\". Now the output was: " + out, out.endsWith("valeria"));
    }

    @Test
    public void test2() {
        String input = "gabriel,6\nleevi,2\nlilian,1\n\n";
        io.setSysIn(input);
        String oldOut = io.getSysOut();
        callMain(NameOfTheOldest.class);

        String out = io.getSysOut().replace(oldOut, "").trim();
        assertTrue("When input was:\n " + input + ", output was expected to end with string \"gabriel\". Now the output was: " + out, out.endsWith("gabriel"));
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

        Set<Integer> used = new HashSet<>();
        int oAge = -1;
        String oName = "";
        for (int i = 0; i < 10; i++) {
            int age = rnd.nextInt(100);
            while (used.contains(age)) {
                age = rnd.nextInt(100);
            }

            String n = UUID.randomUUID().toString().substring(0, 4);
            if (age > oAge) {
                oAge = age;
                oName = n;
            }

            input += n + "," + age + "\n";
        }
        input += "\n";

        io.setSysIn(input);
        String oldOut = io.getSysOut();
        callMain(NameOfTheOldest.class);

        String out = io.getSysOut().replace(oldOut, "").trim();
        assertTrue("When input was:\n " + input + ", output was expected to end with string \"" + oName + "\". Now the output was: " + out, out.endsWith("" + oName));
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
