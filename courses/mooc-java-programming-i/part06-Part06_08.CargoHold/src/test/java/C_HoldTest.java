
import fi.helsinki.cs.tmc.edutestutils.MockInOut;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class C_HoldTest<_Item, _Suitcase, _Hold> {

    private Class itemClass;
    private Constructor itemConstructor;
    private Method itemToStringMethod;
    private Class suitcaseClass;
    private Constructor suitcaseConstructor;
    private Class holdClass;
    private Constructor holdConstructor;
    String klassName = "Hold";
    Reflex.ClassRef<_Hold> _HoldRef;
    Reflex.ClassRef<_Suitcase> _SuitcaseRef;
    Reflex.ClassRef<_Item> _ItemRef;

    @Before
    public void setup() {
        _SuitcaseRef = Reflex.reflect("Suitcase");
        _ItemRef = Reflex.reflect("Item");
        _HoldRef = Reflex.reflect("Hold");

        try {
            itemClass = ReflectionUtils.findClass("Item");
            itemConstructor = ReflectionUtils.requireConstructor(itemClass, String.class, int.class);
            itemToStringMethod = ReflectionUtils.requireMethod(itemClass, "toString");

            suitcaseClass = ReflectionUtils.findClass("Suitcase");
            suitcaseConstructor = ReflectionUtils.requireConstructor(suitcaseClass, int.class);

            holdClass = ReflectionUtils.findClass("Hold");
            holdConstructor = ReflectionUtils.requireConstructor(holdClass, int.class);
        } catch (Throwable t) {
        }
    }

    @Test
    @Points("06-08.6")
    public void classIsPublic() {
        assertTrue("The class " + klassName + " must be public, declared \npublic class " + klassName + " {...\n}", _SuitcaseRef.isPublic());
    }

    @Test
    @Points("06-08.6")
    public void noUnnecessaryVariables() {
        sanitaryCheck(klassName, 3, "Maximum weight, list of suitcases and their weights.");
    }

    @Test
    @Points("06-08.6")
    public void hasConstructor() throws Throwable {
        Reflex.MethodRef1<_Hold, _Hold, Integer> ctor = _HoldRef.constructor().taking(int.class).withNiceError();
        assertTrue("The class " + klassName + " must have a public constructor: public " + klassName + "(int maximumWeight)", ctor.isPublic());
        String v = "Error caused by:new Hold(10);";
        ctor.withNiceError(v).invoke(10);
    }

    public _Suitcase createSuitcase(int weight) throws Throwable {
        return _SuitcaseRef.constructor().taking(int.class).withNiceError().invoke(weight);
    }

    public _Item createItem(String name, int weight) throws Throwable {
        return _ItemRef.constructor().taking(String.class, int.class).withNiceError().invoke(name, weight);
    }

    public _Hold createHold(int maxWeight) throws Throwable {
        return _HoldRef.constructor().taking(int.class).withNiceError().invoke(maxWeight);
    }

    @Test
    @Points("06-08.6")
    public void holdClassAddSuitcaseMethod() throws Throwable {
        _Suitcase bag = createSuitcase(10);
        _Hold hold = createHold(100);

        String v = "\n"
                + "Suitcase m = new Suitcase(10);\n"
                + "Hold r = new Hold(100;\n)"
                + "r.addSuitcase(m);";

        assertTrue("Class Hold must have method public void addSuitcase(Suitcase suitcase)", _HoldRef.method(hold, "addSuitcase").returningVoid().taking(_SuitcaseRef.cls()).withNiceError(v).isPublic());

        _HoldRef.method(hold, "addSuitcase").returningVoid().taking(_SuitcaseRef.cls()).withNiceError(v).invoke(bag);
    }

    @Test
    @Points("06-08.6")
    public void addWhenHoldIsFull() {
        try {
            Object hold = createHold(20);
            Object bag1 = createSuitcase(10);
            addItem(bag1, createItem("Pig", 7));
            addItem(bag1, createItem("Dog", 2));

            addSuitcase(hold, bag1);

            Object bag2 = createSuitcase(10);
            addItem(bag2, createItem("Chicken", 5));
            addItem(bag2, createItem("Fox", 3));

            addSuitcase(hold, bag2);

            Object bag3 = createSuitcase(10);
            addItem(bag3, createItem("Cat", 5));
            addItem(bag3, createItem("Hat", 3));

            addSuitcase(hold, bag3);

            Object bags = objectVariableList(holdClass, hold);
            if (bags == null) {
                fail("Does the class Hold have a object variable list for storing the suitcases?");
            }

            List<Object> bagsInHold = (List<Object>) bags;

            if (!bagsInHold.contains(bag2)) {
                fail("Are the suitcases added to an object variable list?");
            }

            if (bagsInHold.contains(bag3)) {
                fail("Make sure a hold does not add more suitcases than its maximum weight allows.");
            }

        } catch (Throwable t) {
            junit.framework.Assert.fail(t.getMessage());
        }
    }

    @Test
    @Points("06-08.6")
    public void addUpUntilMaximumWeight() {
        try {
            Object hold = createHold(20);
            Object bag = createSuitcase(20);
            addItem(bag, createItem("Brick", 20));
            addSuitcase(hold, bag);

            Object bags = objectVariableList(holdClass, hold);
            if (bags == null) {
                fail("Make sure that the class Hold contains a list for storing all the suitcases.");
            }

            List<Object> bagsInHold = (List<Object>) bags;

            if (!bagsInHold.contains(bag)) {
                fail("Make sure that new bags can be added to a hold up until the maximum weight");
            }
        } catch (Throwable t) {
            junit.framework.Assert.fail(t.getMessage());
        }
    }

    @Test
    @Points("06-08.6")
    public void checkOutputIsCorrect() {
        try {
            Object hold = createHold(128);

            Object bag1 = createSuitcase(10);
            addItem(bag1, createItem("Piglet", 7));
            addItem(bag1, createItem("Owl", 2));

            addSuitcase(hold, bag1);

            Object bag2 = createSuitcase(10);
            addItem(bag2, createItem("Chicken", 5));
            addItem(bag2, createItem("Fox", 3));

            addSuitcase(hold, bag2);

            Object bag3 = createSuitcase(10);
            addItem(bag3, createItem("Chicken", 5));
            addItem(bag3, createItem("Fox", 3));

            addSuitcase(hold, bag3);

            Method toString = ReflectionUtils.requireMethod(ReflectionUtils.findClass("Hold"), "toString");
            String output = ReflectionUtils.invokeMethod(String.class, toString, hold);

            if (!contains(output, "3", "suitcases", "25", "kg")) {
                throw new Exception();
            }

        } catch (Throwable t) {
            junit.framework.Assert.fail("Make sure, that for a Hold containing three suitcases, the output of toString() is:  \"3 suitcases (<weight> kg)\"");
        }
    }

    @Test
    @Points("06-08.7")
    public void holdPrintItemsMethod() throws Throwable {

        _Suitcase bag = createSuitcase(10);
        _Hold hold = createHold(100);

        String v = "\n"
                + "Hold r = new Hold(100;\n)"
                + "r.printItems();";

        assertTrue("The class Hold must have a method metodi public void printItems()",
                _HoldRef.method(hold, "printItems").returningVoid().takingNoParams().withNiceError(v).isPublic());

        _HoldRef.method(hold, "printItems").returningVoid().takingNoParams().withNiceError(v).invoke();

    }

    @Test
    @Points("06-08.7")
    public void printItemsOutputTest() {
        MockInOut io = new MockInOut("");

        try {
            Object hold = createHold(128);

            Object createSuitcase = createSuitcase(10);
            addItem(createSuitcase, createItem("Pig", 7));
            addItem(createSuitcase, createItem("Wolf", 2));

            addSuitcase(hold, createSuitcase);

            createSuitcase = createSuitcase(10);
            addItem(createSuitcase, createItem("Chicken", 5));
            addItem(createSuitcase, createItem("Fox", 3));

            addSuitcase(hold, createSuitcase);

            createSuitcase = createSuitcase(10);
            addItem(createSuitcase, createItem("Cat", 5));
            addItem(createSuitcase, createItem("Dog", 3));

            addSuitcase(hold, createSuitcase);

            Method m = ReflectionUtils.requireMethod(ReflectionUtils.findClass("Hold"), "printItems");
            ReflectionUtils.invokeMethod(void.class, m, hold);

            if (!contains(io.getOutput(), "Pig", "Wolf", "Chicken", "Fox", "Cat", "Dog")) {
                throw new Exception();
            }

        } catch (Throwable t) {
            junit.framework.Assert.fail("Make sure that the printItems() method prints the contents of each suitcase in a hold");
        }
    }

    private void addSuitcase(Object hold, Object suitcase) {
        try {
            Method addSuitcaseMethod = ReflectionUtils.requireMethod(ReflectionUtils.findClass("Hold"), "addSuitcase", ReflectionUtils.findClass("Suitcase"));
            ReflectionUtils.invokeMethod(void.class, addSuitcaseMethod, hold, suitcase);
        } catch (Throwable ex) {
        }
    }

    private void addItem(Object suitcase, Object item) {
        try {
            Method addItemMethod = ReflectionUtils.requireMethod(ReflectionUtils.findClass("Suitcase"), "addItem", ReflectionUtils.findClass("Item"));
            ReflectionUtils.invokeMethod(void.class, addItemMethod, suitcase, item);
        } catch (Throwable ex) {
        }
    }

    private Object makeHold(int capacity) {
        try {

            return ReflectionUtils.invokeConstructor(holdConstructor, capacity);
        } catch (Throwable ex) {
            return null;
        }
    }

    private Object makeSuitcase(int capacity) {
        try {
            return ReflectionUtils.invokeConstructor(suitcaseConstructor, capacity);
        } catch (Throwable ex) {
            return null;
        }
    }

    private Object makeItem(String name, int weight) {
        try {
            return ReflectionUtils.invokeConstructor(itemConstructor, name, weight);
        } catch (Throwable ex) {
            return null;
        }
    }

    private boolean contains(String returnValue, String... expectedValues) {
        for (String value : expectedValues) {
            if (!returnValue.contains(value)) {
                return false;
            }
        }

        return true;
    }

    private Object objectVariableList(Class clazz, Object container) {
        for (Field f : clazz.getDeclaredFields()) {
            if (f.getType().equals(List.class)) {
                f.setAccessible(true);
                try {
                    return f.get(container);
                } catch (IllegalArgumentException ex) {
                    Logger.getLogger(C_HoldTest.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(C_HoldTest.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            if (f.getType().equals(ArrayList.class)) {
                f.setAccessible(true);
                try {
                    return f.get(container);
                } catch (IllegalArgumentException ex) {
                    Logger.getLogger(C_HoldTest.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(C_HoldTest.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            if (f.getType().equals(LinkedList.class)) {
                f.setAccessible(true);
                try {
                    return f.get(container);
                } catch (IllegalArgumentException ex) {
                    Logger.getLogger(C_HoldTest.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(C_HoldTest.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        return null;
    }

    private void sanitaryCheck(String klassName, int n, String m) throws SecurityException {
        Field[] fields = ReflectionUtils.findClass(klassName).getDeclaredFields();

        for (Field field : fields) {
            assertFalse("You do not need any \"static variables\", from class " + klassName + " remove " + kentta(field.toString(), klassName), field.toString().contains("static") && !field.toString().contains("final"));
            assertTrue("All object variables of a class must be private, but class " + klassName + " has: " + kentta(field.toString(), klassName), field.toString().contains("private"));
        }

        if (fields.length > 1) {
            int var = 0;
            for (Field field : fields) {
                if (!field.toString().contains("final")) {
                    var++;
                }
            }
            assertTrue("The class" + klassName + "only needs " + m + ", remove unnecessary ones", var <= n);
        }
    }

    private String kentta(String toString, String klassName) {
        return toString.replace(klassName + ".", "").replace("java.lang.", "").replace("java.util.", "");
    }
}
