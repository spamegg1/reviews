
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.UUID;
import org.junit.*;
import static org.junit.Assert.*;

public class StackTest {

    @Test
    @Points("06-02.1")
    public void classStackExists() throws Throwable {
        Reflex.reflect("Stack").requirePublic();
        Reflex.reflect("Stack").ctor().takingNoParams().requirePublic();
    }

    @Test
    @Points("06-02.1")
    public void classStackHasObjectVariableOfTypeArrayList() throws Throwable {
        Reflex.reflect("Stack").requirePublic();
        Class clazz = Reflex.reflect("Stack").cls();

        assertEquals("The class Stack should only have one object variable. Now there are: " + clazz.getDeclaredFields().length, 1, clazz.getDeclaredFields().length);
        Field f = clazz.getDeclaredFields()[0];
        assertEquals("The class Stack should have an object of type ArrayList as its object variable. Currently the type of the variable is: ", ArrayList.class, f.getType());
    }

    @Test
    @Points("06-02.1")
    public void methodIsEmptyExists() throws Throwable {
        Reflex.reflect("Stack").method("isEmpty").returning(boolean.class).takingNoParams().requirePublic();
    }

    @Test
    @Points("06-02.1")
    public void methodAddExists() throws Throwable {
        Reflex.reflect("Stack").method("add").returning(void.class).taking(String.class).requirePublic();
    }

    @Test
    @Points("06-02.1")
    public void initiallyStackIsEmptyButAddingStartsFillingIt() throws Throwable {
        Object stack = Reflex.reflect("Stack").ctor().takingNoParams().invoke();
        String code = "Stack p = new Stack();\nSystem.out.println(p.isEmpty());";

        boolean isEmpty = true;
        try {
            isEmpty = Reflex.reflect("Stack").method("isEmpty").returning(boolean.class).takingNoParams().invokeOn(stack);
        } catch (Throwable t) {
            fail("An error occurred when executing the following code:\n" + code);
        }

        assertTrue("Initially, the stack should be empty. Try the code:\n" + code, isEmpty);

        code += "\np.add(\"Hello, world!\");";
        try {
            Reflex.reflect("Stack").method("add").returning(void.class).taking(String.class).invokeOn(stack, "Hello, world!");
        } catch (Throwable t) {
            fail("An error occurred when executing the following code:\n" + code);
        }

        code += "\np.add(\"Hello, world!\");\nSystem.out.println(p.isEmpty());";
        isEmpty = true;
        try {
            isEmpty = Reflex.reflect("Stack").method("isEmpty").returning(boolean.class).takingNoParams().invokeOn(stack);
        } catch (Throwable t) {
            fail("An error occurred when executing the following code:\n" + code);
        }

        assertFalse("When adding a value to the stack, it should no longer be empty. Try the code:\n" + code, isEmpty);
    }

    @Test
    @Points("06-02.1")
    public void methodValuesExists() throws Throwable {
        Reflex.reflect("Stack").method("values").returning(ArrayList.class).takingNoParams().requirePublic();
    }

    @Test
    @Points("06-02.1")
    public void valuesContainsTheAddedValues() throws Throwable {
        Object stack = Reflex.reflect("Stack").ctor().takingNoParams().invoke();

        String code = "Stack p = new Stack();\nSystem.out.println(p.values());";
        ArrayList<String> values = null;
        try {
            values = Reflex.reflect("Stack").method("values").returning(ArrayList.class).takingNoParams().invokeOn(stack);
        } catch (Throwable t) {
            fail("An error occurred when executing the following code:\n" + code);
        }

        assertNotNull("The values method should not return null. Try out your program using the following code:\n" + code, values);
        assertEquals("When no values have been added to the stack, there should be no values on the list returned by the values method.\nTry out your program using the following code:\n" + code, 0, values.size());

        String toAdd = UUID.randomUUID().toString().substring(0, 6);
        code += "\np.add(\"" + toAdd + "\");";
        try {
            Reflex.reflect("Stack").method("add").returning(void.class).taking(String.class).invokeOn(stack, toAdd);
        } catch (Throwable t) {
            fail("An error occurred when executing the following code:\n" + code);
        }

        values = null;
        code += "\nSystem.out.println(p.values());";
        try {
            values = Reflex.reflect("Stack").method("values").returning(ArrayList.class).takingNoParams().invokeOn(stack);
        } catch (Throwable t) {
            fail("An error occurred when executing the following code:\n" + code);
        }

        assertNotNull("The values method should not return null. Try out your program using the following code:\n" + code, values);
        assertEquals("When one value has been added to the stack, the list returned by the values method should contain one value.\nTest your program with this code:\n" + code, 1, values.size());

        assertTrue("The value that was added should be found on the list returned by the values method. Try out your program using the following code:\n" + code, values.contains(toAdd));
    }

    @Test
    @Points("06-02.2")
    public void methodTakeExists() throws Throwable {
        Reflex.reflect("Stack").method("take").returning(String.class).takingNoParams().requirePublic();
    }

    @Test
    @Points("06-02.2")
    public void methodTakeReturnsTheLastValueAddedToTheStack() throws Throwable {
        Object stack = Reflex.reflect("Stack").ctor().takingNoParams().invoke();

        String code = "Stack p = new Stack();";

        String toAdd1 = UUID.randomUUID().toString().substring(0, 6);
        code += "\np.add(\"" + toAdd1 + "\");";

        String toAdd2 = UUID.randomUUID().toString().substring(0, 6);
        code += "\np.add(\"" + toAdd2 + "\");\nSystem.out.println(p.take());";

        String taken = null;
        try {
            Reflex.reflect("Stack").method("add").returning(void.class).taking(String.class).invokeOn(stack, toAdd1);
            Reflex.reflect("Stack").method("add").returning(void.class).taking(String.class).invokeOn(stack, toAdd2);
            taken = Reflex.reflect("Stack").method("take").returning(String.class).takingNoParams().invokeOn(stack);
        } catch (Throwable t) {
            fail("An error occurred during execution of the program. Try out your program using the following code: " + code);
        }
        
        assertEquals("After first adding the string \"" + toAdd1 + "\" to the stack and then adding the string \"" + toAdd2 + "\",\nthe method call should return the string \"" + toAdd2 + "\".\nTry out your program using the following code: " + code, toAdd2, taken);
    }

    @Test
    @Points("06-02.2")
    public void takeReturnsTheLastValueAddedToTheStack() throws Throwable {
        Object stack = Reflex.reflect("Stack").ctor().takingNoParams().invoke();

        String code = "Stack p = new Stack();";

        String toAdd1 = UUID.randomUUID().toString().substring(0, 6);
        code += "\np.add(\"" + toAdd1 + "\");";

        String toAdd2 = UUID.randomUUID().toString().substring(0, 6);
        code += "\np.add(\"" + toAdd2 + "\");\nSystem.out.println(p.take());";

        String taken = null;
        try {
            Reflex.reflect("Stack").method("add").returning(void.class).taking(String.class).invokeOn(stack, toAdd1);
            Reflex.reflect("Stack").method("add").returning(void.class).taking(String.class).invokeOn(stack, toAdd2);
            taken = Reflex.reflect("Stack").method("take").returning(String.class).takingNoParams().invokeOn(stack);
        } catch (Throwable t) {
            fail("An error occurred during execution of the program. Try out your program using the following code: " + code);
        }
        
        assertEquals("After first adding the string \"" + toAdd1 + "\" to the stack and then adding the string \"" + toAdd2 + "\",\nthe method call should return the string \"" + toAdd2 + "\".\nTry out your program using the following code: " + code, toAdd2, taken);
    }

    @Test
    @Points("06-02.2")
    public void finallyTheStackIsEmpty() throws Throwable {
        Object stack = Reflex.reflect("Stack").ctor().takingNoParams().invoke();

        String code = "Stack p = new Stack();";

        String toAdd1 = UUID.randomUUID().toString().substring(0, 6);
        code += "\np.add(\"" + toAdd1 + "\");";

        String toAdd2 = UUID.randomUUID().toString().substring(0, 6);
        code += "\np.add(\"" + toAdd2 + "\");";

        String toAdd3 = UUID.randomUUID().toString().substring(0, 6);
        code += "\np.add(\"" + toAdd3 + "\");\nSystem.out.println(p.take());\nSystem.out.println(p.take());\nSystem.out.println(p.take());";

        String taken1 = null;
        String taken2 = null;
        String taken3 = null;
        try {
            Reflex.reflect("Stack").method("add").returning(void.class).taking(String.class).invokeOn(stack, toAdd1);
            Reflex.reflect("Stack").method("add").returning(void.class).taking(String.class).invokeOn(stack, toAdd2);
            Reflex.reflect("Stack").method("add").returning(void.class).taking(String.class).invokeOn(stack, toAdd3);
            taken1 = Reflex.reflect("Stack").method("take").returning(String.class).takingNoParams().invokeOn(stack);
            taken2 = Reflex.reflect("Stack").method("take").returning(String.class).takingNoParams().invokeOn(stack);
            taken3 = Reflex.reflect("Stack").method("take").returning(String.class).takingNoParams().invokeOn(stack);
        } catch (Throwable t) {
            fail("An error occurred during execution of the program. Try out your program using the following code:\n" + code);
        }
        
        assertTrue("When adding the strings \"" + toAdd1 + "\", \"" + toAdd2 + "\", and \"" + toAdd3 + "\" to the stack, the calls of the take method\nshould receive the values \"" + toAdd3 + "\", \"" + toAdd2 + "\", and \"" + toAdd1 + "\".\nTry out your program using the following code: " + code, toAdd1.equals(taken3) && toAdd2.equals(taken2) && toAdd3.equals(taken1));
        code += "\nSystem.out.println(p.isEmpty())";
        boolean isEmpty = true;
        try {
            isEmpty = Reflex.reflect("Stack").method("isEmpty").returning(boolean.class).takingNoParams().invokeOn(stack);
        } catch (Throwable t) {
            fail("An error occurred when executing the following code:\n" + code);
        } 
        
        assertTrue("When all strings have been taken from the stack it should be empty. Try out your program using the following code:\n" + code, isEmpty);
    }

}
