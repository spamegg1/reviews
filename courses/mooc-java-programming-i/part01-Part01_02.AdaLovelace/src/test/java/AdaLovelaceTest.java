
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import org.junit.*;
import static org.junit.Assert.assertEquals;

@Points("01-02")
public class AdaLovelaceTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void outputIsCorrect() {
        AdaLovelace.main(new String[]{});
        assertEquals("Ada Lovelace", io.getSysOut().trim());
    }

}
