## Best Poker Hand
```
 Program Description:
 This program takes 4 poker playing cards as inputs. The program will determine and display the best poker hand that can be played with the cards.
 
 
 Inputs:  1st Playing Card rank
          1st Playing Card suit
          2nd Playing Card rank
          2nd Playing Card suit
          3rd Playing Card rank
          3rd Playing Card suit
          4th Playing Card rank
          4th Playing Card suit

 Outputs: Displays the cards entered
			Displays the cards sorted low to high
			Displays the best poker hand

 Algorithm:
          BEGIN
              Display a brief program description

              Prompt user for the rank of card 1
				Error Check the input
				Set sortable rank
              Prompt user for the suit of card 1
              Error Check the input
              Prompt user for the rank of card 2
              Error Check the input
				Set sortable rank
              Prompt user for the suit of card 2
              Error Check the input
              Prompt user for the rank of card 3
              Error Check the input
				Set sortable rank
              Prompt user for the suit of card 3
              Error Check the input
              Prompt user for the rank of card 4
              Error Check the input
				Set sortable rank
              Prompt user for the suit of card 4
              Error Check the input
              
              Sort cards low to high

				IF (card 1 rank equals card 2 rank) AND (card 2 rank equals card 3 rank) AND (card 3 rank equals card 4 rank) THEN
        			Output best hand is 4 of a kind
                 	Quit
              ELSE IF (card 1 suit equals card 2 suit) AND (card 2 suit equals card 3 suit) AND (card 3 suit equals card 4 suit) THEN
                 	Output best hand is a flush
                 	Quit
				ELSE IF (card 1 sortable plus 1 equals card 2 sortable) AND
						(card 2 sortable plus 1 equals card 3 sortable) AND
						(card 3 sortable plus 1 equals card 4 sortable) THEN
				   	Output best hand is a straight
					Quit
				ELSE IF (card 1 rank equals card 2 rank) AND (card 2 rank equals card 3 rank) OR
                      (card 1 rank equals card 2 rank) AND (card 2 rank equals card 4 rank) OR
                      (card 1 rank equals card 3 rank) AND (card 3 rank equals card 4 rank) OR
                      (card 2 rank equals card 3 rank) AND (card 3 rank equals card 4 rank) THEN
                 	Output best hand is 3 of a kind
                 	Quit
              ELSE IF (card 1 rank equals card 2 rank) AND (card 3 rank equals card 4 rank) OR
                      (card 1 rank equals card 3 rank) AND (card 2 rank equals card 4 rank) OR
                      (card 1 rank equals card 4 rank) AND (card 2 rank equals card 3 rank) THEN
                 	Output best hand is 2 Pair
                 	Quit
              ELSE IF (card 1 rank equals card 2 rank) OR
                      (card 1 rank equals card 3 rank) OR
                      (card 1 rank equals card 4 rank) OR
                      (card 2 rank equals card 3 rank) OR
                      (card 2 rank equals card 4 rank) OR
                      (card 3 rank equals card 4 rank) THEN
                 	Output best hand is a Pair
                 	Quit
              ELSE
					Display high card 
                 	Quit
			END
```