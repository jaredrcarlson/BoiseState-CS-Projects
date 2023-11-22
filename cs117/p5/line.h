/*
UML

---------------------------------------
Line
---------------------------------------
- x_coord : int
- y_coord : int
- y_intercep : int
- slope : int
---------------------------------------
+ Line(int x, int y, int s) :
+ contains_coord(int x, int y) : bool
+ move_x(int x_increment) : void
+ move_y(int y_increment) : void
+ set_slope(int newSlope) : void
---------------------------------------

*/

#include <iostream>

#ifndef LINE_H
#define LINE_H

class Line {
	public:
		Line(int, int, int);
		bool contains_coord(int, int);
		void move_x(int);
		void move_y(int);
		void set_slope(int);
	private:
		int x_coord;
		int y_coord;
		int y_intercep;
		int slope;
};

#endif
