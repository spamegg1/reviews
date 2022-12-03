
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import java.util.regex.Pattern;
import org.junit.*;
import static org.junit.Assert.assertTrue;

@Points("01-11")
public class VariousVariablesTest {

    String out;

    @Rule
    public MockStdio io = new MockStdio();

    @Before
    public void captureOutput() {
        VariousVariables.main(new String[0]);
        out = io.getSysOut();
    }

    String firstRegex(String a, String b) {
        return "(?s).*" + a + "\\s*" + b + "\\s.*";
    }

    String secondRegex(String a, String b) {
        return "(?s).*summary.*\\s*" + b + "\\s.*";
    }

    void test(String a, String b) {
        assertTrue("Make sure that output for " + a + " is correct!",
                Pattern.matches(firstRegex(a, b), out));
        assertTrue("Make sure that output for " + a + " is correct also in the summary!",
                Pattern.matches(secondRegex(a, b), out));
    }

    @Test
    public void testChicken() {
        test("Chicken:", "9000");
    }

    @Test
    public void testBacon() {
        test("Bacon \\(kg\\):", "0\\.1");
    }

    @Test
    public void testTractor() {
        test("Tractor:", "Zetor");
    }

}
