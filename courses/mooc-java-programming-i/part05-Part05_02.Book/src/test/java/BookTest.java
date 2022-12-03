
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.MockInOut;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Field;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

@Points("05-02")
public class BookTest {

    Reflex.ClassRef<Object> klass;
    String klassName = "Book";

    @Before
    public void fetchClass() {
        klass = Reflex.reflect(klassName);
    }

    @Test
    public void classIsPublic() {
        assertTrue("The class " + klassName + " must be public, so it has to be defined as\npublic class " + klassName + " {...\n}", klass.isPublic());
    }

    @Test
    public void testConstructor() throws Throwable {
        Reflex.MethodRef3<Object, Object, String, String, Integer> cc = klass.constructor().taking(String.class, String.class, int.class).withNiceError();
        assertTrue("Define for the class " + klassName + " a public constructor: public " + klassName + "(String author, String name, int pages)", cc.isPublic());
        cc.invoke("Marie Kondo", "KonMari", 222);
    }

    @Test
    public void noExtraVariables() {
        sanityCheck();
    }

    @Test
    public void methodsExist() throws Throwable {
        Reflex.MethodRef3<Object, Object, String, String, Integer> bookClass = klass.constructor().taking(String.class, String.class, int.class).withNiceError();
        Object book = bookClass.invoke("Marie Kondo", "KonMari", 222);

        try {
            klass.method(book, "getAuthor")
                    .returning(String.class)
                    .takingNoParams().invoke();
        } catch (AssertionError ae) {
            fail("Error: " + ae + "\n so give the class " + klassName + " the method public String getAuthor()");
        }

        assertTrue("The getAuthor method must be public, i.e. defined as public void getAuthor()", klass.method(book, "getAuthor")
                .returning(String.class)
                .takingNoParams().isPublic());

        try {
            klass.method(book, "getName")
                    .returning(String.class)
                    .takingNoParams().invoke();
        } catch (AssertionError ae) {
            fail("Error: " + ae + "\n so give the class " + klassName + " the method public String getName()");
        }

        assertTrue("The getName method must be public, i.e. defined as public void getPages()", klass.method(book, "getName")
                .returning(String.class)
                .takingNoParams().isPublic());

        try {
            klass.method(book, "getPages")
                    .returning(int.class)
                    .takingNoParams().invoke();
        } catch (AssertionError ae) {
            fail("Error: " + ae + "\n so give the class " + klassName + " the method public String getPages()");
        }

        assertTrue("The getPages method must be public, i.e. defined as public void getPages()", klass.method(book, "getPages")
                .returning(int.class)
                .takingNoParams().isPublic());
    }

    @Test
    public void testReturningAuthor() throws Throwable {
        MockInOut mio = new MockInOut("");
        Reflex.MethodRef3<Object, Object, String, String, Integer> bookClass = klass.constructor().taking(String.class, String.class, int.class).withNiceError();
        Object book = bookClass.invoke("Marie Kondo", "KonMari", 222);

        String author = klass.method(book, "getAuthor")
                .returning(String.class)
                .takingNoParams().invoke();

        assertEquals("When executing the commands\n Book b = new Book(\"Marie Kondo\", \"KonMari\", 222);\nString s = b.getAuthor();\nThe variable s should store the value \"Marie Kondo\".", "Marie Kondo", author);

        String out = mio.getOutput();

        mio.close();

        assertTrue("The constructor and the getAuthor methods should print nothing.", out.trim().isEmpty());
    }

    @Test
    public void testReturningAuthor2() throws Throwable {
        MockInOut mio = new MockInOut("");
        Reflex.MethodRef3<Object, Object, String, String, Integer> bookClass = klass.constructor().taking(String.class, String.class, int.class).withNiceError();
        Object kirja = bookClass.invoke("Karie Mondo", "MonKari", 222);

        String author = klass.method(kirja, "getAuthor")
                .returning(String.class)
                .takingNoParams().invoke();

        assertEquals("When executing the commands\n Book b = new Book(\"Karie Mondo\", \"MonKari\", 222);\nString s = b.getAuthor();\nThe variable s should store the value \"Karie Mondo\".", "Karie Mondo", author);

        String out = mio.getOutput();

        mio.close();

        assertTrue("The constructor and the getAuthor methods should print nothing.", out.trim().isEmpty());
    }

    @Test
    public void testReturningName() throws Throwable {
        MockInOut mio = new MockInOut("");
        Reflex.MethodRef3<Object, Object, String, String, Integer> bookClass = klass.constructor().taking(String.class, String.class, int.class).withNiceError();
        Object kirja = bookClass.invoke("Marie Kondo", "KonMari", 222);

        String name = klass.method(kirja, "getName")
                .returning(String.class)
                .takingNoParams().invoke();

        assertEquals("When executing the commands\n Book b = new Book(\"Marie Kondo\", \"KonMari\", 222);\nString s = b.getName();\nThe variable s should store the value \"KonMari\".", "KonMari", name);


        String out = mio.getOutput();

        mio.close();

        assertTrue("The constructor and the getName methods should print nothing.", out.trim().isEmpty());
    }

    @Test
    public void testReturningName2() throws Throwable {
        MockInOut mio = new MockInOut("");
        Reflex.MethodRef3<Object, Object, String, String, Integer> bookClass = klass.constructor().taking(String.class, String.class, int.class).withNiceError();
        Object book = bookClass.invoke("Karie Mondo", "MonKari", 222);

        String name = klass.method(book, "getName")
                .returning(String.class)
                .takingNoParams().invoke();

        assertEquals("When executing the commands\n Book b = new Book(\"Karie Mondo\", \"MonKari\", 222);\nString s = b.getName();\nThe variable s should store the value \"MonKari\".", "MonKari", name);


        String out = mio.getOutput();

        mio.close();

        assertTrue("The constructor and the getName methods should print nothing.", out.trim().isEmpty());
    }

    @Test
    public void testReturningPages() throws Throwable {
         MockInOut mio = new MockInOut("");
        Reflex.MethodRef3<Object, Object, String, String, Integer> bookClass = klass.constructor().taking(String.class, String.class, int.class).withNiceError();
        Object kirja = bookClass.invoke("Marie Kondo", "KonMari", 222);

        int pages = klass.method(kirja, "getPages")
                .returning(int.class)
                .takingNoParams().invoke();

        assertEquals("When executing the commands\n Book b = new Book(\"Marie Kondo\", \"KonMari\", 222);\nint s = b.getPages();\nThe variable s should store the value 222.", 222, pages);


        String out = mio.getOutput();

        mio.close();

        assertTrue("The constructor and the getPages methods should print nothing.", out.trim().isEmpty());
    }

    @Test
    public void testReturningPages2() throws Throwable {
         MockInOut mio = new MockInOut("");
        Reflex.MethodRef3<Object, Object, String, String, Integer> bookClass = klass.constructor().taking(String.class, String.class, int.class).withNiceError();
        Object kirja = bookClass.invoke("Marie Kondo", "KonMari", 2222);

        int pages = klass.method(kirja, "getPages")
                .returning(int.class)
                .takingNoParams().invoke();

        assertEquals("When executing the commands\n Book b = new Book(\"Marie Kondo\", \"KonMari\", 2222);\nint s = b.getPages();\nThe variable s should store the value 2222.", 2222, pages);


        String out = mio.getOutput();

        mio.close();

        assertTrue("The constructor and the getPages methods should print nothing.", out.trim().isEmpty());
    }

    @Test
    public void testToString() throws Throwable {
        MockInOut mio = new MockInOut("");
        Reflex.MethodRef3<Object, Object, String, String, Integer> bookClass = klass.constructor().taking(String.class, String.class, int.class).withNiceError();
        Object book = bookClass.invoke("Marie Kondo", "KonMari", 222);

        String toString = book.toString();

        assertEquals("When executing the commands\n Book b = new Book(\"Marie Kondo\", \"KonMari\", 222);\nString s = b.toString();\nThe variable s should store the value \"Marie Kondo, KonMari, 222 pages\".", "Marie Kondo, KonMari, 222 pages", toString);

        
        String out = mio.getOutput();

        mio.close();

        assertTrue("The constructor and the toString methods should print nothing.", out.trim().isEmpty());

    }

    @Test
    public void testToString2() throws Throwable {
        MockInOut mio = new MockInOut("");
        Reflex.MethodRef3<Object, Object, String, String, Integer> bookClass = klass.constructor().taking(String.class, String.class, int.class).withNiceError();
        Object book = bookClass.invoke("Karie Mondo", "MonKari", 2222);

        String toString = book.toString();

        assertEquals("When executing the commands\n Book b = new Book(\"Karie Mondo\", \"MonKari\", 2222);\nString s = b.toString();\nThe variable s should store the value \"Karie Mondo, MonKari, 2222 pages\".", "Karie Mondo, MonKari, 2222 pages", toString);

        String out = mio.getOutput();

        mio.close();

        assertTrue("The constructor and the toString methods should print nothing.", out.trim().isEmpty());
    }

    private void sanityCheck() throws SecurityException {
        Field[] fields = ReflectionUtils.findClass(klassName).getDeclaredFields();

        for (Field field : fields) {
            assertFalse("you don't need \"static variables\", delete " + field(field.toString()), field.toString().contains("static") && !field.toString().contains("final"));
            assertTrue("the visibility of all the object variables must be private, change " + field(field.toString()), field.toString().contains("private"));
        }

        if (fields.length > 1) {
            int var = 0;
            for (Field field : fields) {
                if (!field.toString().contains("final")) {
                    var++;
                }
            }
            assertTrue("The class " + klassName + " needs only three object variables, delete the extras", var < 4);
        }
    }

    private String field(String toString) {
        return toString.replace(klassName + ".", "");
    }
}
