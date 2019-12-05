package serverLogin;

import java.util.HashSet;
import java.util.Set;
import java.util.Iterator;

public class DeniedOverlapLoginService {
	private static Set<String> onlineUserId = new HashSet<String>();
	Iterator<String> userIter = onlineUserId.iterator();
	
	public void logInSuccess(String userId) {
		//로그인 성공시 onlineUserId에 값 입력
		onlineUserId.add(userId);
	}
	
	public boolean isOverlap(String userId) {
		//중복 로그인인지 확인하는 메소드
		while(!userIter.hasNext()) {
			if (userIter.next()==userId) {
				return true;
			}
		}
		return false;
	}
	
	public boolean logOutSuccess(String userId) {
		//로그아웃시 onlineUserId에서 id 제거
		while(!userIter.hasNext()) {
			if(userIter.next()==userId) {
				onlineUserId.remove(userId);
				return true;
			}
		}
		return false;
	}
	
	public void serverOff() {
		onlineUserId.removeAll(onlineUserId);
	}
}