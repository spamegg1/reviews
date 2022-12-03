
import org.junit.Test;
import static org.junit.Assert.*;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.MockInOut;

import java.lang.reflect.Field;

public class StarSignTest {

    public String sanitize(String s) {
        return s.replace("\r\n", "\n").replace("\r", "\n");
    }

    @Points("02-33.1 02-33.2 02-33.3 02-33.4")
    @Test
    public void verifyNoFields() {
        Field[] fs = StarSign.class.getDeclaredFields();
        if (fs.length != 0) {
            fail("The StarSign class contains a field called " + fs[0].getName() + " remove it!");
        }
    }

    @Test
    @Points("02-33.1")
    public void testPrintStars1() {
        MockInOut mio = new MockInOut("");
        StarSign.printStars(3);
        String out = sanitize(mio.getOutput());
        assertTrue("You didn't print any \"*\" when printStars was called.", out.contains("*"));
        assertTrue("You didn't print any line breaks when printStars was called.", out.contains("\n"));
        assertEquals("You printed incorrectly when printStars(3) was called.", "***\n", out);
        mio.close();
    }

    @Test
    @Points("02-33.1")
    public void testPrintStars2() {
        MockInOut mio = new MockInOut("");
        StarSign.printStars(7);
        String out = sanitize(mio.getOutput());
        assertEquals("You printed incorrectly when printStars(7) was called.", "*******", out.trim());
    }

    @Test
    @Points("02-33.2")
    public void testPrintSquare() {
        MockInOut mio = new MockInOut("");
        StarSign.printSquare(3);
        String out = sanitize(mio.getOutput());
        assertEquals("You printed incorrectly when printSquare(3) was called.", "***\n***\n***", out.trim());
    }

    @Test
    @Points("02-33.3")
    public void testPrintRectangle() {
        MockInOut mio = new MockInOut("");
        StarSign.printRectangle(4, 2);
        String out = sanitize(mio.getOutput());
        assertEquals("You printed incorrectly when printRectangle(4,2) was called.", "****\n****", out.trim());
    }

    @Test
    @Points("02-33.4")
    public void testPrintTriangle1() {
        MockInOut mio = new MockInOut("");
        StarSign.printTriangle(3);
        String out = sanitize(mio.getOutput());
        assertEquals("You printed incorrectly when printTriangle(3) was called.", "*\n**\n***", out.trim());
    }
}
