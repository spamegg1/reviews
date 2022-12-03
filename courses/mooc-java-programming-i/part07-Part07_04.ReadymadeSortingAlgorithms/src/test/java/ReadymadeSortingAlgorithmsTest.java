
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.util.ArrayList;
import org.junit.*;
import static org.junit.Assert.*;

@Points("07-04")
public class ReadymadeSortingAlgorithmsTest {

    String klassName = "Main";
    Reflex.ClassRef<Object> klass;

    @Before
    public void justForKicks() {
        klass = Reflex.reflect(klassName);
    }

    @Test
    public void isClass() {
        klass = Reflex.reflect(klassName);
        assertTrue("Class " + klassName + " must be public, so it needs to be defined as \npublic class " + klassName + " {...\n}", klass.isPublic());
    }

    @Test
    public void sortIntegerArray() throws Throwable {
        int[] t = {2, 1, 3, 0};
        String error = "Test with:\nint[] t = {2, 1, 3, 0};\nsort(t);\nSystem.out.println(Arrays.toString(t));\n";
        klass.staticMethod("sort").returningVoid().taking(int[].class).withNiceError(error).invoke(t);
        Assert.assertArrayEquals(error, new int[]{0, 1, 2, 3}, t);
    }

    @Test
    public void sortStringArray() throws Throwable {
        String[] t = {"x", "a", "b", "d"};
        String error = "Test with:\nString[] t = {\"x\", \"a\", \"b\", \"d\"};\nsort(t);\nSystem.out.println(Arrays.toString(t));\n";
        klass.staticMethod("sort").returningVoid().taking(String[].class).withNiceError(error).invoke(t);
        Assert.assertArrayEquals(error, new String[]{"a", "b", "d", "x"}, t);
    }

    @Test
    public void sortIntegerList() throws Throwable {

        ArrayList<Integer> integers = new ArrayList<>();
        integers.add(3);
        integers.add(2);
        integers.add(0);
        integers.add(1);

        String error = "Test with:\nArrayList<Integer> integers = new ArrayList<>();\n"
                + "integers.add(3);\n"
                + "integers.add(2);\n"
                + "integers.add(0);\n"
                + "integers.add(1);\n"
                + "sortIntegers(integers);\n"
                + "System.out.println(integers);\n";
        klass.staticMethod("sortIntegers").returningVoid().taking(ArrayList.class).withNiceError(error).invoke(integers);

        ArrayList<Integer> integers2 = new ArrayList<>();
        integers2.add(0);
        integers2.add(1);
        integers2.add(2);
        integers2.add(3);

        Assert.assertEquals(error, integers2, integers);
    }

    @Test
    public void sortStringList() throws Throwable {

        ArrayList<String> strings = new ArrayList<>();
        strings.add("d");
        strings.add("b");
        strings.add("a");
        strings.add("c");

        String error = "Test with:\n"
                + "ArrayList<String> strings = new ArrayList<>();\n"
                + "strings.add(\"d\");\n"
                + "strings.add(\"b\");\n"
                + "strings.add(\"a\");\n"
                + "strings.add(\"c\");\n"
                + "sortStrings(strings);\n"
                + "System.out.println(strings);\n";
        klass.staticMethod("sortStrings").returningVoid().taking(ArrayList.class).withNiceError(error).invoke(strings);

        ArrayList<String> strings2 = new ArrayList<>();
        strings2.add("a");
        strings2.add("b");
        strings2.add("c");
        strings2.add("d");
        
        Assert.assertEquals(error, strings2, strings);
    }

}
