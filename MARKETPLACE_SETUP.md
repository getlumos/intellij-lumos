# JetBrains Marketplace Publishing Guide

This guide walks through publishing the LUMOS IntelliJ plugin to the JetBrains Marketplace.

---

## Prerequisites

✅ **Already Complete:**
- [x] Plugin developed and tested
- [x] GitHub Actions CI/CD configured
- [x] Gradle publishing task configured
- [x] Plugin description and change notes in `build.gradle.kts`

⏳ **Need to Complete:**
- [ ] JetBrains Marketplace account
- [ ] Marketplace listing created
- [ ] Screenshots prepared
- [ ] GitHub secret configured

---

## Step 1: Create JetBrains Marketplace Account

### 1.1 Register Account

1. Go to https://plugins.jetbrains.com/
2. Click **Sign In** (top right)
3. Choose sign-in method:
   - **GitHub** (recommended)
   - JetBrains Account
   - Google
   - Email

4. Complete registration and verify email

### 1.2 Complete Developer Profile

1. After sign-in, click your profile icon → **Developer Settings**
2. Fill in required information:
   - **Display Name:** LUMOS Language
   - **Email:** rz1989s@gmail.com
   - **Website:** https://lumos-lang.org
   - **Bio:** (optional) Brief description

---

## Step 2: Generate Marketplace Token

### 2.1 Create Access Token

1. Go to https://plugins.jetbrains.com/author/me/tokens
2. Click **Generate New Token**
3. Fill in details:
   - **Name:** `intellij-lumos-ci`
   - **Scope:** Select **Marketplace**
   - **Expiration:** Choose duration (recommend: 1 year)

4. Click **Generate Token**
5. **IMPORTANT:** Copy the token immediately - it won't be shown again!

### 2.2 Save Token Securely

**Copy the token and save it temporarily** - you'll need it in Step 3.

Example format:
```
perm:XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
```

---

## Step 3: Add GitHub Secret

### 3.1 Navigate to Repository Settings

1. Go to https://github.com/getlumos/intellij-lumos
2. Click **Settings** (repository settings, not your profile)
3. In left sidebar: **Secrets and variables** → **Actions**

### 3.2 Create New Secret

1. Click **New repository secret**
2. Fill in:
   - **Name:** `JETBRAINS_MARKETPLACE_TOKEN`
   - **Secret:** Paste the token from Step 2
3. Click **Add secret**

### 3.3 Verify Secret

The secret should now appear in the list as:
```
JETBRAINS_MARKETPLACE_TOKEN
```

**Note:** You won't be able to view the value after creation (GitHub security).

---

## Step 4: Prepare Plugin Listing

### 4.1 Plugin Metadata

The following is already configured in `build.gradle.kts`:

| Field | Value |
|-------|-------|
| **Plugin ID** | com.lumos.intellij |
| **Name** | LUMOS |
| **Version** | 0.1.0 |
| **Description** | See build.gradle.kts lines 42-62 |
| **Change Notes** | See build.gradle.kts lines 64-76 |

### 4.2 Additional Marketplace Info

You'll need to provide these when creating the marketplace listing:

**Category:**
- Primary: **Languages**
- Tags: `solana`, `schema`, `borsh`, `rust`, `typescript`, `blockchain`, `lsp`

**License:**
- Dual-licensed: MIT OR Apache-2.0
- License files in repository: `LICENSE-MIT`, `LICENSE-APACHE`

**Links:**
- **Website:** https://lumos-lang.org
- **Documentation:** https://docs.lumos-lang.org/editors/intellij
- **Source Code:** https://github.com/getlumos/intellij-lumos
- **Issue Tracker:** https://github.com/getlumos/intellij-lumos/issues
- **Changelog:** https://github.com/getlumos/intellij-lumos/blob/main/CHANGELOG.md

**Support:**
- **Email:** rz1989s@gmail.com
- **GitHub Issues:** Preferred support channel

---

## Step 5: Prepare Screenshots

### 5.1 Screenshot Requirements

**Specifications:**
- **Format:** PNG or JPEG
- **Size:** 1280x800 or 1920x1080 (recommended: 1920x1080)
- **Quantity:** 3-5 screenshots
- **Content:** Show key features in action

**Required Screenshots:**

1. **Syntax Highlighting** (REQUIRED)
   - Show `.lumos` file with colorized syntax
   - Include struct and enum examples
   - Light OR dark theme (or both)

2. **Auto-Completion** (REQUIRED)
   - Screenshot showing completion popup
   - Example: Typing `Pub` suggests `PublicKey`
   - Show relevant suggestions

3. **Error Diagnostics** (REQUIRED)
   - Red squiggles under invalid syntax
   - Hover tooltip showing error message
   - Clear error indication

4. **Hover Documentation** (OPTIONAL)
   - Tooltip showing type information
   - Documentation text visible

5. **Project View** (OPTIONAL)
   - `.lumos` files in project tree
   - File icons visible

### 5.2 Screenshot Naming

Save screenshots as:
```
1-syntax-highlighting.png
2-auto-completion.png
3-diagnostics.png
4-hover-documentation.png
5-project-view.png
```

**Store in:** `docs/screenshots/` directory

### 5.3 Screenshot Checklist

Before taking screenshots:
- [ ] Use latest plugin version (0.1.0)
- [ ] Clean, readable code examples
- [ ] No personal information visible
- [ ] Consistent IDE theme (recommend: Darcula for consistency)
- [ ] IDE window maximized
- [ ] Good contrast and readability

**See:** `SCREENSHOTS.md` for detailed guide

---

## Step 6: Create Marketplace Listing

### 6.1 Manual Upload (First Release)

**Option A: Upload via Website** (Recommended for first release)

1. Go to https://plugins.jetbrains.com/plugin/add
2. Click **Upload Plugin**
3. Select: `build/distributions/intellij-lumos-0.1.0.zip`

**Fill in listing details:**

| Field | Value |
|-------|-------|
| Name | LUMOS |
| Category | Languages |
| Tags | solana, schema, borsh, rust, typescript, blockchain, lsp |
| Short Description | Type-safe schema language support for Solana development |
| Description | (Auto-populated from plugin.xml / build.gradle.kts) |
| Icon | Upload `src/main/resources/META-INF/pluginIcon.svg` (if exists) |
| Screenshots | Upload 3-5 screenshots from docs/screenshots/ |

4. Click **Submit for Review**

### 6.2 Automated Publishing (Future Releases)

Once initial listing is created, GitHub Actions will handle future releases automatically.

**To publish new version:**

```bash
# 1. Update version in build.gradle.kts
version = "0.2.0"

# 2. Update change notes in build.gradle.kts
changeNotes.set("""
    <h3>Version 0.2.0</h3>
    <ul>
      <li>New feature X</li>
      <li>Bug fix Y</li>
    </ul>
""".trimIndent())

# 3. Commit and push
git add build.gradle.kts
git commit -m "chore: Bump version to 0.2.0"
git push

# 4. Create GitHub release
gh release create v0.2.0 \
  --title "v0.2.0 - Release Name" \
  --notes "See CHANGELOG.md" \
  --repo getlumos/intellij-lumos

# 5. GitHub Actions will automatically:
#    - Build plugin
#    - Run tests
#    - Publish to JetBrains Marketplace (using JETBRAINS_MARKETPLACE_TOKEN)
```

---

## Step 7: Review Process

### 7.1 JetBrains Review Timeline

After submission, JetBrains will review the plugin:

- **Average Time:** 1-3 business days
- **Status:** Check at https://plugins.jetbrains.com/author/me/plugins

### 7.2 Review Checklist

JetBrains checks for:
- [ ] Plugin builds and installs correctly
- [ ] No malicious code
- [ ] Description is clear and accurate
- [ ] Screenshots match functionality
- [ ] Compatible with declared IDE versions
- [ ] Dependencies are properly declared

### 7.3 Handling Feedback

If issues are found:
1. JetBrains will send feedback via email
2. Make requested changes
3. Upload new version
4. Wait for re-review

**Common feedback:**
- Improve plugin description
- Add better screenshots
- Fix compatibility issues
- Update dependency versions

---

## Step 8: Post-Publishing

### 8.1 Verify Publication

Once approved:
1. Plugin appears at: https://plugins.jetbrains.com/plugin/XXXXX-lumos
2. Users can install via:
   ```
   Settings → Plugins → Marketplace → Search "LUMOS"
   ```

### 8.2 Update Documentation

After plugin is published, update:

**README.md:**
```markdown
### Installation from JetBrains Marketplace ✅

1. Open Settings → Plugins
2. Search for "LUMOS" in Marketplace
3. Click Install
4. Restart IDE

Or install directly: [LUMOS on JetBrains Marketplace](https://plugins.jetbrains.com/plugin/XXXXX-lumos)
```

**Add marketplace badge:**
```markdown
[![JetBrains Marketplace](https://img.shields.io/jetbrains/plugin/v/PLUGIN_ID.svg)](https://plugins.jetbrains.com/plugin/PLUGIN_ID)
[![Downloads](https://img.shields.io/jetbrains/plugin/d/PLUGIN_ID.svg)](https://plugins.jetbrains.com/plugin/PLUGIN_ID)
```

Replace `PLUGIN_ID` with actual ID from marketplace URL.

### 8.3 Announce Release

Post announcements on:
- [ ] Twitter/X (@lumoslang or personal)
- [ ] GitHub Discussions
- [ ] LUMOS Discord/Telegram (if exists)
- [ ] Reddit r/solana, r/rust (if appropriate)

**Announcement template:**
```
🚀 LUMOS IntelliJ Plugin v0.1.0 is now available!

IntelliJ IDEA & Rust Rover support for LUMOS schema language.

✅ Syntax highlighting
✅ Auto-completion
✅ Real-time diagnostics
✅ LSP integration

Install: Settings → Plugins → Search "LUMOS"

🔗 https://plugins.jetbrains.com/plugin/XXXXX-lumos
📖 https://lumos-lang.org

#Solana #Rust #TypeScript #IDE
```

### 8.4 Monitor Metrics

Track plugin adoption:
- **Downloads:** https://plugins.jetbrains.com/plugin/XXXXX-lumos/versions
- **Ratings:** Encourage users to rate/review
- **Issues:** Monitor GitHub issues for bug reports

---

## Troubleshooting

### Token Not Working

**Error:** `401 Unauthorized` during publish

**Fix:**
1. Verify token is correctly set in GitHub Secrets
2. Check token hasn't expired
3. Regenerate token if needed
4. Ensure token has **Marketplace** scope

### Build Fails During Publish

**Error:** Gradle publish task fails

**Fix:**
1. Test locally first:
   ```bash
   export PUBLISH_TOKEN="your-token"
   ./gradlew publishPlugin
   ```
2. Check logs for specific error
3. Verify plugin.xml is valid
4. Ensure version isn't already published

### Plugin Not Found in Marketplace

**Scenario:** Plugin published but not searchable

**Fix:**
- Wait 10-15 minutes for indexing
- Check plugin status at author dashboard
- Ensure plugin is set to "Public" not "Private"
- Verify IDE version compatibility

---

## Quick Reference

### Commands

```bash
# Build plugin locally
./gradlew buildPlugin

# Verify plugin structure
./gradlew verifyPlugin

# Test publish (dry run)
export PUBLISH_TOKEN="your-token"
./gradlew publishPlugin --dry-run

# Actual publish (manual)
./gradlew publishPlugin

# Create GitHub release (triggers auto-publish)
gh release create v0.1.0 --repo getlumos/intellij-lumos
```

### Links

- **Marketplace:** https://plugins.jetbrains.com/
- **Token Management:** https://plugins.jetbrains.com/author/me/tokens
- **Plugin Dashboard:** https://plugins.jetbrains.com/author/me/plugins
- **Documentation:** https://plugins.jetbrains.com/docs/marketplace/

---

## Summary Checklist

Before publishing, ensure:

- [ ] JetBrains account created
- [ ] Marketplace token generated
- [ ] GitHub secret `JETBRAINS_MARKETPLACE_TOKEN` configured
- [ ] Screenshots prepared (3-5 high-quality images)
- [ ] Plugin tested manually (see TEST_REPORT.md)
- [ ] build.gradle.kts version and change notes updated
- [ ] All tests passing in CI
- [ ] Plugin description is clear and accurate
- [ ] License files present

**Ready to publish!** 🚀

---

**Document Version:** 1.0
**Last Updated:** 2025-11-24
**For:** intellij-lumos v0.1.0
