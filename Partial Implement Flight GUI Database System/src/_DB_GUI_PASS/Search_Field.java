package _DB_GUI_PASS;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

class Search_Field {
	JPanel search_panel;
	JTextField[] search_box = new JTextField[2];
	ArrayList<JComboBox<String>> date_dropdown = new ArrayList<JComboBox<String>>(3);
	JButton search;
	
	Search_Field()
	{
		search_panel = new JPanel();
		search_panel.setLayout(new BoxLayout(search_panel, BoxLayout.Y_AXIS));
		create_search_panel();
	}
	
	void create_search_panel()
	{	
		JPanel field_panel[] = new JPanel[4];
		field_panel[0] = new JPanel();
		field_panel[1] = new JPanel();
		field_panel[2] = new JPanel();
		field_panel[3] = new JPanel();
		field_panel[0].setLayout(new BoxLayout(field_panel[0], BoxLayout.Y_AXIS));
		field_panel[1].setLayout(new BoxLayout(field_panel[1], BoxLayout.Y_AXIS));
		field_panel[2].setLayout(new BoxLayout(field_panel[2], BoxLayout.Y_AXIS));
		field_panel[3].setLayout(new BoxLayout(field_panel[3], BoxLayout.Y_AXIS));
		
		search_box[0] = new JTextField(20);
		search_box[1] = new JTextField(20);
		
		create_depart_date();
		
		field_panel[0].add(new JLabel("FROM:",SwingConstants.CENTER));
		field_panel[0].add(search_box[0]);
		field_panel[0].setAlignmentX(JPanel.CENTER_ALIGNMENT);
		
		field_panel[1].add(new JLabel("DESTINATION:",SwingConstants.CENTER));
		field_panel[1].add(search_box[1]);
		
		JPanel first_line = new JPanel();
		first_line.setLayout(new BoxLayout(first_line, BoxLayout.X_AXIS));
		first_line.add(field_panel[0]);
		first_line.add(field_panel[1]);
		
		field_panel[2].add(new JLabel("DATE",SwingConstants.CENTER));
		JPanel date_drop = new JPanel();
		date_drop.setLayout(new BoxLayout(date_drop, BoxLayout.X_AXIS));
		date_drop.add(date_dropdown.get(0));
		date_drop.add(new JLabel("/"));
		date_drop.add(date_dropdown.get(1));
		date_drop.add(new JLabel("/"));
		date_drop.add(date_dropdown.get(2));
		field_panel[2].add(date_drop);
		
		search = new JButton("Search");
		
		JPanel sec_line = new JPanel();
		sec_line.setLayout(new BoxLayout(sec_line, BoxLayout.X_AXIS));
		sec_line.add(field_panel[2]);
		sec_line.add(field_panel[3]);
		sec_line.add(search);
		
		search_panel.add(first_line);
		search_panel.add(sec_line);
	}
	
	
	int num_of_days(int month, int year)
	{
		if(((month % 2 == 1) && (month <= 7))|| ((month % 2 == 0) && (month >= 8)))
			return 31;
		else if(((month % 2 == 0) && (month <= 6) && month != 2) || ((month % 2 == 1) && (month >= 9)))
			return 30;
		else if((month == 2) && (year % 4 == 0))
			return 29;
		else
			return 28;
	}
	
	void create_depart_date()
	{
		for(int i = 0; i < 3; i++)
			date_dropdown.add(i, new JComboBox<String>());
		
		date_dropdown.get(0).addItem("MM");
		for(int i = 1; i <= 12; i++)
			date_dropdown.get(0).addItem(Integer.toString(i));
		date_dropdown.get(0).setSelectedIndex(0);
		
		date_dropdown.get(2).addItem("YY");
		//The year drop down list can go up to three years
		for(int i = 2017; i <= 2020; i++)
			date_dropdown.get(2).addItem(Integer.toString(i));
		date_dropdown.get(2).setSelectedIndex(0);
		
		date_dropdown.get(1).addItem("DD");
		date_dropdown.get(1).setSelectedIndex(0);
		ItemListener listener = new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				try
				{
					if((e.getItem().equals(date_dropdown.get(0).getSelectedItem()) && date_dropdown.get(0).getSelectedIndex() == 0))
					{
						date_dropdown.get(1).removeAllItems();
						date_dropdown.get(1).addItem("DD");
						date_dropdown.get(1).setSelectedIndex(0);
					}
					else if((e.getItem().equals(date_dropdown.get(0).getSelectedItem()) && date_dropdown.get(0).getSelectedIndex() != 0))
					{
						date_dropdown.get(1).removeAllItems();
						date_dropdown.get(1).addItem("DD");
						for(int i = 1; 
								i <= num_of_days(date_dropdown.get(0).getSelectedIndex(), Integer.parseInt((String)date_dropdown.get(2).getSelectedItem()))
								; i++)
								date_dropdown.get(1).addItem(Integer.toString(i));
						date_dropdown.get(1).setSelectedIndex(0);
					}
				}catch(NumberFormatException er){ return; }
				
				try
				{
					if((e.getItem().equals(date_dropdown.get(2).getSelectedItem()) && date_dropdown.get(2).getSelectedIndex() == 0))
					{
						date_dropdown.get(1).removeAllItems();
						date_dropdown.get(1).addItem("DD");
						date_dropdown.get(1).setSelectedIndex(0);
					}
					else if((e.getItem().equals(date_dropdown.get(2).getSelectedItem()) && date_dropdown.get(2).getSelectedIndex() != 0))
					{
						date_dropdown.get(1).removeAllItems();
						date_dropdown.get(1).addItem("DD");
						for(int i = 1; 
								i <= num_of_days(date_dropdown.get(0).getSelectedIndex(), Integer.parseInt((String)date_dropdown.get(2).getSelectedItem()))
								; i++)
								date_dropdown.get(1).addItem(Integer.toString(i));
						date_dropdown.get(1).setSelectedIndex(0);
					}
				}catch(NumberFormatException er){ return; }
			}
		};
		
		date_dropdown.get(0).addItemListener(listener);
		date_dropdown.get(1).addItemListener(listener);
		date_dropdown.get(2).addItemListener(listener);
	}
}
