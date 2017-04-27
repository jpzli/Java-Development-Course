package exercise2;

import java.util.Random;

public class Random_Data_Col implements Runnable
{
	private int[] RNs = new int[5];
	private int counter = 0;
	
	@Override
	public void run()
	{
		try
		{
			synchronized (RNs) 
			{
				Random rand = new Random();
				RNs[counter] = rand.nextInt(100) + 1;
				counter++;
			}

			Thread.sleep(1);
		}catch(InterruptedException e)
		{
			System.err.println(e.toString());
		}
	}
	
	public int get_num(int index){ return RNs[index]; }
	
	public static void main(String args[])
	{	
		Random_Data_Col rand = new Random_Data_Col();
		
		Runnable r = rand;
		
		Thread t1 = new Thread(r);
		Thread t2 = new Thread(r);
		Thread t3 = new Thread(r);
		Thread t4 = new Thread(r);
		Thread t5 = new Thread(r);
		
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
		
		int sum = 0;
		for(int i = 0; i < 5; i++)
		{
			System.out.println("The number at index " + i + " is " + rand.get_num(i));
			sum += rand.get_num(i);
		}

		System.out.println("The sum of these randomly generated number is " + sum);
	}

}
