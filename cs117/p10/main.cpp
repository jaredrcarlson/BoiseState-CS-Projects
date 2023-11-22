/*
 * Name: Jared Carlson
 * Course: cs117
 * Instructor: Blaine Berguson
 *
 * Program Description:
 * This program calculates the entropy for 5 different temperatures.
 * The program uses a table of known temperature to entropy values ranging from 150 to 500 degrees Celsius.
 * The program produces a text file with a table of input temperatures and calculated entropy values.
 *
 * Inputs:  Temperature to Entropy chart
 *          5 Temperatures
 * 
 * Outputs: Text file report of Temperatures to Entropy values
 *
 * Algorithm:
 *          BEGIN
 *               Create entropy object
 *               Read temperature to entropy chart file into object           
 *               IF unable to open file THEN
 *                  Display error message
 *                  Quit
 *               END IF
 *
 *               Store 5 temperature values
 *
 *               Open output file
 *               IF unable to open output file THEN
 *                  Display error message
 *                  Quit
 *               ELSE
 *                  Write header to output file
 *               END IF
 *
 *               LOOP 5 times
 *                  calculate entropy for input temperature
 *                  IF temperature is out of range THEN
 *                     Display error message
 *                     Quit
 *                  ELSE
 *                     Write temperature and entropy values to output file
 *                  END IF
 *               END LOOP
 *               Close output file
 *               Display program completed message with output file name               
 *          END
 */

#include <cstdlib>
#include <iostream>
#include <fstream>
#include <iomanip>

#include "entropy.h"

using namespace std;

int main() {
    Entropy e1;
    string filename = "Temp2EntropyTable.dat";
    bool opened = e1.readFile(filename);
    if(!opened) {
        cout << "Unable to open data file!";
        return 1;
    }
    
    double temperatures[5] = {169.2, 183.4, 420.7, 322.1, 400.0};
    
    bool valid_tem;
    double ent;
    
    ofstream fout("Entropy_Report.txt");
    if(fout.fail()) {
        cout << "Error creating report file!";
        return 2;
    }
    else {
        fout << setw(17) << left << "Temperature (C)" << setw(17) << left << "Entropy (kJ/kg.K)" << endl;
    }
    
    for(int i = 0; i < 5; i++) {
        ent = e1.calculateEntropy(temperatures[i], valid_tem);
        if(!valid_tem) {
        cout << "Invalid Temperature Detected! : " << temperatures[i] << endl;
        fout << setw(17) << left << fixed << setprecision(1) << temperatures[i] << setw(17) << left << "--Invalid Temperature!" << endl;
        }
        else {
            fout << setw(17) << left << fixed << setprecision(1) << temperatures[i] << setw(17) << left << fixed << setprecision(4) << ent << endl;
        }
    }
       
    fout.close();
    
    cout << "Program Completed Successfully! - \"Entropy_Report.txt\" has been created.\n";
    
    return 0;
}
