
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Field;
import java.util.ArrayList;
import org.junit.*;
import static org.junit.Assert.*;

public class MessagingServiceTest {

    @Test
    @Points("06-03")
    public void classMessagingServiceExists() throws Throwable {
        Reflex.reflect("MessagingService").requirePublic();
        Reflex.reflect("MessagingService").ctor().takingNoParams().requirePublic();
    }

    @Test
    @Points("06-03")
    public void luokallaViestipalveluArrayListTyyppinenOliomuuttuja() throws Throwable {
        Reflex.reflect("MessagingService").requirePublic();
        Class clazz = Reflex.reflect("MessagingService").cls();

        assertEquals("The class MessagingService only have one object variable. Currently there are: " + clazz.getDeclaredFields().length, 1, clazz.getDeclaredFields().length);
        Field f = clazz.getDeclaredFields()[0];
        assertEquals("The class MessagingService should have an object of type ArrayList as its object variable. Currently the type of the variable is: ", ArrayList.class, f.getType());
    }

    @Test
    @Points("06-03")
    public void methodGetMessagesExists() throws Throwable {
        Reflex.reflect("MessagingService").method("getMessages").returning(ArrayList.class).takingNoParams().requirePublic();
    }

    @Test
    @Points("06-03")
    public void lisaaViestin() throws Throwable {
        Object ms = Reflex.reflect("MessagingService").ctor().takingNoParams().invoke();

        String code = "MessagingService ms = new MessagingService();\nSystem.out.println(ms.getMessages());";

        ArrayList messages = null;
        try {
            messages = Reflex.reflect("MessagingService").method("getMessages").returning(ArrayList.class).takingNoParams().invokeOn(ms);
        } catch (Throwable t) {
            fail("An error occurred during execution of the program. Try out your program using the following code:\n" + code);
        }

        assertNotNull("The getMessage method of MessagingService should not return null. Try out your program using the following code:\n" + code, messages);
        assertEquals("When no messages have been added to the messaging service, the getMessages method of MessagingService should return an empty list.\nTry out your program using the following code:\n" + code, 0, messages.size());

        code += "\nMessage m = new Message(\"sender\", \"message\");\nms.add(v);\nSystem.out.println(ms.getMessages());";
        Message m = new Message("sender", "message");
        try {
            Reflex.reflect("MessagingService").method("add").returning(void.class).taking(Message.class).invokeOn(ms, m);
            messages = Reflex.reflect("MessagingService").method("getMessages").returning(ArrayList.class).takingNoParams().invokeOn(ms);
        } catch (Throwable t) {
            fail("An error occurred during execution of the program. Try out your program using the following code:\n" + code);
        }

        assertNotNull("The getMessage method of MessagingService should not return null. Try out your program using the following code:\n" + code, messages);
        assertEquals("When one message has been added to the messaging service, the getMessages method of MessagingService should return a list containing one message.\nTry out your program using the following code:\n" + code, 1, messages.size());

        Message returned = (Message) messages.get(0);
        assertEquals("The message added to the messaging service should be the same as the one on the list returned by the getMessages method. Currently, it was not. Try the following code:\n" + code, m, returned);
        assertNotEquals("The operation of the equals method of the Message class has likely been altered.", new Message("random", "something"), returned);
    }

    @Test
    @Points("06-03")
    public void acceptsAMessageOfLength280() throws Throwable {
        testAddingMessage(280);
    }

    @Test
    @Points("06-03")
    public void doesNotAcceptAMessageOfLength281() throws Throwable {
        testAddingMessage(281);
    }

    private void testAddingMessage(int messageLength) throws Throwable {
        Object ms = Reflex.reflect("MessagingService").ctor().takingNoParams().invoke();
        String mc = "abcdefghijklmnopqrstuvxyz";
        mc = mc + mc + mc + mc + mc + mc + mc + mc + mc + mc + mc + mc + mc + mc + mc + mc + mc + mc;
        mc = mc.substring(0, messageLength);

        Message m = new Message("sender", mc);
        ArrayList<Message> messages = null;
        try {
            Reflex.reflect("MessagingService").method("add").returning(void.class).taking(Message.class).invokeOn(ms, m);
            messages = Reflex.reflect("MessagingService").method("getMessages").returning(ArrayList.class).takingNoParams().invokeOn(ms);
        } catch (Throwable t) {
            fail("An error occurred during execution of the program. Try to create a program in which you add a message containing exactly " + messageLength + " characters to the list.");
        }

        assertNotNull("The getMessage method of MessagingService should not return null.", messages);
        if (messageLength > 280) {
            assertEquals("When one message containing " + messageLength + " characters has been added to the messaging service, the getMessages method of MessagingService should return an empty list.", 0, messages.size());
            return;
        }

        assertEquals("When one message containing " + messageLength + " characters has been added to the messaging service, the getMessages method of MessagingService should return a list containing a single message.", 1, messages.size());

        Message returned = (Message) messages.get(0);
        assertEquals("The message added to the messaging service should be the same as the one on the list returned by the getMessages method.", m, returned);
        assertNotEquals("The operation of the equals method of the Message class has likely been altered.", new Message("random", "something"), returned);
    }
}
