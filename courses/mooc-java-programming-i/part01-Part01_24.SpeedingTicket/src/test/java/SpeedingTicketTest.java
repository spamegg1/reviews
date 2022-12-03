
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import static org.junit.Assert.assertTrue;
import org.junit.Rule;
import org.junit.Test;

@Points("01-24")
public class SpeedingTicketTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void test() {
        testTicket(119);
    }

    @Test
    public void testTwo() {
        testTicket(120);
    }

    @Test
    public void testThree() {
        testTicket(121);
    }

    private void testTicket(int number) {
        ReflectionUtils.newInstanceOfClass(SpeedingTicket.class);
        io.setSysIn(number + "\n");
        SpeedingTicket.main(new String[0]);

        String out = io.getSysOut();

        assertTrue("You did not ask user for the speed!", out.trim().length() > 0);

        if (number > 120) {
            assertTrue("Output should contain the text \"Speeding ticket!\", when the given speed is  "
                    + number + ", but it did not. Output was: " + out, 
                    out.contains("ticket"));
        } else {
            assertTrue("Output should not contain the text \"Speeding ticket!\", when the given speed is "
                    + number + ", but it did. Output was: " + out, 
                    !out.contains("ticket"));
        }
    }
}
