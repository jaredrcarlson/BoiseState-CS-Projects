/* 
 * File:   Finch.h
 * Author: Administrator
 *
 * Created on May 23, 2011, 5:30 PM
 */

#ifndef FINCH_H
#define	FINCH_H

class Finch {
public:
    Finch();
    virtual ~Finch();
    int connect();
    int disConnect();
    int setLED(int red, int green, int blue);
    int setMotors(int leftWheelSpeed, int rightWheelSpeed);
    int setMotors(int leftWheelSpeed, int rightWheelSpeed, int duration);
    int noteOn(int frequency);
    int noteOn(int frequency, int duration);
    int noteOff();
    double getTemperature();
    double* getAccelerations();
    int* getLightSensors();
    int* getObstacleSensors();
    int wasTapped();
    int wasShaken();
    int isObstacleLeftSide();
    int isObstacleRightSide();
    int getLeftLightSensor();
    int getRightLightSensor();
    double getXAcceleration();
    double getYAcceleration();
    double getZAcceleration();
    int isBeakUp();
    int isBeakDown();
    int isFinchLevel();
    int isFinchUpsideDown();
    int isRightWingDown();
    int isLeftWingDown();
    int counter();
    void keepAlive();  
    int finchRead(unsigned char bufToWrite[], unsigned char bufRead[]);
    int finchWrite(unsigned char bufToWrite[]);
    static void* keepAliveEntryPoint(void * pThis)
      {
          Finch * pthX = (Finch*)pThis;   // cast from void to Finch object
          pthX->keepAlive();           // now call the true entry-point-function
          return 0;          // the thread exit code
      }
private:

};

#endif	/* FINCH_H */

