package config;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

// Importing json packaes
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Class for storing configuration variables
 */
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

	/**
	 * Constructor triggers method ReadFile
	 */
	public Configurations() {
		ReadFile();
	}

	/**
	 * @return host
	 */
	public String getHost() {
		return host;
	}
	/**
	 * @param host
	 */
	public void setHost(String host) {
		this.host = host;
	}

	/**
	 * @return port
	 */
	public String getPort() {
		return port;
	}
	/**
	 * @param port
	 */
	public void setPort(String port) {
		this.port = port;
	}

	/**
	 * @return username
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * @param username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return serverport
	 */
	public int getServerport() {
		return Integer.parseInt(serverport);
	}
	/**
	 * @param serverport
	 */
	public void setServerport(String serverport) {
		this.serverport = serverport;
	}

	/**
	 * @return dbname
	 */
	public String getDbname() {
		return dbname;
	}
	/**
	 * @param dbname
	 */
	public void setDbname(String dbname) {
		this.dbname = dbname;
	}

	/**
	 * @return weather_expiration_time
	 */
	public String getWeather_expiration_time() {
		return weather_expiration_time;
	}
	/**
	 * @param weather_expiration_time
	 */
	public void setWeather_expiration_time(String weather_expiration_time) {
		this.weather_expiration_time = weather_expiration_time;
	}

	/**
	 * @return weather_lat
	 */
	public String getWeather_lat() {
		return weather_lat;
	}
	/**
	 * @param weather_lat
	 */
	public void setWeather_lat(String weather_lat) {
		this.weather_lat = weather_lat;
	}

	/**
	 * @return weather_lon
	 */
	public String getWeather_lon() {
		return weather_lon;
	}
	/**
	 * @param weather_lon
	 */
	public void setWeather_lon(String weather_lon) {
		this.weather_lon = weather_lon;
	}

	/**
	 * @return weather_future_in_days
	 */
	public String getWeather_future_in_days() {
		return weather_future_in_days;
	}
	/**
	 * @param weather_future_in_days
	 */
	public void setWeather_future_in_days(String weather_future_in_days) {
		this.weather_future_in_days = weather_future_in_days;
	}

	/**
	 * @return qotd_expiration_time
	 */
	public String getQOTD_expiration_time() {
		return qotd_expiration_time;
	}
	/**
	 * @param qotd_expiration_time
	 */
	public void setQOTD_expiration_time(String qotd_expiration_time) {
		this.qotd_expiration_time = qotd_expiration_time;
	}

	/**
	 * Parses configuration json into a java object and gets/sets variables to this class
	 */
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
