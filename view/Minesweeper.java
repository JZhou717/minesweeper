package view;

import java.util.Scanner;

import model.Board;

/*
 * 
 * 
 * Possible idea for an update. I believe in the game, if the number of remaining mines is equal to the number of remaining tiles. All tiles will be flagged and you will win. Shouldn't be hard to code... but there are more important things rn
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 */

class Minesweeper {
	
	
	//The board used for this game
	private static Board board;
	private static Scanner in;
	
	public static void main(String[] args) {
		//Reading input from user
		in = new Scanner(System.in);
		
		read_difficulty();
		play();
		
		
		in.close();
	}

	private static void play() {
		System.out.println("This terminal version is not the intended final projcet. For instructions on how to format moves, please refer to the readme");
		String line;
		String[] move;
		int row, col;
		while(!board.ended) {
			line = in.nextLine();
			move = line.split(" ");
			if(move.length < 2) {
				System.out.println("Invalid input");
			}
			try {
				//Three parts to the input, third part is either flag or show... ignoring rubbish. GUI will not be able to have rubbish input
				if(move.length >= 3) {
					if(move[2].equalsIgnoreCase("f")) {
						row = Integer.parseInt(move[0]);
						col = Integer.parseInt(move[1]);
						if(row < 0 || row >= board.size || col < 0 || col >= board.size) {
							throw new IllegalArgumentException();
						}
						board.flag(row, col);
						board.display();
					}
					else if(move[2].equalsIgnoreCase("show")) {
						row = Integer.parseInt(move[0]);
						col = Integer.parseInt(move[1]);
						if(row < 0 || row >= board.size || col < 0 || col >= board.size) {
							throw new IllegalArgumentException();
						}
						board.show(row, col);
						board.display();
					}
					else {
						continue;
					}
				}
				//Click move
				else {
					row = Integer.parseInt(move[0]);
					col = Integer.parseInt(move[1]);
					if(row < 0 || row >= board.size || col < 0 || col >= board.size) {
						throw new IllegalArgumentException();
					}
					board.click(row, col);
					board.display();
				}
			}
			catch(IllegalArgumentException e) {
				System.out.println("Please input a valid row number followed by a valid column number");
			}
		}
		
	}

	/**
	 * helper method that reads input from the user for a difficulty setting. remains in this method until a valid input is given
	 */
	private static void read_difficulty() {

		System.out.println("New Game. 'easy', 'medium', 'hard', or 'custom'? 'q' to quit.");
		while(true) {
			String diff = in.nextLine().replaceAll("\\s+", "");
			if(diff.equalsIgnoreCase("q")) {
				in.close();
				System.exit(0);
			}
			
			if(diff.equalsIgnoreCase("easy")) {
				board = new Board(Board.EASY_SIZE);
				return;
			}
			else if(diff.equalsIgnoreCase("medium")) {
				board = new Board(Board.MED_SIZE);
				return;
			}
			else if(diff.equalsIgnoreCase("hard")) {
				board = new Board(Board.HARD_SIZE);
				return;
			}
			else if(diff.equalsIgnoreCase("custom")) {
				int size, mines;
				String temp;
				
				while(true) {
					try {
						System.out.println("How large do you want each side of the board to be? Your answer is inclusively bounded between " + Board.MIN_SIZE + " and " + Board.MAX_SIZE + ". 'q' to quit.");
						temp = in.nextLine().replaceAll("\\s+", "");
						if(temp.equalsIgnoreCase("q")) {
							in.close();
							System.exit(0);
						}
						size = Integer.parseInt(temp);
						
						System.out.println("How many mines do you want to see? Your answer is inclusively bounded between 1 and 1 fewer than the total squares of your board");
						temp = in.nextLine().replaceAll("\\s+", "");
						mines = Integer.parseInt(temp);
						
						//Got the valid inputs without exception
						board = new Board(size, mines);
						return;
					}
					catch(IllegalArgumentException e) {
						System.out.println("Sorry, that doesn't look like a valid number, please try again");
						continue;
					}
				}
				
			}
			else {
				System.out.println("Sorry, I don't recognize this input. Please try again. 'easy', 'medium', 'hard', or 'custom'?");
				continue;
			}
		}
		
	}
}