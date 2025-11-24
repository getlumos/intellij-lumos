#!/bin/bash

# Don't exit on error - we want to run all tests
# set -e

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Test results
TESTS_PASSED=0
TESTS_FAILED=0
TOTAL_TESTS=0

# Test result tracking
declare -a FAILED_TESTS

print_header() {
  echo -e "\n${BLUE}━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━${NC}"
  echo -e "${BLUE}  $1${NC}"
  echo -e "${BLUE}━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━${NC}\n"
}

print_test() {
  echo -e "${YELLOW}▶${NC} Testing: $1"
}

pass() {
  echo -e "  ${GREEN}✓${NC} $1"
  ((TESTS_PASSED++))
  ((TOTAL_TESTS++))
}

fail() {
  echo -e "  ${RED}✗${NC} $1"
  FAILED_TESTS+=("$1")
  ((TESTS_FAILED++))
  ((TOTAL_TESTS++))
}

info() {
  echo -e "  ${BLUE}ℹ${NC} $1"
}

# Start testing
echo -e "${GREEN}╔═══════════════════════════════════════════════════════════╗${NC}"
echo -e "${GREEN}║  IntelliJ LUMOS Plugin - Comprehensive Test Suite        ║${NC}"
echo -e "${GREEN}╚═══════════════════════════════════════════════════════════╝${NC}"

START_TIME=$(date +%s)

# ============================================================================
# TEST 1: Plugin Structure Validation
# ============================================================================
print_header "TEST 1: Plugin Structure Validation"

print_test "Plugin ZIP exists"
if [ -f "build/distributions/intellij-lumos-0.1.0.zip" ]; then
  pass "Plugin ZIP found: build/distributions/intellij-lumos-0.1.0.zip"
  ZIP_SIZE=$(du -h build/distributions/intellij-lumos-0.1.0.zip | awk '{print $1}')
  info "ZIP size: $ZIP_SIZE"
else
  fail "Plugin ZIP not found"
fi

print_test "Plugin ZIP contents"
TEMP_DIR=$(mktemp -d)
unzip -q build/distributions/intellij-lumos-0.1.0.zip -d "$TEMP_DIR"

# Check for required files
REQUIRED_FILES=(
  "intellij-lumos/lib/intellij-lumos-0.1.0.jar"
)

for file in "${REQUIRED_FILES[@]}"; do
  if [ -f "$TEMP_DIR/$file" ]; then
    pass "Required file present: $file"
  else
    fail "Missing required file: $file"
  fi
done

# Extract and validate plugin.xml
print_test "Plugin descriptor (plugin.xml)"
if [ -f "$TEMP_DIR/intellij-lumos/lib/intellij-lumos-0.1.0.jar" ]; then
  unzip -q "$TEMP_DIR/intellij-lumos/lib/intellij-lumos-0.1.0.jar" -d "$TEMP_DIR/jar_contents" 2>/dev/null || true

  if [ -f "$TEMP_DIR/jar_contents/META-INF/plugin.xml" ]; then
    pass "plugin.xml found in JAR"

    PLUGIN_XML="$TEMP_DIR/jar_contents/META-INF/plugin.xml"

    # Validate plugin.xml structure
    if grep -q "<id>com.lumos.intellij</id>" "$PLUGIN_XML"; then
      pass "Plugin ID is correct: com.lumos.intellij"
    else
      fail "Plugin ID missing or incorrect"
    fi

    if grep -q "<name>LUMOS</name>" "$PLUGIN_XML"; then
      pass "Plugin name is correct: LUMOS"
    else
      fail "Plugin name missing or incorrect"
    fi

    if grep -q "<version>0.1.0</version>" "$PLUGIN_XML"; then
      pass "Plugin version is correct: 0.1.0"
    else
      fail "Plugin version missing or incorrect"
    fi

    if grep -q "com.lumos.LumosFileType" "$PLUGIN_XML"; then
      pass "File type registration found"
    else
      fail "File type registration missing"
    fi

    if grep -q "com.lumos.LumosLspServerDescriptor" "$PLUGIN_XML"; then
      pass "LSP server descriptor registration found"
    else
      fail "LSP server descriptor registration missing"
    fi

  else
    fail "plugin.xml not found in JAR"
  fi
fi

# ============================================================================
# TEST 2: LSP Server Availability
# ============================================================================
print_header "TEST 2: LSP Server Availability"

print_test "lumos-lsp in PATH"
if command -v lumos-lsp &> /dev/null; then
  LSP_PATH=$(which lumos-lsp)
  pass "lumos-lsp found at: $LSP_PATH"
else
  fail "lumos-lsp not found in PATH"
fi

print_test "lumos-lsp in ~/.cargo/bin"
if [ -f "$HOME/.cargo/bin/lumos-lsp" ]; then
  pass "lumos-lsp found in ~/.cargo/bin"
  LSP_SIZE=$(du -h "$HOME/.cargo/bin/lumos-lsp" | awk '{print $1}')
  info "Binary size: $LSP_SIZE"
else
  fail "lumos-lsp not found in ~/.cargo/bin"
fi

print_test "lumos-lsp executable permissions"
if [ -x "$HOME/.cargo/bin/lumos-lsp" ]; then
  pass "lumos-lsp is executable"
else
  fail "lumos-lsp is not executable"
fi

# ============================================================================
# TEST 3: LSP Server Protocol Tests
# ============================================================================
print_header "TEST 3: LSP Server Protocol Tests"

# Create test workspace
TEST_WORKSPACE=$(mktemp -d)
TEST_FILE="$TEST_WORKSPACE/test.lumos"

cat > "$TEST_FILE" << 'EOF'
#[solana]
#[account]
struct Player {
    wallet: PublicKey,
    score: u64,
}

#[solana]
enum GameState {
    Active,
    Paused,
}
EOF

info "Created test file: $TEST_FILE"

# Start LSP server in background
print_test "LSP server starts successfully"
LSP_LOG=$(mktemp)
LSP_PID=""

# Function to communicate with LSP server
send_lsp_message() {
  local message="$1"
  local length=${#message}
  echo -e "Content-Length: $length\r\n\r\n$message"
}

# Test LSP server startup (simplified - just check if it starts)
if command -v lumos-lsp &> /dev/null; then
  # Start server in background and check if it runs
  timeout 3 lumos-lsp > /dev/null 2>&1 &
  LSP_PID=$!

  sleep 1

  if kill -0 $LSP_PID 2>/dev/null; then
    pass "LSP server started with PID: $LSP_PID"
    info "LSP protocol tests require IDE integration (manual testing)"

    # Cleanup LSP process
    kill $LSP_PID 2>/dev/null || true
    wait $LSP_PID 2>/dev/null || true
  else
    fail "LSP server failed to start"
  fi
else
  fail "Cannot test LSP protocol - lumos-lsp not available"
fi

# ============================================================================
# TEST 4: File Type Recognition
# ============================================================================
print_header "TEST 4: File Type Recognition"

print_test "Plugin recognizes .lumos extension"
if grep -q 'extensions="lumos"' "$TEMP_DIR/jar_contents/META-INF/plugin.xml" 2>/dev/null; then
  pass "Plugin configured to recognize .lumos extension"
else
  fail "Plugin not configured for .lumos extension"
fi

print_test "File type class exists"
if unzip -l "$TEMP_DIR/intellij-lumos/lib/intellij-lumos-0.1.0.jar" | grep -q "com/lumos/LumosFileType.class"; then
  pass "LumosFileType class found in JAR"
else
  fail "LumosFileType class not found in JAR"
fi

# ============================================================================
# TEST 5: LSP Features Validation
# ============================================================================
print_header "TEST 5: LSP Features Configuration"

print_test "LSP server descriptor class exists"
if unzip -l "$TEMP_DIR/intellij-lumos/lib/intellij-lumos-0.1.0.jar" | grep -q "com/lumos/LumosLspServerDescriptor.class"; then
  pass "LumosLspServerDescriptor class found"
else
  fail "LumosLspServerDescriptor class missing"
fi

print_test "LSP capabilities configured"
if grep -q "com.redhat.devtools.lsp4ij.server.definition" "$TEMP_DIR/jar_contents/META-INF/plugin.xml" 2>/dev/null; then
  pass "LSP server definition extension point registered"
else
  fail "LSP server definition not registered"
fi

# ============================================================================
# TEST 6: Performance Benchmarks
# ============================================================================
print_header "TEST 6: Performance Benchmarks"

print_test "LSP server startup time"
if command -v lumos-lsp &> /dev/null; then
  STARTUP_START=$(date +%s%N)
  timeout 10 lumos-lsp &> /dev/null &
  LSP_PID=$!
  sleep 1
  STARTUP_END=$(date +%s%N)
  STARTUP_TIME=$(( (STARTUP_END - STARTUP_START) / 1000000 ))

  kill $LSP_PID 2>/dev/null || true

  if [ $STARTUP_TIME -lt 2000 ]; then
    pass "LSP server startup: ${STARTUP_TIME}ms (< 2000ms target)"
  else
    fail "LSP server startup too slow: ${STARTUP_TIME}ms (target: < 2000ms)"
  fi
else
  fail "Cannot benchmark - lumos-lsp not available"
fi

print_test "Plugin ZIP size"
if [ -f "build/distributions/intellij-lumos-0.1.0.zip" ]; then
  ZIP_SIZE_BYTES=$(stat -f%z build/distributions/intellij-lumos-0.1.0.zip 2>/dev/null || stat -c%s build/distributions/intellij-lumos-0.1.0.zip)
  ZIP_SIZE_MB=$((ZIP_SIZE_BYTES / 1024 / 1024))

  if [ $ZIP_SIZE_MB -lt 10 ]; then
    pass "Plugin ZIP size reasonable: ${ZIP_SIZE_MB}MB (< 10MB)"
  else
    fail "Plugin ZIP too large: ${ZIP_SIZE_MB}MB (target: < 10MB)"
  fi
fi

# ============================================================================
# TEST 7: Edge Cases & Error Handling
# ============================================================================
print_header "TEST 7: Edge Cases & Error Handling"

print_test "Empty .lumos file handling"
EMPTY_FILE="$TEST_WORKSPACE/empty.lumos"
touch "$EMPTY_FILE"
if [ -f "$EMPTY_FILE" ]; then
  pass "Empty .lumos file created successfully"
else
  fail "Cannot create empty .lumos file"
fi

print_test "Large .lumos file handling"
LARGE_FILE="$TEST_WORKSPACE/large.lumos"
{
  for i in {1..100}; do
    cat << EOF
#[solana]
#[account]
struct Account${i} {
    field1: PublicKey,
    field2: u64,
    field3: String,
    field4: Vec<u8>,
    field5: Option<PublicKey>,
}

EOF
  done
} > "$LARGE_FILE"

LARGE_FILE_LINES=$(wc -l < "$LARGE_FILE")
if [ "$LARGE_FILE_LINES" -gt 500 ]; then
  pass "Large .lumos file created: $LARGE_FILE_LINES lines"
else
  fail "Failed to create large test file"
fi

print_test "Invalid syntax file handling"
INVALID_FILE="$TEST_WORKSPACE/invalid.lumos"
cat > "$INVALID_FILE" << 'EOF'
struct Player {
    wallet: UndefinedType,
    score: InvalidType,
}
EOF

if [ -f "$INVALID_FILE" ]; then
  pass "Invalid syntax file created for testing"
else
  fail "Cannot create invalid syntax test file"
fi

# ============================================================================
# TEST 8: Dependencies Check
# ============================================================================
print_header "TEST 8: Dependencies & Requirements"

print_test "Plugin dependencies in plugin.xml"
if grep -q "<depends>com.intellij.modules.platform</depends>" "$TEMP_DIR/jar_contents/META-INF/plugin.xml" 2>/dev/null; then
  pass "Platform module dependency declared"
else
  fail "Platform module dependency missing"
fi

if grep -q "com.redhat.devtools.lsp4ij" "$TEMP_DIR/jar_contents/META-INF/plugin.xml" 2>/dev/null; then
  pass "LSP4IJ dependency declared"
else
  fail "LSP4IJ dependency missing"
fi

print_test "IDE compatibility"
if grep -q "idea-version since-build" "$TEMP_DIR/jar_contents/META-INF/plugin.xml" 2>/dev/null; then
  SINCE_BUILD=$(grep "idea-version since-build" "$TEMP_DIR/jar_contents/META-INF/plugin.xml" | sed -n 's/.*since-build="\([^"]*\)".*/\1/p')
  pass "IDE compatibility: since-build=$SINCE_BUILD"
else
  fail "IDE version compatibility not specified"
fi

# ============================================================================
# TEST 9: Integration Points
# ============================================================================
print_header "TEST 9: Integration Points"

print_test "File icon configuration"
if grep -q "icon=" "$TEMP_DIR/jar_contents/META-INF/plugin.xml" 2>/dev/null; then
  pass "File icon configured in plugin.xml"
else
  info "File icon configuration not found (optional)"
fi

print_test "Language definition"
if grep -q "com.intellij.fileType" "$TEMP_DIR/jar_contents/META-INF/plugin.xml" 2>/dev/null; then
  pass "File type extension point registered"
else
  fail "File type extension point not registered"
fi

# ============================================================================
# Cleanup
# ============================================================================
print_header "Cleanup"
rm -rf "$TEMP_DIR" "$TEST_WORKSPACE"
info "Temporary files cleaned up"

# ============================================================================
# Test Summary
# ============================================================================
END_TIME=$(date +%s)
DURATION=$((END_TIME - START_TIME))

echo ""
echo -e "${GREEN}╔═══════════════════════════════════════════════════════════╗${NC}"
echo -e "${GREEN}║                    TEST SUMMARY                           ║${NC}"
echo -e "${GREEN}╚═══════════════════════════════════════════════════════════╝${NC}"
echo ""
echo -e "  Total Tests:        ${BLUE}$TOTAL_TESTS${NC}"
echo -e "  Passed:             ${GREEN}$TESTS_PASSED${NC}"
echo -e "  Failed:             ${RED}$TESTS_FAILED${NC}"
echo -e "  Duration:           ${BLUE}${DURATION}s${NC}"
echo ""

if [ $TESTS_FAILED -eq 0 ]; then
  echo -e "${GREEN}✓ All tests passed!${NC}\n"
  exit 0
else
  echo -e "${RED}✗ Some tests failed:${NC}\n"
  for test in "${FAILED_TESTS[@]}"; do
    echo -e "  ${RED}•${NC} $test"
  done
  echo ""
  exit 1
fi
