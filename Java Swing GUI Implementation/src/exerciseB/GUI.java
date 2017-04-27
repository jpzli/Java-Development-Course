package exerciseB;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.DefaultCaret;

public class GUI {
	private JButton[][] buttons=new JButton[3][3];
	private ActionListener button_listener;
	private JTextArea text;
	private JPanel board_panel;
	private JScrollPane scroll;
	private JPanel message_view; 
	private JPanel entry;
	JTextField name = new JTextField();
	
	volatile String value = "";
	private char mark;
	
	volatile int row = -1;
	volatile int col = -1;
	
	GUI(char mark){ this.mark = mark; }
	
	void create_gui()
	{
		JFrame frame = new JFrame("Tic Tac Toe Game");
		frame.setSize(640, 480);
		frame.setLayout(new BorderLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setup_board_panel();
		setup_text_fields();
	
		JLabel label = new JLabel("Player " + mark + " Board", JLabel.CENTER);
		frame.add(label, BorderLayout.NORTH);
		frame.add(board_panel, BorderLayout.CENTER);
		frame.add(message_view, BorderLayout.EAST);
		frame.add(entry, BorderLayout.SOUTH);
		frame.setVisible(true);
	}
	
	private void setup_board_panel()
	{
		board_panel = new JPanel();
		board_panel.setLayout(new GridLayout(3,3,10,10));
		
		for(int i = 0; i < 3; i++)
			for(int j = 0; j < 3; j++)
				buttons[i][j] = new JButton("");
		
		disable_board();
		
		button_listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource() == buttons[0][0] && (buttons[0][0].getText().equals("")))
				{
					buttons[0][0].setText(mark+"\0");
					row = 0;
					col = 0;
				}
				else if(e.getSource() == buttons[0][1] && (buttons[0][1].getText().equals("")))
				{
					buttons[0][1].setText(mark+"\0");
					row = 0;
					col = 1;
				}
				else if(e.getSource() == buttons[0][2] && (buttons[0][2].getText().equals("")))
				{
					buttons[0][2].setText(mark+"\0");
					row = 0;
					col = 2;
				}
				else if(e.getSource() == buttons[1][0] && (buttons[1][0].getText().equals("")))
				{
					buttons[1][0].setText(mark+"\0");
					row = 1;
					col = 0;
				}
				else if(e.getSource() == buttons[1][1] && (buttons[1][1].getText().equals("")))
				{
					buttons[1][1].setText(mark+"\0");
					row = 1;
					col = 1;
				}
				else if(e.getSource() == buttons[1][2] && (buttons[1][2].getText().equals("")))
				{
					buttons[1][2].setText(mark+"\0");
					row = 1;
					col = 2;
				}
				else if(e.getSource() == buttons[2][0] && (buttons[2][0].getText().equals("")))
				{
					buttons[2][0].setText(mark+"\0");
					row = 2;
					col = 0;
				}
				else if(e.getSource() == buttons[2][1] && (buttons[2][1].getText().equals("")))
				{
					buttons[2][1].setText(mark+"\0");
					row = 2;
					col = 1;
				}
				else if(e.getSource() == buttons[2][2] && (buttons[2][2].getText().equals("")))
				{
					buttons[2][2].setText(mark+"\0");
					row = 2;
					col = 2;
				}
			}
		}; 
		
		for(int i = 0; i < 3; i++)
			for(int j = 0; j < 3; j++)
				buttons[i][j].addActionListener(button_listener);
		
		board_panel.add(buttons[0][0]);
		board_panel.add(buttons[0][1]);
		board_panel.add(buttons[0][2]);
		board_panel.add(buttons[1][0]);
		board_panel.add(buttons[1][1]);
		board_panel.add(buttons[1][2]);
		board_panel.add(buttons[2][0]);
		board_panel.add(buttons[2][1]);
		board_panel.add(buttons[2][2]);
		board_panel.setAlignmentX(JPanel.CENTER_ALIGNMENT);
		board_panel.setAlignmentY(JPanel.CENTER_ALIGNMENT);
	}
	
	private void setup_text_fields()
	{
		text = new JTextArea();
		text.setEditable(false);
		
		scroll =new JScrollPane(text);
		text.append("Welcome to the game!\nTo continue, please enter the Player's name and followed by the enter key.\n");
		scroll.setViewportView(text);
		
		message_view = new JPanel(new BorderLayout());
		message_view.add(scroll, BorderLayout.CENTER);
		message_view.add(new JLabel("Message View"), BorderLayout.NORTH);
		message_view.setAlignmentX(JPanel.CENTER_ALIGNMENT);
		message_view.setAlignmentY(JPanel.CENTER_ALIGNMENT);
		
		name.setPreferredSize(new Dimension(100, 20));
		name.setText("");
		entry = new JPanel(new FlowLayout());
		entry.add(new JLabel("Player Name"));
		entry.add(name);
		
		KeyListener enter = new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {	}
			
			@Override
			public void keyReleased(KeyEvent e) { }
			
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == 10) 
				{ 
					name.setEditable(false);
					value = name.getText();
				}
			}
		};
		name.addKeyListener(enter);
	}
	
	void enable_board()
	{
		for(int i = 0; i < 3; i++)
			for(int j = 0; j< 3;j++)
				buttons[i][j].setEnabled(true);
	}
	
	void disable_board()
	{
		for(int i = 0; i < 3; i++)
			for(int j = 0; j< 3;j++)
				buttons[i][j].setEnabled(false);
	}
	
	void set_op_mark(int row, int col, char mark){	buttons[row][col].setText(mark+"\0"); }
	
	void append_text(String in){ text.append(in); }

}
