
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Field;
import org.junit.Test;
import org.junit.Assert;

@Points("04-03")
public class DogTest {

    @Test
    public void dogClassExists() {
        try {
            Reflex.reflect("Dog").requirePublic();
        } catch (Throwable t) {
            Assert.fail("Have you created a class Dog? Make sure the name is spelled exactly right.");
        }
    }

    @Test
    public void threeObjectVariables() {
        dogClassExists();
        int numberOfObjectVariables = Reflex.reflect("Dog").cls().getDeclaredFields().length;
        Assert.assertTrue("Did you create three object variables in the dog class? Number of object variables found: " + numberOfObjectVariables, numberOfObjectVariables == 3);
    }

    @Test
    public void privateStringName() {
        checkObjectVariables("name", String.class, "private String name");
    }

    @Test
    public void privateStringBreed() {
        checkObjectVariables("breed", String.class, "private String breed");
    }

    @Test
    public void privateIntAge() {
        checkObjectVariables("age", int.class, "private int age");
    }

    private void checkObjectVariables(String variableName, Class type, String asString) {
        threeObjectVariables();

        Field objectVariable = null;

        try {
            objectVariable = Reflex.reflect("Dog").cls().getDeclaredField(variableName);
        } catch (NoSuchFieldException e) {
            Assert.fail("Make sure class Dog contains the object variable \"" + asString + ";\"?");
        }

        Assert.assertTrue("Make sure class Dog contains the object variable \"" + asString + ";\"?", objectVariable.getType().equals(type));
        Assert.assertFalse("Make sure class Dog contains the object variable \"" + asString + ";\"?", objectVariable.isAccessible());
    }
}
