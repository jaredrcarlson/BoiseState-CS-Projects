# Project 03

* Title: List Tester
* Author: Jared Carlson
* Class: CS253 Section 2


## Overview

Main Program: UnitTestList.c
This program runs through several Unit Tests in order to verify the proper operation of the Doubly-Linked List Methods implemented in List.c.


## Compiling and Using

To run the Main Program you will need to set the paths to find the library:
export LD_LIBRARY_PATH=$LD_LIBRARY_PATH:./lib

Then run the testing program as follows from the main directory:
testsuite/UnitTestList

The program can also take some optional command-line arguments which, if included, must be supplied in proper order.
Syntax:
	UnitTestList [-s | -m | -l | -x] [-d] [-f filename]

So, for example, the [-d] option cannot be specified after the [-f filename] option

Argument Descriptions:
	Test Size Option:
	-s	(Small) Testing Size: 10 Tests per basic method
        -m	(Medium) Testing Size: 40 Tests per basic method (Default)
        -l	(Large) Testing Size: 80 Tests per basic method
        -x	(X-Large) Testing Size: 200 Tests per basic method 

	Debug Option:
	-d	Enable Debug Mode (OFF by Default)

	stdout/stderr Redirection
	-f	Used to redirect stdout and stderr to files
		stdout will be redirected to a file with the name provided after the -f argument
		stderr will always be redirected to a file named "STDERR_Report"

Example Run: Run Large size Tests with Debug and stdout redirected to a file named "programOutput.txt"
UnitTestList -l -d -f programOutput.txt

Build Instructions:
	Makefiles are already configured so building is simple.
	From the root directory (p3) build with:
	(prompt)$ make all


## Discussion

This was a great project for me to work on as I learn the C language. I was introduced to new problems while trying to get things working correctly which is always good.
The first thing I did for this project was take a few hours to examine the provided files. I studied them line by line and I drew some pictures to represent each piece.
I was able to give myself a "big picture" view of how the overall doubly-linked list would be implemented in order to stay consistent with the provided code.
This was a critical first step and I'm sure it saved me a lot of time during development. Of course, development still took many many hours!

Overall Design:
I decided that I wanted to make my Unit Testing class behave very dynamically. I began by creating a few tests for each method and made sure that all of these tests
were successful. The next step was to identify which parts of all of these tests were essentially the same operations and then move these common operations into more general methods. This was an ongoing process through development and I'm sure I could continue to "tidy up" my program if I had more time. I was able to create general test routines for each of the List.c methods so that I could run as many tests for each method as desired by simply changing one global variable.
This lead me to designing my program to take command-line arguments so that a user can choose from different sized test runs.
Essentially, users can choose from 4 options: Small = 10 Tests per basic routine, Medium = 40, Large = 80, X-Large = 200.
The command line arguments also provide the user to easily toggle Debug Mode as well as redirect stdout/stderr to text files. (Much easier to examine)
The Debug Mode was very helpful as I was tracking down bugs because I took the time to make sure it provided well-formatted, critical information.
With Debug ON, it is easy to see Node memory addresses, all steps taken to verify accuracy of each method, and visual pointers between Node Addresses.
The option to redirect stdout allows the user to specify the output filename and if this option is used, stderr is saved to a file name STDERR_Report.

Challenges / Areas that need Special Attention:
Some of the challenging aspects for this project were related to making sure that I was really verifying the accuracy of each method.
This took a lot of thinking and studying about how C really behaves "under the hood".
I had to be careful while comparing one Node pointer to another to make sure that I was testing correctly.
For example, the removeNode method uses a "direct access" pointer to locate a Node that we were able to assume was already in the list.
This required me to build a base list that already contained the node I was wanting to remove, while making sure I've stored the node's address before trying to remove
it from the list. It could have been easy to lose my original pointer variable or not store and use it correctly so I had to be careful in how I coded it.
I also ran into a sneaky little memory leak that was occurring during my search method testing.
I setup tests that would setup a base list, randomly select an Item (Object->Key) that I knew would be in the list, and search for it.
I also setup tests that would select a random Item that I knew was not in the list to test both cases for a search.
Once a given test finishes, the base list (testlist) is freed to avoid memory leaks which worked great on a successful search.
However, searches that came up empty would result in a memory leak. This is because I hade to create an Object with malloc to provide to the search method.
Since the Object was not found in the list, I needed to make sure that I freed up the Object before the test completed.
These were probably my biggest challenges in addition to just getting used to syntax differences from Java. Working with pointers and passing functions as arguments
was also pretty tricky at first, but I was eventually able to see how this was working.    

I also put a lot of time into validating command-line arguments, which a bit tricky since all of my arguments were optional.
I found that I had to set a rule for their order, but I would eventually like to figure out how to accept them in any order.
Allowing the -f option to be followed by a filename was the difficult part for me because these were two seperate arguments but had to be together.
It made parsing the arguments more difficult so I made sure to use lots and lots of comments in my parseCLA method to help avoid logic errors.

Lessons Learned:
Using structures and malloc in C is a lot like using classes and objects in Java, except you just need to make sure you're freeing up the objects you create in C
since it does not have built-in garbage collection. I found Valgrind to be a life saver in helping me track down malloc instances that did not have the needed
free command following them in order to prevent memory leaks. I am learning that the more planning, brainstorming, and picture drawing I do before coding, the
easier time I have during implementation. I also have less bugs and I don't run into those moments where I realize I need to completely redesign an element of
my project because "I hadn't thought about this" or "I didn't realize it needed to do that". I love programming!


## Sources used

I did not use any resources beyond the provided code, class examples, and general research on syntax, etc.
