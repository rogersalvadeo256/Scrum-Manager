package widgets.designComponents;

import db.pojos.PROJECT;
import db.pojos.USER_PROFILE;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import statics.DB_OPERATION;

public class HBProjectComponent extends HBox{

	
	Label lblName, lblDesPiece;
	Label lblCreator, lblCreatorName;
	Label lblPorcentage;
	
	VBox layout;
	
	public HBProjectComponent(PROJECT p) {

		this.layout = new VBox();
		
		this.lblName = new Label(p.getProjName());
		this.lblDesPiece = new Label(p.getProjDescription());
		
		this.lblCreator = new Label("Criador: ");
//		USER_PROFILE pu = (USER_PROFILE) DB_OPERATION.QUERY( "FROM USER_PROFILE WHERE USER_COD = :COD", "COD", p.getProjCreator()).get(0);
//		this.lblCreatorName = new Label(pu.getName());
		
		layout.getChildren().addAll(lblName, lblDesPiece);
		
		this.getChildren().add(layout);
		this.getChildren().addAll(lblCreator, lblCreatorName);
		
	}	
	
	
	
	

	
	
	
	
	
	
	
	
	
	
	
}
