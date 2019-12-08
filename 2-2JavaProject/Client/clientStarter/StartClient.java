package clientStarter;

import clientSocketConnection.Client;
import fxml.InGameViewController;
import fxml.StartViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class StartClient extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			Client.client.startClient();
			primaryStage.setTitle("알파카파 끝말잇기");
			Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/StartView.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setOnCloseRequest(evene -> {
				if (InGameViewController.checkCount) {
					Client.client.send("exitGameRoom," + StartViewController.account.getUserName() + "," +StartViewController.account.getId());
				} else {
					Client.client.send("exitGame," + StartViewController.account.getId());
				}
				System.exit(0);
			});
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}