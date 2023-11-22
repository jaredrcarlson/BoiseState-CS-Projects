/*
 * Name: Jared Carlson
 * Course: cs117
 * Instructor: Blaine Berguson
 *
 * Program Description:
 * This program reads in a barcode and produces the numeric zip code that the barcode represents.
 * The barcode is 32 characters in length, and consist of 2 characters which are | (full length bar) and : (half length bar). 
 * The | (full length bar) and the : (half length bar) characters represent numeric values 1 and 0 respectively.
 * The barcode has two full length framing bars, 1 at the beginning and 1 at the end. 
 * The 30 middle characters are divided into 6 sections with 5 characters each.
 * The first five sections represent the numeric zip code, while the last section works as a check-sum.
 * If the total sum of all six sections is divisible by 10, then the barcode is valid.
 * Each section is converted to a numeric digit similar to the way binary numbers are converted to decimal.
 * The following formula is used to convert each section to a decimal number:
 *
 * (Character 1 x 7) + (Character 2 x 4) + (Character 3 x 2) + (Character 4 x 1) + (Character 5 x 0)
 *
 *
 * Inputs:  Barcode
 * 
 * Outputs: Numeric zip code
 *
 * Algorithm:
 *          BEGIN
 *               Read in barcode              
 *               IF length of barcode is not equal to 32 THEN
 *                  Display error message
 *                  Quit
 *               ELSE IF any character is not a | or a : THEN
 *                  Display error message
 *                  Quit
 *               END IF
 *
 *               Store characters 1 through 5
 *               Store characters 6 through 10
 *               Store characters 11 through 15
 *               Store characters 16 through 20
 *               Store characters 21 through 25
 *               Store characters 26 through 30
 *               
 *               Convert characters 1 through 5 to decimal number
 *               Convert characters 6 through 10 to decimal number
 *               Convert characters 11 through 15 to decimal number
 *               Convert characters 16 through 20 to decimal number
 *               Convert characters 21 through 25 to decimal number
 *               Convert characters 26 through 30 to decimal number
 *
 *               IF sum of all six decimal numbers is not evenly divisible by 10 THEN
 *                  Display error message
 *                  Quit
 *               ELSE
 *                  Display barcode
 *                  Display numeric zip code 
 *          END
 */

#include <iostream>
#include <string>

using namespace std;

void validate_characters(string str, string str1, string str2, int length, int& counter, int& valid);
string extract_substring(string str, int start_at, int length);
int convert_to_decimal(string str, string str1);

int main() {
    const int VALID_BARCODE_LENGTH = 32;
    const int ENCODED_DIGIT_LENGTH = 5;
    const int CHECKSUM_DIVISIBLE_BY = 10;
    const string VALID_BARCODE_CHARACTER_1 = "|";
    const string VALID_BARCODE_CHARACTER_2 = ":";
    
    string barcode;
    int counter = 0;
    int valid = 0;
    string barcode_section_1;
    string barcode_section_2;
    string barcode_section_3;
    string barcode_section_4;
    string barcode_section_5;
    string barcode_section_6;
    int zip_digit_1 = 0;
    int zip_digit_2 = 0;
    int zip_digit_3 = 0;
    int zip_digit_4 = 0;
    int zip_digit_5 = 0;
    int zip_digit_6 = 0;
    int zipcode = 0;
    int checksum_total = 0;
    
    cout << "[Valid barcode characters are | and :]\n";
    cout << "Please enter a valid 32-character barcode: ";
    cin >> barcode;
    if (barcode.length() != VALID_BARCODE_LENGTH) {
       cout << "The barcode entered does not contain 32 characters. -Goodbye-" << endl;
       return -1;
    }
       
    validate_characters(barcode, VALID_BARCODE_CHARACTER_1, VALID_BARCODE_CHARACTER_2, VALID_BARCODE_LENGTH, counter, valid);
    if (valid != 0) {
       cout << "Sorry, the barcode may only contain characters [ | and : ]. -Goodbye-" << endl;
       return -2;
    }
    
    counter = 1;
    barcode_section_1 = extract_substring(barcode, counter, ENCODED_DIGIT_LENGTH);
    counter += ENCODED_DIGIT_LENGTH;
    barcode_section_2 = extract_substring(barcode, counter, ENCODED_DIGIT_LENGTH);
    counter += ENCODED_DIGIT_LENGTH;
    barcode_section_3 = extract_substring(barcode, counter, ENCODED_DIGIT_LENGTH);
    counter += ENCODED_DIGIT_LENGTH;
    barcode_section_4 = extract_substring(barcode, counter, ENCODED_DIGIT_LENGTH);
    counter += ENCODED_DIGIT_LENGTH;
    barcode_section_5 = extract_substring(barcode, counter, ENCODED_DIGIT_LENGTH);
    counter += ENCODED_DIGIT_LENGTH;
    barcode_section_6 = extract_substring(barcode, counter, ENCODED_DIGIT_LENGTH);
    
    zip_digit_1 = convert_to_decimal(barcode_section_1, VALID_BARCODE_CHARACTER_1); 
    zip_digit_2 = convert_to_decimal(barcode_section_2, VALID_BARCODE_CHARACTER_1);
    zip_digit_3 = convert_to_decimal(barcode_section_3, VALID_BARCODE_CHARACTER_1);
    zip_digit_4 = convert_to_decimal(barcode_section_4, VALID_BARCODE_CHARACTER_1);
    zip_digit_5 = convert_to_decimal(barcode_section_5, VALID_BARCODE_CHARACTER_1);
    zip_digit_6 = convert_to_decimal(barcode_section_6, VALID_BARCODE_CHARACTER_1);
    
    checksum_total = zip_digit_1 + zip_digit_2 + zip_digit_3 + zip_digit_4 + zip_digit_5 + zip_digit_6;
    if ((checksum_total % CHECKSUM_DIVISIBLE_BY) != 0) {
       cout << "Checksum failed! -GoodBye-" << endl;
       return -3;
    }
    
    zipcode = (zip_digit_1 * 10000) + (zip_digit_2 * 1000) + (zip_digit_3 * 100) + (zip_digit_4 * 10) + (zip_digit_5);    
       
    cout << "Barcode entered --> " << barcode;
    cout << "\n       Zip code --> " << zipcode << endl;       
    
    return 0;
}

void validate_characters(string str, string str1, string str2, int length, int& counter, int& valid) {
    if (counter < length) {
       if ((str.substr(counter,1) == str1) || (str.substr(counter,1) == str2)) {
          counter++;
          validate_characters(str, str1, str2, length, counter, valid);
       }   
       else {
          valid = -1;
       }
    }
}

string extract_substring(string str, int start_at, int length) {
    string tmp = str.substr(start_at,length);
    return tmp;
}

int convert_to_decimal(string str, string str1) {
    int position = 0;
    int tmp = 0;
    if (str.substr(position,1) == str1) {
       tmp += 7;
       position++;
    }
    else {
       position++;
    }
    if (str.substr(position,1) == str1) {
       tmp += 4;
       position++;
    }
    else {
       position++;
    }
    if (str.substr(position,1) == str1) {
       tmp += 2;
       position++;
    }
    else {
       position++;
    }
    if (str.substr(position,1) == str1) {
       tmp += 1;
       position++;
    }
    else {
       position++;
    }
    //Conversion exception: 11 should be 0
    if (tmp == 11) {
       tmp = 0;
    }
    return tmp;
}
