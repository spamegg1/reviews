import java.util.Arrays;
import java.util.NoSuchElementException;

public class MainProgram {

    public static int smallest(int[] array){
        if (array == null) {
            throw new NoSuchElementException("empty array");
        } else {
            int minSoFar = array[0];
            for (int number : array) {
                if (number < minSoFar) {
                    minSoFar = number;
                }
            }
            return minSoFar;
        }
    }

    public static int indexOfSmallest(int[] array) {
        if (array == null) {
            throw new NoSuchElementException("empty array");
        } else {
            int minSoFar = array[0];
            int indexSoFar = 0;
            for (int i = 0; i < array.length; i++) {
                if (array[i] < minSoFar) {
                    minSoFar = array[i];
                    indexSoFar = i;
                }
            }
            return indexSoFar;
        }
    }

    public static int indexOfSmallestFrom(int[] array, int startIndex) {
        if (array.length <= startIndex) {
            throw new ArrayIndexOutOfBoundsException("start index out of bounds");
        } else {
            int minSoFar = array[startIndex];
            int indexSofar = startIndex;
            for (int i = startIndex; i < array.length; i++) {
                int number = array[i];
                if (number < minSoFar) {
                    minSoFar = number;
                    indexSofar = i;
                }
            }
            return indexSofar;
        }
    }

    public static void swap(int[] array, int index1, int index2) {
        int temp;
        temp = array[index1];
        array[index1] = array[index2];
        array[index2] = temp;
    }

    public static void sort(int[] array) {
        for (int i = 0; i < array.length; i++) {
            int index = indexOfSmallestFrom(array, i);
            swap(array, i, index);
        }
    }

    public static void main(String[] args) {
        // write your test code here
        int[] numbers = {6, 5, 8, 7, 11};
        System.out.println("Smallest: " + MainProgram.smallest(numbers));


        // indices:       0  1  2  3  4
        int[] numbers2 = {6, 5, 8, 7, 11};
        System.out.println(
            "Index of the smallest number: " +
            MainProgram.indexOfSmallest(numbers2)
        );


        int[] numbers3 = {3, 2, 5, 4, 8};
        System.out.println(Arrays.toString(numbers3));

        MainProgram.swap(numbers3, 1, 0);
        System.out.println(Arrays.toString(numbers3));

        MainProgram.swap(numbers3, 0, 3);
        System.out.println(Arrays.toString(numbers3));


        int[] numbers4 = {8, 3, 7, 9, 1, 2, 4};
        MainProgram.sort(numbers4);
    }
}
