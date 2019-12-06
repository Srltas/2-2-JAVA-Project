package fxml;

import clientSocketConnection.MessageListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
	@FXML
	private Label lblFindID;
	
	String[] message;
	String msg;
	public void findID(ActionEvent event) throws Exception {
		System.out.println("아이디찾기");
	}
	
	public void findID() {
		msg = MessageListener.msg;
		message = msg.split(",");
		
		if(message[0].equals("findIDsuccess")) {
			lblFindID.setText(message[1]);
		}else {
			lblFindID.setText("해당 아이디가 없습니다.");
		}
	}
	public void back(ActionEvent event) throws Exception {
		Parent View = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/InGameView.fxml"));
		Scene scene = new Scene(View);
		Stage primaryStage = (Stage) btnBack.getScene().getWindow();
		primaryStage.setScene(scene);
	}
}