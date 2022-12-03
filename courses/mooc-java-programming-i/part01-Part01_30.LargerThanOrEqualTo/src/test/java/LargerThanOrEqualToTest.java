
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import java.lang.reflect.Method;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Rule;
import org.junit.Test;

@Points("01-30")
public class LargerThanOrEqualToTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void test1() {
        io.setSysIn("4\n5\n");
        callMain(LargerThanOrEqualTo.class);
        String out = io.getSysOut();

        assertTrue("The program did not print anything!", out.trim().length() > 0);
        assertTrue("With input 4,5 you printed " + out.trim(), out.trim().contains("5"));
        assertTrue("With input 4,5 you printed " + out.trim(), !out.trim().contains("4"));

    }

    @Test
    public void test2() throws Throwable {
        io.setSysIn("1\n-2\n");
        callMain(LargerThanOrEqualTo.class);
        String out = io.getSysOut();

        assertTrue("The program did not print anything!", out.trim().length() > 0);
        assertTrue("With input 1,-2 you printed " + out.trim(), out.trim().contains("1"));
        assertTrue("With input 1,-2 you printed " + out.trim(), !out.trim().contains("-2"));
    }

    @Test
    public void test3() {
        io.setSysIn("7\n7\n");
        callMain(LargerThanOrEqualTo.class);       
        String out = io.getSysOut();       

        assertTrue("The program did not print anything!", out.trim().length() > 0);
        assertTrue("With input 7,7 you printed " + out.trim(), !out.trim().contains("7"));
        assertTrue("With input 7,7 program should print  \"The numbers are equal!\". You printed " , out.trim().toLowerCase().contains("equal"));
    }
    
    private void callMain(Class kl) {
        try {
            kl = ReflectionUtils.newInstanceOfClass(LargerThanOrEqualTo.class);
            String[] t = null;
            Method m = null;
            String x[] = new String[0];
            m = ReflectionUtils.requireMethod(kl, "main", x.getClass());
            ReflectionUtils.invokeMethod(Void.TYPE, m, null, (Object) x);
        } catch (Throwable e) {
            fail( kl+"- public static void main(String[] args) -method of the class has dissappeared!");
        }
    }    
}
