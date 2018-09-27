package statics;

import java.io.IOException;

import db.querys.QUERYs_FRIENDSHIP;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class GENERAL_STORE {

	/*
	 * home page stuffs
	 */
	private static Label lblName, lblUserName;
	private static ImageView imgProfile;
	private static Button btnFriendRequest, btnFriendsList;

	public static void setComponentsHOME(Label lblName, Label lblUserName, ImageView imgProfile,
																					Button btnFriendRequest,
																					Button btnFriendsList) {
		GENERAL_STORE.lblName = lblName;
		GENERAL_STORE.lblUserName = lblUserName;
		GENERAL_STORE.imgProfile = imgProfile;
		GENERAL_STORE.btnFriendRequest = btnFriendRequest;
		GENERAL_STORE.btnFriendsList = btnFriendsList;
	}

	public static void updateComponentsHOME() throws IOException {

		lblName.setText(SESSION.getProfileLogged().getName());
		lblUserName.setText(SESSION.getUserLogged().getUserName());

		btnFriendRequest.setText(String.valueOf(QUERYs_FRIENDSHIP.friendshipRequestsList().size()));
		btnFriendsList.setText(String.valueOf(QUERYs_FRIENDSHIP.friendsList().size()));
		imgProfile.setImage(ProfileImg.loadImage());

	}

	public static void loadComponentsHOME() throws IOException {
		lblName.setText(SESSION.getProfileLogged().getName());
		lblUserName.setText(SESSION.getUserLogged().getUserName());

		imgProfile.setImage(ProfileImg.loadImage());

		btnFriendRequest.setText(String.valueOf(QUERYs_FRIENDSHIP.friendshipRequestsList().size()));
		btnFriendsList.setText(String.valueOf(QUERYs_FRIENDSHIP.friendsList().size()));

	}
}
