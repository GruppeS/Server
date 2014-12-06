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
			if(!qb.selectFrom("calendars").where("calendar", "=", calendar).executeQuery().next()) {
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
		}
	}

	public String shareCalendar(String calendar, String username) {
		try {
			String[] fields = {"calendar"};
			if(qb.selectFrom(fields, "calendars").where("calendar", "=", calendar).executeQuery().next()) {
				String[] fieldsInsert = {"username", "calendar"};
				String[] values = {username, calendar};
				qb.insertInto("usercalendars", fieldsInsert).values(values).execute();
				return "Calendar shared";
			} else {
				return "Calendar does not exist";
			}
		} catch (SQLException e) {
			return "error";
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
		}
	}

	// TO DO
	public String createNote(String username, String calendar, boolean isPublic) {
		try {
			if(!qb.selectFrom("calendars").where("calendar", "=", calendar).executeQuery().next()) {
				if(isPublic)
				{
					String[] keys = {"calendar", "createdBy"};
					String[] values = {calendar, username};
					qb.insertInto("calendar", keys).values(values).execute();
				} else {
					String[] keys = {"calendar", "createdBy", "isPublic"};
					String[] values = {calendar, username, "0"};
					qb.insertInto("calendar", keys).values(values).execute();
				}
				return "Calendar created";
			} else {
				return "Calendar exists";
			}
		} catch (SQLException e) {
			return "error";
		}
	}

	public String authenticate(String username, String password, boolean isAdmin) throws Exception {

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
	}
}
