
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.io.File;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import org.junit.*;
import static org.junit.Assert.*;

public class SortingTest {

    String klassName = "MainProgram";
    Reflex.ClassRef<Object> klass;

    @Before
    public void justForKicks() {
        klass = Reflex.reflect(klassName);
    }

    @Test
    @Points("07-03.1")
    public void classExists() {
        klass = Reflex.reflect(klassName);
        assertTrue("The class " + klassName + " msut be public, so it has to be defines as\npublic class " + klassName + " {...\n}", klass.isPublic());
    }

    @Test
    @Points("07-03.1")
    public void methodSmallestExists() throws Throwable {
        String method = "smallest";
        Reflex.ClassRef<Object> klass = Reflex.reflect(klassName);

        assertTrue("create in the class MainProgram the method public static int smallest(int[] array)",
                klass.staticMethod(method).returning(int.class).taking(int[].class).isPublic());

        String e = "The problem was cause by \n"
                + "int[] t = {2, 1, 3, 0};\n"
                + "int s = smallest(t);\n";
        int[] t = {2, 1, 3, 0};
        klass.staticMethod(method).returning(int.class).taking(int[].class).withNiceError(e).invoke(t);
    }

    @Points("07-03.1")
    @Test
    public void noForbiddenCommands0() {
        noForbiddens();
    }

    @Points("07-03.1")
    @Test
    public void smallestDoesNotChangeContentsOfArray() {
        int[] t = {2, 1, 3, 0};
        smallest(t);

        assertTrue("the method smallest must not modify the array it receives as parameter", t[0] == 2);
        assertTrue("the method smallest must not modify the array it receives as parameter", t[1] == 1);
        assertTrue("the method smallest must not modify the array it receives as parameter", t[2] == 3);
        assertTrue("the method smallest must not modify the array it receives as parameter", t[3] == 0);
    }

    @Points("07-03.1")
    @Test
    public void smallestTest1() {
        int[] t = {2, 1, 3};
        int expected = 1;

        assertEquals("the method smallest did not work with the input " + toS(t) + "", expected, smallest(t));
    }

    @Points("07-03.1")
    @Test
    public void smallestTest2() {
        int[] t = {6, 3, 0, -1, 4};
        int expected = -1;

        assertEquals("the method smallest did not work with the input " + toS(t) + "", expected, smallest(t));
    }
    
    Random rand = new Random();

    @Points("07-03.1")
    @Test
    public void smallestTest3() {
        int[] t = {3, 5, 6, 2, 7, 1, 3, 7, 5};
        int expected = rand.nextInt(t.length);
        t[expected] = -10 - rand.nextInt(10);

        assertEquals("the method smallest did not work with the input " + toS(t) + "", t[expected], smallest(t));
    }

    /*
     *
     */
    @Points("07-03.2")
    @Test
    public void methodIndexOfSmallestExists() throws Throwable {
        String error = "create in the class MainProgram the method public static int indexOfSmallest(int[] array)";
        String method = "indexOfSmallest";

        Reflex.ClassRef<Object> klass = Reflex.reflect(klassName);

        assertTrue(error,
                klass.staticMethod(method).returning(int.class).taking(int[].class).isPublic());

        String e = "The problem was cause by \n"
                + "int[] t = {2, 1, 3, 0};\n"
                + "int p = " + method + "(t);\n";
        int[] t = {2, 1, 3, 0};
        klass.staticMethod(method).returning(int.class).taking(int[].class).withNiceError(e).invoke(t);
    }

    @Points("07-03.2")
    @Test
    public void noForbiddenCommands1() {
        noForbiddens();
    }

    @Points("07-03.2")
    @Test
    public void indexOfSmallestDoesNotChangeContentsOfArray() {
        int[] t = {2, 1, 3, 0};
        indexOfSmallest(t);

        assertTrue("the method indexOfSmallest must not modify the array it receives as parameter", t[0] == 2);
        assertTrue("the method indexOfSmallest must not modify the array it receives as parameter", t[1] == 1);
        assertTrue("the method indexOfSmallest must not modify the array it receives as parameter", t[2] == 3);
        assertTrue("the method indexOfSmallest must not modify the array it receives as parameter", t[3] == 0);
    }

    @Points("07-03.2")
    @Test
    public void indexOfSmallestTest1() {
        int[] t = {2, 1, 3};
        int expected = 1;

        assertEquals("the method indexOfSmallest did not work with the input " + toS(t) + "", expected, indexOfSmallest(t));
    }

    @Points("07-03.2")
    @Test
    public void indexOfSmallestTest2() {
        int[] t = {6, 3, 0, -1, 4};
        int expected = 3;

        assertEquals("the method indexOfSmallest did not work with the input " + toS(t) + "", expected, indexOfSmallest(t));
    }

    @Points("07-03.2")
    @Test
    public void indexOfSmallestTest3() {
        int[] t = {3, -5, 6, 1, 7, 1, 3, 7, 5};
        int expected = rand.nextInt(t.length);
        t[expected] = -10 - rand.nextInt(10);

        assertEquals("the method indexOfSmallest did not work with the input " + toS(t) + "", expected, indexOfSmallest(t));
    }

    /*
     *
     */
    @Points("07-03.3")
    @Test
    public void noForbiddenCommands2() {
        noForbiddens();
    }

    @Points("07-03.3")
    @Test
    public void methodIndexOfSmallestFromExists() throws Throwable {
        String error = "create in the class MainProgram the method public static int indexOfSmallestFrom(int[] array, int startIndex)";
        String method = "indexOfSmallestFrom";

        Reflex.ClassRef<Object> klass = Reflex.reflect(klassName);

        assertTrue(error,
                klass.staticMethod(method).returning(int.class).taking(int[].class, int.class).isPublic());

        String e = "The problem was caused by \n"
                + "int[] t = {2, 1, 3, 0};\n"
                + "int p = " + method + "(t, 1);\n";
        int[] t = {2, 1, 3, 0};
        klass.staticMethod(method).returning(int.class).taking(int[].class, int.class).withNiceError(e).invoke(t, 1);
    }

    @Points("07-03.3")
    @Test
    public void indexOfSmallestFromDoesNotChangeContentsOfArray() {
        int[] t = {2, 1, 3, 0};
        indexOfSmallestFrom(t, 0);

        assertTrue("the method indexOfSmallestFrom must not modify the array it receives as parameter", t[0] == 2);
        assertTrue("the method indexOfSmallestFrom must not modify the array it receives as parameter", t[1] == 1);
        assertTrue("the method indexOfSmallestFrom must not modify the array it receives as parameter", t[2] == 3);
        assertTrue("the method indexOfSmallestFrom must not modify the array it receives as parameter", t[3] == 0);

        indexOfSmallestFrom(t, 2);

        assertTrue("the method indexOfSmallestFrom must not modify the array it receives as parameter", t[0] == 2);
        assertTrue("the method indexOfSmallestFrom must not modify the array it receives as parameter", t[1] == 1);
        assertTrue("the method indexOfSmallestFrom must not modify the array it receives as parameter", t[2] == 3);
        assertTrue("the method indexOfSmallestFrom must not modify the array it receives as parameter", t[3] == 0);
    }

    @Points("07-03.3")
    @Test
    public void indexOfSmallestFromTest1() {
        int[] t = {-1, 3, 1, 2};
        int s = 0;
        int expected = 0;

        assertEquals("the method indexOfSmallestFrom did not work with the input " + toS(t) + " start index " + s, expected, indexOfSmallestFrom(t, s));

        s = 1;
        expected = 2;
        assertEquals("the method indexOfSmallestFrom did not work with the input " + toS(t) + " start index " + s, expected, indexOfSmallestFrom(t, s));

        s = 2;
        expected = 2;
        assertEquals("the method indexOfSmallestFrom did not work with the input " + toS(t) + " start index " + s, expected, indexOfSmallestFrom(t, s));

        s = 3;
        expected = 3;
        assertEquals("the method indexOfSmallestFrom did not work with the input " + toS(t) + " start index " + s, expected, indexOfSmallestFrom(t, s));
    }

    @Points("07-03.3")
    @Test
    public void indexOfSmallestFromTest2() {
        //          0  1  2  3  4  5  6  7  8
        int[] t = {-1, 3, 1, 7, 4, 5, 2, 4, 3};
        int s = 0;
        int expected = 0;

        assertEquals("the method indexOfSmallestFrom did not work with the input " + toS(t) + " start index " + s, expected, indexOfSmallestFrom(t, s));

        s = 1;
        expected = 2;
        assertEquals("the method indexOfSmallestFrom did not work with the input " + toS(t) + " start index " + s, expected, indexOfSmallestFrom(t, s));

        s = 2;
        expected = 2;
        assertEquals("the method indexOfSmallestFrom did not work with the input " + toS(t) + " start index " + s, expected, indexOfSmallestFrom(t, s));

        s = 3;
        expected = 6;
        assertEquals("the method indexOfSmallestFrom did not work with the input " + toS(t) + " start index " + s, expected, indexOfSmallestFrom(t, s));

        s = 4;
        expected = 6;
        assertEquals("the method indexOfSmallestFrom did not work with the input " + toS(t) + " start index " + s, expected, indexOfSmallestFrom(t, s));

        s = 5;
        expected = 6;
        assertEquals("the method indexOfSmallestFrom did not work with the input " + toS(t) + " start index " + s, expected, indexOfSmallestFrom(t, s));

        s = 6;
        expected = 6;
        assertEquals("the method indexOfSmallestFrom did not work with the input " + toS(t) + " start index " + s, expected, indexOfSmallestFrom(t, s));

        s = 7;
        expected = 8;
        assertEquals("the method indexOfSmallestFrom did not work with the input " + toS(t) + " start index " + s, expected, indexOfSmallestFrom(t, s));

    }

    /*
     *
     */
    @Points("07-03.4")
    @Test
    public void methodSwapExists() throws Throwable {
        String error = "crete in the class MainProgram the method tee public static void swap(int[] array, int index1, int index2)";
        String method = "swap";
        Reflex.ClassRef<Object> klass = Reflex.reflect(klassName);

        assertTrue(error,
                klass.staticMethod(method).returningVoid().taking(int[].class, int.class, int.class).isPublic());

        String v = "The problem was caused byh \n"
                + "int[] t = {2, 1, 3, 0};\n"
                + method + "(t, 1, 3);\n";
        int[] t = {2, 1, 3, 0};
        klass.staticMethod(method).returningVoid().taking(int[].class, int.class, int.class).withNiceError(v).invoke(t, 1, 3);
    }

    @Points("07-03.4")
    @Test
    public void noForbiddenCommands3() {
        noForbiddens();
    }

    @Points("07-03.4")
    @Test
    public void swapTest1() {
        //             0  1  2  3
        int[] t = {4, 7, 8, 6};
        int[] original = {4, 7, 8, 6};
        int[] expected = {6, 7, 8, 4};
        int i1 = 0;
        int i2 = 3;

        swap(t, i1, i2);
        assertTrue("the method swap does not work correctly with the parameter array " + toS(original) + " index1=" + i1 + " index2=" + i2 + " "
                + "\nthe end result was " + toS(t) + " the correct result is " + toS(expected), Arrays.equals(expected, t));
    }

    @Points("07-03.4")
    @Test
    public void swapTest2() {
        //             0  1  2  3
        int[] t = {4, 7, 8, 6};
        int[] original = {4, 7, 8, 6};
        int[] expected = {4, 8, 7, 6};
        int i1 = 1;
        int i2 = 2;

        swap(t, i1, i2);
        assertTrue("the method swap does not work correctly with the parameter array " + toS(original) + " index1=" + i1 + " index2=" + i2 + " "
                + "\nthe end result was " + toS(t) + " the correct result is " + toS(expected), Arrays.equals(expected, t));
    }

    @Points("07-03.4")
    @Test
    public void swapTest3() {
        //             0  1  2  3  4  5  6
        int[] t = {4, 7, 8, 6, 9, 2, 3};
        int[] original = {4, 7, 8, 6, 9, 2, 3};
        int[] expected = {4, 2, 8, 6, 9, 7, 3};
        int i1 = 1;
        int i2 = 5;

        swap(t, i1, i2);
        assertTrue("the method swap does not work correctly with the parameter array " + toS(original) + " index1=" + i1 + " index2=" + i2 + " "
                + "\nthe end result was " + toS(t) + " the correct result is " + toS(expected), Arrays.equals(expected, t));
    }

    @Points("07-03.4")
    @Test
    public void swapTest4() {
        //             0  1  2  3  4  5  6
        int[] t = {4, 7, 8, 6, 9, 2, 3};
        int[] original = {4, 7, 8, 6, 9, 2, 3};
        int[] expected = {4, 7, 9, 6, 8, 2, 3};
        int i1 = 2;
        int i2 = 4;

        swap(t, i1, i2);
        assertTrue("the method swap does not work correctly with the parameter array " + toS(original) + " index1=" + i1 + " index2=" + i2 + " "
                + "\nthe end result was " + toS(t) + " the correct result is " + toS(expected), Arrays.equals(expected, t));
    }

    @Points("07-03.4")
    @Test
    public void swapTest5() {
        //             0  1  2  3  4  5  6
        int[] t = {4, 7, 8, 6, 9, 2, 3};
        int[] original = {4, 7, 8, 6, 9, 2, 3};
        int[] expected = {3, 7, 8, 6, 9, 2, 4};
        int i1 = 0;
        int i2 = 6;

        swap(t, i1, i2);
        assertTrue("the method swap does not work correctly with the parameter array " + toS(original) + " index1=" + i1 + " index2=" + i2 + " "
                + "\nthe end result was " + toS(t) + " the correct result is " + toS(expected), Arrays.equals(expected, t));
    }

    /*
     *
     */
    @Points("07-03.5")
    @Test
    public void methodSortExists() throws Throwable {
        String error = "create in the class MainProgram the method public static void sort(int[] array)";
        String method = "sort";
        Reflex.ClassRef<Object> klass = Reflex.reflect(klassName);

        assertTrue(error,
                klass.staticMethod(method).returningVoid().taking(int[].class).isPublic());

        String v = "The problem was caused by \n"
                + "int[] t = {2, 1, 3, 0};\n"
                + method + "(t);\n";
        int[] t = {2, 1, 3, 0};
        klass.staticMethod(method).returningVoid().taking(int[].class).withNiceError(v).invoke(t);
    }

    @Points("07-03.5")
    @Test
    public void noForbiddenCommands4() {
        noForbiddens();
    }

    @Points("07-03.5")
    @Test
    public void sortTest1() {
        int[] t = {4, 7, 1};
        int[] original = {4, 7, 1};
        int[] expected = {4, 7, 1};

        Arrays.sort(expected);

        sort(t);
        assertTrue("the method sort does not work correctly with the input " + toS(original) + " "
                + "the end result was " + toS(t) + " the correct result is " + toS(expected), Arrays.equals(expected, t));

    }

    @Points("07-03.5")
    @Test
    public void sortTest2() {
        int[] t = {4, 7, 8, 6, 9, 2, 3};
        int[] original = {4, 7, 8, 6, 9, 2, 3};
        int[] expected = {4, 7, 8, 6, 9, 2, 3};

        Arrays.sort(expected);

        sort(t);
       assertTrue("the method sort does not work correctly with the input " + toS(original) + " "
                + "the end result was " + toS(t) + " the correct result is " + toS(expected), Arrays.equals(expected, t));
    }

    @Points("07-03.5")
    @Test
    public void sortTest3() {
        int n = rand.nextInt(5) + 5;

        int[] t = new int[n];
        int[] original = new int[n];
        int[] expected = new int[n];

        for (int i = 0; i < n; i++) {
            int arvottu = 20 - rand.nextInt(30);
            t[i] = arvottu;
            expected[i] = arvottu;
            original[i] = arvottu;
        }

        Arrays.sort(expected);

        sort(t);
       assertTrue("the method sort does not work correctly with the input " + toS(original) + " "
                + "the end result was " + toS(t) + " the correct result is " + toS(expected), Arrays.equals(expected, t));
    }

    @Points("07-03.5")
    @Test
    public void sortTest4() {
        for (int k = 0; k < 10; k++) {
            int n = rand.nextInt(20) + 20;

            int[] t = new int[n];
            int[] original = new int[n];
            int[] expected = new int[n];

            for (int i = 0; i < n; i++) {
                int arvottu = 20 - rand.nextInt(30);
                t[i] = arvottu;
                expected[i] = arvottu;
                original[i] = arvottu;
            }

            Arrays.sort(expected);

            sort(t);
            assertTrue("the method sort does not work correctly with the input " + toS(original) + " "
                + "the end result was " + toS(t) + " the correct result is " + toS(expected), Arrays.equals(expected, t));
        }

    }

    /*
     *
     */
    private void sort(int[] t) {
        String method = "sort";

        try {
            Method m = ReflectionUtils.requireMethod(MainProgram.class, method, int[].class);
            ReflectionUtils.invokeMethod(Void.TYPE, m, null, t);

        } catch (ArrayIndexOutOfBoundsException e) {
            fail("the method " + method + " references outside of the array when the input is" + toS(t));
        } catch (Throwable e) {
            fail("something unexpected occurred in the method " + method + " , more information:\n" + e);
        }
    }

    private void swap(int[] t, int i1, int i2) {
        String method = "swap";

        try {
            Method m = ReflectionUtils.requireMethod(MainProgram.class, method, int[].class, int.class, int.class);

            ReflectionUtils.invokeMethod(int.class, m, null, t, i1, i2);
        } catch (ArrayIndexOutOfBoundsException e) {
            fail("the method " + method + " references outside of the array when the input is" + toS(t));
        } catch (Throwable e) {
            fail("something unexpected occurred in the method " + method + " , more information:\n" + e);
        }
    }

    private int indexOfSmallestFrom(int[] t, int s) {
        String method = "indexOfSmallestFrom";

        try {
            String[] args = new String[0];
            Method m = ReflectionUtils.requireMethod(MainProgram.class, method, int[].class, int.class);

            return ReflectionUtils.invokeMethod(int.class, m, null, t, s);
        } catch (ArrayIndexOutOfBoundsException e) {
            fail("the method " + method + " references outside of the array when the input is" + toS(t));
        } catch (Throwable e) {
            fail("something unexpected occurred in the method " + method + " , more information:\n" + e);
        }
        return -1;
    }

    private int smallest(int[] t) {
        String method = "smallest";

        try {
            String[] args = new String[0];
            Method m = ReflectionUtils.requireMethod(MainProgram.class, method, int[].class);

            return ReflectionUtils.invokeMethod(int.class, m, null, t);
        } catch (ArrayIndexOutOfBoundsException e) {
            fail("the method " + method + " references outside of the array when the input is" + toS(t));
        } catch (Throwable e) {
            fail("something unexpected occurred in the method " + method + " , more information:\n" + e);
        }
        return -1;
    }

    private int indexOfSmallest(int[] t) {
        String method = "indexOfSmallest";

        try {
            String[] args = new String[0];
            Method m = ReflectionUtils.requireMethod(MainProgram.class, method, int[].class);

            return ReflectionUtils.invokeMethod(int.class, m, null, t);
        } catch (ArrayIndexOutOfBoundsException e) {
            fail("the method " + method + " references outside of the array when the input is" + toS(t));
        } catch (Throwable e) {
            fail("something unexpected occurred in the method " + method + " , more information:\n" + e);
        }
        return -1;
    }

    private String toS(int[] t) {
        return Arrays.toString(t).replace("[", "").replace("]", "");
    }

    private void noForbiddens() {
        try {
            Scanner scanner = new Scanner(new File("src/main/java/MainProgram.java"));
            while (scanner.hasNext()) {
                String row = scanner.nextLine();
                if (row.contains("Arrays.sort(")) {
                    fail("Since we are practising how a sorting algorithm is written, "
                            + "you are not allowed to use the command Arrays.sort() in the program");
                }

                if (row.contains("ArrayList<")) {
                    fail("Since we are practising how a sorting algorithm is written, "
                            + "you are not allowed to use ArrayLists in the program");
                }
            }
        } catch (Exception e) {
            fail(e.toString());
        }
    }
}
