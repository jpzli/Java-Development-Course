package exerciseB;

import java.io.IOException;

/**
 * 
 * @author John
 *@version 1.0
 *@since March 09,2017
 *
 */

public class Sim_Player2 extends Thread implements Constants
{	
	/**
 	*Used to simulate the game program running on a local machine connected to the network
 	*
 	*/
	public static void main(String[] args) throws IOException
	{	
		Player oPlayer;
		
		 //Create a local version of the game board
		Board board = new Board();
		GUI oGUI = new GUI(LETTER_O);
		oGUI.create_gui();
		
		 //Read in the Player's name		
		while(oGUI.value.equals("")){ }
		String name = oGUI.name.getText();
		
		// Create the X Player
		oPlayer = new Player(name, LETTER_O,8090);
		//Assign the board for X Player
		oPlayer.board = board;
		
		boolean running = true;
		//Logic indicate it is the current player's turn
		boolean play = false;
		//In this assignment this is hard code so that Player X always moves first
		boolean first_play = false;
		String ana = oPlayer.s_in.readLine();

		//If the player is the first join the game, print out the waiting message
		if(ana.equals("First"))
		{
			play = true;
			oGUI.append_text("Waiting the other player to join...\n");
		}
		oGUI.append_text("\n" + oPlayer.s_in.readLine().substring(1) + "\n\n");
		if(!first_play){ oGUI.append_text("Waiting for the other player to make a move...\n\n"); }

		
		//Start the Player X client communication process with the Game Server
		while (running) 
		{
			if(first_play)
			{	
				//Inform the Player X to make a move
				oGUI.enable_board();
				oGUI.append_text(oPlayer.name + " is currently your turn to make a move." +"\n\n");
				oPlayer.play();
				while(oGUI.row == -1 || oGUI.col == -1){ }
				
				/*
				 *sends the messages of X is done playing, X's placement on the board, 
				 *and finally whether Player O should also terminate the game.  
				 */
				oPlayer.s_out.println("ODONE");
				oPlayer.s_out.print(" ");
				oPlayer.s_out.println("O"+oGUI.row+" "+oGUI.col);
				oPlayer.s_out.println("OCONTINUE");
				oPlayer.board.addMark(oGUI.row, oGUI.col, LETTER_O);
				oGUI.row = oGUI.col = -1;
				
				oGUI.append_text("Waiting for the other player to make a move...\n\n");
				first_play = false;
				play = false;
				oGUI.disable_board();
			}

			String response = oPlayer.s_in.readLine();
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
					response = oPlayer.s_in.readLine();
					String[] mark_loc = response.substring(1).split("\\s+");
					row = Integer.parseInt(mark_loc[0]);
					col = Integer.parseInt(mark_loc[1]);
				}
				
				//Add the opponent's mark to the local version of the board, and display them on screen
				oGUI.set_op_mark(row, col, LETTER_X);
				oPlayer.board.addMark(row, col, LETTER_X);
				
				/*
				 * Analysis the response of the opponent, if "WIN" OR "TIE" then terminate the server connection
				 * else continue the game
				 */
				response = oPlayer.s_in.readLine();
				if(response.equals("XWIN"))
				{
					oGUI.append_text("Game over!\n The opponent has beaten you! Thanks for playing.");
					break;
				}
				else if(response.equals("XTIE"))
				{
					oGUI.append_text("Game over!\n The game ended with a tie. Thank you for playing.\n");
					break;
				}

				//Inform the Player X to make a move
				oGUI.append_text(oPlayer.name + " is currently your turn to make a move."+"\n\n");
				oGUI.enable_board();
				oPlayer.play();
				while(oGUI.row == -1 || oGUI.col == -1){ }
				oPlayer.board.addMark(oGUI.row, oGUI.col, LETTER_O);
				
				/*
				 *The following if statement checks for whether to terminate the game, and sends the messages of X is done playing, X's placement on the board, 
				 *and finally whether Player O should also terminate the game.  
				 */
				//Check after each move to see if Player X has won the game, if so give win information to server, then relay to the opponent.
				if(oPlayer.board.oWins())
				{
					oGUI.append_text("Game Over!\n You Win! Thank you for playing.");
					oPlayer.s_out.println("ODONE");
					oPlayer.s_out.println("O"+oGUI.row+" "+oGUI.col);
					oPlayer.s_out.println("OWIN");
					oGUI.disable_board();
					oGUI.row = oGUI.col = -1;
					break;
				}
				//Check after each move to see if the board is full, and there is still no winner.
				else if(oPlayer.board.isFull())
				{
					oGUI.append_text("Game Over!\n The game ended with a tie. Thank you for playing.");
					oPlayer.s_out.println("ODONE");
					oPlayer.s_out.println("O"+oGUI.row+" "+oGUI.col);
					oPlayer.s_out.println("OTIE");
					oGUI.disable_board();
					oGUI.row = oGUI.col = -1;
					break;
				}
				//If none of the condition is met, then send the command to "continue" to the server, and the server tells the opponent the game continues
				else
				{
					oGUI.append_text("Waiting for the opponent to make a move...\n\n");
					oPlayer.s_out.println("ODONE");
					oPlayer.s_out.println("O"+oGUI.row+" "+oGUI.col);
					oPlayer.s_out.println("OCONTINUE");
					oGUI.disable_board();
					play = false;
					oGUI.row = oGUI.col = -1;
				}
			}
			//response from the opponent is "ODONE" then set play to true and let Player X play
			else if(response.equals("XDONE"))
				play = true;
			else
				continue;
		}
		
		try {
			//Close down resource
			oPlayer.s_in.close();
			oPlayer.s_out.close();
		}catch (IOException e) 
		{
			System.out.println("closing error: " + e.getMessage());
		}
	}
}
