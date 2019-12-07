package clientGameSystem;

import java.util.Timer;
import java.util.TimerTask;

import clientSocketConnection.Client;

public class GameEndTimer {

	public static void timerSetter() {
		Timer timer = new Timer();
		TimerTask timerTask = new TimerTask() {
			public void run() {
				
			}
		};

		timer.schedule(timerTask, 3000);
	}
}
