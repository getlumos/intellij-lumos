package com.lumos

import com.intellij.testFramework.ParsingTestCase
import org.junit.Test

class LumosParserDefinitionTest : ParsingTestCase("", "lumos", LumosParserDefinition()) {

    @Test
    fun testParserDefinitionCreatesLexer() {
        val parserDefinition = LumosParserDefinition()
        val lexer = parserDefinition.createLexer(project)
        assertNotNull(lexer)
    }

    @Test
    fun testParserDefinitionCreatesParser() {
        val parserDefinition = LumosParserDefinition()
        val parser = parserDefinition.createParser(project)
        assertNotNull(parser)
    }

    @Test
    fun testParserDefinitionFileNodeType() {
        val parserDefinition = LumosParserDefinition()
        val fileNodeType = parserDefinition.fileNodeType
        assertNotNull(fileNodeType)
        assertEquals(LumosLanguage, fileNodeType.language)
    }

    @Test
    fun testParserDefinitionCommentTokens() {
        val parserDefinition = LumosParserDefinition()
        val commentTokens = parserDefinition.commentTokens
        assertNotNull(commentTokens)
        assertTrue(commentTokens.isEmpty)
    }

    @Test
    fun testParserDefinitionStringLiteralElements() {
        val parserDefinition = LumosParserDefinition()
        val stringLiterals = parserDefinition.stringLiteralElements
        assertNotNull(stringLiterals)
        assertTrue(stringLiterals.isEmpty)
    }

    @Test
    fun testParserDefinitionCreatesPsiFile() {
        val content = """
            #[solana]
            struct Player {
                wallet: PublicKey,
                score: u64,
            }
        """.trimIndent()

        val file = createPsiFile("test", content)
        assertNotNull(file)
        assertTrue(file is LumosPsiFile)
        assertEquals(LumosFileType.INSTANCE, file.fileType)
    }

    @Test
    fun testParserDefinitionHandlesEmptyFile() {
        val file = createPsiFile("empty", "")
        assertNotNull(file)
        assertTrue(file is LumosPsiFile)
    }

    @Test
    fun testParserDefinitionHandlesSimpleStruct() {
        val content = "struct Player { }"
        val file = createPsiFile("simple", content)
        assertNotNull(file)
        assertTrue(file is LumosPsiFile)
    }

    override fun getTestDataPath(): String {
        return "src/test/resources/testData"
    }

    override fun skipSpaces(): Boolean {
        return false
    }

    override fun includeRanges(): Boolean {
        return true
    }
}
