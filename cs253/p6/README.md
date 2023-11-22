# Project 06

* Title: Checkpointing
* Author: Jared Carlson
* Class: CS253 Section 2


## Overview

Checkpointing - "Having something to fall back on if a program crashes"

This project demonstrates "Checkpointing" through the use of Linux signals/handlers.
The idea of checkpointing is to save recent program "state" information in case of a crash or other unforseen
error in the program. These saved states can then be used to restore a program to a somewhat recent point
before it crashed. At the very least, checkpointing can be used to save log files (as is demonstrated in this
program) to disk so that a developer can more easily track down the events that led to the crash.

## Compiling and Using

To test the checkpointing implementations for this program, simply use the provided script:
```
./run.sh

```

## Discussion

This project has been really good for me because I haven't had any experience with checkpointing before.
I still have a long way to go with this topic, but I'm glad I've gotten my feet wet.
The way I needed to call the signal method, passing it the type of signal and the method that would be
handling the signal, reminded me of Java actions and their associated listeners.

Testing considerations:
1) Logging a NULL String
   I will create a helper method to use that first checks if the log message string is NULL using the
   strcmp method and only calls the log_msg method for non-NULL strings. There's no sense in logging
   an empty string!

2) Logging a String that is too long
   I will use the strlen method to compare length with the max allowed length and wrap the log message
   across multiple lines. This could be implemented in the same helper method I create to handle NULL strings.
   I don't think truncating a log message is a good idea, especially if an error message has an
   error code at the very end.

3) Logging while Dumping at the same time
   This one has had me thinking for a long time......well this would occur if logging is being done
   at the same time the alarm signal is sent which automatically calls the dump method.
   I think the only way to prevent this action is to prevent the alarm handler from calling the dump
   method directly. Instead, I plan to use a boolean variable that the alarm handler can simply set
   to true. Then the logging method can check this boolean "AFTER" it has finished logging, and call
   the dump method if the boolean is "set". Then the logging method can set the boolean to false.
   
4) Index Counter exceeds Maximum Value of int data type
   My first thought is to just check the counter value every time it increments and reset it to zero
   when it reaches some large number, but that doesn't seem very efficient. The resulting index that
   is calculated from the counter ends up just being a Mod6 number, so maybe there's a better formula
   I can use that will ensure that the Mod6 result is accurate while keeping the counter variable from
   growing indefinately. I plan to research this topic while preparing for the next project as my Math
   skills are not quite top notch.

I am going to continue thinking about additional bugs that may creep up with my existing implementation, 
but I haven't been able to produce any yet. Of course, I'm sure that's because I don't have a whole lot
of experience with C yet.


## Sources used

I found that the slides from lecture were absolutely necessary for me to get things working.
I also really appreciated the links shared on Piazza as they were exactly what I should have been looking for
in the first place!

https://www.freebsd.org/cgi/man.cgi?sektion=3&query=signal
http://man7.org/linux/man-pages/man7/signal.7.html

