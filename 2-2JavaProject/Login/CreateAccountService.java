package Login;

public class CreateAccountService {

	public boolean createAccount(String id, String password, String password1, String userName, String phoneNumber) {
		if (id != null && password != null && userName != null && phoneNumber != null) {
			id = id.replace(" ", "");
			password = password.replace(" ", "");
			password1 = password1.replace(" ", "");
			if (password.equals(password1)) {
				if ((id.length() >= 6 && id.length() <= 20) && (password.length() >= 7 && password.length() <= 20)) {
					if ((userName != null) && (userName.length() >= 4 && userName.length() <= 20)) {
						boolean result = new AccountMapper().createAccount(id, password, userName, phoneNumber);
						if (result != false) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}
}//