
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import java.lang.reflect.Method;
import org.junit.*;
import static org.junit.Assert.*;

@Points("01-36")
public class LeapYearTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void recognizesCorrectYears() {
        int[] years = {1600, 1604, 1608, 1612, 1616, 1620, 1624, 1628, 1632, 1636, 1640, 1644, 1648, 1652, 1656, 1660, 1664, 1668, 1672, 1676, 1680, 1684, 1688, 1692, 1696, 1704, 1708, 1712, 1716, 1720, 1724, 1728, 1732, 1736, 1740, 1744, 1748, 1752, 1756, 1760, 1764, 1768, 1772, 1776, 1780, 1784, 1788, 1792, 1796, 1804, 1808, 1812, 1816, 1820, 1824, 1828, 1832, 1836, 1840, 1844, 1848, 1852, 1856, 1860, 1864, 1868, 1872, 1876, 1880, 1884, 1888, 1892, 1896, 1904, 1908, 1912, 1916, 1920, 1924, 1928, 1932, 1936, 1940, 1944, 1948, 1952, 1956, 1960, 1964, 1968, 1972, 1976, 1980, 1984, 1988, 1992, 1996, 2000, 2004, 2008, 2012, 2016, 2020, 2024, 2028, 2032, 2036, 2040, 2044, 2048, 2052, 2056, 2060, 2064, 2068, 2072, 2076, 2080, 2084, 2088, 2092, 2096, 2104, 2108};

        for (int year : years) {
            regodnizesCorrect(year);
        }
    }

    @Test
    public void failsIncorrectYears() {
        for (int year = 1600; year < 2108; year++) {
            if (leapYear(year)) {
                continue;
            }
            failsIncorrect(year);
        }
    }

    private void regodnizesCorrect(int year) {
        int oldOut = io.getSysOut().length();
        io.setSysIn(year + "\n");
        callMain(LeapYear.class);
        String out = io.getSysOut().substring(oldOut);

        assertTrue("The program had no output!", out.length() > 0);
        assertTrue("With input " + year + " output was \"" + out + "\" ", out.toLowerCase().contains("is a leap"));
        assertTrue("With input " + year + " output was \"" + out + "\" ", !out.toLowerCase().contains("is not"));
    }

    private void failsIncorrect(int year) {
        int oldOut = io.getSysOut().length();
        io.setSysIn(year + "\n");
        callMain(LeapYear.class);
        String out = io.getSysOut().substring(oldOut);

        assertTrue("The program had no output!", out.length() > 0);
        assertTrue("With input " + year + " output was \"" + out + "\" ", !out.toLowerCase().contains("is a leap"));
        assertTrue("With input " + year + " output was \"" + out + "\" ", out.toLowerCase().contains("is not"));
    }

    private void callMain(Class kl) {
        try {
            kl = ReflectionUtils.newInstanceOfClass(kl);
            String[] t = null;
            String x[] = new String[0];
            Method m = ReflectionUtils.requireMethod(kl, "main", x.getClass());
            ReflectionUtils.invokeMethod(Void.TYPE, m, null, (Object) x);
        } catch (Throwable e) {
            fail("Something weird happened. It could be that " + kl + "-class public static void main(String[] args) -method has dissappeared\n"
                    + "or your program failed with an error. More information:" + e);
        }
    }

    private boolean leapYear(int year) {
        int[] leapYears = {1600, 1604, 1608, 1612, 1616, 1620, 1624, 1628, 1632, 1636, 1640, 1644, 1648, 1652, 1656, 1660, 1664, 1668, 1672, 1676, 1680, 1684, 1688, 1692, 1696, 1704, 1708, 1712, 1716, 1720, 1724, 1728, 1732, 1736, 1740, 1744, 1748, 1752, 1756, 1760, 1764, 1768, 1772, 1776, 1780, 1784, 1788, 1792, 1796, 1804, 1808, 1812, 1816, 1820, 1824, 1828, 1832, 1836, 1840, 1844, 1848, 1852, 1856, 1860, 1864, 1868, 1872, 1876, 1880, 1884, 1888, 1892, 1896, 1904, 1908, 1912, 1916, 1920, 1924, 1928, 1932, 1936, 1940, 1944, 1948, 1952, 1956, 1960, 1964, 1968, 1972, 1976, 1980, 1984, 1988, 1992, 1996, 2000, 2004, 2008, 2012, 2016, 2020, 2024, 2028, 2032, 2036, 2040, 2044, 2048, 2052, 2056, 2060, 2064, 2068, 2072, 2076, 2080, 2084, 2088, 2092, 2096, 2104, 2108};

        for (int v : leapYears) {
            if (year == v) {
                return true;
            }
        }

        return false;
    }
}
