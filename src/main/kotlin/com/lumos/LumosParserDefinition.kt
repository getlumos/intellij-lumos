package com.lumos

import com.intellij.lang.ASTNode
import com.intellij.lang.ParserDefinition
import com.intellij.lang.PsiBuilder
import com.intellij.lang.PsiParser
import com.intellij.lexer.Lexer
import com.intellij.openapi.project.Project
import com.intellij.psi.FileViewProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.tree.IElementType
import com.intellij.psi.tree.IFileElementType
import com.intellij.psi.tree.TokenSet

/**
 * Basic parser definition for LUMOS files.
 *
 * Note: Actual parsing is handled by the LSP server (lumos-lsp).
 * This is a minimal implementation to satisfy IntelliJ's requirements.
 */
class LumosParserDefinition : ParserDefinition {
    override fun createLexer(project: Project?): Lexer {
        return LumosLexer()
    }

    override fun createParser(project: Project?): PsiParser {
        return LumosParser()
    }

    override fun getFileNodeType(): IFileElementType {
        return FILE
    }

    override fun getCommentTokens(): TokenSet {
        return COMMENTS
    }

    override fun getStringLiteralElements(): TokenSet {
        return TokenSet.EMPTY
    }

    override fun createElement(node: ASTNode?): PsiElement {
        return LumosPsiElement(node!!)
    }

    override fun createFile(viewProvider: FileViewProvider): PsiFile {
        return LumosPsiFile(viewProvider)
    }

    companion object {
        val FILE = IFileElementType(LumosLanguage)
        val COMMENTS = TokenSet.EMPTY
    }
}

// Minimal lexer implementation
class LumosLexer : com.intellij.lexer.LexerBase() {
    override fun start(buffer: CharSequence, startOffset: Int, endOffset: Int, initialState: Int) {}
    override fun getState(): Int = 0
    override fun getTokenType(): com.intellij.psi.tree.IElementType? = null
    override fun getTokenStart(): Int = 0
    override fun getTokenEnd(): Int = 0
    override fun advance() {}
    override fun getBufferSequence(): CharSequence = ""
    override fun getBufferEnd(): Int = 0
}

// Minimal parser implementation
class LumosParser : PsiParser {
    override fun parse(root: IElementType, builder: PsiBuilder): ASTNode {
        val marker = builder.mark()
        while (!builder.eof()) {
            builder.advanceLexer()
        }
        marker.done(root)
        return builder.treeBuilt
    }
}

// Minimal PSI element
class LumosPsiElement(node: ASTNode) : com.intellij.extapi.psi.ASTWrapperPsiElement(node)

// PSI file
class LumosPsiFile(viewProvider: FileViewProvider) :
    com.intellij.extapi.psi.PsiFileBase(viewProvider, LumosLanguage) {
    override fun getFileType() = LumosFileType
}
