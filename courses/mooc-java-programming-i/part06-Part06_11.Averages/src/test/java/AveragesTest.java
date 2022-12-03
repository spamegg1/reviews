
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.util.*;
import org.junit.*;
import static org.junit.Assert.*;

public class AveragesTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    @Points("06-11.1")
    public void methodAverageOfGradesExists() {
        Reflex.reflect(GradeRegister.class).method("averageOfGrades").returning(double.class).takingNoParams().requirePublic();
    }
    
    @Test
    @Points("06-11.1")
    public void averageOfGradesOfEmptyRegisterReturnsMinusOne() throws Throwable {
        Reflex.reflect(GradeRegister.class).method("averageOfGrades").returning(double.class).takingNoParams().requirePublic();
        
        String e = "The code that caused the error:\n"
                + "GradeRegister register = new GradeRegister();\n"
                + "System.out.println(register.averageOfGrades());";
        
        GradeRegister register = new GradeRegister();
        
        double avg = Reflex.reflect(GradeRegister.class).method("averageOfGrades").returning(double.class).takingNoParams().withNiceError(e).invokeOn(register);
        assertEquals("If the 'averageOfGrades' is called on an empty grade register, it should return the number -1. " + e, -1, avg, 0.0001);
    }

    @Test
    @Points("06-11.1")
    public void averageOfGrades1() throws Throwable {
        GradeRegister register = new GradeRegister();
        register.addGradeBasedOnPoints(91);
        register.addGradeBasedOnPoints(92);
        register.addGradeBasedOnPoints(93);

        String e = "The code that caused the error:\n"
                + "GradeRegister register = new GradeRegister();\n"
                + "register.addGradeBasedOnPoints(91);\n"
                + "register.addGradeBasedOnPoints(92);\n"
                + "register.addGradeBasedOnPoints(93);\n"
                + "register.averageOfGrades();";

        double avg = Reflex.reflect(GradeRegister.class).method("averageOfGrades").returning(double.class).takingNoParams().withNiceError(e).invokeOn(register);
        assertEquals("The average of the grades was not correct. " + e, 5.0, avg, 0.001);
    }

    @Test
    @Points("06-11.1")
    public void averageOfGrades2() throws Throwable {
        GradeRegister register = new GradeRegister();
        register.addGradeBasedOnPoints(91);
        register.addGradeBasedOnPoints(92);
        register.addGradeBasedOnPoints(93);
        register.addGradeBasedOnPoints(88);

        String e = "The code that caused the error:\n"
                + "GradeRegister register = new GradeRegister();\n"
                + "register.addGradeBasedOnPoints(91);\n"
                + "register.addGradeBasedOnPoints(92);\n"
                + "register.addGradeBasedOnPoints(93);\n"
                + "register.addGradeBasedOnPoints(88);\n"
                + "register.avergageOfGrades();";

        double avg = Reflex.reflect(GradeRegister.class).method("averageOfGrades").returning(double.class).takingNoParams().withNiceError(e).invokeOn(register);
        assertEquals("The average of the grades was not correct. " + e, 4.75, avg, 0.001);
    }

    @Test
    @Points("06-11.1")
    public void averageOfGrades3() throws Throwable {
        GradeRegister register = new GradeRegister();
        register.addGradeBasedOnPoints(91);
        register.addGradeBasedOnPoints(92);
        register.addGradeBasedOnPoints(93);
        register.addGradeBasedOnPoints(88);
        register.addGradeBasedOnPoints(61);
        register.addGradeBasedOnPoints(59);
        register.addGradeBasedOnPoints(13);
        register.addGradeBasedOnPoints(14);

        String e = "The code that caused the error:\n"
                + "GradeRegister register = new GradeRegister();\n"
                + "register.addGradeBasedOnPoints(91);\n"
                + "register.addGradeBasedOnPoints(92);\n"
                + "register.addGradeBasedOnPoints(93);\n"
                + "register.addGradeBasedOnPoints(88);\n"
                + "register.addGradeBasedOnPoints(61);\n"
                + "register.addGradeBasedOnPoints(59);\n"
                + "register.addGradeBasedOnPoints(13);\n"
                + "register.addGradeBasedOnPoints(14);\n"
                + "register.averageOfGrades();";

        double avg = Reflex.reflect(GradeRegister.class).method("averageOfGrades").returning(double.class).takingNoParams().withNiceError(e).invokeOn(register);
        assertEquals("The average of the grades was not correct. " + e, 2.75, avg, 0.001);
    }

    @Test
    @Points("06-11.2")
    public void methodAverageOfPointsExists() {
        Reflex.reflect(GradeRegister.class).method("averageOfPoints").returning(double.class).takingNoParams().requirePublic();
    }
    
    @Test
    @Points("06-11.2")
    public void averageOfPointsOfEmptyRegisterReturnsMinusOne() throws Throwable {
        Reflex.reflect(GradeRegister.class).method("averageOfPoints").returning(double.class).takingNoParams().requirePublic();
        
        String e = "The code that caused the error:\n"
                + "GradeRegister register = new GradeRegister();\n"
                + "System.out.println(register.averageOfPoints());";
        
        GradeRegister register = new GradeRegister();
        
        double avg = Reflex.reflect(GradeRegister.class).method("averageOfPoints").returning(double.class).takingNoParams().withNiceError(e).invokeOn(register);
        assertEquals("The average of an empty register should be returned as -1. " + e, -1, avg, 0.0001);
    }

    @Test
    @Points("06-11.2")
    public void averageOfPoints1() throws Throwable {
        GradeRegister register = new GradeRegister();
        register.addGradeBasedOnPoints(91);
        register.addGradeBasedOnPoints(92);
        register.addGradeBasedOnPoints(93);

        String e = "The code that caused the error:\n"
                + "GradeRegister register = new GradeRegister();\n"
                + "register.addGradeBasedOnPoints(91);\n"
                + "register.addGradeBasedOnPoints(92);\n"
                + "register.addGradeBasedOnPoints(93);\n"
                + "register.averageOfPoints();";

        double avg = Reflex.reflect(GradeRegister.class).method("averageOfPoints").returning(double.class).takingNoParams().withNiceError(e).invokeOn(register);
        assertEquals("The average of the points was incorrect. " + e, 92.0, avg, 0.0001);
    }

    @Test
    @Points("06-11.2")
    public void averageOfPoints2() throws Throwable {
        GradeRegister register = new GradeRegister();
        register.addGradeBasedOnPoints(3);
        register.addGradeBasedOnPoints(3);
        register.addGradeBasedOnPoints(4);

        String e = "The code that caused the error:\n"
                + "GradeRegister register = new GradeRegister();\n"
                + "register.addGradeBasedOnPoints(3);\n"
                + "register.addGradeBasedOnPoints(3);\n"
                + "register.addGradeBasedOnPoints(4);\n"
                + "register.averageOfPoints();";

        double avg = Reflex.reflect(GradeRegister.class).method("averageOfPoints").returning(double.class).takingNoParams().withNiceError(e).invokeOn(register);
        assertEquals("The average of the points was incorrect. " + e, 3.333333333, avg, 0.001);
        
    }

    @Test
    @Points("06-11.2")
    public void averageOfPoints3() throws Throwable {
        GradeRegister register = new GradeRegister();
        register.addGradeBasedOnPoints(3);
        register.addGradeBasedOnPoints(3);
        register.addGradeBasedOnPoints(3);
        register.addGradeBasedOnPoints(4);
        register.addGradeBasedOnPoints(4);
        register.addGradeBasedOnPoints(4);
        register.addGradeBasedOnPoints(3);

        String e = "The code that caused the error:\n"
                + "GradeRegister register = new GradeRegister();\n"
                + "register.addGradeBasedOnPoints(3);\n"
                + "register.addGradeBasedOnPoints(3);\n"
                + "register.addGradeBasedOnPoints(3);\n"
                + "register.addGradeBasedOnPoints(4);\n"
                + "register.addGradeBasedOnPoints(4);\n"
                + "register.addGradeBasedOnPoints(4);\n"
                + "register.addGradeBasedOnPoints(3);\n"
                + "register.averageOfPoints();";
        
        double avg = Reflex.reflect(GradeRegister.class).method("averageOfPoints").returning(double.class).takingNoParams().withNiceError(e).invokeOn(register);
        assertEquals("The average of the points was incorrect. " + e, 3.4285714, avg, 0.001);

    }

    @Test
    @Points("06-11.3")
    public void printsInUserInterface1() throws Throwable {
        String in = "82\n83\n96\n51\n48\n56\n61\n\n";
        Scanner input = new Scanner(in);

        GradeRegister register = new GradeRegister();

        UserInterface ui = new UserInterface(register, input);
        ui.start();

        assertTrue("Expected the output of the program to contain the average of the points. With the input:\n" + in + "the program should give 68.1428... as the average of the points.", io.getSysOut().contains("68.1"));
        assertTrue("Expected the output of the program to contain the average of the grades. With the input:\n" + in + "the program should give 2.4285... as the average of the grades.", io.getSysOut().contains("2.4"));
    }

    @Test
    @Points("06-11.3")
    public void printsInUserInterface2() throws Throwable {
        String in = "16\n8\n-3\n62\n99\n101\n64\n\n";
        Scanner input = new Scanner(in);

        GradeRegister register = new GradeRegister();

        UserInterface ui = new UserInterface(register, input);
        ui.start();

        assertTrue("Expected the output of the program to contain the average of the points. With the input:\n" + in + "the program should give 49.8 as the average of the points.", io.getSysOut().contains("49."));
        assertTrue("Expected the output of the program to contain the average of the grades. With the input:\n" + in + "the program should give 1.8 as the average of the grades.", io.getSysOut().contains("1."));
    
    }
}
