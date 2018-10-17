package statics;

import java.io.IOException;
import java.util.ArrayList;

import db.pojos.PROJECT;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import project.QUERY_PROJECT;
import widgets.designComponents.projectContents.HBProjectComponent;

public class GENERAL_STORE {

	/*
	 * home page stuffs
	 */
	private static Label lblName, lblUserName;
	private static ImageView imgProfile;
	private static ArrayList<HBProjectComponent> listProjectComponent;
	private static ArrayList<PROJECT> memberInProjects;
	private static ArrayList<PROJECT> myProjects;
	private static VBox vbProjectComponent;

	public static void setComponentsHOME(Label lblName, Label lblUserName, ImageView imgProfile,
			Button btnFriendRequest, Button btnFriendsList, Button btnProjectInvitation, VBox vbProjectComponents) { // ,VBox
																														// vbCurrentProjects,VBox
																														// vbFinishedProjects)
																														// {
		GENERAL_STORE.lblName = lblName;
		GENERAL_STORE.lblUserName = lblUserName;
		GENERAL_STORE.imgProfile = imgProfile;
		GENERAL_STORE.vbProjectComponent = vbProjectComponents;
	}
//	public static void updateComponentsHOME() throws IOException {
//		lblName.setText(SESSION.getProfileLogged().getName());
//		lblUserName.setText(SESSION.getUserLogged().getUserName());
//		imgProfile.setImage(PROFILE_IMG.loadImage());
//
//	}

	public static void loadComponentsHOME() throws IOException {
		lblName.setText(SESSION.getProfileLogged().getName());
		lblUserName.setText(SESSION.getUserLogged().getUserName());
		imgProfile.setImage(PROFILE_IMG.loadImage());
		updateListProjects();
	}

	private static void updateListProjects() {
		
		GENERAL_STORE.vbProjectComponent.getChildren().clear();
		for (HBProjectComponent component : projectComponent()) {
			GENERAL_STORE.vbProjectComponent.getChildren().add(component);
		}
	}

	public static ArrayList<HBProjectComponent> projectComponent() {
		loadProjects();
		ArrayList<HBProjectComponent> list = new ArrayList<HBProjectComponent>();
		for (PROJECT p : allProjects()) {
			list.add(new HBProjectComponent(p, Pos.CENTER_LEFT));
		}
		return list;
	}

	public static void loadProjects() {
		myProjects = QUERY_PROJECT.USER_PROJECTS();
		memberInProjects = QUERY_PROJECT.USER_PROJECTS_MEMBER();
	}

	private static ArrayList<PROJECT> allProjects() {

		ArrayList<PROJECT> p = new ArrayList<PROJECT>();

		for (PROJECT project : myProjects) {
			p.add(project);
		}
		for (PROJECT project : memberInProjects) {
			p.add(project);
		}
		return p;
	}

}
