#ifndef __VEHICLE__
#define __VEHICLE__

#include <cstdlib>
#include <iostream>
#include <vector>
#include "wheel.h"

using namespace std;

class Vehicle {
    private:
        char symbol;  
        string name;  
        float speed;  
        int flat_rate; 
        vector<Wheel*> wheels;
        int total_miles_traveled;

    public:
        Vehicle(string new_name, char new_symbol, float new_speed, int new_flat_rate, int num_wheels, float new_wheel_radius);
        ~Vehicle(); 

        string getName() const;
        float getSpeed() const;
        char getSymbol() const;

        virtual int go(long elapsed_time); 
        void display() const; 
        virtual void honkHorn() const; 
        void setMilesTraveled(int miles_traveled);
        int getMilesTraveled() const;

        int getNumFlatWheels() const ; 
        void causeFlat(); 
        void fixFlat(); 
};
#endif
