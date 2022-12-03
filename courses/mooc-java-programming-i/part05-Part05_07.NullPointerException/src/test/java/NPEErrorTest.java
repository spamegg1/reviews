
import fi.helsinki.cs.tmc.edutestutils.Points;
import static org.junit.Assert.*;
import org.junit.Test;

@Points("05-07")
public class NPEErrorTest {

    @Test
    public void causesANullPointerExceptionError() {
        try {
            NullPointerExceptionProgram.main(new String[]{});
        } catch (NullPointerException e) {
            return;
        }
        
        fail("The program is supposed to cause a NullPointerException error, now it did not.");
    }
}
