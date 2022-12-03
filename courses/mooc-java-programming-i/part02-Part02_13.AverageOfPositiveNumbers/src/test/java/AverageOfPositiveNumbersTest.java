
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import java.lang.reflect.Method;
import org.junit.*;
import static org.junit.Assert.*;

@Points("02-13")
public class AverageOfPositiveNumbersTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test(timeout = 1000)
    public void test1() {
        test("0\n", "nnot", "0", "1", "-1");
    }

    @Test(timeout = 1000)
    public void test2() {
        test("1\n2\n0\n", "1.5", "0");
    }

    @Test(timeout = 1000)
    public void test3() {
        test("-1\n3\n0\n", "3.0", "1");
    }

    @Test(timeout = 1000)
    public void test4() {
        test("1\n1\n1\n0\n", "1.0", "0.3", "0.7");
    }

    public void test(String input, String expected, String... notExpected) {

        int oldOut = io.getSysOut().length();
        io.setSysIn(input);
        callMain(AverageOfPositiveNumbers.class);
        String out = io.getSysOut().substring(oldOut);

        assertTrue("When input was:\n" + input + ", the expected out put was:\n" + expected + "\nOutput was not found.", out.contains(expected));
        for (String unexpected : notExpected) {
            assertFalse("When input was:\n" + input + ", output shouldn't contain:\n" + unexpected + "", out.contains(unexpected));
        }
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
