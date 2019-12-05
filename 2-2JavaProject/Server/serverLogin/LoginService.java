package serverLogin;

public class LoginService {

	public Account login(String id, String password) {		
		if ((id != null) && (password != null)) {
			Account account = new AccountMapper().getAccountById(id);
			if (account.getPassword() != null && account.getPassword().equals(password)) {
				return account;
			}
		}
		return null;
	}
}//
