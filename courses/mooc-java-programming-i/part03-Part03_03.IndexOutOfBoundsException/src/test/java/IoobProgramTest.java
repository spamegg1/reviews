
import fi.helsinki.cs.tmc.edutestutils.Points;
import static org.junit.Assert.*;
import org.junit.Test;

@Points("03-03")
public class IoobProgramTest {

    @Test
    public void causingIndexOutOfBoundsException() {
        try {
            IoobProgram.main(new String[]{});
            fail("Execution of the program should cause an IndexOutOfBoundsException. Now it didn't happen.");
        } catch (IndexOutOfBoundsException e) {
        }
    }
}
