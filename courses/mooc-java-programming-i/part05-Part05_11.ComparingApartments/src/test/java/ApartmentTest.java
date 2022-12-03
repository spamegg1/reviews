
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class ApartmentTest {

    Reflex.ClassRef<Object> klass;
    String klassName = "Apartment";

    @Before
    public void findClass() {
        klass = Reflex.reflect(klassName);
    }

    @Points("05-11.1")
    @Test
    public void methodLargerThanExist() throws Throwable {
        String method = "largerThan";

        Apartment o = new Apartment(1, 20, 1000);
        Apartment o2 = new Apartment(2, 30, 2000);

        assertTrue("implement a method public boolean " + method + "(Apartment verrattava) for the class " + klassName, klass.method(o, method)
                .returning(boolean.class).taking(Apartment.class).isPublic());

        String e = "\nThe code that caused the error: Apartment a = new Apartment(1, 20, 1000); Apartment b = new Apartment(2, 30, 100); "
                + "a.largerThan(b)";

        klass.method(o, method)
                .returning(boolean.class).taking(Apartment.class).withNiceError(e).invoke(o2);

    }

    @Points("05-11.1")
    @Test
    public void largerThanWorks1() throws Throwable {
        String method = "largerThan";

        Apartment a1 = new Apartment(1, 20, 1000);
        Apartment a2 = new Apartment(2, 30, 2000);
        Apartment a3 = new Apartment(2, 25, 1500);

        assertEquals("Apartment a1 = new Apartment(1,20,1000); Apartment a2 = new Apartment(2,30,2000); Apartment a3 = new Apartment(2, 23, 1500);\n"
                + "a1.largerThan(a2)",
                false, klass.method(a1, method).returning(boolean.class).taking(Apartment.class).invoke(a2));
        assertEquals("Apartment a1 = new Apartment(1,20,1000); Apartment a2 = new Apartment(2,30,2000); Apartment a3 = new Apartment(2, 23, 1500);\n"
                + "a2.largerThan(a1)",
                true, klass.method(a2, method).returning(boolean.class).taking(Apartment.class).invoke(a1));
        assertEquals("Apartment a1 = new Apartment(1,20,1000); Apartment a2 = new Apartment(2,30,2000); Apartment a3 = new Apartment(2, 23, 1500);\n"
                + "a2.largerThan(a3)",
                true, klass.method(a2, method).returning(boolean.class).taking(Apartment.class).invoke(a3));
        assertEquals("Apartment a1 = new Apartment(1,20,1000); Apartment a2 = new Apartment(2,30,2000); Apartment a3 = new Apartment(2, 23, 1500);\n"
                + "a3.largerThan(a2)",
                false, klass.method(a3, method).returning(boolean.class).taking(Apartment.class).invoke(a2));
        assertEquals("Apartment a1 = new Apartment(1,20,1000); Apartment a2 = new Apartment(2,30,2000); Apartment a3 = new Apartment(2, 23, 1500);\n"
                + "a1.largerThan(a3)",
                false, klass.method(a1, method).returning(boolean.class).taking(Apartment.class).invoke(a3));
        assertEquals("Apartment a1 = new Apartment(1,20,1000); Apartment a2 = new Apartment(2,30,2000); Apartment a3 = new Apartment(2, 23, 1500);\n"
                + "a3.largerThan(a1)",
                true, klass.method(a3, method).returning(boolean.class).taking(Apartment.class).invoke(a1));
    }

    @Points("05-11.2")
    @Test
    public void methodPriceDifferenceExists() throws Throwable {
        String method = "priceDifference";

        Apartment o = new Apartment(1, 20, 1000);
        Apartment o2 = new Apartment(2, 30, 2000);

        assertTrue("implement a method public int " + method + "(Apartment verrattava) for the class " + klassName, klass.method(o, method)
                .returning(int.class).taking(Apartment.class).isPublic());

        String e = "\nThe code that caused the error: Apartment a = new Apartment(1, 20, 1000); Apartment b = new Apartment(2, 30, 100); "
                + "a.priceDifference(b)";

        klass.method(o, method)
                .returning(int.class).taking(Apartment.class).withNiceError(e).invoke(o2);
    }

    @Points("05-11.2")
    @Test
    public void priceDifferenceWorks1() throws Throwable {
        String method = "priceDifference";

        Apartment a1 = new Apartment(1, 20, 1000);
        Apartment a2 = new Apartment(2, 30, 2000);
        Apartment a3 = new Apartment(2, 25, 1500);
        
        int res = klass.method(a1, method).returning(int.class).taking(Apartment.class).invoke(a2);
        assertEquals("Apartment a1 = new Apartment(1,20,1000); Apartment a2 = new Apartment(2,30,2000); Apartment a3 = new Apartment(2, 23, 1500);\n"
                + "a1.priceDifference(a2)", 40000, res);
        
        res = klass.method(a2, method).returning(int.class).taking(Apartment.class).invoke(a1);        
        assertEquals("Apartment a1 = new Apartment(1,20,1000); Apartment a2 = new Apartment(2,30,2000); Apartment a3 = new Apartment(2, 23, 1500);\n"
                + "a2.priceDifference(a1)",40000, res);
        res = klass.method(a2, method).returning(int.class).taking(Apartment.class).invoke(a3);
        assertEquals("Apartment a1 = new Apartment(1,20,1000); Apartment a2 = new Apartment(2,30,2000); Apartment a3 = new Apartment(2, 23, 1500);\n"
                + "a2.priceDifference(a3)",22500, res);
        res = klass.method(a3, method).returning(int.class).taking(Apartment.class).invoke(a2);
        assertEquals("Apartment a1 = new Apartment(1,20,1000); Apartment a2 = new Apartment(2,30,2000); Apartment a3 = new Apartment(2, 23, 1500);\n"
                + "a3.priceDifference(a2)", 22500, res);
        res = klass.method(a1, method).returning(int.class).taking(Apartment.class).invoke(a3);
        assertEquals("Apartment a1 = new Apartment(1,20,1000); Apartment a2 = new Apartment(2,30,2000); Apartment a3 = new Apartment(2, 23, 1500);\n"
                + "a1.priceDifference(a3)",17500, res);
        res = klass.method(a3, method).returning(int.class).taking(Apartment.class).invoke(a1);
        assertEquals("Apartment a1 = new Apartment(1,20,1000); Apartment a2 = new Apartment(2,30,2000); Apartment a3 = new Apartment(2, 23, 1500);\n"
                + "a3.priceDifference(a1)", 17500, res);
    }

    @Points("05-11.3")
    @Test
    public void methodMoreExpensiveThanExists() throws Throwable {
        String method = "moreExpensiveThan";

        Apartment o = new Apartment(1, 20, 1000);
        Apartment o2 = new Apartment(2, 30, 2000);

        assertTrue("implement a method public boolean " + method + "(Apartment verrattava) for the class " + klassName, klass.method(o, method)
                .returning(boolean.class).taking(Apartment.class).isPublic());

        String v = "\nThe code that caused the error: Apartment a = new Apartment(1, 20, 1000); Apartment b = new Apartment(2, 30, 100); "
                + "a.moreExpensiveThan(b)";

        klass.method(o, method)
                .returning(boolean.class).taking(Apartment.class).withNiceError(v).invoke(o2);

    }

    @Points("05-11.3")
    @Test
    public void moreExpensiveThanWorks1() throws Throwable {
        String method = "moreExpensiveThan";

        Apartment a1 = new Apartment(1, 20, 1000);
        Apartment a2 = new Apartment(2, 30, 2000);
        Apartment a3 = new Apartment(2, 25, 1500);

        assertEquals("Apartment a1 = new Apartment(1,20,1000); Apartment a2 = new Apartment(2,30,2000); Apartment a3 = new Apartment(2, 23, 1500);\n"
                + "a1.moreExpensiveThan(a2)",
                false, klass.method(a1, method).returning(boolean.class).taking(Apartment.class).invoke(a2));
        assertEquals("Apartment a1 = new Apartment(1,20,1000); Apartment a2 = new Apartment(2,30,2000); Apartment a3 = new Apartment(2, 23, 1500);\n"
                + "a2.moreExpensiveThan(a1)",
                true, klass.method(a2, method).returning(boolean.class).taking(Apartment.class).invoke(a1));
        assertEquals("Apartment a1 = new Apartment(1,20,1000); Apartment a2 = new Apartment(2,30,2000); Apartment a3 = new Apartment(2, 23, 1500);\n"
                + "a2.moreExpensiveThan(a3)",
                true, klass.method(a2, method).returning(boolean.class).taking(Apartment.class).invoke(a3));
        assertEquals("Apartment a1 = new Apartment(1,20,1000); Apartment a2 = new Apartment(2,30,2000); Apartment a3 = new Apartment(2, 23, 1500);\n"
                + "a3.moreExpensiveThan(a2)",
                false, klass.method(a3, method).returning(boolean.class).taking(Apartment.class).invoke(a2));
        assertEquals("Apartment a1 = new Apartment(1,20,1000); Apartment a2 = new Apartment(2,30,2000); Apartment a3 = new Apartment(2, 23, 1500);\n"
                + "a1.moreExpensiveThan(a3)",
                false, klass.method(a1, method).returning(boolean.class).taking(Apartment.class).invoke(a3));
        assertEquals("Apartment a1 = new Apartment(1,20,1000); Apartment a2 = new Apartment(2,30,2000); Apartment a3 = new Apartment(2, 23, 1500);\n"
                + "a3.moreExpensiveThan(a1)",
                true, klass.method(a3, method).returning(boolean.class).taking(Apartment.class).invoke(a1));
    }
}
