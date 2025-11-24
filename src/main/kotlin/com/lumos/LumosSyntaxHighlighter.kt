package com.lumos

import com.intellij.lexer.Lexer
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.openapi.editor.HighlighterColors
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase
import com.intellij.psi.tree.IElementType

/**
 * Syntax highlighter for LUMOS language.
 * Maps tokens to text attribute keys for colorization.
 */
class LumosSyntaxHighlighter : SyntaxHighlighterBase() {
    override fun getHighlightingLexer(): Lexer = LumosLexer()

    override fun getTokenHighlights(tokenType: IElementType?): Array<TextAttributesKey> {
        return when (tokenType) {
            // Keywords
            LumosTokenTypes.KEYWORD -> KEYWORD_KEYS
            LumosTokenTypes.STORAGE_MODIFIER -> MODIFIER_KEYS

            // Types
            LumosTokenTypes.PRIMITIVE_TYPE -> PRIMITIVE_TYPE_KEYS
            LumosTokenTypes.SOLANA_TYPE -> SOLANA_TYPE_KEYS
            LumosTokenTypes.OPTION_TYPE -> OPTION_TYPE_KEYS
            LumosTokenTypes.CUSTOM_TYPE -> CUSTOM_TYPE_KEYS

            // Attributes
            LumosTokenTypes.ATTRIBUTE_START,
            LumosTokenTypes.ATTRIBUTE_END -> ATTRIBUTE_BRACKETS_KEYS
            LumosTokenTypes.ATTRIBUTE_NAME -> ATTRIBUTE_NAME_KEYS

            // Literals
            LumosTokenTypes.STRING -> STRING_KEYS
            LumosTokenTypes.NUMBER,
            LumosTokenTypes.HEX_NUMBER,
            LumosTokenTypes.BINARY_NUMBER,
            LumosTokenTypes.OCTAL_NUMBER -> NUMBER_KEYS

            // Comments
            LumosTokenTypes.LINE_COMMENT,
            LumosTokenTypes.BLOCK_COMMENT -> COMMENT_KEYS

            // Operators and punctuation
            LumosTokenTypes.OPERATOR -> OPERATOR_KEYS
            LumosTokenTypes.COMMA,
            LumosTokenTypes.SEMICOLON,
            LumosTokenTypes.COLON -> PUNCTUATION_KEYS
            LumosTokenTypes.LBRACE,
            LumosTokenTypes.RBRACE -> BRACES_KEYS
            LumosTokenTypes.LPAREN,
            LumosTokenTypes.RPAREN -> PARENTHESES_KEYS
            LumosTokenTypes.LBRACKET,
            LumosTokenTypes.RBRACKET -> BRACKETS_KEYS
            LumosTokenTypes.LT,
            LumosTokenTypes.GT -> ANGLE_BRACKETS_KEYS

            // Identifiers
            LumosTokenTypes.IDENTIFIER -> IDENTIFIER_KEYS

            // Bad character
            LumosTokenTypes.BAD_CHARACTER -> BAD_CHARACTER_KEYS

            else -> EMPTY_KEYS
        }
    }

    companion object {
        // Define text attribute keys for each element type
        val KEYWORD = TextAttributesKey.createTextAttributesKey(
            "LUMOS_KEYWORD",
            DefaultLanguageHighlighterColors.KEYWORD
        )

        val MODIFIER = TextAttributesKey.createTextAttributesKey(
            "LUMOS_MODIFIER",
            DefaultLanguageHighlighterColors.KEYWORD
        )

        val PRIMITIVE_TYPE = TextAttributesKey.createTextAttributesKey(
            "LUMOS_PRIMITIVE_TYPE",
            DefaultLanguageHighlighterColors.KEYWORD
        )

        val SOLANA_TYPE = TextAttributesKey.createTextAttributesKey(
            "LUMOS_SOLANA_TYPE",
            DefaultLanguageHighlighterColors.INSTANCE_FIELD
        )

        val OPTION_TYPE = TextAttributesKey.createTextAttributesKey(
            "LUMOS_OPTION_TYPE",
            DefaultLanguageHighlighterColors.INSTANCE_FIELD
        )

        val CUSTOM_TYPE = TextAttributesKey.createTextAttributesKey(
            "LUMOS_CUSTOM_TYPE",
            DefaultLanguageHighlighterColors.INSTANCE_FIELD
        )

        val ATTRIBUTE_BRACKETS = TextAttributesKey.createTextAttributesKey(
            "LUMOS_ATTRIBUTE_BRACKETS",
            DefaultLanguageHighlighterColors.BRACKETS
        )

        val ATTRIBUTE_NAME = TextAttributesKey.createTextAttributesKey(
            "LUMOS_ATTRIBUTE_NAME",
            DefaultLanguageHighlighterColors.METADATA
        )

        val STRING = TextAttributesKey.createTextAttributesKey(
            "LUMOS_STRING",
            DefaultLanguageHighlighterColors.STRING
        )

        val NUMBER = TextAttributesKey.createTextAttributesKey(
            "LUMOS_NUMBER",
            DefaultLanguageHighlighterColors.NUMBER
        )

        val COMMENT = TextAttributesKey.createTextAttributesKey(
            "LUMOS_COMMENT",
            DefaultLanguageHighlighterColors.LINE_COMMENT
        )

        val OPERATOR = TextAttributesKey.createTextAttributesKey(
            "LUMOS_OPERATOR",
            DefaultLanguageHighlighterColors.OPERATION_SIGN
        )

        val PUNCTUATION = TextAttributesKey.createTextAttributesKey(
            "LUMOS_PUNCTUATION",
            DefaultLanguageHighlighterColors.COMMA
        )

        val BRACES = TextAttributesKey.createTextAttributesKey(
            "LUMOS_BRACES",
            DefaultLanguageHighlighterColors.BRACES
        )

        val PARENTHESES = TextAttributesKey.createTextAttributesKey(
            "LUMOS_PARENTHESES",
            DefaultLanguageHighlighterColors.PARENTHESES
        )

        val BRACKETS = TextAttributesKey.createTextAttributesKey(
            "LUMOS_BRACKETS",
            DefaultLanguageHighlighterColors.BRACKETS
        )

        val ANGLE_BRACKETS = TextAttributesKey.createTextAttributesKey(
            "LUMOS_ANGLE_BRACKETS",
            DefaultLanguageHighlighterColors.BRACKETS
        )

        val IDENTIFIER = TextAttributesKey.createTextAttributesKey(
            "LUMOS_IDENTIFIER",
            DefaultLanguageHighlighterColors.IDENTIFIER
        )

        val BAD_CHARACTER = TextAttributesKey.createTextAttributesKey(
            "LUMOS_BAD_CHARACTER",
            HighlighterColors.BAD_CHARACTER
        )

        // Arrays for convenience
        private val KEYWORD_KEYS = arrayOf(KEYWORD)
        private val MODIFIER_KEYS = arrayOf(MODIFIER)
        private val PRIMITIVE_TYPE_KEYS = arrayOf(PRIMITIVE_TYPE)
        private val SOLANA_TYPE_KEYS = arrayOf(SOLANA_TYPE)
        private val OPTION_TYPE_KEYS = arrayOf(OPTION_TYPE)
        private val CUSTOM_TYPE_KEYS = arrayOf(CUSTOM_TYPE)
        private val ATTRIBUTE_BRACKETS_KEYS = arrayOf(ATTRIBUTE_BRACKETS)
        private val ATTRIBUTE_NAME_KEYS = arrayOf(ATTRIBUTE_NAME)
        private val STRING_KEYS = arrayOf(STRING)
        private val NUMBER_KEYS = arrayOf(NUMBER)
        private val COMMENT_KEYS = arrayOf(COMMENT)
        private val OPERATOR_KEYS = arrayOf(OPERATOR)
        private val PUNCTUATION_KEYS = arrayOf(PUNCTUATION)
        private val BRACES_KEYS = arrayOf(BRACES)
        private val PARENTHESES_KEYS = arrayOf(PARENTHESES)
        private val BRACKETS_KEYS = arrayOf(BRACKETS)
        private val ANGLE_BRACKETS_KEYS = arrayOf(ANGLE_BRACKETS)
        private val IDENTIFIER_KEYS = arrayOf(IDENTIFIER)
        private val BAD_CHARACTER_KEYS = arrayOf(BAD_CHARACTER)
        private val EMPTY_KEYS = emptyArray<TextAttributesKey>()
    }
}
