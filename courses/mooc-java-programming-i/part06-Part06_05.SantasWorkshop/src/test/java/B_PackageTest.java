
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

@Points("06-05.2")
public class B_PackageTest<_Gift, _Package> {

    private Class giftClass;
    private Constructor giftConstructor;
    private Class packageClass;
    private Constructor packageConstructor;
    String klassName = "Package";
    Reflex.ClassRef<_Package> _PackageRef;
    Reflex.ClassRef<_Gift> _GiftRef;

    @Before
    public void setup() {
        _PackageRef = Reflex.reflect("Package");
        _GiftRef = Reflex.reflect("Gift");

        try {
            giftClass = ReflectionUtils.findClass("Gift");
            giftConstructor = ReflectionUtils.requireConstructor(giftClass, String.class, int.class);

            packageClass = ReflectionUtils.findClass("Package");
            packageConstructor = ReflectionUtils.requireConstructor(packageClass);

        } catch (Throwable t) {
        }
    }

    @Test
    public void classIsPublic() {
        assertTrue("The " + klassName + " class must be public, i.e. it should be defined as \npublic class " + klassName + " {...\n}", _PackageRef.isPublic());
    }

    @Test
    public void noExtraVariables() {
        sanitaryCheck(klassName, 1, "the list storing the gifts. You can calculate the total weight of the gifts by going through the list of gifts!");
    }

    @Test
    public void testPackageConstructor() throws Throwable {
        Reflex.MethodRef0<_Package, _Package> ctor = _PackageRef.constructor().takingNoParams().withNiceError();
        assertTrue("Määrittele luokalle Implement a public constructor: public " + klassName + "() for the class " + klassName, ctor.isPublic());
        String v = "the code that caused the error: new Package();";
        ctor.withNiceError(v).invoke();
    }

    public _Package createPackage() throws Throwable {
        return _PackageRef.constructor().takingNoParams().withNiceError().invoke();
    }

    public _Gift createGift(String name, int weight) throws Throwable {
        return _GiftRef.constructor().taking(String.class, int.class).withNiceError().invoke(name, weight);
    }

    @Test
    public void methodAddGift() throws Throwable {
        _Gift gift = createGift("book", 1);
        _Package pakkage = createPackage();

        String v = "\nGift t = new Gift(\"book\",1); \n"
                + "Package m = new Package();\n"
                + "m.addGift(t);";

        assertTrue("The Package class should have a method public void addGift(Gift gift)", _PackageRef.method(pakkage, "addGift").returningVoid().taking(_GiftRef.cls()).withNiceError(v).isPublic());

        _PackageRef.method(pakkage, "addGift").returningVoid().taking(_GiftRef.cls()).withNiceError(v).invoke(gift);
    }

    @Test
    public void checkFunctionOfAddGift() {
        try {
            Object pakkage = ReflectionUtils.invokeConstructor(packageConstructor);

            Method addGiftMethod = ReflectionUtils.requireMethod(packageClass, "addGift", ReflectionUtils.findClass("Gift"));

            Object brick = ReflectionUtils.invokeConstructor(giftConstructor, "Tiili", 8);
            Object tooth = ReflectionUtils.invokeConstructor(giftConstructor, "Hammas", 8);
            ReflectionUtils.invokeMethod(void.class, addGiftMethod, pakkage, brick);
            ReflectionUtils.invokeMethod(void.class, addGiftMethod, pakkage, tooth);

            List<Object> gifts = (List<Object>) objectVariableList(packageClass, pakkage);
            if (!gifts.contains(brick)) {
                fail("Not all added gifts were found on the internal gift list of the package.");
            }

            if (!gifts.contains(tooth)) {
                fail("Not all added gifts were found on the internal gift list of the package.");
            }

        } catch (Throwable t) {
            fail("Make sure that the method addGift of the Package class adds things to the package. Also, does the class have an ArrayList that has been initialized?");
        }
    }

    @Test
    public void methodTotalWeight1() throws Throwable {
        _Gift gift = createGift("book", 1);
        _Package pakkage = createPackage();

        String v = "\nGift t = new Gift(\"book\",1);\n"
                + "Package m = new Package();\n"
                + "m.addGift(t);\n"
                + "System.out.println(m.totalWeight());";

        assertTrue("The Package class should have a method public int totalWeight()", _PackageRef.method(pakkage, "totalWeight").returning(int.class).takingNoParams().withNiceError(v).isPublic());

        _PackageRef.method(pakkage, "addGift").returningVoid().taking(_GiftRef.cls()).withNiceError(v).invoke(gift);

        int weight = _PackageRef.method(pakkage, "totalWeight").returning(int.class).takingNoParams().invoke();
        assertEquals("When running the code: " + v + "\nThe output should be 1. Now it was " + weight, 1, weight);
    }

    @Test
    public void methodTotalWeight2() throws Throwable {
        _Package pakkage = createPackage();

        String v = "\nGift l1 = new Gift(\"book\",2);\n"
                + "Gift l2 = new Gift(\"teddy\",1);\n"
                + "Gift l3 = new Gift(\"turnip\",4);\n"
                + "Package p = new Package();\n"
                + "p.addGift(l1);\n"
                + "p.addGift(l2);\n"
                + "p.addGift(l3);\n"
                + "System.out.println(p.totalWeight());";

        assertTrue("The Package class should have a method public int totalWeight()", _PackageRef.method(pakkage, "totalWeight").returning(int.class).takingNoParams().withNiceError(v).isPublic());

        _PackageRef.method(pakkage, "addGift").returningVoid().taking(_GiftRef.cls()).withNiceError(v).invoke(createGift("book", 2));
        _PackageRef.method(pakkage, "addGift").returningVoid().taking(_GiftRef.cls()).withNiceError(v).invoke(createGift("teddy", 1));
        _PackageRef.method(pakkage, "addGift").returningVoid().taking(_GiftRef.cls()).withNiceError(v).invoke(createGift("turnip", 4));

        int weight = _PackageRef.method(pakkage, "totalWeight").returning(int.class).takingNoParams().invoke();
        assertEquals("When running the code: " + v + "\nThe output should be 7. Now it was  " + weight, 7, weight);
    }

    private boolean includes(String returned, String... expectedValues) {
        for (String value : expectedValues) {
            if (!returned.contains(value)) {
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
            assertFalse("you don't need \"static variables\", remove the variable " + fieldName(field.toString(), klassName) + " from the " + klassName + " class.", field.toString().contains("static") && !field.toString().contains("final"));
            assertTrue("all the object variables of the the class should be private. In the " + klassName + " class, change the following: " + fieldName(field.toString(), klassName), field.toString().contains("private"));
        }

        if (fields.length > 1) {
            int var = 0;
            for (Field field : fields) {
                if (!field.toString().contains("final")) {
                    var++;
                }
            }
            assertTrue("The class " + klassName + " only needs " + m + " Remove the unnecessary ones.", var <= n);
        }
    }

    private String fieldName(String toString, String klassName) {
        return toString.replace(klassName + ".", "").replace("java.lang.", "").replace("java.util.", "");
    }
}
