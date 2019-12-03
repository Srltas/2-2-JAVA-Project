package serverLogin;

import java.util.HashSet;
import java.util.Iterator;

public class LoginService {
	
	HashSet<String> onlineUserId = new HashSet<String>();
	Iterator<String> userId = onlineUserId.iterator();
	
	public boolean login(String id, String password) {
		
		while(userId.hasNext()) {
			if(id.equals(userId.next())) {
				System.out.println("this account already log-on");
				return false;
			}
		}
		
		if ((id != null) && (password != null)) {
			Account account = new AccountMapper().getAccountById(id);
			if (account.getPassword() != null && account.getPassword().equals(password)) {
				onlineUserId.add(id);
				return true;
			}
		}
		return false;
	}
}//
