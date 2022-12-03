
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import static org.junit.Assert.assertTrue;
import org.junit.Rule;
import org.junit.Test;

@Points("01-27")
public class AncientTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void test() {
        testAncient(2016);
    }

    @Test
    public void testTwo() {
        testAncient(2015);
    }

    @Test
    public void testThree() {
        testAncient(2014);
    }

    private void testAncient(int year) {
        ReflectionUtils.newInstanceOfClass(Ancient.class);
        io.setSysIn(year+ "\n");
        Ancient.main(new String[0]);

        String out = io.getSysOut();

        assertTrue("You did not ask the user for a year!", out.trim().length() > 0);

        if (year < 2015) {
            assertTrue("Program output should contain \"Ancient!\" when user input is "
                    + year + ", but it did not. Program output was " + out, 
                    out.contains("Ancient"));
        } else {
            assertTrue("Program output should not contain  \"Ancient\" when user input is  "
                    + year + ", but it did. Program output was: " + out, 
                    !out.contains("Ancient"));
        }
    }
}
