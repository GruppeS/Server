package view;

import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

public class CalendarListPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private JButton btnBackToMain;
	private JScrollPane scrollPane;
	private JTable table;
	
	Vector<Object> columnNames = new Vector<Object>();
	private JButton btnDelete;
	

	public CalendarListPanel() {
		setLayout(null);
		
		btnBackToMain = new JButton("Back to Main Menu");
		btnBackToMain.setBounds(112, 350, 150, 23);
		add(btnBackToMain);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(64, 40, 242, 183);
		add(scrollPane);
		
		btnDelete = new JButton("Set active/inactive");
		btnDelete.setBounds(112, 234, 150, 29);
		add(btnDelete);

	}
	
	public void createTable(Vector<?> data) {
		columnNames = new Vector<Object>();
		columnNames.add("Calendar");
		columnNames.add("Active");
		table = new JTable(data, columnNames);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(table);
	}
	
	public String getSelectedCalendar() {
		String selectedCalendar;
		
		int row = table.getSelectedRow();
		
		if(row!=-1)
		{
			selectedCalendar = (table.getValueAt(row, 0)).toString();
		} else {
			selectedCalendar = null;
		}
		
		return selectedCalendar;
	}
	
	public void addActionListener(ActionListener l)
	{
		btnBackToMain.addActionListener(l);
		btnBackToMain.setActionCommand("btnBackToMain");
		btnDelete.addActionListener(l);
		btnDelete.setActionCommand("btnDelete");
	}
}
