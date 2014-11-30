package view;

import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JButton;

public class EventListPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;	
	private JButton btnBackToMain;
	
	public EventListPanel() {
		setLayout(null);
		
		btnBackToMain = new JButton("Back To Main Menu");
		btnBackToMain.setBounds(123, 348, 150, 23);
		add(btnBackToMain);
	}
	public void addActionListener(ActionListener l)
	{
		btnBackToMain.addActionListener(l);
		btnBackToMain.setActionCommand("btnBackToMain");
	}
}
