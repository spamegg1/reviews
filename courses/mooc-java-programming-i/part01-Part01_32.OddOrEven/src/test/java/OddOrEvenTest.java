
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import java.util.Random;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import org.junit.Rule;
import org.junit.Test;

@Points("01-32")
public class OddOrEvenTest {

    @Rule
    public MockStdio io = new MockStdio();
    private Random r = new Random();

    @Test
    public void test() {
        testOddOrEven(1);
    }

    @Test
    public void test2() {
        testOddOrEven(0);
    }

    @Test
    public void test3() {
        testOddOrEven(-1);
    }

    @Test
    public void extraTest() {
        testOddOrEven(r.nextInt(40) - 40);
    }

    @Test
    public void extraTest1() {
        testOddOrEven(r.nextInt(40) - 40);
    }

    @Test
    public void extraTest2() {
        testOddOrEven(r.nextInt(40) - 40);
    }

    @Test
    public void extraTest3() {
        testOddOrEven(r.nextInt(40) - 400);
    }

    @Test
    public void extraTest4() {
        testOddOrEven(r.nextInt(40));
    }

    @Test
    public void extraTest5() {
        testOddOrEven(r.nextInt(40) - 400);
    }

    private void testOddOrEven(int number) {
        ReflectionUtils.newInstanceOfClass(OddOrEven.class);

        io.setSysIn(number + "\n");
        OddOrEven.main(new String[0]);

        String out = io.getSysOut();

        assertTrue("You did not ask the user for a number", out.trim().length() > 0);

        if (number % 2 == 0) {
            assertTrue("Program output should contain \"is even\", when user input is "
                    + number + ", but it did not. Program output was: " + out,
                    out.contains("is even"));
            assertFalse("Program output should not contain \"is odd\", when user input is "
                    + number + ", but it did. Program output was: " + out,
                    out.contains("is odd"));
        } else {
            assertTrue("Program output should contain  \"is odd\", when user input is "
                    + number + ", but it did not. Program output was: " + out,
                    out.contains("is odd"));
            assertFalse("Program output should not contain \"is even\", when user input is "
                    + number + ", but it did. Program output was: " + out,
                    out.contains("is even"));
        }

    }
}
