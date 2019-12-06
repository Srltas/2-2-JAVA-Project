package serverLogin;

public class DeniedOverlapLoginService {

	public static DeniedOverlapLoginService dols = new DeniedOverlapLoginService();

	String[] onlineUserId = new String[4];

	public void printer() {
		for (int i = 0; i < onlineUserId.length; i++) {
			System.out.println(i + onlineUserId[i]);
		}
	}

	public boolean isOverlap(String userId) {
		if (onlineUserId == null) {
			return false;
		} else {
			for (int i = 0; i < 4; i++) {
				if (onlineUserId[i].equals(userId)) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean loginSuccess(String userId) {
		for (int i = 0; i < onlineUserId.length; i++) {
			if (onlineUserId[i] == null) {
				onlineUserId[i] = userId;
				return true;
			}
		}
		return false;
	}

	public boolean logOut(String userId) {
		for (int i = 0; i < 4; i++) {
			if (onlineUserId[i].equals(userId)) {
				onlineUserId[i] = null;
				return true;
			}
		}
		return false;
	}

}