package controller;
import model.Forecast.ForecastModel;
import model.QOTD.QOTDModel;
import model.calendar.CalendarModel;
import JsonClasses.Calendar;
import JsonClasses.Event;
import JsonClasses.QOTD;
import JsonClasses.UserInfo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ServerSwitch {
	private String username;
	private String answer;
	private boolean authenticated = false;

	public String GiantSwitchMethod(String jsonString) throws Exception {

		ForecastModel forecastModel = new ForecastModel();
		QOTDModel quoteModel = new QOTDModel();
		AdminMethods adminMethods = new AdminMethods();
		SwitchMethods switchMethods = new SwitchMethods();
		CalendarModel calendarModel = new CalendarModel();
		Gson gson = new GsonBuilder().create();

		switch (Determine(jsonString)) {

		case "error":
			answer = "Not supported by API";
			break;

		case "logIn":
			UserInfo userInfo = (UserInfo)gson.fromJson(jsonString, UserInfo.class);

			String authentication = switchMethods.authenticate(userInfo.getUsername(), userInfo.getPassword(), false);
			
			if(authentication.equals("0"))
			{
				authenticated = true;
				username = userInfo.getUsername();
			}
			
			userInfo.setUsername(null);
			userInfo.setPassword(null);
			userInfo.setAuthenticated(authentication);
			
			answer = gson.toJson(userInfo);
			break;

		case "getCalendars":
			answer = calendarModel.getCalendars(username);
			break;

		case "createCalendar":
			Calendar CC = (Calendar)gson.fromJson(jsonString, Calendar.class);
			answer = switchMethods.createCalendar(username, CC.getCalendarname(), CC.getIsPublic());
			break;

		case "deleteCalendar":
			Calendar DC = (Calendar)gson.fromJson(jsonString, Calendar.class);
			adminMethods.deleteCalendar(DC.getCalendarname(), username);
			answer = "Calendar deleted if user had the rights";
			break;

		case "shareCalendar":
			Calendar SC = (Calendar)gson.fromJson(jsonString, Calendar.class);
			answer = switchMethods.shareCalendar(SC.getCalendarname(), SC.getShareWith());
			break;

		case "getEvents":
			answer = calendarModel.getCBSCalendar(username);
			break;

		case "getCustomEvents":
			Event CustomE = (Event)gson.fromJson(jsonString, Event.class);
			answer = calendarModel.getCustomEvents(username, true, CustomE.getCalendar());
			break;

		case "createEvent":
			Event CE = (Event)gson.fromJson(jsonString, Event.class);
			answer = switchMethods.createEvent(username, CE.getCalendar(), CE.getDescription(), CE.getStartdate(), CE.getEnddate(), CE.getLocation());
			break;

		case "deleteEvent":
			Event DE = (Event)gson.fromJson(jsonString, Event.class);
			adminMethods.deleteEvent(DE.getEventid(), username);
			answer = "Event deleted if user had the rights";
			break;

		case "createNote":
			Event CN = (Event)gson.fromJson(jsonString, Event.class);
			answer = switchMethods.createNote(username, CN.getNote(), CN.getEventid());
			break;

		case "deleteNote":
			Event DN = (Event)gson.fromJson(jsonString, Event.class);
			answer = switchMethods.deleteNote(DN.getEventid());
			break;

		case "getQuote":
			QOTD qotd = new QOTD();
			quoteModel.updateQuote();
			String quote = quoteModel.getQuote();
			qotd.setQuote(quote);
			answer = gson.toJson(qotd);
			break;

		case "getForecast":
			forecastModel.updateForecast();
			answer = gson.toJson(forecastModel.getForecast());
			break;
		}
		return answer;
	}

	public String Determine(String ID) {

		if (!authenticated) {
			if (ID.contains("logIn")) {
				return "logIn";
			} else {
				return "error";
			}
		} else if (authenticated) {
			if (ID.contains("getCalendars")) {
				return "getCalendars";
			} else if (ID.contains("createCalendar")) {
				return "createCalendar";
			} else if (ID.contains("deleteCalendar")) {
				return "deleteCalendar";
			} else if (ID.contains("shareCalendar")) {
				return "shareCalendar";
			} else if (ID.contains("getEvents")) {
				return "getEvents";
			} else if (ID.contains("getCustomEvents")) {
				return "getCustomEvents";
			} else if (ID.contains("createEvent")){
				return "createEvent";
			} else if  (ID.contains("deleteEvent")){
				return "deleteEvent";
			} else if (ID.contains("createNote")) {
				return "createNote";
			}else if (ID.contains("deleteNote")) {
				return "deleteNote";
			} else if (ID.contains("getQuote")) {
				return "getQuote";
			} else if (ID.contains("getForecast")) {
				return "getForecast";
			} else {
				return "error";
			}
		} else {
			return "error";
		}
	}
}
