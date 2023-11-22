## Entropy Report
```
 Program Description:
 This program calculates the entropy for 5 different temperatures.
 The program uses a table of known temperature to entropy values ranging from 150 to 500 degrees Celsius.
 The program produces a text file with a table of input temperatures and calculated entropy values.

 Inputs:  Temperature to Entropy chart
          5 Temperatures
 
 Outputs: Text file report of Temperatures to Entropy values

 Algorithm:
          BEGIN
               Create entropy object
               Read temperature to entropy chart file into object           
               IF unable to open file THEN
                  Display error message
                  Quit
               END IF

               Store 5 temperature values

               Open output file
               IF unable to open output file THEN
                  Display error message
                  Quit
               ELSE
                  Write header to output file
               END IF

               LOOP 5 times
                  calculate entropy for input temperature
                  IF temperature is out of range THEN
                     Display error message
                     Quit
                  ELSE
                     Write temperature and entropy values to output file
                  END IF
               END LOOP
               Close output file
               Display program completed message with output file name               
          END
```