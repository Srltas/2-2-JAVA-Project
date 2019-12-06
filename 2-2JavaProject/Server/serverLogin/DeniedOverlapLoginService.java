package serverLogin;

import java.util.HashSet;
import java.util.Iterator;

public class DeniedOverlapLoginService {
	static HashSet<String> onlineUserId = new HashSet<String>();
	static DeniedOverlapLoginService overlap = new DeniedOverlapLoginService();
	static Iterator<String> userList;

	public static boolean search(String id) {
		if (onlineUserId.isEmpty()) {
			return true;
		} else if (onlineUserId.contains(id)) {
			return false;
		}

		/*
		 * userList = onlineUserId.iterator(); while (userList.hasNext()) {
		 * System.out.println(userList.next()); if(userList.next().equals(id)) { return
		 * false; } }
		 */
		return true;
	}

	public static void add(String id) {
		onlineUserId.add(id);
	}

	public static int printer() {
		return onlineUserId.size();
	}
}