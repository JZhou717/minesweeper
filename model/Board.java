package model;
/**
* Represents a game board
**/

import java.util.*;

public class Board {
	public final static int EASY_SIZE = 8;
	public final static int MED_SIZE = 12;
	public final static int HARD_SIZE = 15;
	
	public final static int MIN_SIZE = 5;
	public final static int MAX_SIZE = 26;

	public int size;
	protected int mine_count;

	public boolean ended = false;

	protected Tile[][] board;

	/**
	 * @param s
	 *            size of board this constructor only used when one of the
	 *            default options are selected
	 **/
	public Board(int s) {
		if (s == EASY_SIZE) {
			mine_count = 8;
		} else if (s == MED_SIZE) {
			mine_count = 23;
		} else if (s == HARD_SIZE) {
			mine_count = 46;
		}

		this.size = s;
		board = new Tile[s][s];
		for (int r = 0; r < size; r++) {
			for (int c = 0; c < size; c++) {
				board[r][c] = new Tile(r, c);
			}
		}
		set_mines();
	}

	/**
	 * @param s
	 *            size of board
	 * @param c
	 *            how many bombs to add to the board this constructor is called
	 *            only for the custom board option
	 **/
	public Board(int s, int c) {
		// Checking if board size is out of bounds
		if (s < MIN_SIZE) {
			s = MIN_SIZE;
		} else if (s > MAX_SIZE) {
			s = MAX_SIZE;
		}

		// Checking if valid mine amount
		if (c < 1) {
			c = 1;
		} else if (c >= s * s) {
			c = ((s * s) - 1);
		}

		this.size = s;
		this.mine_count = c;
		board = new Tile[s][s];
		for (int r = 0; r < size; r++) {
			for (int col = 0; col < size; col++) {
				board[r][col] = new Tile(r, col);
			}
		}
		set_mines();
	}

	/**
	 * Sets the mines on the board. Create an ArrayList of Integers. Add all
	 * nums from 0 to ((s^2)-1) to it. In a for loop, bounded by mine count,
	 * pick a random position in the List. Remove the position from the List and
	 * mark position as bomb.
	 *
	 * After the bombs have been set, it runs set_count
	 **/
	protected void set_mines() {
		// Creating list of numbers
		List<Integer> positions = new ArrayList<>();
		for (int i = 0; i < size * size; i++) {
			positions.add(i);
		}
		// Picking positions to set as mines
		Random ran = new Random();
		for (int i = 0; i < mine_count; i++) {
			int index = ran.nextInt(positions.size());
			int pos = positions.remove(index);

			int row_pos = pos / size;
			int col_pos = pos % size;

			board[row_pos][col_pos].mine = true;
		}
		// Setting the counts on all the tiles
		set_counts();
	}

	/**
	 * Goes through all the rows and columns in the board and sets the adj_mines
	 * count for all non-mine tiles
	 *
	 * For each tile, starts from the top left and go clockwise
	 */
	protected void set_counts() {
		// For all the rows
		for (int r = 0; r < size; r++) {
			// For all the columns
			for (int c = 0; c < size; c++) {
				// If the tile is a mine, set count to -1
				if (board[r][c].mine == true) {
					board[r][c].adj_mines = -1;
					continue;
				}

				int count = 0;
				// Checking top
				if (r > 0) {
					// top left
					if (c > 0) {
						if (board[r - 1][c - 1].mine == true) {
							count++;
						}
					}
					// top center
					if (board[r - 1][c].mine == true) {
						count++;
					}
					// top right
					if (c < size - 1) {
						if (board[r - 1][c + 1].mine == true) {
							count++;
						}
					}
				}
				// Checking right
				if (c < size - 1) {
					if (board[r][c + 1].mine == true) {
						count++;
					}
				}
				// Checking below
				if (r < size - 1) {
					// bottom right
					if (c < size - 1) {
						if (board[r + 1][c + 1].mine == true) {
							count++;
						}
					}
					// bottom center
					if (board[r + 1][c].mine == true) {
						count++;
					}
					// bottom left
					if (c > 0) {
						if (board[r + 1][c - 1].mine == true) {
							count++;
						}
					}
				}
				// Checking left
				if (c > 0) {
					if (board[r][c - 1].mine == true) {
						count++;
					}
				}
				board[r][c].adj_mines = count;
			}
		}
		display();
	}

	/**
	*
	**/
	public void display() {
		System.out.println();
		System.out.println(mine_count + " remaining mines");
		for (int i = 0; i< 10 && i < size; i++) {
			System.out.print(" _");
		}
		for(int i = 10; i < size; i++) {
			System.out.print(" __");
		}
		System.out.println();
		// For each row
		for (int r = 0; r < size; r++) {
			// For each col
			for (int c = 0; c < size && c < 10; c++) {
				// if the tile is flagged
				if (board[r][c].flag == true) {
					System.out.print("|F");
				}
				// else if the tile has not been clicked
				else if (board[r][c].clicked == false) {
					System.out.print("|#");
				}
				// else if mine clicked
				else if (board[r][c].mine == true) {
					System.out.print("|*");
				}
				// else if zero adj_mines
				else if (board[r][c].adj_mines == 0) {
					System.out.print("|_");
				}
				// else show number
				else {
					System.out.print("|" + board[r][c].adj_mines);
				}
			}
			for (int c = 10; c < size; c++) {
				// if the tile is flagged
				if (board[r][c].flag == true) {
					System.out.print("|F_");
				}
				// else if the tile has not been clicked
				else if (board[r][c].clicked == false) {
					System.out.print("|#_");
				}
				// else if mine clicked
				else if (board[r][c].mine == true) {
					System.out.print("|*_");
				}
				// else if zero adj_mines
				else if (board[r][c].adj_mines == 0) {
					System.out.print("|__");
				}
				// else show number
				else {
					System.out.print("|" + board[r][c].adj_mines + "_");
				}
			}
			System.out.println("| " + r);
		}
		for (int i = 0; i < size; i++) {
			System.out.print(" " + i);
		}
		System.out.println();
	}

	/**
	 * Checks if the tile has been clicked, if so ignore. If not, check if tile is flagged, if so unflag tile, if not, flag tile
	 * @param row position ranging from 0 to size - 1
	 * @param col position ranging from 0 to size - 1
	 */
	public void flag(int row, int col) {
		
		Tile tile = board[row][col];
		//Can't flag clicked tile
		if(tile.clicked) {
			return;
		}
		//If tile not flagged
		if(!tile.flag) {
			mine_count--;
		}
		//Unflagging tile
		else {
			mine_count++;
		}
		tile.flag = !tile.flag;
		if(mine_count == 0) {
			end_game(true);
		}
		
		if(!ended) {
			check_win();
		}
	}

	/**
	 * Checks the surrounding tiles. If num of flagged tiles == this Tile's adj_mines, show all surrounding tiles. Otherwise ignore
	 * @param row position ranging from 0 to size - 1
	 * @param col position ranging from 0 to size - 1
	 */
	public void show(int row, int col) {
		
		Tile tile = board[row][col];
		int count = 0;
		
		//These booleans inform the if statements where the tile is, makes the if statements easier to read
		boolean top_edge = false, bottom_edge = false, left_edge = false, right_edge = false;
		if(tile.row_pos == 0) {
			top_edge = true;
		}
		else if(tile.row_pos == size - 1) {
			bottom_edge = true;
		}
		if(tile.col_pos == 0) {
			left_edge = true;
		}
		else if(tile.col_pos == size - 1) {
			right_edge = true;
		}
		
		//Counting all surround tiles. Start from top left and work clockwise
		if(!top_edge) {
			//top left
			if(!left_edge) {
				if(board[row - 1][col - 1].flag) {
					count++;
				}
			}
			//top center
			if(board[row - 1][col].flag) {
				count++;
			}
			//top right
			if(!right_edge) {
				if(board[row - 1][col + 1].flag) {
					count++;
				}
			}
		}
		//right
		if(!right_edge) {
			if(board[row][col + 1].flag) {
				count++;
			}
		}
		//bottom
		if(!bottom_edge) {
			//bottom right
			if(!right_edge) {
				if(board[row + 1][col + 1].flag) {
					count++;
				}
			}
			//bottom center
			if(board[row + 1][col].flag) {
				count++;
			}
			//bottom left
			if(!left_edge) {
				if(board[row + 1][col - 1].flag) {
					count++;
				}
			}
		}
		//left
		if(!left_edge) {
			if(board[row][col - 1].flag) {
				count++;
			}
		}
		
		//If count is greater or equal to adj_mines, click all surrounding tiles
		if(count >= tile.adj_mines) {
			click_all(row, col);
		}
		
	}

	/**
	 * Checks to see if the tile in question is already clicked or has been flagged. If so, ignore this move.
	 * Then check to see if the tile is a mine, if so end game with loss
	 * Then check if the tile has no adjacent mines, if so, click all surrounding tiles
	 * Otherwise, just click the tile
	 * @param row position ranging from 0 to size - 1
	 * @param col position ranging from 0 to size - 1
	 */
	public void click(int row, int col) {
		Tile tile = board[row][col];
		if(tile.clicked || tile.flag) {
			return;
		}
		if(tile.mine) {
			end_game(false);
			return;
		}
		tile.clicked = true;
		if(tile.adj_mines == 0) {
			click_all(row, col);
		}
		if(!ended) {
			check_win();
		}
	}

	/**
	 * calls click on all surrounding tiles of this position starting from the top left and moving clockwise
	 * @param row position ranging from 0 to size - 1
	 * @param col position ranging from 0 to size - 1
	 */
	protected void click_all(int row, int col) {
		//These booleans inform the if statements where the tile is, makes the if statements easier to read
		boolean top_edge = false, bottom_edge = false, left_edge = false, right_edge = false;
		if(row == 0) {
			top_edge = true;
		}
		else if(row == size - 1) {
			bottom_edge = true;
		}
		if(col == 0) {
			left_edge = true;
		}
		else if(col == size - 1) {
			right_edge = true;
		}
		
		//top
		if(!top_edge) {
			//top left
			if(!left_edge) {
				click(row - 1, col - 1);
			}
			//top center
			click(row - 1, col);
			//top right
			if(!right_edge) {
				click(row - 1, col + 1);
			}
		}
		//right
		if(!right_edge) {
			click(row, col + 1);
		}
		//bottom
		if(!bottom_edge) {
			//bottom right
			if(!right_edge) {
				click(row + 1, col + 1);
			}
			//bottom center
			click(row + 1, col);
			//bottom left
			if(!left_edge) {
				click(row + 1, col - 1);
			}
		}
		//left
		if(!left_edge) {
			click(row, col - 1);
		}
	}

	/**
	 * Checks to see if the game is over. If all unclicked tiles are hiding mines, then game is won
	 */
	protected void check_win() {
		
		Tile tile;
		
		for(int r = 0; r < size; r++) {
			for(int c = 0; c < size; c++) {
				tile = board[r][c];
				
				//if the spot is not clicked
				if(tile.clicked == false) {
					//if the tile is a mine, go to the next tile
					if(tile.mine) {
						continue;
					}
					//not all unclicked tiles are mines. did not win yet
					else {
						return;
					}
				}
			}
		}
		
		//Went through all tiles and only remaining unclicked tiles are mines. game won
		ended = true;
		//Changing remaining mines to 0
		mine_count = 0;
		//Flagging all mine tiles for final display
		for(int r = 0; r < size; r++) {
			for(int c = 0; c < size; c++) {
				tile = board[r][c];
				if(tile.mine) {
					tile.flag = true;
				}
			}
		}
		//game is over
		end_game(true);
		
		
	}
	
	protected void end_game(boolean won) {
		ended = true;
		if(won) {
			System.out.println("\n\n");
			System.out.println("Good job! You won!");
		}
		else {
			System.out.println("\n\n");
			System.out.println("Better luck next time");
			
			//Going through all tiles, and setting all mines tiles to clicked = true so they show on the last display
			for(int r = 0; r < size; r++) {
				for(int c = 0; c < size; c++) {
					if(board[r][c].mine) {
						board[r][c].clicked = true;
					}
				}
			}
		}
	}
}