package _DB_GUI_ADMIN;

import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

class Button_Field {
	JPanel button_panel1;
	JPanel button_panel2;
	JButton[] button = new JButton[4];

	Button_Field() 
	{
		button_panel1 = new JPanel();
		button_panel1.setLayout(new FlowLayout(FlowLayout.CENTER));
		button_panel2 = new JPanel();
		button_panel2.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		button[0] = new JButton("Cancel Flight");
		button[1] = new JButton("Refresh");
		button[2] = new JButton("Add a flight");
		button[3] = new JButton("Batch add from file");
		
		button_panel1.add(button[0]);
		button_panel1.add(button[1]);
		button_panel2.add(button[2]);
		button_panel2.add(button[3]);
	}
}
