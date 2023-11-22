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
#include <string>

using namespace std;

#ifndef CARD_H
#define CARD_H

class Card {
    public:
        Card();
        char get_value();
        bool set_value(char);
        string get_suit();
        bool set_suit(string);   
    private:
        char value;
        string suit;
};

#endif
