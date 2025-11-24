# Manual Testing Guide - IntelliJ LUMOS Plugin

This is your hands-on testing guide for Issue #1. Follow these steps in order.

---

## Prerequisites

✅ **Ready:**
- Plugin built: `build/distributions/intellij-lumos-0.1.0.zip` (1.6 MB)
- LSP server: `lumos-lsp v0.1.1` installed at `~/.cargo/bin/lumos-lsp`
- IDE: RustRover detected at `/Applications/RustRover.app`

---

## STEP 1: Install Plugin (5 minutes)

### 1.1 Open RustRover

```bash
open -a "RustRover"
```

Or open from Applications folder.

### 1.2 Install Plugin from Disk

1. Click **RustRover** → **Settings** (or **Preferences** on macOS)
2. Navigate to: **Plugins**
3. Click the **⚙️ gear icon** → **Install Plugin from Disk...**
4. Navigate to and select:
   ```
   /Users/rz/local-dev/intellij-lumos/build/distributions/intellij-lumos-0.1.0.zip
   ```
5. Click **OK**
6. Click **Restart IDE** when prompted

### 1.3 Verify Installation

After restart:
1. Go to **Settings** → **Plugins**
2. Search for "LUMOS" in the **Installed** tab
3. Verify it shows:
   - **Name:** LUMOS
   - **Version:** 0.1.0
   - **Vendor:** LUMOS Language

**✅ Check:** Plugin appears in installed list

---

## STEP 2: Create Test File (2 minutes)

### 2.1 Create Test Project/File

1. **Create new file** or use existing project
2. Right-click in project → **New** → **File**
3. Name it: `test.lumos`

### 2.2 Add Test Content

Copy and paste this into `test.lumos`:

```rust
// Test file for LUMOS plugin
#[solana]
#[account]
struct PlayerAccount {
    /// Player's wallet address
    wallet: PublicKey,

    /// Current score
    score: u64,

    /// Player level
    level: u16,

    /// Inventory items
    items: Vec<u8>,

    /// Optional email
    email: Option<String>,
}

#[solana]
enum GameState {
    /// Game is active
    Active,

    /// Game is paused
    Paused,

    /// Game finished with score
    Finished(u64),
}

/* Block comment
   for testing */
const MAX_PLAYERS: u32 = 100;
```

**✅ Check:** File created successfully

---

## STEP 3: Test Syntax Highlighting (3 minutes)

### 3.1 Visual Check

Look at your `test.lumos` file and verify these colors are different:

**Should be colored:**
- Keywords: `struct`, `enum`, `const`
- Types: `PublicKey`, `u64`, `u16`, `Vec`, `Option`, `String`, `u32`
- Attributes: `#[solana]`, `#[account]`
- Comments: `//` and `/* */`
- Numbers: `100`, `64`, `16`, `32`

**✅ Check:** Syntax highlighting works

### 3.2 Test Both Themes

**Dark theme (Darcula):**
1. Settings → Appearance & Behavior → Appearance
2. Theme: **Darcula**
3. Check colors look good

**Light theme:**
1. Same location
2. Theme: **IntelliJ Light**
3. Check colors look good

**✅ Check:** Both themes work

### 3.3 Customize Colors (Optional)

1. Settings → Editor → Color Scheme → **LUMOS**
2. Try changing a color (e.g., Keywords)
3. Verify change applies immediately

**✅ Check:** Color customization works

---

## STEP 4: Test LSP Features (10 minutes)

### 4.1 Auto-Completion

**Test 1: Type completion**
1. Create new line in struct
2. Type: `test_field: Pub`
3. **Expected:** Completion popup shows `PublicKey`
4. Select `PublicKey`

**Test 2: Primitive types**
1. New line
2. Type: `number: u`
3. **Expected:** Shows `u8`, `u16`, `u32`, `u64`, `u128`

**Test 3: Attributes**
1. New line above struct
2. Type: `#[`
3. **Expected:** Shows `#[solana]`, `#[account]`

**✅ Check:** Auto-completion working

### 4.2 Diagnostics (Error Detection)

**Test 1: Undefined type**
1. Add this line to struct:
   ```rust
   bad_field: UndefinedType,
   ```
2. **Expected:** Red squiggle under `UndefinedType`
3. Hover over it
4. **Expected:** Error message appears

**Test 2: Syntax error**
1. Remove colon from a field:
   ```rust
   score u64,  // Missing colon
   ```
2. **Expected:** Error indication

**✅ Check:** Diagnostics work

### 4.3 Hover Documentation

1. Hover over `PublicKey`
2. **Expected:** Tooltip with documentation
3. Hover over `u64`
4. **Expected:** Type information appears

**✅ Check:** Hover works

### 4.4 LSP Server Logs

1. Go to: **Help** → **Show Log in Finder**
2. Open `idea.log`
3. Search for "lumos"
4. **Expected:** See log entries like:
   ```
   LSP server started: lumos-lsp
   Connected to /Users/rz/.cargo/bin/lumos-lsp
   ```

**✅ Check:** LSP server connected

---

## STEP 5: Test Performance (5 minutes)

### 5.1 Large File Test

1. Create `large.lumos`
2. Copy-paste the test struct **50 times** (create large file)
3. Open the file
4. Type and edit

**Expected:**
- No lag when typing
- Syntax highlighting instant
- Auto-completion fast (< 200ms)

**✅ Check:** Performance good with large file

### 5.2 Multiple Files

1. Create 3-4 different `.lumos` files
2. Open all simultaneously
3. Switch between tabs

**Expected:**
- All files highlighted correctly
- No memory issues
- Switching is smooth

**✅ Check:** Multiple files work

---

## STEP 6: Test Edge Cases (5 minutes)

### 6.1 Empty File

1. Create `empty.lumos`
2. Leave it empty
3. Open it

**Expected:** No errors, no crash

**✅ Check:** Empty file handled

### 6.2 Invalid Syntax

1. Create `invalid.lumos`
2. Add garbage:
   ```
   this is not valid lumos syntax!!! 12345
   ```

**Expected:** Diagnostics show errors, but no crash

**✅ Check:** Invalid syntax handled

### 6.3 File Rename

1. Rename `test.lumos` to `test2.lumos`
2. Check file type still recognized

**Expected:** Still shows as LUMOS file

**✅ Check:** Rename preserves file type

---

## STEP 7: Document Results (5 minutes)

Fill out this checklist:

### Installation
- [ ] Plugin installs without errors
- [ ] Shows in installed plugins list
- [ ] IDE restarts successfully

### File Recognition
- [ ] `.lumos` files recognized
- [ ] File type shows as "LUMOS"
- [ ] Icon displays (if available)

### Syntax Highlighting
- [ ] Keywords highlighted
- [ ] Types highlighted
- [ ] Attributes highlighted
- [ ] Comments highlighted
- [ ] Works in Darcula theme
- [ ] Works in Light theme
- [ ] Color customization works

### LSP Features
- [ ] Auto-completion for types
- [ ] Auto-completion for attributes
- [ ] Diagnostics show errors
- [ ] Hover shows documentation
- [ ] LSP server connects in logs

### Performance
- [ ] Large files load quickly
- [ ] Multiple files work
- [ ] No lag when typing
- [ ] Auto-completion is fast

### Edge Cases
- [ ] Empty file handled
- [ ] Invalid syntax handled
- [ ] File rename works

---

## Issues Found (if any)

Document any problems here:

**Issue 1:**
- Description:
- Steps to reproduce:
- Expected vs actual:

**Issue 2:**
- Description:
- Steps to reproduce:
- Expected vs actual:

---

## Summary

**Testing Date:** ___________
**IDE:** RustRover (version: _______)
**Platform:** macOS (Darwin 24.6.0)
**Plugin Version:** 0.1.0

**Overall Result:** [ ] PASS / [ ] FAIL

**Notes:**

---

## Next Steps After Testing

1. **If all tests pass:**
   - Document results in GitHub issue #1
   - Close Issue #1 as complete
   - Proceed to Issue #2 (Marketplace publishing)

2. **If issues found:**
   - Document issues clearly
   - Create new GitHub issues for bugs
   - Fix critical issues before publishing

---

**Ready to start? Begin with STEP 1!** 🚀

**Time estimate:** 30-40 minutes total
