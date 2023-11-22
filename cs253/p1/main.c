// This program counts the number of words, lines, and characters from data gathered through standard input.
// The number of times each decimal digit appears in the data is also counted.
// All of these counts are then displayed to standard output.
// 
// @author jcarlson

#include <stdio.h>

// The constants IN and OUT are used to track when the program is within (IN) a word or has just left (OUT) a word
#define IN 1
#define OUT 0

int main(int argc, char* argv[])
{
	// Declare temporary value variables
	int c, i, state, lastState;

	// Declare "counts" tracking variables
	int numDigits[10];
	long numChars, numWords, numLines;

	// Initialize variables
	lastState = state = OUT;
	numLines = numWords = numChars = 0;
	for(i = 0; i < 10; i++) {
		numDigits[i] = 0;
	}

	// Read all characters until the End Of File character is reached
	while((c = getchar()) != EOF) {
		numChars++; // 1 Character read
		
		if(c == '\n' || c == EOF) {
			lastState = state; // Store previous state
			state = OUT; // Exiting a word
			numLines++; // 1 Line read
		}
		else if(c == ' ' || c == '\t') {
			lastState = state;
			state = OUT;
		}
		else {
			lastState = state;
			state = IN; // Inside of a word
			if(c >= '0' && c <= '9') {
				numDigits[c - '0']++; // Increment digit count
			}
		}
		
		if(state == OUT && lastState == IN) {
			numWords++;
		}
	}
	
	// Write Statistics 
	printf("words: %ld\nchars: %ld\nlines: %ld\n", numWords, numChars, numLines);
	for(i = 0; i < 10; i++) {
		printf("digit %d: %d\n", i, numDigits[i]);
	}

 	return 0;
}
