
import fi.helsinki.cs.tmc.edutestutils.MockInOut;
import fi.helsinki.cs.tmc.edutestutils.Points;
import java.util.Arrays;
import java.util.Random;
import org.junit.*;
import static org.junit.Assert.*;

@Points("03-22")
public class PrinterTest {

    @Test
    public void checkStars() {
        int[] arrayLengths = {0, 1, 2, 3, 4, 5, 10, 20, 100};

        Random random = new Random();
        for (int i = 0; i < arrayLengths.length; i++) {
            int length = arrayLengths[i];
            int[] array = new int[length];
            for (int j = 0; j < array.length; j++) {
                array[j] = 1 + random.nextInt(10) + length;
            }
            checkArray(array);
        }
    }

    private void checkArray(int[] arr) {
        MockInOut mio = new MockInOut("");
        Printer.printArrayInStars(arr);

        String output = mio.getOutput().trim();
        if (arr.length == 0) {
            if (!output.isEmpty()) {
                fail("When the printArrayInStars was given an empty array, the method printed something anyway: " + output);
            }
            return;
        }

        if (output.isEmpty()) {
            fail("You're not printing anything when the parameter is " + Arrays.toString(arr) + " Make sure your code is located in the printArrayInStars(int array) method.");
        }

        String[] stars = output.split("\\n");
        if (stars.length == 0) {
            fail("You're not printing anything when the parameter is" + Arrays.toString(arr) + " Make sure your code is located in the printArrayInStars(int array) method.");
        }

        if (stars.length < arr.length) {
            fail("When the method received " + Arrays.toString(arr) + " as a parameter, the output only had " + stars.length + " lines, whereas the length of the array there should have been: " + arr.length);
        }

        for (int i = 0; i < stars.length; i++) {
            String line = stars[i].trim();
            int count = arr[i];
            if (!line.matches("[\\*]+")) {
                fail("When the method received " + Arrays.toString(arr) + " as a parameter, the output should only contain stars, but there was also: " + line);
            }
            if (line.length() != count) {
                fail("When the method received " + Arrays.toString(arr) + "as a parameter, a line in the output contained " + line.length() + " stars, while it should have contained: " + count);
            }
        }
    }
}
