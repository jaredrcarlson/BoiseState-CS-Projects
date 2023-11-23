## CS 321 Data Structures

### Project 4 - Huffman Tree 

**Overview:**  
This program performs encode/decode operations on text files. Program features are implemented through the use of the following data structures:  
- Priority Queue
- Huffman Tree
- Min-Heap

**Usage Instructions:**  
```bash
	java HuffmanTest <Mode> <Base Filename> <Input Filename> <Output Filename>
```
where  

\<Mode\>  
-encode : Encodes \<Input Filename\> and saves encoded file as \<Output Filename\>  
-decode : Decodes \<Input Filename\> and saves decoded file as \<Output Filename\>  

\<Base Filename\>  
Specifies the file to use for symbol frequency counts to build Huffman Tree
