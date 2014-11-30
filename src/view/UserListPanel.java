package view;

import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class UserListPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JLabel lblListOfUsers;
	private JButton btnAdd;
	private JButton btnDelete;
	private JButton btnBackToMain;
	private JScrollPane scrollPane;
	private JTable table;

	public UserListPanel() {
		setLayout(null);
		
		lblListOfUsers = new JLabel("List of users");
		lblListOfUsers.setBounds(45, 11, 82, 14);
		add(lblListOfUsers);
		
		btnAdd = new JButton("Add");
		btnAdd.setBounds(45, 246, 69, 23);
		add(btnAdd);
		
		btnDelete = new JButton("Delete");
		btnDelete.setBounds(151, 246, 63, 23);
		add(btnDelete);
		
		btnBackToMain = new JButton("Back to Main Menu");
		btnBackToMain.setBounds(123, 348, 150, 23);
		add(btnBackToMain);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(41, 53, 173, 162);
		add(scrollPane);
	}
	
	public void createTable(Vector<?> data)
	{
		Vector<Object> columnNames = new Vector<Object>();
		columnNames.add("User");
		columnNames.add("Active");
		table = new JTable(data, columnNames);
		scrollPane.setViewportView(table);
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
