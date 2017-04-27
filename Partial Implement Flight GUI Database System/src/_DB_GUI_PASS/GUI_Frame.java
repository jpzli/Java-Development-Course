package _DB_GUI_PASS;

import java.awt.BorderLayout;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

class GUI_Frame {
	JFrame frame;
	Button_Field buttons;
	Search_Field search;
	List_View list_cat;
	Socket pass_sock;
	PrintWriter pass_out;
	BufferedReader pass_in;
	TextArea info = new TextArea();
	Double price;
	int id;
	
	GUI_Frame()
	{
		buttons = new Button_Field();
		search = new Search_Field();
		list_cat = new List_View();
		frame  = new JFrame("Passsenger Flight Information Management System");
		frame.setSize(1280, 720);
		frame.setLayout(new BorderLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	void add_component()
	{
		frame.add(search.search_panel, BorderLayout.NORTH);
		frame.add(buttons.button_panel, BorderLayout.SOUTH);
		
		JPanel list = new JPanel();
		list.setLayout(new BoxLayout(list, BoxLayout.Y_AXIS));
		list.add(new JLabel("Search Result:", SwingConstants.CENTER));
		list.add(list_cat.list_panel);
		frame.add(list, BorderLayout.CENTER);
		
		add_action_listeners();
		
		list_cat.result_list.addMouseListener(new MouseAdapter() 
		{
			public void mouseClicked(MouseEvent evt)
			{
				JList<String> list = (JList<String>)evt.getSource();
				if(evt.getClickCount() == 2)
				{
					String selected = (String)list.getSelectedValue();
					Scanner scan = new Scanner(selected);
					int ne = -1;
					while(scan.hasNextInt())
						ne = scan.nextInt();
					if(ne == -1)
						return;
					display_more_info(ne);
				}
				else if(evt.getClickCount() == 1 && !list_cat.result_list.getSelectedValue().equals("Nothing is here right now"))
				{
					String selected = (String)list.getSelectedValue();
					Scanner scan = new Scanner(selected);
					int ne = -1;
					while(scan.hasNextInt())
						ne = scan.nextInt();
					id = ne;
					get_more_info(ne);
					buttons.button.setEnabled(true);
				}
				else if(evt.getClickCount() == 1 && list_cat.result_list.getSelectedValue().equals("Nothing is here right now"))
					buttons.button.setEnabled(false);
			}
		});
		

	}
	
	void add_action_listeners()
	{
		search.search.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource() == search.search)
				{
					if(search.search_box[0].getText().equals("") || search.search_box[1].getText().equals("") ||
							search.date_dropdown.get(0).getSelectedIndex() == 0 || search.date_dropdown.get(1).getSelectedIndex() == 0 || 
							search.date_dropdown.get(2).getSelectedIndex() == 0)
					{
						JOptionPane.showMessageDialog(frame, "The search field cannot be empty!");
						return;
					}
					try
					{
						list_cat.model.clear();
						pass_sock = new Socket("localhost",8090); 
						pass_in = new BufferedReader(new InputStreamReader(pass_sock.getInputStream()));
						pass_out = new PrintWriter(pass_sock.getOutputStream(), true);
						
						pass_out.println("Search " +  search.search_box[0].getText() + " " + search.search_box[1].getText() + " " +
								search.date_dropdown.get(2).getSelectedItem() + "/"+search.date_dropdown.get(0).getSelectedItem()
								+ "/" + search.date_dropdown.get(1).getSelectedItem());
						int i = 0;
						while(true)
						{
							String line = pass_in.readLine();
							if(line == null)

								break;
							list_cat.model.addElement(line);
							i++;
						}
						if(i == 0)
							list_cat.model.addElement("Nothing is here right now");
						pass_in.close();
						pass_out.close();
					}catch(IOException err){ err.printStackTrace(); }
				}
				
			}
		});
		
		buttons.button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(e.getSource() == buttons.button)
				{
					try
					{
						pass_sock = new Socket("localhost",8090); 
						pass_in = new BufferedReader(new InputStreamReader(pass_sock.getInputStream()));
						pass_out = new PrintWriter(pass_sock.getOutputStream(), true);
						booking_information();
						pass_in.close();
						pass_out.close();
					}catch(IOException err){ err.printStackTrace(); }
				}
			}
		});
	}
	
	void get_more_info(int id)
	{
		try
		{
			pass_sock = new Socket("localhost",8090); 
			pass_in = new BufferedReader(new InputStreamReader(pass_sock.getInputStream()));
			pass_out = new PrintWriter(pass_sock.getOutputStream(), true);
		}catch(IOException e){ e.printStackTrace(); }
		
		JScrollPane list_panel = new JScrollPane();
		info.setEditable(false);
		list_panel.setViewportView(info);
		
		pass_out.println("More_Info " + id);
		String Field[] = {"Flight Number: ", "Depart From: ", "Destination: ", "Date of Departure: ", 
				"Time of Departure: ", "Duration of flight: ", "Seats Avaliable: ", "Price: $"};
		try
		{
			String[] incoming = pass_in.readLine().split("\\s+");
			info.setText("");
			for(int i =0; i < Field.length; i++)
				info.append(Field[i] + incoming[i] + "\n");
			price = Double.parseDouble(incoming[7]);
		}catch(IOException e){ e.printStackTrace(); }
		
		try
		{
			pass_in.close();
			pass_out.close();
		}catch(IOException e){ e.printStackTrace(); }
	}
	
	void display_more_info(int id)
	{
		JPanel input_fields = new JPanel();
		input_fields.setLayout(new BoxLayout(input_fields, BoxLayout.Y_AXIS));
		JScrollPane list_panel = new JScrollPane();
		
		get_more_info(id);
		
		input_fields.add(new JLabel("Flight Info:", SwingConstants.CENTER));
		list_panel.setViewportView(info);
		input_fields.add(info);
		
		JOptionPane get_input = new JOptionPane();
		get_input.showMessageDialog(null, input_fields,"Detailed Flight Info", JOptionPane.OK_CANCEL_OPTION);
		
		try
		{
			pass_in.close();
			pass_out.close();
		}catch(IOException e){ e.printStackTrace(); }
	}
	
	void booking_information()
	{	
		JPanel input_fields = new JPanel();
		input_fields.setLayout(new BoxLayout(input_fields, BoxLayout.Y_AXIS));
		JScrollPane list_panel = new JScrollPane();
		
		String current = info.getText();
		
		Double taxes = price*0.07;
		info.append("Taxes at 7%: $" + Double.toString(taxes) + "\n");
		info.append("Total: $" + Double.toString(price + taxes) + "\n");
		
		list_panel.setViewportView(info);
		input_fields.add(new JLabel("First Name:"));
		TextField[] input = new TextField[2];
		input_fields.add(input[0] = new TextField());
		input_fields.add(new JLabel("Last Name:"));
		input_fields.add(input[1] = new TextField());
		input_fields.add(info);
		
		JOptionPane get_input = new JOptionPane();
		int result = get_input.showConfirmDialog(null, input_fields,"Book a Flight", JOptionPane.OK_CANCEL_OPTION);
		if(result  == JOptionPane.OK_OPTION)
		{
			pass_out.println("Book " + Integer.toString(id)  + " " + input[0].getText() + " " + input[1].getText() + " " + Double.toString(price+taxes) );
			try
			{
				PrintWriter writer = new PrintWriter("Recipt.txt", "UTF-8");
				writer.println("First Name: " + input[0].getText() + "\nLast Name: " + input[1].getText() + "\n" + info.getText());
				writer.close();
			}catch(IOException e){ e.printStackTrace(); }
			info.setText("");
			info.append(current);
		}
		else
		{
			pass_out.println("Cancelled");
			info.setText("");
			info.append(current);
		}
	}
	
	
	static public void main(String[] args)
	{
		GUI_Frame GUI = new GUI_Frame();
		GUI.add_component();
	}
}
