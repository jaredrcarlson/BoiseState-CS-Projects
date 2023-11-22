/*
 * Name: Jared Carlson
 * Course: cs117
 * Instructor: Blaine Berguson
 *
 * Program Description:
 * This program allows the user to test the Finch robots movement.
 * The user can move the left and right wheels as well as stop the robot.
 *
 * Inputs:  User commands
 * 
 * Outputs: Commands sent to Finch emulator
 *			Log of commands for debugging
 *
 * Algorithm:
 *          BEGIN
 *             Disable <Return> key requirement for user input
 *			   Display program description
 *			   Open log file
 *			   IF opening log file fails THEN
 *	               Display error message
 *				   Enable <Return> key requirement for user input
 *			       Quit
 *	           ELSE
 *                 Write log file header
 *	           ENDIF
 *			   LOOP while user does not enter quit command
 *				   Display quick controls
 *			       LOOP while user input is invalid
 *                     Ask user for command
 *				       Validate user input
 *                 END LOOP
 *		           Set wheels motion
 *				   Send command to Finch
 *				   Write command and result to log file
 *			   END LOOP
 *			   Close log file
 *			   Display exit message with log filename
 *             Enable <Return> key requirement for user input
 *          END
 */
 
#include <iostream>
#include <fstream>
#include <sstream>
#include <string>
#include <iomanip>
#include <termios.h>
#include <unistd.h>

#include "Finch.h"

using namespace std;

class Keyboard {
    private:
        struct termios t;

    public:
        void disable_wait(); // keyboard will not wait for the enter key.
        void enable_wait();  // keyboard will wait for the enter key.
};

//Helper function to set both wheel directions based on user command
void setWheels(int&, int&, char);

int main()
{
    char selection;
	int left_wheel = 0;
    int	right_wheel = 0;
	int command_count = 0;
	int command_return_value;
	Finch myFinch;
	
	//Disable <return> key requirement for user input
	Keyboard kbd;
    kbd.disable_wait();
	
	//Display program description and controls
	cout << "----------------------------------------------------------------------------\n";
	cout << "------------------------- Finch Movement Generator -------------------------\n";
	cout << "----------------------------------------------------------------------------\n";
	cout << "--                                                                        --\n";
	cout << "--  This program will help you create a Finch movement file that can be   --\n";
	cout << "--  used with the Finch emulator to test the Finch robot's movements.     --\n";
	cout << "--                                                                        --\n";
	cout << "----------------------------------------------------------------------------\n";
	cout << "----------------------------------------------------------------------------\n";
	cout << "--                                                                        --\n";
	cout << "--  You can move the left and right wheels. (forward / backward)          --\n";
	cout << "--  You can also stop the Finch robot.                                    --\n";
	cout << "--  Simply use the first 3 fingers on your right hand for all controls.   --\n";
	cout << "--  No need to hit <return> after each command!                           --\n";
	cout << "--                                                                        --\n";
	cout << "----------------------------------------------------------------------------\n";
	cout << "----------------------------------------------------------------------------\n";
	cout << "--                                                                        --\n";
	cout << "--  Here are your controls (lower-case):                                  --\n";
	cout << "--                                                                        --\n";
	cout << "----------------------------------------------------------------------------\n";
	
	//Open log file : Check for error
	ofstream fout("griffin.log");
	if(fout.fail()) {
	    cout << "Error opening log file!";
		kbd.enable_wait();
		return 2;
	}
	//Write log file header
	else {
	    fout << setw(20) << left << "Order of commands" << setw(30) << left << "Function call" << setw(14) << left << "Return value" << endl;
	}
	
	do{
	    //Display quick controls
		cout << "\n\n----------------------------------------------------------------------------\n";
		cout << "--                     Go Straight: i                                     --\n";
		cout << "--  Rotate Left: j     Stop Finch: k     Rotate Right: l                  --\n";
		cout << "--                     Go Back: ,        Quit Program: q                  --\n";
		cout << "----------------------------------------------------------------------------\n";
		
		//Get command : validate
		cout << "Enter control: ";
		cin >> selection;
		while((selection != 'j') && (selection != 'i') && (selection != 'l') && 
		      (selection != 'k') && (selection != ',') && (selection != 'q') || (cin.fail())) {
		    cout << "Invalid control! - Try Again\n";
			cin.clear();
			cin.ignore();
			cout << "Enter control: ";
			cin >> selection;
		}
		
		//Set wheels motion
		setWheels(left_wheel,right_wheel,selection);
		
		//Send command to Finch : Error Check command
        command_count++;
		command_return_value = myFinch.setMotors(left_wheel,right_wheel);

        //Write command to log file
        ostringstream ss;
        ss << "setMotors(" << left_wheel << "," << right_wheel << ");";
        string command_sent = ss.str();
        fout << setw(20) << left << command_count << setw(30) << left << command_sent << setw(14) << left << command_return_value << endl;
	
    } while(selection != 'q');
	
	//Close log file
	fout.close();
	
	//Display exit message
	cout << "\n\nThanks for playing! - A log of your commands have been saved to \"griffin.log\"\n";
	
	kbd.enable_wait();	
    return 0;
}

void Keyboard::disable_wait() {
    tcgetattr(STDIN_FILENO, &t);
    t.c_lflag &= ~ICANON;
    tcsetattr(STDIN_FILENO, TCSANOW, &t);
}

void Keyboard::enable_wait() {
    tcgetattr(STDIN_FILENO, &t);
    t.c_lflag |= ICANON;
    tcsetattr(STDIN_FILENO, TCSANOW, &t);
}

void setWheels(int& left_w, int& right_w, char command) {
    const int PSPEED = 60;
	const int NSPEED = -60;
	const int STOP = 0;
	
	//Rotate Left
	if(command == 'j') {
		left_w = PSPEED;
		right_w = NSPEED;
	}
	//Go Straight
	else if(command == 'i') {
		left_w = PSPEED;
		right_w = PSPEED;
	}
	//Rotate Right
	else if(command == 'l') {
		left_w = NSPEED;
		right_w = PSPEED;
	}
	//Reverse
	else if(command == ',') {
		left_w = NSPEED;
		right_w = NSPEED;
	}
	//Stop : Both Stop command and Quit command should Stop Finch
	else {
		left_w = STOP;
		right_w = STOP;
	}
}
