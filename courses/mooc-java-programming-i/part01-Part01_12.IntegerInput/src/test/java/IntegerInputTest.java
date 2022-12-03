
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

@Points("01-12")
public class IntegerInputTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void correctOutputWhenNumber8() {
        correctOutput("8");
    }

    @Test
    public void correctOutputWhenNumber300() {
        correctOutput("300");
    }

    @Test
    public void inputConvertedToInteger() {
        try {
            correctOutput("not a number");
            fail("The program must convert the given number to an integer. Currently there is no conversion.");
        } catch (NumberFormatException e) {

        }

    }

    private void correctOutput(String number) {
        io.setSysIn(number + "\n");
        IntegerInput.main(new String[]{});
        String[] lines = new String[]{"Give a number:",
            "You gave the number " + number};

        List<String> rows = rows(io.getSysOut().trim());

        assertEquals("Output was expected to contain " + lines.length + " row" + ((lines.length == 1) ? "" : "s") + ". Now there were " + rows.size() + " rows.", lines.length, rows.size());
        for (int i = 0; i < rows.size(); i++) {
            assertEquals("Row number " + (i + 1) + " produces incorrect output when the given number is " + number + ".\nExpected string:\n" + lines[i] + "\nBut output was:\n" + rows.get(i), lines[i].trim(), rows.get(i).trim());
        }
    }

    @Test
    public void correctOrder() {
        List<String> code = code("IntegerInput.java");
        int count = countOccurrences(code, "System.out.println.*Integer.*System.out.println");
        int count2 = countOccurrences(code, "System.out.println.*parseInt.*System.out.println");
        assertTrue("Implement the program so that reading and printing alternate.\\nFirst printing, then reading, and then printing,", count == 1 || count2 == 1);
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
