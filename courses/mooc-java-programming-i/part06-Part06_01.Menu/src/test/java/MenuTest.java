
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.*;
import static org.junit.Assert.*;

public class MenuTest {

    @Rule
    public MockStdio stdio = new MockStdio();

    @Test
    @Points("06-01.1")
    public void methodAddMealExists() throws Throwable {
        String klassName = "Menu";

        String method = "addMeal";

        Reflex.ClassRef<Object> productClass = Reflex.reflect(klassName);
        Object object = productClass.constructor().takingNoParams().invoke();

        assertTrue("implement a method public void " + method + "(String meal) for the class " + klassName, productClass.method(object, method)
                .returningVoid().taking(String.class).isPublic());

        String v = "\nThe code that caused the error: rl = new Menu(); rl.addMeal(\"Bratwurst\");";

        productClass.method(object, method)
                .returningVoid().taking(String.class).withNiceError(v).invoke("Bratwurst");
    }

    @Test
    @Points("06-01.1")
    public void addMealAddsANewMeal() {
        Field mealsField = null;
        try {
            mealsField = Menu.class.getDeclaredField("meals");
        } catch (NoSuchFieldException ex) {
            fail("Make sure that the class Menu has the attribute private ArrayList<String> meals;");
        }

        Menu menu = new Menu();
        mealsField.setAccessible(true);

        Method m = ReflectionUtils.requireMethod(Menu.class, "addMeal", String.class);

        try {
            ReflectionUtils.invokeMethod(void.class, m, menu, "first");
        } catch (Throwable ex) {
            fail("Make sure that the methof addMeal is of type void, i.e. doesn't return a value.");
        }
        try {
            ArrayList<String> meals = (ArrayList<String>) mealsField.get(menu);
            if (meals.size() != 1) {
                fail("Calling the addMeal method of Menu should add a meal to the meals list.");
            }
            try {
                ReflectionUtils.invokeMethod(void.class, m, menu, "second");
            } catch (Throwable ex) {
                Logger.getLogger(MenuTest.class.getName()).log(Level.SEVERE, null, ex);

            }
            if (meals.size() != 2) {
                fail("When two meals with different names are added, there should be two meals on the list.");
            }
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(MenuTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(MenuTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    @Points("06-01.1")
    public void mealsWithSameNameAreOnlyAddedOnce() {
        Field mealsField = null;
        try {
            mealsField = Menu.class.getDeclaredField("meals");
        } catch (NoSuchFieldException ex) {
            fail("Make sure that the class Menu has the attribute private ArrayList<String> meals;");
        }

        mealsField.setAccessible(true);

        Menu menu = new Menu();
        Method m = ReflectionUtils.requireMethod(Menu.class, "addMeal", String.class);
        try {
            ReflectionUtils.invokeMethod(void.class, m, menu, "first");
            ReflectionUtils.invokeMethod(void.class, m, menu, "first");
        } catch (Throwable ex) {
            Logger.getLogger(MenuTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        ArrayList<String> meals;
        try {
            meals = (ArrayList<String>) mealsField.get(menu);
            if (meals.size() != 1) {
                fail("A particular food must only appear once on the menu.");
            }
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(MenuTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(MenuTest.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Test
    @Points("06-01.2")
    public void methodPrintMealsExists() throws Throwable {
        String klassName = "Menu";

        String method = "printMeals";

        Reflex.ClassRef<Object> productClass = Reflex.reflect(klassName);
        Object object = productClass.constructor().takingNoParams().invoke();

        assertTrue("implement a method public void " + method + "() for the class " + klassName, productClass.method(object, method)
                .returningVoid().takingNoParams().isPublic());

        String v = "\nThe code that caused the error: rl = new Menu(); rl.printMeals();";

        productClass.method(object, method)
                .returningVoid().takingNoParams().withNiceError(v).invoke();
    }

    @Test
    @Points("06-01.2")
    public void printMealsPrintsTheMenu() {
        String carrotSoup = "Le carrot soup";
        String porkStew = "Pork stew";

        Menu menu = new Menu();

        Method addMeal = ReflectionUtils.requireMethod(Menu.class, "addMeal", String.class);

        try {
            ReflectionUtils.invokeMethod(void.class, addMeal, menu, carrotSoup);
            ReflectionUtils.invokeMethod(void.class, addMeal, menu, porkStew);
        } catch (Throwable ex) {
            fail("Make sure that adding a meal works for a new menu.");
        }

        Method m = ReflectionUtils.requireMethod(Menu.class, "printMeals");
        try {
            ReflectionUtils.invokeMethod(void.class, m, menu);
        } catch (Throwable ex) {
            fail("Make sure that printing the meals works when there are more than one meal.");
        }

        String out = stdio.getSysOut();
        assertTrue("Printing the meals should print all the added meals.", out.contains(carrotSoup) && out.contains(porkStew));
        assertTrue("Each meal should be printed on its own line. Currently the output is:" + out, out.split("\n").length > 1);

    }

    @Test
    @Points("06-01.2")
    public void methodClearMenuExists() throws Throwable {
        String klassName = "Menu";

        String method = "clearMenu";

        Reflex.ClassRef<Object> productClass = Reflex.reflect(klassName);
        Object object = productClass.constructor().takingNoParams().invoke();

        assertTrue("implement a method public void " + method + "() for the class " + klassName, productClass.method(object, method)
                .returningVoid().takingNoParams().isPublic());

        String v = "\nThe code that caused the error: rl = new Menu(); rl.clearMenu();";

        productClass.method(object, method)
                .returningVoid().takingNoParams().withNiceError(v).invoke();
    }

    @Test
    @Points("06-01.3")
    public void clearingTheMenuClearsTheMenu() {
        Field mealsField = null;
        try {
            mealsField = Menu.class.getDeclaredField("meals");
        } catch (NoSuchFieldException ex) {
            fail("Make sure that the class Menu has the attribute private ArrayList<String> meals;");
        }

        mealsField.setAccessible(true);

        Menu menu = new Menu();
        Method addMeal = ReflectionUtils.requireMethod(Menu.class, "addMeal", String.class);

        try {
            ReflectionUtils.invokeMethod(void.class, addMeal, menu, "first");
            ReflectionUtils.invokeMethod(void.class, addMeal, menu, "second");
        } catch (Throwable ex) {
            fail("Make sure that the method addMeal is of type void, i.e. doesn't return a value.");
        }

        Method clear = ReflectionUtils.requireMethod(Menu.class, "clearMenu");
        try {
            ReflectionUtils.invokeMethod(void.class, clear, menu);
        } catch (Throwable ex) {
            fail("Make sure the the method clearMenu is of type void, i.e. doesn't return a value. Also, make sure it works as intended.");
        }

        try {
            ArrayList<String> meals = (ArrayList<String>) mealsField.get(menu);
            if (meals == null) {
                fail("Do not empty the menu by setting the value of the meals variable to null.");
            }

            if (!meals.isEmpty()) {
                fail("The menu should be empty after calling the method clearMenu.");
            }
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(MenuTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(MenuTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        Method m = ReflectionUtils.requireMethod(Menu.class, "printMeals");
        try {
            ReflectionUtils.invokeMethod(void.class, m, menu);
        } catch (Throwable ex) {
            fail("Make sure that printing the meals works when there are more than one meal.");
        }

        String out = stdio.getSysOut();
        out = out.trim();
        if (!out.isEmpty()) {
            fail("There should be no output when printing an empty menu.");
        }
    }
}
