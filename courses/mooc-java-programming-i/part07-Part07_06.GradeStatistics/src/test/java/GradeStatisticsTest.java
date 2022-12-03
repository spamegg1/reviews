
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.*;
import static org.junit.Assert.*;

public class GradeStatisticsTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    @Points("07-06.1")
    public void avgAll1() {
        io.setSysIn(userInput(-42, 24, 42, 72, 80, 52) + "-1\n");
        try {
            Main.main(new String[0]);
        } catch (Throwable t) {

        }

        String[] lines = io.getSysOut().split("\n");
        List<String> avgAllRow = Arrays.stream(lines).filter(l -> l.contains("all") && l.contains("average")).collect(Collectors.toList());
        assertTrue("Does your output have a row with the text \"Point average (all):\"?\nNow the output was:\n" + io.getSysOut(), avgAllRow.size() == 1);
        assertTrue("When the input is -42, 24, 42, 72, 80, 52, -1, the output should be \"Point average (all): 54.0\". Now the output was:\n" + avgAllRow.get(0), "Point average (all): 54.0".equals(avgAllRow.get(0).trim()));
    }

    @Test
    @Points("07-06.1")
    public void avgAll2() {
        io.setSysIn(userInput(50, 51, 52) + "-1\n");
        try {
            Main.main(new String[0]);
        } catch (Throwable t) {

        }

        String[] lines = io.getSysOut().split("\n");
        List<String> avgAllRow = Arrays.stream(lines).filter(l -> l.contains("all") && l.contains("average")).collect(Collectors.toList());
        assertTrue("Does your output have a row with the text \"Point average (all):\"?\nNow the output was:\n" + io.getSysOut(), avgAllRow.size() == 1);
        assertTrue("When the input is 50, 51, 52, -1, the output should be \"Point average (all): 51.0\". Now the output was:\n" + avgAllRow.get(0), "Point average (all): 51.0".equals(avgAllRow.get(0).trim()));
    }

    @Test
    @Points("07-06.2")
    public void avgPassing1() {
        io.setSysIn(userInput(-42, 24, 42, 72, 80, 52) + "-1\n");
        try {
            Main.main(new String[0]);
        } catch (Throwable t) {

        }

        String[] lines = io.getSysOut().split("\n");
        List<String> passingAvgRow = Arrays.stream(lines).filter(l -> l.contains("ssing") && l.contains("average")).collect(Collectors.toList());
        assertTrue("Does your output have a row with the text \"Point average (passing):\"?\nThe output was:\n" + io.getSysOut(), passingAvgRow.size() == 1);
        assertTrue("When the input is -42, 24, 42, 72, 80, 52, -1, the output should be \"Point average (passing): 68.0\". Now the output was:\n" + passingAvgRow.get(0), passingAvgRow.get(0).trim().endsWith("68.0"));
    }

    @Test
    @Points("07-06.2")
    public void avgPassing2() {
        io.setSysIn(userInput(69, 70, 71) + "-1\n");
        try {
            Main.main(new String[0]);
        } catch (Throwable t) {

        }

        String[] lines = io.getSysOut().split("\n");
        List<String> passingAvgRow = Arrays.stream(lines).filter(l -> l.contains("ssing") && l.contains("average")).collect(Collectors.toList());
        assertTrue("Does your output have a row with the text \"Point average (passing):\"?\nThe output was:\n" + io.getSysOut(), passingAvgRow.size() == 1);
        assertTrue("When the input is 69, 70, 71, -1, the output should be \"Point average (passing): 70.0\". Now the output was:\n" + passingAvgRow.get(0), passingAvgRow.get(0).trim().endsWith("70.0"));
    }

    @Test
    @Points("07-06.3")
    public void passPercentage1() {
        io.setSysIn("102\n"
                + "-4\n"
                + "33\n"
                + "77\n"
                + "99\n"
                + "1\n"
                + "-1\n");
        try {
            Main.main(new String[0]);
        } catch (Throwable t) {

        }

        String[] lines = io.getSysOut().split("\n");
        List<String> passingAvgRow = Arrays.stream(lines).filter(l -> l.contains("percentage")).collect(Collectors.toList());
        assertTrue("Does your output have a row with the text \"Pass precentage:\"?\nThe output was:\n" + io.getSysOut(), passingAvgRow.size() == 1);
        assertTrue("When the input is 102, -4, 33, 77, 99, 1, -1, the output should be \"Pass precentage: 50.0\". Now the output was:\n" + passingAvgRow.get(0), passingAvgRow.get(0).trim().endsWith("50.0"));
    }

    @Test
    @Points("07-06.3")
    public void passPercentage2() {
        io.setSysIn(userInput(49, 50, 51) + "-1\n");
        try {
            Main.main(new String[0]);
        } catch (Throwable t) {

        }

        String[] lines = io.getSysOut().split("\n");
        List<String> passingAvgRow = Arrays.stream(lines).filter(l -> l.contains("percentage")).collect(Collectors.toList());
        assertTrue("Does your output have a row with the text \"Pass precentage:\"?\nThe output was:\n" + io.getSysOut(), passingAvgRow.size() == 1);
        assertTrue("When the input is 49, 50, 51, -1, the output should be \"Pass precentage: 66.666\". Now the output was:\n" + passingAvgRow.get(0), passingAvgRow.get(0).trim().contains("66.666"));
    }

    @Test
    @Points("07-06.4")
    public void test1() {
        int[] numbers = {70};
        int[] distribution = {0, 0, 0, 1, 0, 0};
        double percentage = 100;
        test(numbers, distribution, percentage);
    }

    @Test
    @Points("07-06.4")
    public void test2() {
        int[] numbers = {62, 70};
        int[] distribution = {0, 0, 1, 1, 0, 0};
        double percentage = 100;
        test(numbers, distribution, percentage);
    }

    @Test
    @Points("07-06.4")
    public void test3() {
        int[] numbers = {75};
        int[] distribution = {0, 0, 0, 1, 0, 0};
        double percentage = 100;
        test(numbers, distribution, percentage);
    }

    @Test
    @Points("07-06.4")
    public void test4() {
        int[] numbers = {88};
        int[] distribution = {0, 0, 0, 0, 1, 0};
        double percentage = 100;
        test(numbers, distribution, percentage);
    }

    @Test
    @Points("07-06.4")
    public void test5() {
        int[] numbers = {94};
        int[] distribution = {0, 0, 0, 0, 0, 1};
        double percentage = 100;
        test(numbers, distribution, percentage);
    }

    @Test
    @Points("07-06.4")
    public void testMany3() {
        int[] numbers = {44, 12, 81, 29, 70};
        int[] distribution = {3, 0, 0, 1, 1, 0};
        double percentage = 40;
        test(numbers, distribution, percentage);
    }

    @Test
    @Points("07-06.4")
    public void testMany4() {
        int[] numbers = {52, 12, 72, 82, 92};
        int[] distribution = {1, 1, 0, 1, 1, 1};
        double percentage = 80;
        test(numbers, distribution, percentage);
    }

    @Test
    @Points("07-06.4")
    public void testMany5() {
        int[] numbers = {34, 53, 62, 62, 61, 72, 73, 92, 96, 11};
        int[] distribution = {2, 1, 3, 2, 0, 2};
        double percentage = 80;
        test(numbers, distribution, percentage);
    }

    @Test
    @Points("07-06.4")
    public void badInput() {
        int[] numbers = {42, 71, 15, 72, -2};
        int[] distribution = {2, 0, 0, 2, 0, 0};
        double percentage = 50;
        try {
            test(numbers, distribution, percentage);
        } catch (AssertionError e) {
            fail("Remember to ignore input that is not within the interval 0-100!\n" + e);
        }
    }

    /*
     * helpers
     */
    private void test(int[] numbers, int[] distribution, double percentage) {
        io.setSysIn(userInput(numbers) + "-1\n");
        Main.main(new String[0]);
        String[] rows = io.getSysOut().split("\n");

        String percentage2 = ("" + percentage).replace('.', ',');

        String row = getRow(rows, "cent");

        try {
            assertTrue("with the input " + toS(numbers) + " the pass percentage should be " + percentage + ", now the output was: \"" + row + "\"",
                    row.contains("" + percentage) || row.contains(percentage2));
        } catch (NullPointerException e) {
            fail("Did you print the pass percentage?\nwith the input " + toS(numbers) + " the pass percentage should be " + percentage + ", now the output was: \"" + row + " \"");
        }

        assertFalse("make sure that the program prints a row with the text \"Pass percentage:\"", row == null);
        for (int i = 0; i < 6; i++) {
            row = getRow(rows, i + ":");
            checkGrade(i, distribution[i], row, numbers);
        }
    }

    private void checkGrade(int i, int expected, String row, int[] numbers) {
        if (expected == 0) {
            assertFalse("with the input " + toS(numbers) + " there should not be any stars on the line for the grade " + i + ", "
                    + "now the output was \"" + row + "\"", row.contains("*"));
            return;
        }

        String stars = "";
        for (int j = 0; j < expected; j++) {
            stars += "*";
        }

        assertTrue("with the input " + toS(numbers) + " the program should print \"" + i + ": " + stars
                + "  now the output was \"" + row + "\"", row.contains(stars));
        assertFalse("with the input " + toS(numbers) + " the program should print \"" + i + ": " + stars
                + "  now the output was \"" + row + "\"", row.contains(stars + "*"));
    }

    /*private void testaaTuloste(int[] luvut) {
        io.setSysIn(syote(luvut) + "-1\n");
        Main.main(new String[0]);
        String[] rivit = io.getSysOut().split("\n");

        String rivi = haeRivi(rivit, "jakauma");
        assertFalse("tarkasta että ohjelma tulostaa rivin jolla teksti \"Arvosanajakauma:\"", rivi == null);
        rivi = haeRivi(rivit, "sentti");
        assertFalse("tarkasta että ohjelma tulostaa rivin jolla teksti \"Hyväksymisprosentti:\"", rivi == null);
        for (int i = 0; i < 6; i++) {
            rivi = haeRivi(rivit, i + ":");
            assertFalse("tarkasta että ohjelma tulostaa rivin jolla teksti \"" + i + ":\"", rivi == null);
        }
    }

    private void testaaSyote(int[] luvut) {
        io.setSysIn(syote(luvut) + "-1\n");
        try {
            Main.main(new String[0]);
        } catch (ArrayIndexOutOfBoundsException e) {
            fail("ohjelmasi viittaa taulukon tai listan ulkopuolelle syötteellä " + toS(luvut));
        } catch (NoSuchElementException e) {
            fail("ohjelmasi pitäisi pysähtyä syötteellä " + toS(luvut));
        } catch (ArithmeticException e) {
            if (toS(luvut).equals("-1")) {
                fail("ohjelmasi tekee nollallajaon kun syötteenä on pelkkä -1 eli yhtään numeroa ei anneta."
                        + "\nVika lienee hyväksymisprosentin laskemisessa. Siellä tulee ottaa huomioon tilanne jossa"
                        + "yhtään syötettyä numeroa ei ole");
            } else {
                fail("ohjelmasi tekee nollallajaon syötteellä " + toS(luvut));
            }
        } catch (Exception e) {
            fail("jotain odottamatonta tapahtui syötteellä " + toS(luvut) + " lisätietoa " + e.getMessage());
        }
    }*/

    private String userInput(int... numbers) {
        String str = "";

        for (int num : numbers) {
            str += num + "\n";
        }

        return str;
    }

    private String toS(int[] numbers) {
        if (numbers.length == 0) {
            return "-1";
        }

        return Arrays.toString(numbers).replace("[", "").replace("]", "") + ", -1";
    }

    private String getRow(String[] rows, String target) {
        for (String row : rows) {
            if (row.contains(target)) {
                return row;
            }
        }

        return null;
    }
}
