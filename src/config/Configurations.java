package config;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

// Importing json packaes
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Configurations {
	private String host;
	private String port;
	private String username;
	private String dbname;
	private String password;
	private String serverport;
	private String weather_expiration_time;
	private String weather_lat;
	private String weather_lon;
	private String weather_future_in_days;
	private String qotd_expiration_time;

	public Configurations() {
		ReadFile();
	}

	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}

	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public int getServerport() {
		return Integer.parseInt(serverport);
	}
	public void setServerport(String serverport) {
		this.serverport = serverport;
	}

	public String getDbname() {
		return dbname;
	}
	public void setDbname(String dbname) {
		this.dbname = dbname;
	}

	public String getWeather_expiration_time() {
		return weather_expiration_time;
	}
	public void setWeather_expiration_time(String weather_expiration_time) {
		this.weather_expiration_time = weather_expiration_time;
	}

	public String getWeather_lat() {
		return weather_lat;
	}
	public void setWeather_lat(String weather_lat) {
		this.weather_lat = weather_lat;
	}

	public String getWeather_lon() {
		return weather_lon;
	}
	public void setWeather_lon(String weather_lon) {
		this.weather_lon = weather_lon;
	}

	public String getWeather_future_in_days() {
		return weather_future_in_days;
	}
	public void setWeather_future_in_days(String weather_future_in_days) {
		this.weather_future_in_days = weather_future_in_days;
	}
	
	public String getQOTD_expiration_time() {
		return qotd_expiration_time;
	}
	public void setQOTD_expiration_time(String qotd_expiration_time) {
		this.qotd_expiration_time = qotd_expiration_time;
	}

	public void ReadFile() {
		JSONParser jsonParser = new JSONParser();

		try {
			FileReader json = new FileReader("src/config.json");

			Object obj = jsonParser.parse(json);
			JSONObject jsonObject = (JSONObject) obj;

			setHost((String) jsonObject.get("host"));
			setPort((String) jsonObject.get("port"));
			setUsername((String) jsonObject.get("username"));
			setDbname((String) jsonObject.get("dbname"));
			setPassword((String) jsonObject.get("password"));
			setServerport((String) jsonObject.get("serverport"));
			setWeather_expiration_time((String) jsonObject.get("weather_expiration_time"));
			setWeather_lat((String) jsonObject.get("weather_lat"));
			setWeather_lon((String) jsonObject.get("weather_lon"));
			setWeather_future_in_days((String) jsonObject.get("weather_future_in_days"));
			setQOTD_expiration_time((String) jsonObject.get("qotd_expiration_time"));

		} catch (ParseException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
