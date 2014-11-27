package view;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.ActionListener;

public class UserListPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JLabel lblListOfUsers;
	private JTable table;
	private JButton btnAdd;
	private JButton btnDelete;
	private JButton btnBackToMain;

	public UserListPanel() {
		setLayout(null);
		
		lblListOfUsers = new JLabel("List of users");
		lblListOfUsers.setBounds(45, 11, 82, 14);
		add(lblListOfUsers);
		
		table = new JTable();
		table.setBounds(45, 36, 169, 180);
		add(table);
		
		btnAdd = new JButton("Add");
		btnAdd.setBounds(45, 246, 69, 23);
		add(btnAdd);
		
		btnDelete = new JButton("Delete");
		btnDelete.setBounds(151, 246, 63, 23);
		add(btnDelete);
		
		btnBackToMain = new JButton("Back to Main Menu");
		btnBackToMain.setBounds(123, 348, 150, 23);
		add(btnBackToMain);

	}
	public void addActionListener(ActionListener l)
	{
		btnAdd.addActionListener(l);
		btnAdd.setActionCommand("AddBtn");
		btnDelete.addActionListener(l);
		btnDelete.setActionCommand("DeleteBtn");
		btnBackToMain.addActionListener(l);
		btnBackToMain.setActionCommand("btnBackToMain");
	}
}
