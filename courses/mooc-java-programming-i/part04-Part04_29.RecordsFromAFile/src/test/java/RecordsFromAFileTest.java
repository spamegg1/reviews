
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Rule;
import org.junit.Test;

@Points("04-29")
public class RecordsFromAFileTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void test1() {
        test("file1.txt", "sauli,32", "tarja,30", "aaro,1", "martti,44", "lennu,0");
    }

    @Test
    public void test2() {
        test("file2.txt", "sauli,41", "tarja,9", "martti,13", "anton,42", "aamu,0", "lilja,13", "leena,41");
    }

    private void test(String file, String... data) {
        deleteAndCreate(file, data);
        io.setSysIn(file + "\n");

        String out = io.getSysOut();

        try {
            RecordsFromAFile.main(new String[]{});
        } catch (Exception e) {
            fail("An error occured while executing the program: " + e.getMessage());
        } finally {
            delete(file);
        }

        out = io.getSysOut().replace(out, "");

        String input = "";
        for (String part : data) {
            input = input + part + "\n";
        }

        for (String part : data) {
            String[] parts = part.split(",");
            String name = parts[0];
            int age = Integer.valueOf(parts[1]);

            if (age == 1) {
                long hits = lines(out).stream().filter(r -> r.contains(name) && r.contains(age + " year")).count();
                assertTrue("When the input is:\n" + input + "the output should contain the line:\n" + name + ", age: " + age + " year\nThe output was:\n" + out, hits == 1);
            } else {
                long hits = lines(out).stream().filter(r -> r.contains(name) && r.contains(age + " years")).count();
                assertTrue("When the input is:\n" + input + "the output should contain the line:\n" + name + ", age: " + age + " years\nThe output was:\n" + out, hits == 1);
            }
        }

    }

    private List<String> lines(String out) {
        return Arrays.asList(out.split("\n")).stream().map(l -> l.trim()).filter(l -> !l.isEmpty()).collect(Collectors.toList());
    }

    private void deleteAndCreate(String file, String... lines) {
        delete(file);

        try {
            writeToFile(file, lines);
        } catch (Exception e) {
            fail("Creating the file " + file + " failed while running the tests.\nIf you think that your program works correctly, then you may submit it to the server.");
        }

    }

    private void delete(String file) {
        if (new File(file).exists()) {
            try {
                new File(file).delete();
            } catch (Exception e) {
                fail("Creating the file " + file + " failed while running the tests.\nIf you think that your program works correctly, then you may submit it to the server.");
            }
        }
    }

    private void writeToFile(String file, String[] lines) throws FileNotFoundException {
        try (PrintWriter pw = new PrintWriter(new File(file))) {
            for (String rivi : lines) {
                pw.println(rivi);
            }
            pw.flush();
        }
    }
}
