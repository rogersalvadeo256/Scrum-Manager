package view.popoups;

import java.io.IOException;

import application.controllers.FriendsComponentController;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.stage.Window;
import statics.SESSION;
import widgets.designComponents.HBFriendContent;

public class FriendListPOPOUP extends StandartLayoutPOPOUP {
	private FriendsComponentController controller;
	public FriendListPOPOUP(Window owner) throws IOException {
		super(owner);
		this.layout.setAlignment(Pos.CENTER);
		this.controller = new FriendsComponentController();
		
		controller.init(layout, this);
	}
}



 	




























