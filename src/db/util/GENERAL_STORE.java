package db.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import db.pojos.Profile;
import friendship.FriendshipRequest;
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
	private static Label lblName, lblUserName, lblBio;
	private static ImageView imgProfile;
	private static Button btnFriendRequest;

	public static void setComponentsHOME(Label lblName, Label lblUserName, Label lblBio,
																					ImageView imgProfile,
																					Button btnFriendRequest) {
		GENERAL_STORE.lblName = lblName;
		GENERAL_STORE.lblUserName = lblUserName;
		GENERAL_STORE.lblBio = lblBio;
		GENERAL_STORE.imgProfile = imgProfile;
		GENERAL_STORE.btnFriendRequest = btnFriendRequest;
	}

	public static void updateComponentsHOME() throws IOException {

		lblName.setText(SESSION.getProfileLogged().getName());
		lblUserName.setText(SESSION.getUserLogged().getUserName());
		lblBio.setText(SESSION.getProfileLogged().getBio());
		imgProfile.setImage(ProfileImg.loadImage());

		btnFriendRequest.setText((SESSION.getProfileLogged().getFriendshipRequests().size() > 0 ? String.valueOf(SESSION.getProfileLogged().getFriendshipRequests().size()) : new String()));

	}

	public static void loadComponentsHOME() throws IOException {
		lblName.setText(SESSION.getProfileLogged().getName());
		lblUserName.setText(SESSION.getUserLogged().getUserName());
		lblBio.setText(SESSION.getProfileLogged().getBio());

		imgProfile.setImage(ProfileImg.loadImage());

		btnFriendRequest.setText((SESSION.getProfileLogged().getFriendshipRequests().size() > 0 ? String.valueOf(SESSION.getProfileLogged().getFriendshipRequests().size()) : new String()));
	}
}
