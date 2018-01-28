package statics;

import java.io.IOException;

import friendship.QUERYs_FRIENDSHIP;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class GENERAL_STORE {

	/*
	 * home page stuffs
	 */
	private static Label lblName, lblUserName;
	private static ImageView imgProfile;
	private static Button btnFriendRequest, btnFriendsList;
	private static VBox vbCurrentProjects, vbFinishedProjects;
	

	public static void setComponentsHOME(Label lblName, Label lblUserName, ImageView imgProfile,
																					Button btnFriendRequest,
																					Button btnFriendsList,VBox vbCurrentProjects,VBox vbFinishedProjects) {
		GENERAL_STORE.lblName = lblName;
		GENERAL_STORE.lblUserName = lblUserName;
		GENERAL_STORE.imgProfile = imgProfile;
		GENERAL_STORE.btnFriendRequest = btnFriendRequest;
		GENERAL_STORE.btnFriendsList = btnFriendsList;
		GENERAL_STORE.vbCurrentProjects = vbCurrentProjects;
		GENERAL_STORE.vbFinishedProjects = vbFinishedProjects;
	}
	public static void updateComponentsHOME() throws IOException {
		lblName.setText(SESSION.getProfileLogged().getName());
		lblUserName.setText(SESSION.getUserLogged().getUserName());

		btnFriendRequest.setText(String.valueOf(QUERYs_FRIENDSHIP.friendshipRequestsList().size()));
		btnFriendsList.setText(String.valueOf(QUERYs_FRIENDSHIP.friendsList().size()));
		imgProfile.setImage(PROFILE_IMG.loadImage());

	}

	public static void loadComponentsHOME() throws IOException {
		lblName.setText(SESSION.getProfileLogged().getName());
		lblUserName.setText(SESSION.getUserLogged().getUserName());

		imgProfile.setImage(PROFILE_IMG.loadImage());

		btnFriendRequest.setText(String.valueOf(QUERYs_FRIENDSHIP.friendshipRequestsList().size()));
		btnFriendsList.setText(String.valueOf(QUERYs_FRIENDSHIP.friendsList().size()));
	}
	
	
	
	
	
	
	
	
	
	
	
	
	public static void SHOW_PROJECT() { 
			
		
		
//		List<?> listProject = DB_OPERATION.QUERY("FROM PROJECT INNER JOIN USER_PROFILE UP ON UP.PROF_COD = PROJECT.");
		
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
