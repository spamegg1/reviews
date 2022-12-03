
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

@Points("01-14")
public class BooleanInputTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void correctOutputWhenSomething() {
        correctOutput("something");
    }

    @Test
    public void correctOutputWhenTrue() {
        correctOutput("true");
    }

    @Test
    public void correctOutputWhenFalse() {
        correctOutput("false");
    }

    @Test
    public void inputNotConvertedToNumber() {
        try {
            correctOutput("this is not a number");
        } catch (NumberFormatException e) {
            fail("The program should not convert the input to a number. ");
        }

    }

    private void correctOutput(String input) {
        io.setSysIn(input + "\n");
        BooleanInput.main(new String[]{});
        String[] lines = new String[]{"Write something:",
            "True or false? " + Boolean.valueOf(input)};

        List<String> rows = rows(io.getSysOut().trim());

        assertEquals("Output was expected to contain " + lines.length + " row" + ((lines.length == 1) ? "" : "s") + ". Now there were " + rows.size() + " rows.", lines.length, rows.size());
        for (int i = 0; i < rows.size(); i++) {
            assertEquals("Row number " + (i + 1) + " produces incorrect output when the input is " + input + ".\nExpected string:\n" + lines[i] + "\nBut output was:\n" + rows.get(i), lines[i].trim(), rows.get(i).trim());
        }
    }

    @Test
    public void correctOrder() {
        List<String> code = code("BooleanInput.java");
        int count = countOccurrences(code, "System.out.println.*Boolean.*System.out.println");
        int count2 = countOccurrences(code, "System.out.println.*nextBoolean.*System.out.println");
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
