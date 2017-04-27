package exerciseA;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Scanner;
import javax.swing.*;
import javax.swing.text.DefaultCaret;

public class SearchGUI 
{
	private Scanner File_in;
	private BinSearchTree bin_tree;
	private JButton[] buttons=new JButton[3];
	private ActionListener button_listener;
	private JTextArea text;
	
	/**
	 * Read in from the input file specified in the file chooser and each new line to the binrary search tree
	 */
	void read_in()
	{
		try
		{
			while(File_in.hasNextLine())
			{
				//Split the line into multiple strings that was just read in, based on spaces between each string 
				String split[] = File_in.nextLine().split("\\s+");
				bin_tree.insert(split[1], split[2], split[3], split[4]);
			}
		}catch(NullPointerException e){ return; }
	}
	
	/**
	 * Handles the event of launching the File Chooser dialog
	 */
	void open()
	{
		JFileChooser file_choose = new JFileChooser();
		file_choose.setCurrentDirectory(new File(System.getProperty("user.home")));
		
		file_choose.showOpenDialog(new JDialog());
		
		try
		{
			File_in = new Scanner(new FileInputStream(file_choose.getSelectedFile().getAbsolutePath()));
			//File_in = new Scanner(new FileInputStream("input.txt"));
		}catch(FileNotFoundException e){ System.err.println("File was not found in the directionary!"); }
		catch(NullPointerException e){ return; }
		bin_tree = new BinSearchTree();
	}
	
	/**
	 * Method handles the event of adding a new entry into the binary search tree
	 */
	void new_entry()
	{ 
		//Creating a new panel for the input fields using the BoxLayout aligned along the y-axis
		JPanel input_fields = new JPanel();
		input_fields.setLayout(new BoxLayout(input_fields, BoxLayout.Y_AXIS));
		
		//Creating four entry fields to retrieve user inputs on the four parameters
		TextField inputs[] = new TextField[4];
		input_fields.add(new JLabel("ID:", SwingConstants.CENTER));
		input_fields.add(inputs[0] = new TextField(5));
		
		input_fields.add(new JLabel("Faculty:", SwingConstants.CENTER));
		input_fields.add(inputs[1] = new TextField(5));
		
		input_fields.add(new JLabel("Major:", SwingConstants.CENTER));
		input_fields.add(inputs[2] = new TextField(5));
		
		input_fields.add(new JLabel("Year:", SwingConstants.CENTER));
		input_fields.add(inputs[3] = new TextField(5));
		
		//The dialog uses an OK_CANCEL style
		JOptionPane get_input = new JOptionPane();
		int result = get_input.showConfirmDialog(null, input_fields,"New Entry Dialogue", JOptionPane.OK_CANCEL_OPTION);

		if(result == get_input.OK_OPTION)
		{
			bin_tree.insert(inputs[0].getText(), inputs[1].getText(), inputs[2].getText(), inputs[3].getText()); 
			print_out();
		}
	}
	
	/**
	 * Method handles the event of adding find an existing entry with the binrary search tree
	 */
	void find_entry()
	{
		//Panel to contain all of the entry fields
		JPanel input_fields = new JPanel();
		input_fields.setLayout(new BoxLayout(input_fields, BoxLayout.Y_AXIS));
		
		//Create a text field to retrieve user input to generate a search result of which studnet does the ID belongs to
		TextField input;
		input_fields.add(new JLabel("ID:", SwingConstants.CENTER));
		input_fields.add(input = new TextField(5));
		
		JOptionPane get_input = new JOptionPane();
		int result = get_input.showConfirmDialog(null, input_fields,"New Entry Dialogue", JOptionPane.OK_CANCEL_OPTION);
		if(result == get_input.OK_OPTION)
		{
			try
			{
				//If the entry exists the information on the owner of the ID number is display
				Node student = bin_tree.find(bin_tree.root, input.getText());
				JOptionPane.showMessageDialog(input_fields, student.toString(), "Search Result",JOptionPane.PLAIN_MESSAGE);
			}catch(NullPointerException e)
			{
				//If the entry does not exist, which would be caught by the nullpointerexception, 
				//and display a message that the information associated with this ID does not exist
				JOptionPane.showMessageDialog(input_fields, "ID " + input.getText() + "'s information does not exist", "Search Result",JOptionPane.PLAIN_MESSAGE);
			}
		}
	}
	
	/**
	 * Calls the print_tree method in Binary Search Tree Object to print to the textarea of the GUI
	 */
	void print_out()
	{
		StringWriter entries = new StringWriter();
		PrintWriter result = new PrintWriter(entries);
		try
		{
			bin_tree.print_tree(bin_tree.root, result);
		}catch(IOException e){ e.printStackTrace(); }
		catch(NullPointerException e){ return; }
		String content = entries.toString();
		//Set the output of the print_tree from the Binary Search Tree Object to the textarea on the GUI
		text.setText(content);
	}
	
	/**
	 * Setting up a frame to contain the individual panels for the different parts of the GUI
	 */
	void create_gui()
	{
		JFrame frame = new JFrame("Binary Tree GUI");
		frame.setSize(640, 480);
		frame.setLayout(new BorderLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Create a panel to host the buttons of this GUI
		JPanel button_panel = new JPanel();
		//Set the Layout of this panel containing the buttons using the FlowLayout aligned to the center
		button_panel.setLayout(new FlowLayout(FlowLayout.CENTER));

		buttons[0]= new JButton("Open & Create");
		buttons[1]= new JButton("Insert");
		buttons[2]= new JButton("Search Tree");
		
		text = new JTextArea();
		//Set the text area to be uneditable since this is the area to display the result of the Binary Search Tree
		text.setEditable(false);
		
		button_listener = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//If the "Open&Create" button is pressed it will open the file chooser dialog, then read in the text file line by line
				//to be put into the Binary Search Tree, finally it would print out the current content of the tree to the text area
				if(e.getSource() == buttons[0])
				{
					open();
					read_in();
					print_out();
				}
				//If the button "Insert" is pressed then it will launch a new dialog for adding new entries
				else if(e.getSource() == buttons[1])
					new_entry();
				//If the button "Find" is pressed then it will launch a new dialog prompting the user to enter an ID to be searched. 
				else if(e.getSource() == buttons[2])
					find_entry();
			}
		}; 
		
		//Make the text area scrollable since the user can see all the items even though that would be fit into the text area otherwise
		JScrollPane scroll =new JScrollPane();
		scroll.setViewportView(text);
		
		//Add ActionListener to the button so the buttons know what to do when clicked
		buttons[0].addActionListener(button_listener);
		buttons[1].addActionListener(button_listener);
		buttons[2].addActionListener(button_listener);
		
		//Add the buttons to the panel so it can be added to the frame
		button_panel.add(buttons[0]);
		button_panel.add(buttons[2]);
		button_panel.add(buttons[1]);
		
		//Panel that has the buttons would be at the very bottom on the frame
		frame.add(button_panel, BorderLayout.SOUTH);
		//Panel for the Text Area would be in the dead center of the GUI
		frame.add(scroll, BorderLayout.CENTER);
		frame.setVisible(true);
	}
	
	public static void main(String [] args)
	{
		SearchGUI GUI= new SearchGUI();
		GUI.create_gui();
	}
}


