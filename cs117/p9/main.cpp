#include <cstdlib>
#include <iostream>
#include <fstream>
#include <string>
#include <ctime>
#include <iomanip>

#include "vehicle.h"
#include "wheel.h"
#include "truck.h"
#include "motorcycle.h"

using namespace std;

void displayHelp();

void wait(int duration);

int main(int argc, char* argv[]) {
    srand(time(NULL));
    int race_length = 0;
    int miles_remaining = 0;
    int race_speed = 0;
    vector<Vehicle*> vehicles;
    vector<Vehicle*> winners;
    int mile_end = 0;
    bool victory = false;
    char v_type = ' ';
    string v_name;
    char v_symbol = ' ';
    float v_speed = 0.0;
    float v_wrad = 0.0;
    Truck* t_ptr;
    Motorcycle* m_ptr;
    
    
    //Process command line arguments : Display Help on error
    if(argc < 4) {
        cout << "You did not enter enough arguments.";
        displayHelp();
        return 1;
    }
    else {
         for(int i = 1; i < 4; i++) {
            string arg = argv[i];
            if(arg == "-tm") {
                race_length = atoi(argv[i+1]);
                i++;
            }
            else if(arg == "-f") {
                race_speed = 1;
            }
            else if(arg == "-s") {
                race_speed = 3;
            }
            else {
                cout << "Invalid option(s).";
                displayHelp();
                return 2;
            }
        }
    }
    
    //Open input file : error check
    ifstream fin("vehicles.dat");
    if(fin.fail()) {
        cout << "Problem opening input file!";
        return 3;
    }
    //Get racers from input file
    else {
        while(fin >> v_type >> v_name >> v_symbol >> v_speed >> v_wrad) {
            if(v_type == 't') {
                bool hitch = false;
                if((rand() % 2) > 0) {
                    hitch = true;
                }
                vehicles.push_back(new Truck(v_name, v_symbol, v_speed, v_wrad, hitch));
            }             
            else if(v_type == 'm') {
                vehicles.push_back(new Motorcycle(v_name, v_symbol, v_speed, v_wrad));
            }
            else {
                cout << "\nSorry " << v_name << ", only Trucks or Motorcycles can enter this race.";
            }    
        }
    }
    
    //Close input file
    fin.close();
    
    //Display Start Race message    
    system("clear");
    cout << "\n\n***    Welcome to the Indy " << race_length << "    ***\n\n";
    cout << "Let's look at today's racers:\n";
    cout << "------------------------------------\n";
    
    //Set Finish Line
    int finish_line = race_length;
    
    //Display Racers
    for(int i = 0; i < vehicles.size(); i++) {
        cout << "Lane " << i+1 << ": ";
        vehicles[i]->display();
        cout << endl;
    }
    
    wait(6);
    cout << "Ready Go!\n\n";
    wait(1);
    
    //Race until the finish line is reached
    while(!victory) {
	    system("clear");
        //Step through each vehicle using vi = vehicle index
		for(int vi = 0; vi < vehicles.size(); vi++) {
            //Draw lane number
            cout << "|" << right << setw(2) << vi+1 << "| ";
            
            mile_end = (vehicles[vi]->getMilesTraveled()) + vehicles[vi]->go(race_speed);
            miles_remaining = race_length - mile_end;
                        
            //Draw Progress and Finish Line
            //Step through each mile using mi = mile index
            if(mile_end < race_length) {
                for(int mi = 0; mi < mile_end; mi++) {
                    cout << ".";
                }
                cout << vehicles[vi]->getSymbol();
                //Draw Finish line using fi = finish line index
                for(int fi = 0; fi < miles_remaining; fi++) {
                    cout << " ";
                }
		        cout << "||\n";
            }
            //Draw Progress and Race Finished
            else {
                for(int mi = 0; mi < race_length + 1; mi++) {
                    cout << ".";
                }
                cout << "}}..... (" << vehicles[vi]->getSymbol() << ") " << vehicles[vi]->getName() << " Finishes!\n";
            }
            
            //Determine Winner(s)
            if(mile_end >= race_length) {
                victory = true;
                winners.push_back(vehicles[vi]);
            }
        }
        wait(race_speed);
        cout << endl << endl;
    }
    
    //Display Winner(s)
    if(winners.size() == 1) {
        cout << "The winner is: (" << winners[0]->getSymbol() << ") " << winners[0]->getName() << "   ---   ";
        winners[0]->honkHorn();
        cout << endl;
    }
    else {
        cout << "We have a " << winners.size() << "-way tie!\n";
        cout << "The Winners are:\n\n";
        for(int i = 0; i < winners.size(); i++) {
            cout << "(" << winners[i]->getSymbol() << ") " << winners[i]->getName() << "   ---   ";
            winners[i]->honkHorn();
            cout << endl;
        }
        cout << endl;
    }
    
    //Delete objects
    for(int i = 0; i < vehicles.size(); i++) {
        delete vehicles[i];
    }
    
    return 0;
}

void displayHelp() {
    cout << "\n\n/--------------------------------------------------------------\\\n";
    cout << "|   Program Directions : Help Window                           |\n";
    cout << "|--------------------------------------------------------------|\n";
    cout << "|   Program Name: Race                                         |\n";
    cout << "|                                                              |\n";
    cout << "|   Switches:                                                  |\n";
    cout << "|       -tm MILES                                              |\n";
    cout << "|           --use MILES (integer) to specify race length       |\n";
    cout << "|           --fast race mode                                   |\n";
    cout << "|       -s                                                     |\n";
    cout << "|           --slow race mode                                   |\n";
    cout << "|                                                              |\n";
    cout << "|   Example: Race 22 miles in fast mode                        |\n";
    cout << "|       Race -tm 22 -f                                         |\n";
    cout << "\\--------------------------------------------------------------/\n\n";
}

void wait(int duration) {
    time_t start = time(NULL);
    while((time(NULL) - start) < duration) {
    }
}
