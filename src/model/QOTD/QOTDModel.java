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

	public void updateQuote(){
		Date date = new Date();
		long maxTimeNoUpdate = Long.parseLong(config.getQOTD_expiration_time());

		long dateNow = date.getTime()/1000L;
		long dateLastQuote = 0;
		
		try {
			crs = qb.selectFrom("qotd").all().executeQuery();
			if(crs.next()){
				dateLastQuote = crs.getDate("date").getTime()/1000L;
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

		if(dateNow-dateLastQuote > maxTimeNoUpdate){
			saveQuote();
		} 
	}
}
