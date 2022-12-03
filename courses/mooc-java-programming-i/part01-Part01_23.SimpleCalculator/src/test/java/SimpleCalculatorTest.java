
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

@Points("01-23")
public class SimpleCalculatorTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void firstExampleHasCorrectOutput() {
        correctOutput("8", "2");
    }

    @Test
    public void secondExampleHasCorrectOutput() {
        correctOutput("9", "5");
    }

    private void correctOutput(String first, String second) {
        String input = first + "\n" + second + "\n";
        io.setSysIn(input);
        SimpleCalculator.main(new String[]{});
        String[] lines = new String[]{"Give the first number:",
            "Give the second number:",
            first + " + " + second + " = " + (Integer.valueOf(first) + Integer.valueOf(second)),
            first + " - " + second + " = " + (Integer.valueOf(first) - Integer.valueOf(second)),
            first + " * " + second + " = " + (Integer.valueOf(first) * Integer.valueOf(second)),
            first + " / " + second + " = " + (1.0 * Integer.valueOf(first) / Integer.valueOf(second))
        };

        List<String> inputLines = inputLines(io.getSysOut().trim());

        assertEquals("Output was expected to be " + lines.length + " lines long"  + ". Output was " + inputLines.size() + " lines long.", lines.length, inputLines.size());
        for (int i = 0; i < inputLines.size(); i++) {
            assertEquals("Line " + (i + 1) + " incorrect with input:\n" + input + "\nExpected:\n" + lines[i] + "\nBut got:\n" + inputLines.get(i), lines[i].trim(), inputLines.get(i).trim());
        }
    }

    @Test
    public void correctOrder() {
        List<String> sourceCode = code("SimpleCalculator.java");
        int no = countOccurrences(sourceCode, "System.out.println.*Integer.*System.out.println.*Integer.*System.out.println");
        int no2 = countOccurrences(sourceCode, "System.out.println.*nextInt.*System.out.println.*nextInt.*System.out.println");
        assertTrue("Implement the program so that reading and printing alternate.\nFirst printing, then reading, then printing, etc...", no == 1 || no2 == 1);
    }

    private List<String> inputLines(String out) {
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
        return contOccurrences(lines.stream().reduce("", (a, b) -> a + " " + b), search);

    }

    private int contOccurrences(String str, String search) {
        int count = 0;
        while (str.matches(".*" + search + ".*")) {
            str = str.replaceFirst(search, "");
            count++;
        }

        return count;
    }

}
