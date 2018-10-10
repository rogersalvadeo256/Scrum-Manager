package widgets.designComponents.profileContents;


import java.io.IOException;

import application.main.Window;
import db.pojos.USER_PROFILE;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import statics.ENUMS;
import widgets.toaster.Toast;

public class HBProfileContentForInvite extends HBProfileContentForgotPassword {
	
	private USER_PROFILE p;
	private VBox layout;
	public HBProfileContentForInvite(USER_PROFILE p) throws IOException {
		super(p);
		this.layout = new VBox();
		this.p = p;
		this.getChildren().clear();
		
		this.getChildren().add(layout);
		
		Circle circle = new Circle();
		circle.setCenterX(30.0f);
		circle.setCenterY(15.0f);
		circle.setRadius(10.0f);
		circle.applyCss();
		
		this.layout.getStylesheets().add(this.getClass().getResource("/css/INVITATION_COMPONENT.css").toExternalForm());
		this.layout.applyCss();
		
		if (p.getStatus().equals(ENUMS.DISPONIBILITY_FOR_PROJECT.AVAILABLE.getValue())) {
			this.layout.setId("available");
			circle.setId("circleAvailable");
		} else {
			this.layout.setId("busy");
			circle.setId("circleBusy");
		}
		this.lblName.applyCss();
		this.lblName.setId("lblName");
		
		
		String status = p.getStatus().equals(ENUMS.DISPONIBILITY_FOR_PROJECT.AVAILABLE.getValue()) ? "Disponivel" : "Ocupado";
		Label lblStatus = new Label(status);
		
		lblStatus.applyCss();
		
		HBox hbStatus = new HBox();
		hbStatus.setAlignment(Pos.CENTER);
		
		
		hbStatus.getChildren().addAll(circle, lblStatus);
		hbStatus.setSpacing(10);
		
		
		this.layout.getChildren().addAll(image, lblName, hbStatus);
		
		this.setAlignment(Pos.CENTER);
		int size = 80;
		this.image.setFitHeight(size);
		this.image.setFitWidth(size);
	}
	public void setClickedEvent(EventHandler<MouseEvent> e) {
		this.setOnMouseClicked(e);
	}
	@Override
	public USER_PROFILE getProfile() {
		return super.getProfile();
	}
}

