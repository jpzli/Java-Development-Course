package exercise2;
import java.util.Random;

public class Random_Data implements Runnable 
{
	private int RN;
	
	@Override
	public void run()
	{
		try
		{
			Random rand = new Random();
			RN = rand.nextInt(100) + 1;
			Thread.sleep(1);
		}catch(InterruptedException e)
		{
			System.err.println(e.toString());
		}
	}
	
	public int get_num(){ return RN; }
	
	public static void main(String args[])
	{	
		Random_Data rand1 = new Random_Data();
		Random_Data rand2 = new Random_Data();
		Random_Data rand3 = new Random_Data();
		Random_Data rand4 = new Random_Data();
		Random_Data rand5 = new Random_Data();
		
		Runnable r1 = rand1;
		Runnable r2 = rand2;
		Runnable r3 = rand3;
		Runnable r4 = rand4;
		Runnable r5 = rand5;
		
		Thread t1 = new Thread(r1);
		Thread t2 = new Thread(r2);
		Thread t3 = new Thread(r3);
		Thread t4 = new Thread(r4);
		Thread t5 = new Thread(r5);
		
		t1.start();
		t2.start();
		t3.start();
		t4.start();
		t5.start();
		
		try
		{
			t1.join();
			t2.join();
			t3.join();
			t4.join();
			t5.join(); 
		}catch(InterruptedException e){ System.out.println(e.toString()); }
		
		int sum = rand1.get_num() + rand2.get_num() + rand3.get_num() + rand4.get_num() + rand5.get_num();
		System.out.println("The sum of these randomly generated number is " + sum);
	}
	
}
