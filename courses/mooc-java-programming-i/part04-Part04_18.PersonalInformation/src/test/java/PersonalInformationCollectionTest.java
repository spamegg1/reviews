
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;

@Points("04-18")
public class PersonalInformationCollectionTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void classPersonalInformationHasNotBeenModified() {
        PersonalInformation info = new PersonalInformation("first", "last", "id");
        assertEquals("You've modified the class PersonalInformation. Revert the changes you've made.", "last, first (id)", info.toString());
    }

    @Test
    public void testInputFirst() {
        List<PersonalInformation> people = new ArrayList<>();
        people.add(new PersonalInformation("Jean", "Bartik", "271224"));
        people.add(new PersonalInformation("Betty", "Holberton", "070317"));

        enterAndCheck(people);
        assertFalse("There was something in the output that doesn't belong.", io.getSysOut().contains("John"));
    }

    @Test
    public void testInputSecond() {
        List<PersonalInformation> henkilot = new ArrayList<>();
        henkilot.add(new PersonalInformation("John", "Kennedy", "290517"));
        henkilot.add(new PersonalInformation("Ronald", "Reagan", "060211"));
        henkilot.add(new PersonalInformation("Bill", "Clinton", "190846"));

        enterAndCheck(henkilot);
        assertFalse("There was something in the output that doesn't belong.", io.getSysOut().contains("Betty"));
    }

    private void enterAndCheck(List<PersonalInformation> people) {
        String input = "";
        for (PersonalInformation p : people) {
            input += p.getFirstName() + "\n" + p.getLastName() + "\n" + p.getIdentificationNumber() + "\n";
        }
        input += "\n";

        io.setSysIn(input);
        callMain(PersonalInformationCollection.class);

        for (PersonalInformation p : people) {
            assertFalse("When the input is\n" + input + "\n, the output of the program should not contain \"" + p.getIdentificationNumber() + "\". The output was:\n" + io.getSysOut(), io.getSysOut().contains(p.getIdentificationNumber()));
        }

        for (PersonalInformation p : people) {
            assertTrue("When the input is\n" + input + "\n, the output of the program should contain \"" + p.getFirstName() + " " + p.getLastName() + "\". The output was:\n" + io.getSysOut(), io.getSysOut().contains(p.getFirstName() + " " + p.getLastName()));
        }

        for (PersonalInformation p : people) {
            String n = p.getFirstName() + " " + p.getLastName();
            assertTrue("When the input is\n" + input + "\n, the string \"" + n + "\" must appear on its own row (with the row containing nothing else). The output was:\n" + io.getSysOut(), rows().contains(n));
        }
    }

    private void callMain(Class kl) {
        try {
            kl = ReflectionUtils.newInstanceOfClass(kl);
            String[] t = null;
            String x[] = new String[0];
            Method m = ReflectionUtils.requireMethod(kl, "main", x.getClass());
            ReflectionUtils.invokeMethod(Void.TYPE, m, null, (Object) x);
        } catch (Throwable e) {
            fail("Something weird occurred. It could be that the void main(String[] args) method of the class " + kl + " has disappeared\n"
                    + " or your program crashed due to an exception. More information: " + e);
        }
    }

    private List<String> rows() {
        return Arrays.asList(io.getSysOut().split("\n")).stream().map(l -> l.trim()).filter(l -> !l.isEmpty()).collect(Collectors.toList());
    }
}
