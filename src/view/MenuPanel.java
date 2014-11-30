package view;

import java.awt.Font;
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

		lblMainMenu = new JLabel("Menu");
		lblMainMenu.setFont(new Font("Calibri", Font.PLAIN, 19));
		lblMainMenu.setBounds(168, 50, 170, 35);
		add(lblMainMenu);

		btnCalendarList = new JButton("Calendar list");
		btnCalendarList.setBounds(120, 183, 150, 50);
		add(btnCalendarList);

		btnEventList = new JButton("Event List");
		btnEventList.setBounds(120, 244, 150, 50);
		add(btnEventList);

		btnNoteList = new JButton("Note list");
		btnNoteList.setBounds(120, 305, 150, 50);
		add(btnNoteList);

		btnUserList = new JButton("User list");
		btnUserList.setBounds(120, 122, 150, 50);
		add(btnUserList);
	}
	
	public void addActionListener(ActionListener l)
	{
		btnCalendarList.addActionListener(l);
		btnCalendarList.setActionCommand("btnCalendarList");
		btnEventList.addActionListener(l);
		btnEventList.setActionCommand("btnEventList");
		btnNoteList.addActionListener(l);
		btnNoteList.setActionCommand("btnNoteList");
		btnUserList.addActionListener(l);
		btnUserList.setActionCommand("btnUserList");
	}
}
