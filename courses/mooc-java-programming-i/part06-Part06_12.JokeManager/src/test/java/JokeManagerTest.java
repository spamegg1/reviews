
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Field;
import java.util.*;
import org.junit.*;
import static org.junit.Assert.*;

public class JokeManagerTest {
    
    public String jokeClassName = "JokeManager";
    public String uiClassName = "UserInterface";

    @Rule
    public MockStdio io = new MockStdio();

    @Test(timeout = 100)
    @Points("06-12.1")
    public void classJokeManagerExists() {
        Reflex.reflect(jokeClassName).requirePublic();
    }

    @Test(timeout = 100)
    @Points("06-12.1")
    public void classJokeManagerHasParameterlessConstructor() {
        Reflex.reflect(jokeClassName).ctor().takingNoParams().requirePublic();
    }

    @Test(timeout = 100)
    @Points("06-12.1")
    public void classJokeManagerOnlyHasOneObjectVariable() {
        sanityCheck(jokeClassName, 1, "one object variable");
    }

    @Test(timeout = 100)
    @Points("06-12.1")
    public void classJokeManagerHasCorrectMethods() {
        Reflex.reflect(jokeClassName).method("addJoke").returningVoid().taking(String.class).requirePublic();
        Reflex.reflect(jokeClassName).method("printJokes").returningVoid().takingNoParams().requirePublic();
        Reflex.reflect(jokeClassName).method("drawJoke").returning(String.class).takingNoParams().requirePublic();
    }

    @Test(timeout = 100)
    @Points("06-12.1")
    public void noJokesAddedAndDraw() throws Throwable {
        String code = "JokeManager manager = new JokeManager();\n"
                + "System.out.println(manager.drawJoke());";

        Object jokes = Reflex.reflect(jokeClassName).ctor().takingNoParams().withNiceError("An error occurred when creating the joke manager. Test the code:\n" + code).invoke();
        String joke = Reflex.reflect(jokeClassName).method("drawJoke").returning(String.class).takingNoParams().withNiceError("An error occurred when drawing a joke. Test the code:\n" + code).invokeOn(jokes);
        assertEquals("When a joke manager contains no jokes, the drawJoke method should return the string \"Jokes are in short supply.\". Test the code:\n" + code, "Jokes are in short supply.", joke);
    }

    @Test(timeout = 100)
    @Points("06-12.1")
    public void oneJokeAndDraw() throws Throwable {
        String code = "JokeManager manager = new JokeManager();\n"
                + "manager.addJoke(\"What is red and smells of blue paint? - Red paint.\"));\n"
                + "System.out.println(manager.drawJoke());";

        Object jokes = Reflex.reflect(jokeClassName).ctor().takingNoParams().withNiceError("An error occurred when creating the joke manager. Test the code:\n" + code).invoke();
        Reflex.reflect(jokeClassName).method("addJoke").returningVoid().taking(String.class).withNiceError("An error occurred when adding a joke. Test the code:\n" + code).invokeOn(jokes, "What is red and smells of blue paint? - Red paint.");
        String joke = Reflex.reflect(jokeClassName).method("drawJoke").returning(String.class).takingNoParams().withNiceError("An error occurred when drawing a joke. Test the code\n" + code).invokeOn(jokes);
        assertEquals("When the joke manager contains jokes, the drawJoke mehtod should return one of them. Test the code:\n" + code, "What is red and smells of blue paint? - Red paint.", joke);
    }

    @Test(timeout = 100)
    @Points("06-12.1")
    public void manyJokesAndDraw() throws Throwable {
        String code = "JokeManager manager = new JokeManager();\n"
                + "manager.addJoke(\"What is red and smells of blue paint? - Red paint.\");\n"
                + "manager.addJoke(\"MWhat is blue and smells of red paint? - Blue paint.\");\n"
                + "System.out.println(manager.drawJoke());";

        Object jokes = Reflex.reflect(jokeClassName).ctor().takingNoParams().withNiceError("An error occurred when creating the joke manager. Test the code:\n" + code).invoke();
        Reflex.reflect(jokeClassName).method("addJoke").returningVoid().taking(String.class).withNiceError("An error occurred when adding a joke. Test the code:\n" + code).invokeOn(jokes, "What is red and smells of blue paint? - Red paint.");
        Reflex.reflect(jokeClassName).method("addJoke").returningVoid().taking(String.class).withNiceError("An error occurred when adding a joke. Test the code:\n" + code).invokeOn(jokes, "What is blue and smells of red paint? - Blue paint.");

        Map<String, Integer> counts = new HashMap<>();
        for (int i = 0; i < 100; i++) {
            String joke = Reflex.reflect(jokeClassName).method("drawJoke").returning(String.class).takingNoParams().withNiceError("An error occurred when drawing a joke. Test the code:\n" + code).invokeOn(jokes);
            counts.put(joke, counts.getOrDefault(joke, 0) + 1);
        }

        assertTrue("When the joke manager contains multiple choice, each should have the same probability of being draw. Check the drawing logic.\nTest the code:\n" + code, counts.getOrDefault("What is red and smells of blue paint? - Red paint.", 0) >= 25);
        assertTrue("When the joke manager contains multiple choice, each should have the same probability of being draw. Check the drawing logic.\nTest the code:\n"  + code, counts.getOrDefault("What is blue and smells of red paint? - Blue paint.", 0) >= 25);
        
    }

    @Test(timeout = 100)
    @Points("06-12.1")
    public void printingJokes() throws Throwable {
        String code = "JokeManager manager = new JokeManager();\n"
                + "manager.addJoke(\"Why do we tell actors to break a leg? - Because every play has a cast.\");\n"
                + "manager.addJoke(\"Have you heard about the new restaurant called Karma? It has no menu -- you get what you deserve.\");\n"
                + "manager.printJokes();";

        Object jokes = Reflex.reflect(jokeClassName).ctor().takingNoParams().withNiceError("An error occurred when creating the joke manager. Test the code:\n" + code).invoke();
        
        Reflex.reflect(jokeClassName).method("addJoke").returningVoid().taking(String.class).withNiceError("An error occurred when adding a joke. Test the code:\n" + code).invokeOn(jokes, "Why do we tell actors to break a leg? - Because every play has a cast.");
        Reflex.reflect(jokeClassName).method("addJoke").returningVoid().taking(String.class).withNiceError("An error occurred when adding a joke. Test the code:\n" + code).invokeOn(jokes, "Have you heard about the new restaurant called Karma? It has no menu -- you get what you deserve.");
        Reflex.reflect(jokeClassName).method("printJokes").returningVoid().takingNoParams().withNiceError("An error occurred when printing the jokes. Test the code:\n" + code).invokeOn(jokes);

        assertEquals("When the jokes are printed, each joke should appear in the print exactly once. Test the code:\n" + code, 1, countInOutput("Why do we tell actors to break a leg? - Because every play has a cast."));
        assertEquals("When the jokes are printed, each joke should appear in the print exactly once. Test the code:\n" + code, 1, countInOutput("Have you heard about the new restaurant called Karma? It has no menu -- you get what you deserve."));
    }

    @Test(timeout = 100)
    @Points("06-12.2")
    public void classUserInterfaceExists() {
        Reflex.reflect(uiClassName).requirePublic();
    }

    @Test(timeout = 100)
    @Points("06-12.2")
    public void classUserInterfaceHasConstructorWithTwoParameters() {
        Reflex.reflect(uiClassName).ctor().taking(Reflex.reflect(jokeClassName).cls(), Scanner.class).requirePublic();
    }

    @Test(timeout = 100)
    @Points("06-12.2")
    public void classUserInterfaceHasMethodStart() {
        Reflex.reflect(uiClassName).method("start").returningVoid().takingNoParams().requirePublic();
    }

    @Test(timeout = 100)
    @Points("06-12.2")
    public void classUserInterfaceHasTwoObjectVariables() {
        sanityCheck(uiClassName, 2, "object variables of types Scanner and JokeManager");
    }

    @Test(timeout = 100)
    @Points("06-12.2")
    public void testStopping() throws Throwable {
        String code = "JokeManager manager = new JokeManager();\n"
                + "Scanner scanner = new Scanner(System.in);\n"
                + "\n"
                + "UserInterface ui = new UserInterface(manager, scanner);\n"
                + "ui.start();";

        String commands = "X\n";

        Object jokes = Reflex.reflect(jokeClassName).ctor().takingNoParams().withNiceError("An error occurred when creating the joke manager. Test the code:\n" + code).invoke();
        Scanner scanner = new Scanner(commands);
        Object ui = Reflex.reflect(uiClassName).ctor().taking(Reflex.reflect(jokeClassName).cls(), Scanner.class).withNiceError("An error occurred when creating the user interface. Test the code:\n" + code).invoke(jokes, scanner);

        Reflex.reflect(uiClassName).method("start").returningVoid().takingNoParams().withNiceError("An error occurred when starting the user interface. Test the code:\n" + code + "\nWith the input:\n" + commands).invokeOn(ui);
    }

    @Test(timeout = 100)
    @Points("06-12.2")
    public void testAddingAndStopping() throws Throwable {
        String code = "JokeManager manager = new JokeManager();\n"
                + "Scanner scanner = new Scanner(System.in);\n"
                + "\n"
                + "UserInterface ui = new UserInterface(manager, scanner);\n"
                + "ui.start();";

        String commands = "1\nTeacher, I came up with a new word! .. Do tell, what is it ?.. Plagiarism!\nX\n";
        Scanner scanner = new Scanner(commands);

        Object jokes = Reflex.reflect(jokeClassName).ctor().takingNoParams().withNiceError("An error occurred when creating the joke manager. Test the code:\n" + code).invoke();
        Object ui = Reflex.reflect(uiClassName).ctor().taking(Reflex.reflect(jokeClassName).cls(), Scanner.class).withNiceError("An error occurred when creating the user interface. Test the code:\n" + code).invoke(jokes, scanner);

        Reflex.reflect(uiClassName).method("start").returningVoid().takingNoParams().withNiceError("An error occurred when starting the user interface. Test the code:\n" + code + "\nWith the input:\n" + commands).invokeOn(ui);

        code += "\nmanager.printJokes();";
        Reflex.reflect(jokeClassName).method("printJokes").returningVoid().takingNoParams().withNiceError("An error occurred when printing the jokes. Test the code:\n" + code + "\nWith the input:\n" + commands).invokeOn(jokes);

        assertEquals("When the jokes are printed, each joke should appear exactly once in the output. Test the code:\n" + code, 1, countInOutput("Teacher, I came up with a new word! .. Do tell, what is it ?.. Plagiarism!"));
    }

    @Test(timeout = 100)
    @Points("06-12.2")
    public void testPrinting() throws Throwable {
        String code = "JokeManager manager = new JokeManager();\n"
                + "manager.addJoke(\"Just joking 1\");\n"
                + "manager.addJoke(\"Just joking 2\");\n"
                + "Scanner scanner = new Scanner(System.in);\n"
                + "\n"
                + "UserInterface ui = new UserInterface(manager, scanner);\n"
                + "ui.start();";

        String commands = "3\nX\n";
        Scanner scanner = new Scanner(commands);

        Object jokes = Reflex.reflect(jokeClassName).ctor().takingNoParams().withNiceError("An error occurred when creating the joke manager. Test the code:\n" + code).invoke();
        Reflex.reflect(jokeClassName).method("addJoke").returningVoid().taking(String.class).withNiceError("An error occurred when adding a joke. Test the code:\n" + code).invokeOn(jokes, "Just joking 1");
        Reflex.reflect(jokeClassName).method("addJoke").returningVoid().taking(String.class).withNiceError("Virhe vitsin lisäämisessä vitsipankkiin. Kokeile koodia:\n" + code).invokeOn(jokes, "Just joking 2");
        
        Object ui = Reflex.reflect(uiClassName).ctor().taking(Reflex.reflect(jokeClassName).cls(), Scanner.class).withNiceError("An error occurred when creating the user interface. Test the code:\n" + code).invoke(jokes, scanner);

        Reflex.reflect(uiClassName).method("start").returningVoid().takingNoParams().withNiceError("An error occurred when starting the user interface. Test the code:\n" + code + "\nWith the input:\n" + commands).invokeOn(ui);

        assertEquals("When the jokes are printed, each joke should appear exactly once in the output. Test the code:\n" + code, 1, countInOutput("Just joking 1"));
        assertEquals("When the jokes are printed, each joke should appear exactly once in the output. Test the code:\n" + code, 1, countInOutput("Just joking 2"));

    }

    @Test(timeout = 100)
    @Points("06-12.2")
    public void testDrawing() throws Throwable {
        String code = "JokeManager manager = new JokeManager();\n"
                + "manager.addJoke(\"Just joking 1\");\n"
                + "manager.addJoke(\"Just joking 2\");\n"
                + "manager.addJoke(\"Just joking 3\");\n"
                + "Scanner scanner = new Scanner(System.in);\n"
                + "\n"
                + "UserInterface ui = new UserInterface(manager, scanner);\n"
                + "ui.start();";

        String commands = "2\nX\n";
        Scanner scanner = new Scanner(commands);

        Object jokes = Reflex.reflect(jokeClassName).ctor().takingNoParams().withNiceError("An error occurred when creating the joke manager. Test the code:\n" + code).invoke();
        Reflex.reflect(jokeClassName).method("addJoke").returningVoid().taking(String.class).withNiceError("An error occurred when adding a joke. Test the code:\n" + code).invokeOn(jokes, "Just joking 1");
        Reflex.reflect(jokeClassName).method("addJoke").returningVoid().taking(String.class).withNiceError("An error occurred when adding a joke. Test the code:\n" + code).invokeOn(jokes, "Just joking 2");
        Reflex.reflect(jokeClassName).method("addJoke").returningVoid().taking(String.class).withNiceError("An error occurred when adding a joke. Test the code:\n" + code).invokeOn(jokes, "Just joking 3");
       
        Object ui = Reflex.reflect(uiClassName).ctor().taking(Reflex.reflect(jokeClassName).cls(), Scanner.class).withNiceError("Virhe kayttoliittyman luomisessa. Kokeile koodia:\n" + code).invoke(jokes, scanner);

        Reflex.reflect(uiClassName).method("start").returningVoid().takingNoParams().withNiceError("An error occurred when starting the user interface. Test the code:\n" + code + "\nWith the input:\n" + commands).invokeOn(ui);

        assertEquals("When the user interface is instructed to draw a joke, the program should print exactly one joke. Test the code:\n" + code + "\nWith the input:\n" + commands, 1, countInOutput("Just joking 1") + countInOutput("Just joking 2") + countInOutput("Just joking 3"));
    }

    private void sanityCheck(String klassName, int n, String m) throws SecurityException {
        Field[] fields = ReflectionUtils.findClass(klassName).getDeclaredFields();

        for (Field field : fields) {
            assertFalse("you don't need \"static variables\", remove from the class " + klassName + " the variable " + field(field.toString(), klassName), field.toString().contains("static") && !field.toString().contains("final"));
            assertTrue("the visibility of all the object variables should be private, but the class " + klassName + " had: " + field(field.toString(), klassName), field.toString().contains("private"));
        }

        if (fields.length > 1) {
            int var = 0;
            for (Field field : fields) {
                if (!field.toString().contains("final")) {
                    var++;
                }
            }
            assertTrue("The class " + klassName + " only needs" + m + ", remove the extra variables", var <= n);
        }
    }

    private String field(String toString, String klassName) {
        return toString.replace(klassName + ".", "").replace("java.lang.", "").replace("java.util.", "");
    }

    private int countInOutput(String joke) {
        String out = io.getSysOut();
        int count = 0;
        while (out.contains(joke)) {
            out = out.replace(joke, "");
            count++;
        }

        return count;
    }

}
