
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Field;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

@Points("04-10")
public class SongTest {

    Reflex.ClassRef<Object> klass;
    String klassName = "Song";

    @Before
    public void findClass() {
        klass = Reflex.reflect(klassName);
    }

    @Test
    public void classIsPublic() {
        assertTrue("Class " + klassName + " should be public. It should be declared \npublic class " + klassName + " {...\n}", klass.isPublic());
    }

    @Test
    public void testConstructor() throws Throwable {
        Reflex.MethodRef2<Object, Object, String, Integer> cc = klass.constructor().taking(String.class, int.class).withNiceError();
        assertTrue("Declare a public constructor in class " + klassName + ": public " + klassName + "(String name, int length)", cc.isPublic());
        cc.invoke("Testsong", 60);
    }

    @Test
    public void noExtraVariables() {
        sanitaryCheck();
    }

    @Test
    public void testName() throws Throwable {
        Reflex.ClassRef<Object> klass = Reflex.reflect(klassName);
        Object instance = klass.constructor().taking(String.class, int.class).invoke("Grace", 25);

        try {
            klass.method(instance, "name")
                    .returning(String.class)
                    .takingNoParams().invoke();
        } catch (AssertionError ae) {
            fail("Error: " + ae + "\n Implement a method public String name() in the class " + klassName );
        }

        assertTrue("The method name must be public, meaning declaring it as public String name()", klass.method(instance, "name")
                .returning(String.class)
                .takingNoParams().isPublic());

        String out = klass.method(instance, "name").returning(String.class).takingNoParams().invoke();

        assertEquals("When we call:\nSong song = new Song(\"Grace\", 25);\nString name = song.name();\nThe variable name should have the value \"Grace\".\nOutput was: " + out, "Grace", out);

    }
    
    @Test
    public void testName2() throws Throwable {
        Reflex.ClassRef<Object> klass = Reflex.reflect(klassName);
        Object instance = klass.constructor().taking(String.class, int.class).invoke("Beathoven", 2000);

        try {
            klass.method(instance, "name")
                    .returning(String.class)
                    .takingNoParams().invoke();
        } catch (AssertionError ae) {
            fail("Error: " + ae + "\n Implement a method public String name() in the class " + klassName );
        }

        assertTrue("The method name must be public, meaning declaring it as public String name()", klass.method(instance, "name")
                .returning(String.class)
                .takingNoParams().isPublic());

        String out = klass.method(instance, "name").returning(String.class).takingNoParams().invoke();

        assertEquals("When we call:\nSong song = new Song(\"Beathoven\", 2000);\nString name = song.name();\nThe variable name should have the value \"Beathoven\".\nOutput was: " + out, "Beathoven", out);
    }

    
    @Test
    public void testLength() throws Throwable {
        Reflex.ClassRef<Object> klass = Reflex.reflect(klassName);
        Object instance = klass.constructor().taking(String.class, int.class).invoke("Grace", 25);

        try {
            klass.method(instance, "length")
                    .returning(int.class)
                    .takingNoParams().invoke();
        } catch (AssertionError ae) {
            fail("Error: " + ae + "\n Implement a method public int length() in the class " + klassName );
        }

        assertTrue("The method length must be public, meaning declaring it as public int length()", klass.method(instance, "length")
                .returning(int.class)
                .takingNoParams().isPublic());

        int out = klass.method(instance, "length").returning(int.class).takingNoParams().invoke();

        assertEquals("When we call:\nSong song = new Song(\"Grace\", 25);\nint length = song.length();\nThe variable length should have the value 25.\nOutput was: " + out, 25, out);
    }
    
    @Test
    public void testLength2() throws Throwable {
        Reflex.ClassRef<Object> klass = Reflex.reflect(klassName);
        Object instance = klass.constructor().taking(String.class, int.class).invoke("Beathoven", 1988);

        try {
            klass.method(instance, "length")
                    .returning(int.class)
                    .takingNoParams().invoke();
        } catch (AssertionError ae) {
            fail("Error: " + ae + "\n Implement a method public int length() in the class " + klassName );
        }

        assertTrue("The method length must be public, meaning declaring it as public int length()", klass.method(instance, "length")
                .returning(int.class)
                .takingNoParams().isPublic());

        int out = klass.method(instance, "length").returning(int.class).takingNoParams().invoke();

        assertEquals("When we call:\nSong song = new Song(\"Beathoven\", 1988);\nint length = song.length();\nThe variable length should have the value 1988.\nOutput was: " + out, 1988, out);
    }
    
    private void sanitaryCheck() throws SecurityException {
        Field[] fields = ReflectionUtils.findClass(klassName).getDeclaredFields();

        for (Field field : fields) {
            assertFalse("You do not need \"static variables\", remove " + oneField(field.toString()), field.toString().contains("static") && !field.toString().contains("final"));
            assertTrue("All object variables of the Song class should be private , change " + oneField(field.toString()), field.toString().contains("private"));
        }

        if (fields.length > 1) {
            int var = 0;
            for (Field field : fields) {
                if (!field.toString().contains("final")) {
                    var++;
                }
            }
            assertTrue("The class " + klassName + " only needs the variables name and length. Remove the extras.", var < 3);
        }
    }

    private String oneField(String toString) {
        return toString.replace(klassName + ".", "");
    }
}
