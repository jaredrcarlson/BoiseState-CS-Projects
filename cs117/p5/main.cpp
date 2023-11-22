/*
 * Name: Animated Graph
 * Course: cs117
 * Instructor: Blaine Bergeson
 *
 * Program Description:
 * This program displays an animated graph.
 * The user can graph a line by choosing the grid size, x and y coordinates, and the slope.
 * The user can also choose to view a demo
 *
 * Inputs:  grid size
            x coordinate
 *          y coordinate
 *          slope
 *          
 *
 * Outputs: Displays animated graph of line defined by inputs
 *			Displays animated graph demo
 *
 * Algorithm:
 *     BEGIN
 *         LOOP until option 3 is selected
 *             Ask for grid size
 *             Display program options
 *             Validate option entered
 *             IF option 1 is selected THEN
 *                 Ask for x coordinate
 *                 Ask for y coordinate
 *                 Ask for slope
 *                 LOOP 15 times
 *                     Graph the line
 *                     Increment x by 2
 *                 END LOOP
 *             ELSE IF option 2 is selected THEN
 *                 Set x coordinate to zero
 *                 Set y coordinate to zero
 *                 Set slope to random number [-5,0)U(0,5]
 *                 LOOP 15 times
 *                     Graph the line
 *                     Decrement slope by 1
 *                 END LOOP
 *             ELSE
 *                 Quit
 *             END IF
 *		   END LOOP
 *	   END
 */
 
#include <iostream>
#include <cstdlib>
#include <time.h>

#include "line.h"
#include "graph.h"

using namespace std;

//This function will wait the number of seconds supplied as an argument
void wait(int);

int main() {
    const int GRID_MIN = 10;
    const int GRID_MAX = 50;
	const int SLOPE_MIN = -5;
	const int SLOPE_MAX = 5;
	const int SLOPE_CHANGE = 1;
	const int NUM_DISPLAYS = 15;
	const int SECONDS_BETWEEN_DISPLAYS = 1;
	const int NUM_MOVE = 2;
	
    int choice = 0;
	int graph_size_value = 0;
	int x_value = 0;
	int y_value = 0;
	int slope_value = 0;
	srand (time(NULL));
	
    cout << "------------------------------------------\n";
    cout << "Welcome to the Animated Graph program!\n";
    cout << "------------------------------------------\n\n";
    
    do {
		cout << "Enter desired grid size [positive integer 10-50]: ";
        cin >> graph_size_value;
        while((cin.fail()) || (graph_size_value < GRID_MIN) || (graph_size_value > GRID_MAX)) {
            cin.clear();
            cin.ignore();
            cout << "Sorry, you must enter a positive integer from " << GRID_MIN << " to " << GRID_MAX << ": ";
            cin >> graph_size_value;
        }
        
        cout << "------------------------------------------\n";
		cout << "Program Options:\n";
		cout << "(1) Graph a line\n";
		cout << "(2) Demo\n";
		cout << "(3) Quit Program\n";
		cout << "------------------------------------------\n";
		cout << "Please make a selection [1-3]: ";
		cin >> choice;
        if(cin.fail()) {
            cin.clear();
	        cin.ignore();
        }
      	
		switch(choice) {
			case 1: {
                cout << "Enter x coordinate [positive or negative integer]: ";
                cin >> x_value;
                while((cin.fail()) || (x_value < -graph_size_value) || (x_value > graph_size_value)) {
                    cin.clear();
	                cin.ignore();
	                cout << "Sorry, you must enter an integer from " << -graph_size_value << " to " << graph_size_value << ": ";
	                cin >> x_value;
                }
                cout << "Enter y coordinate [positive or negative integer]: ";
                cin >> y_value;
                while((cin.fail()) || (y_value < -graph_size_value) || (y_value > graph_size_value)) {
                    cin.clear();
	                cin.ignore();
	                cout << "Sorry, you must enter an integer from " << -graph_size_value << " to " << graph_size_value << ": ";
	                cin >> y_value;
                }
                cout << "Enter slope [positive or negative integer]: ";
                cin >> slope_value;
                while(cin.fail()) {
                    cin.clear();
	                cin.ignore();
	                cout << "Sorry, you must enter a positive or negative integer: ";
	                cin >> slope_value;
                }  
                Line line_1(x_value, y_value, slope_value);
                Graph graph_1(graph_size_value);
                
                /*  When x coord begins on left side - Move to the right
                    When x coord begins on right side - Move to the left  */
                if(x_value <= 0) {
                    for (int count = 1; count <= NUM_DISPLAYS; count++) {
                        graph_1.graph_it(line_1);
                        line_1.move_x(NUM_MOVE);
                        wait(SECONDS_BETWEEN_DISPLAYS);
                    }
                }
                else {
                    for (int count = 1; count <= NUM_DISPLAYS; count++) {
                        graph_1.graph_it(line_1);
                        line_1.move_y(NUM_MOVE);
                        wait(SECONDS_BETWEEN_DISPLAYS);
                    }
                }
                break;
            } 		
			case 2: {
                x_value = 0;
                y_value = 0;
            
                //Generate random slope ignoring zero
                slope_value = rand() % (SLOPE_MAX - SLOPE_MIN + 1) + SLOPE_MIN;
                while(slope_value == 0) {
                    slope_value = rand() % (SLOPE_MAX - SLOPE_MIN + 1) + SLOPE_MIN;
                }
                Line line_demo(x_value, y_value, slope_value);
                Graph graph_demo(graph_size_value);
                        
                for (int count = 1; count <= NUM_DISPLAYS; count++) {
                    graph_demo.graph_it(line_demo);
                    slope_value -= SLOPE_CHANGE;
                    line_demo.set_slope(slope_value);
                    wait(SECONDS_BETWEEN_DISPLAYS);
                } 
			    break;
            }
			case 3: {
				cout << "Good-Bye!\n";
			    break;
            }
			default: {
                cout << "That's not a valid option, try again.\n\n";
				break;
            }   
		}
	} while(choice != 3);
	return 0;
}

void wait(int seconds) {
    time_t start = time(NULL);
    while((time(NULL) - start) < seconds) {
    }
}
