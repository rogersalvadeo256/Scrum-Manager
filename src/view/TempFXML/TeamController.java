package view.TempFXML;

import db.pojos.PROJECT_MEMBER;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class TeamController {

	PROJECT_MEMBER pm;
	public TeamController(PROJECT_MEMBER prm) {
		pm = prm;
		
	}
	
	
	@FXML
	ImageView imgAndre, imgRoger;
	@FXML
	Label lblAndre, lblRoger;
	@FXML
	VBox vbxAndre, vbxRoger;
	
	@FXML
	public void addMember() {
		
	}
	
	@FXML
	public void initialize() {
		
		
		
	}
	
}
