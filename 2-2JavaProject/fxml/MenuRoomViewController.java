package fxml;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class MenuRoomViewController {
	@FXML
	private Text txtRating;
	@FXML
	private Button btnPlay;
	@FXML
	private Button btnExit;
	
	public void play() {
		System.out.println("방에 입장합니다.");
		txtRating.setText("90");
	}
	public void printRating() {
		txtRating.setText("90");
	}
	public void exit() {
		System.exit(0);
	}
}
