package fxml;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class SignUpViewController {
	@FXML
	private Button btnsignUp;
	@FXML
	private Button btnBack;
	
	public void signUp(ActionEvent event)throws Exception {
		System.out.println("회원가입이 되었습니다.");
		Parent StartView = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/StartView.fxml"));
		Scene scene = new Scene(StartView);
		Stage primaryStage = (Stage) btnBack.getScene().getWindow();
		primaryStage.setScene(scene);
	}
	
	public void back(ActionEvent event) throws Exception {
		Parent StartView = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/StartView.fxml"));
		Scene scene = new Scene(StartView);
		Stage primaryStage = (Stage) btnBack.getScene().getWindow();
		primaryStage.setScene(scene);
	}
}
