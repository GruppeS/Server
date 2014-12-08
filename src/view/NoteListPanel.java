package view;

import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

public class NoteListPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JButton btnBackToMain;
	private JButton btnDelete;
	private JScrollPane scrollPane;
	private JTable table;

	Vector<Object> columnNames = new Vector<Object>();

	/**
	 * Sets swing objects
	 */
	public NoteListPanel() {
		setLayout(null);

		btnBackToMain = new JButton("Back to Main Menu");
		btnBackToMain.setBounds(123, 348, 150, 23);
		add(btnBackToMain);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 29, 368, 231);
		add(scrollPane);

		btnDelete = new JButton("Set active/inactive");
		btnDelete.setBounds(123, 271, 150, 29);
		add(btnDelete);

	}

	/**
	 * sets the JTable
	 * @param data
	 */
	public void createTable(Vector<?> data) {
		columnNames = new Vector<Object>();
		columnNames.add("EventID");
		columnNames.add("CbsEventID");
		columnNames.add("NoteID");
		columnNames.add("Note");
		columnNames.add("Active");
		table = new JTable(data, columnNames);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(table);
	}

	/**
	 * Gets the selected note by getting the selected row
	 * @return String selectedNote
	 */
	public String getSelectedNote() {
		String selectedNote = null;

		int row = table.getSelectedRow();

		if(row!=-1)
		{
			if(table.getValueAt(row, 0)!=null) {
				selectedNote = table.getValueAt(row, 0).toString();
			}
			if(table.getValueAt(row, 1)!=null) {
				selectedNote = table.getValueAt(row, 1).toString();
			}
		} else {
			selectedNote = null;
		}
		return selectedNote;
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

