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

	/**
	 * Gets two dimensional vector containing users and their status from database
	 * @return Vector<Vector<Object>>
	 */
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
		} finally {
			try {
				crs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return data;
	}

	/**
	 * Adds a user to database if another user with same username doesn't exist
	 * @param username
	 * @param password
	 * @return boolean if succeeded
	 */
	public boolean addUser(String username, String password) {
		boolean succes = false;
		try {
			crs = qb.selectFrom("users").where("username", "=", username).executeQuery();
			if(crs.next()) {
				succes = false;
			} else {
				String[] fields = {"username", "password"};
				String[] values = {username, password};
				qb.insertInto("users", fields).values(values).execute();
				succes = true;
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
		return succes;
	}

	/**
	 * Sets user active/inactive - Admin cannot be deleted
	 * @param username
	 */
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
		} finally {
			try {
				crs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Gets two dimensional vector containing calendars and their status
	 * @return Vector<Vector<Object>>
	 */
	public Vector<Vector<Object>> calendarTable() {
		try {
			data.clear();

			String[] keys = {"calendar", "active"};
			crs = qb.selectFrom(keys, "calendars").all().executeQuery();

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
		} finally {
			try {
				crs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return data;
	}

	/**
	 * Set a calendar active/inactive - Only admin and calendar creator is allowed
	 * @param calendar
	 * @param user
	 */
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
		} finally {
			try {
				crs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Gets two dimensional vector containing eventID, description, status and which calendar it belongs to
	 * @return Vector<Vector<Object>>
	 */
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
		} finally {
			try {
				crs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return data;
	}

	/**
	 * Sets an event active/inactive - Only admin and event creator is allowed
	 * @param eventID
	 * @param user
	 */
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
		} finally {
			try {
				crs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Gets two dimensional vector containing noteID, note text, status and which event it belongs to
	 * @return Vector<Vector<Object>>
	 */
	public Vector<Vector<Object>> notesTable() {
		try {
			String[] keys = {"eventID", "cbsEventID", "noteID", "text", "active"};
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
		} finally {
			try {
				crs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return data;
	}

	/**
	 * Set a note active/inactive on a specific event
	 * @param eventID
	 * @return status
	 */
	public String deleteNote(String eventID) {
		try {
			String tableName = "notes";
			String activeBoolean = "0";
			boolean active;
			boolean eventExists = false;
			boolean cbsEvent = false;

			try {
				Integer.parseInt(eventID);
				cbsEvent = false;
				crs = qb.selectFrom(tableName).where("eventID", "=", eventID).executeQuery();
			} catch(NumberFormatException e) { 
				cbsEvent = true; 
				crs = qb.selectFrom(tableName).where("cbsEventID", "=", eventID).executeQuery();
			}

			if(crs.next()) {
				eventExists = true;
				active = crs.getBoolean("active");

				if(!active) {
					activeBoolean = "1";
				} else {
					activeBoolean = "0";
				}
			}

			if(eventID != null) {
				if(eventExists && cbsEvent) {
					String[] fields = {"active"};
					String[] values = {activeBoolean};
					qb.update(tableName, fields, values).where("cbsEventID", "=", eventID).execute();
					return "CBS note deleted";
				}
				if(eventExists && !cbsEvent){
					String[] fields = {"active"};
					String[] values = {activeBoolean};
					qb.update(tableName, fields, values).where("eventID", "=", eventID).execute();
					return "Custom event note deleted";
				} else {
					return "No event selected";
				}
			} else {
				return "Please specify an event";
			}
		} catch (SQLException e) {
			return "error";
		} finally {
			try {
				crs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
