package com.duanyifu.conceal

import com.intellij.lang.ASTNode
import com.intellij.lang.folding.FoldingDescriptor
import com.intellij.openapi.editor.FoldingGroup
import com.intellij.openapi.util.TextRange

/**
 * @author yifuduan on 2022/5/10
 */
class GoFoldingDescriptor(
    node: ASTNode,
    range: TextRange,
    group: FoldingGroup?,
    val paramList: String,
    val notExpandable: Boolean
) : FoldingDescriptor(node, range, group) {

    override fun getPlaceholderText(): String = "($paramList) =>"

    override fun isNonExpandable(): Boolean = notExpandable
}