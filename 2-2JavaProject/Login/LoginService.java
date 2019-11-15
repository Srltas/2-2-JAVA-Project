package Login;

public class LoginService {
	public boolean login(String id, String password) {
		if ((id != null) 
				&& (password != null)) {
			Account account = new AccountMapper().getAccountById(id);
			if (account.getPassword() != null 
					&& account.getPassword().equals(password)) {
				return true;
			}
		}
		return false;
	}
}
