
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Field;
import java.util.ArrayList;
import org.junit.*;
import static org.junit.Assert.*;

public class PaymentCardTest {

    @Rule
    public MockStdio io = new MockStdio();
    Reflex.ClassRef<Object> klass;
    String klassName = "PaymentCard";
    String className = "PaymentCard";

    static final double AFFORDABLE = 2.6;
    static final double HEARTY = 4.6;

    @Before
    public void fetchClass() {
        klass = Reflex.reflect(klassName);
    }

    @Points("04-16.1")
    @Test
    public void classIsPublic() {
        assertTrue("The class " + klassName + " must be public, so it has to be defined as\npublic class " + klassName + " {...\n}", klass.isPublic());
    }

    @Points("04-16.1")
    @Test
    public void testConstructor() throws Throwable {
        Reflex.MethodRef1<Object, Object, Double> ctor = klass.constructor().taking(double.class).withNiceError();
        assertTrue("Define for the class " + klassName + " a public constructor: public " + klassName + "(double initialBalance)", ctor.isPublic());
        ctor.invoke(4.0);
    }

    @Points("04-16.1")
    @Test
    public void toStringInitiallyCorrect() throws Throwable {
        double sum = 10;
        Object card = newCard(sum);
        String result = toString(card);

        String expected = "The card has a balance of " + sum + " euros";
        assertFalse("Give the " + className + " class the method public String toString() as instructed in the exercise", result.contains("@"));
        assertEquals("created card =  new " + className + "(" + sum + "); the method toString doesn't work properly:", expected.toLowerCase(), result.toLowerCase());
    }

    @Points("04-16.1")
    @Test
    public void otherInitialBalanceWorks() throws Throwable {
        double sum = 25;
        Object card = newCard(sum);
        String result = toString(card);
        String expected = "The card has a balance of " + sum + " euros";

        assertEquals("created card = new " + className + "(" + sum + "); the method toString doesn't work properly::", expected.toLowerCase(), result.toLowerCase());
    }

    @Points("04-16.1")
    @Test
    public void noExtraVariables1() {
        sanityCheck();
    }

    /*
     * part 2
     */
    @Points("04-16.2")
    @Test
    public void noExtraVariables2() {
        sanityCheck();
    }

    @Points("04-16.2")
    @Test
    public void methodEatAffordablyExists() throws Throwable {
        String method = "eatAffordably";

        Object object = newCard(4.0);

        assertTrue("give the class " + klassName + " the method public void " + method + "() ", klass.method(object, method)
                .returningVoid().takingNoParams().isPublic());

        String v = "\nThe code that caused the error " + className + " lk = new " + className + "(4.0); lk.eatAffordably()";

        klass.method(object, method)
                .returningVoid().takingNoParams().withNiceError(v).invoke();

    }

    @Points("04-16.2")
    @Test
    public void eatAffordablyDecreasesBalance() throws Throwable {
        double sum = 6;
        Object card = newCard(sum);
        eat("Affordably", card);

        String result = toString(card);
        String expected = "The card has a balance of " + (sum - AFFORDABLE) + " euros";

        assertEquals("the method eatAffordably doesn't seem to decrease the balance on the card correctly. \n"
                + "Check that the following works: card = new " + className + "(6); card.eatAffordably(); System.out.println(card);\n",
                expected.toLowerCase(), result.toLowerCase());

        eat("Affordably", card);

        result = toString(card);
        expected = "The card has a balance of " + (sum - 2 * AFFORDABLE) + " euros";

        assertEquals("the method eatAffordably doesn't seem to decrease the balance on the card correctly. \n"
                + "Check that the following works: card = new " + className + "(6); card.eatAffordably(); card.eatAffordably(); System.out.println(card);\n",
                expected.toLowerCase(), result.toLowerCase());
    }

    @Points("04-16.2")
    @Test
    public void methodEatHeartilyExists() throws Throwable {
        String method = "eatHeartily";

        Object object = newCard(4.0);

        assertTrue("give the class " + klassName + " the method public void " + method + "() ", klass.method(object, method)
                .returningVoid().takingNoParams().isPublic());

        String e = "\nThe code that cause the error: " + className + " lk = new " + className + "(4.0); lk.eatHeartily()";

        klass.method(object, method)
                .returningVoid().takingNoParams().withNiceError(e).invoke();

    }

    @Points("04-16.2")
    @Test
    public void eatHeartilyDecreasesBalance() throws Throwable {
        double sum = 10;
        Object card = newCard(sum);
        eat("Heartily", card);

        String result = toString(card);
        String expected = "The card has a balance of " + (sum - HEARTY) + " euros";

        assertEquals("the method eatHeartily doesn't seem to decrease the balance correctly. \n"
                + "Check that the following works: card = new " + className + "(10); card.eatHeartily(); System.out.println(card);\n", expected.toLowerCase(), result.toLowerCase());

        eat("Heartily", card);

        result = toString(card);
        expected = "The card has a balance of " + (sum - 2 * HEARTY) + " euros";

        assertEquals("the method eatHeartily doesn't seem to decrease the balance correctly. \n"
                + "Check that the following works: card = new " + className + "(10); card.eatHeartily(); card.eatHeartily(); System.out.println(card);\n", expected.toLowerCase(), result.toLowerCase());
    }

    /*
     * part 3
     */
    @Points("04-16.3")
    @Test
    public void noExtraVariables3() {
        sanityCheck();
    }

    @Points("04-16.3")
    @Test
    public void eatAffordablyWillNotCauseNegativeBalance() throws Throwable {
        double sum = 2;
        Object card = newCard(sum);
        eat("Affordably", card);

        String result = toString(card);
        String expected = "The card has a balance of " + (sum) + " euros";

        
        assertEquals("The balance should not become negative if an affordable meal cannot be afforded. Check that the following works: \n"
                + "card = new " + className + "(2); card.eatAffordably(); card.eatAffordably(); System.out.println(card);\n", expected.toLowerCase(), result.toLowerCase());
    }

    @Points("04-16.3")
    @Test
    public void eatHeartilyWillNotCauseNegativeBalance() throws Throwable {
        double sum = 7;
        Object card = newCard(sum);

        eat("Heartily", card);
        eat("Heartily", card);

        String result = toString(card);
        String expected = "The card has a balance of " + (sum - HEARTY) + " euros";

        assertEquals("The balance should not become negative if a hearty meal cannot be afforded. Check that the following works: \n"
                + "card = new " + className + "(7); card.eatHeartily(); card.eatHeartily(); System.out.println(card);\n", expected.toLowerCase(), result.toLowerCase());
    }

    @Points("04-16.3")
    @Test
    public void allMoneyCanBeUsedToEat() throws Throwable {
        double sum = HEARTY;
        Object card = newCard(sum);

        eat("Heartily", card);

        String result = toString(card);
        String expected = "The card has a balance of " + (sum - HEARTY) + " euros";

        assertEquals("If the balance on the card is " + HEARTY + ", you should be able to use it to eat heartily. \n"
                + "Check that the following works: card = new " + className + "(" + HEARTY + "); card.eatHeartily();  System.out.println(card);", expected.toLowerCase(), result.toLowerCase());
    }

    @Points("04-16.3")
    @Test
    public void allMoneyCanBeUsedToEat2() throws Throwable {
        double sum = AFFORDABLE;
        Object card = newCard(sum);

        eat("Affordably", card);

        String result = toString(card);
        String expected = "The card has a balance of " + (sum - AFFORDABLE) + " euros";

        assertEquals("If the balance on the card is " + AFFORDABLE + ", you should be able to use it to eat affordably. \n"
                + "Check that the following works: card = new " + className + "(" + AFFORDABLE + "); card.eatAffordably();  System.out.println(card);", expected.toLowerCase(), result.toLowerCase());
    }

    /*
     * part 4
     */

    @Points("04-16.4")
    @Test
    public void noExtraVariables4() {
        sanityCheck();
    }

    @Points("04-16.4")
    @Test
    public void methodAddMoneyExists() throws Throwable {
        String method = "addMoney";

        Object object = newCard(4.0);

        assertTrue("give the class " + klassName + " the method public void " + method + "(double amount) ", klass.method(object, method)
                .returningVoid().taking(double.class).isPublic());

        String e = "\nThe code that caused the error: " + className + " lk = new " + className + "(4.0); lk.addMoney(2.0);";

        klass.method(object, method)
                .returningVoid().taking(double.class).withNiceError(e).invoke(2.0);

    }

    @Points("04-16.4")
    @Test
    public void moneyCanBeAdded() throws Throwable {
        double sum = 7;
        Object card = newCard(sum);

        addMoney(card, 3);

        String result = toString(card);
        String expected = "The card has a balance of " + (sum + 3) + " euros";

        assertEquals("Does the method addMoney work? Check that the following results in expected behavior: \n"
                + "card = new " + className + "(7); card.addMoney(3); System.out.println(card);\n"
                + "", expected.toLowerCase(), result.toLowerCase());
    }

    @Points("04-16.4")
    @Test
    public void addedMoneyCanBeUsedToBuy() throws Throwable {
        Object card = newCard(1);

        addMoney(card, 5);
        eat("Heartily", card);

        double sum = 6 - HEARTY;
        String result = toString(card);
        String expected = "The card has a balance of " + sum + " euros";

        assertEquals("Does the work addMoney work? Check that the following results in expected behavior: \n"
                + "card = new " + className + "(1); card.addMoney(5); card.eatHeartily(); System.out.println(card);\n"
                + "", expected.toLowerCase(), result.toLowerCase());
    }

    @Points("04-16.4")
    @Test
    public void balanceCannotExceedMaximum() throws Throwable {
        Object card = newCard(100);

        addMoney(card, 100);

        double sum = 150;
        String result = toString(card);
        String expected = "The card has a balance of " + sum + " euros";

        assertEquals("The balance of the card should not exceed 150. Check that the following works: \n"
                + "card = new " + className + "(100); card.addMoney(100); System.out.println(card);\n"
                + "", expected.toLowerCase(), result.toLowerCase());
    }

    /*
     * part 5
     */
    @Points("04-16.5")
    @Test
    public void noExtraVariables5() {
        sanityCheck();
    }

    @Points("04-16.5")
    @Test
    public void addingNegativeAmountDoesntChangeBalance() throws Throwable {
        double balance = 10;
        Object card = newCard(10);

        addMoney(card, -7);

        String result = toString(card);
        String expected = "The card has a balance of " + balance + " euros";

        assertEquals("Adding a negative amount should have no effect. Check that the following works:\n"
                + "card = new " + className + "(10); card.addMoney(-7); System.out.println(kortti);\n", expected.toLowerCase(), result.toLowerCase());

        addMoney(card, 1);
        addMoney(card, -3);

        result = toString(card);
        expected = "The card has a balance of " + (balance + 1) + " euros";

        assertEquals("Adding a negative amount should have no effect. Check that the following works:\n"
                + "card = new " + className + "(10); card.addMoney(-7); card.addMoney(1); card.AddMoney(-3);System.out.println(card);\n", expected.toLowerCase(), result.toLowerCase());
    }

    /*
     * part 6
     */
    @Points("04-16.6")
    @Test
    public void multipleCards() {
        MainProgram.main(new String[0]);
        String[] rows = io.getSysOut().split("\n");
        assertTrue("You are not printing anything", rows.length > 0);
        for (String row : rows) {
            assertTrue("Print the owner of the card at the beginning of the row in addition to the card information! Remember to delete any extra code from the main program when doing the exercise 04-16.6", row.toLowerCase().contains("paul") || row.toLowerCase().contains("matt"));
            assertFalse("Print only one card's information on a single row, now the following row is printed: " + row, row.toLowerCase().contains("paul") && row.toLowerCase().contains("matt"));
        }
        ArrayList<String> paul = new ArrayList<String>();
        ArrayList<String> matt = new ArrayList<String>();
        for (String row : rows) {
            if (row.toLowerCase().contains("matt")) {
                matt.add(row);
            } else if (row.toLowerCase().contains("paul")) {
                paul.add(row);
            }
        }

        checkMattsRows(matt);
        checkPaulsRows(paul);
    }

    private void checkMattsRows(ArrayList<String> rows) {
        String error = "check that the print of your program is identical to the sample output. The following is printed ";

        assertEquals("Matt's card's information should be printed 3 times: ", 3, rows.size());

        assertTrue(error + rows.get(0), rows.get(0).contains("27.4"));
        assertTrue(error + rows.get(1), rows.get(1).contains("22.7"));
        assertTrue(error + rows.get(2), rows.get(2).contains("72.8"));
    }

    private void checkPaulsRows(ArrayList<String> rows) {
        assertEquals("Paul's card's information should be printed 3 times: ", 3, rows.size());

        String error = "check that the print of your program is identical to the sample output. The following is printed ";
        assertTrue(error + rows.get(0), rows.get(0).contains("15.4"));
        assertTrue(error + rows.get(1), rows.get(1).contains("35.4"));
        assertTrue(error + rows.get(2), rows.get(2).contains("30."));
    }

    @Points("04-16.6")
    @Test
    public void noExtraVariables6() {
        sanityCheck();
    }

    private void sanityCheck() throws SecurityException {
        Field[] fields = ReflectionUtils.findClass(className).getDeclaredFields();

        for (Field field : fields) {
            assertFalse("you don't need \"static variables\", remove " + field(field.toString()), field.toString().contains("static") && !field.toString().contains("final"));
            assertTrue("the visibility of all object variables must be private, but the code contains: " + field(field.toString()), field.toString().contains("private"));
        }

        if (fields.length > 1) {
            int var = 0;
            for (Field field : fields) {
                if (!field.toString().contains("final")) {
                    var++;
                }
            }
            assertTrue("class " + className + " only needs an object variable that stores the balance, remove the extra variables", var < 2);
        }
    }

    /*
     * helpers
     */
    private void eat(String how, Object card) throws Throwable {
        String method = "eat" + how;

        String e = "\nThe code that caused the error: lk = new " + className + "(4); lk." + method + "()";

        klass.method(card, method)
                .returningVoid().takingNoParams().withNiceError(e).invoke();
    }

    private void addMoney(Object card, double amount) throws Throwable {

        String method = "addMoney";

        String e = "\nThe code that caused the error: lk = new " + className + "(4); lk." + method + "(" + amount + ")";

        klass.method(card, method)
                .returningVoid().taking(double.class).withNiceError(e).invoke(amount);
    }

    private String toString(Object object) throws Throwable {
        String method = "toString";

        String v = "\nThe code that caused the error: lk = new " + className + "(4); lk.toString()";

        return klass.method(object, method)
                .returning(String.class).takingNoParams().withNiceError(v).invoke();
    }

    private Object newCard(double balance) throws Throwable {
        Reflex.MethodRef1<Object, Object, Double> ctor = klass.constructor().taking(double.class).withNiceError();
        assertTrue("Define for the class " + klassName + " a public constructor: public " + klassName + "(double initialBalance)", ctor.isPublic());
        return ctor.invoke(balance);
    }

    private String field(String toString) {
        return toString.replace(className + ".", "");
    }
}
