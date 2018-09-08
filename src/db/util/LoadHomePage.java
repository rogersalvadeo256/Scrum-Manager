package db.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.persistence.EntityManager;

import db.hibernate.factory.Database;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class LoadHomePage {
	
	
	private static Label lblName, lblUserName,lblBio;
	private static ImageView imgProfile;
	public LoadHomePage() {
	}
	public static void setComponents(Label lblName,Label lblUserName, Label lblBio, ImageView imgProfile) { 
		LoadHomePage.lblName = lblName;
		LoadHomePage.lblUserName = lblUserName;
		LoadHomePage.lblBio = lblBio;
		LoadHomePage.imgProfile = imgProfile;
 	}
	public static void updateComponents() throws IOException {
		
		lblName.setText(SESSION.getProfileLogged().getName());
		lblUserName.setText(SESSION.getUserLogged().getUserName());
		lblBio.setText(SESSION.getProfileLogged().getBiography());
		imgProfile.setImage(ProfileImg.loadImage());
	
	}
	public static void loadComponents() throws IOException { 
		lblName.setText(SESSION.getProfileLogged().getName());
		lblUserName.setText(SESSION.getUserLogged().getUserName());
		lblBio.setText(SESSION.getProfileLogged().getBiography());
		
		if(SESSION.getProfileLogged().getPhoto() == null || SESSION.getProfileLogged().getPhoto().length == 0) {
			imgProfile.setImage(new Image(new FileInputStream("resources/images/icons/profile_picture.png")));
		}else {imgProfile.setImage(ProfileImg.loadImage());}	
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}





