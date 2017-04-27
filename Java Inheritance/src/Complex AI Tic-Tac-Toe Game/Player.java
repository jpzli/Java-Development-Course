package exercise3;
import java.io.*;

/**
 * 
 * @author John
 *@version 1.0
 *@since January 28,2017
 *
 */

abstract public class Player 
{
	/**
	 * Keeping record of the player's name.
	 */
	protected String name;
	/**
	 * Board layout data.
	 */
	protected Board board;
	/**
	 * Data object keeps track of the opponent of the game.
	 */
	protected Player opponent;
	/**
	 * A representation of the player in the game. Mark can either 'X' or 'O'.
	 */
	protected char mark;
	
	abstract protected void play();
	abstract protected void makeMove();
	
	/**
	 * Constructor that copies the player's name and the mark that will be used to represent them in the game.
	 * 
	 * @param name Player's name
	 * @param mark Representation of yourself in the game. Valid mark is either 'X' or 'O'.
	 */
	protected Player(String name, char mark)
	{
		this.name = name;
		this.mark = mark;
	}
		
	/**
	 * Retrieve user input from command line, then parse the input into an integer and return the value of this integer.
	 * This method is recursive compatible, which replaces the need for loops to run until there is no parsing error.
	 * 
	 * @return the number that entered via the command line.
	 */
	protected int get_number_input ()
	{
		int number = 0;
		String num;
		try
		{
			try
			{
				BufferedReader stdin;
				stdin = new BufferedReader(new InputStreamReader(System.in));
				num = stdin.readLine();
				number = Integer.parseInt(num);
				if(number < 0 || number > 2)
				{
					System.out.println("Invalid number range has been entered! Valid number range: 0~2. Please try again.");
					number = get_number_input();
				}
			}catch(NumberFormatException e)
				{
					System.out.println("Cannot parse the value you have entered! Valid number range: 0~2. Please try again.");
					number = get_number_input();
				}
		}catch(IOException e)
		{
			System.out.println("Unexpected input and output error! Program will now exit");
			System.exit(1);
		}
		
		return number;
	}
	
	/**
	 * Define the current player's opponent in the current game
	 * 
	 * @param opponent Data object contains information about the opponent.
	 */
	protected void setOpponent(Player opponent){ this.opponent = opponent; }
	
	/**
	 * Define the board layout for the Player Class.
	 * 
	 * @param theBoard Data object contains information regarding the current board layout.
	 */
	protected void setBoard(Board theBoard){ board = theBoard; }
}
