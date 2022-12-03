
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;

@Points("04-20.1 04-20.2")
public class BooksTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void testInputFirst() {
        String input = "To Kill a Mockingbird\n281\n1960\nBeautiful Code\n593\n2007\n";
        String toBePrinted = "name";

        io.setSysIn(input + "\n" + toBePrinted + "\n");
        Main.main(new String[]{});

        assertFalse("When the input is\n" + input + "\n and the choice of information to be printed is '" + toBePrinted + "', the expected output is:\nTo Kill a Mockingbird\nBeautiful Code\n\nNow the output was\n" + io.getSysOut(), io.getSysOut().contains("281") || io.getSysOut().contains("2007"));
        assertTrue("When the input is\n" + input + "\n and the choice of information to be printed is '" + toBePrinted + "', the expected output is:\nTo Kill a Mockingbird\nBeautiful Code\n\nNow the output was\n" + io.getSysOut(), io.getSysOut().contains("To Kill a Mockingbird") && io.getSysOut().contains("Beautiful Code"));
    }

    @Test
    public void testInputSecond() {
        String input = "Winnie-the-Pooh\n50\n1926\n";
        String toBePrinted = "everything";

        io.setSysIn(input + "\n" + toBePrinted + "\n");
        Main.main(new String[]{});

        assertTrue("When the input is\n" + input + "\n and the choice of information to be printed is '" + toBePrinted + "', the expected output is:\nWinnie-the-Pooh, 50 pages, 1926\nNow the output was\n" + io.getSysOut(), io.getSysOut().contains("Winnie-the-Pooh, 50 pages, 1926"));
        assertFalse("When the input is\n" + input + "\n and the choice of information to be printed is '" + toBePrinted + "', the expected output is:\nWinnie-the-Pooh, 50 pages, 1926\nNow the output was\n" + io.getSysOut(), io.getSysOut().contains("To Kill a Mockingbird"));
    }

    @Test
    public void testInputThird() {
        String input = "Winnie-the-Pooh\n50\n1926\nTo Kill a Mockingbird\n281\n1960\n";
        String toBePrinted = "everything";

        io.setSysIn(input + "\n" + toBePrinted + "\n");
        Main.main(new String[]{});

        assertTrue("When the input is\n" + input + "\n and the choice of information to be printed is '" + toBePrinted + "', the expected output is:\nWinnie-the-Pooh, 50 pages, 1926\nTo Kill a Mockingbird, 281 pages, 1960\nNow the output was\n" + io.getSysOut(), io.getSysOut().contains("Winnie-the-Pooh, 50 pages, 1926") && io.getSysOut().contains("To Kill a Mockingbird, 281 pages, 1960"));
    }

}
