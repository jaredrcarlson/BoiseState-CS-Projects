```
PROJECT: Magic Square
AUTHOR: Jared Carlson


OVERVIEW:

 This Java application accepts command line arguments and has two operation modes.
 The first operation checks a user-specified input file for a matrix of integer values
 to see if they form a magic square and then displays the results.
 The second operation takes a user-specified output file and matrix size (Odd Integer)
 and then creates and writes a magic square to the output file.
 
 The text file format for both the check and create options for an example 5x5 matrix is as follows:
 
 5
 11 18 25 2 9
 10 12 19 21 3
 4 6 13 20 22
 23 5 7 14 16
 17 24 1 8 15
 
 (Note: The matrix size dimension should be a single integer on the first line)

 More information about "Magic Squares" can be found at: http://en.wikipedia.org/wiki/Magic_square


INCLUDED FILES:

 README (This File) - Program Description and Usage
 MagicSquare.java (Source Code) - Class used to check and create magic square text files
 MagicSquare.java (Source Code / Driver) - Class used to test MagicSquare.java class functionality
 validSquare.txt (Example File) - Example of a properly formatted magic square matrix file
 invalidSquare.txt (Example File) - Example of a matrix file that is not a magic square

BUILDING AND RUNNING:

 All project files should be in the same directory, including any files to be checked for magic squares.

 From the directory containing the .java source code, compile the program:
    $ javac MagicSquareTester.java

 To check an input file, run the program using the -check option from the directory containing MagicSquareTester.class:
 (Important: Input files must be located in the directory containing MagicSquareTester.class in order to be checked.)
    $ java MagicSquareTester -check validSquare.txt
    
 To create a magic square output file, run the program using the -create option from the directory containing MagicSquareTester.class:
    $ java MagicSquareTester -create newMagicSquare.txt 5
    
 To view program usage and examples, run the program using the -help or -h options from the directory containing MagicSquareTester.class:
    $ java MagicSquareTester -help
    or
    $ java MagicSquareTester -h
 

PROGRAM DESIGN:

 The MagicSquareTester class makes use of the MagicSquare class to check input files for magic squares
 and to create new output files containing magic squares of a user-defined size.
 The MagicSquareTester class must be invoked using command line arguments that tell the application
 to either check an input file for a magic square or create a new magic square output file with a user-defined size.
 
 The application begins by validating command line arguments and displays a helpful program usage message if
 the command line arguments entered are found to be invalid.
 Once the arguments are validated, the application instantiates a MagicSquare object and either displays
 the results for the -check mode or creates the user-defined output file for the -create mode.
 
 The MagicSquare class has two constructors (Overloading), one for the -check mode and one for the -create mode.
 Both constructors take a filename (input / output) parameter and the -create mode constructor also takes a size parameter.
 
 The -check mode constructor uses private methods to validate and read the input file, matrix size, and all matrix values making sure they are integers.
 The readMatrix() private method makes use of the Scanner class to read from the user-defined input file.
 The integers are then tested to make sure they are unique, greater than 0, and less than or equal to size^2. (Max value for Magic Square)
 The validateMagicSum() private method is used to verify that all row, column, and diagonal sums are equal to the magic square sum.
 Once these critical checks have completed, the constructor is able to set the isMagicSquare field to true or false.
 The MagicSquare class has a toString() method that makes use of the StringBuilder class to create the text displayed for the -check mode results.
  
 The -create mode constructor uses private methods to first create a magic square from the user-defined matrix size parameter.
 The private method used to create the magic square was converted from given pseudo-code to an algorithm in Java syntax.
 Then the constructor calls the writeMatrix() private method which makes use of the StringBuilder and FileOutputStream classes
 to format the magic square text and write it to the user-defined output file.
 
 The application was designed to be very user-friendly as appropriate error messages are displayed when a problem is detected.
 If the program detects a problem, an error message is displayed detailing exactly what the problem was.
 If there is a problem with the way command line arguments were entered, the error message will give suggestions as well as
 display a program usage and examples page to help resolve any problems.
 
 
PROGRAM DEVELOPMENT AND TESTING DISCUSSION:

 The first step I took was to read through the program requirements a few times to make sure I clearly understood
 what my application was supposed to provide. This helped me to break down the many tasks that needed to be
 performed into small groups that I could turn into methods.
 
 Once I fully understood the project requirements, I constructed the skeleton code for my MagicSquare class.
 The skeleton code initially included a single constructor, the 4 required methods, and a few helper methods.
 My initial fields (instance variables) included inputFilename, outputFilename, matrix, matrixSize, and magicSum.
 
 While trying to implement my constructor, I realized that I hadn't thought about how my MagicSquare class would
 be able to handle both the -check and -create command line arguments for the MagicSquareTester driver class.
 Since both modes required a filename argument and the -create mode required an additional size argument, I decided
 to create two constructors (Overloading). The first constructor would handle a single String parameter while
 the second constructor would handle a String parameter and an Integer parameter. This made it simple to implement
 both modes and I was able to change my inputFilename and outputFilename fields to a single shared filename field.
 
 With my constructors in place, I began working through the rest of my private methods. I had to look back through
 some of the programs I wrote last semester to refresh my memory on concepts such as reading and writing files
 as well as how to use the StringBuilder class and the String.format() method.
 I was very happy that pseudo-code was provided for how to create a magic square because I don't know that I would
 have ever figured that out on my own!
 
 I found that by starting from a skeleton model and implementing private methods to handle each task made the
 development process much easier. It is too easy to get "tunnel-vision" while writing code so I found it very
 helpful to break the application tasks into several methods that I could focus on later once the overall design
 was in place. I ran into some scope issues at times, but I was able to work through them and learn how to implement
 helper methods by passing them the proper arguments or creating new fields (instance variables) as it seemed
 appropriate. It is much easier to access instance variables from many private methods, but that it is not a good
 enough reason to create an instance variable. To avoid creating inappropriate instance variables, I would ask myself,
 "Is this variable an essential piece of the object's information that should be stored as an instance variable,
 or should it remain local?"
 
 I feel like my design was well-planned and executed because I was able to test several input files with success
 as well as create several magic square files of various sizes that all passed my -check mode test.
 I created several test input files that would fail at the various checks within my program to make sure that
 all of my checks were working properly.
 
 I ran into a few problems while testing for valid command line arguments, but I was able to resolve them quickly
 as I had a firm understanding of the logic I used to test them. I was able to easily recognize the flaws in my
 initial logic designs and resolve them accordingly. One issue in particular was that my MagicSquareTester driver
 class was not detecting extra arguments, so I had to adjust my logic to make sure that only the exact number of
 arguments for each option would be valid. This was as simple as including an extra IF statement to validate the
 proper number of arguments for both the -check and -create options.
 
 This was an excellent project to start out with as it helped me to refresh my basic knowledge of Java that I
 gained last semester. I look forward to learning new concepts and expanding my abilities as a software engineer!
 ```