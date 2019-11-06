package Login;

public class Account {
	private String id;
	private String password;
	private String phoneNumber;
	
	public String getId() {
		return id;
	}
	
	public String getPassword() {
		return password;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
}
