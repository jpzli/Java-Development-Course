package exerciseB;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoGameServer
{
	static final int PORT = 8090;
	
	public static void main(String args[])throws InterruptedException
	{
		ServerSocket serversocket = null;
		Socket clientsocket = null;
		PrintWriter out=null,out1=null;
		BufferedReader in=null;
		Thread t1,t2;
		
		try
		{ 
			serversocket=new ServerSocket(PORT);
			
			clientsocket = serversocket.accept();
			System.out.println("One Player has joined the game.");
			in= new BufferedReader(new InputStreamReader(clientsocket.getInputStream()));
			out = new PrintWriter(clientsocket.getOutputStream(), true);
			Game serve1 = new Game(in, out);
			serve1.out.println("First");

		 
			clientsocket = serversocket.accept();
			System.out.println("Both Player has joined the game.");
			in= new BufferedReader(new InputStreamReader(clientsocket.getInputStream()));
			out1 = new PrintWriter(clientsocket.getOutputStream(), true);
			
			serve1.setOpp(out1);
			t1 = new Thread(serve1);
			t1.start();
			
			Game serve2 = new Game(in, out1);
			serve2.setOpp(out);
			
			serve1.out.println("XREADY");
			serve2.out.println("Second");
			serve2.out.println("OREADY");
			
			t2 = new Thread(serve2);
			t2.start();
			
			t1.join();
			t2.join();
		}
		catch(IOException e){ System.out.println("An I/O Error has occured!"); }
		
		try
		{
			in.close();
			out.close();
			out1.close();
		}catch(IOException e){ e.printStackTrace(); }
	}
}
