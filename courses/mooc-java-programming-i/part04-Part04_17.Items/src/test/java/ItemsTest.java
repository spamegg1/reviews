
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;

@Points("04-17")
public class ItemsTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void luokkaaEsineEiMuokattu() {
        Item datum = new Item("test");
        assertTrue("You have modified the class Item. Revert the changes you've made to it.", datum.toString().startsWith("test (created at:"));
    }

    @Test
    public void testInputFirst() {
        List<Item> items = new ArrayList<>();
        items.add(new Item("mug"));
        items.add(new Item("cup"));

        addAndCheck(items);
        assertFalse("The output had something that doesn't belong there.", io.getSysOut().contains("phone"));
    }

    @Test
    public void testInputSecond() {
        List<Item> items = new ArrayList<>();
        items.add(new Item("phone"));
        items.add(new Item("plate"));
        items.add(new Item("ticket"));

        addAndCheck(items);
        assertFalse("The output had something that doesn't belong there.", io.getSysOut().contains("cup"));
    }

    private void addAndCheck(List<Item> items) {
        String input = "";
        for (Item e : items) {
            input += e.getName() + "\n";
        }
        input += "\n";

        io.setSysIn(input);
        callMain(Items.class);

        for (Item i : items) {
            assertTrue("When the input is\n" + input + "\n, the program's output should include the string \"" + i.getName() + "\". The output was:\n" + io.getSysOut(), io.getSysOut().contains(i.getName()));
            assertTrue("The program should also print the creation time of the item. Input: \n" + input + "\nOutput:\n" + io.getSysOut(), io.getSysOut().contains(i.getName() + " (created at: "));

        }
    }

    private void callMain(Class kl) {
        try {
            kl = ReflectionUtils.newInstanceOfClass(kl);
            String[] t = null;
            String x[] = new String[0];
            Method m = ReflectionUtils.requireMethod(kl, "main", x.getClass());
            ReflectionUtils.invokeMethod(Void.TYPE, m, null, (Object) x);
        } catch (Throwable e) {
            fail("Something weird occurred. It could be that the void main(String[] args) method of the class " + kl + " has disappeared\n"
                    + " or your program crashed due to an exception. More information: " + e);
        }
    }

}
