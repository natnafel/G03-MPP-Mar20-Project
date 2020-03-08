import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Start extends Application {
	public static void main(String[] args) {launch(args);}


	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("ui/resources/Login.fxml"));
		stage.setTitle("Library System");
		stage.setScene(new Scene(root, 572, 491));
		stage.show();
		stage.setResizable(false);
	}
}
