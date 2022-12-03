
import fi.helsinki.cs.tmc.edutestutils.MockInOut;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import java.lang.reflect.Method;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.rule.PowerMockRule;
import static org.powermock.api.easymock.PowerMock.*;

@PrepareForTest({MainProgram.class, Statistics.class})
public class StatisticsBTest {

    Class laskuriLuokka;

    @Rule
    public PowerMockRule p = new PowerMockRule();

    @Points("04-15.4")
    @Test
    public void usingObject() throws Throwable {
        MockInOut mio = new MockInOut("2\n-1\n");

        Statistics sum = createMock(Statistics.class);
        Statistics evenSum = createMock(Statistics.class);
        Statistics oddSum = createMock(Statistics.class);

        //sum.addNumber(2);
        addNumberMock(sum, 2);
        //sum.sum();
        sumMock(sum);
        expectLastCall().andReturn(2);

        // sums
        getCount(sum);
        expectLastCall().andReturn(1).anyTimes();

        // calculates average
        average(sum);
        expectLastCall().andReturn(2).anyTimes();

        //evenSum.addNumber(2);
        addNumberMock(evenSum, 2);
        //evenSum.sum();
        sumMock(evenSum);
        expectLastCall().andReturn(2);

        //oddSum.sum();
        sumMock(oddSum);
        expectLastCall().andReturn(0);

        expectNew(Statistics.class).andReturn(sum);
        expectNew(Statistics.class).andReturn(evenSum);
        expectNew(Statistics.class).andReturn(oddSum);

        replay(sum, Statistics.class);
        replay(evenSum, Statistics.class);
        replay(oddSum, Statistics.class);

        try {
            MainProgram.main(new String[0]);
            verifyAll();
        } catch (Exception e) {
            fail("The program should stop reading input when -1 has been entered");
        } catch (Throwable t) {
            fail("Your program must use Statistics type variables for calculating the sums of total, even, and odd numbers!\n"
                    + "The program must create the objects in the following order: \n"
                    + "  first, the object that tracks the total sum, \n"
                    + "  second, the object that tracks the sum of even numbers, \n"
                    + "  and finally the object that tracks the sum of odd numbers.\n"
                    + "NB: don't add anything else than user-inputted numbers to the Statistics objects\n"
                    + "      number -1 is used as the ending chanracter, and it should not be added to the statistics objects!\n"
                    + "error occured with user input \"2 - 1\"\n"
                    + "more information about the error: " + t);
        }
    }

    private void addNumberMock(Object object, int number) throws Throwable {
        Method method = ReflectionUtils.requireMethod(object.getClass(), "addNumber", int.class);
        ReflectionUtils.invokeMethod(void.class, method, object, number);
    }


    private double average(Object object) throws Throwable {
        Method method = ReflectionUtils.requireMethod(object.getClass(), "average");
        return ReflectionUtils.invokeMethod(double.class, method, object);
    }

    private int getCount(Object object) throws Throwable {
        Method method = ReflectionUtils.requireMethod(object.getClass(), "getCount");
        return ReflectionUtils.invokeMethod(int.class, method, object);
    }


    private int sumMock(Object object) throws Throwable {
        Method method = ReflectionUtils.requireMethod(object.getClass(), "sum");
        return ReflectionUtils.invokeMethod(int.class, method, object);
    }
}
