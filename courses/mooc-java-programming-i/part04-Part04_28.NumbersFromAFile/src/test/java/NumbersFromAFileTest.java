
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import org.junit.Test;
import static org.junit.Assert.assertTrue;
import org.junit.Rule;

@Points("04-28")
public class NumbersFromAFileTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void testNumbers1() throws Throwable {
        numbersTest("numbers-1.txt", 5, 20, 3);
    }

    @Test
    public void testNumbers2() throws Throwable {
        numbersTest("numbers-1.txt", 1, 1, 0);
    }

    @Test
    public void testNumbers3() throws Throwable {
        numbersTest("numbers-1.txt", -999, 999, 4);
    }

    public void numbersTest(String file, int lowerBound, int upperBound, int numbers) throws Throwable {
        String in = file + "\n" + lowerBound + "\n" + upperBound + "\n";
        io.setSysIn(in);

        NumbersFromAFile.main(new String[]{});

        String out = io.getSysOut();

        assertTrue("When the input is:\n" + in + "Expected the output to contain \"Numbers: " + numbers + "\".\nThe output was:\n" + out, out.contains("Numbers: " + numbers));
    }
}
