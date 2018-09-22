package widgets.designComponents;

import java.io.IOException;

import db.pojos.USER_PROFILE;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class HBFriendContent extends HBProfileContent{
	
	protected Button btnDelete, btnInvite;
	private HBox hbButtons;
	private USER_PROFILE p;
	public HBFriendContent(USER_PROFILE p) throws IOException {
		super(p);
		this.getChildren().clear();
		this.getChildren().addAll(vbUsrIMG,vbUsrLABEL);
		
		this.btnDelete = new Button("Remover amigo");
		this.btnInvite = new  Button("Convidar para um projeto");
		
		this.hbButtons = new HBox();
		this.hbButtons.getChildren().addAll(this.btnDelete, this.btnInvite);
		
		this.vbUsrLABEL.getChildren().add(hbButtons);
		this.vbUsrLABEL.setSpacing(5);
	
		this.p = p;
	}
	public void setEventDelete(EventHandler<ActionEvent> e) {
		this.btnDelete.setOnAction(e);
	}
	public void setEventInvite(EventHandler<ActionEvent> e) {
		this.btnInvite.setOnAction(e);
	}

	public USER_PROFILE getP() {return p;}
	public void setP(USER_PROFILE p) {this.p = p;}
	
	
		
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
