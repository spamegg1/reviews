
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.util.Random;
import org.junit.*;
import static org.junit.Assert.*;

@Points("05-01")
public class TimerTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void classAndConstructor() {
        createTimer();
    }

    @Test
    public void toStringInTheBeginning() {
        Object timer = createTimer();

        String toStringFromTimer = callToString(timer);

        assertEquals("Printing the result of toString of a newly created timer should result in \"00:00\". Now the output was " + toStringFromTimer + "\nTry it out yourself:\n"
                + "Timer t = new Timer();\n"
                + "System.out.println(t);", "00:00", toStringFromTimer);
    }

    @Test
    public void advanceMethodExists() {
        Object timer = createTimer();
        Reflex.reflect("Timer").method("advance").returningVoid().takingNoParams().requirePublic();

        try {
            Reflex.reflect("Timer").method("advance").returningVoid().takingNoParams().invokeOn(timer);
        } catch (Throwable t) {
            fail("An error occurred when calling the method 'advance'. The error was: " + t.getMessage() + "\nTry it out:\n"
                    + "Timer t = new Timer();\n"
                    + "t.advance();");
        }

        String toStringFromTimer = callToString(timer);

        assertEquals("After a timer has advanced once, the result of toString should be \"00:01\". Now it was " + toStringFromTimer + "Try it out yourself:\n"
                + "Timer t = new Timer();\n"
                + "t.advance();\n"
                + "System.out.println(t);", "00:01", toStringFromTimer);
    }

    @Test
    public void advanceFar() {
        Object timer = createTimer();

        int randomAdvancementTime = new Random().nextInt(1000) + 1000;
        for (int i = 1; i <= randomAdvancementTime; i++) {
            try {
                Reflex.reflect("Timer").method("advance").returningVoid().takingNoParams().invokeOn(timer);
            } catch (Throwable t) {
                fail("An error occurred when calling the 'advance' method. The error was: " + t.getMessage() + "\nTry calling the advance method " + (i) + " times.");
            }
        }

        int seconds = ((randomAdvancementTime / 100) % 60);
        int hundredths = (randomAdvancementTime % 100);

        String s = seconds < 10 ? "0" + seconds : "" + seconds;
        String hos = hundredths < 10 ? "0" + hundredths : "" + hundredths;

        String toStringFromTimer = callToString(timer);
        String expectedPrint = "" + s + ":" + hos;

        assertEquals("When the advance method is called " + randomAdvancementTime + " times, the print should be \"" + expectedPrint + "\".\nNow it was " + toStringFromTimer + "\nTry it out:\n"
                + "Timer t = new Timer();\n"
                + "int i = 0;\n"
                + "while (i < " + randomAdvancementTime + ") {\n"
                + "    t.advance();\n"
                + "}"
                + "System.out.println(t);", expectedPrint, toStringFromTimer);
    }

    @Test
    public void advanceVeryFar() {
        Object timer = createTimer();

        int randomAdvancementTime = new Random().nextInt(10000) + 360000;
        for (int i = 1; i <= randomAdvancementTime; i++) {
            try {
                Reflex.reflect("Timer").method("advance").returningVoid().takingNoParams().invokeOn(timer);
            } catch (Throwable t) {
                fail("An error occurred when calling the 'advance' method. The error was: " + t.getMessage() + "\nTry calling the advance method " + (i) + " times.");
            }
        }

        int seconds = ((randomAdvancementTime / 100) % 60);
        int hundredths = (randomAdvancementTime % 100);

        String s = seconds < 10 ? "0" + seconds : "" + seconds;
        String hos = hundredths < 10 ? "0" + hundredths : "" + hundredths;

        String toStringFromTimer = callToString(timer);
        String expectedPrint = "" + s + ":" + hos;

        assertEquals("When the advance method is called " + randomAdvancementTime + " times, the print should be \"" + expectedPrint + "\".\nNow it was " + toStringFromTimer + "\nTry it out:\n"
                + "Timer t = new Timer();\n"
                + "int i = 0;\n"
                + "while (i < " + randomAdvancementTime + ") {\n"
                + "    t.advance();\n"
                + "}"
                + "System.out.println(t);", expectedPrint, toStringFromTimer);
    }

    private String callToString(Object timer) {
        String out = io.getSysOut();

        String toStringFromTimer = null;

        try {
            toStringFromTimer = timer.toString();
        } catch (Throwable e) {
            fail("Error when calling the toString method of the timer. Try:\n"
                    + "Timer timer = new Timer();\n"
                    + "timer.toString();\n"
                    + "The error that occurred: " + e.getMessage());
        }

        assertEquals("Calling the method toString shouldn't print anything. It is only to return a string.", out, io.getSysOut());

        return toStringFromTimer;
    }

    private Object createTimer() {
        Reflex.reflect("Timer").ctor().takingNoParams().requirePublic();
        try {
            return Reflex.reflect("Timer").ctor().takingNoParams().invoke();
        } catch (Throwable ex) {
            fail("An error occured while testing the program. Try:\nTimer timer = new Timer();\nError: " + ex.getMessage());
        }
        return null;
    }


}
