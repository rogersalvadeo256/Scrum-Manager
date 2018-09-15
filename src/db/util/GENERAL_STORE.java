package db.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import widgets.designComponents.HBFriendRequest;

public class GENERAL_STORE {
	
	/*
	 * home page stuffs
	 */
	private static Label lblName, lblUserName, lblBio;
	private static ImageView imgProfile;
	
	public static void setComponentsHOME(Label lblName, Label lblUserName, Label lblBio, ImageView imgProfile) {
		GENERAL_STORE.lblName = lblName;
		GENERAL_STORE.lblUserName = lblUserName;
		GENERAL_STORE.lblBio = lblBio;
		GENERAL_STORE.imgProfile = imgProfile;
	}
	
	public static void updateComponentsHOME() throws IOException {
		
		lblName.setText(SESSION.getProfileLogged().getName());
		lblUserName.setText(SESSION.getUserLogged().getUserName());
		lblBio.setText(SESSION.getProfileLogged().getBio());
		imgProfile.setImage(ProfileImg.loadImage());
	}
	
	public static void loadComponentsHOME() throws IOException {
		lblName.setText(SESSION.getProfileLogged().getName());
		lblUserName.setText(SESSION.getUserLogged().getUserName());
		lblBio.setText(SESSION.getProfileLogged().getBio());
		
		if (SESSION.getProfileLogged().getPhoto() == null || SESSION.getProfileLogged().getPhoto().length == 0) {
			imgProfile.setImage(new Image(new FileInputStream("resources/images/icons/profile_picture.png")));
		} else {
			imgProfile.setImage(ProfileImg.loadImage());
		}
	}
	
}






























