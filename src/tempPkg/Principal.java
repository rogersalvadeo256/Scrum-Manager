package tempPkg;
import javafx.application.Application;
import javafx.stage.Stage;

public class Principal extends Application{

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		new AddFriendScene();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
