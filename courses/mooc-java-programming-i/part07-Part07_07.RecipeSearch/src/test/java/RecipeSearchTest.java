import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import java.io.File;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import static org.junit.Assert.assertFalse;
import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Rule;

public class RecipeSearchTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    @Points("07-07.1")
    public void readingAndListing1() throws Throwable {
        test(Arrays.asList("Pancake dough", "15", "milk", "", "Meatballs", "10", "ground meat"), Arrays.asList("list"), Arrays.asList("Pancake dough, cooking time: 15", "Meatballs, cooking time: 10"), Arrays.asList(""));
    }

    @Test
    @Points("07-07.1")
    public void readingAndListing2() throws Throwable {
        test(Arrays.asList("Sausage soup", "20", "sausage", "potato", "water", "", "Meatballs", "10", "ground meat"), Arrays.asList("list"), Arrays.asList("Sausage soup, cooking time: 20", "Meatballs, cooking time: 10"), Arrays.asList("Pancake dough, cooking time: 15"));
    }

    @Test
    @Points("07-07.1")
    public void readingAndNoListing() throws Throwable {
        test(Arrays.asList("Pancake dough", "15", "milk", "", "Meatballs", "10", "ground meat"), Arrays.asList(""), Arrays.asList(""), Arrays.asList("Pancake dough, cooking time: 15", "Meatballs, cooking time: 10"));
    }

    @Test
    @Points("07-07.2")
    public void searchByName1() throws Throwable {
        test(Arrays.asList("Pancake dough", "15", "milk", "", "Meatballs", "10", "ground meat"), Arrays.asList("find name", "a"), Arrays.asList("Pancake dough, cooking time: 15", "Meatballs, cooking time: 10"), Arrays.asList(""));
    }

    @Test
    @Points("07-07.2")
    public void searchByName2() throws Throwable {
        test(Arrays.asList("Sausage soup", "20", "sausage", "potato", "water", "", "Meatballs", "10", "ground meat"), Arrays.asList("find name", "soup"), Arrays.asList("Sausage soup, cooking time: 20"), Arrays.asList("Pancake dough, cooking time: 15", "Meatballs, cooking time: 10"));
    }

    @Test
    @Points("07-07.3")
    public void searchByCookingTime1() throws Throwable {
        test(Arrays.asList("Pancake dough", "15", "milk", "", "Meatballs", "10", "ground meat"), Arrays.asList("find cooking time", "15"), Arrays.asList("Pancake dough, cooking time: 15", "Meatballs, cooking time: 10"), Arrays.asList(""));
    }

    @Test
    @Points("07-07.3")
    public void searchByCookingTime2() throws Throwable {
        test(Arrays.asList("Sausage soup", "20", "sausage", "potato", "water", "", "Meatballs", "10", "ground meat"), Arrays.asList("find cooking time", "10"), Arrays.asList("Meatballs, cooking time: 10"), Arrays.asList("Sausage soup, cooking time: 20"));
    }

    @Test
    @Points("07-07.3")
    public void searchByCookingTime3() throws Throwable {
        test(Arrays.asList("Sausage soup", "20", "sausage", "potato", "water", "", "Meatballs", "10", "ground meat"), Arrays.asList("find cooking time", "5"), Arrays.asList(""), Arrays.asList("Sausage soup, cooking time: 20", "Meatballs, cooking time: 10"));
    }

    @Test
    @Points("07-07.4")
    public void searchByIngredient1() throws Throwable {
        test(Arrays.asList("Pancake dough", "15", "milk", "", "Meatballs", "10", "ground meat"), Arrays.asList("find ingredient", "ilk"), Arrays.asList(""), Arrays.asList("Pancake dough, cooking time: 15", "Meatballs, cooking time: 10"));
    }

    @Test
    @Points("07-07.4")
    public void searchByIngredient2() throws Throwable {
        test(Arrays.asList("Pancake dough", "15", "milk", "", "Meatballs", "10", "ground meat"), Arrays.asList("find ingredient", "milk"), Arrays.asList("Pancake dough, cooking time: 15"), Arrays.asList("Meatballs, cooking time: 10"));
    }

    @Test
    @Points("07-07.4")
    public void searchByIngredient3() throws Throwable {
        test(Arrays.asList("Sausage soup", "20", "sausage", "potato", "water", "", "Tofu rolls", "75", "tofu", "onion", "cucumber", "avocado"), Arrays.asList("find ingredient", "cucumber"), Arrays.asList("Tofu rolls, cooking time: 75"), Arrays.asList("Sausage soup, cooking time: 20"));
    }

    public void test(List<String> fileContents, List<String> commandList, List<String> expectedPrints, List<String> notExpectedPrints) {
        String file = createTestFile(fileContents);

        String commands = file + "\n";
        for (String command : commandList) {
            commands += command + "\n";
        }
        commands += "stop\n";

        io.setSysIn(commands);
        RecipeSearch.main(new String[]{});

        String print = io.getSysOut();
        for (String expected : expectedPrints) {
            if (expected.trim().isEmpty()) {
                continue;
            }

            assertTrue("Expected the output to contain the string " + expected
                    + ".\nWhen the contents of the file are:\n" + byRows(fileContents)
                    + "\nTest the program with the commands:\n" + commands + ".", print.contains(expected));
        }

        for (String notExpected : notExpectedPrints) {
            if (notExpected.trim().isEmpty()) {
                continue;
            }

            assertFalse("Expected that the output would not contain the string " + notExpected
                    + ".\nWhen the contents of the file are:\n" + byRows(fileContents) +
                    "\nTest the program with the commands:\n" + commands + ".", print.contains(notExpected));
        }

        try {
            new File(file).delete();
        } catch (Throwable t) {
            System.out.println("Failed to delete the test file " + file);
        }
    }

    private String byRows(List<String> rows) {
        String s = "";
        for (String row : rows) {
            s += row + "\n";
        }

        return s;
    }

    public String createTestFile(List<String> contents) {
        String fileName = "test-" + UUID.randomUUID().toString().substring(0, 6);

        try (PrintWriter pw = new PrintWriter(fileName)) {
            for (String row : contents) {
                pw.println(row);
            }
            pw.flush();
        } catch (Exception e) {
            fail("Failed to create the test file that contains recipes.\nTry running the tests on the TMC server.");

        }

        return fileName;
    }
}
