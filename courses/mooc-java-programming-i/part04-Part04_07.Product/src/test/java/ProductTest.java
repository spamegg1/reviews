
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.MockInOut;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Field;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

@Points("04-07")
public class ProductTest {

    Reflex.ClassRef<Object> klass;
    String className = "Product";

    @Before
    public void findTheClass() {
        klass = Reflex.reflect(className);
    }

    @Test
    public void classIsPublic() {
        assertTrue("Class" + className + " must be public, so it must be declared \npublic class " + className + " {...\n}", klass.isPublic());
    }

    @Test
    public void testConstructor() throws Throwable {
        Reflex.MethodRef3<Object, Object, String, Double, Integer> cc = klass.constructor().taking(String.class, double.class, int.class).withNiceError();
        assertTrue("For the class " + className + " make a public constructor: public " + className + "(String initialName, double initialPrice, int initialQuantity)", cc.isPublic());
        cc.invoke("Banaani", 1.1, 13);
    }

    @Test
    public void noUnneseccaryVariables() {
        sanitaryCheck();
    }
    
    @Test
    public void hasMethod() throws Throwable {
        Reflex.ClassRef<Object> productClass = Reflex.reflect("Product");
        Object product = productClass.constructor().taking(String.class, double.class, int.class).invoke("Banana", 1.1, 13);

        try {
            productClass.method(product, "printProduct")
                    .returningVoid()
                    .takingNoParams().invoke();
        } catch (AssertionError ae) {
            fail("Error: " + ae + "\n create a method public void printProduct() in class Product");
        }

        assertTrue("Method printProduct must be public, se declared  public void printProduct()", productClass.method(product, "printProduct")
                .returningVoid()
                .takingNoParams().isPublic());
    }

    @Test
    public void testPrinting() throws Throwable {
        MockInOut mio = new MockInOut("");

        Object product = klass.constructor().taking(String.class, double.class, int.class).invoke("Apple", 0.1, 4);
        klass.method(product, "printProduct").returningVoid().takingNoParams().invoke();

        String out = mio.getOutput();

        mio.close();

        assertTrue("printProduct() method did not print the name of the product!", out.contains("Apple"));
        assertTrue("printProduct() method did not print the price of the product!", out.contains("0.1"));
        assertTrue("printProduct() method did not print the quantity of thr product!", out.contains("4"));
    }

    @Test
    public void testPrinting2() throws Throwable {

        MockInOut mio = new MockInOut("");

        Object product = klass.constructor().taking(String.class, double.class, int.class).invoke("Egg", 9000.0, 1);
        klass.method(product, "printProduct").returningVoid().takingNoParams().invoke();

        String out = mio.getOutput();

        assertTrue("printProduct() method did not print the name of thr product!", out.contains("Egg"));
        assertTrue("printProduct() method did not print the price of the product!", out.contains("9000"));
        assertTrue("printProduct() method did not print the quantity of thr product!", out.contains("1"));
    }
    
    

    private void sanitaryCheck() throws SecurityException {
        Field[] classObjectFields = ReflectionUtils.findClass(className).getDeclaredFields();

        for (Field field : classObjectFields) {
            assertFalse("You do not need \"static variables\", remove " + classField(field.toString()), field.toString().contains("static") && !field.toString().contains("final"));
            assertTrue("All object variables of the class should be private, change " + classField(field.toString()), field.toString().contains("private"));
        }

        if (classObjectFields.length >= 1) {
            int var = 0;
            for (Field field : classObjectFields) {
                if (!field.toString().contains("final")) {
                    var++;
                }
            }
            assertTrue("The class" + className + " only needs an object variable for name, price and quantity. Remove the extras ", var < 4);
        }
    }

    private String classField(String toString) {
        return toString.replace(className + ".", "");
    }
}
