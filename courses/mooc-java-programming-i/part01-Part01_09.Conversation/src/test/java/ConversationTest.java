
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

@Points("01-09")
public class ConversationTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void correctOutput() {
        io.setSysIn("something\nsomething\n");
        Conversation.main(new String[]{});
        String[] lines = new String[]{"Greetings! How are you doing?", "Oh, how interesting. Tell me more!", "Thanks for sharing!"};

        List<String> rows = rows(io.getSysOut().trim());

        assertEquals("Output was expected to contain " + lines.length + " line" + ((lines.length == 1) ? "" : "s") + ". Now it contained " + rows.size() + ".", lines.length, rows.size());
        for (int i = 0; i < rows.size(); i++) {
            // An old version of the exercise
            if (i == 1 && rows.get(i).trim().equals("Oh how interesting, tell me more!")) {
                continue;
            }
            assertEquals("Line " + (i + 1) + " output was incorrect. \nExpecting output:\n" + lines[i] + "\nBut output was:\n" + rows.get(i), lines[i].trim(), rows.get(i).trim());
        }
    }

    @Test
    public void correctOrder() {
        List<String> code = code("Conversation.java");
        int count = countOccurrences(code, "System.out.println.*nextLine.*System.out.println.*nextLine.*System.out.println");
        assertTrue("Implement the program so that reading and printing alternate.\\nFirst printing, then reading, then printing, etc...", count == 1);
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

    private int countOccurrences(List<String> rows, String search) {
        return countOccurrences(rows.stream().reduce("", (a, b) -> a + " " + b), search);

    }

    private int countOccurrences(String str, String search) {
        System.out.println("Counting..");
        System.out.println(str);
        System.out.println("---");
        System.out.println(search);
        int lkm = 0;
        while (str.matches(".*" + search + ".*")) {
            str = str.replaceFirst(search, "");
            lkm++;
        }

        return lkm;
    }

}
