
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Set;
import org.junit.*;
import static org.junit.Assert.*;

@Points("03-29")
public class LastWordsTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void ohnoException() throws Exception {
        io.setSysIn("programming is fun\nit's true\n\n");
        try {
            LastWords.main(new String[0]);
        } catch (Exception e) {
            String v = "\n\npress show backtrace, the cause of the error is found a bit below at "
                    + "\"Caused by\"\n"
                    + "by clicking it you get directly to the line that caused it";

            throw new Exception("When the input was \"programming is fun\nit's true\n\n\"" + v, e);
        }
    }

    @Test
    public void test1() {
        String input = "flying heron\nhaiku\n\n";
        io.setSysIn(input);
        String oldOut = io.getSysOut();
        callMain(LastWords.class);

        String out = io.getSysOut().replace(oldOut, "");
        containsExpectedParts(out, input);
    }

    @Test
    public void test2() {
        String input = "programming in style\njava\n\n";
        io.setSysIn(input);
        String oldOut = io.getSysOut();
        callMain(LastWords.class);

        String out = io.getSysOut().replace(oldOut, "");
        containsExpectedParts(out, input);
    }
    
    @Test
    public void test3() {
        String input = "market\nmeerkats sway\ntheir souls are pure\n\n";
        io.setSysIn(input);
        String oldOut = io.getSysOut();
        callMain(LastWords.class);

        String out = io.getSysOut().replace(oldOut, "");
        containsExpectedParts(out, input);
    }

    private static void containsExpectedParts(String output, String input) {
        Set<String> expected = new HashSet<>();
        Scanner s = new Scanner(input);
        while (s.hasNextLine()) {
            String line = s.nextLine();
            if (line.isEmpty()) {
                continue;
            }

            String[] pts = line.split(" ");
            expected.add(pts[pts.length - 1]);
        }

        for (String line : output.split("\n")) {
            line = line.trim();
            if (!expected.contains(line)) {
                fail("Output contained unexpected string " + line + ".\nCheck the program with following input:\n" + input);
            }

            expected.remove(line);
        }

        if (!expected.isEmpty()) {
            ArrayList<String> expectedList = new ArrayList(expected);
            fail("Output was missing" + expectedList.get(0) + "\nCheck the program with following input:\n" + input);
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
            fail("Are you using nextLine() method to get input?");
        } catch (Throwable e) {
            fail("Something unexpected happened. The public static void main(String[] args) method of " + kl + " class has disappeared \n"
            + "or your program crashed due to an exception. More info: " + e);
        }
    }
}
