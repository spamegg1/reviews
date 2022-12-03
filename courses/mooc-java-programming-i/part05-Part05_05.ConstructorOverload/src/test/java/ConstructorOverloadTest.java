
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import static org.junit.Assert.*;
import org.junit.Test;

@Points("05-05")
public class ConstructorOverloadTest {

    @Test
    public void constructorWithName() throws Throwable {
        Reflex.reflect(Product.class).ctor().taking(String.class).requirePublic();

        Product e = Reflex.reflect(Product.class).ctor().taking(String.class).invoke("sausage");
        String errorMessage = "was not right. Try:\n"
                + "Product p = new Product(\"sausage\");\n"
                + "System.out.println(e.getName());\n"
                + "System.out.println(e.getLocation());\n"
                + "System.out.println(e.getWeight());";

        assertEquals("Name " + errorMessage, "sausage", e.getName());
        assertEquals("Location " + errorMessage, "shelf", e.getLocation());
        assertEquals("Weight " + errorMessage, 1, e.getWeight());
    }

    @Test
    public void constructorWithNameAndLocation() throws Throwable {
        Reflex.reflect(Product.class).ctor().taking(String.class, String.class).requirePublic();

        Product p = Reflex.reflect(Product.class).ctor().taking(String.class, String.class).invoke("sheep", "field");
        String errorMessage = "not correct. Try:\n"
                + "Product p = new Product(\"sheep\", \"field\");\n"
                + "System.out.println(p.getName());\n"
                + "System.out.println(p.getLocation());\n"
                + "System.out.println(p.getWeight());";

        assertEquals("Name " + errorMessage, "sheep", p.getName());
        assertEquals("Location " + errorMessage, "field", p.getLocation());
        assertEquals("Weight " + errorMessage, 1, p.getWeight());
    }

    @Test
    public void constructorWithNameAndWeight() throws Throwable {
        Reflex.reflect(Product.class).ctor().taking(String.class, int.class).requirePublic();

        Product e = Reflex.reflect(Product.class).ctor().taking(String.class, int.class).invoke("whale", 1000);
        String errorMessage = "not correct. Try:\n"
                + "Product p = new Product(\"whale\", 1000);\n"
                + "System.out.println(p.getName());\n"
                + "System.out.println(p.getLocation());\n"
                + "System.out.println(p.getWeight());";
        

        assertEquals("Name " + errorMessage, "whale", e.getName());
        assertEquals("Location " + errorMessage, "shelf", e.getLocation());
        assertEquals("Weight " + errorMessage, 1000, e.getWeight());
    }

}
