
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.util.ArrayList;
import org.junit.*;
import static org.junit.Assert.*;

@Points("03-15")
public class PrintInRangeTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void test() throws Throwable {
        ArrayList<Integer> numbers = numbers(3, 1, 7, 9, 2, 6);
        check(1, 2, numbers, 1, 2);
    }

    @Test
    public void testi2() throws Throwable {
        ArrayList<Integer> numbers = numbers(3, 1, 7, 9, 2, 6);
        check(6, 999, numbers, 6, 7, 9);
    }

    private void check(int lower, int upper, ArrayList<Integer> numbers, int... expected) throws Throwable {
        String oldOut = io.getSysOut();
        Reflex.reflect(PrintInRange.class).staticMethod("printNumbersInRange").returningVoid().taking(ArrayList.class, int.class, int.class).invoke(numbers, lower, upper);

        String out = io.getSysOut().replace(oldOut, "");

        for (int i : expected) {
            assertTrue("When the 'printNumbersInRange' method was called with these numbers:\n" + numbers + "\n , lower limit " + lower + " and upper limit " + upper + "\nOutput should contain  " + i + "\nOutput was:\n" + out, out.contains("" + i));
        }

        NEXT:
        for (int num : numbers) {
            for (int exp : expected) {
                if (num == exp) {
                    continue NEXT;
                }
            }

            assertFalse("When 'printNumbersInRange' method was called with these numbers:\n" + numbers + "\n , lower limit " + lower + " and upper limit " + upper + "\nThe output was not expected to contain " + num + "\nOutput was:\n" + out, out.contains("" + num));
        }

    }

    private static ArrayList<Integer> numbers(int... list) {
        ArrayList<Integer> numbers = new ArrayList<>();
        for (int i : list) {
            numbers.add(i);
        }
        return numbers;
    }
}
