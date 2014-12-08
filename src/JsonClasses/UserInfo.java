package JsonClasses;

/**
 * Json class for used for logging in
 */
public class UserInfo {

	@SuppressWarnings("unused")
	private String overallID = "logIn";
	private String username;
	private String password;
	private String authenticated;

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAuthenticated() {
		return authenticated;
	}
	public void setAuthenticated(String authenticated) {
		this.authenticated = authenticated;
	}
}
