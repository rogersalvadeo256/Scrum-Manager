package scenes.popoups;

import java.io.IOException;
import java.util.ArrayList;

import api.FriendsComponentAPI;
import db.util.SESSION;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import widgets.designComponents.HBFriendContent;

public class FriendListPOPOUP extends StandartLayoutPOPOUP {
	
	private FriendsComponentAPI controller;
	private ArrayList<HBFriendContent> friendContent;

	public FriendListPOPOUP(Window owner) throws IOException {
		super(owner);
		this.friendContent = new ArrayList<>();

		drawComponents();

		this.initOwner(owner);
		this.initModality(Modality.WINDOW_MODAL);
		this.setWidth(600);
		this.setHeight(500);
		this.setResizable(false);
	}

	private void drawComponents() throws IOException {

		for (int i = 0; i < SESSION.getProfileLogged().getFriendsList().size(); i++) {

			HBFriendContent component = new HBFriendContent(SESSION.getProfileLogged().getFriendsList().get(i));

			component.setEventDelete(e -> { 
				controller.deleteFriend(component.getP());
				layout.getChildren().clear();
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
