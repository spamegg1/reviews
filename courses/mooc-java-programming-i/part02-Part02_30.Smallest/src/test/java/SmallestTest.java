
import fi.helsinki.cs.tmc.edutestutils.Points;
import org.junit.*;
import static org.junit.Assert.*;

@Points("02-30")
public class SmallestTest {

    @Test
    public void normalCase() {
        test(2, 7, 2);
    }

    @Test
    public void oneIsNegative() {
        test(-5, 4, -5);
    }

    @Test
    public void veryLargeValues() {
        test(Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE);
    }

    @Test
    public void normalCaseReversed() {
        test(7, 2, 2);
    }

    private void test(int num1, int num2, int expected) {
        assertEquals(getError(num1, num2), expected, Smallest.smallest(num1, num2));
    }

    private String getError(int num1, int num2) {
        return "Wrong result when number1=" + num1 + " and nsumber2=" + num2;
    }
}
