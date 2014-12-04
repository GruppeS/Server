package JsonClasses;

public class Note {

	private int noteID;
	private String text;
	private String dateTime;
	private String createdBy;
	private int isActive;
	private int eventID;

	public Note(int noteID, String text, String dateTime, String createdBy, int isActive, int eventID) {
		this.noteID = noteID;
		this.text = text;
		this.dateTime = dateTime;
		this.createdBy = createdBy;
		this.isActive = isActive;
		this.eventID = eventID;	
	}

	public int getEventID() {
		return eventID;
	}
	public int isActive() {
		return isActive;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public int getNoteID() {
		return noteID;
	}
	public String getText() {
		return text;
	}
	public String getDateTime() {
		return dateTime;
	}	
}