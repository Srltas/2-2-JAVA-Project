package clientLoginData;

// login info
public class Account {
	private String id;
	private String password;
	private String phoneNumber;
	private String userName;
	private int rankPoint;
	
	public String getId() {
		return id;
	}
	
	public String getPassword() {
		return password;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public int getRankPoint() {
		return rankPoint;
	}
	
	public String getUserName() {
		return userName;
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
	
	public void setRankPoint(int rankPoint) {
		this.rankPoint = rankPoint;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}

}
