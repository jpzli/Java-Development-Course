package exercise4;

//STUDENTS SHOULD ADD CLASS COMMENT, METHOD COMMENTS, FIELD COMMENTS 

/**
 * 
 * @author John
 * @version 1.0
 * @since January 28,2017
 *
 */

public class Board implements Constants {
	/**
	 * Keep track of the mark the player has inputted
	 */
	private char theBoard[][];
	/**
	 * Keep track of total number of mark presented on the board
	 */
	private int markCount;

	/**
	 * Constructs a new Board object that is 3 rows high, and 3 columns width. theBoard will be filled with Spaces to indicate it is empty.
	 */
	public Board() {
		markCount = 0;
		theBoard = new char[3][];
		for (int i = 0; i < 3; i++) {
			theBoard[i] = new char[3];
			for (int j = 0; j < 3; j++)
				theBoard[i][j] = SPACE_CHAR;
		}
	}

	/**
	 * Retrieve the mark at the specified location
	 * 
	 * @param row Specify the row number
	 * @param col Specify the column number
	 * @return the mark at the specified row and column
	 */
	public char getMark(int row, int col) {
		return theBoard[row][col];
	}

	/**
	 * Check if the board is currently full
	 * 
	 * @return true is the "markcount" is equal to 9, else false to indicate the board is yet full 
	 */
	public boolean isFull() {
		return markCount == 9;
	}

	/**
	 * Check if Player represented by mark 'X' has won the game.
	 * 
	 * @return true is the checkWinner method returns the value 1 for mark 'X', else false
	 */
	public boolean xWins() {
		if (checkWinner(LETTER_X) == 1)
			return true;
		else
			return false;
	}

	/**
	 * Check if Player represneted by mark 'O' has won the game.
	 * 
	 * @return true is the checkWinner method returns the value 1 for mark 'O', else false
	 */
	public boolean oWins() {
		if(checkWinner(LETTER_O)==1)
			return true;
		else
			return false;
	}
	
	/**
	 *Print out the current board layout, including the headers, the cosmetic effects, and the two players' placement of the marks. 
	 */
	public void display() {
		displayColumnHeaders();
		addHyphens();
		for (int row = 0; row < 3; row++) {
			addSpaces();
			System.out.print("    row " + row + ' ');
			for (int col = 0; col < 3; col++)
				System.out.print("|  " + getMark(row, col) + "  ");
			System.out.println("|");
			addSpaces();
			addHyphens();
		}
	}

	/**
	 * Add a mark at the user specified location.
	 * 
	 * @param row Row number that the mark is going to be in.
	 * @param col Column number that the mark is going to be in.
	 * @param mark Represents the current player in-play
	 */
	public void addMark(int row, int col, char mark) {
		
		//If you don't mind me adding something to prevent the other player from overwriting content at the specified location
		//if something is already there, and prevent wasting a turn writing in the same block
		theBoard[row][col] = mark;
		markCount++;
	}

	/**
	 * Empties the board. This is done by resetting "theBoard" array with white spaces.
	 */
	public void clear() {
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				theBoard[i][j] = SPACE_CHAR;
		markCount = 0;
	}
	
	/**
	 * Check if the win condition has been met.
	 * 
	 * @param mark the desired mark that is going to be checked
	 * @return result, result can be either 1 or 0 to indicate if the player of the associated mark has won the game 
	 */
	int checkWinner(char mark) {
		int row, col;
		int result = 0;

		for (row = 0; result == 0 && row < 3; row++) {
			int row_result = 1;
			for (col = 0; row_result == 1 && col < 3; col++)
				if (theBoard[row][col] != mark)
					row_result = 0;
			if (row_result != 0)
				result = 1;
		}

		
		for (col = 0; result == 0 && col < 3; col++) {
			int col_result = 1;
			for (row = 0; col_result != 0 && row < 3; row++)
				if (theBoard[row][col] != mark)
					col_result = 0;
			if (col_result != 0)
				result = 1;
		}

		if (result == 0) {
			int diag1Result = 1;
			for (row = 0; diag1Result != 0 && row < 3; row++)
				if (theBoard[row][row] != mark)
					diag1Result = 0;
			if (diag1Result != 0)
				result = 1;
		}
		if (result == 0) {
			int diag2Result = 1;
			for (row = 0; diag2Result != 0 && row < 3; row++)
				if (theBoard[row][3 - 1 - row] != mark)
					diag2Result = 0;
			if (diag2Result != 0)
				result = 1;
		}
		return result;
	}

	/**
	 * Print out the column headers onto the screen
	 */
	void displayColumnHeaders() {
		System.out.print("          ");
		for (int j = 0; j < 3; j++)
			System.out.print("|col " + j);
		System.out.println();
	}

	/**
	 * Add spaces before adding hyphens to separate each row. This is done to create the effect of a cell.
	 */
	void addHyphens() {
		System.out.print("          ");
		for (int j = 0; j < 3; j++)
			System.out.print("+-----");
		System.out.println("+");
	}

	/**
	 * Add spaces before adding "|" to separate each column. This is done to create the effect of a cell.
	 */
	void addSpaces() {
		System.out.print("          ");
		for (int j = 0; j < 3; j++)
			System.out.print("|     ");
		System.out.println("|");
	}
}
