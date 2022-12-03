
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.util.ArrayList;
import org.junit.*;
import static org.junit.Assert.*;

@Points("03-17")
public class RemoveLastTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void test() throws Throwable {
        ArrayList<String> strs = strings("a", "b", "c");
        check(strs);
    }

    @Test
    public void test2() throws Throwable {
        ArrayList<String> strs = strings("c", "b", "a");
        check(strs);
    }

    @Test
    public void test3() throws Throwable {
        ArrayList<String> strs = strings();
        check(strs);
    }

    private void check(ArrayList<String> strs) throws Throwable {
        String oldOut = io.getSysOut();
        ArrayList<String> original = new ArrayList<>(strs);
        try {
            Reflex.reflect(RemoveLast.class).staticMethod("removeLast").returningVoid().taking(ArrayList.class).invoke(strs);
        } catch (Throwable t) {
            fail("the removeLast method should not cause an exception. Make sure the method doesn't do anything to an empty list.\nAlso check the method withthis list: " + original.toString());
        }

        if (original.size() == 0) {
            return;
        }

        assertFalse("removeLast method should remove the last element of the list.", strs.contains(original.get(original.size() - 1)));
        original.remove(original.size() - 1);
        assertEquals("The last element of the list should be removed. Don't otherwise modify the method.", strs, original);
    }

    private static ArrayList<String> strings(String... list) {
        ArrayList<String> strs = new ArrayList<>();
        for (String s : list) {
            strs.add(s);
        }
        return strs;
    }
}
