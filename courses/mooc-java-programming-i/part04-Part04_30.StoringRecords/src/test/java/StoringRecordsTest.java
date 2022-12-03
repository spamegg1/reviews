
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Rule;
import org.junit.Test;

@Points("04-30")
public class StoringRecordsTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void test1() {
        test("tiedosto1.txt", "saul,32", "tina,30", "aaron,1", "matt,44", "lena,0");
    }

    @Test
    public void test2() {
        test("tiedosto2.txt", "saul,41", "tina,9", "matt,13", "anton,42", "amy,0", "lily,13", "lena,41");
    }

    private void test(String fileName, String... data) {
        deleteAndCreateNew(fileName, data);
        
        List<Person> expected = Arrays.stream(data).map(line -> line.split(",")).map(arr -> new Person(arr[0], Integer.valueOf(arr[1]))).collect(Collectors.toList());
        List<Person> current = StoringRecords.readRecordsFromFile(fileName);
        
        
        String input = "";
        for (String osa : data) {
            input = input + osa + "\n";
        }
        
        assertTrue("Number of records returned as list is not the same as number of records on the file.\n Test your method with a file containing the lines:\n" + input, expected.size() == current.size());

        NEXT:
        for (Person o : expected) {
            for (Person t : current) {
                if(t == null || t.getName() == null) {
                    continue;
                }
                
                if(o.getName().equals(t.getName()) && o.getAge() == t.getAge()) {
                    continue NEXT;
                }
            }
            
            fail("A person with the name" + o.getName() + " and age " + o.getAge() + " could not be found from the file.\n The used file had the following records:\n" + input);            
        }
        

    }

    private List<String> lines(String out) {
        return Arrays.asList(out.split("\n")).stream().map(l -> l.trim()).filter(l -> !l.isEmpty()).collect(Collectors.toList());
    }

    private void deleteAndCreateNew(String fileName, String... lines) {
        remove(fileName);

        try {
            writeToFile(fileName, lines);
        } catch (Exception e) {
            fail("Could not create file " + fileName + " when running the tests. \n If you think your program works correctly, submit it to the server.");
        }

    }

    private void remove(String fileName) {
        if (new File(fileName).exists()) {
            try {
                new File(fileName).delete();
            } catch (Exception e) {
                fail("Could not delete file " + fileName + " when running the tests. \n If you think your program works correctly, submit it to the server.");
            }
        }
    }

    private void writeToFile(String fileName, String[] linesToWrite) throws FileNotFoundException {
        try (PrintWriter pw = new PrintWriter(new File(fileName))) {
            for (String line : linesToWrite) {
                pw.println(line);
            }
            pw.flush();
        }
    }
}
