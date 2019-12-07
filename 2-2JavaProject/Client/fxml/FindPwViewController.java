package fxml;

import java.io.IOException;

import clientSocketConnection.Client;
import clientSocketConnection.MessageListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
	@FXML
	private Label lblID;
	@FXML
	private Label lblPW;
	@FXML
	private Label lblPW2;

	String msg;
	String[] message;
	String ID;
	String PW;
	String PW2;

	public void changePW() throws IOException, InterruptedException {
		ID = txtFieldID.getText();
		PW = txtFieldPW.getText();
		PW2 = txtFieldPW2.getText();

		if (ID.equals("")) {
			lblID.setText("필수 정보입니다.");
		} else {
			lblID.setText("");
		}
		if (PW.equals("")) {
			lblPW.setText("필수 정보입니다.");
		} else {
			lblPW.setText("");
		}
		if (PW2.equals("")) {
			lblPW2.setText("필수 정보입니다.");
		} else {
			lblPW2.setText("");
		}
		if (!ID.equals("") && !PW.equals("") && !PW2.equals("")) {
			Client.client.send(
					"changePW," + txtFieldID.getText() + "," + txtFieldPW.getText() + "," + txtFieldPW2.getText());
			Thread.sleep(2000);
			msg = MessageListener.msg;
			message = msg.split(",");

			if (message[0].equals("changePWSuccess")) {
				lblPW2.setText("비밀번호가 변경되었습니다.");
				MessageListener.msg = null;
			} else {
				lblPW2.setText("해당 아이디가 없거나 잘못입력하였습니다.");
			}
		}
	}

	public void Back() throws IOException {
		Parent View = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/StartView.fxml"));
		Scene scene = new Scene(View);
		Stage primaryStage = (Stage) btnBack.getScene().getWindow();
		primaryStage.setScene(scene);
	}
}
