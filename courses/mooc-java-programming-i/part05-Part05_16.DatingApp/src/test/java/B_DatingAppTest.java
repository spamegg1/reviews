
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import org.junit.Assert;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class B_DatingAppTest {

    Reflex.ClassRef<Object> klass;
    String klassName = "SimpleDate";

    @Before
    public void fetchClass() {
        klass = Reflex.reflect(klassName);
    }

    @Points("05-16.3")
    @Test
    public void methodAfterNumberOfDaysExists() throws Throwable {
        String method = "afterNumberOfDays";

        Object object = new SimpleDate(1, 1, 2011);

        assertTrue("give the class " + klassName + " the method public SimpleDate " + method + "(int days) ", klass.method(object, method)
                .returning(SimpleDate.class).taking(int.class).isPublic());

        String e = "\nThe code that caused the error SimpleDate d = new Date(1, 1, 2011); "
                + "d.afterNumberOfDays(7);";


        klass.method(object, method)
                .returning(SimpleDate.class).taking(int.class).withNiceError(e).invoke(7);

    }

    @Points("05-16.3")
    @Test
    public void methodAfterNumberOfDaysCreatesNewObject() {
        Class c = SimpleDate.class;
        String method = "afterNumberOfDays";
        String error = "Give the class SimpleDate the method public SimpleDate afterNumberOfDays(int days)";
        Method m = null;
        try {
            m = ReflectionUtils.requireMethod(c, method, int.class);
        } catch (Throwable t) {
            fail(error);
        }
        assertTrue(error, m.toString().contains("public"));
        assertFalse(error, m.toString().contains("static"));


        SimpleDate modifyDate = new SimpleDate(30, 12, 2011);
        SimpleDate newDate = null;
        try {
            newDate = ReflectionUtils.invokeMethod(SimpleDate.class, m, modifyDate, 4);
        } catch (Throwable t) {
            fail(error);
        }

        Assert.assertEquals("Check that the afterNumberOfDays method  does not change the values of the original object.",
                "30.12.2011",
                modifyDate.toString());

        Assert.assertFalse("Now the afterNumberOfDays method returns the value null.\n Try executing SimpleDate date = new SimpleDate(30, 12, 2011); SimpleDate newDate = date.afterNumberOfDays(5); The newDate should be in January 2012.",
                 newDate==null);        
        
        Assert.assertEquals("Check that the afterNumberOfDays method advances the new object by the specified number of days. \n"
                + "Execute SimpleDate date = new SimpleDate(30, 12, 2011); SimpleDate newDate = date.afterNumberOfDays(5); The newDate should be in January of 2012.",
                "4.1.2012", newDate.toString());
    }

}
