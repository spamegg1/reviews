
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Rule;
import org.junit.Test;

public class LiquidContainers2Test {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    @Points("07-02.1")
    public void container1() {
        Reflex.reflect("Container").ctor().takingNoParams().requirePublic();
    }

    @Test
    @Points("07-02.1")
    public void container2() {
        Reflex.reflect("Container").method("add").returningVoid().taking(int.class).requirePublic();
    }

    @Test
    @Points("07-02.1")
    public void container3() {
        Reflex.reflect("Container").method("remove").returningVoid().taking(int.class).requirePublic();
    }

    @Test
    @Points("07-02.1")
    public void container4() {
        Reflex.reflect("Container").method("contains").returning(int.class).takingNoParams().requirePublic();
    }

    @Test
    @Points("07-02.1")
    public void container5() throws Throwable {
        Object containerObject = Reflex.reflect("Container").ctor().takingNoParams().invoke();
        String out = containerObject.toString();
        assertEquals("Try:\nContainer container = new Container();\nSystem.out.println(container);\n", "0/100", out);

        Reflex.reflect("Container").method("add").returningVoid().taking(int.class).invokeOn(containerObject, 10);
        out = containerObject.toString();
        assertEquals("Try:\nContainer container = new Container();\ncontainer.add(10);\nSystem.out.printlin(container);\n", "10/100", out);

        assertEquals("Try:\nContainer container = new Container();\ncontainer.add(10);\nSystem.out.println(container.contains());\n", Integer.valueOf(10), Reflex.reflect("Container").method("contains").returning(int.class).takingNoParams().invokeOn(containerObject));

        Reflex.reflect("Container").method("add").returningVoid().taking(int.class).invokeOn(containerObject, 95);
        out = containerObject.toString();
        assertEquals("Try:\nContainer container = new Container();\ncontainer.add(10);\ncontainer.add(95);\nSystem.out.printlin(container);\n", "100/100", out);

        Reflex.reflect("Container").method("add").returningVoid().taking(int.class).invokeOn(containerObject, -5);
        out = containerObject.toString();
        assertEquals("Try:\nContainer container = new Container();\ncontainer.add(10);\ncontainer.add(95);\ncontainer.add(-5);\nSystem.out.printlin(container);\n", "100/100", out);
    }

    @Test
    @Points("07-02.1")
    public void container6() throws Throwable {
        Object container = Reflex.reflect("Container").ctor().takingNoParams().invoke();
        String out = container.toString();
        assertEquals("Try:\nContainer container = new Container();\nSystem.out.printlin(container);\n", "0/100", out);

        Reflex.reflect("Container").method("add").returningVoid().taking(int.class).invokeOn(container, 10);
        out = container.toString();
        assertEquals("Try:\nContainer container = new Container();\ncontainer.add(10);\nSystem.out.printlin(container);\n", "10/100", out);

        Reflex.reflect("Container").method("remove").returningVoid().taking(int.class).invokeOn(container, 20);
        out = container.toString();
        assertEquals("Try:\nContainer container = new Container();\ncontainer.add(10);\ns.remove(20);\nSystem.out.printlin(container);\n", "0/100", out);
    }

    @Test
    @Points("07-02.2")
    public void empties() {
        String command = "quit\n";
        callMain(LiquidContainers2.class, command);

        assertTrue("With input\n" + command + "\nOutput should contain:\nFirst: 0/100\nOutput was:\n" + io.getSysOut(), lines().stream().filter(l -> l.contains("irst") && l.replaceAll("\\s+", "").contains("0/100")).count() == 1);
        assertTrue("With input\n" + command + "\nOutput should contain:\nSecond: 0/100\nOutput was:\n" + io.getSysOut(), lines().stream().filter(l -> l.contains("ond") && l.replaceAll("\\s+", "").contains("0/100")).count() == 1);
    }

    @Test
    @Points("07-02.2")
    public void add1() {
        String command = "add 1\nquit\n";
        callMain(LiquidContainers2.class, command);

        assertTrue("With input\n" + command + "\nOutput should contain:\nFirst: 0/100\nOutput was:\n" + io.getSysOut(), lines().stream().filter(l -> l.contains("irst") && l.replaceAll("\\s+", "").contains("0/100")).count() == 1);
        assertTrue("With input\n" + command + "\nOutput should contain:\nFirst: 1/100\nOutput was:\n" + io.getSysOut(), lines().stream().filter(l -> l.contains("irst") && l.replaceAll("\\s+", "").contains("1/100")).count() == 1);
        assertTrue("With input\n" + command + "\nOutput should contain:\nSecond: 0/100\nOutput was:\n" + io.getSysOut(), lines().stream().filter(l -> l.contains("ond") && l.replaceAll("\\s+", "").contains("0/100")).count() > 0);
    }

    @Test
    @Points("07-02.2")
    public void add2() {
        String command = "add 5\nquit\n";
        callMain(LiquidContainers2.class, command);

        assertFalse("With input\n" + command + "\nOutput should not contain: \nFirst: 1/100\nOutput was:\n" + io.getSysOut(), lines().stream().filter(l -> l.contains("irst") && l.replaceAll("\\s+", "").contains("1/100")).count() == 1);
        assertTrue("With input\n" + command + "\nOutput should contain:\nFirst: 5/100\nOutput was:\n" + io.getSysOut(), lines().stream().filter(l -> l.contains("irst") && l.replaceAll("\\s+", "").contains("5/100")).count() == 1);
        assertTrue("With input\n" + command + "\nOutput should contain:\nSecond: 0/100\nOutput was:\n" + io.getSysOut(), lines().stream().filter(l -> l.contains("ond") && l.replaceAll("\\s+", "").contains("0/100")).count() > 0);
    }

    @Test
    @Points("07-02.2")
    public void add3() {
        String command = "add 25\nadd 25\nquit\n";
        callMain(LiquidContainers2.class, command);

        assertFalse("With input\n" + command + "\nOutput should not contain: \nFirst: 1/100\nOutput was:\n" + io.getSysOut(), lines().stream().filter(l -> l.contains("irst") && l.replaceAll("\\s+", "").contains("1/100")).count() == 1);
        assertTrue("With input\n" + command + "\nOutput should contain:\nFirst: 25/100\nOutput was:\n" + io.getSysOut(), lines().stream().filter(l -> l.contains("irst") && l.replaceAll("\\s+", "").contains("25/100")).count() == 1);
        assertTrue("With input\n" + command + "\nOutput should contain:\nFirst: 50/100\nOutput was:\n" + io.getSysOut(), lines().stream().filter(l -> l.contains("irst") && l.replaceAll("\\s+", "").contains("50/100")).count() == 1);
        assertTrue("With input\n" + command + "\nOutput should contain:\nSecond: 0/100\nOutput was:\n" + io.getSysOut(), lines().stream().filter(l -> l.contains("ond") && l.replaceAll("\\s+", "").contains("0/100")).count() > 0);
    }

    @Test
    @Points("07-02.2")
    public void add4() {
        String command = "add 25\nadd -5\nquit\n";
        callMain(LiquidContainers2.class, command);

        assertFalse("With input\n" + command + "\nOutput should not contain: \nFirst: 1/100\nOutput was:\n" + io.getSysOut(), lines().stream().filter(l -> l.contains("irst") && l.replaceAll("\\s+", "").contains("1/100")).count() == 1);
        assertTrue("With input\n" + command + "\nOutput should contain:\nFirst: 25/100\nOutput was:\n" + io.getSysOut(), lines().stream().filter(l -> l.contains("irst") && l.replaceAll("\\s+", "").contains("25/100")).count() > 0);
        assertFalse("With input\n" + command + "\nOutput should not contain: \nFirst: 20/100\nOutput was:\n" + io.getSysOut(), lines().stream().filter(l -> l.contains("irst") && l.replaceAll("\\s+", "").contains("20/100")).count() == 1);
        assertTrue("With input\n" + command + "\nOutput should contain:\nSecond: 0/100\nOutput was:\n" + io.getSysOut(), lines().stream().filter(l -> l.contains("ond") && l.replaceAll("\\s+", "").contains("0/100")).count() > 0);
    }

    @Test
    @Points("07-02.2")
    public void add5() {
        String command = "add 110\nquit\n";
        callMain(LiquidContainers2.class, command);

        assertTrue("With input\n" + command + "\nOutput should contain:\nFirst: 100/100\nOutput was:\n" + io.getSysOut(), lines().stream().filter(l -> l.contains("irst") && l.replaceAll("\\s+", "").contains("100/100")).count() == 1);
    }

    @Test
    @Points("07-02.2")
    public void add6() {
        String command = "add 90\nadd 20\nquit\n";
        callMain(LiquidContainers2.class, command);

        assertTrue("With input\n" + command + "\nOutput should contain:\nFirst: 90/100\nOutput was:\n" + io.getSysOut(), lines().stream().filter(l -> l.contains("irst") && l.replaceAll("\\s+", "").contains("90/100")).count() == 1);
        assertTrue("With input\n" + command + "\nOutput should contain:\nFirst: 100/100\nOutput was:\n" + io.getSysOut(), lines().stream().filter(l -> l.contains("irst") && l.replaceAll("\\s+", "").contains("100/100")).count() == 1);
    }

    @Test
    @Points("07-02.2")
    public void move1() {
        String command = "move 10\nquit\n";
        callMain(LiquidContainers2.class, command);

        assertTrue("With input\n" + command + "\nOutput should contain:\nFirst: 0/100\nOutput was:\n" + io.getSysOut(), lines().stream().filter(l -> l.contains("irst") && l.replaceAll("\\s+", "").contains("0/100")).count() > 0);
        assertTrue("With input\n" + command + "\nOutput should contain:\nSecond: 0/100\nOutput was:\n" + io.getSysOut(), lines().stream().filter(l -> l.contains("ond") && l.replaceAll("\\s+", "").contains("0/100")).count() > 0);
        assertFalse("With input\n" + command + "\nOutput should not contain: \nSecond: 10/100\nOutput was:\n" + io.getSysOut(), lines().stream().filter(l -> l.contains("ond") && l.replaceAll("\\s+", "").contains("10/100")).count() > 0);
    }

    @Test
    @Points("07-02.2")
    public void move2() {
        String command = "add 10\nmove 10\nquit\n";
        callMain(LiquidContainers2.class, command);

        assertTrue("With input\n" + command + "\nOutput should contain:\nSecond: 10/100\nOutput was:\n" + io.getSysOut(), lines().stream().filter(l -> l.contains("ond") && l.replaceAll("\\s+", "").contains("10/100")).count() > 0);
    }

    @Test
    @Points("07-02.2")
    public void move3() {
        String command = "add 30\nmove 10\nquit\n";
        callMain(LiquidContainers2.class, command);

        assertTrue("With input\n" + command + "\nOutput should contain:\nFirst: 30/100\nOutput was:\n" + io.getSysOut(), lines().stream().filter(l -> l.contains("irst") && l.replaceAll("\\s+", "").contains("30/100")).count() > 0);
        assertTrue("With input\n" + command + "\nOutput should contain:\nFirst: 20/100\nOutput was:\n" + io.getSysOut(), lines().stream().filter(l -> l.contains("irst") && l.replaceAll("\\s+", "").contains("20/100")).count() > 0);
        assertTrue("With input\n" + command + "\nOutput should contain:\nSecond: 10/100\nOutput was:\n" + io.getSysOut(), lines().stream().filter(l -> l.contains("ond") && l.replaceAll("\\s+", "").contains("10/100")).count() > 0);
    }

    @Test
    @Points("07-02.2")
    public void move4() {
        String command = "add 30\nmove 40\nquit\n";
        callMain(LiquidContainers2.class, command);

        assertTrue("With input\n" + command + "\nOutput should contain:\nFirst: 30/100\nOutput was:\n" + io.getSysOut(), lines().stream().filter(l -> l.contains("irst") && l.replaceAll("\\s+", "").contains("30/100")).count() > 0);
        assertTrue("With input\n" + command + "\nOutput should contain:\nSecond: 30/100\nOutput was:\n" + io.getSysOut(), lines().stream().filter(l -> l.contains("ond") && l.replaceAll("\\s+", "").contains("30/100")).count() > 0);
        assertFalse("With input\n" + command + "\nOutput should not contain: \nSecond: 40/100\nOutput was:\n" + io.getSysOut(), lines().stream().filter(l -> l.contains("ond") && l.replaceAll("\\s+", "").contains("40/100")).count() > 0);
    }

    @Test
    @Points("07-02.2")
    public void move5() {
        String command = "add 1000\nmove 90\nadd 100\nmove 90\nquit\n";
        callMain(LiquidContainers2.class, command);

        assertFalse("With input\n" + command + "\nOutput should not contain: \nSecond: 180/100\nOutput was:\n" + io.getSysOut(), lines().stream().filter(l -> l.contains("ond") && l.replaceAll("\\s+", "").contains("180/100")).count() > 0);
        assertTrue("With input\n" + command + "\nOutput should contain:\nSecond: 90/100\nOutput was:\n" + io.getSysOut(), lines().stream().filter(l -> l.contains("ond") && l.replaceAll("\\s+", "").contains("90/100")).count() > 0);
        assertTrue("With input\n" + command + "\nOutput should contain:\nSecond: 100/100\nOutput was:\n" + io.getSysOut(), lines().stream().filter(l -> l.contains("ond") && l.replaceAll("\\s+", "").contains("100/100")).count() > 0);
    }

    @Test
    @Points("07-02.2")
    public void remove1() {
        String command = "remove 10\nquit\n";
        callMain(LiquidContainers2.class, command);

        assertTrue("With input\n" + command + "\nOutput should contain:\nFirst: 0/100\nOutput was:\n" + io.getSysOut(), lines().stream().filter(l -> l.contains("irst") && l.replaceAll("\\s+", "").contains("0/100")).count() > 0);
        assertTrue("With input\n" + command + "\nOutput should contain:\nSecond: 0/100\nOutput was:\n" + io.getSysOut(), lines().stream().filter(l -> l.contains("ond") && l.replaceAll("\\s+", "").contains("0/100")).count() > 0);
        assertFalse("With input\n" + command + "\nOutput should not contain: \nSecond: -10/100\nOutput was:\n" + io.getSysOut(), lines().stream().filter(l -> l.contains("ond") && l.replaceAll("\\s+", "").contains("-10/100")).count() > 0);
    }

    @Test
    @Points("07-02.2")
    public void remove2() {
        String command = "add 30\nmove 20\nremove 15\nquit\n";
        callMain(LiquidContainers2.class, command);

        assertTrue("With input\n" + command + "\nOutput should contain:\nFirst: 0/100\nOutput was:\n" + io.getSysOut(), lines().stream().filter(l -> l.contains("irst") && l.replaceAll("\\s+", "").contains("0/100")).count() > 0);
        assertTrue("With input\n" + command + "\nOutput should contain:\nSecond: 20/100\nOutput was:\n" + io.getSysOut(), lines().stream().filter(l -> l.contains("ond") && l.replaceAll("\\s+", "").contains("20/100")).count() > 0);
        assertTrue("With input\n" + command + "\nOutput should contain:\nSecond: 5/100\nOutput was:\n" + io.getSysOut(), lines().stream().filter(l -> l.contains("ond") && l.replaceAll("\\s+", "").contains("5/100")).count() > 0);
    }

    @Test
    @Points("07-02.2")
    public void remove3() {
        String command = "add 30\nremove 15\nquit\n";
        callMain(LiquidContainers2.class, command);

        assertFalse("With input\n" + command + "\nOutput should not contain: \nFirst: 15/100\nOutput was:\n" + io.getSysOut(), lines().stream().filter(l -> l.contains("irst") && l.replaceAll("\\s+", "").contains("15/100")).count() > 0);
    }

    @Test
    @Points("07-02.2")
    public void remove4() {
        String command = "add 1000\nmove 90\nremove 33\nadd 1000\nmove 50\nremove 33\nquit\n";
        callMain(LiquidContainers2.class, command);

        assertTrue("With input\n" + command + "\nOutput should contain:\nSecond: 57/100\nOutput was:\n" + io.getSysOut(), lines().stream().filter(l -> l.contains("ond") && l.replaceAll("\\s+", "").contains("57/100")).count() > 0);
        assertTrue("With input\n" + command + "\nOutput should contain:\nSecond: 67/100\nOutput was:\n" + io.getSysOut(), lines().stream().filter(l -> l.contains("ond") && l.replaceAll("\\s+", "").contains("67/100")).count() > 0);

        for (int i = 10; i < 100; i++) {
            if (i == 57 || i == 67 || i == 90) {
                continue;
            }

            final int luku = i;
            assertFalse("With input\n" + command + "\nOutput should not contain: \nSecond: " + i + "/100\nOutput was:\n" + io.getSysOut(), lines().stream().filter(l -> l.contains("ond") && l.replaceAll("\\s+", "").contains("" + luku + "/100")).count() > 0);
        }
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
            fail("Program tried to read more input than given by the user. Try: \n" + input);
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
