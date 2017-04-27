package _DB_GUI_ADMIN;

import java.awt.Dimension;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JScrollPane;

class List_View {
	JScrollPane list_panel;
	volatile DefaultListModel<String> model;
	JList<String> result_list;
	
	List_View() 
	{
		result_list = new JList<String>(model = new DefaultListModel<String>());
		model.addElement("Nothing is here right now. Press Refresh Button to see what is in the DataBase");
		list_panel = new JScrollPane();
		list_panel.setPreferredSize(new Dimension(150,150));
		list_panel.setViewportView(result_list);
	}
}
