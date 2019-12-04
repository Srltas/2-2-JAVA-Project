package fxml;

import java.io.DataOutputStream;
import java.io.OutputStream;

import clientLoginData.Account;
import clientSocketConnection.Client;
import clientSocketConnection.MessageListener;
import clientStarter.StartClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class StartViewController {
	@FXML
	private Label lblIdStatus;
	@FXML
	private Label lblPwStatus;
	@FXML
	private TextField txtUserId;
	@FXML
	private TextField txtPassword;
	@FXML
	private Button btnLogin;
	@FXML
	private Button btnSignUp;
	@FXML
	private Button btnFindId;
	@FXML
	private Button btnFindPW;
	@FXML
	private Button btnEixt;

	String text;
	String id;
	String pw;
	OutputStream out = null;
	DataOutputStream dout = null;
	String[] logindata;
	
	public static Account account = new Account();

	public void login(ActionEvent event) throws Exception {
		account.setId(txtUserId.getText());
		account.setPassword(txtPassword.getText());

		if (account.getId().equals("")) {
			lblIdStatus.setText("아이디를 입력하세요.");
			lblPwStatus.setText("");
		} else if (account.getPassword().equals("")) {
			lblPwStatus.setText("비밀번호를 입력하세요.");
			lblIdStatus.setText("");
		} else {

			text = "Login," + account.getId() + "," + account.getPassword();

			System.out.println(text);
			Client.client.send(text);
			Thread.sleep(1000);

			logindata = MessageListener.msg.split(",");
			if (logindata[0].equals("Login success")) {
				new Account().setRankPoint(Integer.parseInt(logindata[1]));
				account.setUserName(logindata[2]);
				Parent menuView = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/MenuRoomView.fxml"));
				AnchorPane root = (AnchorPane) StartClient.stage.getScene().getRoot();
				root.getChildren().add(menuView);
			} else {
				lblPwStatus.setText("가입하지 않은 아이디이거나, 잘못된 비밀번호입니다.");
				lblIdStatus.setText("");

				account.setId(null);
				account.setPassword(null);
			}
		}
	}

	public void enterSignUpView(ActionEvent event) throws Exception {
		Parent signUpView = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/SignUpView.fxml"));
		AnchorPane root = (AnchorPane) StartClient.stage.getScene().getRoot();
		root.getChildren().add(signUpView);
	}

	public void enterFindIdView(ActionEvent event) throws Exception {
		Parent findIdView = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/FindIDView.fxml"));
		AnchorPane root = (AnchorPane) StartClient.stage.getScene().getRoot();
		root.getChildren().add(findIdView);
	}
	
	public void enterFindPWView(ActionEvent event) throws Exception {
		Parent findIdView = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/FindPWView.fxml"));
		AnchorPane root = (AnchorPane) StartClient.stage.getScene().getRoot();
		root.getChildren().add(findIdView);
	}
	
	public void Eixt() {
		System.exit(0);
	}
}
