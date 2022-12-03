
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import org.junit.*;
import static org.junit.Assert.*;

public class DecreasingCounterTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Points("04-08.1")
    @Test
    public void valueDecreases() {
        DecreasingCounter counter = new DecreasingCounter(10);
        counter.printValue();
        String out = io.getSysOut();
        assertTrue("Value of a counter does not print correctly. Did you change the code in the printCounter() method?", out.contains("value: 10"));
        int oldOutput = out.length();
        counter.decrement();
        counter.printValue();
        out = io.getSysOut().substring(oldOutput);
        assertTrue("When the method decrease() is called, counter value should decrease by one \n"
                + "Check your code  "
                + "counter = new DecreasingCounter(10); counter.decrease(); counter.printCounter();", out.contains("value: 9"));
        oldOutput = out.length();
        counter.decrement();
        counter.printValue();
        out = io.getSysOut().substring(oldOutput);
        assertTrue("When the method decrease() is called twice, counter value should decrease by two \n"
                + "Check your code  "
                + "counter = new VDecreasingCounter(10); counter.decrease(); counter.decrease(); counter.printCounter();", out.contains("value: 8"));
    }

    @Points("04-08.2")
    @Test
    public void doesNotDecreaseBelowZero() {
        DecreasingCounter counter = new DecreasingCounter(2);
        counter.decrement();
        counter.decrement();
        counter.printValue();
        String out = io.getSysOut();
        assertTrue("Counter value should decrease by one when decrease method is called. \n Check your code "
                + "counter = new VDecreasingCounter(2); counter.decrease(); counter.decrease(); counter.printCounter();", out.contains("value: 0"));

        int oldOutput = out.length();
        counter.decrement();
        counter.printValue();
        out = io.getSysOut().substring(oldOutput);

        assertTrue("Counter value should never be less than zero \n check your code"
                + "counter = new VDecreasingCounter(2); counter.decrease(); counter.decrease(); counter.decrease(); counter.printCounter();", out.contains("value: 0"));

        oldOutput = out.length();
        counter.decrement();
        counter.printValue();
        out = io.getSysOut().substring(oldOutput);

        assertTrue("Counter value should never be less than zero \n check your code"
                + "counter = new VDecreasingCounter(2); counter.decrease();  counter.decrease(); counter.decrease(); counter.decrease(); counter.printCounter();", out.contains("value: 0"));
    }

    @Points("04-08.3")
    @Test
    public void hasMethodReset() throws Throwable {
        String klassName = "DecreasingCounter";

        String method = "reset";

        Reflex.ClassRef<Object> counterClass = Reflex.reflect(klassName);
        Object object = counterClass.constructor().taking(int.class).invoke(4);

        assertTrue("Create for class " + klassName + " method public void " + method + "() ", counterClass.method(object, method)
                .returningVoid().takingNoParams().isPublic());

        String v = "\nError caused by: counter = new DecreasingCounter(2); counter.reset();";

        counterClass.method(object, method)
                .returningVoid().takingNoParams().withNiceError(v).invoke();
    }

    @Points("04-08.3")
    @Test
    public void methodResets() throws Exception {
        String methodName = "reset";

        DecreasingCounter counter = new DecreasingCounter(2);
        Method method = ReflectionUtils.requireMethod(DecreasingCounter.class, methodName);
        method.invoke(counter);

        counter.printValue();
        String out = io.getSysOut();
        assertTrue("reset() method should reset the counter value to zero. \n Check your code"
                + "counter = new DecreasingCounter(2); counter.reset(); counter.printCounter();", out.contains("value: 0"));

        int oldOutput = out.length();
        counter.decrement();
        counter.printValue();
        out = io.getSysOut().substring(oldOutput);

        assertTrue("Counter value should never be less than zero \n check your code "
                + "counter = new DecreasingCounter(2); counter.reset(); counter.printCounter();", out.contains("value: 0"));
    }

    @Points("04-08.1 04-08.2 04-08.3")
    @Test
    public void noStatics() {
        Class classInstance = DecreasingCounter.class;
        String className = classInstance.toString().replace("class ", "");

        for (Field f : classInstance.getDeclaredFields()) {
            assertFalse("The class " + className + " has a class variable (static) " + f.toString().replace(className + ".", "") + " you do not need it. Remove it! You do not need class variables in this exercise", f.toString().contains("static"));
        }
    }
}
