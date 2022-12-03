
import fi.helsinki.cs.tmc.edutestutils.MockInOut;
import fi.helsinki.cs.tmc.edutestutils.Points;
import java.lang.reflect.Field;
import static org.junit.Assert.*;
import org.junit.Test;

@Points("02-22")
public class ReprintTest {

    @Test
    public void verifyNoFields() {
        Field[] fs = Reprint.class.getDeclaredFields();
        if (fs.length != 0) {
            fail("The Reprint-class contains a field called " + fs[0].getName() + ", delete it!");
        }
    }

    @Test
    public void testMethod() {
        MockInOut mio = new MockInOut("");
        try {
            Reprint.printText();
            assertEquals("You didn't print the right text in the printText() method!",
                    "In a hole in the ground there lived a method", mio.getOutput().trim());
        } catch (Throwable t) {
            fail("Something went wrong with calling the printText() method. Make sure the method only prints \"In a hole in the ground there lived a method\". More info: " + t);
        }
        mio.close();
    }

    @Test
    public void verifySomeOutput() {
        MockInOut mio = new MockInOut("3\n\n");
        Reprint.main(null);
        assertFalse("You didn't print anything!", mio.getOutput().isEmpty());
        mio.close();
    }

    @Test
    public void testPrintingOne() throws Exception {
        MockInOut mio = new MockInOut("1\n");

        Reprint.main(null);

        String out = mio.getOutput();
        String[] lines = out.split("\n");
        assertTrue("You didn't print the correct string!", lines[0].trim().toLowerCase().contains("many"));
        assertEquals("You printed too few lines when the input was \"1\".", 2, lines.length);
        assertEquals("You didn't print the correct string!",
                "In a hole in the ground there lived a method",
                lines[1].trim());
        mio.close();
    }

    @Test
    public void testPrintingMany() throws Exception {
        MockInOut mio = new MockInOut("9\n");

        Reprint.main(null);

        String out = mio.getOutput();
        String[] lines = out.split("\n");
        assertTrue("You didn't print the correct string!", lines[0].trim().toLowerCase().contains("many"));
        assertEquals("You printed too few lines when the input was \"9\".", 10, lines.length);
        for (int i = 1; i < lines.length; i++) {
            assertEquals("You didn't print the correct string!",
                    "In a hole in the ground there lived a method",
                    lines[i].trim());
        }

        mio.close();
    }
}
