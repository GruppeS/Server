package JsonClasses;

/**
 * Json class used to get quote of the day
 */
public class QOTD {

	@SuppressWarnings("unused")
	private String overallID = "getQuote";
	private String quote;

	public String getQuote() {
		return quote;
	}
	public void setQuote(String quote) {
		this.quote = quote;
	}
}
