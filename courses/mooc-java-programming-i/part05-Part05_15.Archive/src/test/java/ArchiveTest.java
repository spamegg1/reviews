
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.fail;
import org.junit.Rule;
import org.junit.Test;

public class ArchiveTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Points("05-15.1")
    @Test
    public void addingAndPrintingOneItem() {
        test("identifier\nitem5\n\n", "identifier: item5");
    }

    @Points("05-15.1")
    @Test
    public void addingAndPrintingTwoItems() {
        test("identifier\nitem\nsecond\nsecondItem\n\n", "identifier: item", "second: secondItem");
    }

    @Points("05-15.1")
    @Test
    public void readingStoppedWithEmptyName() {
        test("identifier\nitem3\nsecond\n\n", "identifier: item3");
    }

    @Points("05-15.2")
    @Test
    public void printingTwoEqualItemsOnlyPrintsOneItem() {
        test("identifier\nitem2\nidentifier\nitem2\n\n", "identifier: item2");
    }

    @Points("05-15.2")
    @Test
    public void printingTwoItemsWithDifferentNamesButSameIdentifiersOnlyContainsOneItem() {
        test("identifier\nitem1\nidentifier\nitem2\n\n", "identifier: item1");
    }

    @Points("05-15.2")
    @Test
    public void printingTwoItemsWithSameNamesButDifferentIdentifiersContainsTwoItems() {
        test("identifier1\nitem\nidentifier2\nitem\n\n", "identifier1: item", "identifier2: item");
    }

    private void test(String input, String... expectedOutput) {
        io.setSysIn(input);

        try {
            Main.main(new String[]{});
        } catch (Throwable t) {
            fail("Executing the program failed.");
        }

        List<String> rows = new ArrayList<>();
        Arrays.stream(io.getSysOut().split("\n")).filter(r -> r.contains(":")).forEach(r -> rows.add(r));

        if (rows.size() != expectedOutput.length) {
            fail("With the input:" + input + " the print had " + rows.size() + " items:\n" + rows + "\nThe expected number of items was " + expectedOutput.length + ". \n Check your program -- also ensure that the only place you use colons in is when you print items.");
        }

        NEXT:
        for (String expected : expectedOutput) {
            for (String row : rows) {
                if (row.contains( expected)) {
                    continue NEXT;
                }
            }

            fail("With the input:\n" + input + "\n the output was expected to contain the string:\n\"" +  expected + "\"\nThis was not the case. Test your program with this input.");

        }
    }

}
