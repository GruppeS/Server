package view;

import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Font;
import java.awt.event.ActionEvent;

public class MenuPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private JButton btnCalendarList;
	private JButton btnEventList;
	private JButton btnNoteList;
	private JButton btnUserList;
	private JLabel  lblMainMenu;
	
	
	public MenuPanel() {
		setLayout(null);
			
			lblMainMenu = new JLabel("Admin - Main Menu");
			lblMainMenu.setFont(new Font("Calibri", Font.PLAIN, 19));
			lblMainMenu.setBounds(112, 46, 170, 35);
			add(lblMainMenu);
			
			btnCalendarList = new JButton("Calendar list");
			btnCalendarList.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
				}
			});
			btnCalendarList.setBounds(112, 183, 150, 50);
			add(btnCalendarList);
			
			btnEventList = new JButton("Event List");
			btnEventList.setBounds(112, 244, 150, 50);
			add(btnEventList);
			
			btnNoteList = new JButton("Note list");
			btnNoteList.setBounds(112, 305, 150, 50);
			add(btnNoteList);
			
			btnUserList = new JButton("User list");
			btnUserList.setBounds(112, 122, 150, 50);
			add(btnUserList);
		}
		public void addActionListener(ActionListener l) // metode til at tilf�je actionlisteners og actioncommands til knapper
		{
			btnCalendarList.addActionListener(l); // tilf�jer actionlistener
			btnCalendarList.setActionCommand("btnCalendarList"); // tilf�jer actioncommand
			btnEventList.addActionListener(l); // tilf�jer actionlistener
			btnEventList.setActionCommand("btnEventList"); // tilf�jer actioncommand
			btnNoteList.addActionListener(l); // tilf�jer actionlistener
			btnNoteList.setActionCommand("btnNoteList"); // tilf�jer actioncommand
			btnUserList.addActionListener(l); // tilf�jer actionlistener
			btnUserList.setActionCommand("btnUserList"); // tilf�jer actioncommand
			
		} // metode slutter
	}

