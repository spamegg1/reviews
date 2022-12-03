
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

@Points("04-24")
public class PrintingAFileTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void test1() {
        test("data.txt", "once", "there was", "a human");
    }

    @Test
    public void test2() {
        test("data.txt", "never", "gonna", "give", "you", "up");
    }

    @Test
    public void testRandom() {
        test("data.txt",
                UUID.randomUUID().toString().substring(0, 4),
                UUID.randomUUID().toString().substring(0, 4),
                UUID.randomUUID().toString().substring(0, 4));
    }

    private void test(String file, String... words) {
        deleteAndCreate(file, words);
        String out = io.getSysOut();

        try {
            PrintingAFile.main(new String[]{});
        } catch (Exception e) {
            fail("An error occured in the execution of the program: " + e.getMessage());
        }

        out = io.getSysOut().replace(out, "");
        String input = "";
        for (String word : words) {
            input = input + word + "\n";
        }

        for (String word : words) {
            textIsIncluded(word, input, out);
        }
    }

    private void textIsIncluded(String shouldBeFound, String contents, String output) {
        assertTrue("When the content of the file data.txt is:\n" + contents + "\nEverything in it should be in the program's output.\nNow the string " + shouldBeFound + " was missing.\nThe output was:\n" + output, output.contains(shouldBeFound));

    }

    private void deleteAndCreate(String file, String... rows) {
        if (new File(file).exists()) {
            try {
                new File(file).delete();
            } catch (Exception e) {
                fail("Deleting the file " + file + " failed when running the tests.\nIf you think the program works as it should, try sending it to the server.");
            }
        }

        try {
            writeToFile(file, rows);
        } catch (Exception e) {
            fail("Creating the file " + file + " failed when running the tests.\nIf you think the program works as it should, try sending it to the server.");
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
