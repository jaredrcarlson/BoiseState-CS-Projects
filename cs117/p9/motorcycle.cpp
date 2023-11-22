#include "motorcycle.h"

Motorcycle::Motorcycle(string name, char new_symbol, float new_speed, float new_wheel_radius)
    : Vehicle(name, new_symbol, new_speed, FLAT_RATE, NUM_WHEELS, new_wheel_radius)
{
}

// calls the base class go and then randomly increases the miles due to a turbo boost.
int Motorcycle::go(long elapsed_time) { 
    int mile_start = Vehicle::getMilesTraveled();
    int miles_gained = Vehicle::go(elapsed_time) - mile_start;
    int blast_miles = 0; 
    if((rand() % 100) < 40) {
        blast_miles = (rand() % 4) + 2;
        miles_gained += blast_miles;
        Vehicle::setMilesTraveled(blast_miles);
    }
    return miles_gained;
}

// displays something that represents this type of horn.
void Motorcycle::honkHorn() const { 
    cout << "Beep! Beep!\n";
    //Play Motorcyle horn audio file
    system("play -q motorcycle_horn.wav");
}
