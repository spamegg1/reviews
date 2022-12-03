
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.UUID;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Rule;
import org.junit.Test;

@Points("04-25")
public class PrintingASpecifiedFileTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void test1() {
        test("file1.txt", "once", "there was", "a human");
    }

    @Test
    public void test2() {
        test("file2.txt", "never", "gonna", "give", "you", "up");
    }

    @Test
    public void testRandom() {
        test("file" + UUID.randomUUID().toString().substring(0, 4) + ".txt",
                UUID.randomUUID().toString().substring(0, 4),
                UUID.randomUUID().toString().substring(0, 4),
                UUID.randomUUID().toString().substring(0, 4));
    }

    private void test(String file, String... words) {
        deleteAndCreate(file, words);
        io.setSysIn(file + "\n");

        String out = io.getSysOut();

        try {
            PrintingASpecifiedFile.main(new String[]{});
        } catch (Exception e) {
            fail("An error occured in the execution of the program: " + e.getMessage());
        } finally {
            delete(file);
        }

        out = io.getSysOut().replace(out, "");
        String input = "";
        for (String word : words) {
            input = input + word + "\n";
        }

        for (String word : words) {
            checkTextIncluded(word, input, out);
        }
    }

    private void checkTextIncluded(String shouldBeFound, String contents, String output) {
        assertTrue("When the contents of the file are:\n" + contents + "\neverything in the file should be printed.\nNow the string " + shouldBeFound + " was missing.\nThe output was:\n" + output, output.contains(shouldBeFound));

    }

    private void deleteAndCreate(String file, String... rivit) {
        delete(file);

        try {
            writeToFile(file, rivit);
        } catch (Exception e) {
            fail("Creating the file " + file + " failed when running the tests.\nIf you think that the program works correctly, send it to the server.");
        }

    }

    private void delete(String file) {
        if (new File(file).exists()) {
            try {
                new File(file).delete();
            } catch (Exception e) {
                fail("Deleting the file " + file + " failed when running the tests.\nIf you think that the program works correctly, send it to the server.");
            }
        }
    }

    private void writeToFile(String file, String[] rows) throws FileNotFoundException {
        try (PrintWriter pw = new PrintWriter(new File(file))) {
            for (String row : rows) {
                pw.println(row);
            }
            pw.flush();
        }
    }
}
