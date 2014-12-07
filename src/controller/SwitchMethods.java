package controller;
import java.sql.SQLException;
import java.util.Date;

import model.QueryBuild.QueryBuilder;

import com.sun.rowset.CachedRowSetImpl;

public class SwitchMethods
{
	QueryBuilder qb = new QueryBuilder();

	private CachedRowSetImpl crs;

	public String createCalendar(String username, String calendar, boolean isPublic) {
		try {
			crs = qb.selectFrom("calendars").where("calendar", "=", calendar).executeQuery();

			if(!crs.next()) {
				if(isPublic)
				{
					String[] keys = {"calendar", "createdBy"};
					String[] values = {calendar, username};
					qb.insertInto("calendars", keys).values(values).execute();
				} else {
					String[] keys = {"calendar", "createdBy", "isPublic"};
					String[] values = {calendar, username, "0"};
					qb.insertInto("calendars", keys).values(values).execute();
					String[] keys2 = {"username", "calendar"};
					String[] values2 = {username, calendar};
					qb.insertInto("usercalendars", keys2).values(values2).execute();
				}
				return "Calendar created";
			} else {
				return "Calendar exists";
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

	public String shareCalendar(String calendar, String username) {
		try {
			String[] fields = {"calendar"};
			crs = qb.selectFrom(fields, "calendars").where("calendar", "=", calendar).executeQuery();
			if(crs.next()) {
				String[] fieldsInsert = {"username", "calendar"};
				String[] values = {username, calendar};
				qb.insertInto("usercalendars", fieldsInsert).values(values).execute();
				return "Calendar shared";
			} else {
				return "Calendar does not exist";
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

	public String createEvent(String username, String calendar, String description, Date start, Date end, String location) {
		try {
			java.sql.Timestamp sqlDateStart = new java.sql.Timestamp(start.getTime());
			java.sql.Timestamp sqlDateEnd = new java.sql.Timestamp(end.getTime());

			crs = qb.selectFrom("calendars").where("calendar", "=", calendar).executeQuery();
			if(crs.next()) {
				if(crs.getString("createdBy").equals(username)) {
					String[] keys = {"eventType", "description", "start", "end", "location", "createdBy", "calendar"};
					String[] values = {"Userevent", description, sqlDateStart.toString(), sqlDateEnd.toString(), location, username, calendar};
					qb.insertInto("events", keys).values(values).execute();
					return "Event created";
				} else {
					return "Only calendar creator can create events";
				}
			} else {
				return "Calendar does not exist";
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

//	public String createNote(String username, String note, String eventID) {
//		try {
//			String tableName = "notes";
//			boolean cbs = false;
//
//			if(eventID.length()>50) {
//				crs = qb.selectFrom(tableName).where("cbsEventID", "=", eventID).executeQuery();
//				cbs = true;
//			}
//			if(eventID.length()<50){
//				crs = qb.selectFrom(tableName).where("eventID", "=", eventID).executeQuery();
//				cbs = false;
//			}
//
//			if(eventID != null && note != null) {
//				if(!crs.next() && cbs) {
//					String[] fields = {"createdBy", "text", "cbsEventID"};
//					String[] values = {username, note, eventID};
//					qb.insertInto(tableName, fields).values(values).execute();
//					return "CBS note created";
//				} 
//				if(!crs.next() && !cbs) {
//					String[] fields = {"createdBy", "text", "eventID"};
//					String[] values = {username, note, eventID};
//					qb.insertInto(tableName, fields).values(values).execute();
//					return "Custom event note created";
//				}
//				if(crs.next() && cbs) {
//					String[] fields = {"createdBy", "text", "active"};
//					String[] values = {username, note, "1"};
//					qb.update(tableName, fields, values).where("cbsEventID", "=", eventID).execute();
//					return "CBS note updated";
//				}
//				if (crs.next() && !cbs){
//					String[] fields = {"createdBy", "text", "active"};
//					String[] values = {username, note, "1"};
//					qb.update(tableName, fields, values).where("eventID", "=", eventID).execute();
//					return "Custom event note updated";
//				}
//			} else {
//				return "No event selected or no note to create";
//			}
//		} catch (SQLException e) {
//			return "error";
//		} finally {
//			try {
//				crs.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//	}
//
//	public String deleteNote(String eventID) {
//		try {
//			String tableName = "notes";
//			boolean cbs = false;
//
//			if(eventID.length()>50) {
//				crs = qb.selectFrom(tableName).where("cbsEventID", "=", eventID).executeQuery();
//				cbs = true;
//			}
//			if(eventID.length()<50){
//				crs = qb.selectFrom(tableName).where("eventID", "=", eventID).executeQuery();
//				cbs = false;
//			}
//
//			if(eventID != null) {
//				if(crs.next() && cbs) {
//					String[] fields = {"active"};
//					String[] values = {"0"};
//					qb.update(tableName, fields, values).where("cbsEventID", "=", eventID).execute();
//					return "CBS note deleted";
//				}
//				if(crs.next() && !cbs){
//					String[] fields = {"active"};
//					String[] values = {"0"};
//					qb.update(tableName, fields, values).where("eventID", "=", eventID).execute();
//					return "Custom event note deleted";
//				} else {
//					return "No event selected";
//				}
//			}
//		} catch (SQLException e) {
//			return "error";
//		} finally {
//			try {
//				crs.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//	}

	public String authenticate(String username, String password, boolean isAdmin) {

		try {
			crs = qb.selectFrom("users").where("username", "=", username).executeQuery();

			if (crs.next()){
				if(crs.getBoolean("active")==true) {
					if(crs.getString("password").equals(password)) {
						if((crs.getBoolean("isAdmin") == true && isAdmin) || (crs.getBoolean("isAdmin") == false && !isAdmin)) {
							return "0";
						} else {
							return "4";
						}
					} else {
						return "3";
					}
				} else {
					return "2";
				}
			} else {
				return "1";
			}
		} catch (SQLException e) {
			e.printStackTrace();
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
