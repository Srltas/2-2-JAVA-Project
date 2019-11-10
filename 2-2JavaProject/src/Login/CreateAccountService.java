package Login;

public class CreateAccountService {
	public boolean createAccount(String id, String password, String phoneNumber) {
		if(id != null && password != null) {
			boolean result = new AccountMapper().createAccount(id, password, phoneNumber);
			if (result != false) {
				return true;
			}
		}
		return false;
	}
}
