
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;

@Points("04-19")
public class TelevisionProgramTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void testInputFirst() {
        String input = "Two and a Half Men\n30\nLove it or list it\n60\n";
        int maxDuration = 0;

        io.setSysIn(input + "\n" + maxDuration + "\n");
        Main.main(new String[]{});

        assertFalse("When the input is\n" + input + "\n and the maximum duration is " + maxDuration + ", no programs should be printed.", io.getSysOut().contains("Two and a Half Men"));
        assertFalse("When the input is\n" + input + "\n and the maximum duration is " + maxDuration + ", no programs should be printed.", io.getSysOut().contains("Love it or list it"));
    }

    @Test
    public void testInputSecond() {
        String input = "House\n60\nLove it or list it\n60\n";
        int maximumDuration = 60;

        io.setSysIn(input + "\n" + maximumDuration + "\n");
        Main.main(new String[]{});

        assertTrue("When the input is\n" + input + "\n and the maximum duration is " + maximumDuration + ", all programs should be printed.", io.getSysOut().contains("House"));
        assertTrue("When the input is\n" + input + "\n and the maximum duration is " + maximumDuration + ", all programs should be printed.", io.getSysOut().contains("Love it or list it"));
    }

    @Test
    public void testInputThird() {
        String input = "House\n60\nTeletubbies\n30\nLove it or list it\n60\n";
        int maximumDuration = 30;

        io.setSysIn(input + "\n" + maximumDuration + "\n");
        Main.main(new String[]{});

        assertTrue("When the input is\n" + input + "\n and the maximum length is " + maximumDuration + ", you should only print programs that last at most" + maximumDuration + " minutes.", io.getSysOut().contains("Teletubbies"));
        assertFalse("When the input is\n" + input + "\n and the maximum length is " + maximumDuration + ", you should only print programs that last at most" + maximumDuration + " minutes.", io.getSysOut().contains("House"));
        assertFalse("When the input is\n" + input + "\n and the maximum length is " + maximumDuration + ", you should only print programs that last at most" + maximumDuration + " minutes.", io.getSysOut().contains("Love it or list it"));

    }
}
