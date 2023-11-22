/*
 * File:   Finch.cpp
 * Author: Tom Lauwers, BirdBrain Technologies (tlauwers@finchrobot.com)
 *
 * Contains all the necessary functions to run a Finch and get data back from it.
 * More information at www.finchrobot.com
 *
 * Created on May 23, 2011, 5:30 PM
 */

#include "Finch.h"
#include <stdio.h>
#include <wchar.h>
#include <string.h>
#include <stdlib.h>
#ifdef __GNUC__
#include <unistd.h>
#endif 

#include "hidapi.h"

/* Global Variables */
hid_device *finch_handle; // The handle to communicate with the Finch
char sendReportCounter = 0; // Used to match incoming and outgoing report in the finchRead function
int syncLock = 0; // Used to prevent the threads from both reading from Finch simultaneously
int wasTappedVal; // Holds whether the Finch has been tapped since the last read
int wasShakenVal; // Holds whether the Finch has been shaken since the last read

/**
 * Constructs a Finch object
 * Calling this constructor automatically connects the program to the robot, and
 * launches a second thread to prevent the Finch from timing out and returning to idle mode
 * before the program ends.
 */
Finch::Finch()
{
	int success;
	success = connect(); // connect Finch
}

/**
 * Destructs a Finch object
 * Destructor calls the disConnect function, which closes the connection and returns
 * Finch to an idle state.
 */
Finch::~Finch()
{
	disConnect();
}

/**
 * Connects to a Finch object. Called by the constructor. Left public for creative uses, but in general should not
 * be used independent of the constructor.
 * @return -1 if connection failed, 1 if connection succeeded.
 */
int Finch::connect()
{
	// Associate the handle with the Finch's VID (0x2354) and PID (0x1111)
	finch_handle = hid_open(0x2354, 0x1111, NULL);
	if (!finch_handle) {
		printf("Unable to connect to Finch, maybe it's not plugged in or another Finch program is already running?\n");
		return -1;
	} else {
		// Turn off the LED to indicate that the connection succeeded
		setLED(0,0,0);
		return 1;
	}
}
/**
 * Tells Finch to go back to idle mode, then closes the connection
 * @return a positive number if resetting the Finch succeeded, -1 if it failed
 */
int Finch::disConnect()
{
	unsigned char bufToWrite[9];
	int res;

	// send an 'R', which resets the Finch to idle mode
	bufToWrite[0] = 0x0;
	bufToWrite[1] = 'R';

	res = finchWrite(bufToWrite);
	hid_close(finch_handle);
	return res;
}

/**
 * Sets the color and intensity of the beak LED.
 * @param red Intensity of the red color element, range is 0 to 255
 * @param green Intensity of the green color element, range is 0 to 255
 * @param blue Intensity of the blue color element, range is 0 to 255
 * @return a positive number if the LED was set, -1 if the command failed.
 */
int Finch::setLED(int red, int green, int blue)
{
	unsigned char bufToWrite[9];

	// Check to make sure that the values are in range
	if(red > 255 || red < 0 || green > 255 || green < 0 || blue > 255 || blue < 0) {
		printf("Error, a light sensor value is out of range (-255 to 255)\n");
		return -1;
	} else {
		// Create command report, then write it to the Finch
		bufToWrite[0] = 0x0;
		bufToWrite[1] = 'O';
		bufToWrite[2] = (char)red;
		bufToWrite[3] = (char)green;
		bufToWrite[4] = (char)blue;
		return finchWrite(bufToWrite);
	}
}

/**
 * Sets the speed of the left and right wheels.
 * @param leftWheelSpeed Power to the left wheel, range is -255 to 255
 * @param rightWheelSpeed Power to the right wheel, range is -255 to 255
 * @return a positive number if the motors were set, -1 if the command failed.
 */
int Finch::setMotors(int leftWheelSpeed, int rightWheelSpeed)
{
	unsigned char bufToWrite[9];

	char leftDir = 0;
	char rightDir = 0;
	// If the numbers are negative, set the direction bit to 1, and make the negative speed positive
	if(leftWheelSpeed < 0) {
		leftWheelSpeed = -leftWheelSpeed;
		leftDir = 1;
	}
	if(rightWheelSpeed < 0) {
		rightWheelSpeed = -rightWheelSpeed;
		rightDir = 1;
	}

	// Check to make sure speeds are within the range
	if(leftWheelSpeed > 255 || rightWheelSpeed > 255) {
		printf("Error, speed value is out of range (-255 to 255)\n");
		return -1;
	} else {
		// Create a command report to set the motor speeds.
		bufToWrite[0] = 0x0;
		bufToWrite[1] = 'M';
		bufToWrite[2] = leftDir;
		bufToWrite[3] = (char)leftWheelSpeed;
		bufToWrite[4] = rightDir;
		bufToWrite[5] = (char)rightWheelSpeed;
		// Write the report to Finch
		return finchWrite(bufToWrite);
	}
}

/**
 * Sets the speed of the left and right wheels for a specified period of time, after which they turn off.
 * This function blocks program execution by the amount of specified by duration.
 * @param leftWheelSpeed Power to the left wheel, range is -255 to 255
 * @param rightWheelSpeed Power to the right wheel, range is -255 to 255
 * @param duration The time in milliseconds to maintain the set speeds
 * @return a positive number if the motors were set, -1 if the command failed.
 */
int Finch::setMotors(int leftWheelSpeed, int rightWheelSpeed, int duration)
{
	int returnVal;
	// Set the speeds
	returnVal = setMotors(leftWheelSpeed, rightWheelSpeed);
	// Sleep the program
	usleep(duration*1000);
	// Turn off the motors
	setMotors(0,0);
	return returnVal;
}

/**
 * Turns on the Finch's buzzer to beep at a certain frequency
 * @param frequency The frequency in Hertz to beep at
 * @return a positive number if the buzzer was set, -1 if the command failed.
 */
int Finch::noteOn(int frequency)
{
	unsigned char bufToWrite[9];

	bufToWrite[0] = 0x0;
	bufToWrite[1] = 'B';
	bufToWrite[2] = 0xFF;
	bufToWrite[3] = 0xFF;
	bufToWrite[4] = (char)((frequency & 0x0000FFFF)>>8);
	bufToWrite[5] = (char)(frequency & 0x000000FF);

	return finchWrite(bufToWrite);
}

/**
 * Turns on the Finch's buzzer to beep at a certain frequency for a certain period of time.
 * This command blocks program execution by the amount of time specified by duration.
 * @param frequency The frequency in Hertz to beep at
 * @param duration The duration in milliseconds to hold the note for.
 * @return a positive number if the buzzer was set, -1 if the command failed.
 */
int Finch::noteOn(int frequency, int duration)
{
	int returnVal;
	returnVal = noteOn(frequency);
	noteOff();
	return returnVal;
}

/**
 * Turns off the Finch's buzzer.
 * @return a positive number if the buzzer was turned off, -1 if the command failed.
 */
int Finch::noteOff()
{
	unsigned char bufToWrite[9];

	bufToWrite[0] = 0x0;
	bufToWrite[1] = 'B';
	bufToWrite[2] = 0x0;
	bufToWrite[3] = 0x0;
	bufToWrite[4] = 0x0;
	bufToWrite[5] = 0x0;

	return finchWrite(bufToWrite);
}

/**
 * Gets the temperature as measured by the Finch's thermometer
 * @return The temperature in degrees Celcius, -1 if the read failed.
 */
double Finch::getTemperature()
{
	unsigned char bufToWrite[9]; // Holds the command report
	unsigned char bufRead[9]; // Holds the raw returned data
	double temperature; // Holds the final calculated temperature

	// Create a command report that requests temperature data
	bufToWrite[0] = 0x0;
	bufToWrite[1] = 'T';
	if(finchRead(bufToWrite, bufRead)==1) {
		temperature = (bufRead[0]-127)/2.4 + 25; // Convert raw temperature to Celcius
		return temperature;
	} else {
		return -1;
	}
}

/**
 * Gets the X, Y, and Z acceleration values in G's experienced by the Finch's accelerometer
 * @return An array of 3 doubles holding X, Y, and Z acceleration, null if the read failed.
 */
double* Finch::getAccelerations()
{
	unsigned char bufToWrite[9]; // Holds the command report
	unsigned char bufRead[9]; // Holds the raw returned data
	double* accelerations = new double[3];

	bufToWrite[0] = 0x0;
	bufToWrite[1] = 'A';
	if(finchRead(bufToWrite, bufRead)==1) {
		// Convert the raw accelerometer data to G-forces
		for(int i = 0; i < 3; i++) {
			if(bufRead[i+1] > 31) {
				accelerations[i] = (bufRead[i+1]-64)*1.5/32;
			} else {
				accelerations[i] = bufRead[i+1]*1.5/32;
			}
		}
		// Check if the latest read indicated the Finch was shaken or tapped.
		// If so, set the wasTapped and/or wasShaken flags.
		wasTappedVal = wasTappedVal || ((bufRead[4] & 0x20)>>5);
		wasShakenVal = wasShakenVal || ((bufRead[4] & 0x80)>>7);
		return accelerations;
	} else {
		return 0;
	}
}

/**
 * Gets the left and right light sensor values. Values range from 0 to 255 with higher values
 * indicating more light.
 * @return An array of two values holding the left and right light sensor values, null if read failed.
 */
int* Finch::getLightSensors()
{
	unsigned char bufToWrite[9]; // Holds command report
	unsigned char bufRead[9]; // Holds raw returned data
	int* lightSensors = new int[2]; // Holds array to return

	bufToWrite[0] = 0x0;
	bufToWrite[1] = 'L';
	if(finchRead(bufToWrite, bufRead)==1) {
		lightSensors[0] = (int)bufRead[0]; // Convert values from char to int
		lightSensors[1] = (int)bufRead[1];
		return lightSensors;
	} else {
		return 0;
	}
}

/**
 * Gets the state of the left and right obstacle sensors. 0 if the sensor does not detect an obstacle,
 * 1 if it does.
 * @return An array of two values holding the left and right obstacle sensor values, null if read failed.
 */
int* Finch::getObstacleSensors()
{
	unsigned char bufToWrite[9];
	unsigned char bufRead[9];
	int* obstacleSensors = new int[2];

	bufToWrite[0] = 0x0;
	bufToWrite[1] = 'I';
	if(finchRead(bufToWrite, bufRead)==1) {
		obstacleSensors[0] = (int)bufRead[0];
		obstacleSensors[1] = (int)bufRead[1];
		return obstacleSensors;
	} else {
		return 0;
	}
}

/**
 * Returns if the Finch was tapped since the last call to wasTapped (no matter how long ago that may have been),
 * or since the start of the program if this is the first call to wasTapped.
 * @return 1 if the Finch was tapped, 0 if not, -1 if the read failed.
 */
int Finch::wasTapped()
{
	int toReturn;
	// We need to do at least one call to getAccelerations to get the current tapped state
	// We are also using this call to detect failure to read
	if(getAccelerations()==0) {
		return -1;
	}
	toReturn = wasTappedVal;
	wasTappedVal = 0;
	return toReturn;
}

/**
 * Returns if the Finch was shaken since the last call to wasShaken (no matter how long ago that may have been),
 * or since the start of the program if this is the first call to wasShaken.
 * @return 1 if the Finch was shaken, 0 if not, -1 if the read failed.
 */
int Finch::wasShaken()
{
	int toReturn;
	// We need to do at least one call to getAccelerations to get the current shaken state
	// We are also using this call to detect failure to read
	if(getAccelerations()==0) {
		return -1;
	}
	toReturn = wasShakenVal;
	wasShakenVal = 0;
	return toReturn;
}

/**
 * Used by the keep alive method, left public for creative uses but not ordinarily used by the user.
 * This function reads the Finch's ping method, which simply returns the number of times the Finch has been
 * pinged (a number from 0 to 255, overflow returns the value to 0).
 * @return Number of times the counter method has been called, -1 if read failed.
 */
int Finch::counter()
{
	unsigned char bufToWrite[9];
	unsigned char bufRead[9];

	bufToWrite[0] = 0x0;
	bufToWrite[1] = 'z';
	if(finchRead(bufToWrite, bufRead)==1) {
		return ((int)bufRead[0]);
	} else {
		return -1;
	}
}

/**
 * Returns the state of the left obstacle sensor
 * @return The state of the left obstacle sensor: 1 for obstacle exists, 0 for no obstacle, -1 for read failed.
 */
int Finch::isObstacleLeftSide()
{
	int* obstacles = getObstacleSensors();
	if(obstacles == 0) {
		return -1;
	} else {
		return obstacles[0];
	}
}

/**
 * Returns the state of the right obstacle sensor
 * @return The state of the right obstacle sensor: 1 for obstacle exists, 0 for no obstacle, -1 for read failed.
 */
int Finch::isObstacleRightSide()
{
	int* obstacles = getObstacleSensors();
	if(obstacles == 0) {
		return -1;
	} else {
		return obstacles[1];
	}
}

/**
 * Returns the value of the left light sensor.
 * @return Intensity of light falling on left light sensor, values range from 0-255. -1 if read failed.
 */
int Finch::getLeftLightSensor()
{
	int* lightSensors = getLightSensors();
	if(lightSensors == 0) {
		return -1;
	} else {
		return lightSensors[0];
	}
}

/**
 * Returns the value of the right light sensor.
 * @return Intensity of light falling on right light sensor, values range from 0-255. -1 if read failed.
 */
int Finch::getRightLightSensor()
{
	int* lightSensors = getLightSensors();
	if(lightSensors == 0) {
		return -1;
	} else {
		return lightSensors[1];
	}
}

/**
 * The value in G's of acceleration along the Finch's X-axis (beak to tail)
 * @return The value in G's of acceleration along the Finch's X-axis (beak to tail), valid values range from -1.5 to 1.5, a value of -2 indicates read failed.
 */
double Finch::getXAcceleration()
{
	double* accelerations = getAccelerations();
	if(accelerations == 0) {
		return -2;
	} else {
		return accelerations[0];
	}
}

/**
 * The value in G's of acceleration along the Finch's Y-axis (wheel to wheel)
 * @return The value in G's of acceleration along the Finch's Y-axis (wheel to wheel), valid values range from -1.5 to 1.5, a value of -2 indicates read failed.
 */
double Finch::getYAcceleration()
{
	double* accelerations = getAccelerations();
	if(accelerations == 0) {
		return -2;
	} else {
		return accelerations[1];
	}
}

/**
 * The value in G's of acceleration along the Finch's Z-axis (bottom to top)
 * @return The value in G's of acceleration along the Finch's X-axis (bottom to top), valid values range from -1.5 to 1.5, a value of -2 indicates read failed.
 */
double Finch::getZAcceleration()
{
	double* accelerations = getAccelerations();
	if(accelerations == 0) {
		return -2;
	} else {
		return accelerations[2];
	}
}

/**
* This method returns 1 if the beak is up (Finch sitting on its tail), 0 otherwise
*
* @return 1 if beak is pointed at ceiling, 0 if not, -1 if reading failed
*/
int Finch::isBeakUp()
{
	double* accels = getAccelerations();
	if (accels != 0) {
		if (accels[0] < -0.8 && accels[0] > -1.5 && accels[1] > -0.3 && accels[1] < 0.3 && accels[2] > -0.3 && accels[2] < 0.3) {
			return 1;
		} else {
			return 0;
		}
	} else {
		return -1;
	}
}

/**
* This method returns true if the beak is pointed at the floor, false otherwise
*
* @return 1 if beak is pointed at the floor, 0 if not, -1 if reading failed.
*/
int Finch::isBeakDown()
{
	double* accels = getAccelerations();
	if (accels != 0) {
		if (accels[0] < 1.5 && accels[0] > 0.8 && accels[1] > -0.3 && accels[1] < 0.3 && accels[2] > -0.3 && accels[2] < 0.3) {
			return 1;
		} else {
			return 0;
		}
	} else {
		return -1;
	}
}

/**
* This method returns true if the Finch is on a flat surface
*
* @return 1 if the Finch is level, 0 if not, -1 if reading failed.
*/
int Finch::isFinchLevel()
{
	double* accels = getAccelerations();
	if (accels != 0) {
		if (accels[0] > -0.5 && accels[0] < 0.5 && accels[1] > -0.5 && accels[1] < 0.5 && accels[2] > 0.65 && accels[2] < 1.5) {
			return 1;
		} else {
			return 0;
		}
	} else {
		return false;
	}
}

/**
* This method returns true if the Finch is upside down, false otherwise
*
* @return 1 if Finch is upside down, 0 if not, -1 if reading failed.
*/
int Finch::isFinchUpsideDown()
{
	double* accels = getAccelerations();
	if (accels != 0) {
		if (accels[0] > -0.5 && accels[0] < 0.5 && accels[1] > -0.5 && accels[1] < 0.5 && accels[2] > -1.5 && accels[2] < -0.65) {
			return 1;
		} else {
			return 0;
		}
	} else {
		return -1;
	}
}

/**
* This method returns true if the Finch's left wing is pointed at the ground
*
* @return 1 if Finch's left wing is down, 0 if not, -1 if reading failed.
*/
int Finch::isLeftWingDown()
{
	double* accels = getAccelerations();
	if (accels != 0) {
		if (accels[0] > -0.5 && accels[0] < 0.5 && accels[1] > 0.7 && accels[1] < 1.5 && accels[2] > -0.5 && accels[2] < 0.5) {
			return 1;
		} else {
			return 0;
		}
	} else {
		return -1;
	}
}

/**
* This method returns true if the Finch's right wing is pointed at the ground
*
* @return 1 if Finch's right wing is down, 0 if not, -1 if reading failed.
*/
int Finch::isRightWingDown()
{
	double* accels = getAccelerations();
	if (accels != 0) {
		if (accels[0] > -0.5 && accels[0] < 0.5 && accels[1] > -1.5 && accels[1] < -0.7 && accels[2] > -0.5 && accels[2] < 0.5) {
			return 1;
		} else {
			return 0;
		}
	} else {
		return -1;
	}
}

/**
 * Not for use by user. The function called to keep the Finch from moving into idle mode while
 * a program is running.
 */
void Finch::keepAlive()
{
	while(1) {
		if(syncLock == 0) {
			counter(); // Note that calling this makes syncLock = 1, so at most this gets called every two seconds
		} else {
			syncLock = 0; // Reset syncLock
		}
		sleep(1);
	}
}

/**
 * Generic function to read data from the Finch.
 * @param bufToWrite 9-byte array to send to Finch to indicate what should be read.
 * @param bufRead 9-byte array containing raw returned value from Finch
 * @return -1 if read failed, 1 is read succeeded.
 */
int Finch::finchRead(unsigned char bufToWrite[], unsigned char bufRead[])
{
	int res; // Holds the result of the HIDAPI hid_write and hid_read commands
	unsigned char tempReportCounter=sendReportCounter;
	// Use the "sendReportCounter" to associate a specific command report with a resulting
	// read report.
	// If we're using the 'z' command (counter command), we don't need to do this since we're
	// only using it for the keepAlive thread.
	if(bufToWrite[1] != 'z') {
		bufToWrite[8] = sendReportCounter;
		sendReportCounter++;
	}
	syncLock = 1; // Prevent the other thread from calling a finchRead at the same time

	// Write a command report
	res = hid_write(finch_handle, bufToWrite, 9);
	if(res == -1) {
		printf("Error, failed to write a read command.");
		printf("Error is: %ls\n", hid_error(finch_handle));
		return -1;
	} else {
		// Read the raw data using hid_read. If the returned report counter value does
		// not match our value, try again (this is for safety only, in practice, this loop
		// only runs once).
		do {
			res = hid_read(finch_handle, bufRead, 8);
			if(res == -1) {
				printf("Error, failed to read.");
				printf("Error is: %ls\n", hid_error(finch_handle));
				return -1;
			}
		} while((bufRead[7] != tempReportCounter)&& (bufToWrite[1] != 'z'));
		// If you got here, your read succeeded. Yeay!
		return 1;

	}
}

/**
 * Generic write-only method, used for set functions that don't expect returned data.
 * @param bufToWrite A 9 byte array containing the command report.
 * @return A positive number of the write succeeded, -1 if it failed.
 */
int Finch::finchWrite(unsigned char bufToWrite[])
{
	int returnVal;
	// Make sure the keepAlive thread knows we're writing the Finch so it doesn't
	// try to execute a write at the same time.
	syncLock = 1;
	returnVal = hid_write(finch_handle, bufToWrite, 9);
	return returnVal;
}
