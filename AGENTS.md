<!-- Satellite context file — extends the global hub (~/.claude/CLAUDE.md | ~/.pi/agent/AGENTS.md). Host-neutral; project-specific only. Do not duplicate hub standards here. -->

# IntelliJ LUMOS Plugin

> IntelliJ IDEA / Rust Rover plugin providing LUMOS language support via the `lumos-lsp` server.

**Ecosystem context:** See [getlumos/lumos/AGENTS.md](https://github.com/getlumos/lumos/blob/main/AGENTS.md) for the LUMOS ecosystem overview, cross-repo standards, and shared guidelines.

## Architecture

- `src/main/kotlin/com/lumos/LumosLanguage.kt` - Language definition
- `src/main/kotlin/com/lumos/LumosFileType.kt` - File type registration
- `src/main/kotlin/com/lumos/LumosLspServerDescriptor.kt` - LSP client integration
- `src/main/kotlin/com/lumos/LumosParserDefinition.kt` - Minimal parser (LSP handles actual parsing)
- `src/main/resources/META-INF/plugin.xml` - Plugin manifest
- `build.gradle.kts` - Gradle build configuration

## Common Commands

```bash
./gradlew buildPlugin    # Build plugin → build/distributions/intellij-lumos-0.1.0.zip
./gradlew runIde         # Run plugin in IDE sandbox
./gradlew test          # Run tests
```

## Installation & Testing

### Option 1: Marketplace
```
Settings → Plugins → Marketplace → Search "LUMOS" → Install
```

### Option 2: From Disk
```
1. Build: ./gradlew buildPlugin
2. Settings → Plugins → ⚙️ → Install Plugin from Disk
3. Select: build/distributions/intellij-lumos-0.1.0.zip
4. Restart IDE
```

### Prerequisites
- Install LUMOS Language Server: `cargo install lumos-lsp`
- LSP server must be in PATH or `~/.cargo/bin/`

## Testing Checklist

- [ ] Plugin recognizes `.lumos` files
- [ ] Syntax highlighting active for `.lumos` files
- [ ] LSP server starts automatically when opening `.lumos` file
- [ ] Auto-completion works (type `Pub` → suggests `PublicKey`)
- [ ] Diagnostics show errors (invalid syntax → red squiggles)
- [ ] Hover information displays (hover over type → shows documentation)

## Publishing to JetBrains Marketplace

```bash
export PUBLISH_TOKEN=<your-jetbrains-marketplace-token>
./gradlew publishPlugin
```

**Marketplace URL:** https://plugins.jetbrains.com/plugin/XXXXX-lumos

## Dependencies

### LSP Client
- **Plugin ID:** `com.redhat.devtools.lsp4ij` (version 0.0.2+) — provides LSP client infrastructure for IntelliJ

### LUMOS LSP Server
- **Package:** `lumos-lsp` (Rust crate) — install via `cargo install lumos-lsp` (v0.1.1+)

## Target IDEs

✅ IntelliJ IDEA Community (2024.1+), IntelliJ IDEA Ultimate (2024.1+), Rust Rover (2024.1+), CLion (2024.1+)

## Gotchas

- **LSP server must be installed separately:** Plugin doesn't bundle lumos-lsp
- **Path detection:** Plugin checks `$CARGO_HOME/bin/lumos-lsp` first, then PATH
- **Plugin reload:** After installing, restart IDE for changes to take effect
- **LSP logs:** Check `Help → Show Log in Finder` for LSP communication logs
- **Version compatibility:** `sinceBuild=241` (2024.1+), `untilBuild=243.*` (2024.3.x)

## Build Properties

| Property | Value |
|----------|-------|
| Plugin ID | `com.lumos.intellij` |
| Version | `0.1.0` |
| IntelliJ Platform | Community Edition 2024.1 |
| Java Target | 17 |
| Kotlin Version | 1.9.21 |

## Troubleshooting

### LSP server not starting
No auto-completion, no diagnostics → check `which lumos-lsp`; install via `cargo install lumos-lsp`; verify `lumos-lsp --version` (0.1.1+); check IDE log via `Help → Show Log in Finder`.

### Plugin not recognized after installation
Ensure IntelliJ IDEA 2024.1+; confirm LSP4IJ plugin is installed (`Settings → Plugins → Installed`); restart IDE completely.

### File type not recognized (`.lumos` opens as plain text)
Right-click `.lumos` file → Associate with File Type → LUMOS; restart IDE.