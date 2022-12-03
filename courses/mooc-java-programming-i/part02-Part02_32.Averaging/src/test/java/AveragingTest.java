
import fi.helsinki.cs.tmc.edutestutils.Points;
import org.junit.*;
import static org.junit.Assert.*;

@Points("02-32")
public class AveragingTest {

    @Test
    public void started() {
        double result = Averaging.average(1, 1, 1, 1);
        assertFalse("You're returning -1, the result of the model. The average of 1, 1, 1, and 1  is 1",
                -1 == result);
    }

    @Test
    public void correctAverage() {
        assertEquals("The average of -12, 2, 8 and 0 is not correct, Are you doing the division with integers?",
                -0.5, Averaging.average(-12, 2, 8, 0), 0.0001);
    }

    @Test
    public void correctAverage2() {
        assertEquals("The average of 1, 2, 3 and 4 is not correct, Are you doing the division with integers?",
                2.5, Averaging.average(1, 2, 3, 4), 0.0001);
    }
}
