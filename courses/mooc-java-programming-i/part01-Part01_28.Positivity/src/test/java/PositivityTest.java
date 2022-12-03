
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import org.junit.Rule;
import org.junit.Test;

@Points("01-28")
public class PositivityTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void test() {
        testPositivity(1);
    }

    @Test
    public void testSecond() {
        testPositivity(0);
    }

    @Test
    public void testThird() {
        testPositivity(-1);
    }

    private void testPositivity(int number) {
        ReflectionUtils.newInstanceOfClass(Positivity.class);
        io.setSysIn(number + "\n");
        Positivity.main(new String[0]);

        String out = io.getSysOut();

        assertTrue("You did not ask the user for a number", out.trim().length() > 0);

        if (number > 0) {
            assertTrue("Program output should contain  \"is positive\", when user input is "
                    + number + ", but it did not. Program output was: " + out,
                    out.contains("is positive"));
            assertFalse("Program output should not contain text \"is not positive\", when user input is "
                    + number + ", but it did not. Program output was: " + out,
                    out.contains("is not positive"));
        } else {
            assertTrue("Program output should contain \"is not positive\", when user input is "
                    + number + ", but it did not. Program output was: " + out,
                    out.contains("is not positive"));
            assertFalse("Program output should not contain  \"is positive\" when user input is "
                    + number + ", but it did. Program output was: " + out,
                    out.contains("is positive"));
        }
    }
}
