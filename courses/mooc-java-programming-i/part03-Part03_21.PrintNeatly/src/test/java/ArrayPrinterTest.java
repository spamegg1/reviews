import fi.helsinki.cs.tmc.edutestutils.MockInOut;
import fi.helsinki.cs.tmc.edutestutils.Points;
import java.util.Arrays;
import java.util.Random;
import org.junit.*;
import static org.junit.Assert.*;

@Points("03-21")
public class ArrayPrinterTest {

    @Test
    public void checkOutput() {
        int[] arrayLengths = {1, 2, 3, 4, 5, 10, 20, 100};

        Random random = new Random();
        for (int i = 0; i < arrayLengths.length; i++) {
            int length = arrayLengths[i];
            int[] arr = new int[length];
            for (int j = 0; j < arr.length; j++) {
                arr[j] = random.nextInt(150);
            }

            checkArray(arr);
        }
    }

    private void checkArray(int[] arr) {
        MockInOut mio = new MockInOut("");

        ArrayPrinter.printNeatly(arr);

        String output = mio.getOutput().trim();
        if (arr.length == 0) {
            if (!output.isEmpty()) {
                fail("When the printNeatly method was given an empty array, it printed something anyway: " + output);
            }
            return;
        }

        if (output.isEmpty()) {
            fail("You didn't print anything when the array was " + Arrays.toString(arr));
        }

        String[] numbers = output.split(",", -1);
        if (numbers.length == 0) {
            fail("You didn't print anything when the array was " + Arrays.toString(arr));
        }
        if (numbers.length != arr.length) {
            fail("The output should contain precisely " + (arr.length - 1)
                    + " commas, while there were  " + (numbers.length - 1) + ". Content of the array was: " + Arrays.toString(arr));
        }

        int end = Math.max(0, output.length() - 2);
        assertFalse("Your output doesn't have a line break in the end! When the input was " + Arrays.toString(arr) + " you printed \n" + output,
                output.substring(0, end).contains("\n"));

        for (int i = 0; i < numbers.length; i++) {
            String numberString = numbers[i].trim();
            if (numberString.isEmpty()) {
                fail("The output should have a number after each comma. Make sure the output doesn't end with a comma. You printed: " + output);
            }

            int number;
            try {
                number = Integer.valueOf(numberString);
            } catch (Exception e) {
                fail("The output should only contain numbers separated by commas! This is not a number: " + numberString + ". You printed: " + output);
                return;
            }

            if (number != arr[i]) {
                fail("At the index " + i + " of the array there was " + arr[i] + ", but the output had: " + number);
            }
        }
    }
}
