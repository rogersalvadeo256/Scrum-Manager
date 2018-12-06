package view.TempFXML;

import java.io.FileInputStream;
import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TeamPOPUP extends Stage {

	// Sorry Furlan, we have to do this...

	// it was the only way

	public TeamPOPUP() throws IOException {
		
		try {
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("TeamPOPUP.fxml"));
			Scene scene = new Scene(root);
//			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			this.setScene(scene);
			this.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
