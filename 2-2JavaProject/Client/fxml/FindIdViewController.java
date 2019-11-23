package fxml;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FindIdViewController {
	@FXML
	private TextField txtUserName;
	@FXML
	private TextField txtPhonNumber;
	@FXML
	private Button btnFindID;
	@FXML
	private Button btnBack;
	
	public void findID(ActionEvent event) throws Exception {
		System.out.println("아이디찾기");
	}
	public void back(ActionEvent event) throws Exception {
		Parent StartView = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/StartView.fxml"));
		Scene scene = new Scene(StartView);
		Stage primaryStage = (Stage) btnBack.getScene().getWindow();
		primaryStage.setScene(scene);
	}
}