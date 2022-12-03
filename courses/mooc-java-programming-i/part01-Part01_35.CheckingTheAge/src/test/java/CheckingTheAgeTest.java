
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import java.lang.reflect.Method;
import org.junit.*;
import static org.junit.Assert.*;

@Points("01-35")
public class CheckingTheAgeTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void testWithOKAges() {
        int[] OkAges = {0, 1, 10, 85, 120};
        for (int age : OkAges) {
            checkOkAge(age);
        }
    }

    @Test
    public void testWithImpossibleAges() {
        int[] impossibleAges = {-100, -1, 121, 1000};
        for (int age : impossibleAges) {
            checkImpossibleAge(age);
        }
    }

    private void checkOkAge(int age) {
        int oldOut = io.getSysOut().length();
        io.setSysIn(age + "\n");
        callMain(CheckingTheAge.class);
        String out = io.getSysOut().substring(oldOut);

        assertTrue("The program had no output!", out.length() > 0);
        assertTrue("When the input is " + age + " program output should contain \"OK\", program output was " + out, out.toLowerCase().contains("ok"));
        assertTrue("When the input is  " + age + " program output should not contain \"OK\", program output was " + out, !out.toLowerCase().contains("imp"));
    }

    private void checkImpossibleAge(int age) {
        int oldOut = io.getSysOut().length();
        io.setSysIn(age + "\n");
        callMain(CheckingTheAge.class);
        String out = io.getSysOut().substring(oldOut);

        assertTrue("The program had no output!", out.length() > 0);
        assertTrue("When the input is  " + age + " program output should contain \"Impossible\", program output was " + out, out.toLowerCase().contains("imp"));
        assertTrue("When the input is " + age + " program output should not contain \"OK\", Program output was " + out, !out.toLowerCase().contains("ok"));
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
