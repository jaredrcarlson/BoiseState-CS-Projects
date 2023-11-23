## CS 321 Data Structures

### Project 2 - CPU Scheduling Simulation

**Introduction:**  
In this assignment, you need to implement a priority queue PQueue class using a max-heap and to
implement a MaxHeap class using an array. Each node in the max-heap contains a process. The
Process class implements Comparable interface so that the comparison between nodes in max-heap
can be made by calling compareTo method. To implement the compareTo method, process with
larger priority level has a higher priority. In case of tie, process with earlier arrival time should be picked.  
This assignment simulates the CPU round robin scheduling. The CPU scheduling works as follows.

1. A system defines a unit time (time slice).

2. A priority queue holds the processes waiting for CPU time.

3. Each process has a priority level, time remaining to finish, and arrival time.

4. The system assigns the next CPU time (time slice) to the highest priority process (if there is a tie, the one arriving first is picked) in the priority queue.

5. Each time a process get a time slice from CPU, its remaining time to finish should be decremented by one.

6. In order to avoid starvation problem, the system will increment the priority for lower priority
processes. In this simulation, if any process does not get any time slice for a certain amount
of time (timeToIncrementPriority), its priority will be incremented by one until it reaches the
highest allowable priority level.

**Description:**  
The following two java source files are provided and should not be modified:

1. CPUScheduling.java: simulates the round robin CPU scheduling algorithm. This class is finished.

2. Averager.java: keeps track of the number of processes in the simulation and computes the average turn around time. This class is finished.

In addition to these two java classes, you should implement other classes for the CPU scheduling simulation. Suggested other classes include, but are not limited to:

1. ProcessGenerator.java randomly generates processes with a given probability. At beginning of each time unit, whether a process will arrive is decided by the given probability. In addition, while generating a new process, both its priority and the required time units to finish the process are randomly generated within given ranges.

2. Process.java defines a process. You need to implement thecompareTomethod in this class. Each process has a priority level, time remaining to finish, and arrival time.

3. MaxHeap.java defines a max-heap. Each node in the max-heap contains a process.

4. PQueue.java defines a priority queue using a max-heap.

**Run Instructions:**  
To run the simulation, the following command-line arguments need to be provided:  
  ```bash
  java CPUScheduling <maxProcessTime> <maxPriorityLevel> <timeToIncrementPriority> <simulationTime> <processArrivalRate>
  ```
where:
- maxProcessTime: largest possible time units required to finish a process;
- maxPriorityLevel: highest possible priority in this simulation. That is, a process can
have a priority, ranging from 1, 2, ..., maxPriorityLevel.
- timeToIncrementPriority: if a process didn’t get any CPU time for this timeToIn-
crementPriority time units, the process’s priority will be increased by one.
- simulationTime: the total time units for the simulation.
- processArrivalRate: using this rate to decide whether to generate a new process in
each time unit.

**Simulation Flow Chart:**  

![Simulation Flow Chart](simulation_flow_chart.png?raw=true)
