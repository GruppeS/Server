package controller;
import model.Forecast.ForecastModel;
import model.QOTD.QOTDModel;
import model.calendar.CalendarModel;
import JsonClasses.CreateCalendar;
import JsonClasses.DeleteCalendar;
import JsonClasses.QOTD;
import JsonClasses.UserInfo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GiantSwitch {
	private String userID;
	private String answer;
	private boolean authenticated = false;

	public String GiantSwitchMethod(String jsonString) throws Exception {

		ForecastModel forecastModel = new ForecastModel();
		QOTDModel quoteModel = new QOTDModel();
		SwitchMethods SW = new SwitchMethods();
		CalendarModel getCalendarData = new CalendarModel();
		Gson gson = new GsonBuilder().create();

		switch (Determine(jsonString)) {

		default:
			System.out.println("Error in switch");
			answer = null;
			break;

			/************
			 ** COURSES **
			 ************/

		case "importCalendar":
			System.out.println("Recieved importCourse");
			break;

			/**********
			 ** LOGIN **
			 **********/
		case "logIn":
			UserInfo AU = (UserInfo)gson.fromJson(jsonString, UserInfo.class);

			answer = SW.authenticate(AU.getAuthUserEmail(), AU.getAuthUserPassword(), false);

			if(answer.equals("0"))
			{
				authenticated = true;
				userID = AU.getAuthUserEmail();
			}
			break;

			/*************
			 ** CALENDAR **
			 *************/
		case "createCalendar":
			CreateCalendar CC = (CreateCalendar)gson.fromJson(jsonString, CreateCalendar.class);
			System.out.println(CC.getCalendarName()+ "Den har lagt det nye ind i klassen");
			answer = SW.createNewCalendar(CC.getUserName(), CC.getCalendarName(), CC.getPublicOrPrivate());
			break;

		case "deleteCalendar":
			DeleteCalendar DC = (DeleteCalendar)gson.fromJson(jsonString, DeleteCalendar.class);
			System.out.println(DC.getCalendarName()+ "Den har lagt det nye ind i klassen");
			answer = SW.deleteCalendar(DC.getUserName(), DC.getCalendarName());
			break;

		case "saveImportedCalendar":
			System.out.println("Recieved saveImportedCalendar");
			break;

		case "getCalendar":
			answer = getCalendarData.getDataFromCalendar(userID);
			break;

		case "getEvents":
			System.out.println("Recieved getEvents");
			break;

		case "createEvent":
			System.out.println("Recieved saveEvent");
			break;

		case "getEventInfo":
			System.out.println("Recieved getEventInfo");
			break;

		case "deleteEvent":
			System.out.println("Recieved deleteEvent");
			break;

		case "saveNote":
			System.out.println("Recieved saveNote");
			break;

		case "getNote":
			System.out.println("Recieved getNote");
			break;

		case "deleteNote":
			System.out.println("Recieved deleteNote");
			break;

			/**********
			 ** QUOTE **
			 **********/
		case "getQuote":
			QOTD qotd = new QOTD();
			quoteModel.updateQuote();
			String quote = quoteModel.getQuote();
			qotd.setQuote(quote);
			answer = gson.toJson(qotd);
			break;

			/************
			 ** WEATHER **
			 ************/
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
				return null;
			}
		} else if (authenticated) {
			if (ID.contains("getEvents")) {
				return "getEvents";
			} else if (ID.contains("getEventInfo")) {
				return "getEventInfo";
			} else if (ID.contains("saveNote")) {
				return "saveNote";
			} else if (ID.contains("getNote")) {
				return "getNote";
			} else if (ID.contains("deleteNote")){
				return "deleteNote";
			}else if  (ID.contains("deleteCalendar")){
				return "deleteCalendar";
			} else if (ID.contains("getForecast")) {
				return "getForecast";
			} else if (ID.contains("saveImportedCalendar")) {
				return "saveImportedCalendar";
			}else if (ID.contains("importCourse")) {
				return "importCourse";
			} else if (ID.contains("exportCourse")) {
				return "exportCourse";
			} else if (ID.contains("getQuote")) {
				return "getQuote";
			} else if (ID.contains("getCalendar")) {
				return "getCalendar";
			} else if (ID.contains("createEvent")) {
				return "createEvent";
			} else if (ID.contains("deleteEvent")) {
				return "deleteEvent"; 
			} else if (ID.contains("createCalendar")) {
				return "createCalendar";
			} else {
				return null;
			}
		} else {
			return null;
		}
	}
}
