## Animated Graph
```
 Program Description:
 This program displays an animated graph.
 The user can graph a line by choosing the grid size, x and y coordinates, and the slope.
 The user can also choose to view a demo

 Inputs:  grid size
            x coordinate
          y coordinate
          slope
          

 Outputs: Displays animated graph of line defined by inputs
			Displays animated graph demo

 Algorithm:
     BEGIN
         LOOP until option 3 is selected
             Ask for grid size
             Display program options
             Validate option entered
             IF option 1 is selected THEN
                 Ask for x coordinate
                 Ask for y coordinate
                 Ask for slope
                 LOOP 15 times
                     Graph the line
                     Increment x by 2
                 END LOOP
             ELSE IF option 2 is selected THEN
                 Set x coordinate to zero
                 Set y coordinate to zero
                 Set slope to random number [-5,0)U(0,5]
                 LOOP 15 times
                     Graph the line
                     Decrement slope by 1
                 END LOOP
             ELSE
                 Quit
             END IF
		   END LOOP
	   END
```