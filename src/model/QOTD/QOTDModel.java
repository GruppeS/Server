package model.QOTD;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import model.UrlReader;
import model.QueryBuild.QueryBuilder;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class QOTDModel {

	UrlReader urlRead = new UrlReader();
	QueryBuilder qb = new QueryBuilder();

	private ResultSet resultSet;

	public void saveQuote() {
		try {
			String json = urlRead.readUrl("http://dist-sso.it-kartellet.dk/quote/");

			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(json);

			String quote = (String) jsonObject.get("quote");
			quote = quote.replace("'", "''");

			String[] fields = {"qotd"};
			String[] values = {quote};

			if(qb.selectFrom("qotd").all().ExecuteQuery().next()){
				qb.update("qotd", fields, values).where("msg_type", "=", "qotd").Execute();
			} else {
				qb.insertInto("qotd", fields).values(values).Execute();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getQuote(){
		String quote = null;
		try {
			resultSet = qb.selectFrom("qotd").all().ExecuteQuery();
			
			if(resultSet.next()) {
				quote = resultSet.getString("qotd");
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
			resultSet = qb.selectFrom("qotd").all().ExecuteQuery();
			if(resultSet.next()){
				dateLastQuote = resultSet.getDate("date").getTime()/1000L;
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}

		if(dateNow-dateLastQuote > maxTimeNoUpdate){
			saveQuote();
		} 
	}
}
