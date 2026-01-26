plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "2.3.0"
    id("org.jetbrains.intellij") version "1.17.4"
}

group = "com.lumos"
version = "0.1.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.mockito:mockito-core:5.21.0")
    testImplementation("org.mockito.kotlin:mockito-kotlin:6.2.2")
    testImplementation("org.junit.jupiter:junit-jupiter:6.0.2")
}

intellij {
    version.set("2024.1")
    type.set("IC") // IntelliJ IDEA Community Edition

    // Include LSP plugin dependency - latest stable version
    plugins.set(listOf("com.redhat.devtools.lsp4ij:0.9.0"))
}

tasks {
    withType<JavaCompile> {
        sourceCompatibility = "17"
        targetCompatibility = "17"
    }

    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
        }
    }

    patchPluginXml {
        sinceBuild.set("241")
        untilBuild.set("243.*")

        pluginDescription.set("""
            LUMOS language support for IntelliJ IDEA, Rust Rover, and CLion.

            <p>Write once in <code>.lumos</code> schema language → Generate type-safe Rust + TypeScript for Solana development.</p>

            <h3>Features</h3>
            <ul>
              <li><strong>File Type Recognition</strong> - .lumos files automatically recognized</li>
              <li><strong>Syntax Highlighting</strong> - Full token coverage with customizable colors</li>
              <li><strong>Auto-Completion</strong> - Smart suggestions for types and attributes</li>
              <li><strong>Hover Documentation</strong> - Inline type information and descriptions</li>
              <li><strong>LSP Integration</strong> - Powered by LUMOS Language Server</li>
            </ul>

            <h3>Getting Started</h3>
            <ol>
              <li>Install LUMOS Language Server: <code>cargo install lumos-lsp</code></li>
              <li>Create a .lumos file in your project</li>
              <li>Start writing schemas with full IDE support!</li>
            </ol>

            <h3>Example</h3>
            <pre><code>#[solana]
#[account]
struct PlayerAccount {
    wallet: PublicKey,
    score: u64,
    level: u16,
}</code></pre>

            <p>Learn more at <a href="https://lumos-lang.org">lumos-lang.org</a></p>
        """.trimIndent())

        changeNotes.set("""
            <h3>Version 0.1.0 - Initial Release</h3>
            <ul>
              <li><strong>File Type Recognition</strong> - .lumos files automatically recognized with custom icon</li>
              <li><strong>Syntax Highlighting</strong> - Full token coverage (keywords, types, attributes, comments)</li>
              <li><strong>LSP Integration</strong> - Connected to lumos-lsp language server</li>
              <li><strong>Auto-Completion</strong> - Smart suggestions for types (PublicKey, u64, Vec, Option) and attributes</li>
              <li><strong>Hover Documentation</strong> - Type information and descriptions on hover</li>
              <li><strong>Color Customization</strong> - Customize syntax colors in Settings → Editor → Color Scheme → LUMOS</li>
              <li><strong>Multi-IDE Support</strong> - Works with IntelliJ IDEA, Rust Rover, and CLion (2024.1+)</li>
            </ul>

            <h3>Requirements</h3>
            <ul>
              <li>LUMOS Language Server: <code>cargo install lumos-lsp</code></li>
              <li>IntelliJ Platform 2024.1 or newer</li>
            </ul>
        """.trimIndent())
    }

    signPlugin {
        certificateChain.set(System.getenv("CERTIFICATE_CHAIN"))
        privateKey.set(System.getenv("PRIVATE_KEY"))
        password.set(System.getenv("PRIVATE_KEY_PASSWORD"))
    }

    publishPlugin {
        token.set(System.getenv("PUBLISH_TOKEN"))
    }

    // Disable buildSearchableOptions for faster builds
    buildSearchableOptions {
        enabled = false
    }

    test {
        useJUnitPlatform()
        testLogging {
            events("passed", "skipped", "failed")
            exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
            showStandardStreams = false
        }
    }
}
