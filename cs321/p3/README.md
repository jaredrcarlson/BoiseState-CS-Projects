## CS 321 Data Structures

### Project 3 - Hash Table

**Introduction:**  

Suppose we are inserting n keys into a hash table of size m. Then the load factor α
is defined to be n/m. For open addressing n≤m, which implies that 0≤α≤1. In this assignment we will study how the load factor affects the average number of probes required by open addressing while using linear probing and double hashing.

**Design:**  

Set up the hash table to be an array of HashObject. A HashObject contains a generic object and a frequency count. The HashObject needs to override both the equal and the toString methods and should also have a getKey method.  

Also we will use linear probing as well as double hashing. So design theHashTable
class by passing in an indicator via constructor so that the appropriate kind of probing
will be performed.  

Choose a value of the table sizemto be a prime in the range [95500...96000]. A good
value is to use a prime that is 2 away from another prime. That is, both m and m−2
are primes. Vary the load factorαas 0.5, 0.6, 0.7, 0.8, 0.9, 0.95, 0.98, 0.99 by setting
the value ofnappropriately, that is,n=αm. Keep track of the average number of
probes required for each value ofαfor linear probing and for double hashing.
For the double hashing, the primary hash function ish 1 (k) =kmodmand the sec-
ondary hash function ish 2 (k) = 1 + (kmod (m−2)).  

There are three sources of data for this experiment as described in the next section.  
*Note that the data can contain duplicates. If a duplicate is detected, then update the
frequency for the object rather than inserting it again. Keep inserting elements until
you have reached the desired load factor. Count the number of probes only for new
insertions and not when you found a duplicate.*

**Experiment:**  

For the experiment we will consider three different sources of data as follows:

- Generate random keys usingjava.util.Randomclass.
- Generate keys by making calls to the method System.currentTimeMillis().
- Insert words from the file word-list provided.
    The file contains 3,037,798 words (one per line) out of which 101,233 are unique.
    Note that after you read in a word, you will have to convert it to a number as its
    key value by calling the hashCode method (a method in Object class).
    Note that two different words may have the same key values, though the prob-
    ability is small. Thus, we must compare the actual words to check if the word
    already exists in the table.

**Required file/class names and output:**  

The source code for the project. The driver program should be named as HashTest,
it should have three (the third one is optional) command-line arguments as follows:
```bash
java HashTest <input type> <load factor> [<debug level>]
```
The \<input type\> should be 1, 2, or 3 depending on whether the data is generated
usingjava.util.Random, System.currentTimeMillis() or from the file word-list.
The program should print out the input source type, total number of keys inserted into
the hash table and the average number of probes required forlinear probing and double
hashing. The optional argument specifies a debug level with the following meaning:

debug = 0 −→ print summary of experiment  
debug = 1 −→ print out the hash table at the end  
debug = 2 −→ print the number of probes for each new insert
