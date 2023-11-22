#ifndef __WHEEL__
#define __WHEEL__

using namespace std;

class Wheel {
    private:
        float radius;
        bool flat;

    public:
        Wheel();
        Wheel(float new_radius);
        void setRadius(float new_radius);
        float getRadius() const;
        bool isFlat() const;
        void setFlat();
        void fixFlat();
};

#endif
