//
// Author: Joseph Connelly
// Date: 4/1/2016
//

#include <stdio.h>
#include <stdlib.h>

struct device{
	int id;
	int ready_state;
	void (*f)(void *);
	void (*f2)(void *);
};

void printDeviceID(void *);
void testDeviceFunct(void (*)(void *), int);

int main(void){
	struct device *speaker = (struct device*) malloc (sizeof(struct device));
	speaker->id = 1234;
	speaker->ready_state = 1;
	speaker->f = printDeviceID;
	speaker->f2 = NULL;	//you can set funct ptrs = NULL if they are unused.
	
	// Now we're ready to use a function that's contained within a struct :)!
	(*speaker->f)("1. Hi");	//this is how you explicitly use a function
				//ptr, by dereferencing the function with *, 
				//and when you do - you have to immediately
				//give it the args it needs cuz its now being 
				//executed.

	testDeviceFunct(speaker->f, speaker->ready_state);
				//This is how you pass a function pointer to
				//another function, which is what you did in
				//your List.c freeList() :)!!! Since a function
				//ptr is just an int variable, passing it is the
				//exact same as passing any other int valiable.
				//	GET THE DIFFERENCE? MAKE MORE SENSE???
	
	speaker->ready_state = 0;
	testDeviceFunct(speaker->f, speaker->ready_state);
				//One more - correct, but unsafe example...
	
	free(speaker);
	return EXIT_SUCCESS;
}

void printDeviceID(void *msg){
	printf("%s\n",(char *)msg);
}

void testDeviceFunct(void (*fPtr)(void *msg), int opt){
	if( opt==1 ) 
		(*fPtr)("2. Another explicit dereference of a C fucnt ptr :)");
	if( opt==0 )
		fPtr("3. implicit dereference !safe when using on old platforms");
}
