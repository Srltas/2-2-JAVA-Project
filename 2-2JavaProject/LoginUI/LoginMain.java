package LoginUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LoginMain extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			//클라이언트 실행
			LoginUIClient client = new LoginUIClient();
			client.startClient();
			
			primaryStage.setTitle("끝말잇기 게임");
			Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/StartView.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setOnCloseRequest(evene -> System.exit(0));
			primaryStage.show();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}