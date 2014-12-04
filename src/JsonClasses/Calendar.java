package JsonClasses;

import java.util.ArrayList;

@SuppressWarnings("unused")
public class Calendar
{
	private String overallID;
	private String calendar;
	private String createdBy;
	private ArrayList<String> users;
	private boolean isPublic;

	public Calendar(String calendar, String createdBy, boolean isPublic) {
		this.calendar = calendar;
		this.createdBy = createdBy;
		this.isPublic = isPublic;
	}

	public void setOverallID(String overallID) {
		this.overallID = overallID;
	}
	public void setUsers(ArrayList<String> users) {
		this.users = users;
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
	public ArrayList<String> getUsers() {
		return users;
	}
}
