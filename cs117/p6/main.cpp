/*
 * Name: Best Poker Hand Calculator
 * Course: cs117
 * Instructor: Blaine Berguson
 *
 * Program Description:
 * This program takes 4 poker playing cards as inputs. The program will determine and display the best poker hand that can be played with the cards.
 *
 * Inputs:  1st Playing Card number
 *          1st Playing Card suit
 *          2nd Playing Card number
 *          2nd Playing Card suit
 *          3rd Playing Card number
 *          3rd Playing Card suit
 *          4th Playing Card number
 *          4th Playing Card suit
 *
 * Outputs: Displays the best poker hand
 *
 * Algorithm:
 *          BEGIN
 *              LOOP until user exits
 *                  Display a brief program description
 *
 *                  LOOP 4 times
 *                      Get card
 *                  END LOOP
 *              
 *                  Display best poker hand
 *              END LOOP            
 *          END
 */

#include <cstdlib>
#include <iostream>

#include "card.h"
#include "player.h"

using namespace std;

Card get_card(int card_number);

int main() {
    const int HAND_SIZE  = 4; 
    char play_again = 'y';
    Card hand[HAND_SIZE];
    Player player_1;
        
    while(play_again == 'y') {
    
        cout << "-----------------------------------------------------------------------------------------\n";
	     cout << "This program will ask you to enter 4 playing cards.\n";
	     cout << "The program will display the best poker hand that can be played with the cards.\n";
	     cout << "Be sure to enter your cards as directed or you will have to rely only on your poker face.\n";
	     cout << "-----------------------------------------------------------------------------------------\n";
    
        for (int i = 1; i <= HAND_SIZE; i++) {
            hand[i - 1] = get_card(i);
        }
    
        player_1.set_cards(hand, HAND_SIZE);
        player_1.display_best_hand();
    
        cout << "\nWould you like to play again? [y or n]: ";
        cin >> play_again;
        while( cin.fail() || ((play_again != 'y') && (play_again != 'n')) ) {
            cin.clear();
            cin.ignore();
            cout << "Sorry, you must enter a lowercase 'y' or 'n'.\n";
            cout << "\nWould you like to play again? [y or n]: ";
            cin >> play_again;
        }
        
        
        
        
    }//End "Play Again?" while
    
    return 0;
}

Card get_card(int card_number) {
    bool valid_value = false;
    bool valid_suit = false;
    char card_value;
    string card_suit;
    Card new_card;
    
    while ((valid_value == false) || (valid_suit == false)) {
        cout << "\n[Valid card numbers: 2-9]" << endl;
	     cout << "Enter the face value of card number " << card_number << ": ";
	     cin >> card_value;
	     
	     cout << "\n[Valid suits: hearts, spades, diamonds, clubs (all lowercase letters)]" << endl;
	     cout << "Enter the suit of card number " << card_number << ": ";
	     cin >> card_suit;
	     
	     valid_value = new_card.set_value(card_value);
        valid_suit = new_card.set_suit(card_suit);	     
    }
    return new_card;
}
