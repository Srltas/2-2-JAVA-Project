package Login;

public class ChangePasswordService {
	// public boolean changePassword(String id, String password)
	public boolean changePassword(String id, String firstPasswordInput, String secondPasswordInput) {
		if ((firstPasswordInput.equals(secondPasswordInput))) {
			if ((id != null) && (firstPasswordInput != null)) {
				new AccountMapper().changePasswordById(id, firstPasswordInput);
				return true;
			}
		}
		return false;
	}
}