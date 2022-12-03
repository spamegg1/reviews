import fi.helsinki.cs.tmc.edutestutils.Points;
import java.util.Arrays;
import java.util.Random;
import org.junit.*;
import static org.junit.Assert.*;

@Points("03-20")
public class SumOfArrayTest {

    @Test
    public void checkTheSum() {
        int[] arrayLengths = {0, 1, 2, 3, 4, 5, 10, 20, 100};

        Random random = new Random();
        for (int i = 0; i < arrayLengths.length; i++) {
            int length = arrayLengths[i];
            int[] arr = new int[length];
            for (int j = 0; j < arr.length; j++) {
                arr[j] = random.nextInt(150);
            }
            checkTheArray(arr);
        }
    }

    private void checkTheArray(int[] arr) {
        int sum = 0;
        for (int i = 0; i < arr.length; i++) {
            sum += arr[i];
        }

        int returnedSum = SumOfArray.sumOfNumbersInArray(arr);

        if (sum != returnedSum) {
            fail("The sum returned by the method was " + returnedSum + ", when it was supposed to be: " + sum + ". The content of the array was: " + Arrays.toString(arr));
        }
    }
}
