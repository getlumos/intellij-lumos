package com.lumos

import com.intellij.testFramework.fixtures.BasePlatformTestCase
import org.junit.Test

class LumosFileTypeTest : BasePlatformTestCase() {

    @Test
    fun testFileTypeRecognition() {
        // Test that .lumos files are recognized as LUMOS file type
        val file = myFixture.configureByText("test.lumos", "struct Player { }")
        assertEquals(LumosFileType.INSTANCE, file.fileType)
    }

    @Test
    fun testFileTypeName() {
        assertEquals("LUMOS", LumosFileType.INSTANCE.name)
    }

    @Test
    fun testFileTypeDescription() {
        assertEquals("LUMOS schema language file", LumosFileType.INSTANCE.description)
    }

    @Test
    fun testFileTypeDefaultExtension() {
        assertEquals("lumos", LumosFileType.INSTANCE.defaultExtension)
    }

    @Test
    fun testFileTypeIcon() {
        assertNotNull(LumosFileType.INSTANCE.icon)
    }

    @Test
    fun testFileTypeLanguage() {
        assertEquals(LumosLanguage, LumosFileType.INSTANCE.language)
    }

    @Test
    fun testFileExtensionConstant() {
        assertEquals("lumos", LumosFileType.EXTENSION)
    }

    @Test
    fun testMultipleLumosFiles() {
        // Test that multiple .lumos files are all recognized
        val file1 = myFixture.configureByText("player.lumos", "struct Player {}")
        val file2 = myFixture.configureByText("game.lumos", "enum GameState {}")

        assertEquals(LumosFileType.INSTANCE, file1.fileType)
        assertEquals(LumosFileType.INSTANCE, file2.fileType)
    }

    @Test
    fun testNonLumosFileNotRecognized() {
        // Test that non-.lumos files are not recognized as LUMOS type
        val rustFile = myFixture.configureByText("test.rs", "struct Player {}")
        assertNotEquals(LumosFileType.INSTANCE, rustFile.fileType)
    }

    override fun getTestDataPath(): String {
        return "src/test/resources/testData"
    }
}
