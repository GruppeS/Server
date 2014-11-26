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
	QOTD qotdlist = new QOTD();
	QueryBuilder qb = new QueryBuilder();

	private ResultSet resultSet;

	public void saveQuote() {
		try {
			String json = urlRead.readUrl("http://dist-sso.it-kartellet.dk/quote/");

			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(json);

			String quote = (String) jsonObject.get("quote");

			String[] keys = {"qotd", "msg_type"};
			String[] key = {quote, "qotd"};

			if(qb.selectFrom("qotd").all().ExecuteQuery().next()){
				qb.update("qotd", keys, key).where("msg_type", "=", "qotd").Execute();
			} else {
				qb.insertInto("qotd", keys).values(key).Execute();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getQuote(){
		String q = null;
		try {
			resultSet = qb.selectFrom("qotd").all().ExecuteQuery();
			if(resultSet.next()) {
				q = resultSet.getString("qotd");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return q;
	}

	public void updateQuote(){
		Date date = new Date();
		long maxTimeNoUpdate = 86400;

		long dateNow = date.getTime();
		long dateLastQuote = 0;
		try {
			resultSet = qb.selectFrom("qotd").all().ExecuteQuery();
			if(resultSet.next()){
				dateLastQuote = Long.parseLong(resultSet.getString("date"));
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		if(dateNow-dateLastQuote > maxTimeNoUpdate){
			saveQuote();
		} 
	}
}
