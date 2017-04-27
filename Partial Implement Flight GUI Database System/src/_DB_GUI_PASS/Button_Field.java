package _DB_GUI_PASS;

import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

class Button_Field {
	JPanel button_panel;
	JButton button;

	Button_Field() 
	{
		button_panel = new JPanel();
		button_panel.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		button = new JButton("Book & Print");
		button.setEnabled(false);
		
		button_panel.add(button);
				
	}
}
