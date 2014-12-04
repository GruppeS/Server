package JsonClasses;

public class Calendar
{
	@SuppressWarnings("unused")
	private String overallID;
	private String calendar;
	private String username;
	private boolean isPublic;

	public Calendar(String calendar, String username, boolean isPublic) {
		this.calendar = calendar;
		this.username = username;
		this.isPublic = isPublic;
	}

	public void setOverallID(String overallID) {
		this.overallID = overallID;
	}
	public String getCalendarname() {
		return calendar;
	}
	public String getUsername() {
		return username;
	}
	public boolean getIsPublic() {
		return isPublic;
	}
}
