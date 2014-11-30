package JsonClasses;

public class UserInfo {

	private String overallID = "logIn";
	private String email;
	private String password;

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
