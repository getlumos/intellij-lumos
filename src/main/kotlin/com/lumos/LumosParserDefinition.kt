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

/**
 * Lexer for LUMOS language.
 * Tokenizes LUMOS source code for syntax highlighting.
 */
class LumosLexer : com.intellij.lexer.LexerBase() {
    private var buffer: CharSequence = ""
    private var startOffset: Int = 0
    private var endOffset: Int = 0
    private var currentOffset: Int = 0
    private var currentTokenType: IElementType? = null
    private var currentTokenStart: Int = 0
    private var currentTokenEnd: Int = 0
    private var attributeDepth: Int = 0  // Track if we're inside attributes

    override fun start(buffer: CharSequence, startOffset: Int, endOffset: Int, initialState: Int) {
        this.buffer = buffer
        this.startOffset = startOffset
        this.endOffset = endOffset
        this.currentOffset = startOffset
        this.attributeDepth = 0
        advance()
    }

    override fun getState(): Int = 0

    override fun getTokenType(): IElementType? = currentTokenType

    override fun getTokenStart(): Int = currentTokenStart

    override fun getTokenEnd(): Int = currentTokenEnd

    override fun advance() {
        if (currentOffset >= endOffset) {
            currentTokenType = null
            return
        }

        currentTokenStart = currentOffset
        val char = buffer[currentOffset]

        // Whitespace
        if (char.isWhitespace()) {
            advanceWhile { it.isWhitespace() }
            currentTokenType = LumosTokenTypes.WHITESPACE
            return
        }

        // Line comment
        if (char == '/' && peek() == '/') {
            advanceUntil { it == '\n' }
            currentTokenType = LumosTokenTypes.LINE_COMMENT
            return
        }

        // Block comment
        if (char == '/' && peek() == '*') {
            currentOffset += 2
            while (currentOffset < endOffset - 1) {
                if (buffer[currentOffset] == '*' && buffer[currentOffset + 1] == '/') {
                    currentOffset += 2
                    break
                }
                currentOffset++
            }
            currentTokenEnd = currentOffset
            currentTokenType = LumosTokenTypes.BLOCK_COMMENT
            return
        }

        // Attributes
        if (char == '#' && peek() == '[') {
            currentOffset += 2
            currentTokenEnd = currentOffset
            currentTokenType = LumosTokenTypes.ATTRIBUTE_START
            attributeDepth++  // Entering attribute
            return
        }

        if (char == ']' && inAttribute()) {
            currentOffset++
            currentTokenEnd = currentOffset
            currentTokenType = LumosTokenTypes.ATTRIBUTE_END
            attributeDepth--  // Exiting attribute
            return
        }

        // String literals
        if (char == '"') {
            currentOffset++
            while (currentOffset < endOffset) {
                val c = buffer[currentOffset]
                if (c == '\\') {
                    currentOffset += 2
                } else if (c == '"') {
                    currentOffset++
                    break
                } else {
                    currentOffset++
                }
            }
            currentTokenEnd = currentOffset
            currentTokenType = LumosTokenTypes.STRING
            return
        }

        // Numbers
        if (char.isDigit() || (char == '0' && currentOffset + 1 < endOffset)) {
            if (char == '0' && peek() == 'x') {
                currentOffset += 2
                advanceWhile { it.isDigit() || it in 'a'..'f' || it in 'A'..'F' || it == '_' }
                currentTokenType = LumosTokenTypes.HEX_NUMBER
                return
            }
            if (char == '0' && peek() == 'b') {
                currentOffset += 2
                advanceWhile { it == '0' || it == '1' || it == '_' }
                currentTokenType = LumosTokenTypes.BINARY_NUMBER
                return
            }
            if (char == '0' && peek() == 'o') {
                currentOffset += 2
                advanceWhile { it in '0'..'7' || it == '_' }
                currentTokenType = LumosTokenTypes.OCTAL_NUMBER
                return
            }
            advanceWhile { it.isDigit() || it == '_' || it == '.' || it == 'e' || it == 'E' || it == '+' || it == '-' }
            currentTokenType = LumosTokenTypes.NUMBER
            return
        }

        // Operators
        if (char in "<>+-*/%=!&|") {
            advanceWhile { it in "<>+-*/%=!&|:" }
            currentTokenType = LumosTokenTypes.OPERATOR
            return
        }

        // Punctuation
        when (char) {
            ',' -> {
                currentOffset++
                currentTokenEnd = currentOffset
                currentTokenType = LumosTokenTypes.COMMA
                return
            }
            ';' -> {
                currentOffset++
                currentTokenEnd = currentOffset
                currentTokenType = LumosTokenTypes.SEMICOLON
                return
            }
            ':' -> {
                currentOffset++
                currentTokenEnd = currentOffset
                currentTokenType = LumosTokenTypes.COLON
                return
            }
            '{' -> {
                currentOffset++
                currentTokenEnd = currentOffset
                currentTokenType = LumosTokenTypes.LBRACE
                return
            }
            '}' -> {
                currentOffset++
                currentTokenEnd = currentOffset
                currentTokenType = LumosTokenTypes.RBRACE
                return
            }
            '(' -> {
                currentOffset++
                currentTokenEnd = currentOffset
                currentTokenType = LumosTokenTypes.LPAREN
                return
            }
            ')' -> {
                currentOffset++
                currentTokenEnd = currentOffset
                currentTokenType = LumosTokenTypes.RPAREN
                return
            }
            '[' -> {
                currentOffset++
                currentTokenEnd = currentOffset
                currentTokenType = LumosTokenTypes.LBRACKET
                return
            }
            ']' -> {
                currentOffset++
                currentTokenEnd = currentOffset
                currentTokenType = LumosTokenTypes.RBRACKET
                return
            }
            '<' -> {
                currentOffset++
                currentTokenEnd = currentOffset
                currentTokenType = LumosTokenTypes.LT
                return
            }
            '>' -> {
                currentOffset++
                currentTokenEnd = currentOffset
                currentTokenType = LumosTokenTypes.GT
                return
            }
        }

        // Identifiers and keywords
        if (char.isLetter() || char == '_') {
            advanceWhile { it.isLetterOrDigit() || it == '_' }
            val text = buffer.substring(currentTokenStart, currentOffset)

            currentTokenEnd = currentOffset
            currentTokenType = when {
                text in LumosTokenTypes.KEYWORDS -> LumosTokenTypes.KEYWORD
                text in LumosTokenTypes.STORAGE_MODIFIERS -> LumosTokenTypes.STORAGE_MODIFIER
                text in LumosTokenTypes.PRIMITIVE_TYPES -> LumosTokenTypes.PRIMITIVE_TYPE
                text in LumosTokenTypes.SOLANA_TYPES -> LumosTokenTypes.SOLANA_TYPE
                text in LumosTokenTypes.OPTION_TYPES -> LumosTokenTypes.OPTION_TYPE
                text in LumosTokenTypes.ATTRIBUTE_NAMES && inAttribute() -> LumosTokenTypes.ATTRIBUTE_NAME
                text.first().isUpperCase() -> LumosTokenTypes.CUSTOM_TYPE
                else -> LumosTokenTypes.IDENTIFIER
            }
            return
        }

        // Unknown character
        currentOffset++
        currentTokenEnd = currentOffset
        currentTokenType = LumosTokenTypes.BAD_CHARACTER
    }

    override fun getBufferSequence(): CharSequence = buffer

    override fun getBufferEnd(): Int = endOffset

    private fun peek(): Char? {
        return if (currentOffset + 1 < endOffset) buffer[currentOffset + 1] else null
    }

    private fun advanceWhile(predicate: (Char) -> Boolean) {
        while (currentOffset < endOffset && predicate(buffer[currentOffset])) {
            currentOffset++
        }
        currentTokenEnd = currentOffset
    }

    private fun advanceUntil(predicate: (Char) -> Boolean) {
        while (currentOffset < endOffset && !predicate(buffer[currentOffset])) {
            currentOffset++
        }
        currentTokenEnd = currentOffset
    }

    private fun inAttribute(): Boolean {
        // Check if we're currently inside attribute brackets
        return attributeDepth > 0
    }
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
