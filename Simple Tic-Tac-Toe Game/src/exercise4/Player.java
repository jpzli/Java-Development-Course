package exercise4;
import java.io.*;

/**
 * 
 * @author John
 *@version 1.0
 *@since January 28,2017
 *
 */

public class Player 
{
	/**
	 * Keeping record of the player's name.
	 */
	private String name;
	/**
	 * Board layout data.
	 */
	private Board board;
	/**
	 * Data object keeps track of the opponent of the game.
	 */
	private Player opponent;
	/**
	 * A representation of the player in the game. Mark can either 'X' or 'O'.
	 */
	private char mark;
	
	/**
	 * Constructor that copies the player's name and the mark that will be used to represent them in the game.
	 * 
	 * @param name Player's name
	 * @param mark Representation of yourself in the game. Valid mark is either 'X' or 'O'.
	 */
	Player(String name, char mark)
	{
		this.name = name;
		this.mark = mark;
	}
	
	/**
	 * Keep calling the method "makeMove" to prompt the player to make a move until one of the win condition have been met.
	 * Full board resulting in a tie also count as a win condition since like Player X wins, or Player O wins, it terminates the game and shows a Game Over
	 */
	void play()
	{
		while((board.xWins() || board.oWins() || board.isFull()) == false)
			this.makeMove();
	}
	
	/**
	 * Inform the player that it is currently their turn to make a move, update the board layout, and check for win condition.
	 * The win condition is either Player X wins, Player O wins, or A tie 
	 */
	void makeMove()
	{
		System.out.println(name + ", please specified which row your next " + mark + " wil be placed at: ");
		int row = get_number_input();
			
		System.out.println(name + ", please specified which column your next " + mark + " wil be placed at: ");
		int column = get_number_input();
		
		while(board.getMark(row, column) != ' ')
		{
			System.out.println(name + " ,this is an occupied slot. Please enter your mark in an empty slot!\nRow first followed by Enter key, and column second followed by Enter key");
			row = get_number_input();
			column = get_number_input();
		}
		
		board.addMark(row, column, mark);
		board.display();
		if(board.xWins() == true)
		{
			System.out.println("Gmae Over!\n The winner of this round is X - " + name);
			return;
		}
		else if(board.isFull() == true)
		{
			System.out.print("Game OVer!\n This round ended with a tie");
			return;
		}
		
		System.out.println(opponent.name + ", please specified which row your next " + opponent.mark + " wil be placed at: ");
		row = get_number_input();
			
		System.out.println(opponent.name + ", please specified which column your next " + opponent.mark + " wil be placed at: ");
		column = get_number_input();
		
		while(board.getMark(row, column) != ' ')
		{
			System.out.println(opponent.name + " ,this is an occupied slot. Please enter your mark in an empty slot!\nRow first followed by Enter key, and column second followed by Enter key");
			row = get_number_input();
			column = get_number_input();
		}		
		board.addMark(row, column, opponent.mark);
		board.display();
		if((board.oWins() == true) && (mark == 'O'))
			System.out.println("Gmae Over!\n The winner of this round is O - " + name);
	}
		
	/**
	 * Retrieve user input from command line, then parse the input into an integer and return the value of this integer.
	 * This method is recursive compatible, which replaces the need for loops to run until there is no parsing error.
	 * 
	 * @return the number that entered via the command line.
	 */
	int get_number_input ()
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
	void setOpponent(Player opponent){ this.opponent = opponent; }
	
	/**
	 * Define the board layout for the Player Class.
	 * 
	 * @param theBoard Data object contains information regarding the current board layout.
	 */
	void setBoard(Board theBoard){ board = theBoard; }
}
