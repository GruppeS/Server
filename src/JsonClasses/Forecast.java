package JsonClasses;

public class Forecast {

	private String overallID = "getForecast";
	private String date;
	private String celsius;
	private String desc;

	public Forecast(String date, String celsius, String desc) {
		this.date = date;
		this.celsius = celsius;
		this.desc = desc;
	}
	public String getOverallID() {
		return overallID;
	}
	public String getDate() {
		return date;
	}
	public String getCelsius() {
		return celsius;
	}
	public String getDesc() {
		return desc;
	}
}
