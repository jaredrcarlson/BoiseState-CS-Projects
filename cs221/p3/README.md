```
PROJECT: Doubly Linked List
AUTHOR: Jared Carlson


OVERVIEW:

 This Java application demonstrates the testing of a Doubly-Linked List data structure.
 The data structure class has been designed to implement the DoubleLinkedListADT interface.
 The Doubly-Linked List data structure is used to store and access a collection of
 generic type data. The data is stored in linear nodes that have links in both
 directions. Any single node in the data structure contains a link to both it's previous
 node and next node in the collection. This data structure is tested using 22 different
 "state change" scenarios in order to ensure that the data structure's storage and
 retrieval operations meet all of the requirements of the DoubleLinkedListADT interface.  


INCLUDED FILES:

 README (This File) - Program Description and Usage
 DoubleLinkedListTester.java (Source Code) - used to test the DoubleLinkedList data structure  
 DoubleLinkedList.java (Source Code) - used as the data structure to store/access the collection.
                                       Also contains an inner DoubleLinkedListIterator class
 									                     used to traverse and modify the data collection.
 DoubleLinkedLinearNode.java (Source Code) - used to create nodes for data storage
 DoubleLinkedListADT.java (Source Code) - interface implemented by DoubleLinkedList class
 UnorderedListADT.java (Source Code) - interface extended by DoubleLinkedListADT class
 IndexedListADT.java (Source Code) - interface extended by DoubleLinkedListADT class
 ListADT.java (Source Code) - interface extended by UnorderedListADT and IndexedListADT 
 ElementNotFoundException.java (Source Code) - use to throw appropriate exception
 EmptyCollectionException.java (Source Code) - use to throw appropriate exception
  
 
BUILDING AND RUNNING:

 All project files should be in the same directory.

 From the directory containing the .java source code, compile the program:
    $ javac DoubleLinkedListTester.java

 Command Line Arguments (case-sensitive) must be used to run this application.
 The single required argument is entered in the following form:
    $ java DoubleLinkedListTester -d
 
    Argument 1: [ -d ]
       -d = tests the DoubleLinked Data Structure for 22 different change scenarios
       
 
PROGRAM DESIGN:

 This application was designed in such a way that future testing scenarios and subsequent modifications
 would be easy to implement. The DoubleLinkedListTester application is easy to follow and modify as
 needed. A series of tests are performed for several different change scenarios in order to discover
 any bugs in the underlying data structure implementation. For each test, a new list is created for
 the current scenario, a method is invoked on the list, and the results of the method are compared to
 the expected result in order to check if the method implementation is working correctly. Some methods
 are tested multiple times with different parameters supplied so that many conditions can be checked to
 help verify the reliability of the data structure.
 
 The DoubleLinkedList class implements the DoubleLinkedListADT interface as well as both the UnorderedListADT
 and the IndexedListADT interfaces through extension.
 The DoubleLinkedList class provides the underlying data structure by making use of the DoubleLinkedLinearNode
 class to create a list that has links in both directions.
 Sentinel nodes are used to serve as pointers to the head and tail of the list.
 The DoubleLinkedList class also makes use of DoubleLinkedListIterator, a private inner class, which extends
 the ListIterator class. Traversing through the list in either direction is simplified through the use of
 this inner class.
 
 The DoubleLinkedLinearNode class has fields to store a generic type element (data), a link to the
 previous node in the list, and a link to the next node in the list. The standard get and set methods
 are used for this class in order to create or update the links between nodes.
 
 The DoubleLinkedListIterator class was designed with efficiency and simplicity in mind. The iterator is
 only used to traverse through the list one single time for each method. So for example, the addAfter
 method uses an iterator to step through the list until it finds the target element. The addAfter method
 then creates a new node and updates all of the necessary links. This implementation is most efficient
 because it ensures that the number of list iterations will always be a minimum. A different approach for
 implementation could have been to first use the indexOf method to locate the target, and then to use the
 add method to insert the new element at the next index. While this approach doesn't require as much code
 within the addAfter method, it is not the most efficient implementation because the list would need to
 be traversed twice. The indexOf method would step through the list and then the add method would step
 through the list again, effectively doubling the execution time for this method.
  
 All of the DoubleLinkedListADT interface's methods were implemented during the design phase according to
 the exact specifications of their definitions. This was the most important aspect of the design of the
 DoubleLinkedList class because in order to implement the interface correctly, the underlying data
 structure had to meet every single expectation of the interface to ensure reliable encapsulation.     
  
  
PROGRAM DEVELOPMENT AND TESTING DISCUSSION:

 The first step I took was to examine each of the DoubleLinkedListADT interface's method definitions. I made
 sure that I completely understood the expectations of each method including any exceptions that needed to be
 thrown if a certain error state was detected. I then studied the ListIterator interface that needed to be
 implemented for this project to make sure that I understood all of the required iterator methods and how I would
 use each of them to manage the underlying data structure.
 
 I began my actual coding in the DoubleLinkedListTester class by studying how the provided methods worked so
 that I could create additional methods that would be able to properly test the new data structure.
 I had to really "dig deep" into this testing class to figure out how to add tests for the IndexedList methods
 in addition to the UnorderedList methods. After much trial and error, I found that I needed to add a conditional
 statement within the runTests() method that would allow me to only run IndexedList tests on data structures
 that supported the IndexedListADT interface. I also found that I needed to create a new method called newIndexedList()
 in addition to the provided newList() method so that I could create a new Indexed list when testing methods for
 data structures that implemented the IndexedListADT methods.
 
 The next step I took in development was to study the ListIterator class as it would be used to traverse the
 collection and would be the most integral part of the DoubleLinkedList class operations. I found that the
 ListIterator class has several useful methods that I was able to implement and use to manage my list.
 I then modified the provided LinearNode class so that it could handle links for both previous and next nodes.
 I decided to add sentinel nodes at the head and tail of the list as the textbook described so that list traversal
 would be more versatile. This decision made some of the initial code a little more challenging, but overall it made
 things easier for me as I knew that I would have pointers to both the front and back of the list if I ever
 needed to use them. 
  
 I didn't run into any major problems during the design phase, but I did have one issue with my decision to use
 sentinel nodes that took me a while to solve. My initial hasNext() method in my iterator class simply checked
 to see if the current node's next field was null to determine if there was indeed a next() element that could
 be returned by the next() method. This was an initial bug that I had to track down because the constructor for
 my DoubleLinkedList class created a sentinel node at the head of the list that pointed to a sentinel at the
 tail of my list before any actual data nodes were added to the collection. This meant that even for an initial
 empty list, my hasNext() method found that my head sentinel node pointed to a next node (tail sentinel) that
 was not null! Once I discovered this bug, I modified my hasNext() method to check to see if the next node's
 element field was null by having it call the next nodes getElement() method. This patched the bug that would
 have also been present in my hasPrevious() method as well, since only the two sentinel nodes would have
 element fields that were still set to null. This was really the only difficult problem to solve during design
 as all the rest of the issues I encountered were very easy to detect and resolve.
 
 
 EXTRA CREDIT:
 
 I was able to implement the ListIterator class into this project with all methods supported.
 While my IndexedList method tests are not extra credit, I was able to create a ListerTester for these
 last few homework assignments and this project that can handle every type of data structure we have implemented
 and run the appropriate tests. You'll see that my DoubleLinkedListTester class can handle all of the following
 arguments: -g -b -a -s -d and run the appropriate tests for each data structure.
 
 
 FINAL THOUGHTS:
 
 These recent assignment and project requirements were confusing at times, but I understand that your goal
 was to help us learn the key concepts without us having to complete hours of unnecessary tedious work.
 I can say that the time and effort that I put into making modifications to the tester class to get it to
 work correctly for all of the different arguments was very educational. I certainly learned more by 
 working through the ListTester conflicts than I would have if the ListTester class were already completed
 for me. I'm sure in the coming semesters you'll be able to find a good balance for how much work a student
 will need to do versus how much of the code is provided.
 
 Overall, this was a very educational project and I feel like I learned a lot of new programming concepts!
 ```