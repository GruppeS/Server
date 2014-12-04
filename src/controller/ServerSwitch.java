package controller;
import model.Forecast.ForecastModel;
import model.QOTD.QOTDModel;
import model.calendar.CalendarModel;
import JsonClasses.Calendar;
import JsonClasses.QOTD;
import JsonClasses.UserInfo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ServerSwitch {
	private String userID;
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
			UserInfo AU = (UserInfo)gson.fromJson(jsonString, UserInfo.class);

			answer = switchMethods.authenticate(AU.getAuthUserEmail(), AU.getAuthUserPassword(), false);

			if(answer.equals("0"))
			{
				authenticated = true;
				userID = AU.getAuthUserEmail();
			}
			break;

		case "getCalendars":
			break;
			
		case "createCalendar":
			Calendar CC = (Calendar)gson.fromJson(jsonString, Calendar.class);
			answer = switchMethods.createCalendar(CC.getUsername(), CC.getCalendarname(), CC.getIsPublic());
			break;

		case "deleteCalendar":
			Calendar DC = (Calendar)gson.fromJson(jsonString, Calendar.class);
			adminMethods.deleteCalendar(DC.getCalendarname());
			answer = "Calendar deleted";
			break;

		case "getEvents":
			answer = calendarModel.getCalendar(userID);
			break;

		case "createEvent":
			break;

		case "deleteEvent":
			break;
			
		case "getNotes":
			break;

		case "saveNote":
			break;

		case "deleteNote":
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
			} else if (ID.contains("getEvents")) {
				return "getEvents";
			} else if (ID.contains("createEvent")){
				return "createEvent";
			}else if  (ID.contains("deleteEvent")){
				return "deleteEvent";
			} else if (ID.contains("getNotes")) {
				return "getNotes";
			} else if (ID.contains("saveNote")) {
				return "saveNote";
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
