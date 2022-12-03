
import org.junit.Test;
import org.junit.Rule;
import static org.junit.Assert.*;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import java.lang.reflect.Field;

@Points("02-21")
public class InAHoleInTheGroundTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void verifyNoFields() {
        Field[] fs = InAHoleInTheGround.class.getDeclaredFields();
        if (fs.length != 0) {
            fail("The 'InAHoleInTheGround' class contains a field called " + fs[0].getName() + ", delete it!");
        }
    }

    @Test
    public void verifySomeOutput() {
        InAHoleInTheGround.printText();
        assertFalse("You aren't printing anything!", io.getSysOut().isEmpty());
    }

    @Test
    public void verifyCorrectOutput() {
        InAHoleInTheGround.printText();
        assertEquals("You didn't print the correct string!", "In a hole in the ground there lived a method", io.getSysOut().trim());
    }
}
