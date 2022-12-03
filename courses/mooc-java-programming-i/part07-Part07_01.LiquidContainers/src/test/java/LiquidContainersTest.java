
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Rule;
import org.junit.Test;

public class LiquidContainersTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    @Points("07-01.1")
    public void empties() {
        String command = "quit\n";
        callMain(LiquidContainers.class, command);

        assertTrue("With input on:\n" + command + "\nOutput should contain:\nFirst: 0/100\nOutput was:\n" + io.getSysOut(), lines().stream().filter(l -> l.contains("irst") && l.replaceAll("\\s+", "").contains("0/100")).count() == 1);
        assertTrue("With input on:\n" + command + "\nOutput should contain:\nSecond: 0/100\nOutput was:\n" + io.getSysOut(), lines().stream().filter(l -> l.contains("ond") && l.replaceAll("\\s+", "").contains("0/100")).count() == 1);
    }

    @Test
    @Points("07-01.1")
    public void add1() {
        String command = "add 1\nquit\n";
        callMain(LiquidContainers.class, command);

        assertTrue("With input\n" + command + "\nOutput should contain:\nFirst: 0/100\nOutput was:\n" + io.getSysOut(), lines().stream().filter(l -> l.contains("irst") && l.replaceAll("\\s+", "").contains("0/100")).count() == 1);
        assertTrue("With input\n" + command + "\nOutput should contain:\nFirst: 1/100\nOutput was:\n" + io.getSysOut(), lines().stream().filter(l -> l.contains("irst") && l.replaceAll("\\s+", "").contains("1/100")).count() == 1);
        assertTrue("With input\n" + command + "\nOutput should contain:\nSecond: 0/100\nOutput was:\n" + io.getSysOut(), lines().stream().filter(l -> l.contains("ond") && l.replaceAll("\\s+", "").contains("0/100")).count() > 0);
    }

    @Test
    @Points("07-01.1")
    public void add2() {
        String command = "add 5\nquit\n";
        callMain(LiquidContainers.class, command);

        assertFalse("With input\n" + command + "\nOutput should not contain:\nFirst: 1/100\nOutput was:\n" + io.getSysOut(), lines().stream().filter(l -> l.contains("irst") && l.replaceAll("\\s+", "").contains("1/100")).count() == 1);
        assertTrue("With input\n" + command + "\nOutput should contain:\nFirst: 5/100\nOutput was:\n" + io.getSysOut(), lines().stream().filter(l -> l.contains("irst") && l.replaceAll("\\s+", "").contains("5/100")).count() == 1);
        assertTrue("With input\n" + command + "\nOutput should contain:\nSecond: 0/100\nOutput was:\n" + io.getSysOut(), lines().stream().filter(l -> l.contains("ond") && l.replaceAll("\\s+", "").contains("0/100")).count() > 0);
    }

    @Test
    @Points("07-01.1")
    public void add3() {
        String command = "add 25\nadd 25\nquit\n";
        callMain(LiquidContainers.class, command);

        assertFalse("With input\n" + command + "\nOutput should not contain:\nFirst: 1/100\nOutput was:\n" + io.getSysOut(), lines().stream().filter(l -> l.contains("irst") && l.replaceAll("\\s+", "").contains("1/100")).count() == 1);
        assertTrue("With input\n" + command + "\nOutput should contain:\nFirst: 25/100\nOutput was:\n" + io.getSysOut(), lines().stream().filter(l -> l.contains("irst") && l.replaceAll("\\s+", "").contains("25/100")).count() == 1);
        assertTrue("With input\n" + command + "\nOutput should contain:\nFirst: 50/100\nOutput was:\n" + io.getSysOut(), lines().stream().filter(l -> l.contains("irst") && l.replaceAll("\\s+", "").contains("50/100")).count() == 1);
        assertTrue("With input\n" + command + "\nOutput should contain:\nSecond: 0/100\nOutput was:\n" + io.getSysOut(), lines().stream().filter(l -> l.contains("ond") && l.replaceAll("\\s+", "").contains("0/100")).count() > 0);
    }

    @Test
    @Points("07-01.1")
    public void add4() {
        String command = "add 25\nadd -5\nquit\n";
        callMain(LiquidContainers.class, command);

        assertFalse("With input\n" + command + "\nOutput should not contain:\nFirst: 1/100\nOutput was:\n" + io.getSysOut(), lines().stream().filter(l -> l.contains("irst") && l.replaceAll("\\s+", "").contains("1/100")).count() == 1);
        assertTrue("With input\n" + command + "\nOutput should contain:\nFirst: 25/100\nOutput was:\n" + io.getSysOut(), lines().stream().filter(l -> l.contains("irst") && l.replaceAll("\\s+", "").contains("25/100")).count() > 0);
        assertFalse("With input\n" + command + "\nOutput should not contain:\nFirst: 20/100\nOutput was:\n" + io.getSysOut(), lines().stream().filter(l -> l.contains("irst") && l.replaceAll("\\s+", "").contains("20/100")).count() == 1);
        assertTrue("With input\n" + command + "\nOutput should contain:\nSecond: 0/100\nOutput was:\n" + io.getSysOut(), lines().stream().filter(l -> l.contains("ond") && l.replaceAll("\\s+", "").contains("0/100")).count() > 0);
    }

    @Test
    @Points("07-01.1")
    public void add5() {
        String command = "add 110\nquit\n";
        callMain(LiquidContainers.class, command);

        assertTrue("With input\n" + command + "\nOutput should contain:\nFirst: 100/100\nOutput was:\n" + io.getSysOut(), lines().stream().filter(l -> l.contains("irst") && l.replaceAll("\\s+", "").contains("100/100")).count() == 1);
    }

    @Test
    @Points("07-01.1")
    public void add6() {
        String command = "add 90\nadd 20\nquit\n";
        callMain(LiquidContainers.class, command);

        assertTrue("With input\n" + command + "\nOutput should contain:\nFirst: 90/100\nOutput was:\n" + io.getSysOut(), lines().stream().filter(l -> l.contains("irst") && l.replaceAll("\\s+", "").contains("90/100")).count() == 1);
        assertTrue("With input\n" + command + "\nOutput should contain:\nFirst: 100/100\nOutput was:\n" + io.getSysOut(), lines().stream().filter(l -> l.contains("irst") && l.replaceAll("\\s+", "").contains("100/100")).count() == 1);
    }

    @Test
    @Points("07-01.2")
    public void move1() {
        String command = "move 10\nquit\n";
        callMain(LiquidContainers.class, command);

        assertTrue("With input\n" + command + "\nOutput should contain:\nFirst: 0/100\nOutput was:\n" + io.getSysOut(), lines().stream().filter(l -> l.contains("irst") && l.replaceAll("\\s+", "").contains("0/100")).count() > 0);
        assertTrue("With input\n" + command + "\nOutput should contain:\nSecond: 0/100\nOutput was:\n" + io.getSysOut(), lines().stream().filter(l -> l.contains("ond") && l.replaceAll("\\s+", "").contains("0/100")).count() > 0);
        assertFalse("With input\n" + command + "\nOutput should not contain:\nSecond: 10/100\nOutput was:\n" + io.getSysOut(), lines().stream().filter(l -> l.contains("ond") && l.replaceAll("\\s+", "").contains("10/100")).count() > 0);
    }

    @Test
    @Points("07-01.2")
    public void move2() {
        String command = "add 10\nmove 10\nquit\n";
        callMain(LiquidContainers.class, command);

        assertTrue("With input\n" + command + "\nOutput should contain:\nSecond: 10/100\nOutput was:\n" + io.getSysOut(), lines().stream().filter(l -> l.contains("ond") && l.replaceAll("\\s+", "").contains("10/100")).count() > 0);
    }

    @Test
    @Points("07-01.2")
    public void move3() {
        String command = "add 30\nmove 10\nquit\n";
        callMain(LiquidContainers.class, command);

        assertTrue("With input\n" + command + "\nOutput should contain:\nFirst: 30/100\nOutput was:\n" + io.getSysOut(), lines().stream().filter(l -> l.contains("irst") && l.replaceAll("\\s+", "").contains("30/100")).count() > 0);
        assertTrue("With input\n" + command + "\nOutput should contain:\nFirst: 20/100\nOutput was:\n" + io.getSysOut(), lines().stream().filter(l -> l.contains("irst") && l.replaceAll("\\s+", "").contains("20/100")).count() > 0);
        assertTrue("With input\n" + command + "\nOutput should contain:\nSecond: 10/100\nOutput was:\n" + io.getSysOut(), lines().stream().filter(l -> l.contains("ond") && l.replaceAll("\\s+", "").contains("10/100")).count() > 0);
    }

    @Test
    @Points("07-01.2")
    public void move4() {
        String command = "add 30\nmove 40\nquit\n";
        callMain(LiquidContainers.class, command);

        assertTrue("With input\n" + command + "\nOutput should contain:\nFirst: 30/100\nOutput was:\n" + io.getSysOut(), lines().stream().filter(l -> l.contains("irst") && l.replaceAll("\\s+", "").contains("30/100")).count() > 0);
        assertTrue("With input\n" + command + "\nOutput should contain:\nSecond: 30/100\nOutput was:\n" + io.getSysOut(), lines().stream().filter(l -> l.contains("ond") && l.replaceAll("\\s+", "").contains("30/100")).count() > 0);
        assertFalse("With input\n" + command + "\nOutput should not contain:\nSecond: 40/100\nOutput was:\n" + io.getSysOut(), lines().stream().filter(l -> l.contains("ond") && l.replaceAll("\\s+", "").contains("40/100")).count() > 0);
    }

    @Test
    @Points("07-01.2")
    public void move5() {
        String command = "add 1000\nmove 90\nadd 100\nmove 90\nquit\n";
        callMain(LiquidContainers.class, command);

        assertFalse("With input\n" + command + "\nOutput should not contain:\nSecond: 180/100\nOutput was:\n" + io.getSysOut(), lines().stream().filter(l -> l.contains("ond") && l.replaceAll("\\s+", "").contains("180/100")).count() > 0);
        assertTrue("With input\n" + command + "\nOutput should contain:\nSecond: 90/100\nOutput was:\n" + io.getSysOut(), lines().stream().filter(l -> l.contains("ond") && l.replaceAll("\\s+", "").contains("90/100")).count() > 0);
        assertTrue("With input\n" + command + "\nOutput should contain:\nSecond: 100/100\nOutput was:\n" + io.getSysOut(), lines().stream().filter(l -> l.contains("ond") && l.replaceAll("\\s+", "").contains("100/100")).count() > 0);
    }

    @Test
    @Points("07-01.3")
    public void remove1() {
        String command = "remove 10\nquit\n";
        callMain(LiquidContainers.class, command);

        assertTrue("With input\n" + command + "\nOutput should contain:\nFirst: 0/100\nOutput was:\n" + io.getSysOut(), lines().stream().filter(l -> l.contains("irst") && l.replaceAll("\\s+", "").contains("0/100")).count() > 0);
        assertTrue("With input\n" + command + "\nOutput should contain:\nSecond: 0/100\nOutput was:\n" + io.getSysOut(), lines().stream().filter(l -> l.contains("ond") && l.replaceAll("\\s+", "").contains("0/100")).count() > 0);
        assertFalse("With input\n" + command + "\nOutput should not contain:\nSecond: -10/100\nOutput was:\n" + io.getSysOut(), lines().stream().filter(l -> l.contains("ond") && l.replaceAll("\\s+", "").contains("-10/100")).count() > 0);
    }

    @Test
    @Points("07-01.3")
    public void remove2() {
        String command = "add 30\nmove 20\nremove 15\nquit\n";
        callMain(LiquidContainers.class, command);

        assertTrue("With input\n" + command + "\nOutput should contain:\nFirst: 0/100\nOutput was:\n" + io.getSysOut(), lines().stream().filter(l -> l.contains("irst") && l.replaceAll("\\s+", "").contains("0/100")).count() > 0);
        assertTrue("With input\n" + command + "\nOutput should contain:\nSecond: 20/100\nOutput was:\n" + io.getSysOut(), lines().stream().filter(l -> l.contains("ond") && l.replaceAll("\\s+", "").contains("20/100")).count() > 0);
        assertTrue("With input\n" + command + "\nOutput should contain:\nSecond: 5/100\nOutput was:\n" + io.getSysOut(), lines().stream().filter(l -> l.contains("ond") && l.replaceAll("\\s+", "").contains("5/100")).count() > 0);
    }

    @Test
    @Points("07-01.3")
    public void remove3() {
        String command = "add 30\nremove 15\nquit\n";
        callMain(LiquidContainers.class, command);

        assertFalse("With input\n" + command + "\nOutput should not contain:\nFirst: 15/100\nOutput was:\n" + io.getSysOut(), lines().stream().filter(l -> l.contains("irst") && l.replaceAll("\\s+", "").contains("15/100")).count() > 0);
    }

    @Test
    @Points("07-01.3")
    public void remove4() {
        String command = "add 1000\nmove 90\nremove 33\nadd 1000\nmove 50\nremove 33\nquit\n";
        callMain(LiquidContainers.class, command);

        assertTrue("With input\n" + command + "\nOutput should contain:\nSecond: 57/100\nOutput was:\n" + io.getSysOut(), lines().stream().filter(l -> l.contains("ond") && l.replaceAll("\\s+", "").contains("57/100")).count() > 0);
        assertTrue("With input\n" + command + "\nOutput should contain:\nSecond: 67/100\nOutput was:\n" + io.getSysOut(), lines().stream().filter(l -> l.contains("ond") && l.replaceAll("\\s+", "").contains("67/100")).count() > 0);

        for (int i = 10; i < 100; i++) {
            if (i == 57 || i == 67 || i == 90) {
                continue;
            }

            final int number = i;
            assertFalse("With input\n" + command + "\nOutput should not contain:\nSecond: " + i + "/100\nOutput was:\n" + io.getSysOut(), lines().stream().filter(l -> l.contains("ond") && l.replaceAll("\\s+", "").contains("" + number + "/100")).count() > 0);
        }
    }

    @Test
    @Points("07-01.1 07-01.2 07-01.3")
    public void onlyMainMethod() {
        assertTrue("Do not create additional methods to the class LiquidContainers.", LiquidContainers.class.getDeclaredMethods().length == 1);
    }

    @Test
    @Points("07-01.1 07-01.2 07-01.3")
    public void noObjectOrClassVariables() {
        assertTrue("All of the functionality should be contained in the method main. Do not create additional classes or objects.", LiquidContainers.class.getDeclaredFields().length == 0);
    }

    private void callMain(Class kl, String input) {
        io.setSysIn(input);
        try {
            kl = ReflectionUtils.newInstanceOfClass(kl);
            String[] t = null;
            String x[] = new String[0];
            Method m = ReflectionUtils.requireMethod(kl, "main", x.getClass());
            ReflectionUtils.invokeMethod(Void.TYPE, m, null, (Object) x);
        } catch (NoSuchElementException e) {
            fail("Program tried to read more inputs than given by the user. Try: \n" + input);
            return;
        } catch (Throwable e) {
            fail("Something went wrong. Might be that " + kl + "-class public static void main(String[] args) -method has dissappeared\n"
                    + "or the program failed with an exception. More information: " + e);
        }
    }

    private List<String> lines() {
        return Arrays.asList(io.getSysOut().split("\n"));
    }
}
