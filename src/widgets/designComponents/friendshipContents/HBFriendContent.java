package widgets.designComponents.friendshipContents;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import db.pojos.USER_PROFILE;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;
import statics.ENUMS;
import widgets.designComponents.profileContents.HBProfileContent;

public class HBFriendContent extends HBProfileContent {
	
	protected Button btnDelete, btnInvite;
	private HBox hbButtons;
	private USER_PROFILE p;
	public HBFriendContent(USER_PROFILE p) throws IOException {
		super(p);
		this.getChildren().clear();
		this.getChildren().addAll(vbUsrIMG, vbUsrLABEL);
		
		super.getStylesheets().add(this.getClass().getResource("/css/FRIEND_LIST_COMPONENT.css").toExternalForm());
		this.setId("vbox");
		this.applyCss();
		
		
		int size = 60;
		ImageView icon_remove_friend = new ImageView();
		icon_remove_friend.setImage(new Image(new FileInputStream(new File("resources/images/icons/remove_friend.png"))));
		icon_remove_friend.setFitHeight(size);
		icon_remove_friend.setFitWidth(size);
		this.btnDelete = new Button();
		
		this.btnDelete.setGraphic(icon_remove_friend);
		
		ImageView icon_invitation = new ImageView();
		icon_invitation.setImage(new Image(new FileInputStream(new File("resources/images/icons/project_invite.png"))));
		icon_invitation.setFitHeight(size);
		icon_invitation.setFitWidth(size);
		this.btnInvite = new Button();
		this.btnInvite.setGraphic(icon_invitation);
		
		
		this.hbButtons = new HBox();
		this.hbButtons.getChildren().addAll(this.btnDelete, this.btnInvite);
		
		this.vbUsrBUTTON.getChildren().clear();
		AnchorPane pane = new AnchorPane();
		AnchorPane.setBottomAnchor(vbUsrBUTTON, 0.0);
		AnchorPane.setTopAnchor(vbUsrBUTTON, 0.0);
		AnchorPane.setLeftAnchor(vbUsrBUTTON, 0.0);
		AnchorPane.setRightAnchor(vbUsrBUTTON, 0.0);
		this.vbUsrBUTTON.getChildren().add(hbButtons);
		this.vbUsrBUTTON.setAlignment(Pos.CENTER);
		pane.getChildren().add(vbUsrBUTTON);
		
		
		Circle circle = new Circle();
		circle.setCenterX(30.0f);
		circle.setCenterY(15.0f);
		circle.setRadius(10.0f);
		circle.applyCss();
		
		if (p.getStatus().equals(ENUMS.DISPONIBILITY_FOR_PROJECT.AVAILABLE.getValue())) {
			this.setId("available");
			circle.setId("circleAvailable");
		} else {
			this.setId("busy");
			circle.setId("circleBusy");
		}
		String status = p.getStatus().equals(ENUMS.DISPONIBILITY_FOR_PROJECT.AVAILABLE.getValue()) ? "Disponivel" : "Ocupado";
		Label lblStatus = new Label(status);
		
		lblStatus.applyCss();
		
		HBox hbStatus = new HBox();
		hbStatus.setAlignment(Pos.CENTER);
		hbStatus.getChildren().addAll(circle, lblStatus);
		hbStatus.setSpacing(10);
		this.vbUsrLABEL.getChildren().add(hbStatus);
		this.vbUsrLABEL.setSpacing(20);
		
		
		this.getChildren().add(pane);
		
		
		this.setSpacing(20);
		this.hbButtons.setSpacing(20);
		
		this.vbUsrLABEL.setSpacing(5);
		
		this.p = p;
	}
	public void setEventDelete(EventHandler<ActionEvent> e) {
		this.btnDelete.setOnAction(e);
	}
	public void setEventInvite(EventHandler<ActionEvent> e) {
		this.btnInvite.setOnAction(e);
	}
	
	public USER_PROFILE getP() {
		return p;
	}
	public void setP(USER_PROFILE p) {
		this.p = p;
	}
	
	
}
