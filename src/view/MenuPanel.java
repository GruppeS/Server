package view;

import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MenuPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private JButton btnCalendarList;
	private JButton btnEventList;
	private JButton btnNoteList;
	private JButton btnUserList;
	private JLabel  lblMainMenu;
	
	
	public MenuPanel() {
		setLayout(null);
			
			lblMainMenu = new JLabel("Main menu");
			lblMainMenu.setBounds(143, 11, 127, 35);
			add(lblMainMenu);
			
			btnCalendarList = new JButton("Calendar list");
			btnCalendarList.setBounds(240, 57, 150, 100);
			add(btnCalendarList);
			
			btnEventList = new JButton("Event List");
			btnEventList.setBounds(10, 57, 150, 100);
			add(btnEventList);
			
			btnNoteList = new JButton("Note list");
			btnNoteList.setBounds(10, 289, 150, 100);
			add(btnNoteList);
			
			btnUserList = new JButton("User list");
			btnUserList.setBounds(240, 289, 150, 100);
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

