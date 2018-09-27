package statics;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import db.hibernate.factory.Database;
import db.pojos.USER_PROFILE;
import db.querys.QUERYs_FRIENDSHIP;
import friendship.FriendshipActions;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import widgets.designComponents.HBFriendRequest;

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
