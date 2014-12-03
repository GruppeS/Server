package view;

import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

public class EventListPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;	
	private JButton btnBackToMain;
	private JButton btnDelete;
	private JScrollPane scrollPane;
	private JTable table;
	
	Vector<Object> columnNames = new Vector<Object>();
	
	public EventListPanel() {
		setLayout(null);
		
		btnBackToMain = new JButton("Back To Main Menu");
		btnBackToMain.setBounds(123, 348, 150, 23);
		add(btnBackToMain);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(26, 29, 333, 231);
		add(scrollPane);
		
		btnDelete = new JButton("Set active/inactive");
		btnDelete.setBounds(123, 271, 150, 29);
		add(btnDelete);
	}
	
	public void createTable(Vector<?> data) {
		columnNames = new Vector<Object>();
		columnNames.add("CalendarID");
		columnNames.add("EventID");
		columnNames.add("Event");
		columnNames.add("Active");
		table = new JTable(data, columnNames);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(table);
	}
	
	public String getSelectedEvent() {
		String selectedEvent;
		
		int row = table.getSelectedRow();
		
		if(row!=-1)
		{
			selectedEvent = (table.getValueAt(row, 1)).toString();
		} else {
			selectedEvent = null;
		}
		
		return selectedEvent;
	}
	
	public void addActionListener(ActionListener l)
	{
		btnBackToMain.addActionListener(l);
		btnBackToMain.setActionCommand("btnBackToMain");
		btnDelete.addActionListener(l);
		btnDelete.setActionCommand("btnDelete");
	}
}
