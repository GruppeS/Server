package controller;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

import model.QueryBuild.QueryBuilder;

public class AdminMethods {

	private QueryBuilder qb = new QueryBuilder();
	private Vector<Vector<Object>> data = new Vector<Vector<Object>>();
	private ResultSet rs;

	public Vector<Vector<Object>> userTable() {
		try {
			String[] keys = {"username", "active"};
			rs = qb.selectFrom(keys, "users").all().executeQuery();

			data.clear();

			while(rs.next()) {
				Vector<Object> row = new Vector<Object>();

				ResultSetMetaData metadata = rs.getMetaData();
				int numberOfColumns = metadata.getColumnCount();

				for(int i = 1; i <= numberOfColumns; i++) {
					row.addElement(rs.getObject(i));
				}
				data.addElement(row);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
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

			rs = qb.selectFrom(fields, table).where("username", "=", username).executeQuery();

			if(rs.next()) {
				active = rs.getBoolean("active");

				if(active == true) {
					activeBoolean = "0";
				} else if (active == false){
					activeBoolean = "1";
				}
			}
			if(!activeBoolean.equals(null) && !username.equals("admin")) {
				String[] values = {activeBoolean};

				qb.update(table, fields, values).where("username", "=", username).execute();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public Vector<Vector<Object>> calendarTable() {
		try {
			String[] keys = {"calendarid", "name", "active"};
			rs = qb.selectFrom(keys, "calendars").all().executeQuery();

			data.clear();

			while(rs.next()) {
				Vector<Object> row = new Vector<Object>();

				ResultSetMetaData metadata = rs.getMetaData();
				int numberOfColumns = metadata.getColumnCount();

				for(int i = 1; i <= numberOfColumns; i++) {
					row.addElement(rs.getObject(i));
				}
				data.addElement(row);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return data;
	}
	
	public void deleteCalendar(String calendarid) {

		boolean active;
		String activeBoolean = null;

		try {
			String table = "calendars";
			String[] fields = {"active"};

			rs = qb.selectFrom(fields, table).where("calendarid", "=", calendarid).executeQuery();

			if(rs.next()) {
				active = rs.getBoolean("active");

				if(active == true) {
					activeBoolean = "0";
				} else if (active == false){
					activeBoolean = "1";
				}
			}
			if(!activeBoolean.equals(null)) {
				String[] values = {activeBoolean};

				qb.update(table, fields, values).where("calendarid", "=", calendarid).execute();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public Vector<Vector<Object>> eventsTable() {
		try {
			String[] keys = {"calendarid", "eventid", "title", "active"};
			rs = qb.selectFrom(keys, "events").all().executeQuery();

			data.clear();

			while(rs.next()) {
				Vector<Object> row = new Vector<Object>();

				ResultSetMetaData metadata = rs.getMetaData();
				int numberOfColumns = metadata.getColumnCount();

				for(int i = 1; i <= numberOfColumns; i++) {
					row.addElement(rs.getObject(i));
				}
				data.addElement(row);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return data;
	}
	
	public void deleteEvent(String eventid) {

		boolean active;
		String activeBoolean = null;

		try {
			String table = "events";
			String[] fields = {"active"};

			rs = qb.selectFrom(fields, table).where("eventid", "=", eventid).executeQuery();

			if(rs.next()) {
				active = rs.getBoolean("active");

				if(active == true) {
					activeBoolean = "0";
				} else if (active == false){
					activeBoolean = "1";
				}
			}
			if(!activeBoolean.equals(null)) {
				String[] values = {activeBoolean};

				qb.update(table, fields, values).where("eventid", "=", eventid).execute();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public Vector<Vector<Object>> notesTable() {
		try {
			String[] keys = {"eventid", "noteid", "text", "active"};
			rs = qb.selectFrom(keys, "notes").all().executeQuery();

			data.clear();

			while(rs.next()) {
				Vector<Object> row = new Vector<Object>();

				ResultSetMetaData metadata = rs.getMetaData();
				int numberOfColumns = metadata.getColumnCount();

				for(int i = 1; i <= numberOfColumns; i++) {
					row.addElement(rs.getObject(i));
				}
				data.addElement(row);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return data;
	}
	
	public void deleteNote(String noteid) {

		boolean active;
		String activeBoolean = null;

		try {
			String table = "notes";
			String[] fields = {"active"};

			rs = qb.selectFrom(fields, table).where("noteid", "=", noteid).executeQuery();

			if(rs.next()) {
				active = rs.getBoolean("active");

				if(active == true) {
					activeBoolean = "0";
				} else if (active == false){
					activeBoolean = "1";
				}
			}
			if(!activeBoolean.equals(null)) {
				String[] values = {activeBoolean};

				qb.update(table, fields, values).where("noteid", "=", noteid).execute();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
