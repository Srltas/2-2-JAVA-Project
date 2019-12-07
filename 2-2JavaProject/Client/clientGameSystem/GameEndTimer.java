package clientGameSystem;

import java.util.Timer;
import java.util.TimerTask;

import clientSocketConnection.Client;
import fxml.InGameViewController;

public class GameEndTimer {

	public static void timerSetter() {
		Timer timer = new Timer();
		TimerTask timerTask = new TimerTask() {
			public void run() {
				InGameViewController.btnResult.setDisable(false);
				InGameViewController.btnResult.setOpacity(1);
			}
		};

		timer.schedule(timerTask, 3000);
	}
}
