package model.QOTD;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import model.UrlReader;
import model.QueryBuild.QueryBuilder;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class QOTDModel {

	private ArrayList<QOTD> qotdlist = new ArrayList<>();

	UrlReader urlRead = new UrlReader();
	QOTD qotdlist2 = new QOTD(null, null, null);
	QueryBuilder qb = new QueryBuilder();

	private ResultSet resultSet;

	public void saveQuote() {

		/**
		 * getting text from website and putiing into string
		 * Making a new object of JSON, and prints out quote
		 */
		 String json;
		try {
			json = urlRead.readUrl("http://dist-sso.it-kartellet.dk/quote/");


			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(json);

			String quote = (String) jsonObject.get("quote");
			String author = (String) jsonObject.get("author");
			String topic = (String) jsonObject.get("topic");


			String[] keys = {"qotd"};
			String[] keys2 = {quote};


			qb.update("dailyupdate", keys, keys2).where("msg_type", "=", "1").Execute();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Retrieve Quote from a website and put it into a String, 
	 * Afterwards we will make it into a json object so it can be printed out to the client.
	 */
	public String getQuote(){
		String q = "";
		String[] key = {"qotd"};
		try {
			resultSet = qb.selectFrom("dailyupdate").all().ExecuteQuery();
			while(resultSet.next()) {
				q = resultSet.getString("qotd");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return q;
	}

	public void updateQuote(){
		Date date = new Date(); // Current date & time
		long maxTimeNoUpdate = 86400; // Maximum one day with no update

		long date1 = date.getTime();
		long date2 = date.getTime() - maxTimeNoUpdate; // minus 1 hour -- should be fetched from database

		long timeSinceUpdate = date1 - date2; 


		// if more than 1 hour ago, do update
		if(timeSinceUpdate > 864000){
			// return fresh weather data
			saveQuote();	
		} 
	}
}