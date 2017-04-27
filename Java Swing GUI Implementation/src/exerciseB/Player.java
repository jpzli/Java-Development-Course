package exerciseB;
import java.io.*;
import java.net.Socket;

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
	String name;
	/**
	 * Board layout data.
	 */
	Board board;
	/**
	 * A representation of the player in the game. Mark can either 'X' or 'O'.
	 */
	
	Socket player_socket;
	PrintWriter s_out;
	BufferedReader s_in;
	
	/**
	 * Keep track of the current player's decision for server communication
	 */
	String mark_loc;
	
	/**
	 * Constructor that copies the player's name and the mark that will be used to represent them in the game.
	 * 
	 * @param name Player's name
	 * @param mark Representation of yourself in the game. Valid mark is either 'X' or 'O'.
	 */
	Player(String name, char mark, int port)
	{
		this.name = name;
		try
		{
			player_socket = new Socket("localhost", port);
			s_out = new PrintWriter(player_socket.getOutputStream(), true);
			s_in = new BufferedReader(new InputStreamReader(player_socket.getInputStream()));
		}catch(IOException e)
		{
			System.err.println(e.getMessage());
		}
	}
	
	/**
	 * Keep calling the method "makeMove" to prompt the player to make a move until one of the win condition have been met.
	 * Full board resulting in a tie also count as a win condition since like Player X wins, or Player O wins, it terminates the game and shows a Game Over
	 */
	void play()
	{
		this.makeMove();
	}
	
	/**
	 * Inform the player that it is currently their turn to make a move, update the board layout, and check for win condition.
	 * The win condition is either Player X wins, Player O wins, or A tie 
	 */
	void makeMove()	{ }
}
