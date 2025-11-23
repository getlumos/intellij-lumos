package com.lumos

import com.intellij.openapi.vfs.VirtualFile
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class LumosLspServerDescriptorTest : BasePlatformTestCase() {

    @Test
    fun testIsSupportedFileLumosExtension() {
        val descriptor = LumosLspServerDescriptor(project)

        // Mock a .lumos file
        val mockFile = mock(VirtualFile::class.java)
        `when`(mockFile.extension).thenReturn("lumos")

        assertTrue(descriptor.isSupportedFile(mockFile))
    }

    @Test
    fun testIsNotSupportedFileOtherExtension() {
        val descriptor = LumosLspServerDescriptor(project)

        // Mock a .rs file
        val mockFile = mock(VirtualFile::class.java)
        `when`(mockFile.extension).thenReturn("rs")

        assertFalse(descriptor.isSupportedFile(mockFile))
    }

    @Test
    fun testIsNotSupportedFileNullExtension() {
        val descriptor = LumosLspServerDescriptor(project)

        // Mock a file with no extension
        val mockFile = mock(VirtualFile::class.java)
        `when`(mockFile.extension).thenReturn(null)

        assertFalse(descriptor.isSupportedFile(mockFile))
    }

    @Test
    fun testCommandLineCreation() {
        val descriptor = LumosLspServerDescriptor(project)
        val commandLine = descriptor.createCommandLine()

        assertNotNull(commandLine)

        // Command should be "lumos-lsp" or a path containing "lumos-lsp"
        val exePath = commandLine.exePath
        assertTrue(
            exePath == "lumos-lsp" || exePath.contains("lumos-lsp"),
            "Expected command to be 'lumos-lsp' or contain 'lumos-lsp', got: $exePath"
        )
    }

    @Test
    fun testMultipleLumosFileSupport() {
        val descriptor = LumosLspServerDescriptor(project)

        val extensions = listOf("lumos", "LUMOS", "lumos", "lumos")
        val mockFiles = extensions.map { ext ->
            mock(VirtualFile::class.java).also {
                `when`(it.extension).thenReturn(ext)
            }
        }

        // Only lowercase "lumos" should be supported
        assertTrue(descriptor.isSupportedFile(mockFiles[0]))
        assertFalse(descriptor.isSupportedFile(mockFiles[1])) // LUMOS is uppercase
        assertTrue(descriptor.isSupportedFile(mockFiles[2]))
        assertTrue(descriptor.isSupportedFile(mockFiles[3]))
    }

    @Test
    fun testServerName() {
        val descriptor = LumosLspServerDescriptor(project)

        // Access via reflection since displayName might be protected
        val displayNameField = descriptor.javaClass.superclass
            ?.getDeclaredField("displayName")
        displayNameField?.isAccessible = true
        val displayName = displayNameField?.get(descriptor) as? String

        assertEquals("LUMOS", displayName)
    }

    override fun getTestDataPath(): String {
        return "src/test/resources/testData"
    }
}
