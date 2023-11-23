## CS 321 Data Structures

### Project 5 - Bioinformatics

### Authors
Jared Carlson, Matt Crosby, Kyle Cummings

**Overview:**  
This program creates a BTree from parsed data from a gene bank file. Then
searches the BTree against a query file and spits out the frequency of substrings
the query file supplies.

**Usage Instructions:**  
Create the BTree binary file:
```bash
java GeneBankCreateBTree <cache> <degree> <gbk file> <sequence length> [<cache size>] [<debug level>]
```
Search against a query file:
```bash
java GeneBankSearch <cache> <btree file> <query file> [<cache size>] [<debug level>]
```

where \<cache\> = 0 (disabled) | 1 (enabled)


## Discussion

- BTree Disk layout:  
	Metadata:
	- int rootAddress
  - int nextOpenAddress
  - int sequenceLength
  - int degree
  - int numNodes
  - int nodeMetadataSize
  - int maxNumKeys
  - int keySize  

  The metadata is 24 bytes long and then all of our Nodes follow.	When a Node is written to disk, the maximum space required for a full node is used.	This way, we would be able to keep all nodes at their same address location in the binary file. Within each Node, the current number of Keys are written in order. We created a Doubly-Linked List for Keys so the links determine the Key layout on disk. This way we are able to build the links as the keys are read from the disk for a search operation. Nodes and Keys have their own metadata preceding their actual data on the disk.
		

- Cache speed improvements:
	
	| | degree | seqLength | gbkTestNumber | Time to build tree | Time to query | # Writes | # Reads |
  | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
	| No cache | 8	|	7 | 3	|	4.42 | 2.68	| 11,129 | 27,685	|
	| Cache 100 |	8	|	7	|	3	|	3.16 | 2.61 |	11,129 | 10,591	|
	| Cache 500 |	8	|	7	|	3	| 2.37 | 2.57 |	11,129 | 2,616 |

	When we ran the GeneBankCreateBTree program using a cache size of 100, it decreased the speed	of creating the BTree by an average of 1 seconds, with a 41% speed imporvement.
  
  When we ran the GeneBankCreate program using a cache size of 500, it decreased the speed of creating the BTree by an aaverage of 2 seconds, with a 59% speed improvement.

	When we ran the GeneBankSearchBTree program using a cache size of 100, it decreased the speed of the querying by an average of 0.05 seconds. 
	
  When we ran the GeneBankSearch program using a cache size of 500, it decreased the speed
	of the querying by an average of 0.10 seconds.

- Extra Debug levels:  
	In GeneBankCreateTree, we added support for a debug level 2 which includes complete step-by-step BTree operations printed to standard out. This is great to have while debugging.
