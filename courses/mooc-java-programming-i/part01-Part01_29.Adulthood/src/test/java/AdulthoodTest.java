
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import static org.junit.Assert.assertTrue;
import org.junit.Rule;
import org.junit.Test;

@Points("01-29")
public class AdulthoodTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void test() {
        testAdulthood(17);
    }

    @Test
    public void testTwo() {
        testAdulthood(18);
    }

    @Test
    public void testThree() {
        testAdulthood(19);
    }

    private void testAdulthood(int age) {
        ReflectionUtils.newInstanceOfClass(Adulthood.class);
        io.setSysIn(age + "\n");
        Adulthood.main(new String[0]);

        String out = io.getSysOut();

        assertTrue("You did not ask user for input!", out.trim().length() > 0);

        String message = "When age is " + age + ", ";
        if (age >= 18) {
            assertTrue(message + "Program output should contain \"are an adult\", but it did not. Output was: " + out,
                    out.contains("are an a"));
            assertTrue(message + "Program output should not contain \"are not an adult\", but it did. Output was: " + out,
                    !out.contains("are not a"));
        } else {
            assertTrue("Program output should contain \"are not an adult\", but it did not. Output was: " + out,
                    out.contains("are not a"));
            assertTrue(message + "Program output should not contain \"are an adult\", but it did. Output was: " + out,
                    !out.contains("are an a"));
        }
    }
}
