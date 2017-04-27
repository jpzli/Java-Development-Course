package exercise3;

public class RandomPlayer extends Player
{
	private RandomGenerator RNG = new RandomGenerator();
	boolean first_turn ;
	
	RandomPlayer(String name, char mark){ super(name, mark); }
	
	@Override
	protected void play(){ this.makeMove(); }
	
	@Override
	protected void makeMove()
	{
		int row = RNG.discrete(0, 2);			
		int column = RNG.discrete(0, 2);
		
		while(board.getMark(row, column) != ' ')
		{
			row = RNG.discrete(0, 2);	
			column = RNG.discrete(0, 2);
		}
		
		System.out.println(name + " is thinking...");
		board.addMark(row, column, mark);
		board.display();
		System.out.println();
	}
}
