package rationals

import org.junit.Assert
import org.junit.Test
import rationals.TestRationals.ComparisonOperation.*

class TestRationals {

    private fun testNormalizedForm(rational: String, normalizedForm: String) {
        Assert.assertEquals("Wrong normalized form for $rational",
                normalizedForm, rational.toRational().toString())
    }

    private fun testArithmeticOperation(first: String, operationRepresentation: String, second: String,
                                        expectedResult: String, operation: Rational.(Rational) -> Rational) {
        Assert.assertEquals("""Wrong result for $first $operationRepresentation $second""",
                expectedResult, first.toRational().operation(second.toRational()).toString())
    }

    private enum class ComparisonOperation(val symbols: String) {
        EQUAL("=="), LESS("<"), MORE(">"), LESS_OR_EQUAL("<="), MORE_OR_EQUAL(">=")
    }

    private fun testComparison(
            first: String,
            comparison: ComparisonOperation,
            second: String,
            expectedResult: Boolean
    ) {
        val firstRational = first.toRational()
        val secondRational = second.toRational()
        val result = when (comparison) {
            EQUAL -> firstRational == secondRational
            LESS -> firstRational < secondRational
            MORE -> firstRational > secondRational
            LESS_OR_EQUAL -> firstRational <= secondRational
            MORE_OR_EQUAL -> firstRational >= secondRational
        }
        Assert.assertEquals("Wrong result for $first ${comparison.symbols} $second",
                expectedResult, result)
    }

    private fun testInRange(element: String, start: String, end: String, expected: Boolean = true) {
        Assert.assertEquals("Wrong result for $element in $start..$end",
                expected, element.toRational() in start.toRational()..end.toRational())
    }

    @Test
    fun test0Sample1Sum() {
        val sum: Rational = (1 divBy 2) + (1 divBy 3)
        Assert.assertEquals("Wrong result for sum", 5 divBy 6, sum)
    }

    @Test
    fun test0Sample2Difference() {
        val difference: Rational = (1 divBy 2) - (1 divBy 3)
        Assert.assertEquals("Wrong result for difference", 1 divBy 6, difference)
    }

    @Test
    fun test0Sample3Product() {
        val product: Rational = (1 divBy 2) * (1 divBy 3)
        Assert.assertEquals("Wrong result for product", 1 divBy 6, product)
    }

    @Test
    fun test0Sample4Quotient() {
        val quotient: Rational = (1 divBy 2) / (1 divBy 3)
        Assert.assertEquals("Wrong result for quotient", 3 divBy 2, quotient)
    }

    @Test
    fun test0Sample5Negation() {
        val negation: Rational = -(1 divBy 2)
        Assert.assertEquals("Wrong result for negation", -1 divBy 2, negation)
    }

    @Test
    fun test0Sample6Integer() {
        Assert.assertEquals("Wrong string representation for integer number",
                "2", (2 divBy 1).toString())
    }

    @Test
    fun test0Sample7NormalizedForm() {
        Assert.assertEquals("Wrong normalized form for '-2 divBy 4'",
                "-1/2", (-2 divBy 4).toString())
        Assert.assertEquals("Wrong normalized form for '117/1098'",
                "13/122", "117/1098".toRational().toString())
    }

    @Test
    fun test0Sample8Comparison() {
        Assert.assertTrue("Wrong result for comparison", (1 divBy 2) < (2 divBy 3))
    }

    @Test
    fun test0Sample9InRange() {
        Assert.assertTrue("Wrong result for checking belonging to a range",
                (1 divBy 2) in (1 divBy 3)..(2 divBy 3))
    }

    @Test
    fun test0Sample10Long() {
        Assert.assertEquals("Wrong result for normalization of '2000000000L divBy 4000000000L'",
                1 divBy 2, 2000000000L divBy 4000000000L)
    }

    @Test
    fun test0Sample11BigInteger() {
        Assert.assertEquals("Wrong result for normalization of\n" +
                "\"912016490186296920119201192141970416029\".toBigInteger() divBy\n" +
                "\"1824032980372593840238402384283940832058\".toBigInteger()",
                1 divBy 2,
                "912016490186296920119201192141970416029".toBigInteger() divBy
                        "1824032980372593840238402384283940832058".toBigInteger())
    }

    @Test
    fun test1Normalized0() = testNormalizedForm("48/30", "8/5")

    @Test
    fun test1Normalized1() = testNormalizedForm("26/56", "13/28")

    @Test
    fun test1Normalized2() = testNormalizedForm("6/3", "2")

    @Test
    fun test1Normalized3() = testNormalizedForm("5670711258187766016096/1017819969418316977248", "39/7")

    @Test
    fun test1Normalized4() = testNormalizedForm("-578136305229133309744/-966904753430936619984", "461/771")

    @Test
    fun test1Normalized5() = testNormalizedForm("31/-541", "-31/541")

    @Test
    fun test2Arithmetic0() = testArithmeticOperation("86/1", "+", "64/16", "90", Rational::plus)

    @Test
    fun test2Arithmetic1() = testArithmeticOperation("17/94", "-", "59/87", "-4067/8178", Rational::minus)

    @Test
    fun test2Arithmetic2() = testArithmeticOperation("6/70", "*", "5/2", "3/14", Rational::times)

    @Test
    fun test2Arithmetic3() = testArithmeticOperation("21/4", "/", "4/44", "231/4", Rational::div)

    @Test
    fun test2Arithmetic4() = testArithmeticOperation("828099487587993325537/44002379163849686934", "+", "597728771407450572129/542645811175759848891", "17617266896778903272923516079952426936739/884359508704835805965897828865092484822", Rational::plus)

    @Test
    fun test2Arithmetic5() = testArithmeticOperation("17311206/15881920", "-", "349928488/277922736", "-8267885027/48896076960", Rational::minus)

    @Test
    fun test2Arithmetic6() = testArithmeticOperation("2339496978/2849004564", "*", "6292023/4423077", "46121885693/39483160566", Rational::times)

    @Test
    fun test2Arithmetic7() = testArithmeticOperation("-159839855495465822788/224723091004279289771", "/", "2199321524829342798673/4174073637464826992623", "-7781380285275554790183890063972035564/5764317318107961130856061713789364263", Rational::div)

    @Test
    fun test3Comparison0() = testComparison("1/2", LESS, "1/3", false)

    @Test
    fun test3Comparison1() = testComparison("20325830850349869048604856908", MORE, "-9192901948302584358938698", true)

    @Test
    fun test3Comparison2() = testComparison("-1042438361047144366998/59812037109262381713", EQUAL, "1076615241954175969826/-61773005685895342531", true)

    @Test
    fun test3Comparison3() = testComparison("-1042438361047144366998/59812037109262381713", MORE_OR_EQUAL, "-1076615241954175969826/61773005685895342531", true)

    @Test
    fun test3Comparison4() = testComparison("259877352047152420620/1282471666315533247320", LESS_OR_EQUAL, "1461828422172186708965/7213993515334933226490", true)

    @Test
    fun test3Comparison5() = testComparison("259877352047152420620/1282471666315533247320", LESS, "1461828422172186708965/7213993515334933226490", false)

    @Test
    fun test3Comparison6() = testComparison("87077/297895", LESS, "40687/138970", true)

    @Test
    fun test3Comparison7() = testComparison("6548172/6479656", MORE, "132455/130414", false)

    @Test
    fun test3Comparison8() = testComparison("95676047876718598058/21886075122687372173", LESS, "719390507052102557245/53591306636978481949", true)

    @Test
    fun test3Comparison9() = testComparison("-48673102662562360478/2261578507553402607", LESS_OR_EQUAL, "-3238921919908420813980/150495361935138744892", true)

    @Test
    fun test3Comparison10() = testComparison("17/382231", EQUAL, "-17/382231", false)

    @Test
    fun test4InRange0() = testInRange("0/68", "0/85", "1/100")

    @Test
    fun test4InRange1() = testInRange("95/8", "516/46", "1101/92")

    @Test
    fun test4InRange2() = testInRange("339/685", "273/562", "277/281")

    @Test
    fun test4InRange3() = testInRange("20395802948019459839003802001190283020/" +
            "32493205934869548609023910932454365628", "1/2", "2/3")

    @Test
    fun test4InRange4() = testInRange("10/527", "37/431", "505/862", false)

    @Test
    fun test4InRange5() = testInRange("2/295", "5/125", "27/50", false)

    @Test
    fun test4InRange6() = testInRange("687/969", "33/275", "31/50", false)
}