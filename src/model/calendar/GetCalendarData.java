package model.calendar;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import com.google.gson.Gson;

public class GetCalendarData {

	private EncryptUserID e;
	private Events events;
	private Gson gson;

	public GetCalendarData() {
		e = new EncryptUserID();
		events = new Events();
		gson = new Gson();
	}

	public void getDataFromCalendar(String userID) throws Exception {

		String json = readUrl("http://calendar.cbs.dk/events.php/"+userID+"/"+e.crypt(userID)+".json");
		events = gson.fromJson(json, Events.class);
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
