package exerciseC_D;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Scanner;

public class DB {
	Connection DBcon;
	PreparedStatement mystmt;
	
	DB()
	{
		try
		{
			DBcon = DriverManager.getConnection("jdbc:mysql://localhost:3306/client?useSSL=false", "root", "root");
		}catch(Exception e){ e.printStackTrace(); }
	}
	
	void init_database()
	{
		Scanner textIN;
		try
		{
			textIN = new Scanner(new FileInputStream("clients.txt"));
		}catch(FileNotFoundException e){ System.out.println("File was not found! Exiting."); return; }
		
		try{
			while(textIN.hasNextLine())
			{
				String[] line = textIN.nextLine().split(";"); 
				String sql = "insert into client_info " + " (FirstName, LastName, Address, postalCod, phoneNumber, clientType)" + " values (?, ?, ?, ?, ?, ?)";
				
				mystmt = DBcon.prepareStatement(sql);
				mystmt.setString(1, line[0]);
				mystmt.setString(2, line[1]);
				mystmt.setString(3, line[2]);
				mystmt.setString(4, line[3]);
				mystmt.setString(5, line[4]);
				mystmt.setString(6, line[5]);
				mystmt.execute();
			}		
		}catch(Exception e){ e.printStackTrace(); }
			
		textIN.close();
	}
}
