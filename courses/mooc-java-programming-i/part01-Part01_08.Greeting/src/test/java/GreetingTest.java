
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@Points("01-08")
public class GreetingTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void adaCorrectOutput() {
        correctOutput("Ada");
    }

    @Test
    public void lilyCorrectOutput() {
        correctOutput("Lily");
    }

    private void correctOutput(String input) {
        io.setSysIn("" + input + "\n");
        Greeting.main(new String[]{});
        String[] lines = new String[]{"What's your name?", "Hi " + input};

        List<String> rows = rows(io.getSysOut().trim());

        assertEquals("Output was expected to contain " + lines.length + " line" + ((lines.length == 1) ? "" : "s") + ". Now it contained " + rows.size() + ".", lines.length, rows.size());
        for (int i = 0; i < rows.size(); i++) {
            assertEquals("Line " + (i + 1) + " output was incorrect (user input was " + input + ". \nExpecting output:\n" + lines[i] + "\nBut output was:\n" + rows.get(i), lines[i].trim(), rows.get(i).trim());
        }
    }

    private List<String> rows(String out) {
        return Arrays.asList(out.split("\n"));
    }

    private List<String> code(String file) {
        try {
            return Files.lines(Paths.get("src", "main", "java", file)).collect(Collectors.toList());
        } catch (IOException e) {
            fail("Reading file " + file + " failed. Write your code in the file " + file);
        }

        return new ArrayList<>();
    }

}
