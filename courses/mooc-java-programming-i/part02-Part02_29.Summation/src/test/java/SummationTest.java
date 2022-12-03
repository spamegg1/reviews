
import fi.helsinki.cs.tmc.edutestutils.Points;
import org.junit.*;
import static org.junit.Assert.*;

@Points("02-29")
public class SummationTest {

    @Test
    public void addingPositiveNumbers() {
        assertEquals("Doesn't work when the input is 1,2,2,1", 6, Summation.sum(1, 2, 2, 1));
    }

    @Test
    public void addingNegativeNumbers() {
        assertEquals("Doesn't work when the input is 1,2,-1,1", 3, Summation.sum(1, 2, -1, 1));
    }

    @Test
    public void testMethod() {
        test("Doesn't work when the input is 1,2,3,4", 10, new int[]{1, 2, 3, 4});
    }
    
    @Test
    public void addingGrandNumbers(){
        test("Doesn't work when the input is 0,0,0,2147483647",Integer.MAX_VALUE,new int[]{0,0,0,Integer.MAX_VALUE});
    }
    

    private void test(String message, int expected, int[] numbers) {
        assertEquals(message, expected, Summation.sum(numbers[0], numbers[1], numbers[2], numbers[3]));
    }
}
