#!/usr/bin/env bash
set -euo pipefail
shopt -s nullglob

STAMP="$(date +%Y-%m-%d_%H-%M-%S)"
BLD_DIR="unit-tests_${STAMP}"
DEBUG_LEVEL="${1:-0}"

# Compile all sources (safer than only RegexValidator.java)
javac ./*.java

mkdir -p "$BLD_DIR"

# Clean stray editor backups
rm -f ./unit-test_*~

tests=(./unit-test_*)
if (( ${#tests[@]} == 0 )); then
  echo "No unit-test_* files found."
  exit 0
fi

for file in "${tests[@]}"; do
  base="$(basename "$file")"

  in="${BLD_DIR}/${base}-input.txt"
  exp="${BLD_DIR}/${base}-expected.txt"
  act="${BLD_DIR}/${base}-actual.txt"
  report="${BLD_DIR}/${base}-REPORT.txt"

  # First line (regex) → input file
  line1="$(awk 'NR==1 {print; exit}' "$file")"
  printf "%s\n" "$line1" > "$in"

  # Remaining lines → split into input strings / expected results
  awk -F "\"*,\"*" 'NR>1 {print $1}' "$file" >> "$in"
  awk -F "\"*,\"*" 'NR>1 {print $2}' "$file"  > "$exp"

  # Run with chosen debug level
  java RegexValidator "$in" "$DEBUG_LEVEL" > "$act"
  java RegexValidator "$in" "$DEBUG_LEVEL" > "$report"

  if cmp --silent "$act" "$exp"; then
    echo "[${base}]: PASSED"
  else
    echo "[${base}]: FAILED"
    # Re-run (same level) and keep report details
    java RegexValidator "$in" "$DEBUG_LEVEL" > "$report"
    echo "See ${report} for details"
  fi
done
