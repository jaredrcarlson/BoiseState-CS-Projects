```
PROJECT: Merge Sort
AUTHOR: Jared Carlson


OVERVIEW:

 This Java application tests the functionality of the MergeSort class. This testing
 application uses already sorted lists, lists sorted in reversed order, and random
 lists to test all of the methods implemented in the MergeSort class. This testing
 application also tests the MergeSort methods requiring a comparator by using a
 class that implements the Comparator interface.   


INCLUDED FILES:

 README (This File) - Program Description and Usage
 DoubleLinkedListADT.java (Source Code) - interface implemented by DoubleLinkedList class
 ElementNotFoundException.java (Source Code) - use to throw appropriate exception
 EmptyCollectionException.java (Source Code) - use to throw appropriate exception
 IndexedListADT.java (Source Code) - interface extended by DoubleLinkedListADT class
 ListADT.java (Source Code) - interface extended by UnorderedListADT and IndexedListADT
 MergeSort.java (Source Code) - utility class used for sorting items in a list
 MergeSortTester.java (Source Code) - used to test the MergeSort class implementation  
 Sniper.java (Source Code) - class used for testing with a comparator
 SniperCompare.java (Source Code) - comparator implementation for Sniper objects
 UnorderedListADT.java (Source Code) - interface extended by DoubleLinkedListADT class
 WrappedDLL.java (Source Code) - Double-Linked List wrapper class
  
 
BUILDING AND RUNNING:

 All project files should be in the same directory.

 From the directory containing the .java source code, compile the program:
    $ javac MergeSortTester.java

 To run the testing program, run this command from the compile directory: 
    $ java MergeSortTester
       
 
PROGRAM DESIGN:

 The design process began with the MergeSort class as I felt the methods were fairly straight-forward
 and implementation would not be too difficult. The methods were designed to traverse the list a maximum of
 one time in order to comply with the program requirements. I was very careful to make sure that I did
 not use private methods that would need to traverse the list in order to return values to a primary method
 that would again need to traverse the list.
 
 The next step was to write the MergeSortTester class in order to verify that my MergeSort utility class
 was implemented correctly. The testing class was designed to call each MergeSort method and store the result
 so that the result could be compared to the expected result in order for the method to pass testing.
 The expected result for the sort method was obtained by checking to make sure each element in the list was
 less than or equal to the next element in the list. The findSmallest method's expected result was obtained
 by establishing the first element as the smallest and then comparing it with every element in the list while
 updating the smallestElement if a smaller element was found. The findLargest method's expected result was
 obtained by following this same procedure, but checking for larger elements during list traversal.
 
 Each method was called and it's result was stored and compared with the expected result. If the method's
 result was the same as the expected result, the method's implementation passed the test and the pass/fail
 counters were updated appropriately.
 
 In order to test the methods that required a comparator argument, I created a Sniper class that contained
 fields for the Sniper's name and number of kills. I then created a SniperCompare class that implements the
 Comparator class that simply compared the number of kills for two Sniper objects. If a Sniper object had
 more kills, it was considered to be a "Larger" Sniper object. Sniper objects are ranked by their number
 of kills, so the comparator sort method sorted them from least kills to most kills using the SniperCompare
 class as the Comparator for sort, findSmallest, and findLargest method calls.    
 
 To create the lists used for testing, I used the provided wrapper class for Double-Linked List implementation.
 This wrapper class made it easy to create lists for testing purposes.     
  
  
PROGRAM DEVELOPMENT AND TESTING DISCUSSION:

 The development and testing stages for this project were very smooth without any major issues. Implementing
 the SniperCompare class was a bit tricky at first, but I managed to figure out how to create a Comparator
 class by using the built-in functionality and error-checking of Eclipse. I ran into a few problems when I
 tried to create my Comparator by defining the type as Sniper and not generic type <T>, but I was able to
 make my Comparator class work with the provided code so that it could properly handle Sniper objects while
 conforming to the specifications of the Comparator interface.
 
 The testing phase revealed that I had made a logical mistake during some of the comparisons since my method
 implementations would yield unexpected results. This was because I was changing my "smallest" and "largest"
 elements if the next element was equal to the current "smallest" or "largest" elements. I was able to remedy
 this problem by adjusting my conditional statements. I also found that my findLargest method was not giving
 me the absolute best possible result because it was only returning a small portion of one's desired breakfast
 rations. I adjusted the method so that it would first create three small sausages wrapped in crescent rolls.
 I then had the method bake these crescent roll-wrapped sausages for ten minutes at approximately three-hundred
 and seventy-five degrees for ten minutes. After about ten to twelve minutes, the method would remove them
 from the oven and allow them to cool for a couple of minutes. I found that this method produced the most
 desirable set of pigs in a blanket that the human palate could ever experience. This is when I knew that my
 development and testing stages were complete. If you were actually able to take the time to read through these
 last statements, send me an email and we'll have a good laugh, but I'm guessing that these write-ups, while
 essential on the job, are as boring for you as they are for us! Have a great summer and thanks for dealing
 with all of my questions this semester. 
 
 
 FINAL THOUGHTS:
 
 This was a great project for me to understand how to implement the comparator interface. The natural order
 does not always apply to objects so it was very helpful to understand how I can implement a class to
 compare objects based on what I deem to be important ranking information.
 ```