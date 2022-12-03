
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import org.junit.*;
import static org.junit.Assert.*;

@Points("02-27")
public class NumberUnoTest {

    @Test(timeout = 1000)
    public void test1() throws Throwable {
        int number = Reflex.reflect(NumberUno.class).staticMethod("numberUno").returning(int.class).takingNoParams().invoke();
        assertEquals("The 'numberUno' method should return the value 1.", 1, number);
    }

}
