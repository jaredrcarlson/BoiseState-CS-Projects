#ifndef __TRUCK__
#define __TRUCK__

#include <iostream>
#include <string>
#include <cstdlib>
#include "vehicle.h"

using namespace std;

class Truck : public Vehicle {
    private:
        bool has_hitch;
        static const int NUM_WHEELS= 4;
        static const int FLAT_RATE = 40;

    public:
        Truck(string new_name, char new_symbol, float new_speed, float new_wheel_radius, bool new_has_hitch);
        int go(long elapsed_time);
        void honkHorn() const;
};
#endif
