# Project 01

* Title: Text Statistics
* Author: Jared Carlson
* Class: CS253 Section 2


## Overview

This program reads text from standard input and displays some statistics of the text to standard output.
The statistics include the number of words, lines, characters, and decimal digits.


## Compiling and Using

Compile with command: gcc -Wall -g main.c

Run with command: ./a.out
  This will allow you to enter the input text from the keyboard and you will need to use Ctrl-D to send the EOF character.

Run with command: ./a.out < input.txt 
  This will allow you to calculate statistics from an input text file

Run with command: ./a.out < input.txt > stats.txt
  This will also save the statistics to an output file rather than just displaying them on screen


## Discussion

This was a fairy easy program to write since it was all about "getting my feet wet" in the C language as well as making sure
I could use the new backpack utilities. I would say that defining constants would be the one thing that I found interesting
with this first project. It was a little ackward to use the #define syntax since it is so different from Java, but it
really does make a lot of sense why the grammar was written this way. When declaring a constant, I am in fact defining an
identifier and it is certainly easy to distinguish between constants and variables in C.

I did not run into anything challenging with this first project since I was provided some very good example files to work from.
I'm sure my future projects will be more challenging and I will have plenty to say in this section later on.


## Sources used

I did not use any other sources outside of the example files provided.
