package model.calendar;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class GetCalendarData {

	private EncryptUserID e;

	public GetCalendarData() {
		e = new EncryptUserID();
	}

	public String getDataFromCalendar(String userID) throws Exception {

		String json = readUrl("http://calendar.cbs.dk/events.php/"+userID+"/"+e.crypt(userID)+".json");		
		return json;

	}

	private static String readUrl(String urlString) throws Exception {

		BufferedReader reader = null;
		try {
			URL url = new URL(urlString);
			reader = new BufferedReader(new InputStreamReader(url.openStream()));
			StringBuffer buffer = new StringBuffer();
			int read;
			char[] chars = new char[1024];
			while ((read = reader.read(chars)) != -1)
				buffer.append(chars, 0, read);

			return buffer.toString();
		} finally {
			if (reader != null)
				reader.close();
		}
	}
}
