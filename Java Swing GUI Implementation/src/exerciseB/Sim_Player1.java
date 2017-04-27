package exerciseB;

import java.io.IOException;

/**
 * 
 * @author John
 *@version 1.0
 *@since March 09,2017
 *
 */

public class Sim_Player1 extends Thread implements Constants
{	
	/**
 	*Used to simulate the game program running on a local machine connected to the network
 	*
 	*/
	public static void main(String[] args) throws IOException
	{	
		Player xPlayer;
		
		 //Create a local version of the game board
		Board board = new Board();
		GUI xGUI = new GUI(LETTER_X);
		xGUI.create_gui();
		
		 //Read in the Player's name
		while(xGUI.value.equals("")){  }
		
		String name = xGUI.name.getText();
		
		// Create the X Player
		xPlayer = new Player(name, LETTER_X,8090);
		//Assign the board for X Player
		xPlayer.board = board;
		
		boolean running = true;
		//Logic indicate it is the current player's turn
		boolean play = false;
		//In this assignment this is hard code so that Player X always moves first
		boolean first_play = true;
		String ana = xPlayer.s_in.readLine();

		//If the player is the first join the game, print out the waiting message
		if(ana.equals("First"))
		{
			play = true;
			xGUI.append_text("Waiting the other player to join...\n");
		}
		xGUI.append_text("\n" + xPlayer.s_in.readLine().substring(1) + "\n\n");
		if(!first_play){ xGUI.append_text("Waiting for the other player to make a move...\n\n"); }

		
		//Start the Player X client communication process with the Game Server
		while (running) 
		{
			if(first_play)
			{	
				//Inform the Player X to make a move
				xGUI.enable_board();
				xGUI.append_text(xPlayer.name + " is currently your turn to make a move." +"\n\n");
				xPlayer.play();
				while(xGUI.row == -1 || xGUI.col == -1){ }
				
				/*
				 *sends the messages of X is done playing, X's placement on the board, 
				 *and finally whether Player O should also terminate the game.  
				 */
				xPlayer.s_out.println("XDONE");
				xPlayer.s_out.println("X"+xGUI.row+" "+xGUI.col);
				xPlayer.s_out.println("XCONTINUE");
				xPlayer.board.addMark(xGUI.row, xGUI.col, LETTER_X);
				xGUI.row = xGUI.col = -1;
				
				xGUI.append_text("Waiting for the other player to make a move...\n\n");
				first_play = false;
				play = false;
				xGUI.disable_board();
			}
			
			String response = xPlayer.s_in.readLine();
			if(play)
			{	
				//Receive the mark of the opponent, split it into row and column and parse them into integer
				int row, col;
				try
				{
					String[] mark_loc = response.substring(1).split("\\s+");
					row = Integer.parseInt(mark_loc[0]);
					col = Integer.parseInt(mark_loc[1]);
				}catch(NumberFormatException e)
				{
					response = xPlayer.s_in.readLine();
					String[] mark_loc = response.substring(1).split("\\s+");
					row = Integer.parseInt(mark_loc[0]);
					col = Integer.parseInt(mark_loc[1]);
				}
					
				//Add the opponent's mark to the local version of the board, and display them on screen
				xGUI.set_op_mark(row, col, LETTER_O);
				xPlayer.board.addMark(row, col, LETTER_O);
				
				/*
				 * Analysis the response of the opponent, if "WIN" OR "TIE" then terminate the server connection
				 * else continue the game
				 */
				response = xPlayer.s_in.readLine();
				if(response.equals("OWIN"))
				{
					xGUI.append_text("Game over!\n The opponent has beaten you! Thanks for playing.");
					break;
				}
				else if(response.equals("OTIE"))
				{
					xGUI.append_text("Game over!\n The game ended with a tie. Thank you for playing.\n");
					break;
				}

				//Inform the Player X to make a move
				xGUI.append_text(xPlayer.name + " is currently your turn to make a move."+"\n\n");
				xGUI.enable_board();
				xPlayer.play();
				while(xGUI.row == -1 || xGUI.col == -1){ }
				xPlayer.board.addMark(xGUI.row, xGUI.col, LETTER_X);
				
				/*
				 *The following if statement checks for whether to terminate the game, and sends the messages of X is done playing, X's placement on the board, 
				 *and finally whether Player O should also terminate the game.  
				 */
				//Check after each move to see if Player X has won the game, if so give win information to server, then relay to the opponent.
				if(xPlayer.board.xWins())
				{
					xGUI.append_text("Game Over!\n You Win! Thank you for playing.");
					xPlayer.s_out.println("XDONE");
					xPlayer.s_out.println("X"+xGUI.row+" "+xGUI.col);
					xPlayer.s_out.println("XWIN");
					xGUI.disable_board();
					xGUI.row = xGUI.col = -1;
					break;
				}
				//Check after each move to see if the board is full, and there is still no winner.
				else if(xPlayer.board.isFull())
				{
					xGUI.append_text("Game Over!\n The game ended with a tie. Thank you for playing.");
					xPlayer.s_out.println("XDONE");
					xPlayer.s_out.println("X"+xGUI.row+" "+xGUI.col);
					xPlayer.s_out.println("XTIE");
					xGUI.disable_board();
					xGUI.row = xGUI.col = -1;
					break;
				}
				//If none of the condition is met, then send the command to "continue" to the server, and the server tells the opponent the game continues
				else
				{
					xGUI.append_text("Waiting for the opponent to make a move...\n\n");
					xPlayer.s_out.println("XDONE");
					xPlayer.s_out.println("X"+xGUI.row+" "+xGUI.col);
					xPlayer.s_out.println("XCONTINUE");
					xGUI.disable_board();
					play = false;
					xGUI.row = xGUI.col = -1;
				}
			}
			//response from the opponent is "ODONE" then set play to true and let Player X play
			else if(response.equals("ODONE"))
				play = true;
			else
				continue;
		}
		
		try {
			//Close down resource
			xPlayer.s_in.close();
			xPlayer.s_out.close();
		}catch (IOException e) 
		{
			System.out.println("closing error: " + e.getMessage());
		}
	}
}
