package JsonClasses;

@SuppressWarnings("unused")
public class Calendar
{
	private String overallID;
	private String calendar;
	private String createdBy;
	private String shareWith;
	private boolean isPublic;

	public Calendar(String calendar, String createdBy, boolean isPublic) {
		this.calendar = calendar;
		this.createdBy = createdBy;
		this.isPublic = isPublic;
	}

	public void setOverallID(String overallID) {
		this.overallID = overallID;
	}
	public void setShareWith(String username) {
		this.shareWith = username;
	}
	public String getCalendarname() {
		return calendar;
	}
	public String getUsername() {
		return createdBy;
	}
	public boolean getIsPublic() {
		return isPublic;
	}
	public String getShareWith() {
		return shareWith;
	}
}
