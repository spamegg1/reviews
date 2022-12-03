
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import java.lang.reflect.Method;
import org.junit.*;
import static org.junit.Assert.*;

@Points("01-37")
public class GiftTaxTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void test1() {
        calculatesRightTax(1200, -1);
    }

    @Test
    public void test2() {
        calculatesRightTax(6000, 180);
    }

    @Test
    public void test3() {
        calculatesRightTax(30000, 2200);
    }

    @Test
    public void test4() {
        calculatesRightTax(55000, 4700);
    }

    @Test
    public void test5() {
        calculatesRightTax(199000, 21980);
    }

    @Test
    public void test6() {
        calculatesRightTax(201000, 22250);
    }

    @Test
    public void test7() {
        calculatesRightTax(1100000, 159100);
    }

    @Test
    public void test8() {
        calculatesRightTax(10000000, 1672100);
    }

    private void calculatesRightTax(int valueOfGift, double tax) {
        int oldOut = io.getSysOut().length();
        io.setSysIn(valueOfGift + "\n");
        callMain(GiftTax.class);
        String out = io.getSysOut().substring(oldOut);

        assertTrue("The program had no output!", out.length() > 0);

        if (tax < 0) {
            assertTrue("With input " + valueOfGift + " output was \"" + out + "\". Expected \"No tax!\"", out.toLowerCase().contains("no"));
        } else {
            assertFalse("With input " + valueOfGift + " output was \"" + out + "\". Did not expext  \"No Tax\".", out.toLowerCase().contains("no"));
            assertTrue("With input " + valueOfGift + " output was \"" + out + "\". Expected the tax to be" + tax, out.toLowerCase().contains("" + tax));
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
            fail(kl + "-class public static void main(String[] args) -method has dissappeared!");
        }
    }

}
