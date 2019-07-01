# minesweeper
The goal is to create a minesweeper game designed for PC. The main purpose of working on this project is for fun and to practice java skills so that they don't go rusty.  
## steps
### 1: game design
- create grid for mines
- create bomb generating method
- display to console
- read input using formatted text input
- add different grid size options, with custom option limited to max and min value
- ...
### 2: gui design
- using Javafx, create gui for game
- ...
  
  
## board design
The board will be a 2d array of Tile objects. The console board should resemble:  
 _ _ _ _  
|#|#|#|#| 0  
|#|#|#|#| 1  
|#|#|#|#| 2  
|#|#|#|#| 3  
 0 1 2 3  
 > 3 3  
   
 _ _ _ _  
|#|1|_|_| 0  
|1|1|_|_| 1  
|_|_|_|_| 2  
|_|_|_|_| 3  
 0 1 2 3  
 > 0 0 F  
   
 _ _ _ _  
|F|1|_|_| 0  
|1|1|_|_| 1  
|_|_|_|_| 2  
|_|_|_|_| 3  
 0 1 2 3  
 >Game Won!  
  
### tile class
The tile class will store these fields:
- bool: bomb
- bool: clicked
- bool: flag
- int: adj_mines (meaning adjacent mines)
- int: row_pos, col_pos
  
The tile class will have these methods:
- constructor with position arguments
- methods to set the bomb and adj_mines fields
- click: clicked = true;
- flag: flag = !flag
  
tile class will be a simple state design. bomb bool deteremined by board initialization as well as adj_mines. Initially, clicked and flag is false. If the tile is clicked, show the adj_mines int if bomb == false, or else show a bomb and end game. Once clicked, tile can no longer be flagged. If tile not clicked and is flagged, it cannot be clicked until it is unflagged.

### board class
The board class will store these fields:
- final int: EASY_SIZE, MED_SIZE, HARD_SIZE
- int: custom_size
- bool: game_ended
- int: bomb_count
  
The board class will have these methods:
- constructor with int arg for size of board
- set_bombs: places bombs randomly within the board
- display: (for part 1 only) prints the board to console
- click: takes row and col as arguments from main class. Checks to see if that tile is flagged, then if it has already been clicked, then if it is bomb. If none of these, are true, show the number and mark clicked as true on the tile.
- flag: takes row and col as arugments. Checks to see if that tile is already clicked. If not, call Tile's flag method.
- show: takes row and col as arguments. If tile.can_show() == true, then show adjacent tiles
- can_show: takes row and col as arguments. helper method that checks if number of exposed mines adjacent to the tile == adj_mines
  
board class displays the board upon initialization. After every move, checks if gamed_ended, if not display again.
  
### main class
The main class will create an instance of the board. It will run a loop that takes input for a move then displays the board. The board will be displayed with numbers along the side and bottom (like in chess). The input will be formatted such as:  
- "0 1" for click on row 0 col 1
- "0 1 f" for flag on 0 1
- "0 1 show" for show surrounding tiles to 0 1
  
  
## next steps
Just thought about what happens on the first click in minesweeper. If there is a big bubble spot around the first click, all the tiles with no bombs adjacent are revealed as well as the numbered tiles on the edges of this "bubble". Will have to consider how to code this after initial setup is completed
