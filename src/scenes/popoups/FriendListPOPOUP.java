package scenes.popoups;

import java.io.IOException;

import api.FriendsComponentAPI;
import db.util.SESSION;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.stage.Window;
import widgets.designComponents.HBFriendContent;

public class FriendListPOPOUP extends StandartLayoutPOPOUP {
	
	private FriendsComponentAPI controller;
	public FriendListPOPOUP(Window owner) throws IOException {
		super(owner);
		this.layout.setAlignment(Pos.CENTER);
		this.controller=new FriendsComponentAPI();
		drawComponents();
	}
	private void drawComponents() throws IOException {

		if(SESSION.getProfileLogged().getFriendsList().isEmpty()) {
		/* just for test*/	this.layout.getChildren().add(new Label("you have no friends"));
		}
		for (int i = 0; i < SESSION.getProfileLogged().getFriendsList().size(); i++) {

			HBFriendContent component = new HBFriendContent(SESSION.getProfileLogged().getFriendsList().get(i));

			component.setEventDelete(e -> { 
				controller.deleteFriend(component.getP());
				try {
					drawComponents();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			});
			this.layout.getChildren().add(component);
		}
	}
}















