### Porting Java program to CSharp

**Basic Approach**  
My first step was to understand the code and make sure I knew what the program was meant to produce. (Purpose of the Program) Then I began by looking at each line of code from the Java files. I disected each line and thought hard about each element. I then researched how to implement each element or code objective (import, extends, implements, etc) using C#.

I decided to install Visual Studio Community to work through the coding for this assignment. Here's a list of items that I researched in order to port the program to C#. I have listed the information that was most helpful for each topic.

**Import Statements**  
Java organizes related classes into packages.
Available through the "import" statement

C# organizes related classes into namespaces.
Available through the "using" statement


**Class Declarations - Extends & Implements**  
C# replaces both of these keywords with ":"
The Base class (Extends) must be listed first.
Interface names begin with a Capitol I by convention to make
them "stand out".


**Field Declarations**  
Same Syntax & Data Types are the same too!


**Method Declarations**  
C# methods begin with a Capitol letter by convention
Most method names I came across while looking at example code
appear to use the underscore character to seperate words instead
of using Camel-Case which is my preference in Java


**GUI Components**  
Java has JLabel, JButton
C# equiv - Label, Button (System.Windows.Forms)


**Event handling**  
Java - action listeners --> GUI Components  
C# - GUI Controls can be "assigned" event handlers  

I used the following lines of code to handle events in C#:
```csharp
// Executes the Button_Click method when the button is clicked
button.Click += Button_Click;
// Executes the LightSwitch_SizeChanged method when the user resizes the window (Keeps my items centered)
SizeChanged += LightSwitch_SizeChanged; 
```

I found an interesting difference in the way events are handled in C# compared to Java. The events are declared and raised in a class and associated with the event handlers using delegates within the same class or some other class. The class containing the event is used to publish the event. This is called the publisher class. Some other class that accepts this event is called the subscriber class. Events use the publisher-subscriber model. A publisher is an object that contains the definition of the event and the delegate. The event-delegate association is also defined in this object. A publisher class object invokes the event and it is notified to other objects. A subscriber is an object that accepts the event and provides an event handler. The delegate in the publisher class invokes the method (event handler) of the subscriber class.


**Overall Experience**  
While I still have much to learn about C#, I found this porting experience very educational. It can be difficult to begin learning about a new language so I find it helpful to start with an objective. With the objective of creating a binary counter, I was able to research specific aspects of the language
and immediately begin trying to implement them piece by piece. Of course, I did walk through a basic
tutorial of the language first in order to get a feel for C#. I ran into some trouble when I first began trying to get the button click event to work properly. After quite some time, I "backed up" and watched some very basic tutorials on the topic. This allowed me to jump back into the project and develop a simple implementation. I've found that stepping away for a minute, thinking about the problem, and then keeping things simple really helps me to get past a road block without going down a "rabbit hole" and losing sight of the big picture.


**Visual Studio Design Feature**  
I fooled around with the "Designer" feature in Visual Studio, but I ultimately just stuck with the code
editor to accomplish the assignment. I found that the visual design feature removed me from the coding side of the project and inhibited me from actually learning the language. I could see this feature being handy and saving time in the future, but a true programmer should understand the language and how to write code before using a graphical tool that implements the code "behind the scenes".

I plan on working more in C# as I have an interest in developing apps for mobile devices so this was a great assignment for me to get my feet wet in the language that is the root of so many mobile platforms.

