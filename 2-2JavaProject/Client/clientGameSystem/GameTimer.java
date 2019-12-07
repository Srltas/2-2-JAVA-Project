package clientGameSystem;

import java.util.Timer;
import java.util.TimerTask;

import clientSocketConnection.Client;

public class GameTimer {

	public static void timerSetter(String playerName) {
		Timer timer = new Timer();
		TimerTask timerTask = new TimerTask() {
			public void run() {
				Client.client.send("endGame," + playerName +","+ GameScoreCounter.score);
			}
		};

		timer.schedule(timerTask, 10000);
	}
}
