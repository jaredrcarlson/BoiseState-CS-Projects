```
PROJECT: Circuit Tracer
AUTHOR: Jared Carlson


OVERVIEW:

 This Java application reads in circuit board layouts from a text file and finds
 the shortest paths required to connect a specific starting component with a
 specific ending component. The shortest path(s) are then displayed either on
 the console or through a graphical user interface. The best path(s) search is
 performed using either a stack or a queue storage method. Command line arguments
 must be used to specify the storage method, display mode, and input filename.
 
 The first line of the input file gives two integer values for the number of rows
 and columns on the circuit board. Each remaining line represents one row of the board.
 Various characters represent components, occupied spaces, and open spaces as follows:

  'O' - open space (that's capital letter O, not zero)
  'X' - an occupied position (a component or existing trace the new trace cannot cross)
  '1' - first of two components that need to be connected
  '2' - second of two components that need to be connected
  'T' - a position in the new trace connecting the two components

 An example of a properly formatted circuit board input file is as follows:
 
  5 6
  X O 1 O O O
  X X X O O O
  O O O O X O
  O 2 O O X O
  O X O O O O 


INCLUDED FILES:

 README (This File) - Program Description and Usage
 CircuitBoard.java (Source Code) - used to create a circuit board from input file
 CircuitTracer.java (Source Code) - performs path searches and displays solutions
 Storage.java (Source Code) - used to create stack or queue storage objects
 TraceState.java (Source Code) - used to store all path search attempts
 CircuitTracerGraphical.java (Source Code) - used to display results graphically
 InvalidFileFormatException.java (Source Code) - used to indicate file formatting error
 OccupiedPositionException.java (Source Code) - used to indicate an occupied position on circuit board
 
 
BUILDING AND RUNNING:

 All project files should be in the same directory, including any input files.

 From the directory containing the .java source code, compile the program:
    $ javac CircuitTracer.java

 Command Line Arguments (case-sensitive) must be used to run this application.
 The three required arguments are entered in the following form:
    $ java CircuitTracer [ -s | -q ] [ -c | -g ] [ fileName.dat ]
 
    Argument 1: [ -s | -q ]
       -s = uses a Stack Data Structure to store best path information
       -q = uses a Queue Data Structure to store best path information

    Argument 2: [ -c | -g ]
       -c = displays best path results in command-line mode
	   -g = displays best path results in graphical interface mode
	   
	Argument 3: [ fileName.dat ]
	   fileName.dat = the filename of the circuit board to be tested
 
 To search the input file named Circuit1.dat using stack storage and display results in console mode,
 run the following command from the directory containing CircuitTracer.class:
    $ java CircuitTracer -s -c Circuit1.dat
    
 To search the input file named Circuit1.dat using queue storage and display results in graphical mode,
 run the following command from the directory containing CircuitTracer.class:
    $ java CircuitTracer -q -g Circuit1.dat
 

PROGRAM DESIGN:

 Execution begins in CircuitTracer's main method where the first step is to parse the required command line
 arguments and display a helpful message if they fail. Once the command line arguments are validated, the
 Storage class is used to create the appropriate storage method passed in as a command line argument.
 CircuitTracer then makes use of the CircuitBoard class to instantiate a circuit board object from the
 user-defined input file. The board is then checked for adjacent starting and ending points so that the proper
 TraceState constructor can be called for this unique case.
 
 One TraceState constructor is used for the initial traces adjacent to the starting point, and then another
 TraceState constructor is used for all the rest of the traces in order to add each new trace to it's previous trace.
 All of these chains of TraceState objects are kept in storage until they hit a dead end on the board or they reach
 the ending point. This is where the choice between using a stack or a queue comes into play during the search process.
 After each successful trace, it's path size (distance from starting point to ending point) is compared with any previous
 successful traces. If the new trace is equal, it gets added to the list of solutions, but if it has a shorter path, it 
 replaces all previous solutions.
 
 Finally, each solution is either displayed on the console or viewable in a graphical interface depending on the
 command line argument supplied. The console mode uses a StringBuilder object as well as the CircuitBoard's toString
 method to display all of the best path solutions to the screen. The graphical mode makes use of the CircuitTracerGraphical
 class which includes a file menu (JMenu), visual circuit board (JLabel), and list of solutions (JButtons).
 The CircuitTracerGraphical object initially displays the original circuit board without any traces.
 Through the use of an ActionListener, each solution is displayed by simply clicking on the labeled JButtons that are
 generated for each solution.
 
  
PROGRAM DEVELOPMENT AND TESTING DISCUSSION:

 The first step I took was to examine each of the provided classes very closely in order to understand how
 they work. I spent time studying each class's fields and methods and created a basic UML diagram so that
 I would be able to use each class according to the original designer's intentions.
 
 I began coding in the Storage class where I was able to find a simple switch statement used in the constructor
 to execute the correct methods for either the stack or queue data structure. I used this same type of
 switch statement and found the proper methods for both data structures on the Oracle site. Completing the
 Storage class was fairly simple as we talked about it in class and Oracle's site was easy to use.  
 The CircuitBoard class was also pretty easy to complete as the structure was already in place. I was able to
 create a method to parse the input file while checking for any errors and then store the data for later use.
 
 The biggest challenge was writing the code in the CircuitTracer class because that is where everything comes
 together. I wrote down all of the program requirements and broke them into easier to handle tasks which 
 helped me to decide where to use private methods and how the tasks should flow together. I was able to write
 the code needed to validate command line arguments and display a help page if the arguments failed.
 I had a hard time grasping how the TraceState objects would be used and how path searches would be found, but
 I realized first-hand how valuable this concept of encapsulation can be. I didn't need to completely understand
 the implementation side of the provided classes in order to use them!
 
 The provided pseudo-code helped me to write the search algorithm which would have been very challenging if I
 would have had to figure it out on my own. It took a lot of work and troubleshooting for me to use the
 TraceState objects correctly, but after a while it all began to make sense. I just needed to call the appropriate
 TraceState constructor for my initial traces and all additional traces.
  
 The extra credit portion was fairly basic as it was just a matter of creating three panels, one for the file
 menu, another for the circuit board grid, and another to hold buttons to show each solution. It took some
 trial and error to get things lined up correctly, but did not take much time to finish this portion. I created
 a new class to handle the graphical output to keep the application's extra tasks separate from the rest.
 This was the first time I included a file menu for a graphical user interface so I was able to learn a few new
 tricks to play around with which is always fun!
 
 I didn't run into any major problems or oversights as I tested the application's functionality because I spent
 a lot of time making sure I understood the provided classes and the project requirements before starting to code.
 I feel like my design was well-planned and executed because I was able to test several input files with success
 and my application passes the testProg script that uses the linux diff command to compare my output with
 expected output.
 
 This was an excellent project to help me get a good feel for abstract data types and their usage.
 I look forward to learning more new concepts and expanding my abilities as a software engineer!
 ```