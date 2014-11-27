package view;

import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.JButton;

public class CalendarListPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private JButton btnBackToMain;

	public CalendarListPanel() {
		setLayout(null);
		
		btnBackToMain = new JButton("Back to Main Menu");
		btnBackToMain.setBounds(123, 348, 150, 23);
		add(btnBackToMain);

	}
	public void addActionListener(ActionListener l) // metode til at tilføje actionlisteners og actioncommands til knapper
	{
		btnBackToMain.addActionListener(l); // tilføjer actionlistener
		btnBackToMain.setActionCommand("btnBackToMain"); // tilføjer actioncommand
	}
}
