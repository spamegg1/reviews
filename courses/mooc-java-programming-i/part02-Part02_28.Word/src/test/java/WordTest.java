
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import org.junit.*;

@Points("02-28")
public class WordTest {

    @Test(timeout = 1000)
    public void test1() throws Throwable {
        String returnValue = Reflex.reflect(Word.class).staticMethod("word").returning(String.class).takingNoParams().invoke();
    }
}
