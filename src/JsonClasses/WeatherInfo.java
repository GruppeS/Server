package JsonClasses;

import java.io.Serializable;

public class WeatherInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	private String overallID = "weather";
	private String weatherLocation;
	private String weather;
	private String temperature;

	public String getOverallID() {
		return overallID;
	}
	public String getWeatherLocation() {
		return weatherLocation;
	}
	public void setWeatherLocation(String weatherLocation) {
		this.weatherLocation = weatherLocation;
	}
	public String getWeather() {
		return weather;
	}
	public void setWeather(String weather) {
		this.weather = weather;
	}
	public String getTemperature() {
		return temperature;
	}
	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}
}
