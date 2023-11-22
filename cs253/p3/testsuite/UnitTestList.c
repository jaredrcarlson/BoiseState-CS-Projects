/*
 * UnitTestList.c
 *	Description: Runs a series of Tests to Verify proper implementation of List methods
 *      Author: jcarlson <jaredcarlson@u.boisestate.edu>
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "Object.h"
#include <Node.h>
#include <List.h>
#include "Test.h"

/*
 * macro to mimic the functionality of assert() from <assert.h>. The difference is that this version doesn't exit the program entirely.
 * It will just break out of the current function (or test case in this context).
 */
#define myassert(expr) if(!(expr)){ fprintf(stderr, "\t[assertion failed] %s: %s\n", __PRETTY_FUNCTION__, __STRING(expr)); return 0; }

// --------------------------- Global Constants ------------------------------ //
// --- Testing Size Options --- //
static const int NUM_TESTS_SMALL = 10;
static const int NUM_TESTS_MEDIUM = 40; // Default: Medium
static const int NUM_TESTS_LARGE = 80;
static const int NUM_TESTS_XLARGE = 200;

// --- Preset Test Sizes --- //
// *** Maintain Balance between Testing Accuracy and Duration *** //
const int NUM_TESTS_SEARCH_LARGE_LIST = 4; // Total: 8 = 4(Item Found) + 4(Item Not Found)
const int LARGE_LIST_SIZE_START = 100; // Initial List of 100 Nodes
const int LARGE_LIST_SIZE_STEP = 100; // +100 --> Final List has 500 Nodes
const int NUM_TESTS_NULL_NODE = 5; // Total:

// --- Command Line Argument Options --- //
const char* sizeSmall = "-s";
const char* sizeMedium = "-m"; // Default: Medium
const char* sizeLarge = "-l";
const char* sizeXLarge = "-x";

const char* enableDebug = "-d"; // Default: Debug OFF
const char* outputFile = "-f"; // Default: Print to Standard Out

// --------------------------- Program Variables ----------------------------- //
// --- General --- //
struct list *testlist;
int testCount = 0;
int passCount = 0;
int testNumber = 0;
int listIsNull = 0;
int i, numNodes, nodeID, result, objKey;
char* objData;
struct node* newNode;
struct test* test;
struct object* searchItem;
char testName[100];
char testDesc[100];
int debugOn = 0;
char outputFilename[40];
char outputFilenameError[50];
int outputToFile = 0;

// --- Number of Tests --- //
int numTestsAddAtFront;
int numTestsAddAtRear;
int numTestsRemoveFront;
int numTestsRemoveRear;
int numTestsRemoveNode;
int numTestsReverseList;

// -------------------------- Method Declarations ---------------------------- //
// --- Process Command-Line Arguments --- //
void processCLA(int numArgs, char** userArgs);
int sizeParsed(char* arg);
int debugParsed(char* arg);
int outputFilenameParsed(char* arg1, char* arg2);

// --- Display Program Usage Message --- //
void printUsage();

// --- Execute All Tests --- //
void runUnitTests();
void runAddAtFrontTests();
void runAddAtRearTests();
void runRemoveFrontTests();
void runRemoveRearTests();
void runRemoveNodeTests();
void runSearchSmallListsTests();
void runSearchLargeListsTests();
void runReverseListTests();
void runNullNodeTests();
void runNullListTests();

// --- Print Statistics --- //
void printStats();

// --------------------------------- MAIN ------------------------------------ //
int main(int argc, char *argv[]) {
	processCLA((argc - 1), argv); // Process CLA
    if(outputToFile) {
    	freopen(outputFilename, "w", stdout);
    	freopen("STDERR_Report", "w", stderr);
    }
	runUnitTests(); // Run All Tests
	printStats(); // Show Statistics
    exit(0); // Exit Program
}

// ------------------------ Method Implementations --------------------------- //
void processCLA(int numArgs, char** userArgs) {

	if(numArgs > 0) {
		if(numArgs > 4) {
			fprintf(stderr,"Error! Number/Order of arguments is Invalid.\n");
			printUsage();
			exit(1); // Number/Order of arguments Invalid
		}
		else { // Valid Number of Args
			if(sizeParsed(userArgs[1])) {
				// Arg1 == Size
				if(numArgs > 1) {
					if(debugParsed(userArgs[2])) {
						// Arg2 == debug
						if(numArgs > 2 && numArgs == 4) {
							if(outputFilenameParsed(userArgs[3],userArgs[4])) {
								// Arg3,Arg4 == Output Filename
								return; // Parsing Complete [-size] [-d] [-f filename]
							}
							else {
								fprintf(stderr,"Error! (Arg3/Arg4) Invalid.\n");
								printUsage();
								exit(4); // Arg3,Arg4 Invalid
							}
						}
						else return; // Parsing Complete: [-size] [-d]
					}
					else if(numArgs > 2 && outputFilenameParsed(userArgs[2],userArgs[3])) {
						// Arg2,Arg3 == Output Filename
						return; // Parsing Complete: [-size] [-f filename]
					}
					else {
						fprintf(stderr,"Error! (Arg2/Arg3) Invalid.\n");
						printUsage();
						exit(3); // Arg2,Arg3 Invalid
					}
				}
				else return; // Parsing Complete: [-size]
			}
			else if(debugParsed(userArgs[1])) {
				// Arg1 == Debug
				if(numArgs > 1) {
					if(outputFilenameParsed(userArgs[2],userArgs[3])) {
						// Arg2,Arg3 == Output Filename
						return; // Parsing Complete: [-d] [-f filename]
					}
					else {
						fprintf(stderr,"Error! (Arg2/Arg3) Invalid.\n");
						printUsage();
						exit(3); // Arg2,Arg3 Invalid
					}
				}
				else return; // Parsing Complete: [-d]
			}
			else if(numArgs == 2 && outputFilenameParsed(userArgs[1],userArgs[2])) {
				// Arg1,Arg2 == Output Filename
				return; // Parsing Complete: [-f filename]
			}
			else {
				// Arg1 is Invalid
				fprintf(stderr,"Error! Arg1 is Invalid.\n");
				printUsage();
				exit(2); // Art1 is Invalid
			}
		}
	}
	else {
		// Use Default Test Size Values
		numTestsAddAtFront = numTestsAddAtRear = NUM_TESTS_MEDIUM; // # of "Add" Tests
		numTestsRemoveFront = numTestsRemoveRear = numTestsRemoveNode = NUM_TESTS_MEDIUM; // # of "Remove" Tests
		numTestsReverseList = NUM_TESTS_MEDIUM; // # of "Reverse" Tests
	}
}

int sizeParsed(char* arg) {
	if(strcmp(arg,sizeSmall) == 0) {
		numTestsAddAtFront = numTestsAddAtRear = NUM_TESTS_SMALL; // # of "Add" Tests
		numTestsRemoveFront = numTestsRemoveRear = numTestsRemoveNode = NUM_TESTS_SMALL; // # of "Remove" Tests
		numTestsReverseList = NUM_TESTS_SMALL;
		return 1;
	}
	else if(strcmp(arg,sizeMedium) == 0) {
		numTestsAddAtFront = numTestsAddAtRear = NUM_TESTS_MEDIUM; // # of "Add" Tests
		numTestsRemoveFront = numTestsRemoveRear = numTestsRemoveNode = NUM_TESTS_MEDIUM; // # of "Remove" Tests
		numTestsReverseList = NUM_TESTS_MEDIUM;
		return 1;
	}
	else if(strcmp(arg,sizeLarge) == 0) {
		numTestsAddAtFront = numTestsAddAtRear = NUM_TESTS_LARGE; // # of "Add" Tests
		numTestsRemoveFront = numTestsRemoveRear = numTestsRemoveNode = NUM_TESTS_LARGE; // # of "Remove" Tests
		numTestsReverseList = NUM_TESTS_LARGE;
		return 1;
	}
	else if(strcmp(arg,sizeXLarge) == 0) {
		numTestsAddAtFront = numTestsAddAtRear = NUM_TESTS_XLARGE; // # of "Add" Tests
		numTestsRemoveFront = numTestsRemoveRear = numTestsRemoveNode = NUM_TESTS_XLARGE; // # of "Remove" Tests
		numTestsReverseList = NUM_TESTS_XLARGE;
		return 1;
	}
	else {
		return 0;
	}
}

int debugParsed(char* arg) {
	if(strcmp(arg,enableDebug) == 0) {
		debugOn = 1;
		return 1;
	}
	else {
		return 0;
	}
}

int outputFilenameParsed(char* arg1, char* arg2) {
	int filenameIsValid = 1; // Add filename validation
	if(strcmp(arg1,outputFile) == 0 && filenameIsValid) {
		sprintf(outputFilename,arg2);
		outputToFile = 1;
		return 1;
	}
	else {
		return 0;
	}
}

// --- Display Program Usage Message --- //
void printUsage() {
	fprintf(stdout,"\n\n------------------------------------------------------------------------------------------\n");
	fprintf(stdout,"------------------------------- Program Usage --------------------------------------------\n");
	fprintf(stdout,"------------------------------------------------------------------------------------------\n");
	fprintf(stdout,"NAME\n");
	fprintf(stdout,"\tList Unit Testing - Runs a series of Unit Tests to verify List Method Implementations\n\n");

	fprintf(stdout,"SYNOPSIS\n");
	fprintf(stdout,"\tUnitTestList [-s | -m | -l | -x] [-d] [-f filename]\n\n");

	fprintf(stdout,"OPTIONS\n");
	fprintf(stdout,"\tTesting Size:\n");
	fprintf(stdout,"\t-s\t(Small) Testing Size: 10 Tests per basic method\n");
	fprintf(stdout,"\t-m\t(Medium) Testing Size: 40 Tests per basic method (default)\n");
	fprintf(stdout,"\t-l\t(Large) Testing Size: 80 Tests per basic method\n");
	fprintf(stdout,"\t-x\t(X-Large) Testing Size: 200 Tests per basic method\n\n");

	fprintf(stdout,"\tDebug:\n");
	fprintf(stdout,"\t-d\tEnable Debug Mode (Default:OFF)\n\n");

	fprintf(stdout,"\tOutput:\n");
	fprintf(stdout,"\t-f\tRedirect Output to a text file (Default:Standard Output)\n\n");

	fprintf(stdout,"\tExample Run: Perform Small Test with Debug sending output to text file 'myOutput.txt'\n");
	fprintf(stdout,"\tUnitTestList -s -d -f myOutput.txt\n");
}

// --- Execute All Tests --- //
void runUnitTests() {
    runAddAtFrontTests();
    runAddAtRearTests();
    runRemoveFrontTests();
    runRemoveRearTests();
    runRemoveNodeTests();
    runSearchSmallListsTests();
    runSearchLargeListsTests();
    runReverseListTests();
    runNullNodeTests();
    runNullListTests();
}

// --- Print Statistics --- //
void printStats() {
	fprintf(stdout,"\n===============================\n");
	fprintf(stdout, "Test Cases: %d\n", testCount);
	fprintf(stdout, "Passed: %d\n", passCount);
	fprintf(stdout, "Failed: %d\n", testCount - passCount);
	fprintf(stdout,"===============================\n\n");
}

// --- Show Info Methods --- //
void printTestInfo(char* testName, char *info) {
    fprintf(stdout, "%s\n%s\n", testName, info);
}
void printTestResult(char* testName, int passed) {
    if(passed) {
        fprintf(stdout, "%s ---> %s\n", "[PASSED]", testName);
    } else {
        fprintf(stdout, "%s ---> %s\n", "[FAILED]", testName);
    }
}
void beforeTest(char* testName, char* visualDescription) {
	fprintf(stdout,"\nTest Number: %i\n",++testNumber);
	if(debugOn) {
    	fprintf(stdout,"============================================================================\n");
    }
	printTestInfo(testName, visualDescription);
    if(!listIsNull) {
    	testlist = createList(equals, toString, freeObject);
    }
	if(debugOn) fprintf(stdout,"----------------------------------------------------------------------------\n");
    testCount++;
}
void afterTest(char* testName, int result) {
	if(debugOn) {
		fprintf(stdout,"----------------------------------------------------------------------------\n");
		fprintf(stdout,"Resulting List: ");
		printList(testlist);
		fprintf(stdout,"----------------------------------------------------------------------------\n");
	}
	printTestResult(testName, result);
    if(debugOn) fprintf(stdout,"============================================================================\n");
    if(!listIsNull) {
    	freeList(testlist);
    }
    passCount += result;
}

// ---------------------------- Helper Methods ------------------------------- //
struct node* createTestNode(int jobid) {
	char dataString[20];
	sprintf(dataString,"Key%i",jobid);
    struct object * job = createObject(jobid,dataString);
    struct node *node = createNode(job);
    return node;
}
void buildTestListWithNumNodes(struct list *list, struct node** nodes, int numNodes) {
	if(numNodes == 0) {
		return;
	}
	else {
		int headIndex = 1;
		int i;
		for(i = headIndex; i <= numNodes; i++) {
			nodes[i] = createTestNode(i);
			addAtRear(list,nodes[i]);
		}
	}
}
void buildTestListWithNumNodesContainingNode(struct list *list, struct node** nodes, int numNodes, struct node* node) {
	if(numNodes < 1 || node == NULL) {
		return;
	}
	else {
		struct object* nodeObj = node->obj;
		int nodeObjKey = nodeObj->key;
		int headIndex = 1;
		int i;
		for(i = headIndex; i <= numNodes; i++) {
			if(i == nodeObjKey) {
				addAtRear(list,node);
				nodes[i] = node;
			}
			else {
				nodes[i] = createTestNode(i);
				addAtRear(list,nodes[i]);
			}
		}
	}
}
void addAtFrontTestNode(struct node **nodes, int startIndex, int endIndex, struct node *newNode) {
	if(debugOn) {
		if(newNode == NULL) endIndex++;
		// Display Original List
		fprintf(stdout,"------> New Node Address: | %p |\n",(void *)newNode);
		fprintf(stdout,"------>   Original Order: |");
		if(endIndex == startIndex) { fprintf(stdout," EMPTY |"); }
		int index;
		for(index = startIndex; index <= endIndex - 1; index++) {
			fprintf(stdout," %p |",(void *)nodes[index]);
		}
		fprintf(stdout,"\n");
	}

	if(newNode != NULL) {
		// Shift Right
		int i;
		for(i = endIndex; i > startIndex; i--) {
			nodes[i] = nodes[i - 1];
		}
		nodes[startIndex] = newNode; // Add To Front
	}
	else endIndex--;

	if(debugOn) {
		// Display New List
		fprintf(stdout,"------>        New Order: |");
		if(endIndex < startIndex) { fprintf(stdout," EMPTY |"); }
		int index;
		for(index = startIndex; index <= endIndex; index++) {
				fprintf(stdout," %p |",(void *)nodes[index]);
		}
		fprintf(stdout,"\n");
	}
}
void addAtRearTestNode(struct node **nodes, int startIndex, int endIndex, struct node *newNode) {
	if(debugOn) {
		if(newNode == NULL) endIndex++;
		// Display Original List
		fprintf(stdout,"------> New Node Address: | %p |\n",(void *)newNode);
		fprintf(stdout,"------>   Original Order: |");
		if(endIndex == startIndex) { fprintf(stdout," EMPTY |"); }
		int index;
		for(index = startIndex; index <= endIndex - 1; index++) {
			fprintf(stdout," %p |",(void *)nodes[index]);
		}
		fprintf(stdout,"\n");
	}

	if(newNode != NULL) {
		nodes[endIndex] = newNode; // Add To Rear
	}
	else endIndex--;

	if(debugOn) {
		// Display New List
		fprintf(stdout,"------>        New Order: |");
		if(endIndex < startIndex) { fprintf(stdout," EMPTY |"); }
		int index;
		for(index = startIndex; index <= endIndex; index++) {
				fprintf(stdout," %p |",(void *)nodes[index]);
		}
		fprintf(stdout,"\n");
	}
}
struct node* removeFrontTestNode(struct node **nodes, int endIndex) {
	int headIndex = 1;
	struct node* headNode = nodes[headIndex];
	int index;
	for(index = headIndex; index < endIndex; index++) {
		nodes[index] = nodes[index + 1];
	}
	nodes[index] = NULL;

	return headNode;
}
struct node* removeRearTestNode(struct node **nodes, int endIndex) {
	struct node* tailNode = nodes[endIndex];
	nodes[endIndex] = NULL;
	return tailNode;
}
struct node* removeTestNode(struct node **nodes, int endIndex, struct node* node) {
	struct node* currNode;
	int index = 1;
	while(index <= endIndex) {
		currNode = nodes[index];
		if(currNode == node) {
			for(; index < endIndex; index++) {
				nodes[index] = nodes[index + 1];
			}
			nodes[index] = NULL;
			return currNode;
		}
		index++;
	}

	return NULL;
}
void reverseArray(struct node **array, int size) {
	struct node *temp;
	int lowerIndex;
	int upperIndex;
	for(lowerIndex = 1, upperIndex = size; lowerIndex < upperIndex; lowerIndex++, upperIndex--) {
		temp = array[lowerIndex];
		array[lowerIndex] = array[upperIndex];
		array[upperIndex] = temp;
	}
}

// --- List Verification --- //
int verifyHeadTail(struct list *list, struct node *head, struct node *tail) {
	if(debugOn) fprintf(stdout,"------> Checking Access Pointer to Head == (%p)\n",(void *)head);
	myassert(testlist->head == head)
	if(debugOn) fprintf(stdout,"------> Checking Access Pointer to Tail == (%p)\n",(void *)tail);
	myassert(testlist->tail == tail)
	return 1;
}
int verifyAllNodeLinks(struct list *list, struct node **nodes, int tailIndex) {
	if(debugOn) fprintf(stdout,"------> Checking All Node Links\n");
	int headIndex = 1;
	int i;
	struct node *currNode = list->head;
	if(debugOn) fprintf(stdout,"--------->  Checking Head->prev == (NULL <-- %p)\n",(void *)currNode);
	myassert(currNode->prev == NULL)

	if(tailIndex == headIndex) {
		if(debugOn) fprintf(stdout,"--------->  Checking Head->next == (%p --> NULL)\n",(void *)currNode);
		myassert(currNode->next == NULL);
	}
	else {
		if(debugOn) fprintf(stdout,"--------->  Checking Head->next == (%p --> %p)\n",(void *)currNode,(void *)nodes[headIndex+1]);
		myassert(currNode->next == nodes[headIndex + 1]);
		for(i = headIndex + 1; i < tailIndex; i++) {
			currNode = currNode->next;
			if(debugOn) fprintf(stdout,"---------> Checking Node%i->prev == (%p <-- %p)\n",i,(void *)nodes[i-1],(void *)currNode);
			myassert(currNode->prev == nodes[i - 1])
			if(debugOn) fprintf(stdout,"---------> Checking Node%i->next == (%p --> %p)\n",i,(void *)currNode,(void *)nodes[i+1]);
			myassert(currNode->next == nodes[i + 1])
		}
		currNode = currNode->next;
	}
	if(tailIndex == headIndex) {
		if(debugOn) fprintf(stdout,"--------->  Checking Tail->prev == (NULL <-- %p)\n",(void *)currNode);
		myassert(currNode->prev == NULL);
	}
	else {
		if(debugOn) fprintf(stdout,"--------->  Checking Tail->prev == (%p <-- %p)\n",(void *)nodes[tailIndex-1],(void *)currNode);
		myassert(currNode->prev == nodes[tailIndex - 1]);
	}
	if(debugOn) fprintf(stdout,"--------->  Checking Tail->next == (%p --> NULL)\n",(void *)currNode);
	myassert(currNode->next == NULL)
	return 1;
}

// --- General Testing Methods --- //
// - addAtFront - //
int addAtFrontWithNumNodes(int numNodes, struct node *newNode) {
	int headIndex = 1;
	int tailIndex = numNodes;
	struct node *testNodes[tailIndex + 2];

	// Build List
	if(numNodes < 1) {
		if(debugOn) fprintf(stdout,"---> Starting with EMPTY List\n");
	}
	else {
		if(debugOn) fprintf(stdout,"---> Building Base List\n");
		buildTestListWithNumNodes(testlist,testNodes,numNodes);

		// Verify Build
		if(debugOn) fprintf(stdout,"---> Verifying Build\n");
		myassert(getSize(testlist) == numNodes)
		verifyHeadTail(testlist,testNodes[headIndex],testNodes[tailIndex]);
		verifyAllNodeLinks(testlist,testNodes,tailIndex);
	}
	if(debugOn) {
		fprintf(stdout,"----------------------------------------------------------------------------\n");
		fprintf(stdout," Original List: ");
		printList(testlist);
		fprintf(stdout,"----------------------------------------------------------------------------\n");
	}
	// Add At Front and Verify New List
	if(debugOn) fprintf(stdout,"---> Adding To Front\n");
	addAtFront(testlist,newNode);
	int newTailIndex = tailIndex + 1;
	addAtFrontTestNode(testNodes,headIndex,newTailIndex,newNode);

	if(debugOn) fprintf(stdout,"---> Verifying Contents of Resulting List\n");
	myassert(getSize(testlist) == numNodes + 1)

	verifyHeadTail(testlist,testNodes[headIndex],testNodes[newTailIndex]);
	verifyAllNodeLinks(testlist,testNodes,newTailIndex);

    return 1;
}
int addAtFrontNullNode(int numNodes, struct node *newNode) {
	int headIndex = 1;
	int tailIndex = numNodes;
	struct node *testNodes[tailIndex + 2];

	// Build List
	if(numNodes < 1) {
		if(debugOn) fprintf(stdout,"---> Starting with EMPTY List\n");
	}
	else {
		if(debugOn) fprintf(stdout,"---> Building Base List\n");
		buildTestListWithNumNodes(testlist,testNodes,numNodes);

		// Verify Build
		if(debugOn) fprintf(stdout,"---> Verifying Build\n");
		myassert(getSize(testlist) == numNodes)
		verifyHeadTail(testlist,testNodes[headIndex],testNodes[tailIndex]);
		verifyAllNodeLinks(testlist,testNodes,tailIndex);
	}
	if(debugOn) {
		fprintf(stdout,"----------------------------------------------------------------------------\n");
		fprintf(stdout," Original List: ");
		printList(testlist);
		fprintf(stdout,"----------------------------------------------------------------------------\n");
	}

	// Add At Front and Verify New List == Base List
	if(debugOn) fprintf(stdout,"---> Adding To Front (NULL Node)\n");
	addAtFront(testlist,newNode);
	int newTailIndex = tailIndex;
	addAtFrontTestNode(testNodes,headIndex,newTailIndex,newNode);

	if(debugOn) fprintf(stdout,"---> Verifying Resulting List == Base List\n");
	myassert(getSize(testlist) == numNodes)
	if(numNodes < 1) {
		myassert(isEmpty(testlist))
		verifyHeadTail(testlist,NULL,NULL);
		if(debugOn) fprintf(stdout,"---> Resulting List is EMPTY\n");
	}
	else {
		verifyHeadTail(testlist,testNodes[1],testNodes[numNodes]);
		verifyAllNodeLinks(testlist,testNodes,numNodes);
	}

    return 1;
}

// - addAtRear - //
int addAtRearWithNumNodes(int numNodes, struct node *newNode) {
	int headIndex = 1;
	int tailIndex = numNodes;
	struct node *testNodes[tailIndex + 2];

	// Build List
	if(numNodes < 1) {
		if(debugOn) fprintf(stdout,"---> Starting with EMPTY List\n");
	}
	else {
		if(debugOn) fprintf(stdout,"---> Building Base List\n");
		buildTestListWithNumNodes(testlist,testNodes,numNodes);

		// Verify Build
		if(debugOn) fprintf(stdout,"---> Verifying Build\n");
		myassert(getSize(testlist) == numNodes)
		verifyHeadTail(testlist,testNodes[headIndex],testNodes[tailIndex]);
		verifyAllNodeLinks(testlist,testNodes,tailIndex);
	}
	if(debugOn) {
		fprintf(stdout,"----------------------------------------------------------------------------\n");
		fprintf(stdout," Original List: ");
		printList(testlist);
		fprintf(stdout,"----------------------------------------------------------------------------\n");
	}

	// Add At Rear
	if(debugOn) fprintf(stdout,"---> Adding To Rear\n");
	addAtRear(testlist,newNode);
	int newTailIndex = tailIndex + 1;
	addAtRearTestNode(testNodes,headIndex,newTailIndex,newNode);

	// Verify Contents of Resulting List
	if(debugOn) fprintf(stdout,"---> Verifying Contents of Resulting List\n");
	myassert(getSize(testlist) == numNodes + 1)

	verifyHeadTail(testlist,testNodes[headIndex],testNodes[newTailIndex]);
	verifyAllNodeLinks(testlist,testNodes,newTailIndex);

    return 1;
}
int addAtRearNullNode(int numNodes, struct node *newNode) {
	int headIndex = 1;
	int tailIndex = numNodes;
	struct node *testNodes[tailIndex + 2];

	// Build List
	if(numNodes < 1) {
		if(debugOn) fprintf(stdout,"---> Starting with EMPTY List\n");
	}
	else {
		if(debugOn) fprintf(stdout,"---> Building Base List\n");
		buildTestListWithNumNodes(testlist,testNodes,numNodes);

		// Verify Build
		if(debugOn) fprintf(stdout,"---> Verifying Build\n");
		myassert(getSize(testlist) == numNodes)
		verifyHeadTail(testlist,testNodes[headIndex],testNodes[tailIndex]);
		verifyAllNodeLinks(testlist,testNodes,tailIndex);
	}
	if(debugOn) {
		fprintf(stdout,"----------------------------------------------------------------------------\n");
		fprintf(stdout," Original List: ");
		printList(testlist);
		fprintf(stdout,"----------------------------------------------------------------------------\n");
	}

	// Add At Rear and Verify New List == Base List
	if(debugOn) fprintf(stdout,"---> Adding To Rear (NULL Node)\n");
	addAtRear(testlist,newNode);
	int newTailIndex = tailIndex;
	addAtRearTestNode(testNodes,headIndex,newTailIndex,newNode);

	if(debugOn) fprintf(stdout,"---> Verifying Resulting List == Base List\n");
	myassert(getSize(testlist) == numNodes)
	if(numNodes < 1) {
		myassert(isEmpty(testlist))
		verifyHeadTail(testlist,NULL,NULL);
		if(debugOn) fprintf(stdout,"---> Resulting List is EMPTY\n");
	}
	else {
		verifyHeadTail(testlist,testNodes[1],testNodes[numNodes]);
		verifyAllNodeLinks(testlist,testNodes,numNodes);
	}

    return 1;
}

// - removeFront - //
int removeFrontWithNumNodes(int numNodes) {
	if(numNodes < 1) {
		if(debugOn) fprintf(stdout,"---> List is EMPTY - Nothing to Remove\n");
	}
	else {
		// Build List
		int headIndex = 1;
		int tailIndex = numNodes;
		struct node *testNodes[tailIndex + 2];
		if(debugOn) fprintf(stdout,"---> Building Base List\n");
		buildTestListWithNumNodes(testlist,testNodes,numNodes);

		// Verify Build
		if(debugOn) fprintf(stdout,"---> Verifying Build\n");
		myassert(getSize(testlist) == numNodes)
		verifyHeadTail(testlist,testNodes[headIndex],testNodes[tailIndex]);
		verifyAllNodeLinks(testlist,testNodes,tailIndex);

		if(debugOn) {
			fprintf(stdout,"----------------------------------------------------------------------------\n");
			fprintf(stdout," Original List: ");
			printList(testlist);
			fprintf(stdout,"----------------------------------------------------------------------------\n");
		}

		// Remove Front
		if(debugOn) fprintf(stdout,"---> Removing Front\n");
		struct node* removedNode = removeFront(testlist);
		struct node* correctNode = removeFrontTestNode(testNodes,tailIndex);

		// Verify Correct Node was Removed
		if(debugOn) fprintf(stdout,"---> Verifying Correct Node was Removed\n");
		myassert((void *)removedNode == (void *)correctNode)

		// Free removed Node
		freeNode(removedNode,freeObject);

		// Verify Contents of New List
		if(debugOn) fprintf(stdout,"---> Verifying Contents of Resulting List\n");
		myassert(getSize(testlist) == numNodes - 1)
		if((numNodes - 1) < 1) {
			myassert(isEmpty(testlist))
			verifyHeadTail(testlist,testNodes[1],testNodes[1]);
			if(debugOn) fprintf(stdout,"---> Resulting List is EMPTY\n");
		}
		else {
			verifyHeadTail(testlist,testNodes[headIndex],testNodes[tailIndex - 1]);
			verifyAllNodeLinks(testlist,testNodes,tailIndex - 1);
		}
	}

    return 1;
}

// - removeRear - //
int removeRearWithNumNodes(int numNodes) {
	if(numNodes < 1) {
		if(debugOn) fprintf(stdout,"---> List is EMPTY - Nothing to Remove\n");
	}
	else {
		// Build List
		int headIndex = 1;
		int tailIndex = numNodes;
		struct node *testNodes[tailIndex + 2];

		if(debugOn) fprintf(stdout,"---> Building Base List\n");
		buildTestListWithNumNodes(testlist,testNodes,numNodes);

		// Verify Build
		if(debugOn) fprintf(stdout,"---> Verifying Build\n");
		myassert(getSize(testlist) == numNodes)
		verifyHeadTail(testlist,testNodes[headIndex],testNodes[tailIndex]);
		verifyAllNodeLinks(testlist,testNodes,tailIndex);

		if(debugOn) {
			fprintf(stdout,"----------------------------------------------------------------------------\n");
			fprintf(stdout," Original List: ");
			printList(testlist);
			fprintf(stdout,"----------------------------------------------------------------------------\n");
		}

		// Remove Rear
		if(debugOn) fprintf(stdout,"---> Removing Rear\n");
		struct node* removedNode = removeRear(testlist);
		struct node* correctNode = removeRearTestNode(testNodes,tailIndex);

		// Verify Correct Node was Removed
		if(debugOn) fprintf(stdout,"---> Verifying Correct Node was Removed\n");
		myassert((void *)removedNode == (void *)correctNode)

		// Free removed Node
		freeNode(removedNode,freeObject);

		// Verify Contents of New List
		if(debugOn) fprintf(stdout,"---> Verifying Contents of Resulting List\n");
		myassert(getSize(testlist) == numNodes - 1)
		if((numNodes - 1) < 1) {
			myassert(isEmpty(testlist))
			verifyHeadTail(testlist,testNodes[1],testNodes[1]);
			if(debugOn) fprintf(stdout,"---> Resulting List is EMPTY\n");
		}
		else {
			verifyHeadTail(testlist,testNodes[headIndex],testNodes[tailIndex - 1]);
			verifyAllNodeLinks(testlist,testNodes,tailIndex - 1);
		}
	}

	return 1;
}

// - removeNode - //
int removeNodeWithNumNodes(int numNodes,struct node* node) {
	if(numNodes < 1) {
		if(debugOn) fprintf(stdout,"---> List is EMPTY: Cannot Remove Node from EMPTY List\n");
		// Free Node
		freeNode(node,freeObject);
	}
	else {
		struct node *testNodes[numNodes + 1];

		// Build List
		if(debugOn) fprintf(stdout,"---> Building Base List Containing Node: %p\n",(void *)node);
		buildTestListWithNumNodesContainingNode(testlist,testNodes,numNodes,node);

		// Verify Build
		if(debugOn) fprintf(stdout,"---> Verifying Build\n");
		myassert(getSize(testlist) == numNodes);
		verifyHeadTail(testlist,testNodes[1],testNodes[numNodes]);
		verifyAllNodeLinks(testlist,testNodes,numNodes);

		if(debugOn) {
			fprintf(stdout,"----------------------------------------------------------------------------\n");
			fprintf(stdout," Original List: ");
			printList(testlist);
			fprintf(stdout,"----------------------------------------------------------------------------\n");
		}

		// Remove Node
		if(debugOn) fprintf(stdout,"---> Removing Node with Address: %p\n",(void *)node);
		struct node* removedNode = removeNode(testlist,node);
		removeTestNode(testNodes,numNodes,node);

		// Verify Removed Node
		if(debugOn) fprintf(stdout,"---> Verifying Correct Node was Removed\n");
		myassert((void *)removedNode == (void *)node);

		// Free Removed Node
		freeNode(removedNode,freeObject);

		// Verify Contents of Resulting List
		if(debugOn) fprintf(stdout,"---> Verifying Contents of Resulting List\n");
		myassert(getSize(testlist) == numNodes - 1)
		if((numNodes - 1) < 1) {
			myassert(isEmpty(testlist))
			verifyHeadTail(testlist,testNodes[1],testNodes[1]);
			if(debugOn) fprintf(stdout,"---> Resulting List is EMPTY\n");
		}
		else {
			verifyHeadTail(testlist,testNodes[1],testNodes[numNodes-1]);
			verifyAllNodeLinks(testlist,testNodes,(numNodes-1));
		}
	}

	return 1;
}
int removeNodeNullNode(int numNodes,struct node* node) {
	if(numNodes < 1) {
		if(debugOn) fprintf(stdout,"---> List is EMPTY: Cannot Remove Node from EMPTY List\n");
	}
	else {
		struct node *testNodes[numNodes + 1];

		// Build List
		if(debugOn) fprintf(stdout,"---> Building Base List\n");
		buildTestListWithNumNodes(testlist,testNodes,numNodes);

		// Verify Build
		if(debugOn) fprintf(stdout,"---> Verifying Build\n");
		myassert(getSize(testlist) == numNodes);
		verifyHeadTail(testlist,testNodes[1],testNodes[numNodes]);
		verifyAllNodeLinks(testlist,testNodes,numNodes);

		if(debugOn) {
			fprintf(stdout,"----------------------------------------------------------------------------\n");
			fprintf(stdout," Original List: ");
			printList(testlist);
			fprintf(stdout,"----------------------------------------------------------------------------\n");
		}

		// Remove Node
		if(debugOn) fprintf(stdout,"---> Removing Node: (NULL Node)\n");
		struct node* removedNode = removeNode(testlist,node);

		// Verify Removed Node == NULL
		if(debugOn) fprintf(stdout,"---> Verifying Returned Node == NULL\n");
		myassert(removedNode == NULL);

		// Verify Contents of Resulting List
		if(debugOn) fprintf(stdout,"---> Verifying Resulting List == Base List\n");
		myassert(getSize(testlist) == numNodes)
		if(numNodes < 1) {
			myassert(isEmpty(testlist))
			verifyHeadTail(testlist,NULL,NULL);
			if(debugOn) fprintf(stdout,"---> Resulting List is EMPTY\n");
		}
		else {
			verifyHeadTail(testlist,testNodes[1],testNodes[numNodes]);
			verifyAllNodeLinks(testlist,testNodes,numNodes);
		}
	}

	return 1;
}

// - search - //
int searchWithNumNodes(int numNodes, struct object* obj) {
	// Build List
	if(numNodes < 1) {
		if(debugOn) fprintf(stdout,"---> Starting with EMPTY List\n");
	}
	else {
		if(debugOn) fprintf(stdout,"---> Building Base List\n");
	}
	struct node *testNodes[numNodes + 1];
	buildTestListWithNumNodes(testlist,testNodes,numNodes);

	if(numNodes > 0) {
		// Verify Build
		if(debugOn) fprintf(stdout,"---> Verifying Build\n");
		myassert(getSize(testlist) == numNodes);
		verifyHeadTail(testlist,testNodes[1],testNodes[numNodes]);
		verifyAllNodeLinks(testlist,testNodes,numNodes);
	}

	if(debugOn) {
		fprintf(stdout,"----------------------------------------------------------------------------\n");
		fprintf(stdout," Original List: ");
		printList(testlist);
		fprintf(stdout,"----------------------------------------------------------------------------\n");
	}

	// Search List
	int nodeID = obj->key;
	if(debugOn) fprintf(stdout,"---> Searching List for Node containing key: %i (Node%i)\n",nodeID,nodeID);
	struct node* returnedNode = search(testlist,obj);
	if(nodeID < 1 || nodeID > numNodes) {
		myassert(returnedNode == NULL)
		if(debugOn) fprintf(stdout,"------> Search Item (key: %i) was Not Found\n",nodeID);
	}
	else {
		myassert((void *)returnedNode == (void *)testNodes[nodeID])
		if(debugOn) fprintf(stdout,"------> Search Item (key: %i) Found in Node%i with Address: %p\n",nodeID,nodeID,(void *)returnedNode);
	}

	return 1;
}

// - reverseList - //
int reverseListWithNumNodes(int numNodes) {
	if(numNodes < 1) {
		if(debugOn) fprintf(stdout,"---> Starting with EMPTY List\n");
	}
	else {
		// Build List
		if(debugOn) fprintf(stdout,"---> Building Base List\n");
		struct node *testNodes[numNodes + 1];
		buildTestListWithNumNodes(testlist,testNodes,numNodes);

		// Verify Build
		if(debugOn) fprintf(stdout,"---> Verifying Build\n");
		myassert(getSize(testlist) == numNodes);
		verifyHeadTail(testlist,testNodes[1],testNodes[numNodes]);
		verifyAllNodeLinks(testlist,testNodes,numNodes);

		if(debugOn) {
			fprintf(stdout,"----------------------------------------------------------------------------\n");
			fprintf(stdout," Original List: ");
			printList(testlist);
			fprintf(stdout,"----------------------------------------------------------------------------\n");
		}

		// Reverse List
		if(debugOn) fprintf(stdout,"---> Reversing List\n");
		reverseList(testlist);
		reverseArray(testNodes,numNodes);

		// Verify Contents of New List
		if(debugOn) fprintf(stdout,"---> Verifying Contents of Resulting List\n");
		myassert(getSize(testlist) == numNodes);
		verifyHeadTail(testlist,testNodes[1],testNodes[numNodes]);
		verifyAllNodeLinks(testlist,testNodes,numNodes);
	}

	return 1;
}

// --- Specific Testing Routines --- //

// - addAtFront - //
void runAddAtFrontTests() {
	for(i = 0; i < numTestsAddAtFront; i++) {
		sprintf(testName,"<addAtFront> - Base List Size: %i",i);
		switch (i) {
		case 0:
			sprintf(testDesc,"{ } --> addAtFront(1) --> {1}");
			break;
		case 1:
			sprintf(testDesc,"{1} --> addAtFront(2) --> {2,1}");
			break;
		case 2:
			sprintf(testDesc,"{1,2} --> addAtFront(3) --> {3,1,2}");
			break;
		case 3:
			sprintf(testDesc, "{1,2,3} --> addAtFront(4) --> {4,1,2,3}");
			break;
		default:
			sprintf(testDesc,"{1, ... ,%i} --> addAtFront(%i) --> {%i,1, ... ,%i}",i,i+1,i+1,i);
		}
		test = newTest(testNumber);
		setName(test,testName);
		setDesc(test,testDesc);
		beforeTest(testName,testDesc);
		numNodes = i;
		nodeID = numNodes + 1;
		newNode = createTestNode(nodeID);
		result = addAtFrontWithNumNodes(numNodes,newNode);
		afterTest(testName, result);
		free(test);
	}
}

// - addAtRear - //
void runAddAtRearTests() {
	for(i = 0; i < numTestsAddAtRear; i++) {
		sprintf(testName,"<addAtRear> - Base List Size: %i",i);
		switch (i) {
		case 0:
			sprintf(testDesc,"{ } --> addAtRear(1) --> {1}");
			break;
		case 1:
			sprintf(testDesc,"{1} --> addAtRear(2) --> {1,2}");
			break;
		case 2:
			sprintf(testDesc,"{1,2} --> addAtRear(3) --> {1,2,3}");
			break;
		case 3:
			sprintf(testDesc, "{1,2,3} --> addAtRear(4) --> {1,2,3,4}");
			break;
		default:
			sprintf(testDesc,"{1, ... ,%i} --> addAtRear(%i) --> {1, ... ,%i,%i}",i,i+1,i,i+1);
		}
		test = newTest(testNumber);
		setName(test,testName);
		setDesc(test,testDesc);
		beforeTest(testName,testDesc);
		numNodes = i;
		nodeID = numNodes + 1;
		newNode = createTestNode(nodeID);
		result = addAtRearWithNumNodes(numNodes,newNode);
		afterTest(testName, result);
		free(test);
	}
}

// - removeFront - //
void runRemoveFrontTests() {
	for(i = 0; i < numTestsRemoveFront; i++) {
		sprintf(testName,"<removeFront> - Base List Size: %i",i);
		switch (i) {
		case 0:
			sprintf(testDesc,"{ } --> removeFront() --> { }");
			break;
		case 1:
			sprintf(testDesc,"{1} --> removeFront() --> { }");
			break;
		case 2:
			sprintf(testDesc,"{1,2} --> removeFront() --> {2,3}");
			break;
		case 3:
			sprintf(testDesc, "{1,2,3} --> removeFront() --> {2,3,4}");
			break;
		default:
			sprintf(testDesc,"{1, ... ,%i} --> removeFront() --> {2, ... ,%i}",i,i);
		}
		test = newTest(testNumber);
		setName(test,testName);
		setDesc(test,testDesc);
		beforeTest(testName,testDesc);
		numNodes = i;
		result = removeFrontWithNumNodes(numNodes);
		afterTest(testName, result);
		free(test);
	}
}

// - removeRear - //
void runRemoveRearTests() {
	for(i = 0; i < numTestsRemoveRear; i++) {
		sprintf(testName,"<removeRear> - Base List Size: %i",i);
		switch (i) {
		case 0:
			sprintf(testDesc,"{ } --> removeRear() --> { }");
			break;
		case 1:
			sprintf(testDesc,"{1} --> removeRear() --> { }");
			break;
		case 2:
			sprintf(testDesc,"{1,2} --> removeRear() --> {1,2}");
			break;
		case 3:
			sprintf(testDesc, "{1,2,3} --> removeRear() --> {1,2,3}");
			break;
		default:
			sprintf(testDesc,"{1, ... ,%i} --> removeRear() --> {1, ... ,%i}",i,i-1);
		}
		test = newTest(testNumber);
		setName(test,testName);
		setDesc(test,testDesc);
		beforeTest(testName,testDesc);
		numNodes = i;
		result = removeRearWithNumNodes(numNodes);
		afterTest(testName, result);
		free(test);
	}
}

// - removeNode - //
void runRemoveNodeTests() {
	for(i = 1; i < numTestsRemoveNode; i++) {
		int randMin = 1;
		int randMax = i;
		int randNodeID = (randMax > randMin) ? ( (rand() % randMax) + randMin ) : 1;
		int* ri = &randNodeID;
		if(i < 2) {
			sprintf(testName,"<removeNode> - Base List Size: %i",i);
		}
		else {
			sprintf(testName,"<removeNode> - Base List Size: %i\n <Removing Randomly Chosen (Node%i)>",i,*ri);
		}
		switch (i) {
		case 0:
			sprintf(testDesc,"{ } --> removeNode() --> { }");
			break;
		case 1:
			sprintf(testDesc,"{1} --> removeNode(1) --> { }");
			break;
		case 2:
			if(*ri == 1) {
				sprintf(testDesc,"{1,2} --> removeNode(%i) --> {2}",*ri);
			}
			else {
				sprintf(testDesc,"{1,2} --> removeNode(%i) --> {1}",*ri);
			}
			break;
		case 3:
			if(*ri == 1) {
				sprintf(testDesc,"{1,2,3} --> removeNode(%i) --> {2,3}",*ri);
			}
			else if(*ri == 2) {
				sprintf(testDesc,"{1,2,3} --> removeNode(%i) --> {1,3}",*ri);
			}
			else {
				sprintf(testDesc,"{1,2,3} --> removeNode(%i) --> {1,2}",*ri);
			}
			break;
		case 4:
			if(*ri == 1) {
				sprintf(testDesc,"{1,2,3,4} --> removeNode(%i) --> {2,3,4}",*ri);
			}
			else if(*ri == 2) {
				sprintf(testDesc,"{1,2,3,4} --> removeNode(%i) --> {1,3,4}",*ri);
			}
			else if(*ri == 3) {
				sprintf(testDesc,"{1,2,3,4} --> removeNode(%i) --> {1,2,4}",*ri);
			}
			else {
				sprintf(testDesc,"{1,2,3,4} --> removeNode(%i) --> {1,2,3}",*ri);
			}
			break;
		default:
			if(*ri == 1) {
				sprintf(testDesc,"{1,2, ... ,%i} --> removeNode(1) --> {2, ... ,%i}",i,i);
			}
			else if(*ri == i) {
				sprintf(testDesc,"{1, ... ,%i,%i} --> removeNode(%i) --> {1, ... ,%i}",i-1,i,i,i-1);
			}
			else {
				sprintf(testDesc,"{1, ... ,%i,%i,%i, ... ,%i} --> removeNode(%i) --> {1, ... ,%i,%i, ... ,%i}",(*ri)-1,*ri,(*ri)+1,i,*ri,(*ri)-1,(*ri)+1,i);
			}
		}
		test = newTest(testNumber);
		setName(test,testName);
		setDesc(test,testDesc);
		beforeTest(testName,testDesc);
		numNodes = i;
		char dataString[20];
		sprintf(dataString,"Key%i",randNodeID);
		struct object* randObj = createObject(randNodeID,dataString);
		struct node* node = createNode(randObj);
		result = removeNodeWithNumNodes(numNodes,node);
		afterTest(testName, result);
		free(test);
	}
}

// - search (Small Lists) - //
void runSearchSmallListsTests() {
	// --- EMPTY (search) --- //
	numNodes = 0;
	objKey = 1;
	objData = "search Item 1";
	searchItem = createObject(objKey,objData);
	sprintf(testName,"<search> - EMPTY List - Searching for Node with Key: %i",objKey);
	sprintf(testDesc,"{ } --> search(objKey:%i) --> Returns NULL (Not Found)",objKey);
	test = newTest(testNumber);
	setName(test,testName);
	setDesc(test,testDesc);
	beforeTest(testName,testDesc);
	result = searchWithNumNodes(numNodes,searchItem);
	afterTest(testName, result);
	freeObject(searchItem);
	free(test);

	numNodes = 0;
	objKey = 2;
	objData = "search Item 2";
	searchItem = createObject(objKey,objData);
	sprintf(testName,"<search> - EMPTY List - Searching for Node with Key: %i",objKey);
	sprintf(testDesc,"{ } --> search(objKey:%i) --> Returns NULL (Not Found)",objKey);
	test = newTest(testNumber);
	setName(test,testName);
	setDesc(test,testDesc);
	beforeTest(testName,testDesc);
	result = searchWithNumNodes(numNodes,searchItem);
	afterTest(testName, result);
	freeObject(searchItem);
	free(test);

	// --- Single Node (search) --- //
	numNodes = 1;
	objKey = 1;
	objData = "search Item 1";
	searchItem = createObject(objKey,objData);
	sprintf(testName,"<search> - List Size: %i --> Searching for Node with Key: %i",numNodes,objKey);
	sprintf(testDesc,"{1} --> search(objKey:%i) --> Returns Node%i",objKey,objKey);
	test = newTest(testNumber);
	setName(test,testName);
	setDesc(test,testDesc);
	beforeTest(testName,testDesc);
	result = searchWithNumNodes(numNodes,searchItem);
	afterTest(testName, result);
	freeObject(searchItem);
	free(test);

	numNodes = 1;
	objKey = 2;
	objData = "search Item 2";
	searchItem = createObject(objKey,objData);
	sprintf(testName,"<search> - List Size: %i --> Searching for Node with Key: %i",numNodes,objKey);
	sprintf(testDesc,"{1} --> search(objKey:%i) --> Returns NULL (Not Found)",objKey);
	test = newTest(testNumber);
	setName(test,testName);
	setDesc(test,testDesc);
	beforeTest(testName,testDesc);
	result = searchWithNumNodes(numNodes,searchItem);
	afterTest(testName, result);
	freeObject(searchItem);
	free(test);

	// --- Two Nodes (search) --- //
	numNodes = 2;
	objKey = 1;
	objData = "search Item 1";
	searchItem = createObject(objKey,objData);
	sprintf(testName,"<search> - List Size: %i --> Searching for Node with Key: %i",numNodes,objKey);
	sprintf(testDesc,"{1,2} --> search(objKey:%i) --> Returns Node%i",objKey,objKey);
	test = newTest(testNumber);
	setName(test,testName);
	setDesc(test,testDesc);
	beforeTest(testName,testDesc);
	result = searchWithNumNodes(numNodes,searchItem);
	afterTest(testName, result);
	freeObject(searchItem);
	free(test);

	numNodes = 2;
	objKey = 2;
	objData = "search Item 2";
	searchItem = createObject(objKey,objData);
	sprintf(testName,"<search> - List Size: %i --> Searching for Node with Key: %i",numNodes,objKey);
	sprintf(testDesc,"{1,2} --> search(objKey:%i) --> Returns Node%i",objKey,objKey);
	test = newTest(testNumber);
	setName(test,testName);
	setDesc(test,testDesc);
	beforeTest(testName,testDesc);
	result = searchWithNumNodes(numNodes,searchItem);
	afterTest(testName, result);
	freeObject(searchItem);
	free(test);

	numNodes = 2;
	objKey = 3;
	objData = "search Item 3";
	searchItem = createObject(objKey,objData);
	sprintf(testName,"<search> - List Size: %i --> Searching for Node with Key: %i",numNodes,objKey);
	sprintf(testDesc,"{1,2} --> search(objKey:%i) --> Returns NULL (Not Found)",objKey);
	test = newTest(testNumber);
	setName(test,testName);
	setDesc(test,testDesc);
	beforeTest(testName,testDesc);
	result = searchWithNumNodes(numNodes,searchItem);
	afterTest(testName, result);
	freeObject(searchItem);
	free(test);

	// --- Three Nodes (search) --- //
	numNodes = 3;
	objKey = 1;
	objData = "search Item 1";
	searchItem = createObject(objKey,objData);
	sprintf(testName,"<search> - List Size: %i --> Searching for Node with Key: %i",numNodes,objKey);
	sprintf(testDesc,"{1,2,3} --> search(objKey:%i) --> Returns Node%i",objKey,objKey);
	test = newTest(testNumber);
	setName(test,testName);
	setDesc(test,testDesc);
	beforeTest(testName,testDesc);
	result = searchWithNumNodes(numNodes,searchItem);
	afterTest(testName, result);
	freeObject(searchItem);
	free(test);

	numNodes = 3;
	objKey = 2;
	objData = "search Item 2";
	searchItem = createObject(objKey,objData);
	sprintf(testName,"<search> - List Size: %i --> Searching for Node with Key: %i",numNodes,objKey);
	sprintf(testDesc,"{1,2,3} --> search(objKey:%i) --> Returns Node%i",objKey,objKey);
	test = newTest(testNumber);
	setName(test,testName);
	setDesc(test,testDesc);
	beforeTest(testName,testDesc);
	result = searchWithNumNodes(numNodes,searchItem);
	afterTest(testName, result);
	freeObject(searchItem);
	free(test);

	numNodes = 3;
	objKey = 3;
	objData = "search Item 3";
	searchItem = createObject(objKey,objData);
	sprintf(testName,"<search> - List Size: %i --> Searching for Node with Key: %i",numNodes,objKey);
	sprintf(testDesc,"{1,2,3} --> search(objKey:%i) --> Returns Node%i",objKey,objKey);
	test = newTest(testNumber);
	setName(test,testName);
	setDesc(test,testDesc);
	beforeTest(testName,testDesc);
	result = searchWithNumNodes(numNodes,searchItem);
	afterTest(testName, result);
	freeObject(searchItem);
	free(test);

	numNodes = 3;
	objKey = 4;
	objData = "search Item 4";
	searchItem = createObject(objKey,objData);
	sprintf(testName,"<search> - List Size: %i --> Searching for Node with Key: %i",numNodes,objKey);
	sprintf(testDesc,"{1,2,3} --> search(objKey:%i) --> Returns NULL (Not Found)",objKey);
	test = newTest(testNumber);
	setName(test,testName);
	setDesc(test,testDesc);
	beforeTest(testName,testDesc);
	result = searchWithNumNodes(numNodes,searchItem);
	afterTest(testName, result);
	freeObject(searchItem);
	free(test);
}

// - search (Large Lists) - //
void runSearchLargeListsTests() {
	// - Search Item Found - //
	for(i = 0; i < NUM_TESTS_SEARCH_LARGE_LIST; i++) {
		numNodes = LARGE_LIST_SIZE_START + (LARGE_LIST_SIZE_STEP * i);
		int randMin = 1;
		objKey = (rand() % numNodes) + randMin;
		objData = "Data";
		searchItem = createObject(objKey,objData);
		sprintf(testName,"<search> - List Size: %i --> Randomly Chosen Item (Not in List) with Key: %i",numNodes,objKey);
		sprintf(testDesc,"{1, ... ,%i} --> search(objKey:%i) --> Returns Node%i",numNodes,objKey,objKey);
		test = newTest(testNumber);
		setName(test,testName);
		setDesc(test,testDesc);
		beforeTest(testName,testDesc);
		result = searchWithNumNodes(numNodes,searchItem);
		afterTest(testName, result);
		freeObject(searchItem);
		free(test);
	}

	// - Search Item Not Found - //
	for(i = 0; i < NUM_TESTS_SEARCH_LARGE_LIST; i++) {
		numNodes = LARGE_LIST_SIZE_START + (LARGE_LIST_SIZE_STEP * i);
		int randMin = numNodes + 1;
		objKey = (rand() % numNodes) + randMin;
		objData = "Data";
		searchItem = createObject(objKey,objData);
		sprintf(testName,"<search> - List Size: %i --> Randomly Chosen Item (In List) with Key: %i",numNodes,objKey);
		sprintf(testDesc,"{1, ... ,%i} --> search(objKey:%i) --> Returns NULL (Not Found)",numNodes,objKey);
		test = newTest(testNumber);
		setName(test,testName);
		setDesc(test,testDesc);
		beforeTest(testName,testDesc);
		result = searchWithNumNodes(numNodes,searchItem);
		afterTest(testName, result);
		freeObject(searchItem);
		free(test);
	}
}

// - reverseList - //
void runReverseListTests() {
	for(i = 0; i < numTestsReverseList; i++) {
		sprintf(testName,"<reverseList> - Base List Size: %i",i);
		switch (i) {
		case 0:
			sprintf(testDesc,"{ } --> reverseList() --> { }");
			break;
		case 1:
			sprintf(testDesc,"{1} --> reverseList() --> {1}");
			break;
		case 2:
			sprintf(testDesc,"{1,2} --> reverseList() --> {2,1}");
			break;
		case 3:
			sprintf(testDesc, "{1,2,3} --> reverseList() --> {3,2,1}");
			break;
		default:
			sprintf(testDesc,"{1, ... ,%i} --> reverseList() --> {%i, ... ,1}",i,i);
		}
		test = newTest(testNumber);
		setName(test,testName);
		setDesc(test,testDesc);
		beforeTest(testName,testDesc);
		numNodes = i;
		result = reverseListWithNumNodes(numNodes);
		afterTest(testName, result);
		free(test);
	}
}

// - NULL Node - //
void runNullNodeTests() {
	// --- addAtFront (NULL Node) --- //
	for(i = 0; i < NUM_TESTS_NULL_NODE; i++) {
		sprintf(testName,"<NULL Node - addAtFront> - Base List Size: %i",i);
		switch (i) {
		case 0:
			sprintf(testDesc,"{ } --> addAtFront(NULL) --> { }");
			break;
		case 1:
			sprintf(testDesc,"{1} --> addAtFront(NULL) --> {1}");
			break;
		case 2:
			sprintf(testDesc,"{1,2} --> addAtFront(NULL) --> {1,2}");
			break;
		case 3:
			sprintf(testDesc, "{1,2,3} --> addAtFront(NULL) --> {1,2,3}");
			break;
		default:
			sprintf(testDesc,"{1, ... ,%i} --> addAtFront(NULL) --> {1, ... ,%i}",i,i);
		}
		test = newTest(testNumber);
		setName(test,testName);
		setDesc(test,testDesc);
		beforeTest(testName,testDesc);
		numNodes = i;
		newNode = NULL;
		result = addAtFrontNullNode(numNodes,newNode);
		afterTest(testName, result);
		free(test);
	}

	// --- addAtRear (NULL Node) --- //
	for(i = 0; i < NUM_TESTS_NULL_NODE; i++) {
		sprintf(testName,"<NULL Node - addAtRear> - Base List Size: %i",i);
		switch (i) {
		case 0:
			sprintf(testDesc,"{ } --> addAtRear(NULL) --> { }");
			break;
		case 1:
			sprintf(testDesc,"{1} --> addAtRear(NULL) --> {1}");
			break;
		case 2:
			sprintf(testDesc,"{1,2} --> addAtRear(NULL) --> {1,2}");
			break;
		case 3:
			sprintf(testDesc, "{1,2,3} --> addAtRear(NULL) --> {1,2,3}");
			break;
		default:
			sprintf(testDesc,"{1, ... ,%i} --> addAtRear(NULL) --> {1, ... ,%i}",i,i);
		}
		test = newTest(testNumber);
		setName(test,testName);
		setDesc(test,testDesc);
		beforeTest(testName,testDesc);
		numNodes = i;
		newNode = NULL;
		result = addAtRearNullNode(numNodes,newNode);
		afterTest(testName, result);
		free(test);
	}

	// --- removeNode (NULL Node) --- //
	for(i = 0; i < NUM_TESTS_NULL_NODE; i++) {
		sprintf(testName,"<NULL Node - removeNode> - Base List Size: %i",i);
		switch (i) {
		case 0:
			sprintf(testDesc,"{ } --> removeNode(NULL) --> { }");
			break;
		case 1:
			sprintf(testDesc,"{1} --> removeNode(NULL) --> {1}");
			break;
		case 2:
			sprintf(testDesc,"{1,2} --> removeNode(NULL) --> {1,2}");
			break;
		case 3:
			sprintf(testDesc, "{1,2,3} --> removeNode(NULL) --> {1,2,3}");
			break;
		default:
			sprintf(testDesc,"{1, ... ,%i} --> removeNode(NULL) --> {1, ... ,%i}",i,i);
		}
		test = newTest(testNumber);
		setName(test,testName);
		setDesc(test,testDesc);
		beforeTest(testName,testDesc);
		numNodes = i;
		newNode = NULL;
		result = removeNodeNullNode(numNodes,newNode);
		afterTest(testName, result);
		free(test);
	}
}

// - NULL List - //
void runNullListTests() {
	testlist = NULL;
	listIsNull = 1;
	int returnedInt = 0;
	struct node* node;
	struct node* returnedNode;

	// --- getSize (NULL List) --- //
	sprintf(testName,"<NULL List - getSize>");
	sprintf(testDesc,"NULL --> getSize(NULL) --> NULL");
	test = newTest(testNumber);
	setName(test,testName);
	setDesc(test,testDesc);
	beforeTest(testName,testDesc);
	if(debugOn) {
		fprintf(stdout,"---> Starting with NULL List\n");
		fprintf(stdout,"---> Calling getSize(NULL)\n");
	}
	returnedInt = getSize(testlist);
	if(debugOn) fprintf(stdout,"---> Verifying Size == 0\n");
	result = (returnedInt == 0) ? 1 : 0;
	afterTest(testName, result);
	free(test);

	// --- isEmpty (NULL List) --- //
	sprintf(testName,"<NULL List - isEmpty>");
	sprintf(testDesc,"NULL --> isEmpty(NULL) --> NULL");
	test = newTest(testNumber);
	setName(test,testName);
	setDesc(test,testDesc);
	beforeTest(testName,testDesc);
	if(debugOn) {
		fprintf(stdout,"---> Starting with NULL List\n");
		fprintf(stdout,"---> Calling isEmpty(NULL)\n");
	}
	returnedInt = isEmpty(testlist);
	if(debugOn) fprintf(stdout,"---> Verifying isEmpty Returned True\n");
	result = (returnedInt == 1) ? 1 : 0;
	afterTest(testName, result);
	free(test);

	// --- addAtFront (NULL List) --- //
	sprintf(testName,"<NULL List - addAtFront>");
	sprintf(testDesc,"NULL --> addAtFront(NULL,Node) --> NULL");
	test = newTest(testNumber);
	setName(test,testName);
	setDesc(test,testDesc);
	beforeTest(testName,testDesc);
	if(debugOn) {
		fprintf(stdout,"---> Starting with NULL List\n");
		fprintf(stdout,"---> Calling addAtFront(NULL,Node)\n");
	}
	node = createNode(createObject(1,"Key1"));
	addAtFront(testlist,node);
	if(debugOn) fprintf(stdout,"---> Verifying List is still NULL\n");
	result = (!testlist) ? 1 : 0;
	afterTest(testName, result);
	freeNode(node,freeObject);
	free(test);

	// --- addAtRear (NULL List) --- //
	sprintf(testName,"<NULL List - addAtRear>");
	sprintf(testDesc,"NULL --> addAtRear(NULL,Node) --> NULL");
	test = newTest(testNumber);
	setName(test,testName);
	setDesc(test,testDesc);
	beforeTest(testName,testDesc);
	if(debugOn) {
		fprintf(stdout,"---> Starting with NULL List\n");
		fprintf(stdout,"---> Calling addAtRear(NULL,Node)\n");
	}
	node = createNode(createObject(1,"Key1"));
	addAtRear(testlist,node);
	if(debugOn) fprintf(stdout,"---> Verifying List is still NULL\n");
	result = (!testlist) ? 1 : 0;
	afterTest(testName, result);
	freeNode(node,freeObject);
	free(test);

	// --- removeFront (NULL List) --- //
	sprintf(testName,"<NULL List - removeFront>");
	sprintf(testDesc,"NULL --> removeFront(NULL) --> NULL");
	test = newTest(testNumber);
	setName(test,testName);
	setDesc(test,testDesc);
	beforeTest(testName,testDesc);
	if(debugOn) {
		fprintf(stdout,"---> Starting with NULL List\n");
		fprintf(stdout,"---> Calling removeFront(NULL)\n");
	}
	node = removeFront(testlist);
	if(debugOn) fprintf(stdout,"---> Verifying removeFront Returned NULL\n");
	result = (node == NULL) ? 1 : 0;
	afterTest(testName,result);
	free(test);

	// --- removeRear (NULL List) --- //
	sprintf(testName,"<NULL List - removeRear>");
	sprintf(testDesc,"NULL --> removeRear(NULL) --> NULL");
	test = newTest(testNumber);
	setName(test,testName);
	setDesc(test,testDesc);
	beforeTest(testName,testDesc);
	if(debugOn) {
		fprintf(stdout,"---> Starting with NULL List\n");
		fprintf(stdout,"---> Calling removeRear(NULL)\n");
	}
	node = removeRear(testlist);
	if(debugOn) fprintf(stdout,"---> Verifying removeRear Returned NULL\n");
	result = (node == NULL) ? 1 : 0;
	afterTest(testName,result);
	free(test);

	// --- removeNode (NULL List) --- //
	sprintf(testName,"<NULL List - removeNode>");
	sprintf(testDesc,"NULL --> removeNode(NULL,Node) --> NULL");
	test = newTest(testNumber);
	setName(test,testName);
	setDesc(test,testDesc);
	beforeTest(testName,testDesc);
	if(debugOn) {
		fprintf(stdout,"---> Starting with NULL List\n");
		fprintf(stdout,"---> Calling removeNode(NULL,Node)\n");
	}
	node = removeNode(testlist,node);
	if(debugOn) fprintf(stdout,"---> Verifying removeNode Returned NULL\n");
	result = (node == NULL) ? 1 : 0;
	afterTest(testName, result);
	free(test);

	// --- search (NULL List) --- //
	sprintf(testName,"<NULL List - search>");
	sprintf(testDesc,"NULL --> search(NULL,Node) --> NULL");
	test = newTest(testNumber);
	setName(test,testName);
	setDesc(test,testDesc);
	beforeTest(testName,testDesc);
	if(debugOn) {
		fprintf(stdout,"---> Starting with NULL List\n");
		fprintf(stdout,"---> Calling search(NULL,Node)\n");
	}
	node = createNode(createObject(1,"Key1"));
	returnedNode = search(testlist,node);
	if(debugOn) fprintf(stdout,"---> Verifying search Returned NULL\n");
	result = (returnedNode == NULL) ? 1 : 0;
	afterTest(testName, result);
	freeNode(node,freeObject);
	free(test);

	// --- reverseList (NULL List) --- //
	sprintf(testName,"<NULL List - reverseList>");
	sprintf(testDesc,"NULL --> reverseList(NULL) --> NULL");
	test = newTest(testNumber);
	setName(test,testName);
	setDesc(test,testDesc);
	beforeTest(testName,testDesc);
	if(debugOn) {
		fprintf(stdout,"---> Starting with NULL List\n");
		fprintf(stdout,"---> Calling reverseList(NULL)\n");
	}
	reverseList(testlist);
	if(debugOn) fprintf(stdout,"---> Verifying List is still NULL\n");
	result = (testlist == NULL) ? 1 : 0;
	afterTest(testName, result);
	free(test);

	// --- freeList (NULL List) --- //
	sprintf(testName,"<NULL List - freeList>");
	sprintf(testDesc,"NULL --> freeList(NULL) --> NULL");
	test = newTest(testNumber);
	setName(test,testName);
	setDesc(test,testDesc);
	beforeTest(testName,testDesc);
	if(debugOn) {
		fprintf(stdout,"---> Starting with NULL List\n");
		fprintf(stdout,"---> Calling freeList(NULL)\n");
	}
	freeList(testlist);
	if(debugOn) fprintf(stdout,"---> Verifying List is still NULL without SEG FAULT\n");
	result = (testlist == NULL) ? 1 : 0;
	afterTest(testName, result);
	free(test);

	listIsNull = 0;
}

