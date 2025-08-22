# Regex → NFA → DFA Validator

A Java implementation of automata construction and validation from regular expressions.  

## Implementation Overview

This program executes the following major procedures:

* Parses a Regular Expression into Context-Free Grammar objects
* Constructs an NFA from the resulting CFG objects
* Converts the NFA to a DFA that accepts the same set of strings
* Validates input strings using the DFA printing Yes (Accepted) or No (Rejected) for each

## Major Procedure Source Code Descriptions

**Main**  
MainProject1.java contains the "main" method and makes use of the other major procedure classes to build the DFA from the original regular expression. All command line arguments as well as input/output filenames are validated and then other major procedure classes are used to produce output. A detailed usage message is displayed if any errors are detected while processing command-line arguments.

**REParser / REScanner**  
REParser.java makes use of REScanner.java and uses a Context-Free Grammar to recursively parse the regular expression into RE[Grammar-Type] class objects. See Context-Free Grammar section for the grammar definition.

**NFABuilder**  
NFABuilder.java uses the RE[Grammar-Type] objects to construct an NFAStateMachine object.

**NFAtoDFAConverter**  
NFAtoDFAConverter.java contains a static method which takes an NFAStateMachine object as a parameter
and returns an equivalent DFAStateMachine object. (Accepts the same language)

**DFAStringChecker**  
DFAStringChecker.java checks if an input string is Accepted or Rejected by the DFAStateMachine that
is passed as a parameter to the constructor upon instantiation.

## Additional Source Code Descriptions

* NFAStateMachine.java/DFAStateMachine.java represent NFA/DFA State Machines.
* FAState.java represents a Finite Automata State for either an NFA or DFA State Machine.
* RE.java represents a Regular Expression.
* REPathComponent.java represents one portion of a Regular Expression which corresponds to a set
   of NFA/DFA states that will exist after building the state machine. Think of a single path component
   as one major "step" from the start state on the way to the final state. The regular expression: ab
   has two path components since an input string MUST have an 'a' and then MUST have a 'b' to be valid.
* RE[Grammar-Type].java classes represent each valid Grammar-Type for a Regular Expression.

## Context-Free Grammar Definition

The Context-Free Grammar used is as follows:

```html
EXPR_SINGLE:          <REExpr> : <RETerm>
EXPR_COMPOUND:        <REExpr> | <RETerm> '|' <REExpr>

TERM_SYMBOL:          <RETerm> : <RESymb>
TERM_GROUP:           <ReTerm> | <REGroup>

SYMB_A:               <RESymb> : 'a'
SYMB_B:               <RESymb> : 'b'
SYMB_E:               <RESymb> : 'e'

GROUP_SINGLE_EXPR:    <REGroup> : '(' <REExpr> ')'
GROUP_MULT_EXPR:      <REGroup> | '(' <REExprMult> ')'
GROUP_MULT_SYMBOL:    <REGroup> | <RESymbMult>

EXPR_MULT:            <REExprMult> : <REExpr> { <REExpr> }
SYMB_MULT:            <RESymbMult> : <RESymb> { <RESymb> }


  *Note: { <RExxxx> } --> denotes that "One or More" <RExxxx> items are present   
```

## Debugging Options

Debugable.java is used to make debug integration more fluid and helps to produce code that is easier to read and maintain.

Available Debugging options are as follows:

| Option | Description |
| :--- | :--- |
| LEVEL_0 | Disabled (Default) - Only shows Required Output for input string checks (yes/no) |
| LEVEL_1 | Show Only Final Statistics - Input String (PASS/FAIL) |
| LEVEL_2 | LEVEL_1 + Major Procedure Start and Final Results |
| LEVEL_3 | LEVEL_2 + Minor Procedure Activity |
| LEVEL_4 | LEVEL_2 with a Delay between Procedures |
| LEVEL_5 | LEVEL_3 with a Delay between Procedures |
| LEVEL_6 | LEVEL_3 with output redirection --> ("debug.stdout.log", "debug.stderr.log") |

**Debug Usage**  
To run the program with debugging, simply include the desire debug level as the second command line argument.

Example 1: Display only Major Process Results

```bash
java MainProject1 inputFile.txt 1
```

Example 2: Redirect Full Debug Report to (STDOUT/STDERR) log files

```bash
java MainProject1 inputFile.txt 6
```

## Compiling and Using (Default - No Debug)

Compile with the following command from within the MainProject1 source directory:

```bash
javac MainProject1.java
```

To run the program, simply use:

```bash
java MainProject1.java input.txt
```

where "input.txt" is the input filename containing the regular expression on line 1 followed by
any number of input strings to check on subsequent lines

## Unit Testing

### Overview

Unit testing is performed through the use of Linux bash scripts that build and run a user-defined set of tests. Each test is performed and a "PASSED" or "FAILED" message is displayed for each test. If a test happens to FAIL, the results for that test are displayed showing which strings were ACCEPTED and which strings were REJECTED. All of the files used for each test are saved to a "time-stamped" unit-tests directory, including a REPORT.txt file that shows the language definition and exactly which strings were ACCEPTED/REJECTED during the test run.

**Usage Instructions**  
In order to perform unit tests, you must first create individual unit test files for each test you would like to run. This is very simple and there are already some example unit test files in the main project directory. Each unit test filename MUST begin with "unit-test_" in order for it to be included in test runs. Examples are: unit-test_1, unit-test_2, unit-test_MyTest. Each unit test file has the same basic format as the input file used for MainProject1, except that each input string must end with either ",yes" or ",no" depending on whether or not the string should be Accepted or Rejected respectively.

The following is an example of a simple unit test file:  

### unit-test_a_or_b

```bash
(a|b)
a,yes
b,yes
ab,no
ba,no
e,no
```

Once you have created your unit test files, simply execute the following command:

```bash
./runUnitTests.sh
```

If needed, give the runUnitTests.sh script executable permissions:

```bash
chmod +x runUnitTests.sh
```

## Sample Testing Results (Using Debug Level 1)

The following several sample tests were produced using Debug Level 1 to make it easier to quickly
see the RegEx and how all input test strings were handled:

```bash
Language Definition: a*(b(a|b))

  -----<| Accepted |>-----
  aba
  aaaaaba
  aaaaaaaaaaaaaabb

  -----<| Rejected |>-----
  aaaaaaaaaaaaaab
  abba
  aaaaaaaaaabab
  a
  b
  aabbb
  aaabaa


Language Definition: a(a|b|e)a

  -----<| Accepted |>-----
  aa
  aaa
  aba

  -----<| Rejected |>-----
  aab
  aaaa
  ab
  
  
Language Definition: aa(a|b|e)

  -----<| Accepted |>-----
  aa
  aaa
  aab

  -----<| Rejected |>-----
  aaaa
  aaba
  baaa
  
  
Language Definition: b(a*|b)a

  -----<| Accepted |>-----
  baa
  bba
  baaaaaaaaaaaa
  ba

  -----<| Rejected |>-----
  baaaab
  bb
  aaaaaa
  e
  
  
Language Definition: aaa(a|b)*bbb

  -----<| Accepted |>-----
  aaaabbb
  aaabbbb
  aaabbb
  aaabbbbb
  aaaaabbb

  -----<| Rejected |>-----
  aaaa
  aaabb
  bba
  aaabb
  e
  
  
Language Definition: aa*bb*(aa|e)

  -----<| Accepted |>-----
  aabb
  aaabbb
  ab
  abaa
  aaabbbbb
  aaabbbbbaa

  -----<| Rejected |>-----
  aba
  baa
  e
  aaaabbbbbba
  aabbaaa
```

## Final Thoughts

This was a very challenging project and required more time to complete than I had originally anticipated. Working through this project forced me to think differently while programming and helped me to find new ways to approach problems. By writing every line of every source file, instead of copying/pasting some of the Java recursive decent code found in the project resource, I gained a much deeper understanding of recursive decent parsing. However, this decision increased my development time which is certainly something I will consider in the future. My goal for this course has been to learn as much as I can which, at times, has required more time and effort than it would take to simply earn a passing grade. Completing this project was very rewarding.
