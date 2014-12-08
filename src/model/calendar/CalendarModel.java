package model.calendar;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import model.Model;
import model.UrlReader;
import model.note.NoteModel;
import JsonClasses.Calendar;
import JsonClasses.Calendars;
import JsonClasses.Event;
import JsonClasses.Events;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class CalendarModel extends Model {

	private EncryptUserID e;
	private UrlReader urlRead;
	private Calendars calendars;
	private Events events;
	private NoteModel noteModel;
	private Gson gson;
	private PreparedStatement pstmt;

	public CalendarModel() {
		e = new EncryptUserID();
		urlRead = new UrlReader();
		calendars = new Calendars();
		events = new Events();
		noteModel = new NoteModel();
		gson = new GsonBuilder().create();
	}

	/**
	 * Reads data from calendar.cbs.dk and deserializes it using gson.
	 * Every date is converted from arraylists into dates using SimpleDateFormat
	 * Finally the method getCustomEvents is called to add all custom events too
	 * @param username
	 * @return events
	 */
	@SuppressWarnings("rawtypes")
	public String getCBSCalendar(String username) {

		String result = urlRead.readUrl("http://calendar.cbs.dk/events.php/"+username+"/"+e.crypt(username)+".json");

		events = gson.fromJson(result, Events.class);

		for(int i = 0; i<events.events.size(); i++) {

			try {
				ArrayList start = events.events.get(i).getStart();
				ArrayList end = events.events.get(i).getEnd();

				String tempStringStart = start.get(0)+"-"+start.get(2)+"-"+Integer.toString(Integer.parseInt((String) start.get(1))+1)+" "+start.get(3)+":"+start.get(4);

				Date tempDateStart = new SimpleDateFormat("yyyy-dd-MM HH:mm").parse(tempStringStart);
				events.events.get(i).setStartdate(tempDateStart);

				String tempStringEnd = end.get(0)+"-"+end.get(2)+"-"+Integer.toString(Integer.parseInt((String) end.get(1))+1)+" "+end.get(3)+":"+end.get(4);

				Date tempDateEnd = new SimpleDateFormat("yyyy-dd-MM HH:mm").parse(tempStringEnd);
				events.events.get(i).setEnddate(tempDateEnd);

			} catch (ParseException e1) {
				e1.printStackTrace();
			}
		}

		getCustomEvents(username, false, null);

		return gson.toJson(events);
	}

	/**
	 * Selects the users events from the database and adds them to the arraylist events in the events class.
	 * Datetimes from the database is made into dates via SimpleDateFormat
	 * The SQL queries has been done using sub queries which the querybuilder does not support. Therefore the method doQuery has been used instead.
	 * @param username
	 * @param onlyCustomEvents
	 * @param fromCalendar
	 * @return custom events
	 */
	public String getCustomEvents(String username, boolean onlyCustomEvents, String fromCalendar) {
		try {
			if(onlyCustomEvents) {
				events.events.clear();
				pstmt = doQuery("SELECT * FROM events WHERE active = true AND calendar = ? AND calendar IN (SELECT calendar FROM calendars WHERE active = true AND (isPublic = true OR calendar IN (SELECT calendar FROM usercalendars WHERE username = ?)))");
				pstmt.setString(1, fromCalendar);
				pstmt.setString(2, username);
				rs = pstmt.executeQuery();
			} else {
				pstmt = doQuery("SELECT * FROM events WHERE active = true AND calendar IN (SELECT calendar FROM calendars WHERE active = true AND (isPublic = true OR calendar IN (SELECT calendar FROM usercalendars WHERE username = ?)))");
				pstmt.setString(1, username);
				rs = pstmt.executeQuery();
			}
			while(rs.next()) {
				try {
					String eventid = rs.getString("eventID");
					String type = rs.getString("eventType");
					String description = rs.getString("description");
					String location = rs.getString("location");
					String start = rs.getString("start");
					String end = rs.getString("end");

					events.events.add(new Event(null, eventid, type, description, location, null, null));

					int size = (events.events.size())-1;

					Date dateStart = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(start);

					Date dateEnd = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(end);
					events.events.get(size).setStartdate(dateStart);
					events.events.get(size).setEnddate(dateEnd);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(onlyCustomEvents) {
			return gson.toJson(events);
		} else {
			events = noteModel.getNotes(events);
			return null;
		}

	}

	/**
	 * Selects all calendars the user has access to and puts them into the calendars arraylist in the calendars class
	 * The calendars class is then serialized to a json object via gson.
	 * The SQL query has been done using sub queries which the querybuilder does not support. Therefore the method doQuery has been used instead.
	 * @param username
	 * @return calendars json
	 */
	public String getCalendars(String username) {
		try {
			pstmt = doQuery("SELECT calendar, createdBy FROM calendars WHERE active = true AND (isPublic = true OR calendar IN (SELECT calendar FROM usercalendars WHERE username = ?))");
			pstmt.setString(1, username);

			rs = pstmt.executeQuery();

			while(rs.next()) {
				String calendar = rs.getString("calendar");
				String createdBy = rs.getString("createdBy");

				calendars.calendars.add(new Calendar(calendar, createdBy, true));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return gson.toJson(calendars);
	}
}
