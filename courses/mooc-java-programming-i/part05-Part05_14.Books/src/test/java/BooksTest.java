
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Rule;
import org.junit.Test;

public class BooksTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Points("05-14")
    @Test
    public void addingSameBookTwice() {
        test("book\n1990\nbook\n1990\n\n", 1);
    }

    @Points("05-14")
    @Test
    public void addingSameBookThrice() {
        test("abc\n1991\nabc\n1991\nabc\n1991\n\n", 1);
    }

    @Points("05-14")
    @Test
    public void sameNamesDifferentYears() {
        test("alphabet book\n2017\nalphabet book\n2018\n\n", 2);
    }

    @Points("05-14")
    @Test
    public void differentNamesSameYears() {
        test("alphabet book\n2018\nbetabet book\n2018\n\n", 2);
    }

    @Points("05-14")
    @Test
    public void test1() {
        test("book1\n2018\nbook2\n2018\nbook3\n2015\nbook4\n2015\nbook4\n2015\nbook5\n2018\nbook5\n2018\n\n", 5);
    }

    private void test(String input, int expectedNumOfBooks) {
        io.setSysIn(input);

        try {
            Main.main(new String[]{});
        } catch (Throwable t) {
            fail("Failed to execute the program.");
        }

        String out = io.getSysOut();

        for (int i = 0; i < 10; i++) {
            if (expectedNumOfBooks == i) {
                continue;
            }

            assertFalse("The number of books was not what was expected. Try your program with the input:\n" + input,
                    out.contains("Thank you! Books added: " + i)); 
        }

        String expected = "Thank you! Books added: " + expectedNumOfBooks;
        assertTrue("Expected the output to contain the string:\n\"" + expected +
                "\"\nThis was not the case. Try the program with the input:\n" + input, out.contains(expected));
    }

}
