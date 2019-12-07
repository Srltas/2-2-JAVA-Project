package clientGameSystem;

import java.util.Timer;
import java.util.TimerTask;

public class GameTimer {

	public static void timerSetter(String[] args) {
		Timer timer = new Timer();

		TimerTask timerTask = new TimerTask() {
			public void run() {
				System.out.println("bang");
			}
		};

		timer.schedule(timerTask, 60000);
		timer.cancel();
	}
}
