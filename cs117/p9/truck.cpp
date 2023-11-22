#include "truck.h"

Truck::Truck(string new_name, char new_symbol, float new_speed, float new_wheel_radius, bool new_has_hitch)
    : Vehicle(new_name, new_symbol, new_speed, FLAT_RATE, NUM_WHEELS, new_wheel_radius)
{
    has_hitch = new_has_hitch;
}

// calls the base class go and then randomly will increase the miles due to a nitro burst.
int Truck::go(long elapsed_time) { 
    int mile_start = Vehicle::getMilesTraveled();
    int miles_gained = Vehicle::go(elapsed_time) - mile_start;
    int boost_miles = 0; 
    if((rand() % 100) < 40) {
        boost_miles = (rand() % 4) + 2;
        miles_gained += boost_miles;
        Vehicle::setMilesTraveled(boost_miles);
    }
    return miles_gained;
}

// displays something that represents this type of horn.
void Truck::honkHorn() const { 
    cout << "Honk! Honk!\n";
    //Play Truck horn audio file
    system("play -q truck_horn.wav");
}
