# Regex → NFA → DFA Validator

A Java implementation of automata construction and validation from regular expressions.  

## Summary

This program converts regular expressions into nondeterministic finite automata (NFA), then into deterministic finite automata (DFA), and validates input strings against the resulting automata. It includes end-to-end parsing, automaton construction, minimization, and a command-line interface for testing. Comprehensive unit tests demonstrate correctness and maintainability.

## Implementation

```csharp
Regex (string)
      │
      ▼
[REScanner] ──► [REParser]
      │              │
      │              ▼
      │        RE* grammar objects
      │              │
      ▼              ▼
         [NFABuilder] ──► NFAStateMachine
                               │
                               ▼
                    [NFAtoDFAConverter] ──► DFAStateMachine
                                               │
                                               ▼
                                        [DFAStringChecker]
                                               │
                                               ▼
                               "Yes (Accepted)" / "No (Rejected)"
```

### Program execution flows through four major steps  

1. **Parse** a regular expression into grammar objects  
2. **Build** an NFA from the parsed grammar  
3. **Convert** the NFA into an equivalent DFA  
4. **Validate** input strings against the DFA, printing `Yes (Accepted)` or `No (Rejected)`  

### Major Components  

- **`RegexValidator.java` (Main)**  
  Entry point. Handles command-line arguments, validates input/output filenames, and coordinates all major procedures. Displays detailed usage instructions if errors are detected.  

- **`REParser.java` / `REScanner.java`**  
  Use a recursive-descent approach with a context-free grammar to parse regular expressions into typed objects (`RE*` classes).  

- **`NFABuilder.java`**  
  Consumes parsed regex objects to build an `NFAStateMachine`.  

- **`NFAtoDFAConverter.java`**  
  Converts an `NFAStateMachine` into an equivalent `DFAStateMachine` (accepts the same language).  

- **`DFAStringChecker.java`**  
  Validates input strings against the DFA and outputs Accepted/Rejected results.  

### Supporting Classes  

- **`NFAStateMachine.java` / `DFAStateMachine.java`** – Represent automata state machines  
- **`FAState.java`** – Represents an individual automaton state (used by NFA and DFA)  
- **`RE.java`** – Represents a regular expression object  
- **`REPathComponent.java`** – Represents a single path component in a regex (e.g., `"ab"` → path components `"a"` then `"b"`)  
- **`RE[GrammarType].java` classes** – Represent specific grammar types in the regex definition  

### Why Design Decisions Matter

This organization mirrors the **theoretical pipeline** (Regex → NFA → DFA → Validation), making it easy to trace each step from parsing through execution. It also demonstrates structured software design: **single-responsibility classes, separation of concerns, and clear interfaces**.  

## Context-Free Grammar Definition

The following CFG defines the subset of regular expressions supported by this project:

```csharp
EXPR_SINGLE      → RETerm
EXPR_COMPOUND    → REExpr '|' REExpr

TERM_SYMBOL      → RESymb
TERM_GROUP       → REGroup

RESymb           → 'a' | 'b' | 'e'

REGroup          → '(' REExpr ')'
                 | '(' REExprMult ')'
                 | RESymbMult

REExprMult       → REExpr { REExpr }   // one or more
RESymbMult       → RESymb { RESymb }   // one or more
```

**Notes**  

- `{ X }` denotes *one or more* repetitions of X.  
- Grammar objects are implemented as `RE[GrammarType].java` classes and used to build NFAs.

## Debugging Options

`Debugable.java` centralizes debugging configuration to make the codebase easier to read and maintain.

| Level | Description |
|-------|-------------|
| 0     | Disabled (default) – only final Yes/No results for string checks |
| 1     | Final statistics (PASS/FAIL) per input string |
| 2     | Level 1 + major procedure start and final results |
| 3     | Level 2 + detailed step-by-step activity |
| 4     | Level 2 + delay between procedures |
| 5     | Level 3 + delay between procedures |
| 6     | Level 3 + redirect output to `debug.stdout.log` / `debug.stderr.log` |

**Usage**  
Specify the debug level as the second command-line argument.  

```bash
# Example 1: Show only major process results
java RegexValidator inputFile.txt 1

# Example 2: Redirect full debug output to log files
java RegexValidator inputFile.txt 6
```

## Compiling and Running

From the `regex-nfa-dfa-validator` source directory:

```bash
# Compile
javac RegexValidator.java

# Run (default, no debug)
java RegexValidator input.txt
```

Where `input.txt` contains:  

- Line 1: the regular expression  
- Line 2+: input strings to validate against the regex

## Unit Testing

Unit tests are run using a Bash script that compiles the program, executes each test file, and compares actual results against expectations. Each test has two parts:

1. **Line 1:** A regular expression to validate  
2. **Subsequent lines:** Input strings, each annotated with the expected result (`yes` or `no`)  

When run, the script automatically creates a timestamped results directory containing:  

- The generated input file  
- An expected output file  
- The program’s actual output  
- A `REPORT.txt` summarizing accepted/rejected strings  

### Test File Naming

- All unit test files must start with `unit-test_`  
- Examples: `unit-test_1`, `unit-test_email`, `unit-test_a_or_b`  
- Unit test files extend the normal input format by appending `,yes` or `,no` to each string to indicate the expected outcome  

### Example Test File: `unit-test_a_or_b`

```text
(a|b)
a,yes
b,yes
ab,no
ba,no
e,no
```

### Running the Tests

From the project root:

```bash
./runUnitTests.sh [DEBUG_LEVEL]
```

- `DEBUG_LEVEL` is optional (0–6). If omitted, it defaults to `0`.  
- Results are stored in a new directory named `unit-tests_YYYY-MM-DD_HH-MM-SS/`  

If needed, give the script execute permission:

```bash
chmod +x runUnitTests.sh
```

## Examples of test results (Debug Level 1) ##

Each regex is explained in plain English, followed by tables of accepted and rejected strings.

---

**Regular Expression:** `a*(b(a|b))`  
*Matches zero or more `a`s, followed by `b`, then either `a` or `b`.*  

| Accepted            | Rejected           |
|---------------------|--------------------|
| aba                 | aaaaaaaaaaaaaab    |
| aaaaaba             | abba               |
| aaaaaaaaaaaaaabb    | aaaaaaaaaabab      |
|                     | a                  |
|                     | b                  |
|                     | aabbb              |
|                     | aaabaa             |

---

**Regular Expression:** `a(a|b|e)a`  
*Matches strings that begin and end with `a`, with an optional middle of `a`, `b`, or empty.*  

| Accepted            | Rejected           |
|---------------------|--------------------|
| aa                  | aab                |
| aaa                 | aaaa               |
| aba                 | ab                 |

---

**Regular Expression:** `aa(a|b|e)`  
*Matches `aa` followed by an optional `a`, `b`, or empty.*  

| Accepted            | Rejected           |
|---------------------|--------------------|
| aa                  | aaaa               |
| aaa                 | aaba               |
| aab                 | baaa               |

---

**Regular Expression:** `b(a*|b)a`  
*Matches strings starting with `b`, followed by either a run of `a`s or a single `b`, and ending with `a`.*  

| Accepted            | Rejected           |
|---------------------|--------------------|
| baa                 | baaaab             |
| bba                 | bb                 |
| baaaaaaaaaaaa       | aaaaaa             |
| ba                  | e                  |

---

**Regular Expression:** `aaa(a|b)*bbb`  
*Matches strings beginning with `aaa`, followed by zero or more `a`s or `b`s, and ending with `bbb`.*  

| Accepted            | Rejected           |
|---------------------|--------------------|
| aaaabbb             | aaaa               |
| aaabbbb             | aaabb              |
| aaabbb              | bba                |
| aaabbbbb            | aaabb              |
| aaaaabbb            | e                  |

---

**Regular Expression:** `aa*bb*(aa|e)`  
*Matches one or more `a`s, followed by one or more `b`s, and optionally ending with `aa`.*  

| Accepted            | Rejected           |
|---------------------|--------------------|
| aabb                | aba                |
| aaabbb              | baa                |
| ab                  | e                  |
| abaa                | aaaabbbbbba        |
| aaabbbbb            | aabbaaa            |
| aaabbbbbaa          |                    |

## Growth Indicators

- Strengthened ability to **translate formal specifications** (regular expression grammar) into a working, maintainable software system  
- Reinforced the importance of **unit testing** to validate correctness and provide confidence during changes  
- Gained experience in **structuring code with single-responsibility classes**, making the system easier to read, debug, and extend  
- Practiced building with an **iterative, step-by-step approach** (Regex → NFA → DFA → Validation) that mirrors Agile development workflows  
