package exerciseC_D;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import java.sql.*;
import java.util.Scanner;

public class Client_Man_GUI 
{
	DefaultListModel model;
	JPanel search_panel;
	JPanel info_panel;
	JFrame management;
	JTextField entry_fields[] = new JTextField[6];
	JComboBox client_list;
	JList result_list;
	
	ButtonGroup search_type = new ButtonGroup();
	JRadioButton[] type_buttons = new JRadioButton[3];
	
	DB myDB;
	Statement mystatement;
	
	public Client_Man_GUI() { myDB = new DB(); }
	
	void import_query(String variable, String condition)
	{
		String query = "select id,FirstName,LastName,clientType from client_info" + " where "+ variable + "=" + "'" + condition + "' ";
		mystatement = null;
		
		try
		{
			mystatement = myDB.DBcon.createStatement();
			ResultSet set = mystatement.executeQuery(query);
			while(set.next())
			{
				int i = set.getInt("id");
				String first = set.getString("FirstName");
				String last = set.getString("LastName");
				String type = set.getString("clientType");
				model.addElement(i + " . "+ " " + first + " " + last + " " + type);
			}
			model.addElement("<Add a New Row>");
		}
		catch(NullPointerException e)
		{ 
			myDB.init_database();
			import_query(variable, condition);
		}
		catch(SQLException e){ e.printStackTrace(); }
	}
	
	void add_new_row()
	{
		String sql = "insert into client_info " + " (FirstName, LastName, Address, postalCod, phoneNumber, clientType)" + " values (?, ?, ?, ?, ?, ?)";
		try
		{
			myDB.mystmt = myDB.DBcon.prepareStatement(sql);
			for(int i=1; i < entry_fields.length; i++)
				myDB.mystmt.setString(i, entry_fields[i].getText());
			myDB.mystmt.setString(6, (String)client_list.getSelectedItem());
			myDB.mystmt.execute();
		}catch(SQLException e){ e.printStackTrace(); }
	}
	
	void modify_row()
	{
		String query = "select *" + " from client_info" + " where id"+ "=" + "'" + entry_fields[0].getText() + "' ";
		mystatement = null;
		Statement mystatement2 = null;
		
		try
		{
			mystatement = myDB.DBcon.createStatement();
			ResultSet set = mystatement.executeQuery(query);
			String op;
			set.next();
			
			for(int i = 1; i < entry_fields.length;i++)
			{
				if(entry_fields[i].getText().equals(set.getString(i)))
					continue;

				switch(i)
				{
					case 1:
						op = "update client_info" + " set FirstName="+ "'" + entry_fields[i].getText() + "'" + " where id='" + entry_fields[0].getText() + "'";
						mystatement2 = myDB.DBcon.createStatement();
						mystatement2.executeUpdate(op);
					break;
					
					case 2:
						op = "update client_info" + " set LastName="+ "'" + entry_fields[i].getText() + "'" + " where id='" + entry_fields[0].getText() + "'";
						mystatement2 = myDB.DBcon.createStatement();
						mystatement2.executeUpdate(op);
					break;
					
					case 3:
						op = "update client_info" + " set Address="+ "'" + entry_fields[i].getText() + "'" + " where id='" + entry_fields[0].getText() + "'";
						mystatement2 = myDB.DBcon.createStatement();
						mystatement2.executeUpdate(op);
					break;
					
					case 4:
						op = "update client_info" + " set postalCod="+ "'" + entry_fields[i].getText() + "'" + " where id='" + entry_fields[0].getText() + "'";
						mystatement2 = myDB.DBcon.createStatement();
						mystatement2.executeUpdate(op);
					break;
					
					case 5:
						op = "update client_info" + " set phoneNumber="+ "'" + entry_fields[i].getText() + "'" + " where id='" + entry_fields[0].getText() + "'";
						mystatement2 = myDB.DBcon.createStatement();
						mystatement2.executeUpdate(op);
					break;
				}
			}
			
			if(((String)client_list.getSelectedItem()).equals(set.getString(6)))
				return;
			else
			{
				op = "update client_info" + " set clientType="+ "'" + client_list.getSelectedItem() + "'" + " where id='" + entry_fields[0].getText() + "'";
				mystatement2 = myDB.DBcon.createStatement();
				mystatement2.executeUpdate(op);
			}
			
			
		}catch(SQLException e){ e.printStackTrace(); }
	}
	
	void delete_row()
	{
		try
		{
			if(result_list.getSelectedValue().equals("<Add a New Row>"))
				JOptionPane.showMessageDialog(management, "You cannot delete this placeholder for adding a new row!");
			else
			{
				String query = "delete from client_info" + " where id"+ "=" + "'" + entry_fields[0].getText() + "' ";
				mystatement = null;
				
				try
				{
					mystatement = myDB.DBcon.createStatement();
					int response = JOptionPane.showConfirmDialog(management, "Are you sure you want delete this row?", "WARNING!", JOptionPane.YES_NO_OPTION);
					if(response == JOptionPane.YES_OPTION)
					{
						mystatement.executeUpdate(query);
						model.remove(result_list.getSelectedIndex());
					}
				}catch(SQLException e){ e.printStackTrace(); }
			}
		}catch(NullPointerException e){ JOptionPane.showMessageDialog(management, "Select a row from the search result first!"); }
	}
	
	void populate_to_entry(int id)
	{
		String query = "select * from client_info" + " where id" + "=" + "'" + id + "' ";
		mystatement = null;
		
		try
		{
			mystatement = myDB.DBcon.createStatement();
			ResultSet set = mystatement.executeQuery(query);
			while(set.next())
			{
				entry_fields[0].setText(Integer.toString(set.getInt("id")));
				entry_fields[1].setText(set.getString("FirstName"));
				entry_fields[2].setText(set.getString("LastName"));
				entry_fields[3].setText(set.getString("Address"));
				entry_fields[4].setText(set.getString("postalCod"));
				entry_fields[5].setText(set.getString("phoneNumber"));
				
				if(set.getString("clientType").equals("C"))
					client_list.setSelectedItem("C");
				else if(set.getString("clientType").equals("R"))
					client_list.setSelectedItem("R");
			}
		}
		catch(NullPointerException e)
		{ 
			myDB.init_database();
			populate_to_entry(id);
		}
		catch(SQLException e){ e.printStackTrace(); }
		
	}
	
	void create_search_panel()
	{
		JLabel panel_title = new JLabel("Search Clients", JLabel.CENTER);
		panel_title.setFont(new Font("Courier New", Font.BOLD, 16));
		
		JLabel selection_title = new JLabel("Select type of search to be performed:");
		selection_title.setFont(new Font("Courier New", Font.PLAIN, 12));
		type_buttons = radio_buttons_search();
		
		JPanel seearch_box = create_search_field();
		
		JLabel result_title = new JLabel("Search Results");
		result_list = new JList(model=new DefaultListModel());
		model.addElement("<Add a New Row>");
		result_list.addMouseListener(new MouseAdapter() 
		{
			public void mouseClicked(MouseEvent evt)
			{
				JList list = (JList)evt.getSource();
				if(evt.getClickCount() == 1)
				{
					String selected = (String)list.getSelectedValue();
					int ne = -1;
					Scanner scan = new Scanner(selected);
					while(scan.hasNextInt())
						ne = scan.nextInt();
					if(ne == -1)
					{
						for(int i =1; i < entry_fields.length; i++)
							entry_fields[i].setText("");
						entry_fields[0].setText("To be generated by DB Server");
						client_list.setSelectedItem("-");
						scan.close();
						return;
					}
					populate_to_entry(ne);
					scan.close();
				}
			}
		});
		
		JScrollPane scroll_result =new JScrollPane();
		scroll_result.setViewportView(result_list);
		
		search_panel = new JPanel();
		search_panel.setLayout(new BoxLayout(search_panel, BoxLayout.Y_AXIS));
		search_panel.add(panel_title);
		search_panel.add(selection_title);
		for(int i = 0; i < type_buttons.length; i++)
			search_panel.add(type_buttons[i]);
		search_panel.add(seearch_box);
		search_panel.add(result_title);
		search_panel.add(scroll_result);
	}
	
	void create_entry_panel()
	{
		JLabel panel_title = new JLabel("Client Information", JLabel.CENTER);
		panel_title.setFont(new Font("Courier New", Font.BOLD, 16));
		
		info_panel = new JPanel();
		info_panel.setLayout(new BoxLayout(info_panel, BoxLayout.Y_AXIS));
		
		info_panel.add(panel_title);
		info_panel.add(entry_fields());
		
		JPanel client_type =new JPanel(new FlowLayout(FlowLayout.CENTER));
		JLabel label_client = new JLabel("Client Type: ");
		client_list = new JComboBox();
		client_list.addItem("-");
		client_list.addItem("C");
		client_list.addItem("R");
		client_type.add(label_client);
		client_type.add(client_list);
		
		info_panel.add(client_type);
		info_panel.add(entry_panel_buttons());
	}
	
	JPanel entry_fields()
	{
		JLabel entry_labels[] = new JLabel[6];
		JPanel entries[] = new JPanel[6];
		JPanel edit_panel = new JPanel();
		edit_panel.setLayout(new BoxLayout(edit_panel, BoxLayout.Y_AXIS));
		
		for(int i = 0; i < entries.length; i++)
			entries[i]= new JPanel(new FlowLayout(FlowLayout.CENTER));
		
		entry_labels[0] = new JLabel("Client ID");
		entry_labels[1] = new JLabel("First Name");
		entry_labels[2] = new JLabel("Last Name");
		entry_labels[3] = new JLabel("Address");
		entry_labels[4] = new JLabel("Postal Code");
		entry_labels[5] = new JLabel("Phone Number");
		
		for(int i = 0; i < entry_fields.length; i++)
		{
			entry_fields[i] = new JTextField();
			entry_fields[i].setFont(new Font("Courier New", Font.PLAIN, 12));
			entry_fields[i].setPreferredSize(new Dimension(250, 20));
			entries[i].add(entry_labels[i]);
			entries[i].add(entry_fields[i]);
			edit_panel.add(entries[i]);
		}
		
		entry_fields[0].setEditable(false);
		return edit_panel;
		
	}
	
	JPanel entry_panel_buttons()
	{
		JButton save = new JButton("Save");
		JButton delete = new JButton("Delete");
		JButton clear = new JButton("Clear");
		
		ActionListener button_listener = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource() == save)
				{
					switch(check_valid())
					{
						case 1:
							JOptionPane.showMessageDialog(management, "Maximum allowed length exceeded!\nPlease double check your inputs!");
						break;
						
						case 2:
							JOptionPane.showMessageDialog(management, "Field cannot be empty!\nPlease double check your inputs!");
						break;
						
						case 3:
							JOptionPane.showMessageDialog(management, "Invalid Postal Code Format!\nPlease make sure input is in the format of A0A 0A0");
						break;
						
						case 4:
							JOptionPane.showMessageDialog(management, "Invalid Telephone Format!\nPlease make sure input is in the format of 000-000-0000");
						break;
						
						default:
							try
							{
								if(((String)result_list.getSelectedValue()).equals("<Add a New Row>"))
									add_new_row();
								else
									modify_row();
							}catch(NullPointerException e1){ add_new_row(); }
						break;
					}
				}
				else if(e.getSource() == delete)
				{
					delete_row();
				}
				else if(e.getSource() == clear)
				{
					//Clear button will function to clear all of the entry area's content
					for(int i = 1; i < entry_fields.length; i++)
						entry_fields[i].setText("");
					client_list.setSelectedItem("-");
				}
			}
		};
		
		save.addActionListener(button_listener);
		delete.addActionListener(button_listener);
		clear.addActionListener(button_listener);
		
		JPanel buttons = new JPanel();
		buttons.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		buttons.add(save);
		buttons.add(delete);
		buttons.add(clear);
		
		return buttons;
	}
	
	int check_valid()
	{
		if(entry_fields[1].getText().length() > 20 || entry_fields[2].getText().length() > 20 || entry_fields[3].getText().length() > 50
				|| entry_fields[4].getText().length() > 7 || entry_fields[5].getText().length() > 12)
			return 1;
		else if(entry_fields[1].getText().equals("") || entry_fields[2].getText().equals("") || entry_fields[3].getText().equals("") || client_list.getSelectedItem().equals("-"))
			return 2;
		
		String test = entry_fields[4].getText();
		for(int i = 0; i < 4; i++)
		{
			if(i % 2 == 0 && ((int)test.charAt(i) > 90 || (int)test.charAt(i) < 65))
				return 3;
			else if(i % 2 == 1 && i != 3)
			{
				try
				{
					Integer.parseInt((test.charAt(i) + "\0").trim());
				}catch(NumberFormatException e){ return 3; }
			}
			else if(i == 3 && (int)test.charAt(i) != 32)
				return 3;
		}
		for(int i = 4; i < 6; i++)
		{
			if(i % 2 == 1 && ((int)test.charAt(i) > 90 || (int)test.charAt(i) < 65))
				return 3;
			else if(i % 2 == 0)
			{
				try
				{
					Integer.parseInt((test.charAt(i) + "\0").trim());
				}catch(NumberFormatException e){ return 3; }
			}
		}
		
		test = entry_fields[5].getText();
		for(int i = 0; i < test.length(); i++)
		{
			if((i == 3 || i == 7) && test.charAt(i) == '-')
				continue;
			else if((i == 3 || i == 7) && test.charAt(i) != '-')
				return 4;
			else
			{
				try
				{
					Integer.parseInt((test.charAt(i) + "\0").trim());
				}catch(NumberFormatException e){ return 4; }	
			}
		}
		
		return 0;
	}
	
	JPanel create_search_field()
	{
		JTextField search_box = new JTextField();
		search_box.setPreferredSize(new Dimension(200, 20));
		
		JButton search = new JButton("Search");
		JButton clear = new JButton("Clear Result");
		
		ActionListener search_field_listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource() == search)
				{
					if(type_buttons[0].isSelected())
					{
						if(search_box.getText().equals(""))
						{
							JOptionPane.showMessageDialog(management, "The search field cannot be empty!");
							return;
						}
						model.clear();
						import_query("id", search_box.getText());
					}
					else if(type_buttons[1].isSelected())
					{
						if(search_box.getText().equals(""))
						{
							JOptionPane.showMessageDialog(management, "The search field cannot be empty!");
							return;
						}
						model.clear();
						import_query("LastName", search_box.getText());
					}
					else if(type_buttons[2].isSelected())
					{
						if(search_box.getText().equals(""))
						{
							JOptionPane.showMessageDialog(management, "The search field cannot be empty!");
							return;
						}
						model.clear();
						import_query("clientType", search_box.getText());
					}
				}
				else if(e.getSource() == clear)
				{
					search_box.setText("");
					model.clear();
					model.addElement("<Add a New Row>");
				}
			}
		};
		
		search.addActionListener(search_field_listener);
		clear.addActionListener(search_field_listener);
		
		JPanel search_func = new JPanel(new FlowLayout(FlowLayout.CENTER));
		search_func.add(search_box);
		search_func.add(search);
		search_func.add(clear);
		return search_func;
	}
	
	JRadioButton[] radio_buttons_search()
	{
		JRadioButton[] buttons = new JRadioButton[3];
		buttons[0] = new JRadioButton("Client ID");
		buttons[0].setSelected(true);
		buttons[1] = new JRadioButton("Last Name");
		buttons[2] = new JRadioButton("Client Type");
		
		search_type = new ButtonGroup();
		search_type.add(buttons[0]);
		search_type.add(buttons[1]);
		search_type.add(buttons[2]);
		
		return buttons;
	}
	
	void create_gui()
	{
		management = new JFrame("Database Managment System");
		management.setLayout(new FlowLayout());
		management.setSize(new Dimension(1024, 600));
		create_search_panel();
		create_entry_panel();
		management.add(search_panel);
		management.add(info_panel);
		management.setVisible(true);
		management.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static void main(String [] args)
	{
		Client_Man_GUI A = new Client_Man_GUI();
		A.create_gui();
	}
}
