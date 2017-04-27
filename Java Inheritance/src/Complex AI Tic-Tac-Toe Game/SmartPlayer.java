package exercise3;

public class SmartPlayer extends BlockingPlayer 
{
	SmartPlayer(String name, char mark){ super(name, mark); }
	
	@Override
	/**
	 * Check for each of the possible pattern that would result a win, and respond accordingly.
	 * If there is no winning pattern that can be taken, then this.makeMove() will invoke its super, 
	 * which is trying to block an opponent from winning. 
	 */
	protected void makeMove()
	{
		get_mark_loc(this.mark);
		if(check_row() == true && !row_location.isEmpty())
		{
			System.out.println(name + " is thinking... ... ...");
			board.display();
			System.out.println();
			return;
		}

		if(check_col() == true && !row_location.isEmpty())
		{
			System.out.println(name + " is thinking... ... ...");
			board.display();
			System.out.println();
			return;
		}

		if(check_diag1() == true && !row_location.isEmpty())
		{
			System.out.println(name + " is thinking... ... ...");
			board.display();
			System.out.println();
			return;
		}
		
		if(check_diag2() == true && !row_location.isEmpty())
		{
			System.out.println(name + " is thinking... ... ...");
			board.display();
			System.out.println();
			return;
		}
	
		super.makeMove();
	}
}