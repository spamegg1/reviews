
import fi.helsinki.cs.tmc.edutestutils.MockInOut;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Random;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class StatisticsATest {
    
    Random rand = new Random();
    String className = "Statistics";
    Class statisticsClass;
    Reflex.ClassRef<Object> klass;
    String klassName = "Statistics";

    @Before
    public void fetchClass() {
        klass = Reflex.reflect(klassName);
    }

    @Points("04-15.1")
    @Test
    public void classIsPublic() {
        assertTrue("Class " + klassName + " must be public, so it must be defined as\npublic class " + klassName + " {...\n}", klass.isPublic());
    }

    @Points("04-15.1")
    @Test
    public void testConstructor() throws Throwable {
        newObject();
    }

    @Points("04-15.1")
    @Test
    public void methodAddNumberExists1() throws Throwable {
        String method = "addNumber";
        int param = 2;
        hasVoidMethodInt(newObject(), method, param);
    }

    @Points("04-15.1")
    @Test
    public void methodAddNumberExists() throws Throwable {
        String method = "addNumber";
        int param = 2;

        Object object = newObject();

        assertTrue("give the class " + klassName + " the method public void " + method + "(int number) ",
                klass.method(object, method).returningVoid().taking(int.class).isPublic());

        String v = "\nThe code that caused the error "
                + klassName + " t = new " + klassName + "(); t." + method + "(" + param + ");";

        klass.method(object, method).returningVoid().taking(int.class).withNiceError(v).invoke(2);
    }

    @Points("04-15.1")
    @Test
    public void methodGetCountExists() throws Throwable {
        hasMethod0(newObject(), "getCount", int.class);
    }

    @Points("04-15.1")
    @Test
    public void addNumberAndGetCountWork() throws Throwable {
        Object statistics = newStatistics();

        assertEquals("Count should be 0 in the beginning. Check your code \n"
                + "statistics = new Statistics(); System.out.println( statistics.getCount()); ", 0, getCount(statistics));

        addNumber(statistics, 3);

        assertEquals("The count should increase. Check the code \n"
                + "statistics = new Statistics(); statistics.addNumber(3); System.out.println( statistics.getCount()); ", 1, getCount(statistics));

        addNumber(statistics, 5);
        addNumber(statistics, 2);
        addNumber(statistics, -4);

        assertEquals("The count should increase. Check the code \n"
                + "statistics = new Statistics(); statistics.addNumber(3); statistics.addNumber(5); statistics.addNumber(2); statistics.addNumber(-4);System.out.println( statistics.getCount()); ", 4, getCount(statistics));
    }

     @Points("04-15.1")
    @Test
    public void addingManyAndGetCountWork() throws Throwable {

        for (int i = 0; i < 5; i++) {
            int[] numbers = randomize(10 + rand.nextInt(10));

            Object statistics = newStatistics();

            for (int number : numbers) {
                addNumber(statistics, number);
            }

            assertEquals("The following numbers were added " + toString(numbers) + " statistics.getCount()", numbers.length, getCount(statistics));
        }
    }

    @Points("04-15.1")
    @Test
    public void noExtraVariables1() {
        sanityCheck();
    }

    /*
     * part 2
     */
    @Points("04-15.2")
    @Test
    public void noExtraVariables2() {
        sanityCheck();
    }

    @Points("04-15.2")
    @Test
    public void methodSumExists() throws Throwable {
        hasMethod0(newObject(), "sum", int.class);
    }

    @Points("04-15.2")
    @Test
    public void sumWorks() throws Throwable {
        Object statistics = newStatistics();

        assertEquals("Sum in the beginning should be 0. Check the code \n"
                + "statistics = new Statistics(); System.out.println( statistics.sum()); ", 0, sum(statistics));

        addNumber(statistics, 3);

        assertEquals("The sum should increase by the added number. Check the code \n"
                + "statistics = new Statistics(); statistics.addNumber(3); System.out.println( statistics.sum()); ", 3, sum(statistics));

        addNumber(statistics, 5);
        addNumber(statistics, 2);

        assertEquals("The sum should increase by the added numbers. Check the code \n"
                + "statistics = new Statistics(); statistics.addNumber(3); statistics.addNumber(5); statistics.addNumber(2); System.out.println( statistics.sum()); ", 10, sum(statistics));


        addNumber(statistics, -4);

        assertEquals("The sum should increase by the added numbers. Check the code \n"
                + "statistics = new Statistics(); statistics.addNumebr(3); statistics.addNumber(5); statistics.addNumber(2); statistics.addNumber(-4) System.out.println( statistics.sum()); ", 6, sum(statistics));
    }

    @Points("04-15.2")
    @Test
    public void bigSumWorks() throws Throwable {

        for (int i = 0; i < 5; i++) {
            int[] numbers = randomize(10 + rand.nextInt(10));

            Object statistics = newStatistics();

            int sum = 0;
            for (int number : numbers) {
                addNumber(statistics, number);
                sum += number;
            }

            assertEquals("The following numbers were added " + toString(numbers) + " statistics.sum()", sum, sum(statistics));
        }
    }

    @Points("04-15.2")
    @Test
    public void methodAverageExists() throws Throwable {
        hasMethod0(newObject(),"average", double.class, 
                "NB: if no numbers have been added, return 0 as the average");
    }

    @Points("04-15.2")
    @Test
    public void averageWorks() throws Throwable {
        Object statistics = newStatistics();

        try {
            assertEquals("The average should be 0 in the beginning. Are you sure you are not trying to divide by zero? Check the code \n"
                    + "statistics = new Statistics(); System.out.println( statistics.average()); ", 0, average(statistics), 0.01);
        } catch (Exception e) {
            fail("The average should be 0 in the beginning. Are you sure you are not trying to divide by zero? Check the code \n"
                    + "statistics = new Statistics(); System.out.println( statistics.average()); ");
        }

        addNumber(statistics, 3);

        assertEquals("The average is not calculated correclty. Check the code \n"
                + "statistics = new Statistics(); statistics.average(); statistics.addNumber(3); System.out.println( statistics.average()); ", 3, average(statistics), 0.01);

        addNumber(statistics, 5);
        addNumber(statistics, 2);

        assertEquals("Keskiarvoa ei lasketa oikein. Tarkasta koodi \n"
                + "tilasto = new Lukutilasto(); tilasto.keskiarvo(); tilasto.lisaaLuku(3); tilasto.lisaaLuku(5); tilasto.lisaaLuku(2); System.out.println( tilasto.keskiarvo()); ", 3.333, average(statistics), 0.01);


        addNumber(statistics, -4);

        assertEquals("The average is not calculated correclty. Check the code \n"
                + "statistics = new Statistics(); statistics.average(); statistics.addNumber(3); statistics.addNumber(5); statistics.addNumber(2); statistics.addNumber(-4) System.out.println( statistics.average()); ", 1.5, average(statistics), 0.01);
    }

    @Points("04-15.2")
    @Test
    public void bigAverageWorks() throws Throwable {

        for (int i = 0; i < 5; i++) {
            int[] numbers = randomize(10 + rand.nextInt(10));

            Object statistics = newStatistics();

            double sum = 0;
            for (int number : numbers) {
                addNumber(statistics, number);
                sum += number;
            }
            double avg = sum / numbers.length;

            assertEquals("The following numbers were added " + toString(numbers) + " statistics.average()", avg, average(statistics), 0.01);
        }
    }
    
    /*
     * part 3
     */
    @Points("04-15.3")
    @Test
    public void sumOfUserInputs() throws Exception {
        MockInOut mio = new MockInOut("2\n-1\n");

        try {
            MainProgram.main(new String[0]);
        } catch (Exception e) {
            fail("The program should stop reading inputs when -1 has been entered");
        }
        String[] rows = mio.getOutput().split("\n");
        assertTrue("Your main program prints nothing", rows.length > 0);
        assertTrue("The main program should print \"Enter numbers:\" in the beginning", rows[0].contains("nter numbers"));
        String sumRow = getRow(rows, "sum");
        assertTrue("Your program should print a row in the form \"Sum: 10\" where the calculated sum is in the place of 10", sumRow != null);

        assertTrue("with the input \"2 -1 \" the program should print \"Sum: 2\". What was printed: " + sumRow, sumRow.contains("2"));
    }

    @Points("04-15.3")
    @Test
    public void sumOfUserInputs2() {
        MockInOut mio = new MockInOut("2\n4\n1\n7\n-1\n");
        try {
            MainProgram.main(new String[0]);
        } catch (Exception e) {
            fail("The program should stop reading inputs when -1 is entered");
        }
        String[] rows = mio.getOutput().split("\n");
        String sumRow = getRow(rows, "sum");

        assertTrue("with the input \"2 4 1 7 -1\" the program should print \"Sum: 14\". What was printed: " + sumRow, sumRow.contains("14"));
    }
    
     /*
     * part 4
     */

    @Points("04-15.4")
    @Test
    public void oddAndEvenNumbers() {
        MockInOut mio = new MockInOut("2\n4\n1\n6\n-1\n");
        try {
            MainProgram.main(new String[0]);
        } catch (Exception e) {
            fail("The program should stop reading inputs when -1 is entered");
        }
        String[] rows = mio.getOutput().split("\n");
        String sumRow = getRow(rows, "sum");

        assertTrue("ensure that your program prints a line with \"Sum \"", sumRow != null);
        assertTrue("with the input \"2 4 1 6 -1\" the program should print \"Sum: 13\". What was printed: " + sumRow, sumRow.contains("13"));

        String evenRow = getRow(rows, "even");
        assertTrue("Check that your program has a line that prints \"Sum of even numbers\"", evenRow != null);
        assertTrue("Your program must print a line of the form \"Sum of even numbers: 10\" where the sum of even numbers replaces 10", evenRow != null);
        assertTrue("With the input \"2 4 1 6 -1\" the program should print \"Sum of even numbers: 12\". What was printed: " + evenRow, evenRow.contains("12"));

           
        String oddRow = getRow(rows, "odd");
        assertTrue("Check that your program has a line that prints \"Sum of odd numbers\"", oddRow != null);
        assertTrue("Your program must print a line of the form \"Sum of odd numbers: 10\" where the sum of odd numbers replaces 10", oddRow != null);
        assertTrue("With the input \"2 4 1 6 -1\" the program should print \"Sum of odd numbers: 1\". What was printed: " + oddRow, oddRow.contains("1"));
    }
    
    
    private Object newStatistics() {
        try {
            statisticsClass = ReflectionUtils.findClass(className);
            return ReflectionUtils.invokeConstructor(statisticsClass.getConstructor());
        } catch (Throwable t) {
            fail("ensure that the following works in the main program:  Statistics stats = new Statistics();");
        }
        return null;
    }


    private void addNumber(Object object, int number) throws Throwable {
        Method method = ReflectionUtils.requireMethod(statisticsClass, "addNumber", int.class);
        ReflectionUtils.invokeMethod(void.class, method, object, number);
    }

    private double average(Object object) throws Throwable {
        Method method = ReflectionUtils.requireMethod(statisticsClass, "average");
        return ReflectionUtils.invokeMethod(double.class, method, object);
    }

    private int sum(Object object) throws Throwable {
        Method method = ReflectionUtils.requireMethod(statisticsClass, "sum");
        return ReflectionUtils.invokeMethod(int.class, method, object);
    }


    private int getCount(Object object) throws Throwable {
        Method method = ReflectionUtils.requireMethod(statisticsClass, "getCount");
        return ReflectionUtils.invokeMethod(int.class, method, object);
    }


    private void sanityCheck() throws SecurityException {
        Field[] fields = ReflectionUtils.findClass(className).getDeclaredFields();

        for (Field field : fields) {
            assertFalse("you don't need \"static variables\", remove " + field(field.toString()), field.toString().contains("static") && !field.toString().contains("final"));
            assertTrue("the visibility of all the object variables should be private, change " + field(field.toString()), field.toString().contains("private"));
        }

        if (fields.length > 1) {
            int var = 0;
            for (Field field : fields) {
                if (!field.toString().contains("final")) {
                    var++;
                }
            }
            assertTrue("the class " + className + " only needs object variables to store the number and sum of numbers (average can be calculated with them), remove extra fields", var < 3);
        }
    }

    private String toString(int[] numbers) {
        String str = "";
        for (int number : numbers) {
            str += number + " ";
        }
        return str;
    }


    private int[] randomize(int n) {
        int[] numbers = new int[n];

        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = rand.nextInt(30);
        }

        return numbers;
    }

    private String getRow(String[] rows, String word) {
        for (String row : rows) {
            if (row.toLowerCase().contains(word.toLowerCase())) {
                return row;
            }
        }

        return null;
    }

    private Object newObject() throws Throwable {
        Reflex.MethodRef0<Object, Object> ctor = klass.constructor().takingNoParams().withNiceError();
        assertTrue("Define a public constructor for the class " + klassName + ": public " + klassName + "()", ctor.isPublic());
        return ctor.invoke();
    }

    private void hasMethod0(Object object, String name, Class<?> returns) throws Throwable {
        hasMethod0(object, name, returns, "");
    }

    private void hasMethod0(Object object, String name, Class<?> returns, String msg) throws Throwable {
        String toReturn = returns.toString();
        String variable = ("" + klassName.charAt(0)).toLowerCase();

        assertTrue("create for the class " + klassName + " the method public " + toReturn + " " + name + "()",
                klass.method(object, name).returning(returns).takingNoParams().isPublic());

        String e = "\nThe code that caused the error "
                + klassName + " " + variable + " = new " + klassName + "(); " + variable + "." + name + "();";

        if (!msg.isEmpty()) {
            msg = "\n" + msg;
        }

        klass.method(object, name).returning(returns).takingNoParams().withNiceError(e + msg).invoke();
    }

    private void hasVoidMethodInt(Object object, String name, int v1) throws Throwable {
        String variable = ("" + klassName.charAt(0)).toLowerCase();

        assertTrue("create for the class " + klassName + " the method 'public void " + name + "(int number)' ",
                klass.method(object, name).returningVoid().taking(int.class).isPublic());

        String e = "\nThe code that caused the error "
                + klassName + " " + variable + " = new " + klassName + "(); " + variable + "." + name + "(" + v1 + ");";

        klass.method(object, name).returningVoid().taking(int.class).withNiceError(e).invoke(v1);

    }

    private String field(String toString) {
        return toString.replace(klassName + ".", "");
    }
}
