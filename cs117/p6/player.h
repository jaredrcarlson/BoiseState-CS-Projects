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

#include "card.h"

#ifndef PLAYER_H
#define PLAYER_H

class Player {
    public:
        Player();
        void set_cards(Card c[], int);
        void display_best_hand();
    private:
        static const int NUM_CARDS = 4;
        Card cards[NUM_CARDS];
        void sort_cards();
        void swap_cards(Card&, Card&);
        bool have_flush();
        bool have_straight();
        bool have_4_of_a_kind();
        bool have_3_of_a_kind();
        bool have_2_pair();
        bool have_pair();
        Card get_high_card();
};

#endif
