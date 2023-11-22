/*
 * Name: Jared Carlson
 * Course: cs117
 * Instructor: Blaine Berguson
 *
 * Program Description:
 * This program computes and displays the maximum deflection at the end of a cantilevered beam.
 * The program prompts the user for a Length, Height, and Base in feet, and the object's Weight in pounds,
 *  as double-precision arguments, and then calculates the deflection in inches for Aluminum, Wood, 
 *  and Steel based upon the same length, height, and base received from the user and predetermined Elasticity.
 *  The program then displays the values on the screen in the form of a table with column headers and aligned column values.
 *
 * Inputs:  Length (ft)
 *          Height (ft)
 *          Base (ft)
 *          Weight (lbs)
 *
 * Outputs: Max Deflection for Aluminum (in)
 *          Max Deflection for Wood (in)
 *          Max Deflection for Steel (in)
 *
 * Algorithm:
 *          BEGIN
 *              Display a brief program description
 *              Prompt user for Length
 *              Prompt user for Height
 *              Prompt user for Base
 *              Prompt user for Weight
 *              Calculate the Length to the 3rd power
 *              Calculate the Height to the 3rd power
 *              Calculate the Max Deflection for Aluminum
 *              Calculate the Max Deflection for Wood
 *              Calculate the Max Deflection for Steel
 *              Display all values on the screen in the form of a table
 *          END
 */
 
#include<iostream> // standard input/output stream
#include<cmath> // use of the pow() function
#include<iomanip> // table output formatting

using namespace std;

int main() {
    //Elasticity (lb/ft^2) for Aluminum, Wood, & Steel
    const double ALUMINUM = 1.49e9;
    const double WOOD = .187e9;
    const double STEEL = 3.9e9;
    
    //non-constant Variable Declarations
    double length = 0;
    double length_cubed = 0;
    double height = 0;
    double height_cubed = 0;
    double base = 0;
    double weight = 0;
    double max_deflection_aluminum = 0;
    double max_deflection_wood = 0;
    double max_deflection_steel = 0;
    
    //output formatting Variables
    int column_width = 20;
    char column_fill = ' ';
    
    //Brief Program Description
    cout << "Program Description:" << endl;
    cout << "This program computes and displays the maximum deflection at the end of a cantilevered beam." << endl;
    cout << "You will need to provide the Length, Height, Base and Weight of an object." << endl;
    cout << "The program will display the maximum deflection results for Aluminum, Wood, and Steel." << endl << endl;

    //Gather input values from user for Length, Height, Base, and Weight
    cout << "Please enter the object's Length in feet: ";
    cin >> length;
    cout << "Please enter the object's Height in feet: ";
    cin >> height;
    cout << "Please enter the object's Base in feet: ";
    cin >> base;
    cout << "Please enter the object's Weight in pounds: ";
    cin >> weight;
    
    //Calculate length and height cubes
    length_cubed = pow(length,3);
    height_cubed = pow(height,3);
    
    //Calculate max deflection for Aluminum
    max_deflection_aluminum = (4 * weight * length_cubed) / (ALUMINUM * base * height_cubed);
    
    //Calculate max deflection for Wood
    max_deflection_wood= (4 * weight * length_cubed) / (WOOD * base * height_cubed);
    
    //Calculate max deflection for Steel
    max_deflection_steel = (4 * weight * length_cubed) / (STEEL * base * height_cubed);
    
    
    
    //Display results in formatted table
    cout << endl << endl;
    cout << "--- Results Table ---" << endl << endl;
    cout << left << setw(column_width) << setfill(column_fill) << "Material" << setw(column_width) <<  "Elasticity(lb/ft^2)" << setw(column_width) << "Length(ft)" << setw(column_width) << "Height(ft)" << setw(column_width) << "Base(ft)" << setw(column_width) << "Weight(lb)" << setw(column_width) << "Max Deflection(in)" << endl;
    cout << left << setw(column_width) << setfill(column_fill) << "Aluminum" << setw(column_width) << ALUMINUM << setw(column_width) << length << setw(column_width) << height << setw(column_width) << base << setw(column_width) << weight << setw(column_width) << max_deflection_aluminum << endl;
    cout << left << setw(column_width) << setfill(column_fill) << "Wood" << setw(column_width) << WOOD << setw(column_width) << length << setw(column_width) << height << setw(column_width) << base << setw(column_width) << weight << setw(column_width) << max_deflection_wood << endl;
    cout << left << setw(column_width) << setfill(column_fill) << "Steel" << setw(column_width) << STEEL << setw(column_width) << length << setw(column_width) << height << setw(column_width) << base << setw(column_width) << weight << setw(column_width) << max_deflection_steel << endl;
    cout << endl << endl;
    return 0;
}

