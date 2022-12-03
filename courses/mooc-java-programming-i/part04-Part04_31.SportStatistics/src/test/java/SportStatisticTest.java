
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

public class SportStatisticTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    @Points("04-31.1")
    public void games1() {
        String filename = "file1.txt";
        String textToWriteToFile = "cats,mice,32,34\nbatman,superman,30,31\nbatman,robin,1,0\ncats,dogs,3,0";
        deleteAndCreateNew(filename, textToWriteToFile.split("\n"));
        io.setSysIn(filename + "\ndogs\n");
        
        try {
            SportStatistics.main(new String[]{});
        } catch (Exception e) {
            fail("Error when running the program: " + e.getMessage());
        } finally {
            deleteFile(filename);
        }
       
        assertTrue("With the file:\n" + textToWriteToFile + "\nWhen searching for dogs, the output should contain \"Games: 1\".\nThe output was:\n" + io.getSysOut(), lines(io.getSysOut()).stream().filter(l -> l.contains("Games: 1")).count() == 1);
    }

    @Test
    @Points("04-31.1")
    public void games2() {
        String filename = "file1.txt";
        String textToWriteToFile = "cats,mice,32,34\nbatman,superman,30,31\nbatman,robin,1,0\ncats,dogs,3,0";
        deleteAndCreateNew(filename, textToWriteToFile.split("\n"));
        io.setSysIn(filename + "\nbatman\n");
        
        try {
            SportStatistics.main(new String[]{});
        } catch (Exception e) {
            fail("Error when running the program: " + e.getMessage());
        } finally {
            deleteFile(filename);
        }
       
        assertTrue("With the file:\n" + textToWriteToFile + "\nWhen searching for batman, the output should contain \"Games: 2\".\nThe output was:\n" + io.getSysOut(), lines(io.getSysOut()).stream().filter(l -> l.contains("Games: 2")).count() == 1);
    }

    @Test
    @Points("04-31.1")
    public void games3() {
        String filename = "file2.txt";
        String textToWriteToFile = "cats,mice,32,34\nbatman,superman,30,31\nbatman,robin,1,0\ncats,dogs,3,0";
        deleteAndCreateNew(filename, textToWriteToFile.split("\n"));
        io.setSysIn(filename + "\nsupergirl\n");
        
        try {
            SportStatistics.main(new String[]{});
        } catch (Exception e) {
            fail("Error when running the program: " + e.getMessage());
        } finally {
            deleteFile(filename);
        }
       
        assertTrue("With the file:\n" + textToWriteToFile + "\nWhen searching for supergirl, the output should contain \"Games: 0\".\nThe output was:\n" + io.getSysOut(), lines(io.getSysOut()).stream().filter(l -> l.contains("Games: 0")).count() == 1);
    }

    @Test
    @Points("04-31.2")
    public void winsAndLosses1() {
        String filename = "file1.txt";
        String textToWriteToFile = "cats,mice,32,34\nbatman,superman,30,31\nbatman,robin,1,0\ncats,dogs,3,0";
        deleteAndCreateNew(filename, textToWriteToFile.split("\n"));
        io.setSysIn(filename + "\nmice\n");
        
        try {
            SportStatistics.main(new String[]{});
        } catch (Exception e) {
            fail("Error running the program: " + e.getMessage());
        } finally {
            deleteFile(filename);
        }
       
        assertTrue("With the file:\n" + textToWriteToFile + "\nWhen searcing for mice the output should have the line \" Wins: 1\".\nthe output was:\n" + io.getSysOut(), lines(io.getSysOut()).stream().filter(l -> l.contains("Wins: 1")).count() == 1);
        assertTrue("With the file:\n" + textToWriteToFile + "\nWhen searcing for mice the output should have the line \" Losses: 0\".\nthe output was:\n" + io.getSysOut(), lines(io.getSysOut()).stream().filter(l -> l.contains("Losses: 0")).count() == 1);
    }

    @Test
    @Points("04-31.2")
    public void winsAndLosses2() {
        String fileName = "file1.txt";
        String textToWriteToFile = "cats,mice,32,34\nbatman,superman,30,31\nbatman,robin,1,0\ncats,dogs,3,0";
        deleteAndCreateNew(fileName, textToWriteToFile.split("\n"));
        io.setSysIn(fileName + "\ncats\n");
        
        try {
            SportStatistics.main(new String[]{});
        } catch (Exception e) {
            fail("Error when running the program: " + e.getMessage());
        } finally {
            deleteFile(fileName);
        }
       
        assertTrue("With the file:\n" + textToWriteToFile + "\nWhen searcing for cats the output should have the line \" Wins: 1\".\nthe output was:\n" + io.getSysOut(), lines(io.getSysOut()).stream().filter(l -> l.contains("Wins: 1")).count() == 1);
        assertTrue("With the file:\n" + textToWriteToFile + "\nWhen searcing for superman the output should have the line \" Losses: 1\".\nthe output was:\n" + io.getSysOut(), lines(io.getSysOut()).stream().filter(l -> l.contains("Losses: 1")).count() == 1);
    }

    @Test
    @Points("04-31.2")
    public void winsAndLosses3() {
        String fileName = "file1.txt";
        String textToWriteToTheFile = "bestTeam,superteam,32,34\nbatman,superman,30,31\nbatman,robin,1,0\ncats,dogs,3,0";
        deleteAndCreateNew(fileName, textToWriteToTheFile.split("\n"));
        io.setSysIn(fileName + "\nspiderman\n");
        
        try {
            SportStatistics.main(new String[]{});
        } catch (Exception e) {
            fail("Error when running the program: " + e.getMessage());
        } finally {
            deleteFile(fileName);
        }
       
        assertTrue("With the file:\n" + textToWriteToTheFile + "\nWhen searcing for superman the output should have the line \" Wins: 0\".\nthe output was:\n" + io.getSysOut(), lines(io.getSysOut()).stream().filter(l -> l.contains("Wins: 0")).count() == 1);
        assertTrue("With the file:\n" + textToWriteToTheFile + "\nWhen searcing for superman the output should have the line \" Losses: 0\".\nthe output was:\n" + io.getSysOut(), lines(io.getSysOut()).stream().filter(l -> l.contains("Losses: 0")).count() == 1);
    }

    
    private List<String> lines(String out) {
        return Arrays.asList(out.split("\n")).stream().map(l -> l.trim()).filter(l -> !l.isEmpty()).collect(Collectors.toList());
    }

    private void deleteAndCreateNew(String fileName, String... textToWrite) {
        deleteFile(fileName);

        try {
            writeToFile(fileName, textToWrite);
        } catch (Exception e) {
            fail("Could not create file" + fileName + " .\n If your program works correctly, submit it to the server.");
        }

    }

    private void deleteFile(String fileName) {
        if (new File(fileName).exists()) {
            try {
                new File(fileName).delete();
            } catch (Exception e) {
                fail("Could not delete file " + fileName + ".\n If your program works correctly, submit it to the server.");
            }
        }
    }

    private void writeToFile(String fileName, String[] textToWrite) throws FileNotFoundException {
        try (PrintWriter pw = new PrintWriter(new File(fileName))) {
            for (String line : textToWrite) {
                pw.println(line);
            }
            pw.flush();
        }
    }
}
