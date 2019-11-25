package fxml;

import java.net.URL;
import java.util.ResourceBundle;

import clientSocketConnection.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MenuRoomViewController implements Initializable{
	@FXML
	private Text txtRating;
	@FXML
	private Button btnPlay;
	@FXML
	private Button btnExit;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		printRating();
	}
	
	public void play(ActionEvent event)throws Exception {
		Client.client.send("4" + Client.client.toString());
		Thread.sleep(100);

		Parent WaitRoomtView = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/WaitRoomView.fxml"));
		Scene scene = new Scene(WaitRoomtView);
		Stage primaryStage = (Stage) btnPlay.getScene().getWindow();
		primaryStage.setScene(scene);
	}
	public void printRating() {
		//유저 랭킹 불러오는 메소드
		txtRating.setText("90");
	}
	public void exit() {
		Client.client.stopClient();
		System.exit(0);
	}
}
