package exercise3;

/**
 * 
 * @author John
 *@version 1.0
 *@since January 28,2017
 *
 */

public class Referee 
{
	/**
	 * Player X's data
	 */
	private Player xPlayer;
	/**
	 * Player O's data
	 */
	private Player oPlayer;
	/**
	 *  Board layout data.
	 */
	private Board board;
	
	/**
	 * Setup opponent information, clearing the board, and inform the player to play the game.
	 */
	void runTheGame()
	{
		xPlayer.setOpponent(oPlayer);
		oPlayer.setOpponent(xPlayer);
		board.clear();
		board.display();
		System.out.println();
		while(true)
		{
			xPlayer.play();
			if(board.xWins() == true)
			{
				System.out.println("Game Over!\n The winner of this round is X - " + xPlayer.name);
				break;
			}
			else if(board.isFull() == true)
			{
				System.out.print("Game Over!\n This round ended with a tie");
				break;
			}
			
			oPlayer.play();
			if(board.oWins() == true)
			{
				System.out.println("Game Over!\n The winner of this round is O - " + oPlayer.name);
				break;
			}
		}
	}
	
	/**
	 * Define board layout for the Referee Class.
	 * 
	 * @param board Data object contains information regarding the current board layout.
	 */
	void setBoard(Board board){ this.board = board; }
	
	/**
	 * Define Player represented by O for the Referee.
	 * 
	 * @param oPlayer Data object contains information regarding Player O.
	 */
	void setoPlayer(Player oPlayer){ this.oPlayer = oPlayer; }
	
	/**
	 * Define Player represented by X for the Referee.
	 * 
	 * @param xPlayer Data object contains information regarding Player X.
	 */
	void setxPlayer(Player xPlayer){ this.xPlayer = xPlayer; }

}
