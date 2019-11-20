package serverLogin;

public class ChangePasswordService {
	public boolean changePassword(String id, String password, String password1) {
		if ((id != null) && (password != null) && (password1 != null)) {
			if (password.equals(password1)) {
				new AccountMapper().changePasswordById(id, password);
				return true;
			}
		}
		return false;
	}
}