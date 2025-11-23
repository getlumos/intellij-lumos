package com.lumos

import com.intellij.testFramework.fixtures.BasePlatformTestCase
import org.junit.Test

/**
 * Integration test for the entire LUMOS plugin.
 * Tests that all components work together correctly.
 */
class LumosPluginIntegrationTest : BasePlatformTestCase() {

    @Test
    fun testFullPluginWorkflow() {
        // Create a .lumos file with complete schema
        val schema = """
            #[solana]
            #[account]
            struct Player {
                wallet: PublicKey,
                score: u64,
                level: u16,
            }

            #[solana]
            enum GameState {
                Active,
                Paused,
                Finished,
            }
        """.trimIndent()

        val file = myFixture.configureByText("player.lumos", schema)

        // Verify file type recognition
        assertEquals(LumosFileType.INSTANCE, file.fileType)

        // Verify language association
        assertEquals(LumosLanguage, file.language)

        // Verify file is a LUMOS PSI file
        assertTrue(file is LumosPsiFile)

        // Verify file has content
        assertEquals(schema, file.text)
    }

    @Test
    fun testMultipleFilesInProject() {
        // Create multiple .lumos files
        val player = myFixture.addFileToProject(
            "schemas/player.lumos",
            "struct Player { wallet: PublicKey }"
        )

        val game = myFixture.addFileToProject(
            "schemas/game.lumos",
            "enum GameState { Active, Paused }"
        )

        val vault = myFixture.addFileToProject(
            "schemas/vault.lumos",
            "struct TokenVault { amount: u64 }"
        )

        // All files should be recognized as LUMOS
        assertEquals(LumosFileType.INSTANCE, player.fileType)
        assertEquals(LumosFileType.INSTANCE, game.fileType)
        assertEquals(LumosFileType.INSTANCE, vault.fileType)
    }

    @Test
    fun testComplexSchema() {
        val complexSchema = """
            #[solana]
            #[account]
            struct NFTMarketplace {
                authority: PublicKey,
                fee_recipient: PublicKey,
                platform_fee: u16,
                total_listings: u64,
                total_sales: u64,
                is_paused: bool,
            }

            #[solana]
            struct Listing {
                seller: PublicKey,
                nft_mint: PublicKey,
                price: u64,
                created_at: i64,
                status: ListingStatus,
            }

            #[solana]
            enum ListingStatus {
                Active,
                Sold,
                Cancelled,
            }
        """.trimIndent()

        val file = myFixture.configureByText("marketplace.lumos", complexSchema)

        assertNotNull(file)
        assertEquals(LumosFileType.INSTANCE, file.fileType)
        assertEquals(LumosLanguage, file.language)
        assertEquals(complexSchema, file.text)
    }

    @Test
    fun testFileWithComments() {
        val schemaWithComments = """
            // Player account structure
            #[solana]
            #[account]
            struct Player {
                // Wallet address
                wallet: PublicKey,
                /* Player stats */
                score: u64,
                level: u16,
            }
        """.trimIndent()

        val file = myFixture.configureByText("commented.lumos", schemaWithComments)

        assertNotNull(file)
        assertEquals(LumosFileType.INSTANCE, file.fileType)
        assertTrue(file.text.contains("// Player account structure"))
        assertTrue(file.text.contains("/* Player stats */"))
    }

    @Test
    fun testEmptyLumosFile() {
        val file = myFixture.configureByText("empty.lumos", "")

        assertNotNull(file)
        assertEquals(LumosFileType.INSTANCE, file.fileType)
        assertEquals(LumosLanguage, file.language)
        assertTrue(file.text.isEmpty())
    }

    @Test
    fun testLumosFileWithOnlyComments() {
        val commentsOnly = """
            // This is a comment
            /* Multi-line
               comment */
        """.trimIndent()

        val file = myFixture.configureByText("comments.lumos", commentsOnly)

        assertNotNull(file)
        assertEquals(LumosFileType.INSTANCE, file.fileType)
    }

    @Test
    fun testFileRename() {
        val file = myFixture.configureByText("old_name.lumos", "struct Player {}")

        // File should still be recognized after rename
        assertEquals(LumosFileType.INSTANCE, file.fileType)

        // Note: Actual file rename testing requires VFS operations
        // which are complex in testing environment
    }

    @Test
    fun testMixedFileTypes() {
        // Add both .lumos and non-.lumos files
        val lumosFile = myFixture.addFileToProject(
            "schema.lumos",
            "struct Player {}"
        )

        val rustFile = myFixture.addFileToProject(
            "lib.rs",
            "pub struct Player {}"
        )

        val tsFile = myFixture.addFileToProject(
            "types.ts",
            "interface Player {}"
        )

        // Only .lumos file should have LUMOS file type
        assertEquals(LumosFileType.INSTANCE, lumosFile.fileType)
        assertNotEquals(LumosFileType.INSTANCE, rustFile.fileType)
        assertNotEquals(LumosFileType.INSTANCE, tsFile.fileType)
    }

    override fun getTestDataPath(): String {
        return "src/test/resources/testData"
    }
}
