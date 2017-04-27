package exercise3;

public class HumanPlayer extends Player{
	
	HumanPlayer(String name, char mark){ super(name, mark); }
	
	protected void play(){ this.makeMove(); }
	
	protected void makeMove()
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
		System.out.println();
	}
}
