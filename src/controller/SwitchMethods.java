package controller;
import java.sql.SQLException;

import model.QueryBuild.QueryBuilder;

import com.sun.rowset.CachedRowSetImpl;

public class SwitchMethods
{
	QueryBuilder qb = new QueryBuilder();
	
	private CachedRowSetImpl resultSet;

	public String createNewCalendar (String userName, String calendarName, int privatePublic) throws SQLException {
		String stringToBeReturned ="";
		if(authenticateNewCalendar(calendarName) == false)
		{
			addNewCalendar(calendarName, userName, privatePublic);
			stringToBeReturned = "The new calendar has been created!";
		}
		else
		{
			stringToBeReturned = "The new calendar has not been created!";
		}

		return stringToBeReturned;
	}

	public boolean authenticateNewCalendar(String newCalendarName) throws SQLException {
		
		boolean authenticate = false;

		resultSet= qb.selectFrom("calendar").where("name", "=", newCalendarName).executeQuery();

		//("select * from test.calendar where Name = '"+newCalendarName+"';");
		while(resultSet.next())
		{
			authenticate = true;
		}
		return authenticate;
	}

	public void addNewCalendar (String newCalendarName, String userName, int publicOrPrivate) throws SQLException {
		String [] keys = {"Name","active","CreatedBy","PrivatePublic"};
		String [] values = {newCalendarName,"1",userName, Integer.toString(publicOrPrivate)};
		qb.insertInto("calendar", keys).values(values).execute();

		//		doUpdate("insert into test.calendar (Name, Active, CreatedBy, PrivatePublic) VALUES ('"+newCalendarName+"', '1', '"+userName+"', '"+publicOrPrivate+"');");
	}
	/**
	 * Allows the client to delete a calendar
	 * @param userName
	 * @param calendarName
	 * @return
	 */
	public String deleteCalendar (String userName, String calendarName) throws SQLException {
		String stringToBeReturned ="";
		stringToBeReturned = removeCalendar(userName, calendarName);

		return stringToBeReturned;
	}

	public String removeCalendar (String userName, String calendarName) throws SQLException {
		String stringToBeReturend = "";
		String usernameOfCreator ="";
		String calendarExists = "";
		resultSet = qb.selectFrom("Calendar").where("Name", "=", calendarName).executeQuery();

		//				("select * from calendar where Name = '"+calendarName+"';");
		while(resultSet.next()) {
			calendarExists = resultSet.toString();
		}
		if(!calendarExists.equals("")) {
			String [] value = {"CreatedBy"};
			resultSet = qb.selectFrom(value, "Calendar").where("Name", "=", calendarName).executeQuery();
			while(resultSet.next()) {
				usernameOfCreator = resultSet.toString();
				System.out.println(usernameOfCreator);
			}
			if(!usernameOfCreator.equals(userName)) {
				stringToBeReturend = "Only the creator of the calendar is able to delete it.";
			}
			else {
				String [] keys = {"Active"};
				String [] values = {"2"};
				qb.update("Calendar", keys, values).where("Name", "=", calendarName).execute();
				stringToBeReturend = "Calendar has been set inactive";
			}
			stringToBeReturend = resultSet.toString();
		}
		else {
			stringToBeReturend = "The calendar you are trying to delete, does not exists.";
		}

		return stringToBeReturend;
	}

	public String authenticate(String username, String password, boolean isAdmin) throws Exception {

		resultSet = qb.selectFrom("users").where("username", "=", username).executeQuery();

		if (resultSet.next()){
			if(resultSet.getBoolean("active")==true) {
				if(resultSet.getString("password").equals(password)) {
					if((resultSet.getBoolean("isAdmin") == true && isAdmin) || (resultSet.getBoolean("isAdmin") == false && !isAdmin)) {
						return "0"; // returnerer "0" hvis bruger/admin er godkendt
					} else {
						return "4"; // returnerer fejlkoden "4" hvis brugertype ikke stemmer overens med loginplatform
					}
				} else {
					return "3"; // returnerer fejlkoden "3" hvis password ikke matcher
				}
			} else {
				return "2"; // returnerer fejlkoden "2" hvis bruger er sat som inaktiv
			}
		} else {
			return "1"; // returnerer fejlkoden "1" hvis email ikke findes
		}
	}
}
