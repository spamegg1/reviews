import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Rule;
import org.junit.Test;

@Points("02-16.1")
public class WhereToTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void verifyOutput() {
        io.setSysIn("3\n");

        ReflectionUtils.newInstanceOfClass(FromWhereToWhere.class);
        try {
            FromWhereToWhere.main(new String[0]);
        } catch (NoSuchElementException e) {
            return;
        }

        String output = io.getSysOut();
        output = output.replaceAll("[^\\d]", " ");
        output = output.trim();
        output = output.replace("1", "");
        output = output.replace("2", "");
        output = output.replace("3", "");

        output = output.trim();
        if (!output.isEmpty()) {
            fail("When you're printing numbers until 3, you should only print numbers 1, 2, and 3. Now you printed: " + output);
        }
    }

    @Test
    public void testi() {
        int[] numbers = {1, 50, 100};
        for (int number : numbers) {
            testaa(number);
        }
    }

    private void testaa(int whereTo) {
        io.setSysIn(whereTo + "\n");

        ReflectionUtils.newInstanceOfClass(FromWhereToWhere.class);
        try {
            FromWhereToWhere.main(new String[0]);
        } catch (NoSuchElementException e) {
            return;
        }

        int lastNumber = getLastNumber(io.getSysOut(), whereTo);

        if (whereTo != lastNumber) {
            fail("There should be " + whereTo + " on the last line, now there was " + lastNumber);
        }
    }

    private static int getLastNumber(String inputStr, int last) {
        String patternStr = "(?s).*?(\\d+)\\s*$";
        Matcher matcher = Pattern.compile(patternStr).matcher(inputStr);
        assertTrue("You should print numbers. With the user input "+last+ ", \""+inputStr+"\"" + "was printed", matcher.find());

        int number = Integer.valueOf(matcher.group(1));
        return number;
    }
}
