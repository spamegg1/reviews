
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Rule;
import org.junit.Test;

@Points("03-23")
public class PrintThriceTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void testPrintThrice() {
        works("test");
    }
    
    @Test
    public void testHahaha() {
        works("ha");
    }
    
    @Test
    public void testFlora() {
        works("flora");
    }
    
    private void works(String str) {
        ReflectionUtils.newInstanceOfClass(PrintThrice.class);
        io.setSysIn(str + "\n");
        try {
            PrintThrice.main(new String[0]);
        } catch (NumberFormatException e) {
            fail("When you're reading a string form the user, don't try to convert it into a number. Error: " + e.getMessage());
        }
        

        String out = io.getSysOut();

        assertTrue("You didn't prompt the user for any input!", out.trim().length() > 0);
        
        assertTrue("When the input is \"" + str + "\" the output should contain \"" + str + str + str + "\", but now it didn't. The output was: "+out,
                   out.contains(str + str + str));
        
        assertTrue("When the input is \"" + str + "\" the output should contain \"" + str + str + str + "\", but now it didn't. The output was: "+out,
                   !out.contains(str + str + str + str));
    }
}
