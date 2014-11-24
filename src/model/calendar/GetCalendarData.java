package model.calendar;

import model.UrlReader;

public class GetCalendarData {

	private EncryptUserID e;
	private UrlReader urlRead;

	public GetCalendarData() {
		e = new EncryptUserID();
		urlRead = new UrlReader();
	}

	public String getDataFromCalendar(String userID) throws Exception {

		String json = urlRead.readUrl("http://calendar.cbs.dk/events.php/"+userID+"/"+e.crypt(userID)+".json");
		
		return json;
	}
}
