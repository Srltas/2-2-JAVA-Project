package fxml;

import java.net.URL;
import java.util.ResourceBundle;

import clientSocketConnection.Client;
import clientSocketConnection.MessageListener;
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
		getRankName();
		printRating();
	}
	
	public void getRankName() {
		
		String[] logindata = MessageListener.msg.split(",");
		StartViewController.account.setRankPoint(Integer.parseInt(logindata[1]));
		StartViewController.account.setUserName(logindata[2]);
	}
	
	public void play(ActionEvent event)throws Exception {
		Client.client.send("enterGameRoom," + StartViewController.account.getUserName());
		Thread.sleep(1000);

		Parent View = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/InGameView.fxml"));
		Scene scene = new Scene(View);
		Stage primaryStage = (Stage) btnPlay.getScene().getWindow();
		primaryStage.setScene(scene);
	}
	public void printRating() {
		txtRating.setText(Integer.toString(StartViewController.account.getRankPoint()));
		
	}
	public void exit() {
		if (InGameViewController.checkCount == 1) {
			Client.client.send("exitGameRoom," + StartViewController.account.getUserName());
		} else {
			Client.client.send("exitGame," + StartViewController.account.getId());
		}
		System.exit(0);
	}
}
