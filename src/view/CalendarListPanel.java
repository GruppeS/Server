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
	private JButton btnDelete;
	private JScrollPane scrollPane;
	private JTable table;

	Vector<Object> columnNames = new Vector<Object>();

	/**
	 * Sets swing objects
	 */
	public CalendarListPanel() {
		setLayout(null);

		btnBackToMain = new JButton("Back to Main Menu");
		btnBackToMain.setBounds(112, 350, 150, 23);
		add(btnBackToMain);

		btnDelete = new JButton("Set active/inactive");
		btnDelete.setBounds(112, 284, 150, 29);
		add(btnDelete);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(36, 35, 305, 238);
		add(scrollPane);
	}

	/**
	 * sets the JTable
	 * @param data
	 */
	public void createTable(Vector<?> data) {
		columnNames = new Vector<Object>();
		columnNames.add("Calendar");
		columnNames.add("Active");
		table = new JTable(data, columnNames);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(table);
	}

	/**
	 * Gets the selected calendar by getting the selected row
	 * @return String selectedCalendar
	 */
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

	/**
	 * Adds actionlisteners and actioncommands
	 * @param l
	 */
	public void addActionListener(ActionListener l)
	{
		btnBackToMain.addActionListener(l);
		btnBackToMain.setActionCommand("btnBackToMain");
		btnDelete.addActionListener(l);
		btnDelete.setActionCommand("btnDelete");
	}
}
