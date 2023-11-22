#include <cstdlib>
#include <iostream>
#include <fstream>

#include "entropy.h"

using namespace std;

Entropy::Entropy() {
    temperature = 0.0;
    entropy = 0.0;
    table = new double*[6];
    for(int i = 0; i < 6; i++) {
        table[i] = new double[2];
    }
    for(int row = 0; row < 6; row++) {
        for(int col = 0; col < 2; col++) {
            table[row][col] = 0.0;
        }
    }
}

Entropy::~Entropy() {
    for(int i = 0; i < 6; i++) {
        delete[] table[i];
    }
    delete[] table;
}

bool Entropy::readFile(string new_filename) {
    bool status = true;
    ifstream fin(new_filename.c_str());
    if(fin.fail()) {
        status = false;
        return status;
    }
    int row = 0;
    double in_data0, in_data1;
    while(fin >> in_data0 >> in_data1) {
        table[row][0] = in_data0;
        table[row][1] = in_data1;
        row++;
    }
    return status;
}

double Entropy::calculateEntropy(double new_temperature, bool& valid) {
    double x = new_temperature;
    double x0, x1, y0, y1;
    double tem0 = table[0][0];
    double tem1 = table[1][0];
    double tem2 = table[2][0];
    double tem3 = table[3][0];
    double tem4 = table[4][0];
    double tem5 = table[5][0];
    if((x < tem0) || (x > tem5)) {
        valid = false;
        return 0.0;    
    }
    else {
        valid = true;
    }

    if(x <= tem1) {
        x0 = tem0;
        x1 = tem1;
        y0 = table[0][1];
        y1 = table[1][1];
    }
    else if(x <= tem2) {
        x0 = tem1;
        x1 = tem2;
        y0 = table[1][1];
        y1 = table[2][1];
    }
    else if(x <= tem3) {
        x0 = tem2;
        x1 = tem3;
        y0 = table[2][1];
        y1 = table[3][1];
    }
    else if(x <= tem4) {
        x0 = tem3;
        x1 = tem4;
        y0 = table[3][1];
        y1 = table[4][1];
    }
    else {
        x0 = tem4;
        x1 = tem5;
        y0 = table[4][1];
        y1 = table[5][1];
    }
    return y0 + (y1 - y0) * ((x - x0) / (x1 - x0));     
}
