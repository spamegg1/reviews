
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import java.util.NoSuchElementException;
import org.junit.*;
import static org.junit.Assert.*;

@Points("07-08.1 07-08.2 07-08.3")
public class BirdDatabaseTest {

    @Rule
    public MockStdio io = new MockStdio();

    String test(String in) {

        String s;
        try {
            io.setSysIn(in);
            mainProgram.main(new String[0]);
            s = io.getSysOut();
        } catch (NoSuchElementException e) {
            fail("Error reading the input. Try:\n" + in);
            return null;
        } catch (Throwable t) {
            fail("Something went wrong. Try:\n" + in + "\n\nMore information:\n" + t);
            return null;
        }
        return s;

    }

    @Test
    public void testQuit1() {
        test("Quit\n");
    }

    @Test
    public void testEmptyAll() {
        test("All\nQuit\n");
    }

    @Test
    public void testOneNotExisting() {
        test("One\nPidgeon\nQuit\n");
    }

    @Test
    public void testObservationNotExisting() {
        test("Observation\nBigBird\nQuit\n");
    }

    @Test
    public void testAddOne() {
        String in = "Add\nXXX\nYYY\nOne\nXXX\nQuit\n";
        String out = test(in);
        String message = "Try:\n" + in;

        assertTrue("Program did not print birds name with the One-command. " + message,
                out.contains("XXX"));
        assertTrue("Program did not print birds name in Latin with the One-command. " + message,
                out.contains("YYY"));
        assertTrue("Program did not print number of observations with the One-command. " + message,
                out.contains("0"));
    }

    @Test
    public void testAddObservationObservationOne() {
        String in = "Add\nXXX\nYYY\nObservation\nXXX\nObservation\nXXX\nOne\nXXX\nQuit\n";
        String out = test(in);
        String message = "Try:\n" + in;

        assertTrue("Program did not print birds name with the One-command. " + message,
                out.contains("XXX"));
        assertTrue("Program did not print birds name in Latin with the One-command. " + message,
                out.contains("YYY"));
        assertTrue("Program did not print number of observations with the One-command. " + message,
                out.contains("2"));

    }

    public void bird(String message, String out, String a, String b, int no) {
        String[] lines = out.split("\n");
        boolean ok = false;
        for (String line : lines) {
            if (line.contains(a) && line.contains(b) && line.contains("" + no)) {
                ok = true;
            }
        }
        assertTrue("Program did not print birds " + a + " (" + b + ") number of observations correctly. " + message,
                ok);
    }

    @Test
    public void testWithTwoBirds() {
        String in = "Add\nXXX\nYYY\nObservation\nXXX\n"
                + "Add\nWWW\nZZZ\n"
                + "Observation\nWWW\n"
                + "One\nXXX\n"
                + "Observation\nXXX\nObservation\nWWW\n"
                + "One\nWWW\n"
                + "Quit\n";
        String out = test(in);
        String message = "Try:\n" + in;

        bird(message, out, "XXX", "YYY", 1);
        bird(message, out, "WWW", "ZZZ", 2);
    }

    @Test
    public void testWithTwoBirds2() {
        String in = "Add\nXX\nYY\n"
                + "Add\nWW\nZZ\n"
                + "Observation\nWW\n"
                + "Observation\nXX\nObservation\nWW\n"
                + "Observation\nXX\nObservation\nWW\n"
                + "All\n"
                + "Quit\n";
        String out = test(in);
        String message = "Try:\n" + in;

        bird(message, out, "XX", "YY", 2);
        bird(message, out, "WW", "ZZ", 3);
    }

    @Test
    public void testIncorrectCommands() {
        test("Beer\nQuit\n");
        test("Cake\nQuit\n");
        test("Oservation\nQuit\n");
    }
}
