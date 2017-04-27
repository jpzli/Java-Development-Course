package _Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


class Server implements Runnable{
	int port = 8050;
	String type = null;
	ServerSocket serversocket = null;
	boolean stopped= false;
	Thread running = null;
	ExecutorService threadpool = Executors.newFixedThreadPool(2);

	Server(int port, String type)
	{ 
		this.port = port;
		this.type = type;
	}
	
	public void run()
	{
		synchronized(this){ running = Thread.currentThread(); }
		open_socket();
		while(!stopped())
		{
			Socket clientsocket = null;
			try{ clientsocket = serversocket.accept(); }
			catch(IOException e)
			{ 
				if(stopped())
				{
					System.out.println("The Server has stopped.");
					break;
				}
				throw new RuntimeException("Error accepting incoming connection",e);
			}
			threadpool.execute(new ThreadWorker(clientsocket, type));
		}
		threadpool.shutdown();
		System.out.println("Server has stopped");
	}

	private void open_socket()
	{
		try
		{
			this.serversocket = new ServerSocket(port);
		}catch(IOException e)
		{
			throw new RuntimeException("Error while trying to open a port.", e);
		}
	}
	
	private synchronized boolean stopped(){ return stopped; }
	
	public synchronized void stop()
	{
		try
		{
			this.serversocket.close();
		}catch(IOException e)
		{
			throw new RuntimeException("Error while trying to close down the server", e);
		}
	}
	
	public static void main(String[] args)
	{
		Server pass = new Server(8090, "PASS");
		Server admin = new Server(8095, "ADMIN");
		new Thread(pass).start();
		new Thread(admin).start();

		try {
		    Thread.sleep(20 * 1000);
		} catch (InterruptedException e) {
		    e.printStackTrace();
		}
//		System.out.println("Stopping Server");
//		pass.stop();
	}
}
