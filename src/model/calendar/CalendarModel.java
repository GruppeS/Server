package model.calendar;

import java.io.IOException;
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

	@SuppressWarnings("rawtypes")
	public String getCBSCalendar(String username) {

		try {
			String result = urlRead.readUrl("http://calendar.cbs.dk/events.php/"+username+"/"+e.crypt(username)+".json");

			events = gson.fromJson(result, Events.class);

			for(int i = 0; i<events.getEvents().size(); i++) {

				try {
					ArrayList start = events.getEvents().get(i).getStart();
					ArrayList end = events.getEvents().get(i).getEnd();

					String tempStringStart = start.get(0)+"-"+start.get(2)+"-"+Integer.toString(Integer.parseInt((String) start.get(1))+1)+" "+start.get(3)+":"+start.get(4);

					Date tempDateStart = new SimpleDateFormat("yyyy-dd-MM HH:mm").parse(tempStringStart);
					events.getEvents().get(i).setStartdate(tempDateStart);

					String tempStringEnd = end.get(0)+"-"+end.get(2)+"-"+Integer.toString(Integer.parseInt((String) end.get(1))+1)+" "+end.get(3)+":"+end.get(4);

					Date tempDateEnd = new SimpleDateFormat("yyyy-dd-MM HH:mm").parse(tempStringEnd);
					events.getEvents().get(i).setEnddate(tempDateEnd);

				} catch (ParseException e1) {
					e1.printStackTrace();
				}
			}

			getCustomEvents(username, false, null);

		} catch (IOException e) {
			e.printStackTrace();
		}

		return gson.toJson(events);
	}

	public String getCustomEvents(String username, boolean onlyCustomEvents, String fromCalendar) {

		try {
			if(onlyCustomEvents) {
				events.events.clear();
				pstmt = doQuery("SELECT * FROM events WHERE active = true AND calendar = ? AND calendar IN (SELECT calendar FROM calendars WHERE active = true AND (isPublic = true OR calendar IN (SELECT calendar FROM usercalendars WHERE username = ?)))");
				pstmt.setString(1, fromCalendar);
				pstmt.setString(2, username);
				resultSet = pstmt.executeQuery();
			} else {
				pstmt = doQuery("SELECT * FROM events WHERE active = true AND calendar IN (SELECT calendar FROM calendars WHERE active = true AND (isPublic = true OR calendar IN (SELECT calendar FROM usercalendars WHERE username = ?)))");
				pstmt.setString(1, username);
				resultSet = pstmt.executeQuery();
			}

			while(resultSet.next()) {
				try {
					String eventid = resultSet.getString("eventID");
					String type = resultSet.getString("eventType");
					String description = resultSet.getString("description");
					String location = resultSet.getString("location");
					String start = resultSet.getString("start");
					String end = resultSet.getString("end");

					events.events.add(new Event(null, eventid, type, description, location, null, null));

					int size = (events.getEvents().size())-1;

					Date dateStart = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(start);

					Date dateEnd = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(end);
					events.getEvents().get(size).setStartdate(dateStart);
					events.getEvents().get(size).setEnddate(dateEnd);

				} catch (ParseException e) {
					e.printStackTrace();
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

		if(onlyCustomEvents) {
			return gson.toJson(events);
		} else {
			events = noteModel.getNotes(events);
			return null;
		}

	}

	public String getCalendars(String username) {
		try {
			pstmt = doQuery("SELECT calendar, createdBy FROM calendars WHERE active = true AND (isPublic = true OR calendar IN (SELECT calendar FROM usercalendars WHERE username = ?))");
			pstmt.setString(1, username);

			resultSet = pstmt.executeQuery();

			while(resultSet.next()) {
				String calendar = resultSet.getString("calendar");
				String createdBy = resultSet.getString("createdBy");

				calendars.calendars.add(new Calendar(calendar, createdBy, true));
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
		return gson.toJson(calendars);
	}
}
