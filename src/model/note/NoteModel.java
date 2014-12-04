package model.note;

import java.sql.SQLException;

import JsonClasses.Note;
import model.Model;
import model.QueryBuild.QueryBuilder;

public class NoteModel extends Model{

	Note notes = new Note(0, null, null, null, 0, 0);
	QueryBuilder qb = new QueryBuilder(); 

	public void CreateNote(
			int noteID,
			String text, 
			String dateTime, 
			String createdBy, 
			int isActive, 
			int eventID)	{

		String nId = String.valueOf(noteID);
		String eId = String.valueOf(eventID);

		String[] fields = {"noteId", "eventId", "createdBy", "text", "dateTime", "active"};
		String[] values = {nId, eId, createdBy, text, dateTime, String.valueOf(isActive)};
		try {
			qb.insertInto("notes", fields).values(values).execute();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Note GetNote (int noteID) throws SQLException{

		try {
			resultSet = qb.selectFrom("notes").where("noteID", "=", String.valueOf(noteID)).executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		while(resultSet.next()){
			notes = new Note(
					resultSet.getInt("noteID"), 
					resultSet.getString("text"), 
					resultSet.getString("dateTime"), 
					resultSet.getString("createdBy"), 
					resultSet.getInt("Active"), 
					noteID);
		}
		return notes;
	}

	public void SaveNote (Note note){

		String text = note.getText();
		String dateTime = note.getDateTime();
		String createdBy = note.getCreatedBy();
		int isActive = note.isActive();

		@SuppressWarnings("unused")
		int eventID = note.getEventID();
		int noteID = note.getNoteID();

		String[] fields = {"eventID", "createdBy", "text", "dateTime", "Active"};
		String[] values = {String.valueOf(noteID), text, dateTime, createdBy, String.valueOf(isActive)};
		qb.update("notes", fields, values).where("noteID", "=", String.valueOf(noteID));
	}
}