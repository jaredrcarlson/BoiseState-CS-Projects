/*
---------------------------------------------------------
Card
---------------------------------------------------------
-value: char
-suit: string
---------------------------------------------------------
+Card()
+get_value(): char
+set_value(char): bool
+get_suit(): string
+set_suit(string): bool
---------------------------------------------------------
*/
#include <iostream>

#include "card.h"

using namespace std;

Card::Card() {
    value = '0';
}
char Card::get_value(){
    return value;
}
bool Card::set_value(char new_value){
    if((new_value < '2') || (new_value > '9')) {
        cout << "\nInvalid value - Try Again!";
        return false;
    }
    value = new_value;
    return true;
}
string Card::get_suit(){
    return suit;
}
bool Card::set_suit(string new_suit){
    if((new_suit != "hearts") && (new_suit != "spades") && (new_suit != "diamonds") && (new_suit != "clubs")) {
        cout << "\nInvalid suit - Try Again!";
        return false;
    }
    suit = new_suit;
    return true;
}
