
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Field;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

@Points("05-04")
public class FitbyteTest {

    Reflex.ClassRef<Object> klass;
    String klassName = "Fitbyte";

    @Before
    public void fetchClass() {
        klass = Reflex.reflect(klassName);
    }

    @Test
    public void classIsPublic() {
        assertTrue("The class " + klassName + " must be public, so it should be defined as\npublic class " + klassName + " {...\n}", klass.isPublic());
    }

    @Test
    public void testConstructor() throws Throwable {
        Reflex.MethodRef2<Object, Object, Integer, Integer> cc = klass.constructor().taking(int.class, int.class).withNiceError();
        assertTrue("Give the class " + klassName + " a public constructor: public " + klassName + "(int age, int restingHeartRate)", cc.isPublic());
        cc.invoke(2, 5);
    }

    @Test
    public void testTargetHeartRate() throws Throwable {
        assertTrue("The class " + klass + " should have the method public double targetHeartRate(double percentageOfMaximum). Now the method was not found.", klass.method("targetHeartRate").returning(double.class).taking(double.class).exists());
    }

    @Test
    public void testTargetHeartRate1() throws Throwable {
        testConstructor();
        testTargetHeartRate();

        Reflex.MethodRef2<Object, Object, Integer, Integer> cc = klass.constructor().taking(int.class, int.class).withNiceError();
        assertTrue("Give the class " + klassName + " a public constructor: public " + klassName + "(int age, int restingHeartRate)", cc.isPublic());
        Object helper = cc.invoke(30, 60);
        double target = klass.method("targetHeartRate").returning(double.class).taking(double.class).invokeOn(helper, 0.5);

        assertEquals("With age 30 and resting heart rate 60, the target heart rate should be about 122.485 when the target percentage is half of maximum.", 122.485, target, 0.1);

        target = klass.method("targetHeartRate").returning(double.class).taking(double.class).invokeOn(helper, 0.7);
        assertEquals("With age 30 and resting heart rate 60, the target heart rate should be about 147.479 when the target percentage is 70% of maximum.", 147.479, target, 0.1);
    }

    @Test
    public void testTargetHeartRate2() throws Throwable {
        testConstructor();
        testTargetHeartRate();

        Reflex.MethodRef2<Object, Object, Integer, Integer> cc = klass.constructor().taking(int.class, int.class).withNiceError();
        assertTrue("Give the class " + klassName + " a public constructor: public " + klassName + "(int age, int restingHeartRate)", cc.isPublic());
        Object helper = cc.invoke(60, 70);
        double targt = klass.method("targetHeartRate").returning(double.class).taking(double.class).invokeOn(helper, 0.6);

        
        assertEquals("With age 60 and resting heart rate 70, the target heart rate should be about 126.184 when the target percentage is 60% of maximum.", 126.184, targt, 0.1);

        targt = klass.method("targetHeartRate").returning(double.class).taking(double.class).invokeOn(helper, 0.8);

        assertEquals("With age 60 and resting heart rate 70, the target heart rate should be about 144.912 when the target percentage is 80% of maximum.", 144.912, targt, 0.1);
    }

    @Test
    public void noExtraVariables() {
        sanityCheck();
    }

    private void sanityCheck() throws SecurityException {
        Field[] fields = ReflectionUtils.findClass(klassName).getDeclaredFields();

        for (Field field : fields) {
            assertFalse("you don't need \"static variables\", delete " + field(field.toString()), field.toString().contains("static") && !field.toString().contains("final"));
            assertTrue("the visibility of all the object variables should be private, change " + field(field.toString()), field.toString().contains("private"));
        }

        if (fields.length > 1) {
            int var = 0;
            for (Field field : fields) {
                if (!field.toString().contains("final")) {
                    var++;
                }
            }

            assertTrue("the class " + klassName + " only needs object variables for age and resting heart rate. Delete the other variables", var < 3);
        }
    }

    private String field(String toString) {
        return toString.replace(klassName + ".", "");
    }
}
