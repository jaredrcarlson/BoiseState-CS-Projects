## Max Deflection
 Program Description:
 This program computes and displays the maximum deflection at the end of a cantilevered beam.
 The program prompts the user for a Length, Height, and Base in feet, and the object's Weight in pounds,
  as double-precision arguments, and then calculates the deflection in inches for Aluminum, Wood, 
  and Steel based upon the same length, height, and base received from the user and predetermined Elasticity.
  The program then displays the values on the screen in the form of a table with column headers and aligned column values.

 Inputs:  Length (ft)
          Height (ft)
          Base (ft)
          Weight (lbs)

 Outputs: Max Deflection for Aluminum (in)
          Max Deflection for Wood (in)
          Max Deflection for Steel (in)

 Algorithm:
          BEGIN
              Display a brief program description
              Prompt user for Length
              Prompt user for Height
              Prompt user for Base
              Prompt user for Weight
              Calculate the Length to the 3rd power
              Calculate the Height to the 3rd power
              Calculate the Max Deflection for Aluminum
              Calculate the Max Deflection for Wood
              Calculate the Max Deflection for Steel
              Display all values on the screen in the form of a table
          END