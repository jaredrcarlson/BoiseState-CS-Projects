#ifndef __MOTORCYCLE__
#define __MOTORCYCLE__

#include <iostream>
#include <string>
#include <cstdlib>
#include "vehicle.h"

using namespace std;

class Motorcycle : public Vehicle {
    private:
        static const int NUM_WHEELS = 2;
        static const int FLAT_RATE = 35;
 
    public:
        Motorcycle(string name, char new_symbol, float new_speed, float new_wheel_radius);
        int go(long elapsed_time);
        void honkHorn() const;
};
#endif
