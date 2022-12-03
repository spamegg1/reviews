
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Rule;
import org.junit.Test;

@Points("04-27")
public class IsItInTheFileTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void found1() throws Throwable {
        testIsItInTheFile("names.txt", "ada", true, false);
    }

    @Test
    public void found2() throws Throwable {
        testIsItInTheFile("names.txt", "testi", true, false);
    }

    @Test
    public void fileError() throws Throwable {
        testIsItInTheFile("names-nonexistent.txt", "ada", false, true);
    }

    @Test
    public void notFound1() throws Throwable {
        testIsItInTheFile("names.txt", "antti", false, false);
    }

    @Test
    public void notFound2() throws Throwable {
        testIsItInTheFile("names.txt", "elina", false, false);
    }

    public void testIsItInTheFile(String file, String searchedFor, boolean shouldBeFound, boolean tiedostovirhe) throws Throwable {
        io.setSysIn(file + "\n" + searchedFor + "\n");
        IsItInTheFile.main(new String[]{});

        if (tiedostovirhe) {
            assertTrue("When reading the file \"" + file + "\", the message \"Reading the file " + file + " failed.\" should be printed. The output was:\n" + io.getSysOut(), io.getSysOut().contains("Reading the file"));
            return;
        } else {
            assertFalse("When reading the file \"" + file + "\", the message \"Reading the file " + file + " failed.\" should not be printed. The output was:\n" + io.getSysOut(), io.getSysOut().contains("Reading the file"));
        }

        if (shouldBeFound) {
            assertTrue("When searching for the string \"" + searchedFor + "\" in the file \"" + file + "\" it should be found.\nExpected the output to contain the string \"Found\".\nThe output was:\n" + io.getSysOut(), io.getSysOut().contains("Found"));
            assertFalse("When searching for the string \"" + searchedFor + "\" in the file \"" + file + "\" it should be found.\nExpected the output not to contain the string \"Not found\".\nThe output was:\n" + io.getSysOut(), io.getSysOut().contains("Not found"));
        } else {
            assertFalse("When searching for the string \"" + searchedFor + "\" in the file \"" + file + "\" it should not be found.\nExpected the output not to contain the string \"Found\".\nThe output was:\n" + io.getSysOut(), io.getSysOut().contains("Found"));
            assertTrue("When searching for the string \"" + searchedFor + "\" in the file \"" + file + "\" it should not be found.\nExpected the output not to contain the string \"Not found\".\nThe output was:\n" + io.getSysOut(), io.getSysOut().contains("Not found"));

        }
    }
}
