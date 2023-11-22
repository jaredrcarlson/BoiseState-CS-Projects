#include <cstdlib>
#include <iostream>
#include <fstream>
#include <unistd.h>
#include <time.h>

using namespace std;

const int ARRAY_SIZE = 1000;
const int NUM_COLS = 2;

void swap_chars(char& ch1, char& ch2);

void swap_ints(int& int1, int& int2);

void reverse_order(char values[ARRAY_SIZE], int size);

void subtract_from_value(char values[ARRAY_SIZE], int size, int value);

void sort(int values[][NUM_COLS], int size);

void change_element_to_index(int values[][NUM_COLS], int size);

void animate_read(int seconds);

void animate_analyze(int seconds);

void animate_key_file(int seconds);

void animate_crack(int seconds);

int main() {
    const int NUM_ASCII_CHARS = 127;
    char input[ARRAY_SIZE] = {' '};
    int key[ARRAY_SIZE][NUM_COLS] = {' ',' '};
    
    //Clear Screen
    system("clear");
    
    //Create input file stream and open encrypted file
    ifstream fin("can_u_decipher_em_127-c.txt");
    if(fin.fail()) {
        cout << "Unable to open input file" << endl;
        return 1;
    }
    
    //Read input file and store in input array
    int index = 0;
    while(!fin.eof()) {
        input[index] = fin.get();
        //Do not increment index if last read was eof : index will be used for size
        if(!fin.eof()) {
            index++;
        }               
    }
    int input_size = index;
    
    animate_read(6);
       
    //reverse characters
    reverse_order(input, input_size);
    
    //subtract each character by total number of ascii characters
    subtract_from_value(input, input_size, NUM_ASCII_CHARS);
    
    animate_analyze(6);
    
        
    //close and clear file input stream in order to open new file
    fin.close();
    fin.clear();
    
    //open key file
    fin.open("key.txt");
    if(fin.fail()) {
        cout << "Unable to open key file" << endl;
        return 2;
    }
    
    int row = 0;
    while(!fin.eof()) {
        for(int col = 0; col < NUM_COLS; col++) {
            fin >> key[row][col];      
        }
        //Do not increment row if last read was eof - row will be used for key_ size
		if(!fin.eof()) {
            row++;
        }
    }  
    int key_size = row;
    
    animate_crack(10);
    
    //sort key file
    sort(key, key_size);
    
    //convert key element numbers to index numbers
    change_element_to_index(key, key_size);
    
    animate_key_file(20);
    
    //display secret message
	system("clear");
    cout << "\n\n---------------------------------------------------------------\n";
    for(int i = 0; i < key_size; i++) {
        //convert element to index
        index = key[i][1] - 1;
        cout << input[index];
    }
    cout << "\n---------------------------------------------------------------\n\n";
    return 0;
}

void swap_chars(char& ch1, char& ch2) {
    char tmp = ch1;
    ch1 = ch2;
    ch2 = tmp;
}

void swap_ints(int& int1, int& int2) {
    int tmp = int1;
    int1 = int2;
    int2 = tmp;
}

void reverse_order(char values[ARRAY_SIZE], int size) {
    //This method will work for both odd and even sized arrays
    for(int i = 0; i < size / 2; i++) {
        swap_chars(values[i], values[size - i - 1]);
    }
}

void subtract_from_value(char values[ARRAY_SIZE], int size, int value) {
    for(int i = 0; i < size; i++) {
        values[i] = value - values[i];
    }
}

void sort(int values[][NUM_COLS], int size) {
    int unsorted_bound = size - 1;
    while(unsorted_bound > 1) {
        for(int i = 0; i < unsorted_bound; i++) {
            if(values[i][0] > values[i + 1][0]) {
                swap_ints(values[i][0], values[i + 1][0]);
                swap_ints(values[i][1], values[i + 1][1]);
            }
        }
        unsorted_bound--;
    } 
}

void change_element_to_index(int values[][NUM_COLS], int size) {
    for(int i = 0; i < size; i++) {
        values[i][0]--;
    }
}

void animate_read(int seconds) {
    cout << "\nReading encrypted file";
    cout << '.' << flush;
    time_t start = time(NULL);
    while((time(NULL) - start) < seconds) {
        sleep(1);
        cout << "....." << flush;
        sleep(1);
        cout << ".........." << flush;
    }   
}

void animate_analyze(int seconds) {
    cout << "\n\nAnalyzing file structure --> ";
    time_t start = time(NULL);
    while((time(NULL) - start) < seconds) {
        cout << "-" << "\b" << flush;
        sleep(1);
        cout << "\\" << "\b" << flush;
        sleep(1);
        cout << "/" << "\b" << flush;
        sleep(1);
    }  
}

void animate_key_file(int seconds) {
    cout << "\n\nSearching for Key File ---> " << flush;
    time_t start = time(NULL);
    while((time(NULL) - start) < seconds) {
        sleep(1);
        cout << "---> " << flush;
        sleep(1);
        cout << "---> " << flush;
        sleep(1);
        cout << "---> " << flush;
        sleep(1);
        cout << "---> " << flush;
        sleep(1);
        cout << "---> " << flush;
    }
    sleep(3);
    cout << "\n\nKey file found!";
    sleep(2);
    cout << "\n\nProcessing Key File --> ";
    start = time(NULL);
    while((time(NULL) - start) < seconds) {
        cout << "-" << "\b" << flush;
        sleep(1);
        cout << "\\" << "\b" << flush;
        sleep(1);
        cout << "/" << "\b" << flush;
        sleep(1);
    }  
    sleep(5);
    cout << "\n\nDetected matching algorithm in Key File!!!";
    sleep(2);
    cout << "\n\nBeginning Key File based Decryption algorithm...";
    sleep(1);
    cout << "....--->" << flush;
    sleep(1); 
    cout << " - - - - - - - > > >" << flush;
    sleep(1);
    cout << " --------->>" << flush;
    sleep(2);
    cout << "\n\nAlgorithm completed successfully!";
    sleep(6);
    cout << "\n\nStructuring decrypted characters...";
    sleep(1);
    cout << "......" << flush;
    sleep(1);
    cout << "............" << flush;
    sleep(1);
    cout << "........................" << flush;
    sleep(1);
    cout << "\n\nFinalizing Decryption parameters...." << flush;
    sleep(1);
    cout << "...." << flush;
    sleep(1);
    cout << "........" << flush;
    sleep(1);
    cout << "............" << flush;
    sleep(3);
    cout << "\n\nDecryption Complete in: 10 " << flush;
    sleep(1);
    cout << "9 " << flush;
    sleep(1);
    cout << "8 " << flush;
    sleep(1);
    cout << "7 " << flush;
    sleep(1);
    cout << "6 " << flush;
    sleep(1);
    cout << "5 " << flush;
    sleep(1);
    cout << "4 " << flush;
    sleep(1);
    cout << "3 " << flush;
    sleep(1);
    cout << "2 " << flush;
    sleep(1);
    cout << "1 " << flush;
    sleep(4);
    cout << "\n\n\nThe secret message is." << flush;
    sleep(1);
    cout << ".." << flush;
    sleep(1);
    cout << "..." << flush;
    sleep(1);
    cout << "...." << flush;
    sleep(5);
}

void animate_crack(int seconds) {
    cout << "\n\nAttempting Reverse Attack    ---> ";
    time_t start = time(NULL);
    while((time(NULL) - start) < seconds) {
        sleep(1);
        cout << "123456789 -- " << flush;
        sleep(1);
        cout << "987654321 -- " << flush;
    }
    cout << "\n\n          ....Reverse Attack method FAILED!";
    
    cout << "\n\nAttempting Dictionary Attack    ---> ";
    sleep(3);
    start = time(NULL);
    while((time(NULL) - start) < seconds) {
        sleep(1);
        cout << "A-B-C-D-E-F-G" << flush;
        sleep(1);
        cout << "-H-I-J-K-L-M-N" << flush;
        sleep(1);
        cout << "-O-P-Q-R-S-T-U" << flush;
        sleep(1);
        cout << "-V-W-X-Y-Z --- " << flush;
    }
    cout << "\n\n          ....Dictionary Attack method FAILED!";
    
    cout << "\n\nAttempting Brute Force Attack    ---> ";
    sleep(3);
    start = time(NULL);
    while((time(NULL) - start) < seconds) {
        sleep(1);
        cout << "-+-+-+-+8888888888888888888+-+-+-+-+" << flush;
        sleep(1);
        cout << "================28942800138--Lkyzingls232___________..$$$$$()llll-ajdia;sllll...aisdofj" << flush;
        sleep(1);
        cout << ">>>>>>>>>>R9324<<<<<<<<<<<888nzzzz..************8888888888+-+-+-+-+--+-+-+-+88888" << flush;
        sleep(1);
        cout << "ls232___________..$$$$$()llll-ajdia;sllll...aisdofj+++++++++++++++++...1245068dvk" << flush;
    }
    cout << "\n\n          ....Brute Force Attack method FAILED!";
    
    cout << "\n\nAttempting Rainbow Tables Attack    ---> ";
    sleep(3);
    start = time(NULL);
    while((time(NULL) - start) < seconds) {
        sleep(1);
        cout << "|R|_|A|_|I|_|N|_|B|_|O|_|W|_|-|_|-|_" << flush;
    }
    cout << "\n\n          ....Rainbow Tables Attack method FAILED!";
    sleep(3);
}
