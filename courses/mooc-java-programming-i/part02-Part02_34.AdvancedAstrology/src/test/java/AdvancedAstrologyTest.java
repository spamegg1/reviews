
import fi.helsinki.cs.tmc.edutestutils.MockInOut;
import org.junit.Test;
import static org.junit.Assert.*;
import fi.helsinki.cs.tmc.edutestutils.Points;

import java.lang.reflect.Field;

public class AdvancedAstrologyTest {

    public String sanitize(String s) {
        return s.replace("\r\n", "\n").replace("\r", "\n");
    }

    @Test
    @Points("02-34.1 02-34.2 02-34.3")
    public void verifyNoFields() {
        Field[] fs = AdvancedAstrology.class.getDeclaredFields();
        if (fs.length != 0) {
            fail("The AdvancedAstrology class contains a field called " + fs[0].getName() + " remove it!");
        }
    }

    @Test
    @Points("02-34.1")
    public void testPrintStars() {
        MockInOut mio = new MockInOut("");
        AdvancedAstrology.printStars(3);
        String out = sanitize(mio.getOutput());
        assertTrue("You didn't print any \"*\" when printStars was called.", out.contains("*"));
        assertTrue("You didn't print any spaces when printStars was called.", out.contains("\n"));
        assertEquals("You printed incorrectly when printStars(3) was called.", "***\n", out);
        mio.close();
    }

    @Test
    @Points("02-34.1")
    public void testPrintSpaces1() {
        MockInOut mio = new MockInOut("");
        AdvancedAstrology.printSpaces(1);
        String out = sanitize(mio.getOutput());
        assertEquals("Calling printSpaces(1) should print 1 character, now it printed", 1, out.length());
        assertFalse("Calling printSpaces(1) should not print a line break, but now it did", out.contains("\n"));
        assertEquals("Check printSpaces(1)", " ", out);
        mio.close();
    }

    @Test
    @Points("02-34.1")
    public void testPrintSpaces2() {
        MockInOut mio = new MockInOut("");
        AdvancedAstrology.printSpaces(3);
        String out = sanitize(mio.getOutput());
        assertEquals("Calling printSpaces(3) should print 3 characters, now it printed", 3, out.length());
        assertFalse("Calling printSpaces(3) should not print a line break, but now it did", out.contains("\n"));
        assertEquals("Check printSpaces(3)", "   ", out);
        mio.close();
    }

    @Test
    @Points("02-34.2")
    public void testPrintSmallTriangle() {
        MockInOut mio = new MockInOut("");
        AdvancedAstrology.printTriangle(1);
        String out = sanitize(mio.getOutput());

        int rows = out.split("\n").length;

        assertEquals("when printTriangle(1) was called, wrong amount of lines was printed", 1, rows);

        assertFalse("Are you sure you don't have an extra space in the beginning of the lines?", out.startsWith(" "));
        assertEquals("You printed incorrectly when printTriangle(1) was called.", "*\n", out);
        mio.close();
    }

    @Test
    @Points("02-34.2")
    public void testPrintTriangle() {
        MockInOut mio = new MockInOut("");
        AdvancedAstrology.printTriangle(3);
        String out = sanitize(mio.getOutput());
        assertEquals("You printed incorrectly when printTriangle(3) was  called.", "  *\n **\n***\n", out);
        mio.close();
    }

    @Test
    @Points("02-34.3")
    public void testChristmasTree0() {
        MockInOut mio = new MockInOut("");
        AdvancedAstrology.christmasTree(4);
        String out = sanitize(mio.getOutput());
        assertTrue("When calling christmasTree(4) is called, the first line should contain 3 spaces and then one star, make sure there's not too much or too little spaces",
                out.startsWith("   *"));

        mio.close();
    }

    @Test
    @Points("02-34.3")
    public void testChristmasTree1() {
        MockInOut mio = new MockInOut("");
        AdvancedAstrology.christmasTree(4);
        String out = sanitize(mio.getOutput());
        assertEquals("You printed incorrectly when christmasTree(4) was called.",
                "   *\n  ***\n *****\n*******\n  ***\n  ***\n", out);

        mio.close();
    }

    @Test
    @Points("02-34.3")
    public void testChristmasTree2() {
        MockInOut mio = new MockInOut("");
        AdvancedAstrology.christmasTree(7);
        String out = sanitize(mio.getOutput());
        assertEquals("You printed incorrectly when christmasTree(7).",
                "      *\n"
                + "     ***\n"
                + "    *****\n"
                + "   *******\n"
                + "  *********\n"
                + " ***********\n"
                + "*************\n"
                + "     ***\n"
                + "     ***\n",
                out);

        mio.close();
    }
}
