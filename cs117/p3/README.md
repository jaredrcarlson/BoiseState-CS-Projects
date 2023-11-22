## Barcode Converter
```
 Program Description:
 This program reads in a barcode and produces the numeric zip code that the barcode represents.
 The barcode is 32 characters in length, and consist of 2 characters which are | (full length bar) and : (half length bar). 
 The | (full length bar) and the : (half length bar) characters represent numeric values 1 and 0 respectively.
 The barcode has two full length framing bars, 1 at the beginning and 1 at the end. 
 The 30 middle characters are divided into 6 sections with 5 characters each.
 The first five sections represent the numeric zip code, while the last section works as a check-sum.
 If the total sum of all six sections is divisible by 10, then the barcode is valid.
 Each section is converted to a numeric digit similar to the way binary numbers are converted to decimal.
 The following formula is used to convert each section to a decimal number:

 (Character 1 x 7) + (Character 2 x 4) + (Character 3 x 2) + (Character 4 x 1) + (Character 5 x 0)


 Inputs:  Barcode
 
 Outputs: Numeric zip code

 Algorithm:
          BEGIN
               Read in barcode              
               IF length of barcode is not equal to 32 THEN
                  Display error message
                  Quit
               ELSE IF any character is not a | or a : THEN
                  Display error message
                  Quit
               END IF

               Store characters 1 through 5
               Store characters 6 through 10
               Store characters 11 through 15
               Store characters 16 through 20
               Store characters 21 through 25
               Store characters 26 through 30
               
               Convert characters 1 through 5 to decimal number
               Convert characters 6 through 10 to decimal number
               Convert characters 11 through 15 to decimal number
               Convert characters 16 through 20 to decimal number
               Convert characters 21 through 25 to decimal number
               Convert characters 26 through 30 to decimal number

               IF sum of all six decimal numbers is not evenly divisible by 10 THEN
                  Display error message
                  Quit
               ELSE
                  Display barcode
                  Display numeric zip code 
          END
```