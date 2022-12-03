
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import org.junit.*;
import static org.junit.Assert.*;

@Points("05-03")
public class CubeTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void classAndConstructor() {
        createCube(5);
    }

    @Test
    public void volumeMethod() {
        Object c = createCube(5);

        assertEquals("With edge length 5, the volume of the cube should be 125. Test:\n"
                + "Cube c = new Cube(5);\n"
                + "System.out.println(c.volume());", 125, callVolume(c, 5));
    }

    @Test
    public void volumeMethod2() {
        Object c = createCube(7);

        assertEquals("With edge length 7, the volume of the cube should be 343. Test:\n"
                + "Cube c = new Cube(7);\n"
                + "System.out.println(c.volume());", 343, callVolume(c, 7));
    }

    @Test
    public void toStringMethod() {
        Object c = createCube(7);
        String out = io.getSysOut();
        String toStringFromCube = c.toString();
        assertTrue("toString should print nothing. Test:\n"
                + "Cube c = new Cube(7);\n"
                + "c.toString();\n"
                + "The program should print nothing.", out.equals(io.getSysOut()));

        assertTrue("Try it out:\n"
                + "Cube c = new Cube(7);\n"
                + "System.out.println(c.toString());\n"
                + "The output of the program should be:\n"
                    + "\"The length of the edge is 7 and the volume 343\"", toStringFromCube.contains("edge") && toStringFromCube.contains("7") && toStringFromCube.contains("343"));
    }

    @Test
    public void toStringMethod2() {
        Object c = createCube(3);
        String out = io.getSysOut();
        String toStringFromCube = c.toString();

        assertTrue("toString should print nothing. Test:\n"
                + "Cube c = new Cube(3);\n"
                + "c.toString();\n"
                + "The program should print nothing.", out.equals(io.getSysOut()));


        assertTrue("Try it out:\n"
                + "Cube c = new Cube(3);\n"
                + "System.out.println(c.toString());\n"
                + "The output of the program should be:\n"
                    + "\"The length of the edge is 3 and the volume 27\"", toStringFromCube.contains("edge") && toStringFromCube.contains("3") && toStringFromCube.contains("27"));
        

        assertFalse("Try it out:\n"
                + "Cube c = new Cube(3);\n"
                + "System.out.println(c.toString());\n"
                + "The output of the program should be:\n"
                    + "\"The length of the edge is 3 and the volume 27\"", toStringFromCube.contains("34") || toStringFromCube.contains("43"));
    }

    private Object createCube(int edgeLength) {
        Reflex.reflect("Cube").ctor().taking(int.class).requirePublic();
        try {
            return Reflex.reflect("Cube").ctor().taking(int.class).invoke(edgeLength);
        } catch (Throwable ex) {
            fail("Creating a cube led to an error. Try:\nCube cube = new Cube(" + edgeLength + ");");
        }

        return null;
    }

    private int callVolume(Object cube, int edgeLength) {
        Reflex.reflect("Cube").method("volume").returning(int.class).takingNoParams().requirePublic();
        try {
            return Reflex.reflect("Cube").method("volume").returning(int.class).takingNoParams().invokeOn(cube);
        } catch (Throwable ex) {
            fail("An error occurred when calling a cube's volume method. Try it out:\nCube cube = new Cube(" + edgeLength + ");\nSystem.out.println(cube.volume());");
        }

        return -1;
    }
}
