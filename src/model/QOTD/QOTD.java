package model.QOTD;

import java.io.Serializable;

public class QOTD implements Serializable {

	private static final long serialVersionUID = 1L;
	private String overallID = "getQuote";
	private String quote;
	private String author;
	private String topic;

	public QOTD(String quote, String author, String topic) {
		super();
		this.quote = quote;
		this.author = author;
		this.topic = topic;
	}
	public String getQuote() {
		return quote;
	}
	public void setQuote(String quote) {
		this.quote = quote;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}
}