
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import static org.junit.Assert.assertTrue;
import org.junit.Rule;
import org.junit.Test;

@Points("04-26")
public class GuestListFromAFileTest {
    
    @Rule
    public MockStdio io = new MockStdio();
    
    @Test
    public void testNames() throws Throwable {
        guestListTest("names.txt", 4, 0, "ada", "arto", "leena", "test");
    }
    
    @Test
    public void testNames2() throws Throwable {
        guestListTest("other-names.txt", 3, 2, "leo", "jarmo", "alicia", "ada", "test");
    }
    
    public void guestListTest(String file, int onListCount, int notOnListCount, String... searchedFor) throws Throwable {
        String in = file + "\n";
        for (String name : searchedFor) {
            in += name + "\n";
        }
        
        io.setSysIn(in + "\n");
        
        GuestListFromAFile.main(new String[]{});
        
        String out = io.getSysOut();
       
        assertTrue("When the input is:\n" + in + ", " + onListCount + " people were expected to be on the list and " + notOnListCount + " not to be.\nThe output was:\n" + out, out.split("is on the list").length == onListCount + 1);
        assertTrue("When the input is:\n" + in + ", " + onListCount + " people were expected to be on the list and " + notOnListCount + " not to be.\nThe output was:\n" + out, out.split("is not on the list").length == notOnListCount + 1);
    }
}
