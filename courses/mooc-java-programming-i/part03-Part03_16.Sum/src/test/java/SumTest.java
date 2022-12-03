
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.util.ArrayList;
import org.junit.*;
import static org.junit.Assert.*;

@Points("03-16")
public class SumTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void test() throws Throwable {
        ArrayList<Integer> numbers = numbers(3, 1, 7, 9, 2, 6);
        check(numbers);
    }

    @Test
    public void test2() throws Throwable {
        ArrayList<Integer> numbers = numbers(3, 1, 7, 9, 2, 6);
        check(numbers);
    }

    private void check(ArrayList<Integer> numbers) throws Throwable {
        String oldOut = io.getSysOut();
        int sum = Reflex.reflect(Sum.class).staticMethod("sum").returning(int.class).taking(ArrayList.class).invoke(numbers);

        String out = io.getSysOut().replace(oldOut, "");

        int expected = numbers.stream().mapToInt(i -> i).sum();

        assertEquals("When the sum method was called with this list: " + numbers + ", the sum should be " + expected + ". Your method returned " + sum, expected, sum);
    }

    private static ArrayList<Integer> numbers(int... list) {
        ArrayList<Integer> numbers = new ArrayList<>();
        for (int i : list) {
            numbers.add(i);
        }
        return numbers;
    }
}
