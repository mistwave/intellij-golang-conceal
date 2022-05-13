package com.duanyifu.conceal

import org.junit.Test
import java.util.regex.Pattern
import kotlin.test.assertEquals

/**
 *
 * @author yifuduan on 2022/5/13
 */
internal class GoFoldingBuilderTest {
    @Test
    fun testMatcherWithNoParameters() {
        val symbolPattern = Pattern.compile("func\\(")
        val text = "\tf3 := func() int {\n" +
                "\t\tprintln(\"f3\")\n" +
                "\t\treturn 1\n" +
                "\t}"

        val matcher = symbolPattern.matcher(text)
        if (matcher.find()) {
            val (rangeStart, rangeEnd, name) = GoFoldingBuilder.getFoldingRangeAndParamList(text, matcher)

            assertEquals("", name)
            assertEquals("func()", text.substring(rangeStart, rangeEnd))
        }
    }
    @Test
    fun testMatcherWithParameters() {
        val symbolPattern = Pattern.compile("func\\(")
        val text = "\tf3 := func(a int, b string) int {\n" +
                "\t\tprintln(\"f3\")\n" +
                "\t\treturn a + 1\n" +
                "\t}"

        val matcher = symbolPattern.matcher(text)
        if (matcher.find()) {
            val (rangeStart, rangeEnd, name) = GoFoldingBuilder.getFoldingRangeAndParamList(text, matcher)

            assertEquals("a int, b string", name)
            assertEquals("func(a int, b string)", text.substring(rangeStart, rangeEnd))
        }
    }
}