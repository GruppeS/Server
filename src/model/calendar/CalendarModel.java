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
import com.sun.rowset.CachedRowSetImpl;

public class CalendarModel extends Model {

	private EncryptUserID e;
	private UrlReader urlRead;
	private Events events;
	private Gson gson;
	private PreparedStatement pstmt;
	private CachedRowSetImpl crs;
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

					String tempStringStart = start.get(0)+"-"+start.get(2)+"-"+start.get(1)+" "+start.get(3)+":"+start.get(4);
					Date tempDateStart;


					tempDateStart = new SimpleDateFormat("yyyy-dd-MM HH:mm").parse(tempStringStart);

					events.getEvents().get(i).setStartdate(tempDateStart);

					String tempStringEnd = end.get(0)+"-"+end.get(2)+"-"+end.get(1)+" "+end.get(3)+":"+end.get(4);
					Date tempDateEnd;

					tempDateEnd = new SimpleDateFormat("yyyy-dd-MM HH:mm").parse(tempStringEnd);
					events.getEvents().get(i).setEnddate(tempDateEnd);
					
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
			}

//			getCustomEvents(username);

		} catch (IOException e) {
			e.printStackTrace();
		}

		return gson.toJson(events);
	}

	public void getCustomEvents(String username) {		
		try {
			String[] fields = {"userid"};
			crs = qb.selectFrom(fields, "users").where("username", "=", username).executeQuery();

			int userid = crs.getInt("userid");

			pstmt = doQuery("SELECT * FROM events WHERE calendarid IN (SELECT calendarid FROM calendar WHERE active = true AND (public = true OR calendarid IN (SELECT calendarid FROM usercalendars WHERE userid = ?)))");
			pstmt.setInt(1, userid);
			rs = pstmt.executeQuery();

			while(rs.next()) {

				String activityid = rs.getString("activityid");
				String eventid = rs.getString("eventid");
				String type = rs.getString("eventType");
				String title = rs.getString("title");
				String description = rs.getString("description");
				String location = rs.getString("location");
				String createdby = rs.getString("createdBy");
				Date start = rs.getDate("start");
				Date end = rs.getDate("end");

				events.events.add(new Event(activityid, eventid, type, title, description, location, createdby, null, null));
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
