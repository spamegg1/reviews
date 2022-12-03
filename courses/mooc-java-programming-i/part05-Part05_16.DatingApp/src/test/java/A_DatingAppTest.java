
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import org.junit.Assert;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class A_DatingAppTest {

    Reflex.ClassRef<Object> klass;
    String klassName = "SimpleDate";

    @Before
    public void fetchClass() {
        klass = Reflex.reflect(klassName);
    }

    @Points("05-16.1")
    @Test
    public void notTooManyObjectVariables1() {
        sanityCheck("SimpleDate", 3, "Don't add new object variables to the SimpleDate class, they are not needed");
    }

    @Points("05-16.1")
    @Test
    public void methodAdvanceExists() throws Throwable {
        String method = "advance";

        Object object = new SimpleDate(1, 1, 2011);

        assertTrue("give the class " + klassName + " the method public void " + method + "()", klass.method(object, method)
                .returningVoid().takingNoParams().isPublic());


        String e = "\nThe code that caused the error SimpleDate d = new SimpleDate(1, 1, 2011); "
                + "d.advance();";

        klass.method(object, method)
                .returningVoid().takingNoParams().withNiceError(e).invoke();

    }

    @Points("05-16.1")
    @Test
    public void methodAdvanceAdvancesOneDay() {
        Class c = SimpleDate.class;
        String method = "advance";
        String error = "Give the class SimpleDate the method public void advance()";
        Method advance = null;
        try {
            advance = ReflectionUtils.requireMethod(c, method);
        } catch (Throwable t) {
            fail(error);
        }
        assertTrue(error, advance.toString().contains("public"));
        assertFalse(error, advance.toString().contains("static"));

        SimpleDate changedDate = new SimpleDate(26, 12, 2011);

        try {
            ReflectionUtils.invokeMethod(void.class, advance, changedDate);
        } catch (Throwable t) {
            fail("Make sure the SimpleDate class has the method public void advance().");
        }

        
        error = "Check that calling the advance method advances the date by one. \n"
                + "When you create an object SimpleDate date = new SimpleDate(26, 12, 2011); and call the method date.advance() once, the date should be 27.12.2011.\n";
        Assert.assertEquals(error, "27.12.2011", changedDate.toString());
    }

    @Points("05-16.1")
    @Test
    public void methodAdvanceChangesMonthProperly() {
        Class c = SimpleDate.class;
        String method = "advance";
        String error = "Give the class SimpleDate the method public void advance()";
        Method advance = null;
        try {
            advance = ReflectionUtils.requireMethod(c, method);
        } catch (Throwable t) {
            fail(error);
        }
        assertTrue(error, advance.toString().contains("public"));
        assertFalse(error, advance.toString().contains("static"));

        SimpleDate modifyDate = new SimpleDate(29, 12, 2011);

        try {
            ReflectionUtils.invokeMethod(void.class, advance, modifyDate);
        } catch (Throwable t) {
            fail("Make sure the SimpleDate class has the method public void advance().");
        }

        error = "Check that calling the advance method advances the date by one. \n"
                + "When you create an object SimpleDate date = new SimpleDate(29, 12, 2011); and call the method date.advance() once, the date should be 30.12.2011.\n";
        Assert.assertEquals(error, "30.12.2011", modifyDate.toString());

        try {
            ReflectionUtils.invokeMethod(void.class, advance, modifyDate);
        } catch (Throwable t) {
            fail("Make sure the SimpleDate class has the method public void advance().");
        }
        
        error = "Check that calling the advance method advances the date by one. \n"
                + "When you create an object SimpleDate date = new SimpleDate(30, 12, 2011); and call the method date.advance() once, the date should be 1.1.2012.\n";
        Assert.assertEquals(error, "1.1.2012", modifyDate.toString());
    }

    @Points("05-16.1")
    @Test
    public void repeatedlyCallingAdvanceAlsoAdvancesMonths() {
        Class c = SimpleDate.class;
        String method = "advance";
        String error = "Give the class SimpleDate the method public void advance()";
        Method advance = null;
        try {
            advance = ReflectionUtils.requireMethod(c, method);
        } catch (Throwable t) {
            fail(error);
        }
        assertTrue(error, advance.toString().contains("public"));
        assertFalse(error, advance.toString().contains("static"));

        SimpleDate modifyDate = new SimpleDate(30, 12, 2011);

        try {
            for (int i = 0; i < 500; i++) {
                ReflectionUtils.invokeMethod(void.class, advance, modifyDate);
            }
        } catch (Throwable t) {
            fail("Make sure the SimpleDate class has the method public void advance().");
        }

        
        error = "Check that calling the advance method advances the date by one. \n"
                + "When you create an object SimpleDate date = new SimpleDate(30, 12, 2011); and call the method date.advance() 500 times, the date should in the year 2013.\nYou printed: " + modifyDate.toString();
        Assert.assertTrue(error, modifyDate.toString().contains("2013"));
    }

    @Points("05-16.2")
    @Test
    public void parameterizedMethodAdvanceExists() throws Throwable {
        String method = "advance";

        Object object = new SimpleDate(1, 1, 2011);

        assertTrue("give the class " + klassName + " the method public void " + method + "(int howManyDays) ",
                klass.method(object, method)
                        .returningVoid().taking(int.class).isPublic());

        String e = "\nThe code that caused the error SimpleDate d = new SimpleDate(1, 1, 2011); "
                + "d.advance(23);";

        klass.method(object, method)
                .returningVoid().taking(int.class).withNiceError(e).invoke(23);

    }

    @Points("05-16.2")
    @Test
    public void parameterizedMethodAdvanceAdvancesByOneDay() {
        Class c = SimpleDate.class;
        String method = "advance";
        String error = "Give the class SimpleDate the method public void advance(int howManyDays)";
        Method advance = null;
        try {
            advance = ReflectionUtils.requireMethod(c, method, int.class);
        } catch (Throwable t) {
            fail(error);
        }
        assertTrue(error, advance.toString().contains("public"));
        assertFalse(error, advance.toString().contains("static"));

        SimpleDate modifyDate = new SimpleDate(30, 12, 2011);
        try {
            ReflectionUtils.invokeMethod(void.class, advance, modifyDate, 3);
        } catch (Throwable t) {
            fail("Make sure the SimpleDate class has the method public void advance(int howManyDays).");
        }

        Assert.assertEquals("Check that the parameterized advance method advances days by the specified number. \n"
                + "Try out executing SimpleDate date = new SimpleDate(30, 12, 2011); date.advance(3); Now the date should be 3.1.2012.\n",
                "3.1.2012",
                modifyDate.toString());
    }

    private void sanityCheck(String className, int numOfVariables, String msg) throws SecurityException {

        Field[] fields = ReflectionUtils.findClass(className).getDeclaredFields();

        for (Field field : fields) {
            assertFalse("you don't  need \"static variables\", delete " + field(field.toString()), field.toString().contains("static") && !field.toString().contains("final"));
            assertTrue("the visibility of all the object variables should be private, change " + field(field.toString()), field.toString().contains("private"));
        }

        if (fields.length > 1) {
            int var = 0;
            for (Field field : fields) {
                if (!field.toString().contains("final")) {
                    var++;
                }
            }
            assertTrue(msg, var <= numOfVariables);
        }
    }

    private String field(String toString) {
        return toString.replace("SimpleDate" + ".", "");
    }
}
