
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.*;
import static org.junit.Assert.*;

@Points("02-16.2")
public class WhereFromTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void test() {
        int[][] pairs = {{1, 1}, {12, 8}, {50, 100}, {-2,2}};
        for (int[] pair : pairs) {
            test(pair);
        }
    }

    private void test(int[] pair) {
        io.setSysIn(pair[0] + "\n" + pair[1] + "\n");
        int len = io.getSysOut().length();

        ReflectionUtils.newInstanceOfClass(FromWhereToWhere.class);
        FromWhereToWhere.main(new String[0]);
        String output = io.getSysOut().substring(len);

        output = output.replaceAll("[^-\\d]+", " ").trim();
        String[] lines = output.split("\\s+");
        int linesInOutput = (lines.length == 1 && lines[0].isEmpty()) ? 0 : lines.length;

        int linesCount;
        if(pair[0] < pair[1]) {
            linesCount = 0;
        } else {
            linesCount = pair[0]  - pair[1] + 1;
        }

        if (linesCount != linesInOutput) {
            String numbersCount = (linesCount == 1) ? "number": "numbers";
            fail("With the input " + pair[0] + ", " + pair[1] + " output should contain " + linesCount + " " + numbersCount + ", now it contained " + linesInOutput);
        }
        
        if(linesCount == 0) {
            return;
        }

        int firstNumber = Integer.valueOf(lines[0]);
        if(firstNumber != pair[1]) {
            fail("With the input " + pair[0] + ", " + pair[1] + " the first printed number should be " + pair[1] + ", now it was " + firstNumber);
        }
        
        int lastNumber = getLastNumber(output);
        if(lastNumber != pair[0]) {
            fail("With the input " + pair[0] + ", " + pair[1] + " the last printed number should be " + pair[0] + ", now it was " + lastNumber);
        }
    }
    
    private static int getLastNumber(String inputStr) {
        String patternStr = "(?s).*?(-?\\d+)\\s*$";
        Matcher matcher = Pattern.compile(patternStr).matcher(inputStr);
        assertTrue("The output should be a number.", matcher.find());

        int number = Integer.valueOf(matcher.group(1));
        return number;
    }
}
