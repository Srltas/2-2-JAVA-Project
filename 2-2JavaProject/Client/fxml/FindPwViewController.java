package fxml;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FindPwViewController {
	@FXML
	private TextField txtFieldID;
	@FXML
	private TextField txtFieldPW;
	@FXML
	private TextField txtFieldPW2;
	@FXML
	private Button btnChange;
	@FXML
	private Button btnBack;
	
	public void changePW() throws IOException {
		Parent View = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/FindPWView2.fxml"));
		Scene scene = new Scene(View);
		Stage primaryStage = (Stage) btnBack.getScene().getWindow();
		primaryStage.setScene(scene);
	}
	
	public void Back() throws IOException {
		Parent View = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/MenuRoomView.fxml"));
		Scene scene = new Scene(View);
		Stage primaryStage = (Stage) btnBack.getScene().getWindow();
		primaryStage.setScene(scene);
	}
}
