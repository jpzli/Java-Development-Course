package exercise4;
import java.io.*;

//STUDENTS SHOULD ADD CLASS COMMENT, METHOD COMMENTS, FIELD COMMENTS 

/**
 * 
 * @author John
 *@version 1.0
 *@since January 28,2017
 *
 */

public class Game implements Constants {

	/**
	 * Board layout data object
	 */
	private Board theBoard;
	/**
	 * Referee data object
	 */
	private Referee theRef;
	
	/**
	 * Default constructor to create a new game object. As well as, setup a new board for the new game
	 */
    public Game( ) {
        theBoard  = new Board();

	}
    
    /**
     * Assign "Referee r" to "theRef" as the new referee. Then invoke the method to run the game by "theRef"
     * 
     * @param r object contains information of the Referee being assigned
     * @throws IOException
     */
    public void appointReferee(Referee r) throws IOException {
        theRef = r;
    	theRef.runTheGame();
    }
	
	public static void main(String[] args) throws IOException {
		Referee theRef;
		Player xPlayer, oPlayer;
		BufferedReader stdin;
		Game theGame = new Game();
		stdin = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("\nPlease enter the name of the \'X\' player: ");
		String name= stdin.readLine();
		while (name == null) {
			System.out.print("Please try again: ");
			name = stdin.readLine();
		}

		xPlayer = new Player(name, LETTER_X);
		xPlayer.setBoard(theGame.theBoard);
		
		System.out.print("\nPlease enter the name of the \'O\' player: ");
		name = stdin.readLine();
		while (name == null) {
			System.out.print("Please try again: ");
			name = stdin.readLine();
		}
		
		oPlayer = new Player(name, LETTER_O);
		oPlayer.setBoard(theGame.theBoard);
		
		theRef = new Referee();
		theRef.setBoard(theGame.theBoard);
		theRef.setoPlayer(oPlayer);
		theRef.setxPlayer(xPlayer);
        
        theGame.appointReferee(theRef);
        System.out.println("\nNew Game?\nType Y to begin New Game.\nType N to Quit");
        
        name = stdin.readLine().toUpperCase();
        char letter = name.charAt(0);
        
        if(letter == 'N')
        { }
        else if(letter == 'Y')
        	main(null);
        else
        	System.out.println("Invalid option was inputted, default option used. Program will now exit");
	
	}
	

}
