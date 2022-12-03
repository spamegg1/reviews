
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import java.lang.reflect.Method;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Rule;
import org.junit.Test;

@Points("01-31")
public class GradesAndPointsTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void fail1() {
        check(0, "failed", "fail");
    }

    @Test
    public void fail2() {
        check(49, "failed", "fail");
    }

    @Test
    public void grade1first() {
        check(50, "1", "1");
    }
    
    @Test
    public void grade1second() {
        check(59, "1", "1");
    }
    
    @Test
    public void grade2first() {
        check(60, "2", "2");
    }
    
    @Test
    public void grade2second() {
        check(69, "2", "2");
    }
    
    @Test
    public void grade3first() {
        check(70, "3", "3");
    }

    @Test
    public void grade3second() {
        check(79, "3", "3");
    }

    @Test
    public void grade4first() {
        check(80, "4", "4");
    }
    
    @Test
    public void grade4second() {
        check(85, "4", "4");
    }
    
    @Test
    public void grade4third() {
        check(89, "4", "4");
    }

    @Test
    public void grade5first() {
        check(90, "5", "5");
    }

    @Test
    public void grade5second() {
        check(100, "5", "5");
    }

    @Test
    public void impossible() {
        check(-1, "impossible!", "imp");
    }

    @Test
    public void incredible() {
        check(101, "incredible!", "inc");
    }

    private void check(int points, String grade, String gradeShort) {
        io.setSysIn(points + "\n");
        callMain(GradesAndPoints.class);
        String out = io.getSysOut();

        assertTrue("The program did not print anything", out.length() > 0);

        assertTrue("With " + points + " the grade should be " + grade + ", you printed: " + out, out.toLowerCase().contains("" + gradeShort));

        String[] incorrect = {"imp", "inc", "acc", "fail", "5"};
        for (String incorrectGrade : incorrect) {
            if (incorrectGrade.equals(gradeShort)) {
                continue;
            }

            assertTrue("With " + points + " the grade should be " + grade + ", you printed: " + out, !out.toLowerCase().contains("" + incorrectGrade));
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
