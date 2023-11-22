# Project 04

* Title: Make
* Author: Jared Carlson
* Class: CS253 Section 2


## Overview

Makefiles - make program

This project demonstrates the most basic usage of the make program.
The make program uses the file named Makefile in order to compile and run the main program.
The Makefile also specifies how to remove any files it has created (clean up).


## Compiling and Using

There are a few options in this Makefile that can be used for the make program.

To build an executable file named "hello" from the main.c source file:
<shell>$ make   or   <shell>$ make all   or   <shell>$ make hello

To build and run the executable program (hello):
<shell>$ make run

To clean up the directory (remove all files created by make):
<shell>$ make clean


## Discussion

I've been working with linux for quite a few years and I've followed many "installing from source" guides along
the way. I've typed "make && make install" so many times and have always wondered what these commands actually do.
This project was such a great way for me to learn about the make program and I'm starting to think about ways it
can make my life easier. Just like any other tool, it will take lots of practice and tinkering with, to really figure
out how to make the best use of it.

I really like how I was able to use the special symbols $@, $<, etc. in order to have targets with generic code.
This will make updating/adding to projects much easier as it will just be a matter of making a change to some of the
macros and the targets will still work as expected without needing any modification.

As I read more about Makefiles and examine the structure of them from our last project, I'm beginning to see that
they can be very powerful if they are implemented carefully. I could move all of my .o files to their own directory,
use wildcards instead of hard-coding executable names, etc. I look forward to learning more about make with future projects.


## Sources used

In addition to classroom slides, I was able to find some great information about make/Makefiles.
While I didn't end up using any of the examples from these source, they were certainly very helpful
as I was getting a feel for what I accomplish with make.

http://makepp.sourceforge.net/1.19/makepp_cookbook.html
http://www.cprogramming.com/tutorial/makefiles_continued.html
