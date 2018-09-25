package statics;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import db.hibernate.factory.Database;
import db.pojos.USER_PROFILE;
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
	private static Label lblName, lblUserName;
	private static ImageView imgProfile;
	private static Button btnFriendRequest;
	private static EntityManager em;

	public static void setComponentsHOME(Label lblName, Label lblUserName, ImageView imgProfile,
																					Button btnFriendRequest) {
		GENERAL_STORE.lblName = lblName;
		GENERAL_STORE.lblUserName = lblUserName;
		GENERAL_STORE.imgProfile = imgProfile;
		GENERAL_STORE.btnFriendRequest = btnFriendRequest;
	}

	public static void updateComponentsHOME() throws IOException {

		lblName.setText(SESSION.getProfileLogged().getName());
		lblUserName.setText(SESSION.getUserLogged().getUserName());
		imgProfile.setImage(ProfileImg.loadImage());

		if (em == null)
			em = Database.createEntityManager();
		Query q = em.createQuery("FROM FRIENDSHIP_REQUEST WHERE FRQ_COD_PROF_RECEIVER =: COD AND FRQ_REQUEST_STATUS = 'ON_HOLD'");
		q.setParameter("COD", SESSION.getProfileLogged().getCod());
		
		if(q.getResultList().isEmpty()) btnFriendRequest.setText(new String());	
		if(!q.getResultList().isEmpty()) btnFriendRequest.setText(String.valueOf(q.getResultList().size()));
	}

	public static void loadComponentsHOME() throws IOException {
		lblName.setText(SESSION.getProfileLogged().getName());
		lblUserName.setText(SESSION.getUserLogged().getUserName());

		imgProfile.setImage(ProfileImg.loadImage());

		if (em == null)
			em = Database.createEntityManager();
		Query q = em.createQuery("FROM FRIENDSHIP_REQUEST WHERE FRQ_COD_PROF_RECEIVER =: COD AND FRQ_REQUEST_STATUS = 'ON_HOLD'");
		q.setParameter("COD", SESSION.getProfileLogged().getCod());
		
		if(q.getResultList().isEmpty()) btnFriendRequest.setText(new String());	
		if(!q.getResultList().isEmpty()) btnFriendRequest.setText(String.valueOf(q.getResultList().size()));
		
	}
}























