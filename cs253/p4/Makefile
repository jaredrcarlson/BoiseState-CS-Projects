# This is a Makefile used to make compiling and running 
# the hello program easier to manage.
# 
# The Macros section is used by the make program to define
# global values for use in the Targets section. Macros help
# to make Target definitions more generic and easier to maintain.
#
# The Targets section is used to define actions for the make
# command. Common actions are "make all", "make run", "make clean".


### Macros

CC=gcc
CFLAGS=-Wall -g
SRC=main.c
PROG=hello


### Targets

# The all: target is listed first making it the 
# default target which means that it will be executed when make
# is called without any target specified.
all: $(PROG)

# The hello: target is the "main" target that creates the executable
# for the main program. It uses the SRC Macro to specify which
# source file dependencies it needs.
hello: $(SRC)
	$(CC) $(CFLAGS) $(SRC) -o $@ 

# The run: target depends on the hello: target which is the name
# of the main program, so the PROG macro is used as it's dependency.
# This target, executes the hello: target and then runs the executable.
run: $(PROG)
	./$<

# The clean: target is used to "clean up" the directory. As a general
# rule, the clean target should remove all files generated by 
# previous calls to the make program.
clean:
	rm -f *.o $(PROG)
