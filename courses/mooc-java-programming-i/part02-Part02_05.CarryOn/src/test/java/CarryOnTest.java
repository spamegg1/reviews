
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import java.lang.reflect.Method;
import org.junit.*;
import static org.junit.Assert.*;

@Points("02-05")
public class CarryOnTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test(timeout = 1000)
    public void test1() {
        test("y\nyes\nyup\nsure\nno\n");
    }

    @Test(timeout = 1000)
    public void test2() {
        test("no\n");
    }

    @Test(timeout = 1000)
    public void test3() {
        test("noooo\nno way\nnope\nlet me out\nn\noo\nnn\nno\n");
    }

    public void test(String input) {
        int oldOut = io.getSysOut().length();

        io.setSysIn(input);
        callMain(CarryOn.class);
        String out = io.getSysOut().substring(oldOut);

        int count = out.trim().split("arry").length - 1;
        int expected = input.split("\n").length;
        assertEquals("When input was:\n" + input + "\n'Shall we carry on?' question should appear " + expected + " times. Now it appeared " + count + " times.", expected, count);
    }

    private void callMain(Class kl) {
        try {
            kl = ReflectionUtils.newInstanceOfClass(kl);
            String[] t = null;
            String x[] = new String[0];
            Method m = ReflectionUtils.requireMethod(kl, "main", x.getClass());
            ReflectionUtils.invokeMethod(Void.TYPE, m, null, (Object) x);
        } catch (Throwable e) {
            fail("Something unexpected happened. The public static void main(String[] args) method of '" + kl + "' class has disappeared \n"
                    + "or your program crashed because of an exception. More info: " + e);	
        }
    }

}
