CC=gcc
CFLAGS=-Wall -g -fPIC -shared
LFLAGS=-L$(LIB) -lmylib -lpthread

all: ring-buffer 

ring-buffer:
	$(CC) $(CFLAGS) $(LFLAGS) -c ring.c
	$(LD) -shared -fPIC -o libring.so ring.o

clean:
	rm -f *.o a.out core libring*
