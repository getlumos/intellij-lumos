# Screenshot Guide for JetBrains Marketplace

This guide provides step-by-step instructions for creating high-quality screenshots for the LUMOS IntelliJ plugin marketplace listing.

---

## Requirements

### Technical Specifications

| Requirement | Value |
|-------------|-------|
| **Format** | PNG (preferred) or JPEG |
| **Minimum Size** | 1280x800 pixels |
| **Recommended Size** | 1920x1080 pixels |
| **Aspect Ratio** | 16:10 or 16:9 |
| **File Size** | < 2 MB per image |
| **Quantity** | 3-5 screenshots |
| **Color Space** | sRGB |

### Quality Standards

- ✅ High resolution (no pixelation)
- ✅ Good contrast and readability
- ✅ Clean, professional appearance
- ✅ No personal information visible
- ✅ Consistent IDE theme across screenshots
- ✅ Real, working examples (not mock-ups)

---

## Setup Before Screenshots

### 1. IDE Configuration

**Recommended Theme:** Darcula (dark theme)
- Consistent with most developer preferences
- Good contrast for syntax highlighting
- Matches JetBrains marketing materials

**To set theme:**
```
Settings → Appearance & Behavior → Appearance → Theme → Darcula
```

**Alternative:** IntelliJ Light (if you prefer light theme, keep consistent across all screenshots)

### 2. Font Settings

**Recommended:**
- **Font:** JetBrains Mono or Fira Code
- **Size:** 14-16pt (for readability in screenshots)
- **Line Spacing:** 1.2

```
Settings → Editor → Font
```

### 3. Window Size

- Maximize IDE window (full screen)
- Or use fixed size: 1920x1080 minimum
- Consistent size for all screenshots

### 4. Hide Unnecessary UI

To focus on the feature being showcased:
- Hide tool windows: `Shift+Cmd+F12` (macOS) / `Shift+Ctrl+F12` (Windows/Linux)
- Keep only editor and essential UI elements visible

---

## Required Screenshots

### Screenshot 1: Syntax Highlighting ⭐ (REQUIRED)

**Purpose:** Show that .lumos files are properly recognized and highlighted

**Setup:**
1. Create or open file: `example.lumos`
2. Use this sample code:

```rust
#[solana]
#[account]
struct PlayerAccount {
    /// Player's wallet address
    wallet: PublicKey,

    /// Current score
    score: u64,

    /// Player level (1-100)
    level: u16,

    /// In-game items
    inventory: Vec<u8>,

    /// Last active timestamp
    last_active: i64,
}

#[solana]
enum GameState {
    /// Game is active
    Active,

    /// Game is paused
    Paused,

    /// Game ended with final score
    Finished(u64),
}
```

**Capture:**
- Full editor window showing syntax highlighting
- File tab showing "example.lumos" with icon
- Line numbers visible (optional)

**Focus:** Colorful syntax highlighting of keywords, types, attributes, comments

**Filename:** `1-syntax-highlighting.png`

---

### Screenshot 2: Auto-Completion ⭐ (REQUIRED)

**Purpose:** Demonstrate intelligent code completion

**Setup:**
1. Create new .lumos file
2. Start typing a struct:

```rust
#[solana]
#[account]
struct Test {
    wallet: Pub
```

3. When you type `Pub`, the completion popup should appear

**Capture the moment when:**
- Completion popup is visible
- `PublicKey` is highlighted in the suggestion list
- Other suggestions visible (if any)
- Cursor position clear

**Alternative scenarios to show:**
- Type `u` → shows `u8`, `u16`, `u32`, `u64`, `u128`
- Type `#[` → shows `#[solana]`, `#[account]`

**Filename:** `2-auto-completion.png`

---

### Screenshot 3: Error Diagnostics ⭐ (REQUIRED)

**Purpose:** Show real-time error detection

**Setup:**
1. Create .lumos file with intentional errors:

```rust
#[solana]
#[account]
struct Player {
    wallet: PublicKey,
    score: u64,
    invalid_field: UndefinedType,  // <-- This should show error
}

#[solana]
enum Status {
    Active
    Paused  // <-- Missing comma
}
```

**Capture:**
- Red squiggles under errors
- Error tooltip/hover showing (if possible)
- Multiple errors visible

**Hover over error to show tooltip** (before taking screenshot)

**Filename:** `3-error-diagnostics.png`

---

### Screenshot 4: Hover Documentation ✅ (OPTIONAL BUT RECOMMENDED)

**Purpose:** Show hover information tooltips

**Setup:**
1. Create .lumos file with well-documented types:

```rust
#[solana]
#[account]
struct Account {
    /// Wallet address of the account owner
    wallet: PublicKey,
    balance: u64,
}
```

2. Hover over `PublicKey` or other types

**Capture:**
- Tooltip/popup with documentation
- Type information clearly visible
- Readable text

**Filename:** `4-hover-documentation.png`

---

### Screenshot 5: File Icon & Project Structure ✅ (OPTIONAL)

**Purpose:** Show .lumos files in project tree with proper icon

**Setup:**
1. Create small project with multiple .lumos files:
```
project/
├── schemas/
│   ├── player.lumos
│   ├── game.lumos
│   └── inventory.lumos
└── src/
    └── lib.rs
```

2. Show project view panel (usually left sidebar)

**Capture:**
- Project tree visible
- .lumos files with LUMOS icon (if configured)
- Organized folder structure

**Filename:** `5-project-view.png`

---

## Taking Screenshots

### macOS

**Method 1: Built-in Screenshot Tool**
```bash
# Full window
Cmd+Shift+4, then press Space, click window

# Selection
Cmd+Shift+4, drag to select area
```

**Method 2: Using Preview**
1. Cmd+Shift+3 (full screen)
2. Open in Preview → Tools → Rectangular Selection → Crop

### Windows

**Method 1: Snipping Tool**
1. Search "Snipping Tool" in Start Menu
2. New → Window Snip or Rectangular Snip

**Method 2: Snip & Sketch**
```
Win+Shift+S → Select area
```

### Linux

**GNOME:**
```bash
gnome-screenshot -w  # Window
gnome-screenshot -a  # Area selection
```

**KDE:**
```bash
spectacle
```

---

## Post-Processing

### 1. Resize if Needed

If screenshot is larger than 1920x1080:

**Using ImageMagick:**
```bash
convert original.png -resize 1920x1080 resized.png
```

**Using Preview (macOS):**
1. Tools → Adjust Size
2. Set width to 1920px
3. Maintain aspect ratio

### 2. Optimize File Size

Keep under 2 MB per file.

**Using ImageOptim (macOS):**
```bash
brew install imageoptim
imageoptim *.png
```

**Using pngquant:**
```bash
pngquant --quality=80-95 screenshot.png
```

**Using online tools:**
- https://tinypng.com/
- https://squoosh.app/

### 3. Verify Quality

Check:
- [ ] Text is readable (not blurry)
- [ ] Colors are accurate
- [ ] No artifacts or compression issues
- [ ] File size < 2 MB
- [ ] Correct dimensions (1280x800 minimum)

---

## Screenshot Checklist

Before submitting screenshots to marketplace:

### Content Quality
- [ ] All code examples are syntactically correct
- [ ] No typos in code or comments
- [ ] Realistic, useful examples (not "foo", "bar")
- [ ] Professional appearance

### Technical Quality
- [ ] Resolution: 1920x1080 or 1280x800 minimum
- [ ] Format: PNG or JPEG
- [ ] File size: < 2 MB each
- [ ] No pixelation or blur

### Privacy & Security
- [ ] No personal file paths visible
- [ ] No sensitive project names
- [ ] No personal information in code
- [ ] No internal URLs or credentials

### Consistency
- [ ] Same IDE theme across all screenshots
- [ ] Same font size and family
- [ ] Consistent window size
- [ ] Similar code style

---

## Sample Code for Screenshots

### Comprehensive Example (Use for Screenshot 1)

```rust
#[solana]
#[account]
struct NFTMarketplace {
    /// Authority who can manage the marketplace
    authority: PublicKey,

    /// Fee percentage (in basis points, 100 = 1%)
    fee_basis_points: u16,

    /// Total volume traded
    total_volume: u64,

    /// Number of listings
    listing_count: u32,

    /// Marketplace status
    status: MarketplaceStatus,
}

#[solana]
enum MarketplaceStatus {
    /// Marketplace is active and accepting listings
    Active,

    /// Marketplace is paused (no new listings)
    Paused,

    /// Marketplace is closed permanently
    Closed,
}

#[solana]
#[account]
struct Listing {
    /// NFT mint address
    nft_mint: PublicKey,

    /// Seller's wallet
    seller: PublicKey,

    /// Price in lamports
    price: u64,

    /// Whether listing is active
    active: bool,
}
```

### For Completion Demo (Screenshot 2)

```rust
#[solana]
#[account]
struct Demo {
    // Type "Pub" here to trigger completion
    wallet:
}
```

### For Error Demo (Screenshot 3)

```rust
#[solana]
struct BrokenStruct {
    field1: UndefinedType,
    field2 u64  // Missing colon
    field3: PublicKey
}  // Missing comma after field2
```

---

## Storage

Save screenshots to:
```
intellij-lumos/
└── docs/
    └── screenshots/
        ├── 1-syntax-highlighting.png
        ├── 2-auto-completion.png
        ├── 3-error-diagnostics.png
        ├── 4-hover-documentation.png
        └── 5-project-view.png
```

**Add to .gitignore if screenshots are large:**
```gitignore
# docs/screenshots/*.png  # Optional: if files are very large
```

Or commit them if < 1 MB each (recommended for documentation).

---

## Uploading to Marketplace

When creating/editing plugin listing:

1. Go to plugin edit page
2. Scroll to "Screenshots" section
3. Click "Upload Screenshot"
4. Select files in order:
   - Screenshot 1 will be the "featured" image
   - Upload in the order you want them displayed
5. Add captions (optional):
   - "Syntax highlighting for .lumos files"
   - "Intelligent auto-completion"
   - "Real-time error diagnostics"
   - "Hover documentation"
   - "Project structure view"

---

## Tips for Great Screenshots

### DO ✅
- Use real, working code examples
- Show the feature clearly in action
- Use consistent IDE theme
- Maximize contrast for readability
- Include enough context (but not too much)
- Show realistic use cases

### DON'T ❌
- Don't use placeholder code like "foo", "bar", "test"
- Don't show personal or proprietary information
- Don't crop too tightly (leave some context)
- Don't mix light and dark themes
- Don't include cluttered UI or distractions
- Don't use low-quality or blurry images

---

## Example from Other Plugins

**Good examples to reference:**
1. Rust plugin screenshots
2. Python plugin screenshots
3. Go plugin screenshots

**Find them at:** https://plugins.jetbrains.com/

Look for:
- Clean, focused composition
- Clear demonstration of features
- Professional appearance

---

## Questions?

If you need help with screenshots:
- Check existing JetBrains plugins for inspiration
- Refer to [JetBrains Plugin Marketing Guidelines](https://plugins.jetbrains.com/docs/marketplace/plugin-overview-page.html)
- Ask in issue #2 on GitHub

---

**Document Version:** 1.0
**Last Updated:** 2025-11-24
**For:** intellij-lumos v0.1.0
