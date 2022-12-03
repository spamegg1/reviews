
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import java.util.Random;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import org.junit.Rule;
import org.junit.Test;

@Points("01-33")
public class PasswordTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void test1() {
        check("Wattlebird");
    }

    @Test
    public void test2() {
        check("Caput Draconis");
    }

    private void check(String word) {
        ReflectionUtils.newInstanceOfClass(Password.class);
        String input = word + "\n";
        io.setSysIn(input);
        Password.main(new String[0]);

        String out = io.getSysOut();

        assertTrue("You did not ask the user for a password!", out.trim().length() > 0);

        if (word.equals("Caput Dragonis") || word.equals("Caput Draconis")) {
            assertTrue("Program output should contain \"Welcome!\", when the input is :\n" + input + "Program output was:\n" + out,
                    out.contains("Welcome!"));
            assertFalse("Program output should not contain \"Off with you!\", when the input is :\n" + input + "Program output was:\n" + out,
                    out.contains("Off with you!"));
        } else {
            assertTrue("Program output should contain  \"Off with you!\", when the input is :\n" + input + "Program output was:\n" + out,
                    out.contains("Off with you!"));
            assertFalse("Program output should not contain \"Welcome!\", when the input is :\n" + input + "Program output was:\n" + out,
                    out.contains("Welcome!"));
        }

    }
}
