package JsonClasses;

import java.io.Serializable;

public class UserInfo implements Serializable
{
	private static final long serialVersionUID = 1L;
	private String overallID = "logIn";
	private String email;
	private String password;

	public String getOverallID() {
		return overallID;
	}
	public String getAuthUserEmail() {
		return email;
	}
	public void setAuthUserEmail(String email) {
		this.email = email;
	}
	public String getAuthUserPassword() {
		return password;
	}
	public void setAuthUserPassword(String password) {
		this.password = password;
	}
}