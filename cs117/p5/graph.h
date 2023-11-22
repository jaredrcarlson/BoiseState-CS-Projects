/*
UML

---------------------------------------
Graph
---------------------------------------
- grid_size : int
---------------------------------------
+ Graph()
+ set_grid_size(int size) : void
+ graph_it(Line l) : void
---------------------------------------

*/

#include "line.h"

using namespace std;

#ifndef GRAPH_H
#define GRAPH_H

class Graph {
	public:
		Graph();
		Graph(int);
		void set_grid_size(int);
		void graph_it(Line);
	private:
		int grid_size;
};

#endif
