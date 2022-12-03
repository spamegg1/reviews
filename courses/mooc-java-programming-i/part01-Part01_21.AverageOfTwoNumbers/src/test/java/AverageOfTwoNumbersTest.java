
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
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

@Points("01-21")
public class AverageOfTwoNumbersTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void correctOutputFirstExample() {
        correctOutput("8", "2");
    }

    @Test
    public void correctOutputSecondExample() {
        correctOutput("9", "2");
    }

    private void correctOutput(String first, String second) {
        String input = first + "\n" + second + "\n";
        io.setSysIn(input);
        AverageOfTwoNumbers.main(new String[]{});
        String[] lines = new String[]{"Give the first number:",
            "Give the second number:",
            "The average is " + (1.0 * Integer.valueOf(first) + Integer.valueOf(second)) / 2
        };

        List<String> rows = rows(io.getSysOut().trim());

        assertEquals("The output was expected to contain " + lines.length + " line" + ((lines.length == 1) ? "" : "s") + ". Now it contained " + rows.size() + ".", lines.length, rows.size());
        for (int i = 0; i < rows.size(); i++) {
            assertEquals("Line " + (i + 1) + " output was incorrect when the input was:\n" + input + "\nExpecting output:\n" + lines[i] + "\nBut the output was:\n" + rows.get(i), lines[i].trim(), rows.get(i).trim());
        }
    }

    @Test
    public void readingIntegerInput() {
        List<String> code = code("AverageOfTwoNumbers.java");
        assertTrue("Read integers from the user input, tip: Integer.valueOf(...).", code.stream().filter(l -> l.contains("Integer.")).count() > 0);
    }

    @Test
    public void correctOrder() {
        List<String> code = code("AverageOfTwoNumbers.java");
        int count = countOccurrences(code, "System.out.println.*Integer.*System.out.println.*Integer.*System.out.println");
        assertTrue("Implement the program so that reading and printing alternate.\nFirst printing, then reading, then printing, etc...", count == 1);
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

    private int countOccurrences(List<String> lines, String search) {
        return countOccurrences(lines.stream().reduce("", (a, b) -> a + " " + b), search);

    }

    private int countOccurrences(String str, String search) {
        int count = 0;
        while (str.matches(".*" + search + ".*")) {
            str = str.replaceFirst(search, "");
            count++;
        }

        return count;
    }

}
