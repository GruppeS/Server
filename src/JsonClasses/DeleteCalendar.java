package JsonClasses;

public class DeleteCalendar
{
	@SuppressWarnings("unused")
	private String overallID = "deleteCalendar";
	private String calendarName;
	private String userName;

	public String getCalendarName() {
		return calendarName;
	}
	public void setCalendarName(String calendarName) {
		this.calendarName = calendarName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
}
