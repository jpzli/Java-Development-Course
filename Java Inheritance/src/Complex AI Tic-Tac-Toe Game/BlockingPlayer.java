package exercise3;
import java.util.ArrayList;

public class BlockingPlayer extends RandomPlayer
{
	/**
	 * row_location is an ArrayList that of the rows where the opponent has placed a mark
	 */
	protected ArrayList<Integer>row_location = new ArrayList<Integer>();
	
	/**
	 * row_location is an ArrayList that of the rows where the opponent has placed a mark
	 */
	protected ArrayList<Integer>col_location = new ArrayList<Integer>();
	
	BlockingPlayer(String name, char mark) { super(name, mark); }
	
	@Override
	/**
	 * Check for each of the possible pattern that would result a win for the opponent, and respond accordingly.
	 * If there is no winning pattern for the opponent then this.makeMove() will invoke its super, which is randomly select a tile and place a mark 
	 */
	protected void makeMove()
	{
		get_mark_loc(opponent.mark);
		if(check_row() == true && !row_location.isEmpty())
		{
			System.out.println(name + " is thinking... ...");
			board.display();
			System.out.println();
			return;
		}

		if(check_col() == true && !row_location.isEmpty())
		{
			System.out.println(name + " is thinking... ...");
			board.display();
			System.out.println();
			return;
		}

		if(check_diag1() == true && !row_location.isEmpty())
		{
			System.out.println(name + " is thinking... ...");
			board.display();
			System.out.println();
			return;
		}
		
		if(check_diag2() == true && !row_location.isEmpty())
		{
			System.out.println(name + " is thinking... ...");
			board.display();
			System.out.println();
			return;
		}
	
		super.makeMove();
		
	}
	
	/**
	 *Checks the current board layout and store the row and the column location of the specified player's mark.
	 *
	 *@param mark_check specifies whose mark placement to get and to be stored.
	 */
	protected void get_mark_loc(char mark_check)
	{
		row_location.clear();
		col_location.clear();
		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 3; j++)
			{
				if(board.getMark(i, j) == mark_check)
				{
					row_location.add(i);
					col_location.add(j);
				}
			}
		}
	}
	
	/**
	 * Goes through each row and invokes check_row_freq as a part of the switch statement condition. 
	 * Inside of the switch statement if check_row_freq returns a '1', then loops through each column on that particular row to see 
	 * if there is a blocking opportunity.
	 *   
	 * @return true if TestforBlocking indicates there is an empty spot available to stop the opponent from winning, while looping
	 * through each column at the specified row, and have successfully placed down a mark.
	 * Else return false
	 */
	protected boolean check_row()
	{
		for(int row = 0; row < 3; row++)
		{
			switch(check_row_freq(row))
			{
				case 1:
				for(int col=0; col < 3; col++)
				{
					if(TestforBlocking(row, col) == true)
					{
						board.addMark(row, col, mark);
						return true;
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * Goes through each column and invokes check_col_freq as a part of the switch statement condition. 
	 * Inside of the switch statement if check_col_freq returns a '1', then loops through each row on that particular column to see 
	 * if there is a blocking opportunity.
	 *   
	 * @return true if TestforBlocking indicates there is an empty spot available to stop the opponent from winning, while looping
	 * through each row at the specified column, and have successfully placed down a mark.
	 * Else return false
	 */
	protected boolean check_col()
	{
		for(int col = 0; col < 3; col++)
		{
			switch(check_col_freq(col))
			{
				case 1:
				for(int row=0; row < 3; row++)
				{
					if(TestforBlocking(row, col) == true)
					{
						board.addMark(row, col, mark);
						return true;
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * Invokes check_dig1_freq as part of the switch statement condition. Inside of the switch statement if check_diag1_freq returns a '1'.
	 * then loops through each tile where the row number equals the column number and see if there is a blocking opportunity.
	 * 
	 * @return true if TestforBlocking indicates there is an empty spot available in the diag1 pattern to stop the opponent from winning, 
	 * and have successfully placed down a mark.
	 * Else return false
	 */
	protected boolean check_diag1()
	{
		switch(check_diag1_freq())
		{
			case 1:
			for(int i = 0, j=0; i < 3; i++,j++)
			{
				if(TestforBlocking(i, j) == true)
				{
					board.addMark(i, j, mark);
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Invokes check_dig2_freq as part of the switch statement condition. Inside of the switch statement if check_diag2_freq returns a '1'.
	 * then loops through each tile where the sum of row number and column number equals to 2, to see if there is a blocking opportunity.
	 * 
	 * @return true if TestforBlocking indicates there is an empty spot available in the diag2 pattern to stop the opponent from winning,
	 * and have successfully placed down a mark.
	 * Else return false
	 */
	protected boolean check_diag2()
	{
		switch(check_diag2_freq())
		{
			case 1:
			for(int i = 0, j=2; i < 3; i++,j--)
			{
				if(TestforBlocking(i, j) == true)
				{
					board.addMark(i, j, mark);
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Test if the tile at row, and col is empty.
	 * 
	 * @param row is the row number
	 * @param col is the column number
	 * @return true if the row, and col specified is empty. Else return false
	 */
	private boolean TestforBlocking(int row, int col)
	{		
		if(board.getMark(row, col) == ' ')
			return true;
		else
			return false;
	}
	
	/**
	 * Check the occurrence of the specified row number in the rows where the opponent has placed a mark.
	 * If the specified row number appeared twice inside of the ArrayList "row_location", then there is possibility
	 * that the opponent is close to winning.
	 *  
	 * @param i specified the row number to be checked against
	 * @return -1 to indicate there is no occurrence of a possible win.
	 * @return 1 to indicate an occurrence has been found.
	 */
	private int check_row_freq(int i)
	{
		int freq = 0;
		
		for(int j = 0; j < row_location.size(); j++)
		{
			if(row_location.get(j) == i)
				freq++;	
			if(freq == 2)
				return 1;
		}
		
		return -1;
	}
	
	/**
	 * Check the occurrence of the specified column number in the column where the opponent has placed a mark.
	 * If the specified column number appeared twice inside of the ArrayList "col_location", then there is possibility
	 * that the opponent is close to winning.
	 *  
	 * @param i specified the column number to be checked against
	 * @return -1 to indicate there is no occurrence of a possible win.
	 * @return 1 to indicate an occurrence has been found.
	 */
	private int check_col_freq(int i)
	{
		int freq = 0;
		
		for(int j = 0; j < col_location.size(); j++)
		{
			if(col_location.get(j) == i)
				freq++;	
			if(freq == 2)
				return 1;
		}
		
		return -1;
	}
	
	/**
	 * Check the occurrence of diagonal placement by the opponent where row and column number are the same.
	 * If the said occurrence appeared twice, then there is possibility that the opponent is close to winning.
	 * 
	 * @return -1 to indicate there is no occurrence of a possible win.
	 * @return 1 to indicate an occurrence has been found.
	 */
	private int check_diag1_freq()
	{
		for(int i = 0, freq = 0; i < row_location.size(); i++)
		{
			if(row_location.get(i) == col_location.get(i))
				freq++;
			if(freq == 2)
				return 1;
		}
		return -1;
	}
	
	/**
	 * Check the occurrence of diagona2 placement by the opponent where the sum of row and column number are 2.
	 * If the said occurrence appeared twice, then there is possibility that the opponent is close to winning.
	 * 
	 * @return -1 to indicate there is no occurrence of a possible win.
	 * @return 1 to indicate an occurrence has been found.
	 */
	private int check_diag2_freq()
	{
		for(int i = 0, freq = 0; i < row_location.size(); i++)
		{
			if((row_location.get(i) + col_location.get(i)) == 2)
				freq++;
			if(freq == 2)
				return 1;
		}
		return -1;
	}
}
