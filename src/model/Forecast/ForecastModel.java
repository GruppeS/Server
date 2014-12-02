package model.Forecast;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.Iterator;

import model.UrlReader;
import model.QueryBuild.QueryBuilder;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import JsonClasses.Forecast;
import JsonClasses.Forecasts;

import com.sun.rowset.CachedRowSetImpl;

public class ForecastModel {

	UrlReader urlReader = new UrlReader();
	QueryBuilder qb = new QueryBuilder();	
	Forecasts forecasts = new Forecasts();
	
	private CachedRowSetImpl rs;

	@SuppressWarnings("rawtypes")
	public void saveForecast() {

		String result = null;
		String weatherDescription = null;		
		
		try {			
			result = urlReader.readUrl("http://api.openweathermap.org/data/2.5/forecast/daily?lat=55.681589&lon=12.529092&cnt=14&mode=json&units=metric");

			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(result);

			JSONArray list = (JSONArray) jsonObject.get("list");

			Iterator i = list.iterator();

			boolean dataIsPresent;
			if(qb.selectFrom("forecast").all().executeQuery().next()){
				dataIsPresent = true;
			} else {
				dataIsPresent = false;
			}
			
			int count = 1;
			
			while (i.hasNext()) {

				JSONObject innerObj = (JSONObject) i.next();

				Date date = new Date((Long) innerObj.get("dt") * 1000L);
				String string_date = date.toString();

				JSONObject temp = (JSONObject) innerObj.get("temp");
				double celsius = Double.parseDouble(temp.get("day").toString());
				String temperature = String.valueOf(celsius);

				JSONArray subList = (JSONArray) innerObj.get("weather");

				Iterator y = subList.iterator();

				while (y.hasNext()) {
					JSONObject childObj = (JSONObject) y.next();
					weatherDescription = (String) childObj.get("description");
				}

				String count_String = Integer.toString(count);
				
				String[] fields = {"forecastID", "day", "temperature", "summary"};
				String[] values = {count_String, string_date, temperature, weatherDescription};

				if(!dataIsPresent){
					qb.insertInto("forecast", fields).values(values).execute();
				} else {
					qb.update("forecast", fields, values).where("forecastID", "=", count_String).execute();
				}
				count++;
			} 
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException ex) {
			ex.printStackTrace();
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Forecasts getForecast() {
		try {
			rs = qb.selectFrom("forecast").where("msg_type", "=", "forecast").executeQuery();
			
			while(rs.next()){
				String date = rs.getString("day");
				String temperature = rs.getString("temperature");
				String weatherDescription = rs.getString("summary");
				forecasts.forecastlist.add(new Forecast(date, temperature, weatherDescription));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return forecasts;
	}

	public void updateForecast() throws SQLException{
		Date date = new Date();
		long maxTimeNoUpdate = 3600;

		long timeNow = date.getTime()/1000L;
		long timeLastForecast = 0;
		
		try {
			rs = qb.selectFrom("forecast").all().executeQuery();
			if(rs.next()){
				timeLastForecast = rs.getDate("date").getTime()/1000L;
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		if(timeNow-timeLastForecast > maxTimeNoUpdate){
			saveForecast();
		}
	}
}
