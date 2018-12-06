package view.TempFXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class DevelopmentScreen extends Stage{

	public DevelopmentScreen() {
		// TODO Auto-generated constructor stub

		try {
			AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("DevelopmentScene.fxml"));
			Scene scene = new Scene(root);
//		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			this.setScene(scene);
			this.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
