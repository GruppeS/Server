package model.QOTD;

import java.sql.SQLException;
import java.util.Date;

import model.UrlReader;
import model.QueryBuild.QueryBuilder;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.sun.rowset.CachedRowSetImpl;

public class QOTDModel {

	UrlReader urlRead = new UrlReader();
	QueryBuilder qb = new QueryBuilder();

	private CachedRowSetImpl rs;

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
			rs = qb.selectFrom("qotd").all().executeQuery();
			
			if(rs.next()) {
				quote = rs.getString("qotd");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return quote;
	}

	public void updateQuote(){
		Date date = new Date();
		long maxTimeNoUpdate = 86400;

		long dateNow = date.getTime()/1000L;
		long dateLastQuote = 0;
		
		try {
			rs = qb.selectFrom("qotd").all().executeQuery();
			if(rs.next()){
				dateLastQuote = rs.getDate("date").getTime()/1000L;
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}

		if(dateNow-dateLastQuote > maxTimeNoUpdate){
			saveQuote();
		} 
	}
}
