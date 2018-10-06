package application.controllers;

import java.io.IOException;
import java.util.ArrayList;

import db.pojos.USER_PROFILE;
import friendship.FriendshipActions;
import friendship.QUERYs_FRIENDSHIP;
import javafx.scene.layout.VBox;
import view.popoups.FriendListPOPOUP;
import widgets.designComponents.friendshipContents.HBFriendContent;

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

		for(int i = 0 ; i < this.friendsList.size() ; i++) {
		HBFriendContent fc = new HBFriendContent(this.friendsList.get(i));
		FriendshipActions fr = new FriendshipActions(this.friendsList.get(i));

		fc.setEventDelete(e -> {
			try {
				fr.removeFriend();
			} catch (IOException e2) {
				e2.printStackTrace();
			}
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
