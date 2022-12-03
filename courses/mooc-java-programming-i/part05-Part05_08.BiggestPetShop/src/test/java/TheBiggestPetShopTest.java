
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.*;
import static org.junit.Assert.*;

@Points("05-08")
public class TheBiggestPetShopTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void test() {
        Pet hulda = new Pet("Hulda", "chihuahua");
        Person leo = new Person("Leo", hulda);

        String out = io.getSysOut();
        String returnValueFromLeoToString = leo.toString();
        assertTrue("toString method should not print a string!", out.equals(io.getSysOut()));

        String errorMessage = "String returned by toString() should contain the name of the person, name of their pet and the breed of their pet.\n"
                + "Try:\n"
                + "Pet hulda = new Pet(\"Hulda\", \"chihuahua\");\n"
                + "Person leo = new Person(\"Leo\", hulda);\n"
                + "System.out.println(leo.toString());";
        assertTrue(errorMessage, returnValueFromLeoToString.contains("Leo"));
        assertTrue(errorMessage, returnValueFromLeoToString.contains("Hulda"));
        assertTrue(errorMessage, returnValueFromLeoToString.contains("chihuahua"));
    }

    @Test
    public void test2() {
        Pet flounder = new Pet("Flounder", "fish");
        Person ariel = new Person("Ariel", flounder);

        String out = io.getSysOut();
        String arielToString = ariel.toString();
        assertTrue("toString method should not print a string!", out.equals(io.getSysOut()));

        String errorMessage = "String returned by toString() should contain the name of the person, name of their pet and the breed of their pet.\n"
                + "Try:\n"
                + "Pet flounder = new Pet(\"Flounder\", \"fish\");\n"
                + "Person ariel = new Person(\"Ariel\", flounder);\n"
                + "System.out.println(ariel.toString());";
        assertTrue(errorMessage, arielToString.contains("Ariel"));
        assertTrue(errorMessage, arielToString.contains("Flounder"));
        assertTrue(errorMessage, arielToString.contains("fish"));
    }

}
