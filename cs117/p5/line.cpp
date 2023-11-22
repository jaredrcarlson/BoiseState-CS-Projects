#include "line.h"

Line::Line(int x, int y, int s) {
	x_coord = x;
	y_coord = y;
	slope = s;
	y_intercep = y_coord - (slope * x_coord);
}

bool Line::contains_coord(int x, int y) {
    int temp_y_intercep = y - (slope * x);
	if(temp_y_intercep == y_intercep) {
	    return true;
	}
	else {
	    return false;
	}
}

void Line::move_x(int x_increment) {
	x_coord += x_increment;
	y_intercep = y_coord - (slope * x_coord);
}
		
void Line::move_y(int y_increment) {
	y_coord += y_increment;
	y_intercep = y_coord - (slope * x_coord);
}

void Line::set_slope(int newSlope) {
	slope = newSlope;
	y_intercep = y_coord - (slope * x_coord);
}		
