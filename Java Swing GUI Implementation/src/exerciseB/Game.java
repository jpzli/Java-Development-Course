package exerciseB;
import java.io.*;

//STUDENTS SHOULD ADD CLASS COMMENT, METHOD COMMENTS, FIELD COMMENTS 

/**
 * 
 * @author John
 *@version 1.0
 *@since January 28,2017
 *
 */

public class Game extends Thread implements Constants{
	
	/**
	 * Listener to assigned player's message over the network
	 */
	BufferedReader in;
	PrintWriter out;
	/**
	 * Outlet to opponent's message listener on the network
	 */
	PrintWriter out_opp;
	
	/**
	 * Default constructor to create a new game object. As well as, create the socket for the 2 client communication
	 */
    public Game(BufferedReader in, PrintWriter out) {
    	this.in= in;
    	this.out =out;
}
    /**
     * Setup the route to opponent's listener for incoming messages from the assigned player
     * @param out_opp 
     */
    void setOpp(PrintWriter out_opp){ this.out_opp = out_opp; }
    
    @Override
    public synchronized void run()
    {    	
    	boolean running = true;
		
    	//Set a timeout counter
		int timeout = 0;
		
		while(running)
		{
			try
			{
				//If timeout counter reaches 60 then terminate
				if(timeout == 60)
				{
					//This will terminate the running server if the port for the client is dead
					running = false;
					break;
				}
				
				 //If current is zero then do communication with player 1, else do communication with player 2
				String line = in.readLine();
				out_opp.println(line);	
			}
			/**
			 * If NullPointerException is caught that means the clients have closed their connection, so start count up the timeout counter.
			 */
			catch(NullPointerException e)
			{

				timeout++;
				continue;
			}
			catch(IOException e)
			{
				System.out.println("One of the player has logged off.");
				break;
			}
		}
		
		try
		{
			//Close down resources
			in.close();
			out.close();
			out_opp.close();
		}catch(IOException e)
		{
			System.out.println("An error has occurred while trying to close down.");
		}
    }
}
