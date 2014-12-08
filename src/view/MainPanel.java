package view;

import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class MainPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JButton btnAdmin;
	private JButton btnTerminate;

	/**
	 * Sets swing objects
	 */
	public MainPanel() {
		setLayout(null);

		btnAdmin = new JButton("Admin");
		btnAdmin.setBounds(0, 24, 100, 25);
		add(btnAdmin);

		btnTerminate = new JButton("Terminate");
		btnTerminate.setBounds(110, 24, 100, 25);
		add(btnTerminate);
	}

	/**
	 * Adds actionlisteners and actioncommands
	 * @param l
	 */
	public void addActionListener(ActionListener l)
	{
		btnAdmin.addActionListener(l);
		btnAdmin.setActionCommand("AdminBtn");
		btnTerminate.addActionListener(l);
		btnTerminate.setActionCommand("TerminateBtn");
	}
}
