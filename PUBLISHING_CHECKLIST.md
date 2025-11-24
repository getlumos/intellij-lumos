# Publishing Checklist - JetBrains Marketplace

Use this checklist to track progress when publishing the LUMOS IntelliJ plugin to the JetBrains Marketplace.

---

## Pre-Publishing Checklist

### Plugin Development & Testing

- [x] Plugin developed and feature-complete
- [x] All unit tests passing
- [x] Gradle build succeeds
- [x] Plugin structure validated (verifyPlugin task)
- [x] GitHub Actions CI/CD configured
- [x] Automated test suite created (`test-plugin.sh`)
- [ ] Manual testing completed in RustRover
- [ ] Manual testing in IntelliJ IDEA (optional but recommended)
- [ ] No critical bugs reported

**Status:** Automated testing complete ✅ | Manual testing pending ⏳

---

### Documentation

- [x] README.md complete and accurate
- [x] build.gradle.kts description configured
- [x] build.gradle.kts change notes configured
- [x] License files present (LICENSE-MIT, LICENSE-APACHE)
- [x] MARKETPLACE_SETUP.md created (setup guide)
- [x] SCREENSHOTS.md created (screenshot guide)
- [x] TEST_REPORT.md created (test results)
- [ ] CHANGELOG.md updated with v0.1.0 details
- [ ] Screenshots captured (3-5 images)

**Status:** Guides complete ✅ | Screenshots pending ⏳

---

### Marketplace Account Setup

- [ ] JetBrains Marketplace account created
  - Account type: _____________ (GitHub / JetBrains / Google)
  - Display name: LUMOS Language
  - Email: rz1989s@gmail.com
  - Website: https://lumos-lang.org

- [ ] Developer profile completed
  - Bio filled in
  - Social links added (optional)

**Status:** Not started ⏳

---

### Token & Secret Configuration

- [ ] Marketplace token generated
  - Token name: `intellij-lumos-ci`
  - Scope: Marketplace
  - Expiration: _____________ (date)
  - Token saved securely: [ ]

- [ ] GitHub secret configured
  - Secret name: `JETBRAINS_MARKETPLACE_TOKEN`
  - Secret value: Added to https://github.com/getlumos/intellij-lumos/settings/secrets/actions
  - Verified in secrets list: [ ]

**Status:** Not started ⏳

---

### Screenshots

- [ ] Screenshot 1: Syntax Highlighting (REQUIRED)
  - File: `docs/screenshots/1-syntax-highlighting.png`
  - Size: 1920x1080 or 1280x800
  - Shows: Colorful syntax highlighting in .lumos file
  - Quality checked: [ ]

- [ ] Screenshot 2: Auto-Completion (REQUIRED)
  - File: `docs/screenshots/2-auto-completion.png`
  - Shows: Completion popup with PublicKey suggestion
  - Quality checked: [ ]

- [ ] Screenshot 3: Error Diagnostics (REQUIRED)
  - File: `docs/screenshots/3-error-diagnostics.png`
  - Shows: Red squiggles and error tooltips
  - Quality checked: [ ]

- [ ] Screenshot 4: Hover Documentation (OPTIONAL)
  - File: `docs/screenshots/4-hover-documentation.png`
  - Shows: Hover tooltip with type information
  - Quality checked: [ ]

- [ ] Screenshot 5: Project View (OPTIONAL)
  - File: `docs/screenshots/5-project-view.png`
  - Shows: .lumos files in project tree
  - Quality checked: [ ]

**Status:** Not started ⏳

---

### Marketplace Listing

- [ ] Plugin uploaded to marketplace
  - Method: Manual upload OR automated publish
  - Upload date: _____________

- [ ] Listing information completed:
  - [ ] Plugin name: LUMOS
  - [ ] Category: Languages
  - [ ] Tags: solana, schema, borsh, rust, typescript, blockchain, lsp
  - [ ] Short description added
  - [ ] Full description (auto from build.gradle.kts)
  - [ ] Icon uploaded (if available)
  - [ ] Screenshots uploaded (3-5 images)
  - [ ] Links added:
    - [ ] Website: https://lumos-lang.org
    - [ ] Documentation: https://docs.lumos-lang.org/editors/intellij
    - [ ] Source code: https://github.com/getlumos/intellij-lumos
    - [ ] Issue tracker: https://github.com/getlumos/intellij-lumos/issues

**Status:** Not started ⏳

---

### Review & Approval

- [ ] Plugin submitted for JetBrains review
  - Submission date: _____________
  - Confirmation email received: [ ]

- [ ] Review feedback received
  - Date: _____________
  - Feedback: (note any requested changes)

- [ ] Changes made (if requested)
  - Changes list:

- [ ] Plugin approved by JetBrains
  - Approval date: _____________
  - Approval email received: [ ]

- [ ] Plugin visible on marketplace
  - URL: https://plugins.jetbrains.com/plugin/_____-lumos
  - Searchable in IDE: [ ]

**Status:** Not started ⏳

---

### Post-Publishing

- [ ] Verify plugin installation works
  - Tested in: Settings → Plugins → Marketplace → Search "LUMOS"
  - Installs successfully: [ ]
  - Activates correctly: [ ]

- [ ] Update documentation with marketplace link
  - [ ] README.md updated
  - [ ] CLAUDE.md updated (ecosystem section)
  - [ ] Main LUMOS repo CLAUDE.md updated
  - [ ] Marketplace badge added to README

- [ ] Create v0.1.0 GitHub release
  - [ ] Release created
  - [ ] ZIP artifact attached
  - [ ] Release notes from CHANGELOG.md
  - [ ] URL: https://github.com/getlumos/intellij-lumos/releases/tag/v0.1.0

- [ ] Announcements made
  - [ ] Twitter/X post
  - [ ] GitHub Discussions
  - [ ] Discord/Telegram (if applicable)
  - [ ] Update main LUMOS repo README

- [ ] Close GitHub issues
  - [ ] Issue #1 (Testing) - after manual tests complete
  - [ ] Issue #2 (Publishing) - after successful publication

**Status:** Not started ⏳

---

## Publishing Commands

### Test Locally Before Publishing

```bash
# Build plugin
./gradlew buildPlugin

# Verify plugin structure
./gradlew verifyPlugin

# Test publish (requires token)
export PUBLISH_TOKEN="your-marketplace-token"
./gradlew publishPlugin --dry-run
```

### Manual Publish (If Needed)

```bash
# Set token
export PUBLISH_TOKEN="your-marketplace-token"

# Publish to marketplace
./gradlew publishPlugin
```

### Automated Publish (Recommended)

```bash
# 1. Ensure GitHub secret is configured
# 2. Create GitHub release
gh release create v0.1.0 \
  --repo getlumos/intellij-lumos \
  --title "v0.1.0 - Initial Release" \
  --notes-file CHANGELOG.md

# 3. GitHub Actions will automatically:
#    - Build plugin
#    - Run tests
#    - Publish to JetBrains Marketplace
```

---

## Progress Summary

| Category | Status | Progress |
|----------|--------|----------|
| Development & Testing | 🟡 Partial | Automated ✅ Manual ⏳ |
| Documentation | ✅ Complete | All guides ready |
| Marketplace Account | ⏳ Pending | Not started |
| Token Configuration | ⏳ Pending | Not started |
| Screenshots | ⏳ Pending | Not started |
| Listing Creation | ⏳ Pending | Not started |
| Review Process | ⏳ Pending | Awaiting submission |
| Post-Publishing | ⏳ Pending | After approval |

**Overall Status:** 📝 Ready for marketplace setup - documentation complete

---

## Next Steps

1. **Complete manual testing** (Issue #1)
   - Install in RustRover
   - Run through test checklist
   - Document results

2. **Create JetBrains account** (if not already done)
   - Register at https://plugins.jetbrains.com/
   - Complete developer profile

3. **Generate marketplace token**
   - Create token with Marketplace scope
   - Save securely

4. **Configure GitHub secret**
   - Add JETBRAINS_MARKETPLACE_TOKEN
   - Verify in repository settings

5. **Capture screenshots**
   - Follow SCREENSHOTS.md guide
   - Take 3-5 high-quality screenshots
   - Optimize and save to docs/screenshots/

6. **Submit plugin**
   - Upload to marketplace
   - Fill in listing details
   - Submit for review

7. **Monitor review process**
   - Watch for feedback email
   - Respond to any requests
   - Await approval

8. **Post-publication tasks**
   - Update documentation
   - Create GitHub release
   - Announce on social media
   - Close GitHub issues

---

## Timeline Estimate

| Phase | Duration | Notes |
|-------|----------|-------|
| Manual testing | 1-2 hours | Complete test checklist |
| Screenshots | 30-60 min | 3-5 screenshots |
| Account setup | 15 min | One-time setup |
| Token & secret config | 10 min | One-time setup |
| Listing creation | 30 min | Upload & fill details |
| **WAIT: Review** | 1-3 days | JetBrains review time |
| Post-publishing | 1 hour | Updates & announcements |

**Total active time:** ~3-4 hours
**Total elapsed time:** 2-4 days (including review)

---

## Resources

- **Setup Guide:** `MARKETPLACE_SETUP.md`
- **Screenshot Guide:** `SCREENSHOTS.md`
- **Test Report:** `TEST_REPORT.md`
- **Issue #2:** https://github.com/getlumos/intellij-lumos/issues/2
- **Marketplace Docs:** https://plugins.jetbrains.com/docs/marketplace/
- **Token Management:** https://plugins.jetbrains.com/author/me/tokens

---

## Questions or Issues?

If you encounter problems during publishing:

1. Check `MARKETPLACE_SETUP.md` troubleshooting section
2. Review JetBrains documentation
3. Post in GitHub issue #2
4. Contact JetBrains support: https://plugins.jetbrains.com/docs/marketplace/marketplace-support.html

---

**Document Version:** 1.0
**Last Updated:** 2025-11-24
**For:** intellij-lumos v0.1.0
**GitHub Issue:** #2
