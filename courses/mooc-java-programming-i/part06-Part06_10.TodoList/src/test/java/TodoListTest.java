
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Field;
import java.util.Scanner;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.Test;

public class TodoListTest {

    Reflex.ClassRef<Object> klass;

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    @Points("06-10.1")
    public void classTodoListExists() {
        Reflex.reflect("TodoList").requirePublic();
    }

    @Test
    @Points("06-10.1")
    public void theClassTodoListHasConstructorTakingNoParameters() {
        Reflex.reflect("TodoList").ctor().takingNoParams().requirePublic();
    }

    @Test
    @Points("06-10.1")
    public void theClassTodoListHasOnlyOneObjectVariable() {
        sanitaryCheck("TodoList", 1, "one object variable");
    }

    @Test
    @Points("06-10.1")
    public void todoListHasRequiredMethods() {
        Reflex.reflect("TodoList").method("add").returningVoid().taking(String.class).requirePublic();
        Reflex.reflect("TodoList").method("print").returningVoid().takingNoParams().requirePublic();
        Reflex.reflect("TodoList").method("remove").returningVoid().taking(int.class).requirePublic();
    }

    @Test
    @Points("06-10.1")
    public void theMethodsOfTodoListWorkCorrectly() throws Throwable {
        String code = "TodoList list = new TodoList();\n"
                + "list.add(\"read the course material\");\n"
                + "list.add(\"watch the latest fool us\");\n"
                + "list.add(\"take it easy\");\n"
                + "list.print();\n"
                + "list.remove(2);\n"
                + "list.print();\n"
                + "list.add(\"buy raisins\");\n"
                + "list.print();\n"
                + "list.remove(1);\n"
                + "list.remove(1);\n"
                + "list.print();";

        Object list = Reflex.reflect("TodoList").ctor().takingNoParams().withNiceError("An error occurred when creating the list. Try the code:\n" + code).invoke();
        Reflex.reflect("TodoList").method("add").returningVoid().taking(String.class).withNiceError("An error occurred while adding. Try the code:\n" + code).invokeOn(list, "read the course material");
        Reflex.reflect("TodoList").method("add").returningVoid().taking(String.class).withNiceError("An error occurred while adding. Try the code:\n" + code).invokeOn(list, "watch the latest fool us");
        Reflex.reflect("TodoList").method("add").returningVoid().taking(String.class).withNiceError("An error occurred while adding. Try the code:\n" + code).invokeOn(list, "take it easy");

        Reflex.reflect("TodoList").method("print").returningVoid().takingNoParams().withNiceError("An error occurred while printing. Try the code:\n" + code).invokeOn(list);

        String out = io.getSysOut();
        theOutpuContains(out, "1: read the course material", code);
        theOutpuContains(out, "2: watch the latest fool us", code);
        theOutpuContains(out, "3: take it easy", code);

        assertFalse("Only use the output from the program. Now the program printed the string\n2: take it easy\nin a place where it wasn't expected.", out.contains("2: take it easy"));

        Reflex.reflect("TodoList").method("remove").returningVoid().taking(int.class).withNiceError("An error occurred while removing. Try the code:\n" + code).invokeOn(list, 2);

        Reflex.reflect("TodoList").method("print").returningVoid().takingNoParams().withNiceError("An error occurred while printing. Try the code:\n" + code).invokeOn(list);

        String out2 = io.getSysOut().replace(out, "");

        theOutpuContains(out2, "1: read the course material", code);
        theOutpuContains(out2, "2: take it easy", code);
        assertFalse("Only use the output from the program. Now the program printed the string\n3: buy raisins\nin a place where it wasn't expected.", out2.contains("3: buy raisins"));

        Reflex.reflect("TodoList").method("add").returningVoid().taking(String.class).withNiceError("An error occurred while adding. Try the code:\n" + code).invokeOn(list, "buy raisins");
        Reflex.reflect("TodoList").method("print").returningVoid().takingNoParams().withNiceError("An error occurred while printing. Try the code:\n" + code).invokeOn(list);

        String out3 = io.getSysOut().replace(out, "");
        out3 = out3.replace(out2, "");

        theOutpuContains(out3, "3: buy raisins", code);
        assertFalse("Only use the output from the program. Now the program printed the string\n1: buy raisins\nin a place where it wasn't expected.", out3.contains("1: buy raisins"));
        assertFalse("Only use the output from the program. Now the program printed the string\n2: buy raisins\nin a place where it wasn't expected.", out3.contains("2: buy raisins"));

        Reflex.reflect("TodoList").method("remove").returningVoid().taking(int.class).withNiceError("An error occurred while removing. Try the code:\n" + code).invokeOn(list, 1);
        Reflex.reflect("TodoList").method("remove").returningVoid().taking(int.class).withNiceError("An error occurred while removing. Try the code:\n" + code).invokeOn(list, 1);

        Reflex.reflect("TodoList").method("print").returningVoid().takingNoParams().withNiceError("An error occurred while printing. Try the code:\n" + code).invokeOn(list);
        String out4 = io.getSysOut().replace(out, "");
        out4 = out4.replace(out2, "");
        out4 = out4.replace(out3, "");

        theOutpuContains(out4, "1: buy raisins", code);
        assertFalse("Only use the output from the program. Now the program printed the string\n2: buy raisins\nin a place where it wasn't expected.", out4.contains("2: buy raisins"));
        assertFalse("Only use the output from the program. Now the program printed the string\n3: buy raisins\nin a place where it wasn't expected.", out4.contains("3: buy raisins"));
    }

    @Test
    @Points("06-10.2")
    public void classUserInterfaceExists() {
        Reflex.reflect("UserInterface").requirePublic();
    }

    @Test
    @Points("06-10.2")
    public void theClassUserInterfaceHasConstructorTakingTwoParameters() {
        Reflex.reflect("UserInterface").ctor().taking(Reflex.reflect("TodoList").cls(), Scanner.class).requirePublic();
    }

    @Test
    @Points("06-10.2")
    public void theClassUserInterfaceHasMethodStart() {
        Reflex.reflect("UserInterface").method("start").returningVoid().takingNoParams().requirePublic();
    }

    @Test
    @Points("06-10.2")
    public void theClassUserInterfaceHasTwoObjectVariables() {
        sanitaryCheck("UserInterface", 2, "the object variables Scanner and TodoList");
    }

    @Test
    @Points("06-10.2")
    public void testMethodStop() throws Throwable {
        String code = "TodoList list = new TodoList();\n"
                + "Scanner scanner = new Scanner(System.in);\n"
                + "\n"
                + "UserInterface ui = new UserInterface(list, scanner);\n"
                + "ui.start();";

        String commands = "stop\n";

        Object list = Reflex.reflect("TodoList").ctor().takingNoParams().withNiceError("An error occurred when creating the list. Try the code:\n" + code).invoke();
        Scanner scanner = new Scanner(commands);
        Object ui = Reflex.reflect("UserInterface").ctor().taking(Reflex.reflect("TodoList").cls(), Scanner.class).withNiceError("An error occurred while creating the user interface. Try the code:\n" + code).invoke(list, scanner);

        Reflex.reflect("UserInterface").method("start").returningVoid().takingNoParams().withNiceError("An error occurred while starting the user interface. Try the code:\n" + code + "\nWith the input:\n" + commands).invokeOn(ui);
    }

    @Test
    @Points("06-10.2")
    public void testCommandAdd() throws Throwable {
        String code = "TodoList list = new TodoList();\n"
                + "Scanner scanner = new Scanner(System.in);\n"
                + "\n"
                + "UserInterface ui = new UserInterface(list, scanner);\n"
                + "ui.start();";

        String commands = "add\nview courses\nadd\nsign up for courses\nstop\n";

        Object list = Reflex.reflect("TodoList").ctor().takingNoParams().withNiceError("An error occurred when creating the list. Try the code:\n" + code).invoke();
        Scanner scanner = new Scanner(commands);
        Object ui = Reflex.reflect("UserInterface").ctor().taking(Reflex.reflect("TodoList").cls(), Scanner.class).withNiceError("An error occurred while creating the user interface. Try the code:\n" + code).invoke(list, scanner);

        Reflex.reflect("UserInterface").method("start").returningVoid().takingNoParams().withNiceError("An error occurred while starting the user interface. Try the code:\n" + code + "\nWith the input:\n" + commands).invokeOn(ui);

        String out = io.getSysOut();
        assertFalse("When the commands are:\n" + commands + "\nAnd the code run is:\n" + code + "\nThe program should not print the added tasks.", out.contains("view courses") || out.contains("sign up for courses"));

        code += "\nlist.print();";

        Reflex.reflect("TodoList").method("print").returningVoid().takingNoParams().withNiceError("An error occurred while printing. Try the code:\n" + code + "\nwith the input:\n" + commands).invokeOn(list);

        out = io.getSysOut().replace("out", "");

        theOutpuContains(out, "1: view courses", code + "\nand commands:\n" + commands);
        theOutpuContains(out, "2: sign up for courses", code + "\nand commands:\n" + commands);
    }

    @Test
    @Points("06-10.2")
    public void testCommandList() throws Throwable {
        String code = "TodoList list = new TodoList();\n"
                + "list.add(\"first\");\n"
                + "list.add(\"second\");\n"
                + "Scanner scanner = new Scanner(System.in);\n"
                + "\n"
                + "UserInterface ui = new UserInterface(list, scanner);\n"
                + "ui.start();";

        String commands = "list\nstop\n";

        Object list = Reflex.reflect("TodoList").ctor().takingNoParams().withNiceError("An error occurred when creating the list. Try the code:\n" + code).invoke();
        Reflex.reflect("TodoList").method("add").returningVoid().taking(String.class).withNiceError("An error occurred while adding to the list. Try the code:\n" + code).invokeOn(list, "first");
        Reflex.reflect("TodoList").method("add").returningVoid().taking(String.class).withNiceError("An error occurred while adding to the list. Try the code:\n" + code).invokeOn(list, "second");

        Scanner scanner = new Scanner(commands);
        Object ui = Reflex.reflect("UserInterface").ctor().taking(Reflex.reflect("TodoList").cls(), Scanner.class).withNiceError("An error occurred while creating the user interface. Try the code:\n" + code).invoke(list, scanner);

        Reflex.reflect("UserInterface").method("start").returningVoid().takingNoParams().withNiceError("An error occurred while starting the user interface. Try the code:\n" + code + "\nWith the input:\n" + commands).invokeOn(ui);

        String out = io.getSysOut();

        theOutpuContains(out, "1: first", code + "\nand the commands are:\n" + commands);
        theOutpuContains(out, "2: second", code + "\nand the commands are:\n" + commands);
    }

    @Test
    @Points("06-10.2")
    public void testCommandRemove() throws Throwable {
        String code = "TodoList list = new TodoList();\n"
                + "list.add(\"one\");\n"
                + "list.add(\"two\");\n"
                + "list.add(\"three\");\n"
                + "Scanner scanner = new Scanner(System.in);\n"
                + "\n"
                + "UserInterface ui = new UserInterface(list, scanner);\n"
                + "ui.start();";

        String commands = "remove\n2\nstop\n";

        Object list = Reflex.reflect("TodoList").ctor().takingNoParams().withNiceError("An error occurred when creating the list. Try the code:\n" + code).invoke();
        Reflex.reflect("TodoList").method("add").returningVoid().taking(String.class).withNiceError("An error occurred while adding to the list. Try the code:\n" + code).invokeOn(list, "one");
        Reflex.reflect("TodoList").method("add").returningVoid().taking(String.class).withNiceError("An error occurred while adding to the list. Try the code:\n" + code).invokeOn(list, "two");
        Reflex.reflect("TodoList").method("add").returningVoid().taking(String.class).withNiceError("An error occurred while adding to the list. Try the code:\n" + code).invokeOn(list, "three");

        Scanner scanner = new Scanner(commands);
        Object ui = Reflex.reflect("UserInterface").ctor().taking(Reflex.reflect("TodoList").cls(), Scanner.class).withNiceError("An error occurred while creating the user interface. Try the code:\n" + code).invoke(list, scanner);

        Reflex.reflect("UserInterface").method("start").returningVoid().takingNoParams().withNiceError("An error occurred while starting the user interface. Try the code:\n" + code + "\nWith the input:\n" + commands).invokeOn(ui);

        code += "\nlist.print();";

        Reflex.reflect("TodoList").method("print").returningVoid().takingNoParams().withNiceError("An error occurred while printing. Try the code:\n" + code + "\nwith the input:\n" + commands).invokeOn(list);

        String out = io.getSysOut();

        theOutpuContains(out, "1: one", code + "\nand the commands are:\n" + commands);
        theOutpuContains(out, "2: three", code + "\nand the commands are:\n" + commands);
    }
    
    @Test
    @Points("06-10.2")
    public void testCommandRemove2() throws Throwable {
        String code = "TodoList list = new TodoList();\n"
                + "list.add(\"one\");\n"
                + "list.add(\"two\");\n"
                + "list.add(\"three\");\n"
                + "Scanner scanner = new Scanner(System.in);\n"
                + "\n"
                + "UserInterface ui = new UserInterface(list, scanner);\n"
                + "ui.start();";

        String commands = "remove\n1\nstop\n";

        Object list = Reflex.reflect("TodoList").ctor().takingNoParams().withNiceError("An error occurred when creating the list. Try the code:\n" + code).invoke();
        Reflex.reflect("TodoList").method("add").returningVoid().taking(String.class).withNiceError("An error occurred while adding to the list. Try the code:\n" + code).invokeOn(list, "one");
        Reflex.reflect("TodoList").method("add").returningVoid().taking(String.class).withNiceError("An error occurred while adding to the list. Try the code:\n" + code).invokeOn(list, "two");
        Reflex.reflect("TodoList").method("add").returningVoid().taking(String.class).withNiceError("An error occurred while adding to the list. Try the code:\n" + code).invokeOn(list, "three");

        Scanner scanner = new Scanner(commands);
        Object ui = Reflex.reflect("UserInterface").ctor().taking(Reflex.reflect("TodoList").cls(), Scanner.class).withNiceError("An error occurred while creating the user interface. Try the code:\n" + code).invoke(list, scanner);

        Reflex.reflect("UserInterface").method("start").returningVoid().takingNoParams().withNiceError("An error occurred while starting the user interface. Try the code:\n" + code + "\nWith the input:\n" + commands).invokeOn(ui);

        code += "\nlist.print();";

        Reflex.reflect("TodoList").method("print").returningVoid().takingNoParams().withNiceError("An error occurred while printing. Try the code:\n" + code + "\nwith the input:\n" + commands).invokeOn(list);

        String out = io.getSysOut();

        theOutpuContains(out, "1: two", code + "\nand the commands are:\n" + commands);
        theOutpuContains(out, "2: three", code + "\nand the commands are:\n" + commands);
    }

    private void theOutpuContains(String output, String string, String code) {
        assertTrue("Expected the output to contain the string:\n"
                + string + "\n"
                + "Try the code: " + code, output.contains(string));
    }

    private void sanitaryCheck(String klassName, int n, String m) throws SecurityException {
        Field[] fields = ReflectionUtils.findClass(klassName).getDeclaredFields();

        for (Field field : fields) {
            assertFalse("you don't need \"static variables\", remove the variable " + fieldName(field.toString(), klassName) + " from the class " + klassName, field.toString().contains("static") && !field.toString().contains("final"));
            assertTrue("the visibility of all object variables for the class should be private, change the following variables in the class  " + klassName + ": " + fieldName(field.toString(), klassName), field.toString().contains("private"));
        }

        if (fields.length > 1) {
            int var = 0;
            for (Field field : fields) {
                if (!field.toString().contains("final")) {
                    var++;
                }
            }
            assertTrue("the class " + klassName + " only needs " + m + ", remove unnecessary variables", var <= n);
        }
    }

    private String fieldName(String toString, String klassName) {
        return toString.replace(klassName + ".", "").replace("java.lang.", "").replace("java.util.", "");
    }
}
