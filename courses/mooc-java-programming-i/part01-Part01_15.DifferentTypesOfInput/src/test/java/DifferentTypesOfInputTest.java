
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

@Points("01-15")
public class DifferentTypesOfInputTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void correctOuputForExample1() {
        correctOutput("Hi", "11", "4.2", "true");
    }

    @Test
    public void correctOuputForExample2() {
        correctOutput("Oobs!", "-4", "3200.1", "false");
    }

    @Test
    public void stringIsCastedIntoInteger() {
        try {
            correctOutput("Oobs!", "non-integer", "3200.1", "false");
            fail("The program must cast the given integer to a integer variable");
        } catch (NumberFormatException e) {
        }
    }
    
    @Test
    public void stringIsCastedIntoDouble() {
        try {
            correctOutput("Oobs!", "7", "non-double", "false");
            fail("The program must cast the given double to a double variable.");
        } catch (NumberFormatException e) {
        }
    }

    private void correctOutput(String str, String integer, String dbl, String bool) {
        String syote = str + "\n" + integer + "\n" + dbl + "\n" + bool + "\n";
        io.setSysIn(syote);
        DifferentTypesOfInput.main(new String[]{});
        String[] lines = new String[]{"Give a string: ",
            "Give an integer: ",
            "Give a double: ",
            "Give a boolean: ",
            "You gave the string " + str,
            "You gave the integer " + Integer.valueOf(integer),
            "You gave the double " + Double.valueOf(dbl),
            "You gave the boolean " + Boolean.valueOf(bool)};

        List<String> output = rivit(io.getSysOut().trim());

        assertEquals("Program output should be " + lines.length + " lines long"  + ". Now it was " + output.size() + ".", lines.length, output.size());
        for (int i = 0; i < output.size(); i++) {
            assertEquals("Output on line " + (i + 1) + " was incorrect with the input:\n" + syote + "\nExpected:\n" + lines[i] + "\nOutput was:\n" + output.get(i), lines[i].trim(), output.get(i).trim());
        }
    }

    @Test
    public void correctOrder() {
        List<String> solutionCode = code("DifferentTypesOfInput.java");
        int no = countOccurrences(solutionCode, "System.out.println.*String.*System.out.println.*Integer.*System.out.println.*Double.*System.out.println.*Boolean.*System.out.println");
        int no2 = countOccurrences(solutionCode, "System.out.println.*String.*System.out.println.*nextInt.*System.out.println.*nextDouble.*System.out.println.*nextBoolean.*System.out.println");
        assertTrue("Implement the program so that reading and printing alternate.\\nFirst printing, then reading, then printing, etc...", no == 1 || no2 == 1);
    }

    private List<String> rivit(String out) {
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
