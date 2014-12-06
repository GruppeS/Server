package model.note;

import java.sql.SQLException;

import model.Model;
import model.QueryBuild.QueryBuilder;
import JsonClasses.Events;

public class NoteModel extends Model{

	QueryBuilder qb = new QueryBuilder();

	public Events getNotes(Events events) {

		for(int i = 0; i < events.events.size(); i++){
			String eventid = events.events.get(i).getEventid();
			try {
				resultSet = qb.selectFrom("notes").where("eventID", "=", eventid).executeQuery();

				if(resultSet.next())
				{
					if(resultSet.getBoolean("active")) {
						events.events.get(i).setNote(resultSet.getString("text"));
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					resultSet.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return events;
	}
}