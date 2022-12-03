
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
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class B_SuitcaseTest<_Item, _Suitcase> {

    private Class itemClass;
    private Constructor itemConstructor;
    private Class suitcaseClass;
    private Constructor suitcaseConstructor;
    String klassName = "Suitcase";
    Reflex.ClassRef<_Suitcase> _SuitcaseRef;
    Reflex.ClassRef<_Item> _ItemRef;

    @Before
    public void setup() {
        _SuitcaseRef = Reflex.reflect("Suitcase");
        _ItemRef = Reflex.reflect("Item");

        try {
            itemClass = ReflectionUtils.findClass("Item");
            itemConstructor = ReflectionUtils.requireConstructor(itemClass, String.class, int.class);

            suitcaseClass = ReflectionUtils.findClass("Suitcase");
            suitcaseConstructor = ReflectionUtils.requireConstructor(suitcaseClass, int.class);

        } catch (Throwable t) {
        }
    }

    @Test
    @Points("06-08.2")
    public void classIsPublic() {
        assertTrue("Class " + klassName + " must be public, declared\npublic class " + klassName + " {...\n}", _SuitcaseRef.isPublic());
    }

    @Test
    @Points("06-08.2")
    public void noExtraVariables() {
        sanitaryCheck(klassName, 3, "variables for maximum weight and for storing all items and their weights");
    }

    @Test
    @Points("06-08.2")
    public void testSuitcaseConstructor() throws Throwable {
        Reflex.MethodRef1<_Suitcase, _Suitcase, Integer> ctor = _SuitcaseRef.constructor().taking(int.class).withNiceError();
        assertTrue("Declare a public constructor for class" + klassName + " : public " + klassName + "(int maximumWeight)", ctor.isPublic());
        String v = "Error caused by  new Suitcase( 10);";
        ctor.withNiceError(v).invoke(10);
    }

    public _Suitcase createSuitcase(int weight) throws Throwable {
        return _SuitcaseRef.constructor().taking(int.class).withNiceError().invoke(weight);
    }

    public _Item createItem(String name, int weight) throws Throwable {
        return _ItemRef.constructor().taking(String.class, int.class).withNiceError().invoke(name, weight);
    }

    @Test
    @Points("06-08.2")
    public void addItemMethod() throws Throwable {
        _Item item = createItem("book", 1);
        _Suitcase suitcase = createSuitcase(10);

        String v = "\nItem t = new Item(\"book\",1); \n"
                + "Suitcase m = new Suitcase(10);\n"
                + "m.addItem(t);";

        assertTrue("Suitcase classmust have method public void addItem(Item item)", _SuitcaseRef.method(suitcase, "addItem").returningVoid().taking(_ItemRef.cls()).withNiceError(v).isPublic());

        _SuitcaseRef.method(suitcase, "addItem").returningVoid().taking(_ItemRef.cls()).withNiceError(v).invoke(item);
    }

    @Test
    @Points("06-08.2")
    public void testAddItemWorks() {
        try {
            Object suitcase = ReflectionUtils.invokeConstructor(suitcaseConstructor, 64);

            Method addItemMethod = ReflectionUtils.requireMethod(suitcaseClass, "addItem", ReflectionUtils.findClass("Item"));

            Object brick = ReflectionUtils.invokeConstructor(itemConstructor, "Brick", 8);
            Object mortar = ReflectionUtils.invokeConstructor(itemConstructor, "Mortar", 8);
            ReflectionUtils.invokeMethod(void.class, addItemMethod, suitcase, brick);
            ReflectionUtils.invokeMethod(void.class, addItemMethod, suitcase, mortar);

            List<Object> items = (List<Object>) objectVariableList(suitcaseClass, suitcase);
            if (!items.contains(brick)) {
                fail("The addItem method did not add the item to the items list of a suitcase");
            }

            if (!items.contains(mortar)) {
                fail("The addItem method did not add the item to the items list of a suitcase");
            }

        } catch (Throwable t) {
            junit.framework.Assert.fail("Check that the addItem method in the suitcase class adds items to the suitcase. Make sure that the class has an ArrayList for storing the items.");
        }
    }

    @Test
    @Points("06-08.2")
    public void checkAddItemWorksWhenTooMuchWeight() {
        try {
            Object suitcase = ReflectionUtils.invokeConstructor(suitcaseConstructor, 20);

            Method addItemMethod = ReflectionUtils.requireMethod(suitcaseClass, "addItem", ReflectionUtils.findClass("Item"));

            Object brick = ReflectionUtils.invokeConstructor(itemConstructor, "Brick", 8);
            Object mortar = ReflectionUtils.invokeConstructor(itemConstructor, "Mortar", 8);
            Object kitchenSink = ReflectionUtils.invokeConstructor(itemConstructor, "Kitchen Sink", 8);
            ReflectionUtils.invokeMethod(void.class, addItemMethod, suitcase, brick);
            ReflectionUtils.invokeMethod(void.class, addItemMethod, suitcase, mortar);
            ReflectionUtils.invokeMethod(void.class, addItemMethod, suitcase, kitchenSink);

            List<Object> items = (List<Object>) objectVariableList(suitcaseClass, suitcase);
            if (items.contains(kitchenSink)) {
                fail("asd");
            }

        } catch (Throwable t) {
            fail("Check that the addItem method of the Suitcase class does not add a new item to the suitcase if the suitcase is full.");
        }
    }

    @Test
    @Points("06-08.2")
    public void checkAddItemWhenExactlyAtMaxWeight() {
        try {
            Object suitcase = ReflectionUtils.invokeConstructor(suitcaseConstructor, 20);

            Method addItemMethod = ReflectionUtils.requireMethod(suitcaseClass, "addItem", ReflectionUtils.findClass("Item"));

            Object brick = ReflectionUtils.invokeConstructor(itemConstructor, "Brick", 20);
            ReflectionUtils.invokeMethod(void.class, addItemMethod, suitcase, brick);

            List<Object> items = (List<Object>) objectVariableList(suitcaseClass, suitcase);
            if (!items.contains(brick)) {
                fail("asd");
            }

        } catch (Throwable t) {
            fail("Check, that the addItem method of the Suitcase class accepts an item when, after adding the new item, the weight of the suitcase will be exactly the maximum weight. ");
        }
    }

    @Test
    @Points("06-08.2")
    public void suitcaseToString() {
        String returnValue = "";
        try {
            Object newSuitcase = ReflectionUtils.invokeConstructor(suitcaseConstructor, 20);
            Method suitcaseToStringMethod = ReflectionUtils.requireMethod(suitcaseClass, "toString");


            Method addItemMethod = ReflectionUtils.requireMethod(suitcaseClass, "addItem", ReflectionUtils.findClass("Item"));
            ReflectionUtils.invokeMethod(void.class, addItemMethod, newSuitcase, ReflectionUtils.invokeConstructor(itemConstructor, "New item", 8));
            ReflectionUtils.invokeMethod(void.class, addItemMethod, newSuitcase, ReflectionUtils.invokeConstructor(itemConstructor, "New item", 8));
            ReflectionUtils.invokeMethod(void.class, addItemMethod, newSuitcase, ReflectionUtils.invokeConstructor(itemConstructor, "New item", 8));

            returnValue = ReflectionUtils.invokeMethod(String.class, suitcaseToStringMethod, newSuitcase);

            if (!contains(returnValue, "2", "items", "16", "kg")) {
                fail("");
            }

        } catch (Throwable t) {
            junit.framework.Assert.fail("Check that the toString method of the Suitcase class prints the items in the suitcase. (for example: \"3 items (15 kg)\".\n"
                    + "Adding three 8 kg items to a suitcase with maximum weight of 20kg: " + returnValue);
        }
    }

    @Test
    @Points("06-08.3")
    public void suitcaseLanguageFormatting() {
        try {
            Object newSuitcase = ReflectionUtils.invokeConstructor(suitcaseConstructor, 20);
            Method suitcaseToStringMethod = ReflectionUtils.requireMethod(suitcaseClass, "toString");
            Method addItemMethod = ReflectionUtils.requireMethod(suitcaseClass, "addItem", ReflectionUtils.findClass("Item"));


            String returnValue = ReflectionUtils.invokeMethod(String.class, suitcaseToStringMethod, newSuitcase);
            if (!contains(returnValue, "no", "items", "0", "kg") && !contains(returnValue, "empty", "0", "kg")) {
                fail("Check that toString method of an empty suitcase prints \"no items (0 kg)\"");
            }

            ReflectionUtils.invokeMethod(void.class, addItemMethod, newSuitcase, ReflectionUtils.invokeConstructor(itemConstructor, "New item", 8));

            returnValue = ReflectionUtils.invokeMethod(String.class, suitcaseToStringMethod, newSuitcase);
            if (!contains(returnValue, "item", "8", "kg") || contains(returnValue, "items")) {
                fail("The toString method of a suitcase containing 1 item should print \"1 item (<weight> kg)\"");
            }

            ReflectionUtils.invokeMethod(void.class, addItemMethod, newSuitcase, ReflectionUtils.invokeConstructor(itemConstructor, "New item", 8));
            returnValue = ReflectionUtils.invokeMethod(String.class, suitcaseToStringMethod, newSuitcase);

            if (!contains(returnValue, "2", "items", "16", "kg")) {
                fail("The toString method of a suitcase containing 2 items should be  \"2 items (<weight> kg)\".");
            }

        } catch (Throwable t) {
            junit.framework.Assert.fail(t.getMessage());
        }
    }

    @Test
    @Points("06-08.4")
    public void printItemsMethod() throws Throwable {
        _Suitcase suitcase = createSuitcase(10);

        String v = ""
                + "Suitcase m = new Suitcase(10);\n"
                + "m.printItems();";

        assertTrue("The Suitcase class must have method public void printItems()", _SuitcaseRef.method(suitcase, "printItems").returningVoid().takingNoParams().withNiceError(v).isPublic());

        _SuitcaseRef.method(suitcase, "printItems").returningVoid().takingNoParams().withNiceError(v).invoke();
    }

    @Test
    @Points("06-08.4")
    public void printItemsHasCorrectOutput() {
        MockInOut io = new MockInOut("");

        try {
            Object suitcase = ReflectionUtils.invokeConstructor(suitcaseConstructor, 20);


            Class item = ReflectionUtils.findClass("Item");
            Constructor itemConst = ReflectionUtils.requireConstructor(item, String.class, int.class);

            Object carrot = ReflectionUtils.invokeConstructor(itemConst, "Carrot", 2);
            Object stick = ReflectionUtils.invokeConstructor(itemConst, "Stick", 4);
            Object cake = ReflectionUtils.invokeConstructor(itemConst, "Cake", 8);

            Method addItemMethod = ReflectionUtils.requireMethod(suitcaseClass, "addItem", ReflectionUtils.findClass("Item"));
            ReflectionUtils.invokeMethod(void.class, addItemMethod, suitcase, carrot);
            ReflectionUtils.invokeMethod(void.class, addItemMethod, suitcase, stick);
            ReflectionUtils.invokeMethod(void.class, addItemMethod, suitcase, cake);


            Method totalWeightMethod = ReflectionUtils.requireMethod(suitcaseClass, "printItems");
            ReflectionUtils.invokeMethod(void.class, totalWeightMethod, suitcase);

            if (!contains(io.getOutput(), "Carrot", "Stick", "Cake", "2", "4", "8", "kg")) {
                throw new Exception();
            }

            if (io.getOutput().split("\n").length < 2) {
                throw new Exception();
            }

        } catch (Throwable t) {
            junit.framework.Assert.fail("Check that the printItems method prints items on the screen one by one."
                    + "\nWhen adding three items to a suitcase and calling printItems() the output was:\n" + io.getOutput());
        }
    }

    @Test
    @Points("06-08.4")
    public void suitcaseTotalWeightMethod() throws Throwable {
        _Suitcase suitcase = createSuitcase(10);

        String v = ""
                + "Suitcase m = new Suitcase(10);\n"
                + "m.totalWeight();";

        assertTrue("The Suitcase class must have a mathod public int totalWeight()", _SuitcaseRef.method(suitcase, "totalWeight").returning(int.class).takingNoParams().withNiceError(v).isPublic());

        _SuitcaseRef.method(suitcase, "totalWeight").returning(int.class).takingNoParams().withNiceError(v).invoke();
    }

    @Test
    @Points("06-08.4")
    public void totalWeightHasTheCorrectOutput() {
        try {
            Object suitcase = ReflectionUtils.invokeConstructor(suitcaseConstructor, 20);

            Method addItemMethod = ReflectionUtils.requireMethod(suitcaseClass, "addItem", ReflectionUtils.findClass("Item"));
            ReflectionUtils.invokeMethod(void.class, addItemMethod, suitcase, ReflectionUtils.invokeConstructor(itemConstructor, "T1", 8));
            ReflectionUtils.invokeMethod(void.class, addItemMethod, suitcase, ReflectionUtils.invokeConstructor(itemConstructor, "T2", 6));
            ReflectionUtils.invokeMethod(void.class, addItemMethod, suitcase, ReflectionUtils.invokeConstructor(itemConstructor, "T3", 4));
            ReflectionUtils.invokeMethod(void.class, addItemMethod, suitcase, ReflectionUtils.invokeConstructor(itemConstructor, "T3", 4));

            Method totalWeightMethod = ReflectionUtils.requireMethod(suitcaseClass, "totalWeight");

            int returnValue = ReflectionUtils.invokeMethod(int.class, totalWeightMethod, suitcase);
            if (returnValue != 18) {
                throw new Exception();
            }
        } catch (Throwable t) {
            junit.framework.Assert.fail("Check that the totalWeight method calculates and returns the sum of the weights of all items in the suitcase.");
        }
    }

    @Test
    @Points("06-08.5")
    public void hasHeaviestItemMethod() throws Throwable {
        _Suitcase suitcase = createSuitcase(10);


        String v = "\nFailing code:\n"
                + "Suitcase m = new Suitcase(10); "
                + "m.heaviestItem();";

        assertTrue("The Suitcase class must have a method public Item heaviestItem()", _SuitcaseRef.method(suitcase, "heaviestItem").returning(_ItemRef.cls()).takingNoParams().withNiceError(v).isPublic());

        _SuitcaseRef.method(suitcase, "heaviestItem").returning(_ItemRef.cls()).takingNoParams().withNiceError(v).invoke();

    }

    @Test
    @Points("06-08.5")
    public void heaviestItemFindsTheHeaviestItem() {
        Object ret = null;
        try {
            Object suitcase = ReflectionUtils.invokeConstructor(suitcaseConstructor, 20);
            Constructor itemConstructor = ReflectionUtils.requireConstructor(itemClass, String.class, int.class);

            Object carrot = ReflectionUtils.invokeConstructor(itemConstructor, "Carrot", 2);
            Object stick = ReflectionUtils.invokeConstructor(itemConstructor, "Stick", 4);
            Object cake = ReflectionUtils.invokeConstructor(itemConstructor, "Cake", 8);

            Method addItemMethod = ReflectionUtils.requireMethod(suitcaseClass, "addItem", ReflectionUtils.findClass("Item"));
            ReflectionUtils.invokeMethod(void.class, addItemMethod, suitcase, carrot);
            ReflectionUtils.invokeMethod(void.class, addItemMethod, suitcase, cake);
            ReflectionUtils.invokeMethod(void.class, addItemMethod, suitcase, stick);

            Method heaviestItemMethod = ReflectionUtils.requireMethod(suitcaseClass, "heaviestItem");

            ret = ReflectionUtils.invokeMethod(itemClass, heaviestItemMethod, suitcase);

            if (ret != cake) {
                throw new Exception();
            }
        } catch (Throwable t) {
            junit.framework.Assert.fail("The heaviestItem method must return the heaviest item. Failing code:\n"
                    + "Suitcase m = new Suitcase(20);\n"
                    + "m.addItem(new Item(\"Carrot\", 2));\n"
                    + "m.addItem(new Item(\"Stick\", 8));\n"
                    + "m.addItem(new Item(\"Cake\", 4));\n"
                    + "Item heaviest = m.heaviestItem();\n"
                    + "returned: "+ret);
        }
    }

    @Test
    @Points("06-08.5")
    public void heaviestItemReturnsNullIfSuitcaseIsEmpty() {
        try {
            Object suitcase = ReflectionUtils.invokeConstructor(suitcaseConstructor, 20);
            Method heaviestItemMethod = ReflectionUtils.requireMethod(suitcaseClass, "heaviestItem");

            Object ret = heaviestItemMethod.invoke(suitcase);

            if (ret != null) {
                throw new Exception();
            }
        } catch (Throwable t) {
            junit.framework.Assert.fail(t.getMessage() + "Method heaviestItem must return null when a suitcase is empty.");
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
                } catch (IllegalAccessException ex) {
                }
            }

            if (f.getType().equals(ArrayList.class)) {
                f.setAccessible(true);
                try {
                    return f.get(container);
                } catch (IllegalArgumentException ex) {
                } catch (IllegalAccessException ex) {
                }
            }

            if (f.getType().equals(LinkedList.class)) {
                f.setAccessible(true);
                try {
                    return f.get(container);
                } catch (IllegalArgumentException ex) {
                } catch (IllegalAccessException ex) {
                }
            }
        }

        return null;
    }

    private void sanitaryCheck(String klassName, int n, String m) throws SecurityException {
        Field[] fields = ReflectionUtils.findClass(klassName).getDeclaredFields();

        for (Field field : fields) {
            assertFalse("You do not need \"static variables\", remove from class" + klassName + " variable " + field(field.toString(), klassName), field.toString().contains("static") && !field.toString().contains("final"));
            assertTrue("All object variables of a class must be private, but class " + klassName + " has: " + field(field.toString(), klassName), field.toString().contains("private"));
        }

        if (fields.length > 1) {
            int var = 0;
            for (Field field : fields) {
                if (!field.toString().contains("final")) {
                    var++;
                }
            }
            assertTrue("The class " + klassName + "-needs only " + m + ", remove extras", var <= n);
        }
    }

    private String field(String toString, String klassName) {
        return toString.replace(klassName + ".", "").replace("java.lang.", "").replace("java.util.", "");
    }
}
