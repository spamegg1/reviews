
import fi.helsinki.cs.tmc.edutestutils.Points;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import org.junit.Test;

@Points("05-12")
public class SongTest {

    @Test
    public void comparisonWorks1() throws Throwable {
        Song song1 = new Song("The Lonely Island", "Jack Sparrow", 196);
        Song song2 = new Song("The Lonely Island", "Jack Sparrow", 196);

        assertEquals("Try the program:\n"
                + "Song song1 = new Song(\"The Lonely Island\", \"Jack Sparrow\", 196);\n"
                + "Song song2 = new Song(\"The Lonely Island\", \"Jack Sparrow\", 196);\n"
                + "if(song1.equals(song2)) {\n"
                + "    System.out.println(\"Same!\");\n"
                + "}\n", song1, song2);
    }

    @Test
    public void comparisonWorks2() throws Throwable {
        Song song1 = new Song("Ismo Leikola", "Pendolino", 194);
        Song song2 = new Song("Ismo Leikola", "Pendolino", 194);

        assertEquals("Try the program:\n"
                + "Song song1 = new Song(\"Ismo Leikola\", \"Pendolino\", 194);\n"
                + "Song song2 = new Song(\"Ismo Leikola\", \"Pendolino\", 194);\n"
                + "if(song1.equals(song2)) {\n"
                + "    System.out.println(\"Same!\");\n"
                + "}\n", song1, song2);
    }

    @Test
    public void comparisonWorks3() throws Throwable {
        Song song1 = new Song("The Lonely Island", "Jack Sparrow", 196);
        Song song2 = new Song("Ismo Leikola", "Pendolino", 194);

        assertNotSame("Try the program:\n"
                + "Song song1 = new Song(\"The Lonely Island\", \"Jack Sparrow\", 196);\n"
                + "Song song2 = new Song(\"Ismo Leikola\", \"Pendolino\", 194);\n"
                + "if(song1.equals(song2)) {\n"
                + "    System.out.println(\"Same!\");\n"
                + "}\n", song1, song2);
    }
}
