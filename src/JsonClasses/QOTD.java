package JsonClasses;

import java.io.Serializable;

public class QOTD implements Serializable {

	private static final long serialVersionUID = 1L;
	private String overallID = "getQuote";
	private String quote;

	public String getOverallID() {
		return overallID;
	}
	public String getQuote() {
		return quote;
	}
	public void setQuote(String quote) {
		this.quote = quote;
	}
}