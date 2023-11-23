## CS 321 Data Structures

### Project 1 - Cache

**Introduction:**  
This programming assignment asks you to design acacheimplementation using linked list data
structure. That is, write a Cache class having at least the following public methods – constructor,
getObject, addObject, removeObject, clearCache and some others. The data to be stored in cache
should be generic objects. Also, write a test program to test your cache implementation.

**Description:**  
A cache is a storage in memory. If a data item has a copy in cache, application can read this data
item from cache directly. The usage of cache is as follows. Whenever an application requires a data
item, it searches the cache first. If it is a cache hit, then the cache returns the data item to the
application and the data item will be move to the first position in the cache (we call it theMost
Recently Used MRU scheme). On the other hand, if it is a cache miss, then the application needs
to read the data item from disk and then the data item from disk will be added to the first position
of the cache. Note that if the cache is full, the last entry (oldest one) in the cache will be removed
before a new entry can be added.

Similarly, whenever an application writes a data item to disk, the system will perform the same
write operation to the cache copy of the data item (if any) and then move it to the first position
in cache. Note that thewriteoperation is equivalent to aremoveoperation followed by anadd
operation.

One-level Cache:
A single-level cache and it works as described above.

Two-level Cache:
A 2nd-level cache sits behind the 1st-level cache. Usually, the 2nd-level cache is much bigger than
the 1st-level cache. Assume the 2nd-level cache contains all data in the 1st level cache, which is
called (multilevel inclusion property).  

Two-level cache works as follows:

1) If 1st-level cache hit: Both cache have the hit data item. Move the hit data item to the top on both cache.

2) If 1st-level cache miss and 2nd-level cache hit: The data item is not in 1st-level cache but is in 2nd-level cache. Move the data item to the top of 2nd-level cache and add the item to the top of 1st-level cache.

3) If 1st-level cache miss and 2nd-level cache miss: Retrieve the data item from disk and add the item to the top of both cache.


**Hit Ratio:**  
Some terms used to define hit ratio are:  
H R: (global) cache hit ratio  
H R<sub>1</sub> : 1st-level cache hit ratio  
H R<sub>2</sub> : 2nd-level cache hit ratio  
N H: total number of cache hits  
N H<sub>1</sub> : number of 1st-level cache hits  
N H<sub>2</sub> : number of 2nd-level cache hits  
N R: total references to cache  
N R<sub>1</sub> : number of references to 1st-level cache  
N R<sub>2</sub> : number of references to 2nd-level cache (= number of 1st-level cache misses)

One-level cache:  
$HR = \frac{NH}{NR}$  

Two-level cache:  
$HR_1 = \frac{N H_1}{NR_1}$  

$HR_2 = \frac{N H_2}{NR_2}$  

$HR = \frac{NH_1 + NH_2}{NR_1}$
