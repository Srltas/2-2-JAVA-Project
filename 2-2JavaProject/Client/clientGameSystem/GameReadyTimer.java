package clientGameSystem;

import java.util.Timer;
import java.util.TimerTask;

public class GameReadyTimer {
	public static void timerSetter(String[] args) {
		Timer timer = new Timer();

		TimerTask timerTask = new TimerTask() {
			public void run() {
				System.out.println("Start!");
			}
		};
		System.out.println("ready");
		timer.schedule(timerTask, 3000);
	}
}
