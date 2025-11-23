plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "2.0.0"
    id("org.jetbrains.intellij") version "1.17.4"
}

group = "com.lumos"
version = "0.1.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.mockito:mockito-core:5.8.0")
    testImplementation("org.mockito.kotlin:mockito-kotlin:5.2.1")
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.1")
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
        kotlinOptions.jvmTarget = "17"
    }

    patchPluginXml {
        sinceBuild.set("241")
        untilBuild.set("243.*")

        pluginDescription.set("""
            LUMOS language support for IntelliJ IDEA and Rust Rover.

            <h3>Features</h3>
            <ul>
              <li>Syntax highlighting for .lumos files</li>
              <li>Auto-completion for types and attributes</li>
              <li>Real-time error diagnostics</li>
              <li>Hover documentation</li>
              <li>Integration with LUMOS Language Server (lumos-lsp)</li>
            </ul>

            <h3>Getting Started</h3>
            <ol>
              <li>Install LUMOS CLI: <code>cargo install lumos-lsp</code></li>
              <li>Open a .lumos file to activate language support</li>
              <li>Start coding with full IDE assistance!</li>
            </ol>

            <p>Learn more at <a href="https://lumos-lang.org">lumos-lang.org</a></p>
        """.trimIndent())

        changeNotes.set("""
            <h3>Version 0.1.0</h3>
            <ul>
              <li>Initial release</li>
              <li>File type recognition for .lumos files</li>
              <li>LSP client integration with lumos-lsp server</li>
              <li>Syntax highlighting</li>
              <li>Auto-completion</li>
              <li>Real-time diagnostics</li>
              <li>Hover information</li>
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
