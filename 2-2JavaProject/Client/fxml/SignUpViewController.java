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
	@FXML
	private TextField txtUserID;
	@FXML
	private TextField txtPassword;
	@FXML
	private TextField txtPasswordCheck;
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
	private Label lblPwCheck;
	@FXML
	private Label lblNameStatus;
	@FXML
	private Label lblPhonNumberStatus;
	
	String text;
	String id;
	String pw;
	String pwCheck;
	String userName;
	String userPhonNumber;
	String msg;
	String[] message;
	
	public void signUp(ActionEvent event)throws Exception {
		id = txtUserID.getText();
		pw = txtPassword.getText();
		pwCheck = txtPasswordCheck.getText();
		userName = txtUserName.getText();
		userPhonNumber = txtUserPhonNumber.getText();
		
		if(id.equals("")) {
			lblIdStatus.setText("필수 정보입니다.");
		}else {
			lblIdStatus.setText("");
		}
		if(pw.equals("")) {
			lblPwStatus.setText("필수 정보입니다.");
		}else {
			lblPwStatus.setText("");
		}
		if(pwCheck.equals("")) {
			lblPwStatus.setText("비밀번호를 입력하세요");
		}else {
			lblPwStatus.setText("");
		}
		if(userName.equals("")) {
			lblNameStatus.setText("필수 정보입니다.");
		}else {
			lblNameStatus.setText("");
		}
		if(userPhonNumber.equals("")) {
			lblPhonNumberStatus.setText("필수 정보입니다.");
		}else {
			lblPhonNumberStatus.setText("");
		}
		//회워가입
		if(!id.equals("") && !pw.equals("") && !userName.equals("") && !userPhonNumber.equals("")) {
			text = "signUp," + id + "," + pw + "," + pwCheck + "," + userName + "," + userPhonNumber;
			Client.client.send(text);
			Thread.sleep(1000);
			
			msg = MessageListener.msg;
			message = msg.split(",");
			
			if(message[0].equals("account create success")) {
				Parent View = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/StartView.fxml"));
				Scene scene = new Scene(View);
				Stage primaryStage = (Stage) btnsignUp.getScene().getWindow();
				primaryStage.setScene(scene);
			}else {
				lblPhonNumberStatus.setText("회원가입에 실패했습니다.");
			}
		}
	}
	
	public void back(ActionEvent event) throws Exception {
		Parent View = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/StartView.fxml"));
		Scene scene = new Scene(View);
		Stage primaryStage = (Stage) btnBack.getScene().getWindow();
		primaryStage.setScene(scene);
	}
}