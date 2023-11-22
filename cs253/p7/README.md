# Project 07

* Title: Checkpointing - Multi-Threaded
* Author: Jared Carlson
* Class: CS253 Section 2


## Overview

This project builds upon project 6 by introducing multiple threads.
Protections are put in place to make sure that only a single thread is logging a message to
the buffer at any given time.


## Compiling and Using

To test the checkpointing implementations for this program, simply use the provided script:
```
./run.sh
```

Once the program has finished executing, you can view the log dump file with: 
```
cat ring.log
```


## Discussion

This was my first experience with multiple threads and while I do know better, a part of me still believes that
multi-threading is straight-up witchcraft. I added "print-out" statements for each key piece of my code so that I
could try to follow the overall order of execution for each test run. I wanted to see when a new thread is created,
whein it logs a message, when it is terminated, etc. I was able to accomplish this for the most part, but printing
messages to the screen is not exactly the best way to debug a multi-threaded application. I actually still don't know
what the best approach is for really "seeing" what's going on in a multi-thread app and when different threads are
executing. I was able to get a pretty good feel for things though, and I will comment on my findings.

I found that, for this particular project, using 3 threads that would log 10 messages each, yielded very similar
results nearly every time I executed the program. In fact, I used a random number to generate each "log message" and
these random numbers were quite similar for each run. Maybe my understanding of random number generation (seeding) in C
is a bit too naive so maybe there's a good reason why I was often seeing the same random numbers without providing a seed.

As I increased the number of threads, the final messages in the log file seemed to change more frequently between runs
but I did not see a significant change in the duration of execution. (I did not notice any speed gains/losses)
I tried several different amounts of threads in combination with large and small amounts of messages to be logged for each
thread, but I never really could say that I noticed any sort of substantial differences in execution speed. It was also
hard to say that there were any particular combinations of (# of threads / # of messages) that would result in any sort of
predictable behavior, but I do not think that this should surprise me. I've been running my tests on onyx, which is a 
shared environment, so the number of resource requests and the current number of processes being handled by onyx at any given
point in time, are probably unpredictable and will vary from day to day, hour to hour, etc.

Multi-Threading is certainly important and I look forward to digging deeper into this topic as I progress.
If you know of any additional resources you think would help me, please let me know Joey and thanks for such wonderful feedback
this semester. I seriously appreciate the things you have brought to my attention. There's nothing worse than making the same
mistakes over and over again, simply because you don't know any better.


## Sources used

Class Slides, Piazza Q&A, general syntax research.
