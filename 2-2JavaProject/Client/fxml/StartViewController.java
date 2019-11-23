package fxml;

import java.io.DataOutputStream;
import java.io.OutputStream;
import clientLoginData.Account;
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
	
	String text;
	String id;
	String pw;
	OutputStream out = null;
	DataOutputStream dout = null;

	public void login(ActionEvent event) throws Exception {

		Account account = new Account();
		account.setId(txtUserId.getText());
		account.setPassword(txtPassword.getText());

		if (account.getId().equals("")) {
			lblIdStatus.setText("아이디를 입력하세요.");
			lblPwStatus.setText("");
		} else if (account.getPassword().equals("")) {
			lblPwStatus.setText("비밀번호를 입력하세요.");
			lblIdStatus.setText("");
		} else {

			try {
				text = "0" + account.getId() + "," + account.getPassword();

				System.out.println(text);
				Client.client.send(text);
				Thread.sleep(500);
				
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
			String[] logindata = MessageListener.msg.split(",");
			if (logindata[0].equals("Login success")) {
				new Account().setRankPoint(Integer.parseInt(logindata[1]));
				Parent menuView = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/MenuRoomView.fxml"));
				Scene scene = new Scene(menuView);
				Stage primaryStage = (Stage) btnLogin.getScene().getWindow();
				primaryStage.setScene(scene);
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
		Scene scene = new Scene(signUpView);
		Stage primaryStage = (Stage) btnSignUp.getScene().getWindow();
		primaryStage.setScene(scene);
	}

	public void enterFindIdView(ActionEvent event) throws Exception {
		Parent findIdView = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/FindIDView.fxml"));
		Scene scene = new Scene(findIdView);
		Stage primaryStage = (Stage) btnFindId.getScene().getWindow();
		primaryStage.setScene(scene);
	}
}
