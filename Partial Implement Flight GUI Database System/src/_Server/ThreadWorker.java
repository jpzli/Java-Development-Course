package _Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


class ThreadWorker implements Runnable
{
	Socket clientsocket = null;
	PrintWriter out;
	BufferedReader in;
	String type = null;
	Connection DBcon_general;
	PreparedStatement mystmt;
	Statement mystatement;
	
	ThreadWorker(Socket clientsocket, String type) 
	{ 
		this.clientsocket = clientsocket;
		this.type = type;
	}
	
	public void run()
	{
		try
		{
			in = new BufferedReader(new InputStreamReader(clientsocket.getInputStream()));
			out = new PrintWriter(clientsocket.getOutputStream(), true);
			if(type.equals("PASS"))	
				do_pass(in,out);
			else if(type.equals("ADMIN"))
			{

				do_admin(in,out);
			}
			in.close();
			out.close();
		}catch(IOException e){ e.printStackTrace(); }
	}
	
	void do_pass(BufferedReader input, PrintWriter output) throws IOException
	{
		String in[] = input.readLine().split("\\s+");
		if(in[0].equals("Search"))
		{
			try
			{
				Class.forName("com.mysql.jdbc.Driver"); 
				DBcon_general = DriverManager.getConnection("jdbc:mysql://localhost:3306/sys?useSSL=false", "root", "root");
			}catch(Exception e){ e.printStackTrace(); }
			
			String query = "select flight_num,depart_from,destination from flightcat" + " where "+ "depart_from=" + "'" + in[1] 
					+ "' " + "AND destination="+ "'" + in[2] + "' " + "AND date=" + "'" + in[3] + "' " + "AND avaliable>'0'";
			mystatement = null;
			
			try
			{
				mystatement = DBcon_general.createStatement();
				ResultSet set = mystatement.executeQuery(query);
				while(set.next())
				{
					String line = Integer.toString(set.getInt("flight_num")) + " Depart From:" + set.getString("depart_from") 
					+ " Destination:" + set.getString("destination");
					output.println(line);
					DBcon_general = null;
				}
			}catch(SQLException e){ e.printStackTrace(); }
		}
		else if(in[0].equals("More_Info"))
		{
			try
			{
				Class.forName("com.mysql.jdbc.Driver"); 
				DBcon_general = DriverManager.getConnection("jdbc:mysql://localhost:3306/sys?useSSL=false", "root", "root");
			}catch(Exception e){ e.printStackTrace(); }
			
			String query = "select * from flightcat" + " where "+ "flight_num=" + "'" + in[1] + "' ";
			mystatement = null;
			
			try
			{
				mystatement = DBcon_general.createStatement();
				ResultSet set = mystatement.executeQuery(query);
				set.next();
				String line = Integer.toString(set.getInt("flight_num")) + " " + set.getString("depart_from") + " "
						+ set.getString("destination") + " " + set.getString("date") + " " + set.getString("time") + " "
						+ set.getString("duration") + " " + set.getString("avaliable") + " " + set.getDouble("price"); 
				output.println(line);
				DBcon_general = null;
			}catch(SQLException e){ e.printStackTrace(); }
		}
		else if(in[0].equals("Book"))
		{
			try
			{
				Class.forName("com.mysql.jdbc.Driver"); 
				DBcon_general = DriverManager.getConnection("jdbc:mysql://localhost:3306/sys?useSSL=false", "root", "root");
			}catch(Exception e){ e.printStackTrace(); }
			
			String sql = "insert into booked " + " (first_name, last_name, flight_num, flight_source, flight_destination, flight_date, flight_time, flight_duration, price)" + " values (?, ?, ?, ?,?,?, ?, ?, ?)";
			String query = "select * from flightcat" + " where "+ "flight_num=" + "'" + in[1] + "' ";
			
			try
			{
				mystmt = DBcon_general.prepareStatement(sql);
				mystatement = DBcon_general.createStatement();
				ResultSet set = mystatement.executeQuery(query);
				set.next();
				mystmt.setString(1, in[2]);
				mystmt.setString(2, in[3]);
				mystmt.setString(3, in[1]);
				mystmt.setString(4, set.getString("depart_from"));
				mystmt.setString(5, set.getString("destination"));
				mystmt.setString(6, set.getString("date"));
				mystmt.setString(7, set.getString("time"));
				mystmt.setString(8, set.getString("duration"));
				mystmt.setString(9, in[4]);
				mystmt.execute();
				
				query = "update flightcat" + " set avaliable=" + "'" + (set.getInt("avaliable")-1) + "'" + " where flight_num='" + in[1] + "'";
				mystatement.executeUpdate(query);
			}catch(SQLException e){ e.printStackTrace(); }
		}
		else if(in[0].equals("Cancelled"))
			return;
	}

	void do_admin(BufferedReader input, PrintWriter output)throws IOException
	{
		String in[] = input.readLine().split("\\s+");
		if(in[0].equals("CAT_POP"))
		{
			try
			{
				Class.forName("com.mysql.jdbc.Driver"); 
				DBcon_general = DriverManager.getConnection("jdbc:mysql://localhost:3306/sys?useSSL=false", "root", "root");
			}catch(Exception e){ e.printStackTrace(); }
			
			String query = "select * from flightcat" + " where " + "avaliable>'0'";
			mystatement = null;
			
			try
			{
				mystatement = DBcon_general.createStatement();
				ResultSet set = mystatement.executeQuery(query);
				while(set.next())
				{
					String line = Integer.toString(set.getInt("flight_num")) + " Depart From:" + set.getString("depart_from") 
					+ " Destination:" + set.getString("destination");
					output.println(line);
					DBcon_general = null;
				}
			}catch(SQLException e){ e.printStackTrace(); }
		}
		else if(in[0].equals("BOOK_POP"))
		{
			try
			{
				Class.forName("com.mysql.jdbc.Driver"); 
				DBcon_general = DriverManager.getConnection("jdbc:mysql://localhost:3306/sys?useSSL=false", "root", "root");
			}catch(Exception e){ e.printStackTrace(); }
			
			String query = "select * from booked";
			mystatement = null;
			
			try
			{
				mystatement = DBcon_general.createStatement();
				ResultSet set = mystatement.executeQuery(query);
				while(set.next())
				{
					String line = "First Name: " + set.getString(1) + " Last Name: " + set.getString(2) 
					+ " Flight Number: " + Integer.toString(set.getInt(3)) + " Depart From: " + set.getString(4) 
					+ " Destination: " + set.getString(5);
					output.println(line);
					DBcon_general = null;
				}
			}catch(SQLException e){ e.printStackTrace(); }
		}
		else if(in[0].equals("More_Info_Cat"))
		{
			try
			{
				Class.forName("com.mysql.jdbc.Driver"); 
				DBcon_general = DriverManager.getConnection("jdbc:mysql://localhost:3306/sys?useSSL=false", "root", "root");
			}catch(Exception e){ e.printStackTrace(); }
			
			String query = "select * from flightcat" + " where "+ "flight_num=" + "'" + in[1] + "' ";
			mystatement = null;
			
			try
			{
				mystatement = DBcon_general.createStatement();
				ResultSet set = mystatement.executeQuery(query);
				set.next();
				String line = Integer.toString(set.getInt("flight_num")) + " " + set.getString("depart_from") + " "
						+ set.getString("destination") + " " + set.getString("date") + " " + set.getString("time") + " "
						+ set.getString("duration") + " " + set.getString("avaliable") + " " + set.getDouble("price"); 
				output.println(line);
				DBcon_general = null;
			}catch(SQLException e){ e.printStackTrace(); }
		}
		else if(in[0].equals("More_Info_Book"))
		{
			try
			{
				Class.forName("com.mysql.jdbc.Driver"); 
				DBcon_general = DriverManager.getConnection("jdbc:mysql://localhost:3306/sys?useSSL=false", "root", "root");
			}catch(Exception e){ e.printStackTrace(); }
			
			String query = "select * from book" + " where "+ "first_name=" + "'" + in[1] + "' " 
			+ "last_name="+ "'" + in[2] + "' " + "flight_num="+ "'" + in[3] + "' ";
			mystatement = null;
			
			try
			{
				mystatement = DBcon_general.createStatement();
				ResultSet set = mystatement.executeQuery(query);
				set.next();
				String line = set.getString(1) + " " +set.getString(2) + " " + Integer.toString(set.getInt(3)) + " " + 
				set.getString(4)+ " " +set.getString(5)+ " " +set.getString(6)+ " " +set.getString(7)+ " " +
				set.getString(8)+ " " +Double.toString(set.getDouble(9));
				output.println(line);
				DBcon_general = null;
			}catch(SQLException e){ e.printStackTrace(); }
		}
	}
}
