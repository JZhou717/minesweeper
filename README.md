# minesweeper
The goal is to create a minesweeper game designed for PC. The main purpose of working on this project is for fun and to practice java skills so that they don't go rusty.  
  
Since everything in this repository is now fully functional. I'll be starting a new repository for a JavaFX project built on top of the code in this one.
  
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
- **This part will now be implemented in another project, as I will have to create a new project space with JavaFX**
  
  
## board design
The board will be a 2d array of Tile objects. The console board should resemble the example in this repo.  
  
### tile class
The tile class will store these fields:
- bool: mine
- bool: clicked
- bool: flag
- int: adj_mines
- int: row_pos, col_pos
  
The tile class will have these methods:
- constructor with position arguments
  
### board class
The board class will store these fields:
- final int: EASY_SIZE, MED_SIZE, HARD_SIZE
- int: custom_size
- bool: game_ended
- int: bomb_count
  
The board class will have these methods:
- constructor with int arg for size of board
- constructor with int arg for size of board and int arg for num of mines (for custom games)
- set_bombs: places bombs randomly within the board
- display: (for part 1 only) prints the board to console
- click: takes row and col as arguments from main class. Checks to see if that tile is flagged, then if it has already been clicked, then if it is bomb. If none of these, are true, show the number and mark clicked as true on the tile.
- flag: takes row and col as arugments. Checks to see if that tile is already clicked. If not, call Tile's flag method.
- show: takes row and col as arguments. If tile.can_show() == true, then show adjacent tiles
  
board class displays the board upon initialization. After every move, checks if gamed_ended, if not display again.
  
### main class
The main class will create an instance of the board. It will run a loop that takes input for a move then displays the board. The board will be displayed with numbers along the side and bottom (like in chess). The input will be formatted such as:  
- "0 1" for click on row 0 col 1
- "0 1 f" for flag on 0 1
- "0 1 show" for show surrounding tiles to 0 1
