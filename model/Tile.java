package model;

/**
 * Represents a single tile in
 **/

class Tile {

	protected int row_pos;
	protected int col_pos;

	protected boolean mine = false;
	protected int adj_mines = 0;

	protected boolean clicked = false;
	protected boolean flag = false;

	public Tile(int r, int c) {
		row_pos = r;
		col_pos = c;
	}

}