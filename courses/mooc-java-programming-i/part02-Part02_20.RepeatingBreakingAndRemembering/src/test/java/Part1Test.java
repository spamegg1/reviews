import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import java.lang.reflect.Method;
import java.util.NoSuchElementException;
import org.junit.*;
import static org.junit.Assert.*;

@Points("02-20.1")
public class Part1Test {
    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void test() {
        int[][] input = {{1, -1}, {2, 5, -1}};

       
        for (int i = 0; i < input.length; i++) {
            int oldOut = io.getSysOut().length();
            io.setSysIn(stringify(input[i]));
            callMain(RepeatingBreakingAndRemembering.class);
            String out = io.getSysOut().substring(oldOut);

            String errorMsg = "When the user has given number -1, the program should first print"
                    + "\"Give numbers:\" and in the end \"Thx! Bye!\"";
            assertTrue("you're not printing anything!", out.length() > 0);
            assertTrue(errorMsg, out.contains("numbers"));
            assertTrue(errorMsg, out.contains("hx"));
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
            fail("remember to quit when the user gives -1");
        } catch (Throwable e) {
            fail("unexpected error, are you sure you aren't dividing by zero?");
        }
    }

    private String stringify(int[] array) {
        String str = "";
        for (int luku : array) {
            str += luku + "\n";
        }

        return str;
    }
}
