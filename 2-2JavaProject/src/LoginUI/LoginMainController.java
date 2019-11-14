package LoginUI;

import java.io.DataOutputStream;
import java.io.OutputStream;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class LoginMainController {
	String text;
	OutputStream out = null;
	DataOutputStream dout = null;
	
	@FXML
	private Label lblStatus;

	@FXML
	private TextField txtUserName;

	@FXML
	private TextField txtPassword;

	public void Login(ActionEvent event) throws Exception {
		// 서버로 아이디 + 비밀번호 주기
		text = "0" + txtUserName.getText() + "," + txtPassword.getText();
		LoginUIClient.client.sendLogin(text);
	}
}
