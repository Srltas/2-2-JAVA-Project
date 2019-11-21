package fxml;

import clientSocketConnection.Client;
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

public class SignUpViewController {
	String text;
	String id;
	String pw;
	String userName;
	String userPhonNumber;
	
	@FXML
	private TextField txtUserID;
	@FXML
	private TextField txtPassword;
	@FXML
	private TextField txtUserName;
	@FXML
	private TextField txtUserPhonNumber;
	@FXML
	private Button btnsignUp;
	@FXML
	private Button btnBack;
	@FXML
	private Label lblIdStatus;
	@FXML
	private Label lblPwStatus;
	@FXML
	private Label lblNameStatus;
	@FXML
	private Label lblPhonNumberStatus;
	
	public void signUp(ActionEvent event)throws Exception {
		id = txtUserID.getText();
		pw = txtPassword.getText();
		userName = txtUserName.getText();
		userPhonNumber = txtUserPhonNumber.getText();
		
		if(id.equals("") == true) {
			lblIdStatus.setText("필수 정보입니다.");
		}else if(pw.equals("") == true) {
			lblPwStatus.setText("필수 정보입니다.");
		}else if(userName.equals("") == true) {
			lblNameStatus.setText("필수 정보입니다.");
		}else if(userPhonNumber.equals("") == true) {
			lblPhonNumberStatus.setText("필수 정보입니다.");
		}else {
			text = "1" + id + "," + pw + "," + userName + "," + userPhonNumber;
			Client.client.send(text);
			Thread.sleep(100);
			
			if(MessageListener.msg.equals("account create success")) {
				Parent StartView = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/StartView.fxml"));
				Scene scene = new Scene(StartView);
				Stage primaryStage = (Stage) btnBack.getScene().getWindow();
				primaryStage.setScene(scene);
			}else {
				lblPhonNumberStatus.setText("회원가입에 실패했습니다.");
			}
		}
	}
	
	public void back(ActionEvent event) throws Exception {
		Parent StartView = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/StartView.fxml"));
		Scene scene = new Scene(StartView);
		Stage primaryStage = (Stage) btnBack.getScene().getWindow();
		primaryStage.setScene(scene);
	}
}