package view.TempFXML;

import java.io.FileInputStream;
import java.io.IOException;

import db.pojos.PROJECT;
import db.pojos.PROJECT_MEMBER;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TeamPOPUP extends Stage {

	// Sorry Furlan, we have to do this...

	// it was the only way

	public TeamPOPUP(PROJECT pj) throws IOException {
		
		try {
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("TeamPOPUP.fxml"));

			
			TeamController team = new TeamController(pj);
			loader.setController(team);
			AnchorPane root = loader.load();
			Scene scene = new Scene(root);
			this.setScene(scene);
			this.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
