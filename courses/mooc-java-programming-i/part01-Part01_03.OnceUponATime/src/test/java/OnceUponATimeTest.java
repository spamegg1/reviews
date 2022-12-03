
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

@Points("01-03")
public class OnceUponATimeTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void correctOutput() {
        OnceUponATime.main(new String[]{});
        String[] lines = new String[]{"Once upon a time",
            "there was",
            "a program"};

        List<String> rows = rows(io.getSysOut().trim());
        
        assertEquals("Output was expected to contain " + lines.length + " line" + ((lines.length == 1) ? "" : "s") + ". Now it contained " + rows.size() + ".", lines.length, rows.size());
        for (int i = 0; i < rows.size(); i++) {
            assertEquals("Line " + (i + 1) + " output was incorrect. \nExpecting output:\n" + lines[i] + "\nBut output was:\n" + rows.get(i), lines[i].trim(), rows.get(i).trim());
        }
    }

    @Test
    public void numberOfSystemOutPrintlnCommands() {
        List<String> code = code("OnceUponATime.java");
        int occurrences = countOccurrences(code, "System.out.println");
        assertEquals("The program was expected to contain 3 'System.out.println' commands. Now there were " + occurrences + ".", 3, occurrences);
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

    private int countOccurrences(List<String> rows, String search) {
        int count = 0;
        for (String row : rows) {
            while (row.contains(search)) {
                row = row.replaceFirst(search, "");
                count++;
            }
        }

        return count;
    }

}
