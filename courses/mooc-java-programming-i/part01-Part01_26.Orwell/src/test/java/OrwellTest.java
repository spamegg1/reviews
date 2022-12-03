
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import static org.junit.Assert.assertTrue;
import org.junit.Rule;
import org.junit.Test;

@Points("01-26")
public class OrwellTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void test() {
        testOrwell(1983);
    }

    @Test
    public void testSecond() {
        testOrwell(1984);
    }

    @Test
    public void testThird() {
        testOrwell(1985);
    }

    private void testOrwell(int number) {
        ReflectionUtils.newInstanceOfClass(Orwell.class);
        io.setSysIn(number + "\n");
        Orwell.main(new String[0]);

        String out = io.getSysOut();

        assertTrue("You did not ask the user for a number!", out.trim().length() > 0);

        if (number == 1984) {
            assertTrue("Program output should contain the text \"Orwell\" when the user input is " 
                    + number + ", but it did not. Program output was: " + out, 
                    out.contains("rwell"));
        } else {
            assertTrue("Program output should not contain the text \"Orwell\" when user input is "
                    + number + ", but it did. Program output was: " + out, 
                    !out.contains("rwell"));
        }
    }
}
