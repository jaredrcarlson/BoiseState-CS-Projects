#include <stdio.h>
#include <stdlib.h>
#include <string.h> // strlen() --> String Length, strcmp() --> String Comparison, strcpy() --> String Copy
#include <limits.h> // CHAR_BIT --> Machine-Dependent "Bits per Byte" value

// Function Prototypes
int validateCLA(int numUserArgs, char* userArgs[]);
void displayProgramUsage();
void printBits(unsigned int x);
unsigned int swapBytes(unsigned int x);
unsigned int btoi(char x[]);
void printOutput(unsigned int x, int line);
void printHeadings();
unsigned int maxIntValue(int numBytes);

// <Debugging Mode Control>
//  Current Modes Implemented:
//   0 - Show NO debugging messages
//   1 - Show ALL debugging messages
const int DEBUG_MODE = 0;

// Global Machine-Dependent values
const int NUM_BYTES_PER_UNSIGNED_INT = sizeof(unsigned int);
const int NUM_BITS_PER_BYTE = CHAR_BIT;
const int NUM_BITS_PER_UNSIGNED_INT = sizeof(unsigned int) * CHAR_BIT;

/**
 * <Number Representation Converter>
 *  Unsigned Integer to Binary String & Binary String to Unsigned Integer
 *
 * This program performs one of two operations based upon the command-line arguments supplied.
 * The first operation converts an unsigned integer into it's binary string representation.
 * The second operation converts a binary string to it's unsigned integer representation.
 * The results for each operation are displayed to standard out as well as usage instructions
 * if the command line arguments are invalid.
 *
 * @author Jared Carlson <jaredcarlson@u.boisestate.edu>
 * @return Main returns 0 if execution was successful (without any errors)
 *         Main returns 1 if any errors were encountered (invalid user-supplied command-line arguments)
 */
// <<<<<<<<<< -------- MAIN -------- >>>>>>>>>>
int main(int argc, char* argv[]) {
	int argStatus = validateCLA(argc - 1,argv + 1); // Validate and Classify user-supplied portion of command-line arguments
	switch(argStatus) {
		case 1 : // Option: -i <---> Unsigned Integer to Binary String
			if(DEBUG_MODE) {
				printf("You entered --> Option: %s with Integer: %s <-- These are valid arguments for int to binary string.\n",argv[1],argv[2]);
			}
			printOutput(atol(argv[2]),1); // Print results to standard output
			break;
		case 2 : // Option: -b <---> Binary String to Unsigned Integer
			if(DEBUG_MODE) {
				printf("You entered --> Option: %s with Binary String: %s <-- These are valid arguments for binary string to int.\n",argv[1],argv[2]);
			}
			printOutput(btoi(argv[2]),1); // Print results to standard output
			break;
		default : // Invalid command-line arguments
			displayProgramUsage(); // Print program usage to standard output
			return 1; // Exit with Error Status 1 (Bad Command-Line Arguments)
	}
	return 0; // Exit Success
}
// <<<<<<<<<< -------- END MAIN -------- >>>>>>>>>>

/**
 * Validates user-supplied command-line arguments
 *
 * Validation Constraints:
 *  The number of user-supplied arguments must be exactly 2.
 *  The first argument (program mode) is restricted to '-i' or '-b'.
 *  The second argument must be the correct type: -i <integer> or -b <binary string>
 *
 * @param numUserArgs Actual number of arguments entered by user (this excludes argv[0] == "Program Name")
 * @param userArgs Array of arguments entered by the user
 * @return This function returns an integer signifying the results of the validation process.
 * 		   The results associated with each integer are as follows:
 * 		    1 - Success: Valid arguments for -i option
 * 		    2 - Success: Valid arguments for -b option
 * 		    3 - Error: Not enough arguments
 * 		    4 - Error: Too many arguments
 * 		    5 - Error: Invalid option (Valid: "-i" or "-b")
 * 		    6 - Error: Invalid Type: -i <arg2> | where arg2 is not a valid integer
 * 		    7 - Error: Above Max Bound: -i <arg2> | where the value of arg2 > max value of an unsigned integer
 * 		    8 - Error: Below Min Bound: -i <arg2> | where the value of arg2 < min value of an unsigned integer == 0
 * 		    9 - Error: Length > Max Bound: -b <arg2> | where the length of arg2 > max length of an unsigned integer (binary string)
 * 		   10 - Error: Invalid Binary Character: -b <arg2> | where arg2 contains invalid characters (binary string) (Valid: '0' and '1')
 */
int validateCLA(int numUserArgs, char* userArgs[]) {
	// Valid Option string values
	char option_i[] = {'-','i','\0'};
	char option_b[] = {'-','b','\0'};

	// Valid Binary Digit character values
	char char_zero = '0';
	char char_one = '1';

	if(numUserArgs < 2) {
		if(DEBUG_MODE) {
			printf("Error! Not enough arguments were provided.\n");
			printf("You entered %i arguments.\n",numUserArgs);
		}
		return 3; // Not enough arguments supplied
	}
	else if(numUserArgs > 2) {
		if(DEBUG_MODE) {
			printf("Error! Too many arguments provided.\n");
			printf("You entered %i arguments.\n",numUserArgs);
		}
		return 4; // Too many arguments supplied
	}
	else {
		if( strcmp(userArgs[0],option_i) == 0 ) {
			// Must check for special case where atol(string of zero(s)) returns 0
			// The atol() method returns 0 when either:
			//  It cannot parse a string to type integer (Error)
			//    OR
			//  It parses a string of zero(s) (Not an Error)
			char userIntString[strlen(userArgs[1])];
			strcpy(userIntString,userArgs[1]);
			int i = 0;
			int zeroString = 1; // Assume string is Zero
			while( (i < strlen(userArgs[1])) && zeroString) {
				if(userIntString[i] != char_zero) { // Check for non-zero Character
					zeroString = 0; // Not a string of zero(s)
				}
				i++;
			}
			long int safeResult = atol(userArgs[1]);
			unsigned int stringToIntResult = (unsigned int) safeResult;
			// Check for Bad Type --> Case 1: Not a string of zero(s) AND Case 2: atol(userInteger) == 0
			if(!zeroString && stringToIntResult == 0) {
				if(DEBUG_MODE) {
					printf("Error! %s is not an integer value.",userArgs[1]);
				}
				return 6; // User supplied -i <arg2> : where arg2 is not a valid integer
			}
			else {
				unsigned int maxValue_unsignedInt = maxIntValue(NUM_BYTES_PER_UNSIGNED_INT);
				long int safeResult = atol(userArgs[1]);
				unsigned int userInt = (unsigned int) safeResult;
				if(userInt > maxValue_unsignedInt) {
					if(DEBUG_MODE) {
						printf("Error! Supplied integer: %u is greater than the maximum allowed: %u\n",userInt,maxValue_unsignedInt);
					}
					return 7; // User supplied -i <arg2> : where the value of arg2 > maximum value of an unsigned integer
				}
				else if(userInt < 0) {
					if(DEBUG_MODE) {
						printf("Error! Supplied integer: %s is a negative number which is not allowed for unsigned integers.\n",userArgs[1]);
					}
					return 8; // User supplied -i <arg2> : where the value of arg2 < minimum value of an unsigned integer == 0
				}
				else {
					return 1; // Success - Valid arguments for -i option
				}
			}
		}
		else if( strcmp(userArgs[0],option_b) == 0 ) {
			int userBinStringLength = strlen(userArgs[1]);
			if(userBinStringLength > NUM_BITS_PER_UNSIGNED_INT) {
				if(DEBUG_MODE) {
					printf("Error! Supplied string of length: %i is longer than the maximum allowed: %i\n",userBinStringLength,NUM_BITS_PER_UNSIGNED_INT);
				}
				return 9; // User supplied -b <arg2> : where the length of arg2 > maximum length of unsigned integer (binary string)
			}
			else {
				char userBinString[userBinStringLength];
				strcpy(userBinString,userArgs[1]);
				int i;
				for(i = 0; i < userBinStringLength; i++) {
					if(userBinString[i] != char_zero && userBinString[i] != char_one) {
						if(DEBUG_MODE) {
							printf("Error! Supplied string contains character: %c which is not a valid binary digit.\n",userBinString[i]);
						}
						return 10; // User supplied -b <arg2> : where arg2 contains invalid characters for a binary string (Valid: '0' and '1')
					}
				}
				return 2; // Success - Valid arguments for -b option
			}
		}
		else {
			if(DEBUG_MODE) {
				printf("Error! %s is not a valid option.\n",userArgs[0]);
			}
			return 5; // User supplied an invalid option (Valid: "-i" or "-b")
		}
	}
}

/**
 * Displays Program Usage Message
 */
void displayProgramUsage() {
	printf("Usage: homework -i|-b\n");
	printf(" -i x: positive integer less than %u\n",maxIntValue(NUM_BYTES_PER_UNSIGNED_INT));
	printf(" -b x: binary representation of positive integer less than %u\n\t(string of 1s and 0s)\n",maxIntValue(NUM_BYTES_PER_UNSIGNED_INT));
}

/**
 * Displays each bit value (0 or 1) of an unsigned integer including leading zeros (zero-padded)
 *
 * @param x Unsigned integer value to display as binary string
 */
void printBits(unsigned int x) {
	unsigned int bitMask = (1 << (NUM_BITS_PER_UNSIGNED_INT - 1)); // Only Most Significant Bit is a '1'
	while(bitMask > 0) { // Loop through each bit
		printf("%i",(x & bitMask) / bitMask); // Bitwise AND operation (Dividing by Bit Mask yields 0 or 1)
		bitMask = bitMask >> 1; // Shift the single '1' bit to the right by one
	}
}

/**
 * Converts an unsigned integer between Big and Little Endian by altering the byte-order
 *
 * @param x Unsigned integer value to be converted
 * @return Returns the opposite (Big vs Little) Endian representation of x
 */
unsigned int swapBytes(unsigned int x) {
	unsigned int swappedValue = 0; // Stores the converted value

	// Create initial Right-Most Mask - Least Significant Byte is all 1s
	// Example 32-bit unsigned int: 00000000 00000000 00000000 11111111
	unsigned int rightMask = (1 << NUM_BITS_PER_BYTE) - 1;

	// Create initial Left-Most Mask - Most Significant Byte is all 1s
	// Example 32-bit unsigned int: 11111111 00000000 00000000 00000000
	unsigned int leftMask  = rightMask << (NUM_BITS_PER_UNSIGNED_INT - NUM_BITS_PER_BYTE);

	// Loop dynamically based on the machine-dependent number of bytes needing to be swapped
	// Each iteration uses Bitwise AND Masking on two of the original bytes, working from
	// the outside (Most Significant and Lease Significant Bytes) toward the center.
	// This loop executes once for every two bytes contained in the original integer value.
	int i;
	for(i = NUM_BYTES_PER_UNSIGNED_INT; i > 0; i -= 2) {
		if(DEBUG_MODE) {
			printf("Values Prior to swap #%i:\n",i);
			printf("\t  Original: ");
			printBits(x);
			printf("\n");
			printf("\t Left-Mask: ");
			printBits(leftMask);
			printf("\n");
			printf("\tRight-Mask: ");
			printBits(rightMask);
			printf("\n");
		}

		// Perform Bitwise AND operation for both Bit Masks
		// Each results in the integer value of the single byte in x that each Mask is targeting
		unsigned int leftMaskResult = x & leftMask;
		unsigned int rightMaskResult = x & rightMask;

		// Shift each of the "masked" byte values to their opposite location (Big <-> Little) Endian
		// and Add their new "shifted" values to the new "Endian Converted" variable (swappedValue)
		int numBytes_shift = i - 1;
		swappedValue += leftMaskResult >> (numBytes_shift * NUM_BITS_PER_BYTE);
		swappedValue += rightMaskResult << (numBytes_shift * NUM_BITS_PER_BYTE);
		if(DEBUG_MODE) {
			printf("\t    Result: ");
			printBits(swappedValue);
			printf("\n\n");
		}

		// Shift each Bit Mask "inward" to the next Byte
		leftMask = (leftMask >> NUM_BITS_PER_BYTE);
		rightMask = (rightMask << NUM_BITS_PER_BYTE);
	}
	return swappedValue; // Return "Endian Converted" value
}

/**
 * Converts a Binary string to it's decimal integer value
 *
 * @param x The binary string ('\0'-terminated char array) to be converted
 * @return Returns the integer value of x
 */
unsigned int btoi(char x[]) {
	char SET_BIT = '1'; // Valid character value for a bit that is "set"
	unsigned int intValue = 0; // Stores the integer representation of the binary string
	int numBitsLeftToProcess = strlen(x); // Get the number of binary string "bits" the user actually entered
	int bitPosition_power_of_2 = numBitsLeftToProcess - 1; // Set the correct "powers of two" value for initial bit
	int binStringIndex = 0; // Begin at first character of user-supplied binary string (Starting Index)

	// Step through each "bit" in the binary string, detecting each "set" bit and calculate
	// the summation of all of their appropriate integer values based on their position and
	// corresponding "powers of two" exponent value
	for(; numBitsLeftToProcess > 0; numBitsLeftToProcess--, bitPosition_power_of_2--, binStringIndex++) {
		if(x[binStringIndex] == SET_BIT) {
			intValue += (1 << bitPosition_power_of_2);
		}
	}
	if(DEBUG_MODE) {
		printf(" Binary String Value: %s\n",x);
		printf("  Decimal Conversion: %u\n",intValue);
	}
	return intValue;
}

/**
 * Displays formatted program results
 *
 * @param x The integer to be displayed
 * @param line This value should always be 1 when this function is called from outside itself
 *             This function will call itself provided the value of 2 so that Headings are only printed on line 1
 */
void printOutput(unsigned int x, int line) {
	// Check "line" argument to determine if Headings need to be displayed for this call
	if(line == 1) {
		printHeadings();
		printf("%-3s%-10u\t","x",x);
		printBits(x);
		printf("    0x%08x\n",x);
		printOutput(swapBytes(x),2);
	}
	else {
		printf("%-3s%-10u\t","x'",x);
		printBits(x);
		printf("    0x%08x\n",x);
	}
}

/**
* Displays table headings for formatted program results
*/
void printHeadings() {
	printf("%-13s\t%-36s%-11s","Decimal","Binary","Hexadecimal\n");
	int i;
	for(i = 0; i < 64; i++) {
		printf("-");
	}
	printf("\n");
}

/**
* Calculates and returns the maximum decimal value of an unsigned integer (machine dependent)
*
* @param numBytes The number of bytes used to store an unsigned integer
* @return Returns the Maximum integer value based on numBytes
*/
unsigned int maxIntValue(int numBytes) {
	int maxNumBits = numBytes * NUM_BITS_PER_BYTE;
	// In some cases, the Max values of an Unsigned Integer and an Unsigned Long are equal,
	// so the common formula: (2^n) - 1 | n = max number of bits, will still cause Overflow
	// if stored in an Unsigned Long.
	// The Max Value is essentially just the sum of the integer value of the Most Significant Bit
	// and the integer value of the Rest of the Bits. This results in all bits set to 1.
	unsigned int mostSigBit_value = (1 << (maxNumBits - 1)); // MSB Value
	unsigned int restOfBits_value = mostSigBit_value - 1; // Rest of bits Value
	unsigned int maxValue = mostSigBit_value + restOfBits_value; // All bits are "set"
	if(DEBUG_MODE) {
		printf("sizeof(unsigned int) --> %i\nmaxNumBits --> %i\n",numBytes,maxNumBits);
		printf("mostSigBit_value --> %u\ntemp2 --> %u\nmaxValue --> %u\n",mostSigBit_value,restOfBits_value,maxValue);
	}
	return maxValue;
}
