package model.note;

import java.sql.SQLException;

import model.QueryBuild.QueryBuilder;
import JsonClasses.Events;

import com.sun.rowset.CachedRowSetImpl;

public class NoteModel {

	CachedRowSetImpl crs;
	QueryBuilder qb = new QueryBuilder();

	/**
	 * Gets all notes from database and then starts comparing them with the eventIDs and cbsEventIDs in the arraylist in the Events class.
	 * If there is a match, the note is added to the event
	 * @param events
	 * @return Events
	 */
	public Events getNotes(Events events) {
		try {
			crs = qb.selectFrom("notes").all().executeQuery();

			String eventID = null;
			String note = null;
			boolean active = false;

			while(crs.next()) {
				eventID = crs.getString("eventID");

				if(eventID == null) {
					eventID = crs.getString("cbsEventID");
				}

				active = crs.getBoolean("active");
				note = crs.getString("text");

				if(active && eventID != null) {
					for(int i = 0; i < events.events.size(); i++){
						if(events.events.get(i).getEventid().equals(eventID)) {
							events.events.get(i).setNote(note);
						}
					}
				}	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				crs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return events;
	}
}