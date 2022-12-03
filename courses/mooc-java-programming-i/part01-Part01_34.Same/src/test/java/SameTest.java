
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import org.junit.Rule;
import org.junit.Test;

@Points("01-34")
public class SameTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void test1() {
        areTheySame("hello","hello");
    }

    @Test
    public void test2() {
        areTheySame("hello", "world");
    }

    private void areTheySame(String first, String second) {
        ReflectionUtils.newInstanceOfClass(Same.class);
        String input = first + "\n" + second + "\n";
        io.setSysIn(input);
        Same.main(new String[0]);

        String out = io.getSysOut();
        

        assertTrue("You did not ask the user for input!", out.trim().length() > 0);

        if (first.equals(second)) {
            assertTrue("Program output should contain \"Same\", when the input is :\n" + input + "Program output was:\n" + out,
                    out.contains("Same"));
            assertFalse("Program output should not contain \"Different\", when the input is :\n" + input + "Program output was:\n" + out,
                    out.contains("Different"));
        } else {
            assertTrue("Program output should contain \"Different\", when the input is :\n" + input + "Program output was:\n" + out,
                    out.contains("Different"));
            assertFalse("Program output should not contain \"Same\", when the input is :\n" + input + "Program output was:\n" + out,
                    out.contains("Same"));
        }

    }
}
