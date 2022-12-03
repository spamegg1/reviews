
import fi.helsinki.cs.tmc.edutestutils.Points;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import org.junit.Test;
import static org.junit.Assert.*;

@Points("04-23")
public class CreatingANewFileTest {

    @Test
    public void fileExists() {
        assertTrue(new File("file.txt").exists());
    }

    @Test
    public void containsTextHelloWorld() throws FileNotFoundException {
        fileExists();

        try (Scanner s = new Scanner(new File("file.txt"))) {
            String row = s.nextLine();
            assertTrue("The first line of the file \"file.txt\" should have the text \"Hello, world!\".\nNow the text was:\n" + row, row.startsWith("Hello, world!"));
        }

    }

}
