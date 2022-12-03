

import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import static org.junit.Assert.assertTrue;
import org.junit.Rule;
import org.junit.Test;

@Points("03-18")
public class SwapTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void example1() throws Throwable {
        io.setSysIn("2\n4\n");
        Swap.main(new String[]{});
        List<Integer> numbers = readNumbers();
        assertTrue("You modified the printing of the numbers, didn't you? The output was expected to contain 10 numbers, now there were " + numbers.size() + " numbers.", numbers.size() == 10);
        numbers = numbers.subList(5, numbers.size());
        int[] expected = {1, 3, 9, 7, 5};

        for (int i = 0; i < expected.length; i++) {
            assertTrue("When the values in indices 2 and 4 were swapped, index " + i + " should have contained " + expected[i] + ". Now it contained " + numbers.get(i), expected[i] == numbers.get(i));
        }
    }

    @Test
    public void example2() throws Throwable {
        io.setSysIn("0\n1\n");
        Swap.main(new String[]{});
        List<Integer> numbers = readNumbers();
        assertTrue("You modified the printing of the numbers, didn't you? the output was expected to contain 10 numbers, now there were " + numbers.size(), numbers.size() == 10);
        numbers = numbers.subList(5, numbers.size());
        int[] expected = {3, 1, 5, 7, 9};

        for (int i = 0; i < expected.length; i++) {
            assertTrue("When the values in indices 0 and 1 were swapped, index " + i + " should have contained " + expected[i] + ". Now it contained " + numbers.get(i), expected[i] == numbers.get(i));
        }
    }

    @Test
    public void example3() throws Throwable {
        io.setSysIn("1\n3\n");
        Swap.main(new String[]{});
        List<Integer> numbers = readNumbers();
        assertTrue("You modified the printing of the numbers, didn't you? the output was expected to contain 10 numbers, now there were " + numbers.size(), numbers.size() == 10);
        numbers = numbers.subList(5, numbers.size());
        int[] expected = {1, 7, 5, 3, 9};

        for (int i = 0; i < expected.length; i++) {
            assertTrue("When the values in indices 1 and 3 were swapped, index " + i + " should have contained " + expected[i] + ". Now it contained " + numbers.get(i), expected[i] == numbers.get(i));
        }
    }

    private List<Integer> readNumbers() {
        return Arrays.stream(io.getSysOut().split("\n"))
                .filter(l -> !l.trim().isEmpty())
                .filter(l -> {
                    try {
                        Integer.valueOf(l.trim());
                        return true;
                    } catch (NumberFormatException t) {
                    }
                    return false;
                }).map(i -> Integer.valueOf(i)).collect(Collectors.toList());
    }

}
