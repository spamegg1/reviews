
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Random;
import org.junit.*;
import static org.junit.Assert.*;

public class CardPaymentsTest {

    PaymentCard card;
    PaymentTerminal terminal;

    @Before
    public void setUp() {
        card = new PaymentCard(10);
        terminal = new PaymentTerminal();
    }
    Reflex.ClassRef<Object> klassL;
    String klassNameL = "PaymentCard";
    Reflex.ClassRef<Object> klassK;
    String klassNameK = "PaymentTerminal";

    @Before
    public void findClass() {
        klassL = Reflex.reflect(klassNameL);
        klassK = Reflex.reflect(klassNameK);
    }

    @Points("05-10.1")
    @Test
    public void notTooManyObjectVariablesForCard() {
        sanityCheck("PaymentCard", 1, "Don't add new object variables to the PaymentCard class, they are unnecessary.");
    }

    @Points("05-10.1")
    @Test
    public void canTakeMoneyFromCardIfBalanceIsEnough() {
        assertEquals("If the balance on the card is sufficient, then the takeMoney method should return true. Check the code: \n"
                + "t = new PaymentCard(10); t.takeMoney(8);", true, card.takeMoney(8));
        assertEquals("The balance on the card should decrease when money is taken. Check the code: "
                + "t = new PaymentCard(10); t.takeMoney(8);", 2, card.balance(), 0.01);
    }

    @Points("05-10.1")
    @Test
    public void canEmptyCard() {
        assertEquals("One should be able to take all the money from the card. Check the code: "
                + "t = new PaymentCard(10); t.takeMoney(10);", true, card.takeMoney(10));
        assertEquals("One should be able to take all the money from the card. Check the code: "
                + "t = new PaymentCard(10); t.takeMoney(10);", 0, card.balance(), 0.01);
    }

    /*
     *
     */
    @Points("05-10.2")
    @Test
    public void notTooManyObjectVariablesForTerminal1() {
        sanityCheck("PaymentTerminal", 3, "Don't add new object variables to the PaymentTerminal class, they are unnecessary.");
    }

    @Points("05-10.2")
    @Test
    public void initialMoneyInPaymentTerminal1000() {
        assertTrue("When the payment terminal is created the object varaible tracking the amount of money should get the value 1000. By printing the object we should see that the amount of money is 1000. \n"
                + "Currently the output is: \"" + terminal + "\"", terminal.toString().contains("money: 1000"));
    }

    @Points("05-10.2")
    @Test
    public void initialMoney1000AndNoSoldLunches() {
        String odotettu = "money: 1000.0, number of sold affordable meals: 0, number of sold hearty meals: 0";
        assertEquals("When the payment terminal is created the amount of money should be 1000 and sold meals should be 0, ", odotettu, terminal.toString());
    }
    
    @Points("05-10.2")
    @Test
    public void successfulAffordable() {
        double change = terminal.eatAffordably(4);
        
        String error = "When buying an affordable meal using 4 euros in cash (calling terminal.eatAffordably(4) ";
        assertEquals(error + "the method should return the correct amount of change.", 1.5, change, 0.01);

        assertTrue(error + " the money in the payment terminal should increase by 2.5 euros, i.e. the amount should be 1002.5. \nThe current state is: " + terminal, terminal.toString().contains("money: 1002.5"));

        assertTrue(error + " the amount of affordable meals sold should be 1. \n"
                + "The current state is: " + terminal, terminal.toString().contains("affordable meals: 1"));
        assertTrue(error + " the amount of hearty meals sold should still be 0. The current state is: " + terminal, terminal.toString().contains("hearty meals: 0"));
    }

    @Points("05-10.2")
    @Test
    public void successfulHearty() {
        double change = terminal.eatHeartily(5);

        String error = "When buying a hearty meal using 5 euros in cash (calling terminal.eatHeartily(5)) ";
        assertEquals(error + "the method should return the correct amount of change.", 0.7, change, 0.01);

        assertTrue(error + "the money in the payment terminal should increase by 4.3 euros, i.e. the amount should be 1004.3. \nThe current state is: " + terminal, terminal.toString().contains("money: 1004.3") || terminal.toString().contains("money: 1004.299"));

        assertTrue(error + " the amount of hearty meals sold should be 1. The current state is: " + terminal, terminal.toString().contains("hearty meals: 1"));
        assertTrue(error + " the amount of affordable meals sold should still be 0. The current state is: " + terminal, terminal.toString().contains("affordable meals: 0"));
    }

    @Points("05-10.2")
    @Test
    public void successfulExactChangeAffordable() {
        double change = terminal.eatAffordably(2.5);

        String error = "When buying an affordable meal using 2.5 euros in cash (calling terminal.eatAffordably(2.5)) ";
        assertEquals(error + "all the money should be used up, i.e. the change should be 0.", 0, change, 0.01);

        assertTrue(error + " the money in the payment terminal should increase by 2.5, i.e. the amount should be 1002.5. The current state is: " + terminal, terminal.toString().contains("money: 1002.5"));

        assertTrue(error + " the amount of affordable meals sold should be 1. The current state is: " + terminal, terminal.toString().contains("affordable meals: 1"));
    }

    @Points("05-10.2")
    @Test
    public void successfulExactChangeHearty() {
        double change = terminal.eatHeartily(4.3);

        String virhe = "When buying a hearty meal using 4.3 euros in cash (calling terminal.eatHeartily(4.3)) ";
        assertEquals(virhe + "all the money should be used up, i.e. the change should be 0.", 0, change, 0.01);

        assertTrue(virhe + " the money in the payment terminal should increase by 4.3, i.e. the amount should be 1004.3. The current state is: " + terminal, terminal.toString().contains("money: 1004.3") || terminal.toString().contains("money: 1004.299"));

        assertTrue(virhe + " the amount of hearty meals sold should be 1. \n"
                + "The current state is: " + terminal, terminal.toString().contains("hearty meals: 1"));
    }

    @Points("05-10.2")
    @Test
    public void multipleSold() {
        terminal.eatHeartily(5);
        terminal.eatAffordably(3);
        terminal.eatHeartily(5);
        terminal.eatHeartily(10);
        terminal.eatAffordably(4);

        String virhe = "After executing the operations terminal.eatHeartily(5); terminal.eatAffordably(3); terminal.eatHeartily(5);"
                + "terminal.eatHeartily(10); terminal.eatAffordably(4);";
        assertTrue(virhe + " the amount of money in the payment terminal should be 1017.9 euros. The current state is: " + terminal, terminal.toString().contains("money: 1017.899") || terminal.toString().contains("money: 1017.9"));
        assertTrue(virhe + " the amount of hearty meals sold should be 3. The current state is: " + terminal, terminal.toString().contains("hearty meals: 3"));
        assertTrue(virhe + " the amount of affordable meals sold should be 2. The current state is: " + terminal, terminal.toString().contains("affordable meals: 2"));

    }

    @Points("05-10.2")
    @Test
    public void ifNoMoneyTheSaleFailsAndTerminalUntouched() {
        double change = terminal.eatAffordably(2);

        assertEquals("When trying to buy something using insufficient funds, e.g. terminal.eatAffordably(2), then the whole amount should be returned as change", 2, change, 0.01);

        String expected = "money: 1000.0, number of sold affordable meals: 0, number of sold hearty meals: 0";
        assertEquals("When trying to buy an affordable meal from an empty terminal using insufficient funds, "
                + "the contents of the payment terminal should remain unchanged. The output should be: ", expected, terminal.toString());

        change = terminal.eatHeartily(2);
        assertEquals("When trying to buy something using insufficient funds, e.g. terminal.eatHeartily(2), then the whole amount should be returned as change", 2, change, 0.01);

        expected = "money: 1000.0, number of sold affordable meals: 0, number of sold hearty meals: 0";
        assertEquals("When trying to buy a hearty meal from an empty terminal using insufficient funds, "
                + "the contents of the payment terminal should remain unchanged. The output should be: ", expected, terminal.toString());
    }

    /*
     *
     */
    @Points("05-10.3")
    @Test
    public void notTooManyObjectVariablesForTerminal2() {
        sanityCheck("PaymentTerminal", 3, "Don't add new object variables to the PaymentCard class, they are unnecessary.");
    }

    @Points("05-10.3")
    @Test
    public void methodForCardPurchaseAffordableExists() throws Throwable {
        String method = "eatAffordably";

        PaymentTerminal t = new PaymentTerminal();

        assertTrue("implement a method public boolean " + method + "(PaymentCard card) for the class " + klassNameK,
                klassK.method(t, method).returning(boolean.class).taking(PaymentCard.class).isPublic());

        String e = "\nThe code that caused the error "
                + "t = new PaymentTerminal(); ac = new PaymentCard(10); t." + method + "(ac);";

        PaymentCard ac = new PaymentCard(10);

        klassK.method(t, method).returning(boolean.class).taking(PaymentCard.class).withNiceError(e).invoke(ac);
    }

    @Points("05-10.3")
    @Test
    public void canBuyAffordableWithCardIfFundsSufficient() {
        String scen = "terminal = new PaymentTerminal(); card = new PaymentCard(10); terminal.eatAffordably(card);";
        Boolean ok = eatAffordably(terminal, card);

        assertEquals("Buying an affordable meal using a card should be possible as long as the funds are sufficient. Check the following:\n" + scen, true, ok);

        assertEquals("The balance on the card should decrease by the price of one affordable meal. Check the code:\n"
                + scen + " card.balance();\n", 7.5, card.balance(), 0.01);

        String expected = "money: 1000.0, number of sold affordable meals: 1, number of sold hearty meals: 0";
        assertEquals("When buying an affordable meal from an empty terminal using a card, the money in the payment terminal should remain unchanged and"
                + " the amount of affordable meals sold should be 1.\n", expected, terminal.toString());
    }

    @Points("05-10.3")
    @Test
    public void canBuyAffordableWithCardIfFundsBarelySufficient() {
        String scen = "terminal = new PaymentTerminal(); card = new PaymentCard(2.5); terminal.eatAffordably(card);";
        card = new PaymentCard(2.5);
        Boolean ok = eatAffordably(terminal, card);

        assertEquals("Buying an affordable meal using a card should be possible if the balance on the card is exactly same as the price of the meal. Check the following:\n" + scen + "\n", true, ok);

        assertEquals("The balance on the card should drop to zero if we buy an affordable meal when the balance is exactly the price of that meal. Check the code:\n"
                + scen + " card.balance();\n", 0, card.balance(), 0.01);

        String expected = "money: 1000.0, number of sold affordable meals: 1, number of sold hearty meals: 0";
        assertEquals("When buying an affordable meal from an empty terminal using a card, the money in the payment terminal should remain unchanged and"
                + " the amount of affordable meals sold should be 1.", expected, terminal.toString());
    }

    @Points("05-10.3")
    @Test
    public void cannotBuyAffordableWithCardIfInsufficientFunds() {
        String scen = "terminal = new PaymentTerminal(); card = new PaymentCard(2); terminal.eatAffordably(card);";
        card = new PaymentCard(2);
        Boolean ok = eatAffordably(terminal, card);

        assertEquals("Should not be able to make a purchase using a card with insufficient funds. Check the following:\n"
                + scen + "\n", false, ok);

        assertEquals("The balance on the card should remain unchanged if the funds are insufficient for the purchase. Check the code:\n"
                + scen + " card.balance();\n", 2, card.balance(), 0.01);

        String expected = "money: 1000.0, number of sold affordable meals: 0, number of sold hearty meals: 0";
        assertEquals("When buying something from an empty terminal using a card with insufficient funds, the state of the terminal should remain unchanged.",
                expected, terminal.toString());
    }

    @Points("05-10.3")
    @Test
    public void methodForCardPurchaseHeartyExists() throws Throwable {
        String method = "eatHeartily";

        PaymentTerminal t = new PaymentTerminal();

        assertTrue("implement a method public boolean " + method + "(PaymentCard card) for the class " + klassNameK,
                klassK.method(t, method).returning(boolean.class).taking(PaymentCard.class).isPublic());

        String v = "\nThe code that caused the error "
                + "t = new PaymentTerminal(); ac = new PaymentCard(10); t." + method + "(ac);";

        PaymentCard ac = new PaymentCard(10);

        klassK.method(t, method).returning(boolean.class).taking(PaymentCard.class).withNiceError(v).invoke(ac);
    }

    @Points("05-10.3")
    @Test
    public void canBuyHeartyWithCardIfFundsSufficient() {
        String scen = "terminal = new PaymentTerminal(); card = new PaymentCard(10); terminal.eatHeartily(card);";
        Boolean ok = eatHeartily(terminal, card);

        assertEquals("Buying a hearty meal using a card should be possible as long as the funds are sufficient. Check the following: " + scen + " "
                + "\n", true, ok);

        assertEquals("The balance on the card should decrease by the price of one hearty meal. \n"
                + "Check the code "
                + scen + " card.balance();\n", 5.7, card.balance(), 0.01);

        String expected = "money: 1000.0, number of sold affordable meals: 0, number of sold hearty meals: 1";
        assertEquals("When buying a hearty meal from an empty terminal using a card, the money in the payment terminal should remain unchanged and"
                + " the amount of hearty meals sold should be 1.\n", expected, terminal.toString());
    }

    @Points("05-10.3")
    @Test
    public void canBuyHeartyWithCardIfFundsBarelySufficient() {
        String scen = "terminal = new PaymentTerminal(); card = new PaymentCard(4.3);";
        card = new PaymentCard(4.3);
        Boolean ok = eatHeartily(terminal, card);

        assertEquals("Buying a hearty meal using a card should be possible if the balance on the card is exactly same as the price of the meal. Check the following: " + scen
                + "\n", true, ok);

        assertEquals("The balance on the card should drop to zero if we buy a hearty meal when the balance is exactly the price of that meal. Check the code "
                + scen + " card.balance();\n", 0, card.balance(), 0.01);

        String expected = "money: 1000.0, number of sold affordable meals: 0, number of sold hearty meals: 1";
        assertEquals("When buying a hearty meal from an empty terminal using a card, the money in the payment terminal should remain unchanged and"
                + " the amount of hearty meals sold should be 1. ", expected, terminal.toString());
    }

    @Points("05-10.3")
    @Test
    public void cannotBuyHeartyWithCardIfInsufficientFunds() {
        String scen = "terminal = new PaymentTerminal(); card = new PaymentCard(4.1);";
        card = new PaymentCard(4.1);
        Boolean ok = eatHeartily(terminal, card);

        assertEquals("Should not be able to make a purchase using a card with insufficient funds. Check the following:\n"
                + scen + "\n", false, ok);

        assertEquals("The balance on the card should remain unchanged if the funds are insufficient for the purchase. Check the code:\n"
                + scen + " card.balance();\n", 4.1, card.balance(), 0.01);

        String expected = "money: 1000.0, number of sold affordable meals: 0, number of sold hearty meals: 0";
        assertEquals("When buying something from an empty terminal using a card with insufficient funds, the state of the terminal should remain unchanged.",
                expected, terminal.toString());
    }

    /*
     *
     */
    @Points("05-10.4")
    @Test
    public void notTooManyObjectVariablesForTerminal3() {
        sanityCheck("PaymentTerminal", 3, "Don't add new object variables to the PaymentCard class, they are unnecessary.");
    }

    @Points("05-10.4")
    @Test
    public void methodForAddingMoneyToCardExists() throws Throwable {
        String method = "addMoneyToCard";

        PaymentTerminal t = new PaymentTerminal();

        assertTrue("implement a method public void " + method + "(PaymentCard card, double summa) for the class " + klassNameK,
                klassK.method(t, method).returningVoid().taking(PaymentCard.class, double.class).isPublic());

        String v = "\nThe code that caused the error "
                + "t = new PaymentTerminal(); ac = new PaymentCard(10); t." + method + "(ac, 5);";

        PaymentCard ac = new PaymentCard(10);

        klassK.method(t, method).returningVoid().taking(PaymentCard.class, double.class).withNiceError(v).invoke(ac, 5.0);
    }
    
    @Points("05-10.4")
    @Test
    public void addingMoneyIncreasesTheBalanceOnTheCard() {
        addMoneyToCard(terminal, card, 10);
        String error = "Does adding money work? Check the code:\n"
                + "terminal = new PaymentTerminal(); card = new PaymentCard(10); terminal.addMoneyToCard(card, 10); card.balance()";
        assertEquals(error, 20, card.balance(), 0.01);

        String expected = "money: 1010.0, number of sold affordable meals: 0, number of sold hearty meals: 0";
        assertEquals("When adding money to a card, the amount of money added to the card is put into the terminal. "
                + "Initially the payment terminal contains 1000. When we add 10 euros to the card,"
                + " the output of the terminal should be: \n", expected, terminal.toString());

    }

    @Points("05-10.4")
    @Test
    public void addingNegativeAmountDoesNotChangeCardBalance() {
        addMoneyToCard(terminal, card, -10);
        String error = "Adding a negative amount should neither change the balance on the card nor the money in the terminal! Check the code:\n"
                + "terminal = new PaymentTerminal(); card = new PaymentCard(10); terminal.addMoneyToCard(card, -10); card.balance()";
        assertEquals(error, 10, card.balance(), 0.01);

        String expected = "money: 1000.0, number of sold affordable meals: 0, number of sold hearty meals: 0";
        assertEquals("Adding a negative amount should neither change the balance on the card nor the money in the terminal!"
                + "Initially the payment terminal contains 1000. When we add -10 euros to the card,"
                + " the output of the terminal should be: \n", expected, terminal.toString());

    }    
    
    @Points("05-10.4")
    @Test
    public void addingMoneyMultipleTimesWorks() {
        Random rand = new Random();
        int addedMoneyTotal = 0;
        int[] addedMoney = new int[5];
        for (int i = 0; i < addedMoney.length; i++) {
            int toAdd = 1 + rand.nextInt(20);
            addedMoney[i] = toAdd;
            addedMoneyTotal += toAdd;
            addMoneyToCard(terminal, card, toAdd);
        }

        String mj = "";
        for (int l : addedMoney) {
            mj += l + " ";
        }

        String error = "Does adding money work? check the balance on the card when you consecutively add the amounts " + mj + " to the card";
        assertEquals(error, 10 + addedMoneyTotal, card.balance(), 0.01);

        double exp = 1000 + addedMoneyTotal;
        String expected = "money: " + exp + ", number of sold affordable meals: 0, number of sold hearty meals: 0";
        assertEquals("When adding money to a card, the amount of money added to the card is put into the terminal. "
                + "Initially the payment terminal contains 1000. When we consecutively add the amounts " + mj + " to the card,"
                + " the output of the terminal should be: ", expected, terminal.toString());

    }
    /*
     *
     */

    private void addMoneyToCard(Object terminal, Object card, double amount) {
        String methodName = "addMoneyToCard";
        try {
            Method method = ReflectionUtils.requireMethod(PaymentTerminal.class, methodName, PaymentCard.class, double.class);
            ReflectionUtils.invokeMethod(void.class, method, terminal, card, amount);
        } catch (Throwable t) {
            t.printStackTrace();
            fail("implement a method public boolean addMoneyToCard(PaymentCard card, double summa) for the class PaymentTerminal");
        }
    }

    private boolean eatAffordably(Object terminal, Object card) {
        String methodName = "eatAffordably";
        try {
            Method method = ReflectionUtils.requireMethod(PaymentTerminal.class, methodName, PaymentCard.class);
            return ReflectionUtils.invokeMethod(boolean.class, method, terminal, card);
        } catch (Throwable t) {
            t.printStackTrace();
            fail("implement a method public boolean eatAffordably(PaymentCard card) for the class PaymentTerminal");
        }
        return false;
    }

    private boolean eatHeartily(Object terminal, Object card) {
        String methodName = "eatHeartily";
        try {
            Method method = ReflectionUtils.requireMethod(PaymentTerminal.class, methodName, PaymentCard.class);
            return ReflectionUtils.invokeMethod(boolean.class, method, terminal, card);
        } catch (Throwable t) {
            t.printStackTrace();
            fail("implement a method public boolean eatHeartily(PaymentCard card) for the class PaymentTerminal");
        }
        return false;
    }

    private void sanityCheck(String className, int variable, String msg) throws SecurityException {

        Field[] fields = ReflectionUtils.findClass(className).getDeclaredFields();

        String viesti = ", NB: if you wish to store the price of a meal into an object variable, do the following: "
                + " private static final double AFFORDABLE_PRICE = 2.5; ";

        for (Field field : fields) {
            assertFalse("you don't need \"static variables\", remove " + fieldName(field.toString()) + viesti, field.toString().contains("static") && !field.toString().contains("final"));
            assertTrue("the visibility of all object variables for the class should be private, change " + fieldName(field.toString()) + viesti,
                    field.toString().contains("private"));
        }

        if (fields.length > 1) {
            int var = 0;
            for (Field field : fields) {
                if (!field.toString().contains("final")) {
                    var++;
                }
            }
            assertTrue(msg + viesti, var <= variable);
        }
    }

    private String fieldName(String toString) {
        return toString.replace("PaymentCard" + ".", "");
    }
}
