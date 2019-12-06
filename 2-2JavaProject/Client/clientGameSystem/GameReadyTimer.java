package clientGameSystem;

import java.util.Timer;
import java.util.TimerTask;

public class GameReadyTimer {
	public static void timerSetter(String[] args) {
		Timer timer = new Timer();

		TimerTask timerTask = new TimerTask() {
			public void run() {
				boolean stopCount = true;
				if (stopCount) {
					for (int i = 1; i <= 3; i++) {
						System.out.println(i + " sec");
						try {
							Thread.sleep(1000);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					System.out.println("bang");
				}
			}
		};
		
		try {
			timer.schedule(timerTask, 1000, 1000);
			Thread.sleep(3000);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			timer.cancel();
		}
	}
}
