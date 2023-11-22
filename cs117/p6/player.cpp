/*
---------------------------------------------------------
Player
---------------------------------------------------------
-cards[NUM_CARDS]: Card
---------------------------------------------------------
+Player()
+set_cards(Card c[], int size): void
+display_best_hand(): void
-sort_cards(): void
-swap_cards(Card&, Card&): void
-have_flush(): bool
-have_straight(): bool
-have_4_of_a_kind(): bool
-have_3_of_a_kind(): bool
-have_2_pair(): bool
-have_pair(): bool
-get_high_card(): Card
---------------------------------------------------------
*/

#include <iostream>
#include "player.h"

using namespace std;

Player::Player() {  
}
void Player::set_cards(Card new_c[], int size){
    for(int i = 0; i < size; i++) {
        cards[i] = new_c[i];
    }
}
void Player::display_best_hand(){
    cout << "\n\nCards entered are:";
    cout << "\n---------------------------\n";
    for(int i = 0; i < NUM_CARDS; i++) {
        cout << "Card " << i+1 << ": " << cards[i].get_value() << " of " << cards[i].get_suit() << endl;
    }
    cout << "---------------------------\n";
    sort_cards();
    cout << "\nCards sorted are:";
    cout << "\n---------------------------\n";
    for(int i = 0; i < NUM_CARDS; i++) {
        cout << "Card " << i+1 << ": " << cards[i].get_value() << " of " << cards[i].get_suit() << endl;
    }
    cout << "---------------------------\n";
    
    if(have_straight() && have_flush()) {
        cout << "\nYour best hand is a straight flush!\n";
    }
    else if(have_4_of_a_kind()) {
        cout << "\nYour best hand is four of a kind!\n";
    }
    else if(have_flush()) {
        cout << "\nYour best hand is a flush!\n";
    }
    else if(have_straight()) {
        cout << "\nYour best hand is a straight!\n";
    }
    else if(have_3_of_a_kind()) {
        cout << "\nYour best hand is three of a kind.\n";
    }
    else if(have_2_pair()) {
        cout << "\nYour best hand is two pair.\n";
    }
    else if(have_pair()) {
        cout << "\nYour best hand is a pair.\n";
    }
    else {
        cout << "\nYour high card is the " << cards[3].get_value() << " of " << cards[3].get_suit() << ".\n";
    }
}
void Player::sort_cards(){
    int unsorted_marker = NUM_CARDS - 1;
    while(unsorted_marker > 0) {
        for(int i = 0; i < unsorted_marker; i++) {
            if(cards[i].get_value() > cards[i + 1].get_value()) {
                swap_cards(cards[i], cards[i + 1]);
            }
        }
        unsorted_marker--;
    }
}
void Player::swap_cards(Card& card_a, Card& card_b){
    Card tmp = card_a;
    card_a = card_b;
    card_b = tmp;
}
bool Player::have_flush(){
    if((cards[0].get_suit() == cards[1].get_suit()) &&
       (cards[1].get_suit() == cards[2].get_suit()) &&
       (cards[2].get_suit() == cards[3].get_suit())) {
        return true;
    }
    return false;
}
bool Player::have_straight(){
    if((cards[0].get_value() == cards[1].get_value() - 1) &&
       (cards[1].get_value() == cards[2].get_value() - 1) &&
       (cards[2].get_value() == cards[3].get_value() - 1)) {
        return true;
    }
    return false;
}
bool Player::have_4_of_a_kind(){
    if((cards[0].get_value() == cards[1].get_value()) &&
       (cards[1].get_value() == cards[2].get_value()) &&
       (cards[2].get_value() == cards[3].get_value())) {
        return true;
    }
    return false;
    
}
bool Player::have_3_of_a_kind(){
    if((cards[0].get_value() == cards[1].get_value()) && (cards[1].get_value() == cards[2].get_value()) ||
       (cards[0].get_value() == cards[1].get_value()) && (cards[1].get_value() == cards[3].get_value()) ||
       (cards[0].get_value() == cards[2].get_value()) && (cards[2].get_value() == cards[3].get_value()) ||
       (cards[1].get_value() == cards[2].get_value()) && (cards[2].get_value() == cards[3].get_value())) {
        return true;
    }
    return false;    
}
bool Player::have_2_pair(){
    if((cards[0].get_value() == cards[1].get_value()) && (cards[2].get_value() == cards[3].get_value()) ||
       (cards[0].get_value() == cards[2].get_value()) && (cards[1].get_value() == cards[3].get_value()) ||
       (cards[0].get_value() == cards[4].get_value()) && (cards[1].get_value() == cards[2].get_value())) {
        return true;
    }
    return false;   
}
bool Player::have_pair(){
    if((cards[0].get_value() == cards[1].get_value()) ||
       (cards[0].get_value() == cards[2].get_value()) ||
       (cards[0].get_value() == cards[3].get_value()) ||
       (cards[1].get_value() == cards[2].get_value()) ||
       (cards[1].get_value() == cards[3].get_value()) ||
       (cards[2].get_value() == cards[3].get_value())) {
        return true;
    }
    return false;
}
Card Player::get_high_card(){
    return cards[3];
}
