/*
 * Name: Best Poker Hand Calculator
 * Course: cs117
 * Instructor: Blaine Bergeson
 *
 * Program Description:
 * This program takes 4 poker playing cards as inputs. The program will determine and display the best poker hand that can be played with the cards.
 *
 * Inputs:  1st Playing Card rank
 *          1st Playing Card suit
 *          2nd Playing Card rank
 *          2nd Playing Card suit
 *          3rd Playing Card rank
 *          3rd Playing Card suit
 *          4th Playing Card rank
 *          4th Playing Card suit
 *
 * Outputs: Displays the cards entered
 *			Displays the cards sorted low to high
 *			Displays the best poker hand
 *
 * Algorithm:
 *          BEGIN
 *              Display a brief program description
 *
 *              Prompt user for the rank of card 1
 *				Error Check the input
 *				Set sortable rank
 *              Prompt user for the suit of card 1
 *              Error Check the input
 *              Prompt user for the rank of card 2
 *              Error Check the input
 *				Set sortable rank
 *              Prompt user for the suit of card 2
 *              Error Check the input
 *              Prompt user for the rank of card 3
 *              Error Check the input
 *				Set sortable rank
 *              Prompt user for the suit of card 3
 *              Error Check the input
 *              Prompt user for the rank of card 4
 *              Error Check the input
 *				Set sortable rank
 *              Prompt user for the suit of card 4
 *              Error Check the input
 *              
 *              Sort cards low to high
 *
 *				IF (card 1 rank equals card 2 rank) AND (card 2 rank equals card 3 rank) AND (card 3 rank equals card 4 rank) THEN
 *        			Output best hand is 4 of a kind
 *                 	Quit
 *              ELSE IF (card 1 suit equals card 2 suit) AND (card 2 suit equals card 3 suit) AND (card 3 suit equals card 4 suit) THEN
 *                 	Output best hand is a flush
 *                 	Quit
 *				ELSE IF (card 1 sortable plus 1 equals card 2 sortable) AND
 *						(card 2 sortable plus 1 equals card 3 sortable) AND
 *						(card 3 sortable plus 1 equals card 4 sortable) THEN
 *				   	Output best hand is a straight
 *					Quit
 *				ELSE IF (card 1 rank equals card 2 rank) AND (card 2 rank equals card 3 rank) OR
 *                      (card 1 rank equals card 2 rank) AND (card 2 rank equals card 4 rank) OR
 *                      (card 1 rank equals card 3 rank) AND (card 3 rank equals card 4 rank) OR
 *                      (card 2 rank equals card 3 rank) AND (card 3 rank equals card 4 rank) THEN
 *                 	Output best hand is 3 of a kind
 *                 	Quit
 *              ELSE IF (card 1 rank equals card 2 rank) AND (card 3 rank equals card 4 rank) OR
 *                      (card 1 rank equals card 3 rank) AND (card 2 rank equals card 4 rank) OR
 *                      (card 1 rank equals card 4 rank) AND (card 2 rank equals card 3 rank) THEN
 *                 	Output best hand is 2 Pair
 *                 	Quit
 *              ELSE IF (card 1 rank equals card 2 rank) OR
 *                      (card 1 rank equals card 3 rank) OR
 *                      (card 1 rank equals card 4 rank) OR
 *                      (card 2 rank equals card 3 rank) OR
 *                      (card 2 rank equals card 4 rank) OR
 *                      (card 3 rank equals card 4 rank) THEN
 *                 	Output best hand is a Pair
 *                 	Quit
 *              ELSE
 *					Display high card 
 *                 	Quit
 *			END
 */
 
#include <iostream> 

using namespace std;

int main() {
	char card_1_rank = '0';
	char card_1_sortable = '0';
	char card_2_rank = '0';
	char card_2_sortable = '0';
	char card_3_rank = '0';
	char card_3_sortable = '0';
	char card_4_rank = '0';
	char card_4_sortable = '0';
	char temp_rank_swap = '0';
    char temp_sortable_swap = '0';
	string card_1_suit = "";
	string card_2_suit = "";
	string card_3_suit = "";
	string card_4_suit = "";
	string temp_suit_swap = "";
   
	cout << "-----------------------------------------------------------------------------------------\n";
	cout << "This program will ask you to enter 4 playing cards.\n";
	cout << "The program will display the best poker hand that can be played with the cards.\n";
	cout << "Be sure to enter your cards as directed or you will have to rely only on your poker face.\n";
	cout << "-----------------------------------------------------------------------------------------\n";
   
	//Get Input values
	//Card (1)
	cout << "\n[Valid card ranks: 2-9,j,q,k,a]\n";
	cout << "Enter the rank of your 1st card: ";
	cin >> card_1_rank;
	
	//Create contiguous and continuous (sortable) variables based on ascii table
	if (card_1_rank == 'j') {
		card_1_sortable = ':';
	}
	else if (card_1_rank == 'q') {
		card_1_sortable = ';';
	}
	else if (card_1_rank == 'k') {
		card_1_sortable = '<';
	}
	else if (card_1_rank == 'a') {
		card_1_sortable = '=';
	}
	else {
		card_1_sortable = card_1_rank;
	}
   
	//Check for errors
	if (card_1_sortable < '2' || card_1_sortable > '=') {
		cout << "You did not enter a valid card rank. Good-Bye!" << endl;
		return 1; // Quit with exit status 1 (incorrect rank value)
	}
	cout << "\n[Valid suits: hearts, spades, diamonds, clubs (all lowercase letters)]\n";
	cout << "Type a valid suit for your 1st card: ";
	cin >> card_1_suit;
   
	//Check for errors
	if (card_1_suit != "hearts" && card_1_suit != "spades" && card_1_suit != "diamonds" && card_1_suit != "clubs") { 
		cout << "You did not enter a valid suit. Good-Bye!" << endl;
		return 2; // Quit with exit status 2 (incorrect suit value)
	}
	  
	//Card (2)
	cout << "\n[Valid card ranks: 2-9,j,q,k,a]\n";
	cout << "Enter the rank of your 2nd card: ";
	cin >> card_2_rank;
	
	//Create contiguous and continuous (sortable) variables based on ascii table
	if (card_2_rank == 'j') {
		card_2_sortable = ':';
	}
	else if (card_2_rank == 'q') {
		card_2_sortable = ';';
	}
	else if (card_2_rank == 'k') {
		card_2_sortable = '<';
	}
	else if (card_2_rank == 'a') {
		card_2_sortable = '=';
	}
	else {
		card_2_sortable = card_2_rank;
	}
   
	//Check for errors
	if (card_2_sortable < '2' || card_2_sortable > '=') {
		cout << "You did not enter a valid card rank. Good-Bye!" << endl;
		return 1; // Quit with exit status 1 (incorrect rank value)
	}
	cout << "\n[Valid suits: hearts, spades, diamonds, clubs (all lowercase letters)]\n";
	cout << "Type a valid suit for your 2nd card: ";
	cin >> card_2_suit;
   
	//Check for errors
	if (card_2_suit != "hearts" && card_2_suit != "spades" && card_2_suit != "diamonds" && card_2_suit != "clubs") { 
		cout << "You did not enter a valid suit. Good-Bye!" << endl;
		return 2; // Quit with exit status 2 (incorrect suit value)
	}
	
	//Card (3)
	cout << "\n[Valid card ranks: 2-9,j,q,k,a]\n";
	cout << "Enter the rank of your 3rd card: ";
	cin >> card_3_rank;
	
	//Create contiguous and continuous (sortable) variables based on ascii table
	if (card_3_rank == 'j') {
		card_3_sortable = ':';
	}
	else if (card_3_rank == 'q') {
		card_3_sortable = ';';
	}
	else if (card_3_rank == 'k') {
		card_3_sortable = '<';
	}
	else if (card_3_rank == 'a') {
		card_3_sortable = '=';
	}
	else {
		card_3_sortable = card_3_rank;
	}
   
	//Check for errors
	if (card_3_sortable < '2' || card_3_sortable > '=') {
		cout << "You did not enter a valid card rank. Good-Bye!" << endl;
		return 1; // Quit with exit status 1 (incorrect rank value)
	}
	cout << "\n[Valid suits: hearts, spades, diamonds, clubs (all lowercase letters)]\n";
	cout << "Type a valid suit for your 3rd card: ";
	cin >> card_3_suit;
   
	//Check for errors
	if (card_3_suit != "hearts" && card_3_suit != "spades" && card_3_suit != "diamonds" && card_3_suit != "clubs") { 
		cout << "You did not enter a valid suit. Good-Bye!" << endl;
		return 2; // Quit with exit status 2 (incorrect suit value)
	}
	
	//Card (4)
	cout << "\n[Valid card ranks: 2-9,j,q,k,a]\n";
	cout << "Enter the rank of your 4th card: ";
	cin >> card_4_rank;
	
	//Create contiguous and continuous (sortable) variables based on ascii table
	if (card_4_rank == 'j') {
		card_4_sortable = ':';
	}
	else if (card_4_rank == 'q') {
		card_4_sortable = ';';
	}
	else if (card_4_rank == 'k') {
		card_4_sortable = '<';
	}
	else if (card_4_rank == 'a') {
		card_4_sortable = '=';
	}
	else {
		card_4_sortable = card_4_rank;
	}
   
	//Check for errors
	if (card_4_sortable < '2' || card_4_sortable > '=') {
		cout << "You did not enter a valid card rank. Good-Bye!" << endl;
		return 1; // Quit with exit status 1 (incorrect rank value)
	}
	cout << "\n[Valid suits: hearts, spades, diamonds, clubs (all lowercase letters)]\n";
	cout << "Type a valid suit for your 4th card: ";
	cin >> card_4_suit;
   
	//Check for errors
	if (card_4_suit != "hearts" && card_4_suit != "spades" && card_4_suit != "diamonds" && card_4_suit != "clubs") { 
		cout << "You did not enter a valid suit. Good-Bye!" << endl;
		return 2; // Quit with exit status 2 (incorrect suit value)
	}
	
    cout << "\nHere are the cards you've entered:";  
	cout << "\n------------------------------------";
	cout << "\nCard 1: " << card_1_rank << " of " << card_1_suit;
	cout << "\nCard 2: " << card_2_rank << " of " << card_2_suit;
	cout << "\nCard 3: " << card_3_rank << " of " << card_3_suit;
	cout << "\nCard 4: " << card_4_rank << " of " << card_4_suit;
	cout << "\n------------------------------------\n";
	
	
    //Sort the sortable values
	if (card_1_sortable > card_2_sortable) {
		temp_sortable_swap = card_1_sortable;
		temp_rank_swap = card_1_rank;
		temp_suit_swap = card_1_suit;
		card_1_sortable = card_2_sortable;
		card_1_rank = card_2_rank;
		card_1_suit = card_2_suit;
		card_2_sortable = temp_sortable_swap;
		card_2_rank = temp_rank_swap;
		card_2_suit = temp_suit_swap;
	}
	if (card_2_sortable > card_3_sortable) {
		temp_sortable_swap = card_2_sortable;
		temp_rank_swap = card_2_rank;
		temp_suit_swap = card_2_suit;
		card_2_sortable = card_3_sortable;
		card_2_rank = card_3_rank;
		card_2_suit = card_3_suit;
		card_3_sortable = temp_sortable_swap;
		card_3_rank = temp_rank_swap;
		card_3_suit = temp_suit_swap;
	}
	if (card_3_sortable > card_4_sortable) {
		temp_sortable_swap = card_3_sortable;
		temp_rank_swap = card_3_rank;
		temp_suit_swap = card_3_suit;
		card_3_sortable = card_4_sortable;
		card_3_rank = card_4_rank;
		card_3_suit = card_4_suit;        
        card_4_sortable = temp_sortable_swap;
        card_4_rank = temp_rank_swap;
		card_4_suit = temp_suit_swap;
	} //card 4 is now highest card
	
	if (card_1_sortable > card_2_sortable) {
		temp_sortable_swap = card_1_sortable;
		temp_rank_swap = card_1_rank;
		temp_suit_swap = card_1_suit;
		card_1_sortable = card_2_sortable;
		card_1_rank = card_2_rank;
		card_1_suit = card_2_suit;
		card_2_sortable = temp_sortable_swap;
		card_2_rank = temp_rank_swap;
		card_2_suit = temp_suit_swap;
	}
	if (card_2_sortable > card_3_sortable) {
		temp_sortable_swap = card_2_sortable;
		temp_rank_swap = card_2_rank;
		temp_suit_swap = card_2_suit;
		card_2_sortable = card_3_sortable;
		card_2_rank = card_3_rank;
		card_2_suit = card_3_suit;
		card_3_sortable = temp_sortable_swap;
		card_3_rank = temp_rank_swap;
		card_3_suit = temp_suit_swap;
	} //card 3 is now 2nd highest card
	
	if (card_1_sortable > card_2_sortable) {
		temp_sortable_swap = card_1_sortable;
		temp_rank_swap = card_1_rank;
		temp_suit_swap = card_1_suit;
		card_1_sortable = card_2_sortable;
		card_1_rank = card_2_rank;
		card_1_suit = card_2_suit;
		card_2_sortable = temp_sortable_swap;
		card_2_rank = temp_rank_swap;
		card_2_suit = temp_suit_swap;
	} //cards are sorted
	
	cout << "\nHere's your hand sorted low to high:";   
	cout << "\n------------------------------------";
	cout << "\nCard 1: " << card_1_rank << " of " << card_1_suit;
	cout << "\nCard 2: " << card_2_rank << " of " << card_2_suit;
	cout << "\nCard 3: " << card_3_rank << " of " << card_3_suit;
	cout << "\nCard 4: " << card_4_rank << " of " << card_4_suit;
	cout << "\n------------------------------------\n\n";
	
	
	
		 
	//Determine Best Poker Hand and Output result to the screen
	//Check for a 4 of a kind
	if ((card_1_rank == card_2_rank) && (card_2_rank == card_3_rank) && (card_3_rank == card_4_rank)) {
		cout << "Your best hand is a 4 of a kind!\n";
	}
   
	//Check for a flush
	else if ((card_1_suit == card_2_suit) && (card_2_suit == card_3_suit) && (card_3_suit == card_4_suit)) {
		cout << "Your best hand is a flush!\n";
	}
   
	//Check for straight
	else if ( ((card_1_sortable + 1) == card_2_sortable) && ((card_2_sortable + 1) == card_3_sortable) && ((card_3_sortable + 1 == card_4_sortable)) ) {
        cout << "Your best hand is a straight!\n";
    }
	
    //Check for a 3 of a kind
	else if ((card_1_rank == card_2_rank) && (card_2_rank == card_3_rank) ||
			(card_1_rank == card_2_rank) && (card_2_rank == card_4_rank) ||
			(card_1_rank == card_3_rank) && (card_3_rank == card_4_rank) ||
			(card_2_rank == card_3_rank) && (card_3_rank == card_4_rank)) {
		cout << "Your best hand is a 3 of a kind.\n";
	}    
	
	//Check for 2 Pair
	else if ((card_1_rank == card_2_rank) && (card_3_rank == card_4_rank) ||
			(card_1_rank == card_3_rank) && (card_2_rank == card_4_rank) ||
			(card_1_rank == card_4_rank) && (card_2_rank == card_3_rank)) { 
		cout << "Your best hand is 2 Pair.\n";                 
	}
	
	//Check for a Pair
	else if ((card_1_rank == card_2_rank) ||
			(card_1_rank == card_3_rank) ||
			(card_1_rank == card_4_rank) ||
			(card_2_rank == card_3_rank) ||
			(card_2_rank == card_4_rank) ||
			(card_3_rank == card_4_rank)) {
		cout << "Your best hand is a Pair.\n";            
	}
	
	//Display High Card (cards are already sorted)
	else
        cout << "Your high card is the " << card_4_rank << " of " << card_4_suit << ".\n";                 
		
	return 0;
}

