package JsonClasses;

import java.util.ArrayList;

public class Forecasts {
	
	private String overallID = "getForecast";
	public ArrayList<Forecast> forecastlist = new ArrayList<Forecast>();
	
	public ArrayList<Forecast> getForecasts() {
		return forecastlist;
	}
}
