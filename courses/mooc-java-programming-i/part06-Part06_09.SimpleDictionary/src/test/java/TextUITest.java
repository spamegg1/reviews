
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Scanner;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class TextUITest<_SimpleDictionary> {

    private Class textUIClass;
    private Constructor textUIConstructor;
    private Method startMethod;

    String klassName = "TextUI";
    Reflex.ClassRef<Object> klass;

    @Rule
    public MockStdio io = new MockStdio();

    @Before
    public void setUp() {
        klass = Reflex.reflect(klassName);
        try {
            textUIClass = ReflectionUtils.findClass("TextUI");
            textUIConstructor = ReflectionUtils.requireConstructor(textUIClass, Scanner.class, SimpleDictionary.class);
            startMethod = ReflectionUtils.requireMethod(textUIClass, "start");
        } catch (Throwable t) {
        }
    }

    @Test
    @Points("06-09.1")
    public void classIsPublic() {
        assertTrue("The class " + klassName + " must be public, so it must be defined as\npublic class " + klassName + " {...\n}", klass.isPublic());
    }

    @Test
    @Points("06-09.1")
    public void noExtraVariables() {
        sanityCheck(klassName, 2, "Scanner and SimpleDictionary typed object variables");
    }

    @Test
    @Points("06-09.1")
    public void constructor() throws Throwable {
        Reflex.ClassRef<_SimpleDictionary> _SimpleDictionaryRef = Reflex.reflect("SimpleDictionary");
        _SimpleDictionary dict = _SimpleDictionaryRef.constructor().takingNoParams().invoke();

        Reflex.MethodRef2<Object, Object, Scanner, _SimpleDictionary> ctor = klass.constructor().
                taking(Scanner.class, _SimpleDictionaryRef.cls()).withNiceError();
        assertTrue("Give the class " + klassName + " a public constructor: public " + klassName + "(Scanner scanner, SimpleDictionary dictionary)", ctor.isPublic());
        String e = "the error was caused by the code new TextUI(new Scanner(System.in), new SimpleDictionary());";
        ctor.withNiceError(e).invoke(new Scanner(System.in), dict);
    }

    public Object create() throws Throwable {
        Reflex.ClassRef<_SimpleDictionary> _SimpleDictionaryRef = Reflex.reflect("SimpleDictionary");
        _SimpleDictionary dict = _SimpleDictionaryRef.constructor().takingNoParams().invoke();

        Reflex.MethodRef2<Object, Object, Scanner, _SimpleDictionary> ctor = klass.constructor().
                taking(Scanner.class, _SimpleDictionaryRef.cls()).withNiceError();
        return ctor.withNiceError().invoke(new Scanner("end"), dict);
    }

    public Object create(Scanner scanner) throws Throwable {
        Reflex.ClassRef<_SimpleDictionary> _SimpleDictionaryRef = Reflex.reflect("SimpleDictionary");
        _SimpleDictionary dict = _SimpleDictionaryRef.constructor().takingNoParams().invoke();

        Reflex.MethodRef2<Object, Object, Scanner, _SimpleDictionary> ctor = klass.constructor().
                taking(Scanner.class, _SimpleDictionaryRef.cls()).withNiceError();
        return ctor.withNiceError().invoke(scanner, dict);
    }

    @Test
    @Points("06-09.1")
    public void startMethod() throws Throwable {
        String method = "start";

        Object object = TextUITest.this.create();

        assertTrue("give the class " + klassName + " the method public void " + method + "() ",
                klass.method(object, method)
                        .returningVoid().takingNoParams().isPublic());

        String v = "\nThe error was caused by the following code\n "
                + "TextUI t = new TextUI(new Scanner(System.in), new SimpleDictionary());\n"
                + "t.start();";

        klass.method(object, method)
                .returningVoid().takingNoParams().withNiceError(v).invoke();
    }

    @Test(timeout = 200)
    @Points("06-09.1")
    public void endingCommandOfTextUIWorks() {
        Scanner scanner = new Scanner("end\n");
        Object textUI = createTextUIWithScannerAndWords(scanner, "a", "b");
        try {
            startMethod.invoke(textUI);
        } catch (Throwable t) {
            fail("Make sure the text UI is stopped when the user enters the command 'end'."
                    + " Are you certain you are using the Scanner that was passed as a constructor parameter?");
        }
    }

    @Test(timeout = 200)
    @Points("06-09.1")
    public void textUIEndCommandWorksEvenWithDifferentInput() {
        Scanner scanner = new Scanner("help\ncarrot\nend\n");
        Object textUI = createTextUIWithScannerAndWords(scanner, "a", "b");
        try {
            startMethod.invoke(textUI);
        } catch (Throwable t) {
            fail("Make sure the text UI stops when the user enters the command 'end'.");
        }
    }

    @Test(timeout = 200)
    @Points("06-09.2")
    public void addCommandWorks() {
        Scanner scanner = new Scanner("add\ncarrot\nporkkana\nend\n");
        SimpleDictionary dictionary = createSimpleDictionaryWithWords();
        Object textUI = createTextUI(scanner, dictionary);

        try {
            startMethod.invoke(textUI);
        } catch (Throwable t) {
            fail("Make sure that the text UI stops when the user enters the command 'end'.");
        }

        if (!containsTranslation(dictionary, "carrot", "porkkana")) {
            fail("Make sure that the command \"add\" adds a new translation to the dictionary.");
        }
    }

    @Test(timeout = 200)
    @Points("06-09.2")
    public void addCommandWorksWithManyPairsOfWords() {
        Scanner scanner = new Scanner("add\ncarrot\nporkkana\nadd\nkey\navain\nend\n");
        SimpleDictionary dictionary = createSimpleDictionaryWithWords();
        Object textUI = createTextUI(scanner, dictionary);

        try {
            startMethod.invoke(textUI);
        } catch (Throwable t) {
            fail("Make sure that the text UI stops when the user enters the command 'end'.");
        }

        if (!containsTranslation(dictionary, "carrot", "porkkana")) {
            fail("Make sure that the command \"add\" adds a new translation to the dictionary.");
        }

        if (!containsTranslation(dictionary, "key", "avain")) {
            fail("Make sure that the command \"add\" adds a new translation to the dictionary.");
        }
    }

    @Test(timeout = 200)
    @Points("06-09.3")
    public void searchCommandWorksWithOnePairOfWords() {
        Scanner scanner = new Scanner("search\ncarrot\nend\n");
        Object dictionary = createSimpleDictionaryWithWords("carrot", "porkkana");
        Object textUI = createTextUI(scanner, dictionary);

        try {
            startMethod.invoke(textUI);
        } catch (Throwable t) {
            fail("Make sure that the text UI stops when the user enters the command 'end'.");
        }

        String output = io.getSysOut();
        if (!output.contains("porkkana")) {
            fail("Make sure the command 'search' returns the correct string.");
        }
    }

    @Test(timeout = 200)
    @Points("06-09.3")
    public void searchCommandWorksWithMultiplePairsOfWords() {
        Scanner scanner = new Scanner("search\none\nend\n");
        Object dictionary = createSimpleDictionaryWithWords("carrot", "porkkana", "one", "yksi");
        Object textUI = createTextUI(scanner, dictionary);

        try {
            startMethod.invoke(textUI);
        } catch (Throwable t) {
            fail("Make sure that the text UI stops when the user enters the command 'end'.");
        }

        String output = io.getSysOut();
        if (!output.contains("yksi")) {
            fail("Make sure the command 'search' returns the correct string.");
        }

        if (output.contains("porkkana") || output.contains("carrot")) {
            fail("When the contents of the dictionary are carrot=porkkana and one=yksi, and the word 'one' is searcher for,"
                    + "the words 'carrot' or 'porkkana' should not appear in the output."); 
        }
    }

    @Test(timeout = 200)
    @Points("06-09.4")
    public void searchCommandDoesNotReturnNull() {
        Scanner scanner = new Scanner("search\ntwo\nend\n");
        Object dictionary = createSimpleDictionaryWithWords("carrot", "porkkana", "one", "yksi");
        Object textUI = createTextUI(scanner, dictionary);

        try {
            startMethod.invoke(textUI);
        } catch (Throwable t) {
            fail("Make sure that the text UI stops when the user enters the command 'end'.");
        }

        String output = io.getSysOut();
        if (!output.contains("Word two was not")) {
            fail("If the searched string is not found, the output should tell that."
                    + "Make sure that the print of the program is exactly as instructed in the assignment.");
        }
        
        if (output.contains("null")) {
            fail("The output should not contain the string 'null'.");
        }
    }

    /*
     *
     */
    private SimpleDictionary createSimpleDictionaryWithWords(String... wordsAndTranslations) {
        SimpleDictionary dictionary = new SimpleDictionary();
        for (int i = 0; i < wordsAndTranslations.length; i += 2) {
            dictionary.add(wordsAndTranslations[i], wordsAndTranslations[i + 1]);
        }
        return dictionary;
    }

    private Object createTextUIWithScannerAndWords(Scanner scanner, String... wordsAndTranslations) {
        Object dictionary = createSimpleDictionaryWithWords(wordsAndTranslations);

        try {
            return ReflectionUtils.invokeConstructor(textUIConstructor, scanner, dictionary);

        } catch (Throwable ex) {
            return null;
        }
    }

    private Object createTextUI(Scanner scanner, Object dictionary) {
        try {
            return ReflectionUtils.invokeConstructor(textUIConstructor, scanner, dictionary);
        } catch (Throwable ex) {
            return null;
        }
    }

    private boolean containsTranslation(SimpleDictionary dictionary, String word, String translation) {
        if (dictionary.translate(word) == null) {
            return false;
        }

        if (dictionary.translate(word).equals(translation)) {
            return true;
        }

        return false;
    }

    private void sanityCheck(String klassName, int n, String m) throws SecurityException {
        Field[] fields = ReflectionUtils.findClass(klassName).getDeclaredFields();

        for (Field field : fields) {
            assertFalse("you don't need \"static variables\", remove from the class " + klassName + " the variable " + field(field.toString(), klassName), field.toString().contains("static") && !field.toString().contains("final"));
            assertTrue("the visibility of all the object variables should be private, but the class " + klassName + " contained: " + field(field.toString(), klassName), field.toString().contains("private"));
        }

        if (fields.length > 1) {
            int var = 0;
            for (Field field : fields) {
                if (!field.toString().contains("final")) {
                    var++;
                }
            }
            assertTrue("the class " + klassName + " only needs " + m + ", remove the unnecessary variables", var <= n);
        }
    }

    private String field(String toString, String klassName) {
        return toString.replace(klassName + ".", "").replace("java.lang.", "").replace("java.util.", "");
    }
}
