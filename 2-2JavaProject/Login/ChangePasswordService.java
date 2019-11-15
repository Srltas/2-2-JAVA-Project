package Login;

public class ChangePasswordService {
	// public boolean changePassword(String id, String password)
	public boolean changePassword(String id, String password) {
		if ((id != null) && (password != null)) {
			new AccountMapper().changePasswordById(id, password);
			return true;
		}
		return false;
	}
}
