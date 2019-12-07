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

public class FindIdViewController {
	@FXML
	private TextField txtPhonNumber;
	@FXML
	private Button btnFindID;
	@FXML
	private Button btnBack;
	@FXML
	private Label lblFindID;
	@FXML
	private Label lblWaring;

	String[] message;
	String msg;
	String phonNumber;

	public void findID() throws InterruptedException, IOException {
		phonNumber = txtPhonNumber.getText();
		if (phonNumber.equals("")) {
			lblWaring.setText("필수 정보입니다.");
		} else {
			lblWaring.setText("");
		}

		if (!phonNumber.equals("")) {
			Client.client.send("findID," + txtPhonNumber.getText());
			Thread.sleep(2000);

			msg = MessageListener.msg;
			message = msg.split(",");

			if (message[0].equals("findIDsuccess")) {
				lblFindID.setText(message[1]);
				MessageListener.msg = null;
			} else {
				lblWaring.setText("해당 아이디가 없습니다.");
			}
		}
	}

	public void back() throws Exception {
		Parent View = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/StartView.fxml"));
		Scene scene = new Scene(View);
		Stage primaryStage = (Stage) btnBack.getScene().getWindow();
		primaryStage.setScene(scene);
	}
}