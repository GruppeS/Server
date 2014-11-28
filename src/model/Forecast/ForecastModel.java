package model.forecast;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import model.UrlReader;
import model.QueryBuild.QueryBuilder;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ForecastModel {

	UrlReader urlReader = new UrlReader();
	QueryBuilder qb = new QueryBuilder();	

	private ArrayList<Forecast> forecastList = new ArrayList();
	private ResultSet resultSet;

	public ArrayList<Forecast> requestForecast() {
//		forecastList.add(new Forecast(string_date, temperatur, weatherDescription));
		return forecastList;
	}

	public void saveForecast() {

		String result = null;
		String weatherDescription = null;		
		
		try {			
			result = urlReader.readUrl("http://api.openweathermap.org/data/2.5/forecast/daily?lat=55.681589&lon=12.529092&cnt=14&mode=json&units=metric");

			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(result);

			// get an array from the JSON object
			JSONArray list = (JSONArray) jsonObject.get("list");

			Iterator i = list.iterator();

			boolean dataIsPresent;
			if(qb.selectFrom("forecast").all().ExecuteQuery().next()){
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
				
				String[] keys = {"forecastID", "day", "apparentTemperature", "summary"};
				String[] key = {count_String, string_date, temperature, weatherDescription};

				if(!dataIsPresent){
					qb.insertInto("forecast", keys).values(key).Execute();
				} else {
					qb.update("forecast", keys, key).where("forecastID", "=", count_String).Execute();
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

	// Henter vejrudsigten og gemmer de hentede data i en ArrayList
	public ArrayList<Forecast> getForecast() throws SQLException{
		QueryBuilder qb = new QueryBuilder();
		Date date = new Date(); // Current date & time
		long maxTimeNoUpdate = 3600; // Maximum one hour with no update
		ArrayList<Forecast> forecastFromDB = new ArrayList();

		long date1 = date.getTime();
		long date2 = date.getTime() - maxTimeNoUpdate; // minus 1 hour -- should be fetched from database

		long timeSinceUpdate = 3601; 

		// if more than 1 hour ago, do update
		if(timeSinceUpdate > 3600){
			// return fresh weather data
			return this.requestForecast();
		} else {
			// Query database and fetch existing weather data from db
			ResultSet forecast = null;
			try {
				forecast = qb.selectFrom("forecast").where("msg_type", "=", "forecast").ExecuteQuery();
				// Method to add these ResultSet values to ArrayList needs to be created
				return (ArrayList<Forecast>) forecastFromDB;
			} catch (SQLException e) {
				e.printStackTrace();
			}

			//Do something nice with ResultSet in order to make it into an ArrayList
			try {
				while(forecast.next()){
					//forecastFromDB.add("xx");
					return forecastFromDB;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return null;
		}
	}

}
