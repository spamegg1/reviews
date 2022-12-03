
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import java.util.UUID;
import org.junit.*;
import static org.junit.Assert.*;

public class PrintingACollectionTest {

    @Rule
    public MockStdio io = new MockStdio();
    
    @Test
    @Points("06-04")
    public void toStringPrintsNothing() throws Throwable {
        String out = io.getSysOut();
        
        SimpleCollection c = new SimpleCollection("test");
        c.toString();
        c.add("first");
        c.toString();
        c.add("second");
        c.toString();
        c.add("third");
        c.toString();
        
        assertEquals("The toString method should print nothing. Instead it should return a string representation of the object.", out, io.getSysOut());
    }
    
    @Test
    @Points("06-04")
    public void emptyCollection() throws Throwable {
        SimpleCollection c = new SimpleCollection("test");
        assertEquals("When the code was:\nSimpleCollection c = new SimpleCollection(\"test\");\nSystem.out.println(c);\nExpected output: \"The collection test is empty.\".\nCurrently output is: \"" + c.toString() + "\".", "The collection test is empty.", c.toString());

        c = new SimpleCollection("collection");
        assertEquals("When the code was:\nSimpleCollection c = new SimpleCollection(\"collection\");\nSystem.out.println(c);\nExpected output: \"The collection collection is empty.\".\nCurrently output is: \"" + c.toString() + "\".", "The collection collection is empty.", c.toString());
    }
    
    @Test
    @Points("06-04")
    public void collectionSizeOne() throws Throwable {
        String out = io.getSysOut();
        
        SimpleCollection c = new SimpleCollection("test");
        c.add("first");
        assertEquals("When the code was:\nSimpleCollection c = new SimpleCollection(\"test\");\nc.add(\"first\");\nSystem.out.println(c);\nExpected output:\nThe collection test has 1 element:\nfirst\n\nCurrently output is:\n" + c.toString() + "", "The collection test has 1 element:\nfirst", c.toString().trim());

        c = new SimpleCollection("collection");
        c.add("element");
        assertEquals("When the code was:\nSimpleCollection c = new SimpleCollection(\"collection\");\nc.add(\"element\");\nSystem.out.println(c);\nExpected output:\nThe collection collection has 1 element:\nelement\n\nCurrently output is:\n" + c.toString() + "", "The collection collection has 1 element:\nelement", c.toString());
    }
    
    @Test
    @Points("06-04")
    public void collectionWithTwoOrMoreElements() throws Throwable {
        String out = io.getSysOut();
        
        String collectionName = "collection-" + UUID.randomUUID().toString().substring(0, 3);
        String firstElement = "element-" + UUID.randomUUID().toString().substring(0, 3);
        String secondElement = "element-" + UUID.randomUUID().toString().substring(0, 3);
        
        SimpleCollection c = new SimpleCollection(collectionName);
        c.add(firstElement);
        c.add(secondElement);
        assertEquals("When the code was:\nSimpleCollection c = new SimpleCollection(\"" + collectionName + "\");\nc.add(\"" + firstElement + "\");\nc.add(\"" + firstElement + "\");\nSystem.out.println(c);\nExpected output:\nThe collection " + collectionName +" has 2 elements:\n" + firstElement + "\n" + secondElement + "\n\nCurrently output is:\n" + c.toString() + "", "The collection " + collectionName +" has 2 elements:\n" + firstElement + "\n" + secondElement, c.toString().trim());
    }

}
