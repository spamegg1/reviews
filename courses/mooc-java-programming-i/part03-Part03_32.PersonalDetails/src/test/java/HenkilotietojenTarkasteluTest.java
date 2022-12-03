
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import org.junit.*;
import static org.junit.Assert.*;

@Points("03-32")
public class HenkilotietojenTarkasteluTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void test1() {
        String input = "sebastian,2017\n"
                + "lucas,2017\n"
                + "lily,2017\n"
                + "hanna,2014\n"
                + "gabriel,2009\n\n";
        io.setSysIn(input);
        String oldOut = io.getSysOut();
        PersonalDetails.main(new String[0]);

        String out = io.getSysOut().replace(oldOut, "");
        isDataCorrect(out, input, "sebastian", "2014.8");
    }

    @Test
    public void test2() {
        String input = "levi,2017\n\n";
        io.setSysIn(input);
        String oldOut = io.getSysOut();
        PersonalDetails.main(new String[0]);

        String out = io.getSysOut().replace(oldOut, "");
        isDataCorrect(out, input, "levi", "2017.0");
    }

    @Test
    public void test3() {
        String input = "saul,1948\n"
                + "tanya,1943\n"
                + "martin,1936\n"
                + "mickey,1923\n"
                + "will,1900\n\n";
        io.setSysIn(input);
        String oldOut = io.getSysOut();
        PersonalDetails.main(new String[0]);

        String out = io.getSysOut().replace(oldOut, "");
        isDataCorrect(out, input, "martin", "1930.0");
    }

    private static void isDataCorrect(String output, String input, String longestName, String average) {
        for (String line : input.split("\n")) {
            String name = line.split(",")[0];
            if (name.equals(longestName)) {
                continue;
            }

            assertFalse("When input was:\n" + input + "\nOutput wasn't expected to contain name:\n" + name + "\nOutput was:\n" + output, output.contains(name));
        }

        assertTrue("When input was:\n" + input + "\nOutput wasn't expected to contain name:\n" + longestName + "\nOutput was:\n" + output, output.contains(longestName));
        assertTrue("When input was:\n" + input + "\nOutput was expected to contain the average:\n" + average + "\nOutput was:\n" + output, output.contains(average));
    }

}
