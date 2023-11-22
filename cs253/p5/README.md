# Project 05

* Title: Bash - Testing C Program
* Author: Jared Carlson
* Class: CS253 Section 2


## Overview

Shell Scripting and using a library

This project demonstates how to use bash scripting to test the proper functionality of a C program.
The Makefile was updated to build and link project 5 to the existing linked list library code.

The C program "wf" was provided and a bash script named backpack.sh was created to automate the testing.
The wf program has two options, both of which require an input text file, with the following syntax:
wf --self-organized-list <textfile>
wf --std-list} <textfile>

The bash script takes a single "file name" argument to write the testing results with the following syntax:
./backpack.sh <output file>

The script redirects the output for both of the wf program options to a text file.
The script performs the following "checks" to verify the output of the wf program options:
1) Checks to make sure that option 1 (--self-organized-list) creates the redirected output file.
2) Checks to make sure that option 2 (--std-list) appends the redirected output to the output file.
3) Checks to make sure that the output file does not contain the wf program's usage message.

The script will set an error code "flag" if any of these checks fail and then makes use of a switch
statement to display the results as well as write them to the user-provided output file name.
The possible results are:
HW5:PASS
HW5:FAIL - <Reason for failure> 


## Compiling and Using

To run the bash script, use the following syntax to provide the output "results" file name:
./backpack.sh <output file>

Example:
./backpack.sh results
(The testing results will be displayed and written to a file named "results")

## Discussion

Learning bash scripting techniques was a little tricky, but was a lot of fun.
The syntax took some getting used to since I'm more familiar with Java and C which seem to have more
flexibility, like ignoring whitespace in some places where bash requires it.
For example, I failed to properly space out one of my if conditional statements which took me a while
to realize my error:
"if [$ERROR_CODE]" needed to be changed to "if [ $ERROR_CODE ]" to avoid a bash error.

I found that there were many ways I could implement my 3 checks because there are a lot of useful tools
build into linux. It was nice to have these tools to play around with and begin to think about how they
can be used to solve problems while programming.

Here are the tools I used to perform the 3 checks that I felt were needed to verify functionality:
Check 1: Making sure that the output file was created by wf option 1
I used some built-in bash "file-attributes" tools to perform this check.
I simply used an if statement to check if the file did not (!) exist (-e).
if [ ! -e $WF_OUTFILE ]

Check 2: Making sure that wf option 2 appended the redirected output to the output file
I made a copy of the output file produced by wf option 1 and then just compared (cmp) them to
see if they were the same (-s) which would indicate that wf option 2 failed.
cmp -s $WF_OUTFILE $TEMP_OUTFILE && FAIL_CODE=2

Check 3: Making sure the output file did not contain the wf usage message
I executed the wf program with a "bad_argument" paramater and redirected the output to a file so that
I could use the contents if this file to check if they existed in the output file.
The grep tool is so powerful and I found it to be the perfect tool for this check.
I wanted grep to search the output file for lines that were exactly the same as the usage message so
I had to use the -F option which searches for "fixed strings" that are seperated by newlines.
The -f option simply tells grep that it is working with a file and it needs to match the file's contents.
I redirected the output of the grep command to a file, which should be empty if the usage message is
not found in the output file. Here's how I used the grep command in my script:
grep -F -f $WF_USAGE_MESSAGE $WF_OUTFILE > $GREP_RESULT

I then just used the buit-in (-s) operator which checks if a file exists and is NOT empty.
if [ -s $GREP_RESULT ]

Overall, I found this project extremely useful because bash scripting can make life much easier and
bash is almost always available. I've been using basic bash scripts for a while to run backup tasks
and things of that nature on my home server so I am glad that I've learned a few more tools that I can
use while automating tasks!


## Sources used

I found the "Shell Programming" slides from lecture to be extremely helpful while working on this project.
I did need to lookup various examples of the grep command on the internet, but this was just to help me
understand the syntax options available so that I could write my own code. 
