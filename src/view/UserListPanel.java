package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

public class UserListPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JButton btnAdd;
	private JButton btnDelete;
	private JButton btnBackToMain;
	private JScrollPane scrollPane;
	private JTable table;
	private JLabel lblAddNewUser;
	private JLabel lblUser;
	private JLabel lblPassword;
	private JTextField username;
	private JTextField password;
	private JLabel lblUserExists;
	
	Vector<Object> columnNames = new Vector<Object>();

	public UserListPanel() {
		setLayout(null);
		
		btnAdd = new JButton("Add");
		btnAdd.setBounds(228, 240, 69, 23);
		add(btnAdd);
		
		btnDelete = new JButton("Set active/inactive");
		btnDelete.setBounds(35, 297, 150, 23);
		add(btnDelete);
		
		btnBackToMain = new JButton("Back to Main Menu");
		btnBackToMain.setBounds(123, 348, 150, 23);
		add(btnBackToMain);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(25, 56, 173, 222);
		add(scrollPane);
		
		lblAddNewUser = new JLabel("Add new user:");
		lblAddNewUser.setBounds(228, 65, 80, 14);
		add(lblAddNewUser);
		
		lblUser = new JLabel("Username:");
		lblUser.setBounds(228, 106, 100, 14);
		add(lblUser);
		
		lblPassword = new JLabel("Password:");
		lblPassword.setBounds(228, 172, 127, 14);
		add(lblPassword);
		
		username = new JTextField();
		username.setBounds(228, 131, 127, 20);
		add(username);
		username.setColumns(10);
		
		password = new JTextField();
		password.setBounds(228, 197, 127, 20);
		add(password);
		password.setColumns(10);
		
		lblUserExists = new JLabel("User already exists");
		lblUserExists.setFont(new Font("Calibri", Font.ITALIC, 11));
		lblUserExists.setBounds(228, 286, 162, 14);
		lblUserExists.setForeground(Color.red);
		lblUserExists.setVisible(false);
		add(lblUserExists);
	}
	
	public void createTable(Vector<?> data) {
		columnNames = new Vector<Object>();
		columnNames.add("User");
		columnNames.add("Active");
		table = new JTable(data, columnNames);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(table);
	}
	
	public void userExists() {
		lblUserExists.setVisible(true);
	}
	
	public String getSelectedUser() {
		String selectedUser;
		
		int row = table.getSelectedRow();
		
		if(row!=-1)
		{
			selectedUser = (table.getValueAt(row, 0)).toString();
		} else {
			selectedUser = null;
		}
		
		return selectedUser;
	}
	
	public void reset() {
		lblUserExists.setVisible(false);
		username.setText("");
		password.setText("");
	}
	
	public void addActionListener(ActionListener l) {
		btnAdd.addActionListener(l);
		btnAdd.setActionCommand("btnAdd");
		btnDelete.addActionListener(l);
		btnDelete.setActionCommand("btnDelete");
		btnBackToMain.addActionListener(l);
		btnBackToMain.setActionCommand("btnBackToMain");
	}
	
	public String getUsername() {
		return username.getText();
	}
	public String getPassword() {
		return password.getText();
	}
}
