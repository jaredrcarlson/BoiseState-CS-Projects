#Reference cs.boisestate.edu/~cs253/handouts/make-handout.pdf
#For more information regarding make
CC = gcc
CFLAGS = -Wall -g 

homework: main.o
	$(CC) $(CFLAGS) $^ -o $@

clean:
	rm -f *.o homework
	rm -f expected
	rm -f actual
	rm -f a.out
