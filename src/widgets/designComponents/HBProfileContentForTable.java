package widgets.designComponents;

import java.io.IOException;

import db.pojos.USER_PROFILE;
import javafx.scene.layout.VBox;

public class HBProfileContentForTable extends HBProfileContentForgotPassword{
	
	private VBox layout ;
	public HBProfileContentForTable(USER_PROFILE p) throws IOException {
		super(p);
		
		this.layout= new VBox() ;
		
		this.getChildren().clear();

		this.layout.getChildren().addAll(image,lblName);
		
		
		int size = 50;
		this.image.setFitHeight(size);
		this.image.setFitWidth(size);
		
	
	}

	



































}


