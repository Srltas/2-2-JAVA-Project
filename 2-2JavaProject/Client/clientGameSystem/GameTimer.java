package clientGameSystem;

import java.util.Timer;
import java.util.TimerTask;

import clientSocketConnection.Client;

public class GameTimer {

	public static void timerSetter(String playerName,String score) {
		Timer timer = new Timer();
		TimerTask timerTask = new TimerTask() {
			public void run() {
				Client.client.send("endGame," + playerName +","+ score);
			}
		};

		timer.schedule(timerTask, 60000);
	}
}
