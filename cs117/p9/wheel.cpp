#include "wheel.h"

Wheel::Wheel() {
    radius = 0;
    flat = false;
}

Wheel::Wheel(float new_radius) {
    radius = new_radius;
    flat = false;
}

void Wheel::setRadius(float new_radius) {
    radius = new_radius;
}

float Wheel::getRadius() const {
    return radius;
}

bool Wheel::isFlat() const {
    if(flat) {
        return true;
    }
    else {
        return false;
    }
}

void Wheel::setFlat() {
    flat = true;
}

void Wheel::fixFlat() {
    flat = false;
}
