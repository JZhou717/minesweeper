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

	protected int size;
	protected int mine_count;

	private boolean ended = false;

	private Tile[][] board;

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
	private void set_mines() {
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
	private void set_counts() {
		// For all the rows
		for (int r = 0; r < size; r++) {
			// For all the columns
			for (int c = 0; c < size; c++) {
				// If the tile is a mine, skip
				if (board[r][c].mine == true) {
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
		for (int i = 0; i < size; i++) {
			System.out.print(" _");
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
					System.out.print("|F ");
				}
				// else if the tile has not been clicked
				else if (board[r][c].clicked == false) {
					System.out.print("|# ");
				}
				// else if mine clicked
				else if (board[r][c].mine == true) {
					System.out.print("|* ");
				}
				// else if zero adj_mines
				else if (board[r][c].adj_mines == 0) {
					System.out.print("|_ ");
				}
				// else show number
				else {
					System.out.print("|" + board[r][c].adj_mines + " ");
				}
			}
			System.out.println("| " + r);
		}
		for (int i = 0; i < size; i++) {
			System.out.print(" " + i);
		}
		System.out.println();
	}
}