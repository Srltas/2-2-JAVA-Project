package fxml;

import java.net.URL;
import java.util.ResourceBundle;

import clientSocketConnection.Client;
import clientStarter.StartClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
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
		Client.client.send("enterGameRoom," + StartViewController.account.getId());
		//Thread.sleep(100);

		Parent GameRoomView = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/InGameView.fxml"));
		AnchorPane root = (AnchorPane) StartClient.stage.getScene().getRoot();
		root.getChildren().add(GameRoomView);
	}
	public void printRating() {
		txtRating.setText(Integer.toString(StartViewController.account.getRankPoint()));
		
	}
	public void exit() {
		Client.client.stopClient();
		System.exit(0);
	}
}
