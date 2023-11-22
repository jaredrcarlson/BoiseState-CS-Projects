#include "vehicle.h"

// based upon the num_wheels, this constructor should create each wheel and add it to the wheels vector.
Vehicle::Vehicle(string new_name, char new_symbol, float new_speed, int new_flat_rate, int num_wheels, float new_wheel_radius) {
    name = new_name;
    symbol = new_symbol;
    speed = new_speed;
    flat_rate = new_flat_rate;
    total_miles_traveled = 0;
    for(int i = 0; i < num_wheels; i++) {
        wheels.push_back(new Wheel(new_wheel_radius));
    }
}

// destructor. this should free up the memory that was allocated for each of the wheels.
Vehicle::~Vehicle() { 
    for(int i = 0; i < wheels.size(); i++) {
        delete wheels[i];
    }
}

string Vehicle::getName() const {
    return name;
}

float Vehicle::getSpeed() const {
    return speed;
}

char Vehicle::getSymbol() const {
    return symbol;
}

int Vehicle::go(long elapsed_time) {
    int damage = (rand() % 100) + 1;
    if (damage <= flat_rate) {
        causeFlat();
    }
    else {
        fixFlat();
    }
    float actual_speed = (wheels.size() - getNumFlatWheels())/(float)wheels.size() * speed;
    total_miles_traveled += elapsed_time * actual_speed;
    return total_miles_traveled;
}

// should display the name and symbol
void Vehicle::display() const { 
    cout << name << " (" << symbol << ")";
}

void Vehicle::honkHorn() const {
    cout << "Standard Vehicle Honk!\n";
}

void Vehicle::setMilesTraveled(int miles_traveled) {
    total_miles_traveled += miles_traveled;
}

int Vehicle::getMilesTraveled() const {
    return total_miles_traveled;
}

// returns the total number of flat wheels.
int Vehicle::getNumFlatWheels() const { 
    int flat_count = 0;
    for(int i = 0; i < wheels.size(); i++) {
        if(wheels[i]->isFlat()) {
            flat_count++;
        }
    }
    return flat_count;
}

// should change at least one tire to a status of flat regardless of whether it is already flat
void Vehicle::causeFlat() { 
    int i = rand() % wheels.size();
    wheels[i]->setFlat();
}

// should change at least one tire to a status of not flat
void Vehicle::fixFlat() { 
    for(int i = 0; i < wheels.size(); i++) {
        if(wheels[i]->isFlat()) {
            wheels[i]->fixFlat();
            return;
        }
    }
}
