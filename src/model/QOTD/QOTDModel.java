package model.QOTD;

import java.sql.SQLException;
import java.util.Date;

import model.UrlReader;
import model.QueryBuild.QueryBuilder;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.sun.rowset.CachedRowSetImpl;

import config.Configurations;

public class QOTDModel {

	UrlReader urlRead = new UrlReader();
	QueryBuilder qb = new QueryBuilder();
	Configurations config = new Configurations();

	private CachedRowSetImpl crs;

	/**
	 * Reads data from qotd server and deserializes it using gson.
	 * The quote is retrieved from the object and added to the database.
	 * If a qotd is already present in the database, it will be updated with the new one
	 */
	public void saveQuote() {
		try {
			String json = urlRead.readUrl("http://dist-sso.it-kartellet.dk/quote/");

			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(json);

			String quote = (String) jsonObject.get("quote");
			quote = quote.replace("'", "''");

			String[] fields = {"qotd"};
			String[] values = {quote};

			if(qb.selectFrom("qotd").all().executeQuery().next()){
				qb.update("qotd", fields, values).where("msg_type", "=", "qotd").execute();
			} else {
				qb.insertInto("qotd", fields).values(values).execute();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Selects qotd from database and returns it
	 * @return String qotd
	 */
	public String getQuote(){
		String quote = null;
		try {
			crs = qb.selectFrom("qotd").all().executeQuery();

			if(crs.next()) {
				quote = crs.getString("qotd");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				crs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return quote;
	}

	/**
	 * Gets the creation date of qotd in database and checks if it should be updated according to the QOTD_expiration_time in config
	 * if it is outdated the method saveQuote will be called
	 */
	public void updateQuote(){
		Date date = new Date();
		long maxTimeNoUpdate = Long.parseLong(config.getQOTD_expiration_time());

		long timeNow = date.getTime()/1000L;
		long timeLastQuote = 0;

		try {
			crs = qb.selectFrom("qotd").all().executeQuery();
			if(crs.next()){
				timeLastQuote = crs.getDate("date").getTime()/1000L;
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				crs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		if(timeNow-timeLastQuote > maxTimeNoUpdate){
			System.out.println("UPDATING QUOTE");
			saveQuote();
		} else {
			System.out.println("QUOTE IS UP TO DATE");
		}
	}
}
