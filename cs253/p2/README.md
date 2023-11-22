# Project 02

* Title: Binary String | Unsigned Integer Converter
* Author: Jared Carlson
* Class: CS253 Section 2


## Overview

This program performs one of two operations based upon the command-line arguments supplied.
The first operation converts an unsigned integer into it's binary string representation.
The second operation converts a binary string to it's unsigned integer representation.
The results for each operation are displayed to standard out as well as usage instructions if the command line arguments are invalid.


## Compiling and Using

Compile with command: gcc -Wall -g main.c

To convert and display an unsigned integer to binary string:
Run with command: ./a.out -i <unsigned integer>
  
To convert and display a binary string to unsigned integer:
Run with command: ./a.out -b <binary string>


## Discussion

This was a great project for me to work on as I learn the C language. I have learned that bitwise operations are very powerful as they do not require as much overhead as traditional mathematical operations do on a central processing unit.
Shifting bits within a range of bytes must be "easier" on the CPU than performing multiple arithmetic operations.

This project has really helped me to "take a look" at the bit-level of numbers and more easily alter their values by shifting bits around instead of performing multiplication and division operations.
The masking techniques I learned throughout this project are not only interesting, but have also given me a new way to think about how to alter variable values to produce desired results while minimizing CPU overhead.

I found that I had to write out several binary translations of integers on paper as well as certain masks to really "see" the bit manipulations I was processing which gave me a great education on bitwise operators and the power they have while writing programs like this one. This was a great exercise that helped me to really see that data stored as 1's and 0's can be manipulated at the bit-level to easily produce the same results of "costly" operations that the Arithmetic Logic Unit would have to perform if bit-shifting were not used.

I did run into some difficulty while trying to create "dynamic" masks while I was converting between Little Endian and Big Endian values, but by drawing pictures of the bits and thinking a lot about how the different bitwise operators (AND, OR, etc) could help me, I was able to obtain the results I desired by applying the proper "<<" and ">>" operators on my bitmasks.

There are many ways to perform these bit-shifts, but I chose operations that would always work dynamically to account for any differences in the "number of bytes" of an unsigned integer for any given system. These dynamic methods will allow my implementation to be successful for all systems, regardless of how many bytes are used to store an unsigned integer.

I also put a lot of time into validating command-line arguments, beyond just checking that the correct number of them were input by the user. I think this is a very important piece for any program because if a user makes a mistake, but is unable to determine what that mistake was, they will simply find a more "user-friendly" piece of software instead of using yours in frustration. This is why I not only checked for as many error cases as I could think of, but I also added specific debug messages for each type of error which can be toggled on and off. I also used a different exit status from Main depending on which error was detected. This way, if I ever decide to use this program within a larger application, I will not only know IF this program fails, but I will also know WHY (which is far more useful).

While this has just been the first step in manipulating values in the C environment, I am confident that I now have the knowledge to move forward with more difficult challenges which I think will not only be exciting, but will enhance my abilites as a software engineer. I look forward to using header files on the next project as they will make things much "cleaner" and easier to manage.

Also, thanks so much for all of your feedback on my first project. I was shocked when I realized that someone had actually looked at my coding decisions and had some advice for me. This is what I expected when I started taking CS courses, but it hasn't been the case. In one of my previous courses, I actually put together an entire paragraph in the middle of my ReadMe file describing how I prefer mustard over ketchup when it comes to eating a hotdog at a baseball game.
Maybe the person grading it also prefers mustard so they didn't have any advice on the subject, or maybe they just opened the file for a few seconds to see if it has the right number of paragraphs.

Long story short, I appreciate advice from someone with more experience.
School is expensive and I want to learn as much as I can, so I'm open to any and all suggestions.  


## Sources used

I did not need or want to use any outside sources for this project other than some general research on bit-shifting operations and of course, C syntax! The in-class bit-shifting assignment gave me a really good feel for these types of operations.
I am also currently enrolled in ECE-330 so I have been getting plenty of practice with bitwise operators.

In general, I do not believe in "re-inventing the wheel", but I think that it is absolutely necessary to re-invent the wheel while trying to learn a particular topic/principal in college, especially for engineering students. As we begin to attempt to  solve a problem, Googling for assistance is the last tool we should be considering. There is no shortcut to learning and while "Googling" is a desirable skill, it can cripple your critical thinking skills. As an engineering student, I am trying to learn how to solve problems with the course principals I have learned and my own experience so that I can actually still work on my projects if my internet connection goes down.

Sure, it might be possible to pass this course by simply using Google and learning how others have solved similar problems, but this is not the path to becoming a real engineer. This path will only get you a piece of paper claiming that you know how to program, but a piece of paper just gets you an interview, not a job.

Learning a valueable skill takes time and effort and sitting down for a while to come up with some pseudocode is such an important step in any project. Eventually, as these projects become more challenging, there will be no substitute for this type of method.
