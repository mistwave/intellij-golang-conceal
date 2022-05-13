package com.duanyifu.conceal

import com.intellij.lang.ASTNode
import com.intellij.lang.folding.FoldingBuilder
import com.intellij.lang.folding.FoldingDescriptor
import com.intellij.openapi.editor.Document
import com.intellij.openapi.util.TextRange
import java.util.regex.Matcher
import java.util.regex.Pattern

/**
 * @author yifuduan on 2022/5/10
 */
class GoFoldingBuilder : FoldingBuilder {
    companion object {
        private val pattern = Pattern.compile("func\\(")
        fun getFoldingRangeAndParamList(text: String, matcher: Matcher): Triple<Int, Int, String> {
            val funcStart = matcher.start()
            val leftParenthesis = matcher.end()
            var rightParenthesis = leftParenthesis
            while (text[rightParenthesis] != ')') {
                rightParenthesis++
            }
            return Triple(funcStart, rightParenthesis + 1, text.substring(leftParenthesis, rightParenthesis))
        }
    }

    override fun buildFoldRegions(node: ASTNode, document: Document): Array<FoldingDescriptor> {
        val res = ArrayList<FoldingDescriptor>()
        val text = node.text
        val matcher = pattern.matcher(text)

        while (matcher.find()) {
            val nodeRange = node.textRange

            val (rangeStart, rangeEnd, paramList) = getFoldingRangeAndParamList(text, matcher)
            val range = TextRange.create(
                rangeStart + nodeRange.startOffset,
                rangeEnd + nodeRange.startOffset
            )

            res.add(
                GoFoldingDescriptor(
                    node, range, null, paramList, true
                )
            )
        }
        return res.toArray(arrayOfNulls(res.size))
    }

    override fun getPlaceholderText(node: ASTNode): String? = null

    override fun isCollapsedByDefault(node: ASTNode): Boolean = true

}