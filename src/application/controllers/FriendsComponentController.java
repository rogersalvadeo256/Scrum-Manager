package application.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import db.hibernate.factory.Database;
import db.pojos.FRIENDSHIP;
import db.pojos.USER_PROFILE;
import db.querys.QUERYs_FRIENDSHIP;
import friendship.FriendshipActions;
import javafx.scene.layout.VBox;
import statics.SESSION;
import view.popoups.FriendListPOPOUP;
import widgets.designComponents.HBFriendContent;

public class FriendsComponentController {
	private ArrayList<USER_PROFILE> friendsList;

	public FriendsComponentController() throws IOException {
		this.friendsList = new ArrayList<>();
	}

	/**
	 * Draw the components with friends information on the scene
	 * 
	 * @param layout
	 * @param screen
	 * @throws IOException
	 */
	public void init(VBox layout, FriendListPOPOUP screen) throws IOException {

		layout.getChildren().clear();
		this.loadFriendsList();
		if (this.friendsList.isEmpty())
			screen.close();
		for (int i = 0; i < this.friendsList.size(); i++) {
			HBFriendContent fc = new HBFriendContent(this.friendsList.get(i));
			FriendshipActions fr = new FriendshipActions(this.friendsList.get(i));

			fc.setEventDelete(e -> {
				fr.removeFriend();
				loadFriendsList();
				try {
					init(layout, screen);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			});

			layout.getChildren().add(fc);
		}
	}

	private void loadFriendsList() {
		this.friendsList = (ArrayList<USER_PROFILE>) QUERYs_FRIENDSHIP.friendsList();
	}
}
