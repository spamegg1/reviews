
import fi.helsinki.cs.tmc.edutestutils.Points;
import org.junit.Test;
import static org.junit.Assert.*;

@Points("02-31")
public class GreatestTest {

    public GreatestTest() {
    }

    @Test
    public void TestWithNegative() {
        int[] input = {-5, -8, -4, -4};
        assertEquals(errorMsg(input), result(input), test(input));
    }

    @Test
    public void testWithPositive() {
        int[] input = {42, 5, 9, 42};
        assertEquals(errorMsg(input), result(input), test(input));
    }

    @Test
    public void testWithPositive2() {
        int[] input = {5, 42, 9, 42};
        assertEquals(errorMsg(input), result(input), test(input));
    }

    @Test
    public void testWithPositive3() {
        int[] input = {5, 9, 42, 42};
        assertEquals(errorMsg(input), result(input), test(input));
    }

    @Test
    public void testWithSame() {
        int[] input = {9, 9, 9, 9};
        assertEquals(errorMsg(input), result(input), test(input));
    }

    @Test
    public void testWithModelSolution() {
        int[] input = {2, 7, 3, 7};
        assertEquals(errorMsg(input), result(input), test(input));
    }

    @Test
    public void testWithZeros() {
        int[] input = {0, 0, 0, 0};
        assertEquals(errorMsg(input), result(input), test(input));
    }

    private int test(int[] input) {
        return Greatest.greatest(input[0], input[1], input[2]);
    }

    private int result(int[] input) {
        return input[input.length - 1];
    }

    private String errorMsg(int[] input) {
        return "The greatest of " + input[0] + ", " + input[1] + ", " + input[2]
                + " ";
    }
}
