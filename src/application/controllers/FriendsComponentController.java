package application.controllers;

import java.io.IOException;
import java.util.ArrayList;

import javax.persistence.EntityManager;

import db.hibernate.factory.Database;
import db.pojos.Profile;
import friendship.FriendshipFunctions;
import javafx.scene.layout.VBox;
import scenes.popoups.FriendListPOPOUP;
import scenes.popoups.FriendshipRequestPOPOUP;
import statics.SESSION;
import widgets.designComponents.HBFriendContent;

public class FriendsComponentController {
	private ArrayList<Profile> friendsList;

	public FriendsComponentController() throws IOException {
		this.friendsList = new ArrayList<>();
	}

	public void init(VBox layout, FriendListPOPOUP screen) throws IOException {

		layout.getChildren().clear();

		loadFriendsList();

		if (this.friendsList.isEmpty())
			screen.close();

		for (int i = 0; i < this.friendsList.size(); i++) {
			HBFriendContent fComponent = new HBFriendContent(this.friendsList.get(i));
			FriendshipFunctions fFunctions = new FriendshipFunctions(this.friendsList.get(i));
			layout.getChildren().add(fComponent);

			fComponent.setEventDelete(e -> {
				fFunctions.deleteFriend();
				loadFriendsList();
				SESSION.UPDATE_SESSION();
				try {
					init(layout, screen);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			});
		}
		return;
	}

	private void loadFriendsList() {
		this.friendsList.clear();
		for (int i = 0; i < SESSION.getProfileLogged().getFriendsList().size(); i++) {
			this.friendsList.add(SESSION.getProfileLogged().getFriendsList().get(i));
		}
	}

}
