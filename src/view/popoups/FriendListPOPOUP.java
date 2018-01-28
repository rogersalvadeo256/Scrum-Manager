package view.popoups;

import java.io.IOException;

import application.controllers.FriendsComponentController;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import statics.SESSION;
import widgets.designComponents.HBFriendContent;

public class FriendListPOPOUP extends StandartLayoutPOPOUP {
	private FriendsComponentController controller;
	public FriendListPOPOUP(Window owner) throws IOException {
		super(owner);
		this.layout.setAlignment(Pos.CENTER);
		this.controller = new FriendsComponentController();
		
		this.initStyle(StageStyle.DECORATED);
		
		controller.init(layout, this);
	}
}



 	




























