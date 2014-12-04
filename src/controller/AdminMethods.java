package controller;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

import model.QueryBuild.QueryBuilder;

import com.sun.rowset.CachedRowSetImpl;

public class AdminMethods {

	private QueryBuilder qb = new QueryBuilder();
	private Vector<Vector<Object>> data = new Vector<Vector<Object>>();
	private CachedRowSetImpl crs;

	public Vector<Vector<Object>> userTable() {
		try {
			String[] keys = {"username", "active"};
			crs = qb.selectFrom(keys, "users").all().executeQuery();

			data.clear();

			while(crs.next()) {
				Vector<Object> row = new Vector<Object>();

				ResultSetMetaData metadata = crs.getMetaData();
				int numberOfColumns = metadata.getColumnCount();

				for(int i = 1; i <= numberOfColumns; i++) {
					row.addElement(crs.getObject(i));
				}
				data.addElement(row);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return data;
	}

	public boolean addUser(String username, String password) {
		boolean succes = false;
		try {
			if(qb.selectFrom("users").where("username", "=", username).executeQuery().next()) {
				succes = false;
			} else {
				String[] fields = {"username", "password"};
				String[] values = {username, password};
				qb.insertInto("users", fields).values(values).execute();
				succes = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return succes;
	}

	public void deleteUser(String username) {

		boolean active;
		String activeBoolean = null;

		try {
			String table = "users";
			String[] fields = {"active"};

			crs = qb.selectFrom(fields, table).where("username", "=", username).executeQuery();

			if(crs.next()) {
				active = crs.getBoolean("active");

				if(active) {
					activeBoolean = "0";
				} else if (!active){
					activeBoolean = "1";
				}
			}
			if(!activeBoolean.equals(null) && !username.equals("admin")) {
				String[] values = {activeBoolean};

				qb.update(table, fields, values).where("username", "=", username).execute();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Vector<Vector<Object>> calendarTable() {
		try {
			String[] keys = {"calendar", "active"};
			crs = qb.selectFrom(keys, "calendars").all().executeQuery();

			data.clear();

			while(crs.next()) {
				Vector<Object> row = new Vector<Object>();

				ResultSetMetaData metadata = crs.getMetaData();
				int numberOfColumns = metadata.getColumnCount();

				for(int i = 1; i <= numberOfColumns; i++) {
					row.addElement(crs.getObject(i));
				}
				data.addElement(row);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return data;
	}

	public void deleteCalendar(String calendar, String user) {

		boolean active;
		String activeBoolean = null;

		try {
			String table = "calendars";
			String[] fields = {"active", "createdBy"};
			String[] fieldsUpdate = {"active"};

			crs = qb.selectFrom(fields, table).where("calendar", "=", calendar).executeQuery();

			if(crs.next()) {
				String username = crs.getString("createdBy");

				if(username.equals(user) || user.equals("admin")) {
					active = crs.getBoolean("active");

					if(active) {
						activeBoolean = "0";
					} else if (!active){
						activeBoolean = "1";
					}
					if(!activeBoolean.equals(null)) {
						String[] values = {activeBoolean};

						qb.update(table, fieldsUpdate, values).where("calendar", "=", calendar).execute();
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Vector<Vector<Object>> eventsTable() {
		try {
			String[] keys = {"calendar", "eventID", "description", "active"};
			crs = qb.selectFrom(keys, "events").all().executeQuery();

			data.clear();

			while(crs.next()) {
				Vector<Object> row = new Vector<Object>();

				ResultSetMetaData metadata = crs.getMetaData();
				int numberOfColumns = metadata.getColumnCount();

				for(int i = 1; i <= numberOfColumns; i++) {
					row.addElement(crs.getObject(i));
				}
				data.addElement(row);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return data;
	}

	public void deleteEvent(String eventID, String user) {

		boolean active;
		String activeBoolean = null;

		try {
			String table = "events";
			String[] fields = {"active", "createdBy"};
			String[] fieldsUpdate = {"active"};

			crs = qb.selectFrom(fields, table).where("eventID", "=", eventID).executeQuery();

			if(crs.next()) {
				String username = crs.getString("createdBy");

				if(username.equals(user) || user.equals("admin")) {
					active = crs.getBoolean("active");

					if(active) {
						activeBoolean = "0";
					} else if (!active){
						activeBoolean = "1";
					}
					if(!activeBoolean.equals(null)) {
						String[] values = {activeBoolean};

						qb.update(table, fieldsUpdate, values).where("eventID", "=", eventID).execute();
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Vector<Vector<Object>> notesTable() {
		try {
			String[] keys = {"eventID", "noteID", "text", "active"};
			crs = qb.selectFrom(keys, "notes").all().executeQuery();

			data.clear();

			while(crs.next()) {
				Vector<Object> row = new Vector<Object>();

				ResultSetMetaData metadata = crs.getMetaData();
				int numberOfColumns = metadata.getColumnCount();

				for(int i = 1; i <= numberOfColumns; i++) {
					row.addElement(crs.getObject(i));
				}
				data.addElement(row);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return data;
	}

	public void deleteNote(String noteID, String user) {

		boolean active;
		String activeBoolean = null;

		try {
			String table = "notes";
			String[] fields = {"active", "createdBy"};
			String[] fieldsUpdate = {"active"};

			crs = qb.selectFrom(fields, table).where("noteID", "=", noteID).executeQuery();

			if(crs.next()) {
				String username = crs.getString("createdBy");

				if(username.equals(user) || user.equals("admin")) {
					active = crs.getBoolean("active");

					if(active) {
						activeBoolean = "0";
					} else if (!active){
						activeBoolean = "1";
					}
					if(!activeBoolean.equals(null)) {
						String[] values = {activeBoolean};

						qb.update(table, fieldsUpdate, values).where("noteID", "=", noteID).execute();
					}
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
