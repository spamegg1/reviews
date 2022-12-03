
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Field;
import org.junit.Test;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Assert;

@Points("04-04")
public class RoomTest {

    @Test
    public void classExists() {
        try {
            Reflex.reflect("Room").requirePublic();
        } catch (Throwable t) {
            Assert.fail("Have you created a class Room? Make sure the name is spelled exactly right.");
        }
    }

    @Test
    public void hasTwoObjectVariables() {
        classExists();
        int numberOfObjectVariables = Reflex.reflect("Room").cls().getDeclaredFields().length;
        Assert.assertTrue("Have you made two object variables in class Room? Number of object variables found: " + numberOfObjectVariables, numberOfObjectVariables == 2);
    }

    @Test
    public void privateStringCode() {
        checkObjectVariables("code", String.class, "private String code");
    }

    @Test
    public void privateIntSeats() {
        checkObjectVariables("seats", int.class, "private int seats");
    }

    @Test
    public void hasAConstructor() {
        classExists();

        try {
            Reflex.reflect("Room").constructor().taking(String.class, int.class).requireExists();
        } catch (Throwable t) {
            Assert.fail("Have you made a constructor public Room(String code, int seats)? Make sure the name has been spelled exactly right.");
        }
    }

    @Test
    public void constructorSetsAValueToTheCode() {
        hasAConstructor();

        Object classInstance = null;
        try {
            classInstance = Reflex.reflect("Room").constructor().taking(String.class, int.class).invoke("b221", 25);
        } catch (Throwable t) {
            Assert.fail("Constructor call new Room(\"b221\", 25); failed. Check the constructor method.");
        }

        Assert.assertEquals("Constructor must set the value of a parameter to the value of an object variable. The object variable  \"code\" was not set.", "b221", valueOfObjectVariable(classInstance, "code"));
    }

    @Test
    public void constructorSetsAValueToTheCode2() {
        hasAConstructor();

        Object classInstance = null;
        try {
            classInstance = Reflex.reflect("Room").constructor().taking(String.class, int.class).invoke("b215", 75);
        } catch (Throwable t) {
            Assert.fail("Constructor call new Room(\"b215\", 75); failed. Check the constructor method.");
        }

        Assert.assertEquals("Constructor must set the value of a parameter to the value of an object variable. The object variable  \"code\" was not set.", "b215", valueOfObjectVariable(classInstance, "code"));
    }

    @Test
    public void constructorSetsTheValueOfSeats() {
        hasAConstructor();

        Object classInstance = null;
        try {
            classInstance = Reflex.reflect("Room").constructor().taking(String.class, int.class).invoke("b221", 25);
        } catch (Throwable t) {
            Assert.fail("Constructor call new Room(\"b221\", 25); failed. Check the constructor method.");
        }

        Assert.assertEquals("Constructor must set the value of a parameter to the value of an object variable. The object variable  \"seats\" was not set.", 25, valueOfObjectVariableInt(classInstance, "seats"));
    }

    @Test
    public void constructorSetsTheValueOfSeats2() {
        hasAConstructor();

        Object classInstance = null;
        try {
            classInstance = Reflex.reflect("Room").constructor().taking(String.class, int.class).invoke("b215", 75);
        } catch (Throwable t) {
            Assert.fail("Constructor call new Room(\"b215\", 75); failed. Check the constructor method.");
        }

        Assert.assertEquals("Constructor must set the value of a parameter to the value of an object variable. The object variable  \"seats\" was not set.", 75, valueOfObjectVariableInt(classInstance, "seats"));
    }

    private void checkObjectVariables(String variableName, Class type, String asString) {
        hasTwoObjectVariables();

        Field classInstance = null;

        try {
            classInstance = Reflex.reflect("Room").cls().getDeclaredField(variableName);
        } catch (NoSuchFieldException e) {
            Assert.fail("Does the class Room have the object variable \"" + asString + ";\"?");
        }

        Assert.assertTrue("Does the class Room have the object variable \"" + asString + ";\"?", classInstance.getType().equals(type));
        Assert.assertFalse("Does the class Room have the object variable \"" + asString + ";\"?", classInstance.isAccessible());
    }

    public String valueOfObjectVariable(Object object, String variableName) {

        Field fieldToTest = null;

        try {
            fieldToTest = Reflex.reflect("Room").cls().getDeclaredField(variableName);
        } catch (NoSuchFieldException e) {
            Assert.fail("Does the class Room have the object variable " + variableName + "?");
        }

        fieldToTest.setAccessible(true);

        try {
            return (String) fieldToTest.get(object);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(RoomTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(RoomTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public int valueOfObjectVariableInt(Object object, String variableName) {

        Field fieldToTest = null;

        try {
            fieldToTest = Reflex.reflect("Room").cls().getDeclaredField(variableName);
        } catch (NoSuchFieldException e) {
            Assert.fail("Does the class Room have the object variable " + variableName + "?");
        }

        fieldToTest.setAccessible(true);

        try {
            return (int) fieldToTest.get(object);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(RoomTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(RoomTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        return -1;
    }
}
