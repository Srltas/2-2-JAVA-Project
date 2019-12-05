package fxml;

import java.net.URL;
import java.util.ResourceBundle;

import clientLoginData.Account;
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
	
	Account account = new Account();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		printRating();
	}
	
	public void play(ActionEvent event)throws Exception {
		Client.client.send("enterGameRoom," + account.getId());
		Thread.sleep(1000);

		Parent View = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/InGameView.fxml"));
		Scene scene = new Scene(View);
		Stage primaryStage = (Stage) btnPlay.getScene().getWindow();
		primaryStage.setScene(scene);
	}
	public void printRating() {
		txtRating.setText(Integer.toString(account.getRankPoint()));
		
	}
	public void exit() {
		Client.client.stopClient();
		System.exit(0);
	}
}
