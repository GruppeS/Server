package model.calendar;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import model.Model;
import model.UrlReader;
import model.QueryBuild.QueryBuilder;
import JsonClasses.Event;
import JsonClasses.Events;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class CalendarModel extends Model {

	private EncryptUserID e;
	private UrlReader urlRead;
	private Events events;
	private Gson gson;
	private PreparedStatement pstmt;
	private ResultSet rs;
	private QueryBuilder qb;

	public CalendarModel() {
		e = new EncryptUserID();
		urlRead = new UrlReader();
		events = new Events();
		gson = new GsonBuilder().create();
		qb = new QueryBuilder();
	}

	@SuppressWarnings("rawtypes")
	public String getCalendar(String username) {

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

			getCustomEvents(username);

		} catch (IOException e) {
			e.printStackTrace();
		}

		return gson.toJson(events);
	}

	public void getCustomEvents(String username) {		
		try {
			String[] fields = {"userid"};
			rs = qb.selectFrom(fields, "users").where("username", "=", username).executeQuery();

			int userid = -1;

			if(rs.next()) {
				userid = rs.getInt("userid");
			}

			pstmt = doQuery("SELECT * FROM events WHERE calendarid IN (SELECT calendarid FROM calendar WHERE active = true AND (public = true OR calendarid IN (SELECT calendarid FROM usercalendars WHERE userid = ?)))");
			pstmt.setInt(1, userid);
			rs = pstmt.executeQuery();

			while(rs.next()) {
				try {
					String activityid = rs.getString("activityid");
					String eventid = rs.getString("eventid");
					String type = rs.getString("eventType");
					String description = rs.getString("description");
					String location = rs.getString("location");
					String start = rs.getString("start");
					String end = rs.getString("end");

					events.events.add(new Event(activityid, eventid, type, description, location, null, null));

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
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
