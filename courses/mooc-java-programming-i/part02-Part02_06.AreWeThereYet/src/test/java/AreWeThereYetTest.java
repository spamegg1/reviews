
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import java.lang.reflect.Method;
import org.junit.*;
import static org.junit.Assert.*;

@Points("02-06")
public class AreWeThereYetTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test(timeout = 1000)
    public void test1() {
        test("1\n5\n4\n");
    }

    @Test(timeout = 1000)
    public void test2() {
        test("4\n");
    }

    @Test(timeout = 1000)
    public void test3() {
        test("-2\n-7\n99\n123\n4\n");
    }

    public void test(String input) {
        int oldOut = io.getSysOut().length();

        io.setSysIn(input);
        callMain(AreWeThereYet.class);
        String out = io.getSysOut().substring(oldOut);

        int count = out.trim().split("numbe").length - 1;
        int expected = input.split("\n").length;
        assertEquals("When input was:\n" + input + "\nthe input should be requested " + expected + " times in total. Now the count was" + count, expected, count);
    }

    private void callMain(Class kl) {
        try {
            kl = ReflectionUtils.newInstanceOfClass(kl);
            String[] t = null;
            String x[] = new String[0];
            Method m = ReflectionUtils.requireMethod(kl, "main", x.getClass());
            ReflectionUtils.invokeMethod(Void.TYPE, m, null, (Object) x);
        } catch (Throwable e) {
            fail("Something strange happened. It may be that '" + kl + "' class's public static void main(String[] args) -method is missing \n"
                    + "or your program crashed due to an exception. More information " + e);
        }
    }

}
