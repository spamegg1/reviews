
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import fi.helsinki.cs.tmc.edutestutils.Reflex.ClassRef;
import java.lang.reflect.Field;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class MoneyTest {

    Reflex.ClassRef<Object> classToBeTested;
    String className = "Money";

    @Before
    public void findClass() {
        classToBeTested = Reflex.reflect(className);
    }

    @Points("05-17.1 05-17.2 05-17.3")
    @Test
    public void noUnnecessaryVariables() {
        sanitaryCheck("Money", 2, " object variables for the amount of euroes and cents");
    }

    @Points("05-17.1")
    @Test
    public void testPlusMethod() throws Throwable {
        String methodToTest = "plus";

        Reflex.ClassRef<Object> classToTest = Reflex.reflect(className);

        Money firstMoneyObject = new Money(1, 0);

        assertTrue("In the class" + className + " create method public Money " + methodToTest + "(Money addition) ",
                classToTest.method(firstMoneyObject, methodToTest).returning(Money.class).taking(Money.class).isPublic());

        Money secondMoneyObject = new Money(2, 0);

        String codeToRun = " Money firstMoneyObject = new Money(1, 0);"
                + "secondMoneyObject = new Money(2, 0);"
                + "Money firstPlusSecond = firstMoneyObject.plus(secondMoneyObject);";

        Money firstPlusSecond = classToTest.method(firstMoneyObject, methodToTest).returning(Money.class).taking(Money.class).
                withNiceError("Failing code: " + codeToRun).invoke(secondMoneyObject);

        assertFalse("When we did " + codeToRun + " the plus method returned null ", firstPlusSecond == null);

        assertEquals(codeToRun + " firstPlusSecond.euros();", 3, firstPlusSecond.euros());
        assertEquals(codeToRun + " firstPlusSecond.cents(); ", 0, firstPlusSecond.cents());
        assertEquals(codeToRun + " System.out.println(firstPlusSecond)", "3.00e", firstPlusSecond.toString());
        assertEquals("Alkuperäiset oliot eivät saa muuttua:\n" + codeToRun + " System.out.println(firstMoneyObject)", "1.00e", firstMoneyObject.toString());
        assertEquals("Alkuperäiset oliot eivät saa muuttua:\n" + codeToRun + " System.out.println(secondMoneyObject)", "2.00e", secondMoneyObject.toString());

        firstMoneyObject = new Money(7, 50);
        secondMoneyObject = new Money(1, 60);

        codeToRun = " Money firstMoneyObject = new Money(7, 50);"
                + "secondMoneyObject = new Money(1, 60);"
                + "Money firstPlusSecond = firstMoneyObject.plus(secondMoneyObject);";

        firstPlusSecond = classToTest.method(firstMoneyObject, methodToTest).returning(Money.class).taking(Money.class).
                withNiceError("Failing code: " + codeToRun).invoke(secondMoneyObject);

        assertEquals(codeToRun + " System.out.println(firstPlusSecond)", "9.10e", firstPlusSecond.toString());
        assertEquals("The original objects should not change:\n" + codeToRun + " System.out.println(firstMoneyObject)", "7.50e", firstMoneyObject.toString());
        assertEquals("The original objects should not change:\n" + codeToRun + " System.out.println(secondMoneyObject)", "1.60e", secondMoneyObject.toString());

    }

    @Points("05-17.2")
    @Test
    public void testMethodLess() throws Throwable {
        String methodToTest = "lessThan";

        Reflex.ClassRef<Object> classToTest = Reflex.reflect(className);

        Money firstMoneyObject = new Money(1, 0);

        assertTrue("In the class" + className + " create the method public boolean " + methodToTest + "(Money compared) ",
                classToTest.method(firstMoneyObject, methodToTest).returning(boolean.class).taking(Money.class).isPublic());

        Money secondMoneyObject = new Money(1, 5);
        Money thirdMoneyObject = new Money(-3, 5);
        Money fourthMoneyObject = new Money(2, 0);

        String codeToTest = "When Money firstMoneyObject = new Money(1, 0); "
                + "secondMoneyObject = new Money(1, 50); "
                + "thirdMoneyObject = new Money(-3,5); "
                + "fourthMoneyObject = new Money(2,0); ";

        check(firstMoneyObject, secondMoneyObject, true, codeToTest + " firstMoneyObject.lessThan(secondMoneyObject);", classToTest);
        check(secondMoneyObject, firstMoneyObject, false, codeToTest + " secondMoneyObject.lessThan(firstMoneyObject);", classToTest);

        check(firstMoneyObject, thirdMoneyObject, false, codeToTest + " firstMoneyObject.lessThan(thirdMoneyObject);", classToTest);
        check(thirdMoneyObject, firstMoneyObject, true, codeToTest + " thirdMoneyObject.lessThan(firstMoneyObject);", classToTest);

        check(secondMoneyObject, thirdMoneyObject, false, codeToTest + " secondMoneyObject.lessThan(thirdMoneyObject);", classToTest);
        check(thirdMoneyObject, secondMoneyObject, true, codeToTest + " thirdMoneyObject.lessThan(secondMoneyObject);", classToTest);

        check(secondMoneyObject, fourthMoneyObject, true, codeToTest + " secondMoneyObject.lessThan(fourthMoneyObject);", classToTest);
        check(fourthMoneyObject, secondMoneyObject, false, codeToTest + " fourthMoneyObject.lessThan(secondMoneyObject);", classToTest);

        check(thirdMoneyObject, fourthMoneyObject, true, codeToTest + " thirdMoneyObject.lessThan(fourthMoneyObject);", classToTest);
        check(fourthMoneyObject, thirdMoneyObject, false, codeToTest + " fourthMoneyObject.lessThan(thirdMoneyObject);", classToTest);
    }

    private void check(Money e, Money t, boolean res, String v, ClassRef<Object> klass) throws Throwable {

        assertEquals(v, res, klass.method(e, "lessThan").returning(boolean.class).taking(Money.class).invoke(t));
    }

    @Points("05-17.3")
    @Test
    public void testMinusMethod() throws Throwable {
        String methodToBeTested = "minus";

        Reflex.ClassRef<Object> classToBeTested = Reflex.reflect(className);

        Money firstMoneyObject = new Money(10, 0);

        assertTrue("For the class " + className + ", create method Money" + methodToBeTested + "(Money decreaser) ", classToBeTested.method(firstMoneyObject, methodToBeTested)
                .returning(Money.class).taking(Money.class).isPublic());

        Money secondMoneyObject = new Money(2, 0);

        String codeToRun = "Money firstMoneyObject = new Money(10, 0);"
                + "Money secondMoneyObjecy = new Money(2, 0);"
                + "Money tennerMinusTwoEuros = firstMoneyObject.minus(secondMoneyObject);";

        Money firstMinusSecond = classToBeTested.method(firstMoneyObject, methodToBeTested).returning(Money.class).taking(Money.class).
                withNiceError("Failing code " + codeToRun).invoke(secondMoneyObject);

        assertFalse("When we did" + codeToRun + " the minus method returned a null", firstMinusSecond == null);

        assertEquals(codeToRun + " tennerMinusTwoEuros.euros();", 8, firstMinusSecond.euros());
        assertEquals(codeToRun + " tennerMinusTwoEuros.cents(); ", 0, firstMinusSecond.cents());
        assertEquals(codeToRun + " System.out.println(r3)", "8.00e", firstMinusSecond.toString());
        assertEquals("The original objects should not change:\n" + codeToRun + " System.out.println(tenner)", "10.00e", firstMoneyObject.toString());
        assertEquals("The original objects should not change:\n" + codeToRun + " System.out.println(twoEuros)", "2.00e", secondMoneyObject.toString());

        secondMoneyObject = new Money(7, 40);

        codeToRun = "Money firstMoneyObject = new Money(10, 0);"
                + "Money secondMoneyObject = new Money(7, 40);"
                + "Money firstMinusSecond = firstMoneyObject.minus(secondMoneyObject);";

        firstMinusSecond = classToBeTested.method(firstMoneyObject, methodToBeTested).returning(Money.class).taking(Money.class).
                withNiceError("Failing code: " + codeToRun).invoke(secondMoneyObject);

        assertEquals(codeToRun + " firstMinusSecond.euros();", 2, firstMinusSecond.euros());
        assertEquals(codeToRun + " firstMinusSecond.cents(); ", 60, firstMinusSecond.cents());
        assertEquals(codeToRun + " System.out.println(firstMinusSecond)", "2.60e", firstMinusSecond.toString());
        assertEquals("The original objects should not change:\n" + codeToRun + " System.out.println(firstMoneyObject)", "10.00e", firstMoneyObject.toString());
        assertEquals("The original objects should not change:\n" + codeToRun + " System.out.println(secondMoneyObject)", "7.40e", secondMoneyObject.toString());

        firstMoneyObject = new Money(1, 00);
        secondMoneyObject = new Money(2, 00);

        codeToRun = "Remember that a Money cannot have a negative value: test the code \n"
                + "Money firstMoneyObject = new Money(1, 0);"
                + "Money secondMoneyObject = new Money(2, 0);"
                + "Money firstMinusSecond = firstMoneyObject.minus(secondMoneyObject);";

        firstMinusSecond = classToBeTested.method(firstMoneyObject, methodToBeTested).returning(Money.class).taking(Money.class).
                withNiceError("Failing code: " + codeToRun).invoke(secondMoneyObject);

        assertEquals(codeToRun + " firstMinusSecond.euros();", 0, firstMinusSecond.euros());
        assertEquals(codeToRun + " firstMinusSecond.cents(); ", 0, firstMinusSecond.cents());
        assertEquals(codeToRun + " System.out.println(r3)", "0.00e", firstMinusSecond.toString());
        assertEquals("The original objects should not change:\n" + codeToRun + " System.out.println(firstMoneyObject)", "1.00e", firstMoneyObject.toString());
        assertEquals("The original objects should not change:\n" + codeToRun + " System.out.println(secondMoneyObject)", "2.00e", secondMoneyObject.toString());


        firstMoneyObject = new Money(1, 50);
        secondMoneyObject = new Money(2, 00);

        codeToRun = "Remember that a Money cannot have a negative value: test the code \nMoney firstMoneyObject = new Money(1, 50);"
                + "Money secondMoneyObject = new Money(2, 0);"
                + "Money firstMinusSecond = firstMoneyObject.minus(secondMoneyObject);";

        firstMinusSecond = classToBeTested.method(firstMoneyObject, methodToBeTested).returning(Money.class).taking(Money.class).
                withNiceError("Failing code: " + codeToRun).invoke(secondMoneyObject);

        assertEquals(codeToRun + " firstMinusSecond.euros();", 0, firstMinusSecond.euros());
        assertEquals(codeToRun + " firstMinusSecond.cents(); ", 0, firstMinusSecond.cents());
        assertEquals(codeToRun + " System.out.println(r3)", "0.00e", firstMinusSecond.toString());
        assertEquals("The original objects should not change:\n" + codeToRun + " System.out.println(firstMoneyObject)", "1.50e", firstMoneyObject.toString());
        assertEquals("The original objects should not change:\n" + codeToRun + " System.out.println(secondMoneyObject)", "2.00e", secondMoneyObject.toString());

        firstMoneyObject = new Money(2, 50);
        secondMoneyObject = new Money(2, 00);

        codeToRun = "Remember the cents. Test the code: \n"
                + "Money firstMoneyObject = new Money(2, 50);"
                + "Money secondMoneyObject = new Money(2, 0);"
                + "Money firstMinusSecond = firstMoneyObject.minus(secondMoneyObject);";

        firstMinusSecond = classToBeTested.method(firstMoneyObject, methodToBeTested).returning(Money.class).taking(Money.class).
                withNiceError("Failing code: " + codeToRun).invoke(secondMoneyObject);

        assertEquals(codeToRun + " firstMinusSecond.euros();", 0, firstMinusSecond.euros());
        assertEquals(codeToRun + " firstMinusSecond.cents(); ", 50, firstMinusSecond.cents());
        assertEquals(codeToRun + " System.out.println(r3)", "0.50e", firstMinusSecond.toString());
        assertEquals("The original objects should not change:\n" + codeToRun + " System.out.println(firstMoneyObject)", "2.50e", firstMoneyObject.toString());
        assertEquals("The original objects should not change:\n" + codeToRun + " System.out.println(secondMoneyObject)", "2.00e", secondMoneyObject.toString());

    }

    private void sanitaryCheck(String className, int numberOfVariablesNeeded, String explanationOfNeededVariables) throws SecurityException {
        Field[] fields = ReflectionUtils.findClass(className).getDeclaredFields();

        for (Field field : fields) {
            assertFalse("You do not need \"static variables\", remove from class " + className + " variables " + field(field.toString(), className), field.toString().contains("static") && !field.toString().contains("final"));
            assertTrue("All object variables of a class should be private, but class " + className + " has: " + field(field.toString(), className), field.toString().contains("private"));
        }

        if (fields.length > 1) {
            int var = 0;
            for (Field field : fields) {
                if (!field.toString().contains("final")) {
                    var++;
                }
            }
            assertTrue("You do not need for" + className + "class other than " + explanationOfNeededVariables + ", remove all others", var <= numberOfVariablesNeeded);
        }
    }

    private String field(String toString, String className) {
        return toString.replace(className + ".", "").replace("java.lang.", "");
    }
}
