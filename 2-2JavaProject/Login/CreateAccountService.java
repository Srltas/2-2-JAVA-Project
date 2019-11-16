package Login;

public class CreateAccountService {
	public boolean createAccount(String id, String password, String phoneNumber) {
		if (id != null && password != null) {
			id = id.replace(" ", "");
			password = password.replace(" ","");
			if ((id.length() >= 6 && id.length() <= 20) && (password.length() >= 7 && password.length() <= 20)) {
				boolean result = new AccountMapper().createAccount(id, password, phoneNumber);
				if (result != false) {
					return true;
				}
			}
		}
		return false;
	}
}//