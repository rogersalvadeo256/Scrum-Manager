package widgets.designComponents;

import java.io.IOException;

import db.pojos.USER_PROFILE;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class HBProfileContentForInvite extends HBProfileContentForgotPassword{
	
	private VBox layout ;
	public HBProfileContentForInvite(USER_PROFILE p) throws IOException {
		super(p);
		this.layout= new VBox() ;
		
		this.getChildren().clear();

		this.getChildren().add(layout);
		

		this.layout.getChildren().addAll(image,lblName);


		int size = 50;
		this.image.setFitHeight(size);
		this.image.setFitWidth(size);
		

	
	}
	public void  setClickedEvent (EventHandler<MouseEvent> e) { 
		this.setOnMouseClicked(e);
	}
	@Override
	public USER_PROFILE getProfile() {
		return super.getProfile();
	}


































}


