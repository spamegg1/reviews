
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Field;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

@Points("04-11")
public class FilmTest {

    Reflex.ClassRef<Object> klass;
    String klassName = "Film";

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
        assertTrue("Declare a public contstructor in class " + klassName + ": public " + klassName + "(String filmName, int filmAgeRating)", cc.isPublic());
        cc.invoke("Testfilm", 5);
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

        assertEquals("When we call:\nFilm picture = new Film(\"Grace\", 25);\nString name = picture.name();\nThe variable name should have the value \"Grace\".\nOutput was: " + out, "Grace", out);

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

        assertEquals("When we call:\nFilm picture = new Film(\"Beathoven\", 16);\nString name = picture.name();\nThe variable name should have the value \"Beathoven\".\nOutput was: " + out, "Beathoven", out);
    }

    @Test
    public void testAgeRating() throws Throwable {
        Reflex.ClassRef<Object> klass = Reflex.reflect(klassName);
        Object instance = klass.constructor().taking(String.class, int.class).invoke("Beathoven", 16);

        try {
            klass.method(instance, "ageRating")
                    .returning(int.class)
                    .takingNoParams().invoke();
        } catch (AssertionError ae) {
            fail("Error: " + ae + "\n Implement a method public int ageRating() in the class " + klassName );
        }

        assertTrue("The method ageRating must be public, meaning declaring it as public int ageRating()", klass.method(instance, "ageRating")
                .returning(int.class)
                .takingNoParams().isPublic());

        int out = klass.method(instance, "ageRating").returning(int.class).takingNoParams().invoke();

        assertEquals("When we call:\nFilm picture = new Film(\"Beathoven\", 16);\nint ageRating = picture.ageRating();\nThe variable ageRating should have the value 16.\nOutput was: " + out, 16, out);
    }
    
    @Test
    public void testAgeRating2() throws Throwable {
        Reflex.ClassRef<Object> klass = Reflex.reflect(klassName);
        Object instance = klass.constructor().taking(String.class, int.class).invoke("Karvinen", 7);

        try {
            klass.method(instance, "ageRating")
                    .returning(int.class)
                    .takingNoParams().invoke();
        } catch (AssertionError ae) {
            fail("Error: " + ae + "\n Implement a method public int ageRating() in the class " + klassName);
        }

        assertTrue("The method ageRating must be public, meaning declaring it as public int ageRating()", klass.method(instance, "ageRating")
                .returning(int.class)
                .takingNoParams().isPublic());

        int out = klass.method(instance, "ageRating").returning(int.class).takingNoParams().invoke();

        assertEquals("When we call:\nFilm picture = new Film(\"Karvinen\", 7);\nint ageRating = picture.ageRating();\nThe variable ageRating should have the value 7.\nOutput: " + out, 7, out);
    }

    private void sanitaryCheck() throws SecurityException {
        Field[] fields = ReflectionUtils.findClass(klassName).getDeclaredFields();

        for (Field field : fields) {
            assertFalse("You do not need \"static variables\", remove " + oneField(field.toString()), field.toString().contains("static") && !field.toString().contains("final"));
            assertTrue("All object variables of the Film class should be private , change " + oneField(field.toString()), field.toString().contains("private"));
        }

        if (fields.length > 1) {
            int var = 0;
            for (Field field : fields) {
                if (!field.toString().contains("final")) {
                    var++;
                }
            }
            assertTrue("The class " + klassName + " only needs the variables name and ageRating. Remove the extras.", var < 3);
        }
    }

    private String oneField(String toString) {
        return toString.replace(klassName + ".", "");
    }
}
