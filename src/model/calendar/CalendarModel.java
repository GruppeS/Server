package model.calendar;

import java.io.IOException;

import model.UrlReader;

public class CalendarModel {

	private EncryptUserID e;
	private UrlReader urlRead;

	public CalendarModel() {
		e = new EncryptUserID();
		urlRead = new UrlReader();
	}

	public String saveCalendar(String userID) {

		String result = null;

		try {
			result = urlRead.readUrl("http://calendar.cbs.dk/events.php/"+userID+"/"+e.crypt(userID)+".json");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return result;
	}
}
