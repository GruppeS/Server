package controller;
import java.sql.SQLException;

import model.QueryBuild.QueryBuilder;

import com.sun.rowset.CachedRowSetImpl;

public class SwitchMethods
{
	QueryBuilder qb = new QueryBuilder();

	private CachedRowSetImpl crs;

	public String getCalendars() {
		return null;
	}
	
	public String getNotes() {
		return null;
	}
	
	public String createCalendar(String username, String calendar, boolean isPublic) {
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
	
	// TO DO
	public String createEvent(String username, String calendar, boolean isPublic) {
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
