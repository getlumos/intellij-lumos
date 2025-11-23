package com.lumos

import com.intellij.testFramework.fixtures.BasePlatformTestCase
import org.junit.Test

class LumosLanguageTest : BasePlatformTestCase() {

    @Test
    fun testLanguageDisplayName() {
        assertEquals("LUMOS", LumosLanguage.displayName)
    }

    @Test
    fun testLanguageId() {
        assertEquals("LUMOS", LumosLanguage.id)
    }

    @Test
    fun testLanguageCaseSensitivity() {
        assertTrue(LumosLanguage.isCaseSensitive)
    }

    @Test
    fun testLanguageIsRegistered() {
        // Verify language is properly registered with IntelliJ
        val language = com.intellij.lang.Language.findLanguageByID("LUMOS")
        assertNotNull(language)
        assertEquals(LumosLanguage, language)
    }

    @Test
    fun testLanguageFileTypeAssociation() {
        // Test that language is associated with file type
        assertEquals(LumosLanguage, LumosFileType.INSTANCE.language)
    }

    @Test
    fun testLanguageSingleton() {
        // Verify LumosLanguage is a singleton object
        val language1 = LumosLanguage
        val language2 = LumosLanguage
        assertSame(language1, language2)
    }

    override fun getTestDataPath(): String {
        return "src/test/resources/testData"
    }
}
