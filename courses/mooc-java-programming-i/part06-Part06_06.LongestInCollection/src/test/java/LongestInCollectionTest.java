
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import org.junit.*;
import static org.junit.Assert.*;

public class LongestInCollectionTest {

    @Test
    @Points("06-06")
    public void methodLongestExists() throws Throwable {
        Reflex.reflect(SimpleCollection.class).method("longest").returning(String.class).takingNoParams().requirePublic();
    }

    @Test
    @Points("06-06")
    public void returnsNullWhenCollectionIsEmpty() throws Throwable {
        SimpleCollection c = new SimpleCollection("test");
        String res = Reflex.reflect(SimpleCollection.class).method("longest").returning(String.class).takingNoParams().invokeOn(c);
        assertNull("The longest method should return a null reference in case the collection is empty. Now it returned: " + res, res);
    }

    @Test
    @Points("06-06")
    public void longestTest1() throws Throwable {
        SimpleCollection c = new SimpleCollection("test");
        c.add("one");
        c.add("two");
        c.add("three");
        String res = Reflex.reflect(SimpleCollection.class).method("longest").returning(String.class).takingNoParams().invokeOn(c);
        assertEquals("When the strings \"one\", \"two\", ja \"three\" have been added to the collection, the longest method should return the string \"three\". Now the method returned: " + res, "three", res);
    }

    @Test
    @Points("06-06")
    public void longestTest2() throws Throwable {
        SimpleCollection c = new SimpleCollection("test");
        c.add("long");
        c.add("longer");
        c.add("short");
        String res = Reflex.reflect(SimpleCollection.class).method("longest").returning(String.class).takingNoParams().invokeOn(c);
        assertEquals("When the strings \"long\", \"longer\", ja \"short\" have been added to the collection, the longest method should return the string \"longer\". Now the method returned: " + res, "longer", res);
    }

}
