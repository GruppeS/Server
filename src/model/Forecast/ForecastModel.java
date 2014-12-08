package model.Forecast;

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

import config.Configurations;

public class ForecastModel {

	UrlReader urlReader = new UrlReader();
	QueryBuilder qb = new QueryBuilder();
	Forecasts forecasts = new Forecasts();
	Configurations config = new Configurations();

	private CachedRowSetImpl crs;

	/**
	 * Reads data from weather server and deserializes it using gson.
	 * The jsonarray is then iterated over so that every day of forecasts gets added to the database.
	 * If forecasts are already present in the database, they will be updated with the new ones
	 */
	@SuppressWarnings("rawtypes")
	public void saveForecast() {

		String result = null;
		String weatherDescription = null;		

		try {			
			result = urlReader.readUrl("http://api.openweathermap.org/data/2.5/forecast/daily?lat=" + config.getWeather_lat() + "&lon=" + config.getWeather_lon() + "&cnt=" + config.getWeather_future_in_days() + "&mode=json&units=metric");

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

				String table = "forecast";
				String[] fields = {"forecastID", "day", "temperature", "summary"};
				String[] values = {count_String, string_date, temperature, weatherDescription};

				if(dataIsPresent){
					qb.update(table, fields, values).where("forecastID", "=", count_String).execute();
				} else {
					qb.insertInto(table, fields).values(values).execute();
				}
				count++;
			} 
		} catch (ParseException ex) {
			ex.printStackTrace();
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Selects forecasts from database and ads them to forecasts arraylist in Forecasts class
	 * @return Forecasts
	 */
	public Forecasts getForecast() {
		try {
			crs = qb.selectFrom("forecast").where("msg_type", "=", "forecast").executeQuery();

			while(crs.next()){
				String date = crs.getString("day");
				String temperature = crs.getString("temperature");
				String weatherDescription = crs.getString("summary");
				forecasts.forecasts.add(new Forecast(date, temperature, weatherDescription));
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

		return forecasts;
	}

	/**
	 * Gets the creation date of forecasts in database and checks if they should be updated according to the Weather_expiration_time in config
	 * if they are outdated the method saveForecast will be called
	 */
	public void updateForecast() {
		Date date = new Date();
		long maxTimeNoUpdate = Long.parseLong(config.getWeather_expiration_time());

		long timeNow = date.getTime()/1000L;
		long timeLastForecast = 0;

		try {
			crs = qb.selectFrom("forecast").all().executeQuery();
			if(crs.next()){
				timeLastForecast = crs.getDate("date").getTime()/1000L;
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

		if(timeNow-timeLastForecast > maxTimeNoUpdate){
			System.out.println("UPDATING FORECAST");
			saveForecast();
		} else {
			System.out.println("FORECAST IS UP TO DATE");
		}
	}
}
