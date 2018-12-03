package application.controllers;

import java.io.IOException;
import java.util.Calendar;

import db.pojos.PROJECT;
import db.pojos.PROJECT_MEMBER;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import project.InvitationQuerys;
import project.PROJECT_SESSION;
import statics.DB_OPERATION;
import statics.ENUMS;
import statics.SESSION;
import view.popoups.NewProjectPOPOUP;
import view.popoups.TeamPOPOup;

public class TeamComponentController {
	private InvitationQuerys invitation;

	private Label lblProjectName, lblAboutTheProject, lblScrumMaster;
	private HBox hButtons;
	private Button btnCancel, btnInvite;
	private TeamPOPOup screen;

	public TeamComponentController(TeamPOPOup screen) throws IOException {
		this.screen = screen;
		this.invitation = new InvitationQuerys();
	}

	public void actionBack() {
		this.screen.close();
	}

	public void actionFinish(String projectName, String projectDescription) {

		this.btnCancel = new Button("Cancelar");
		this.btnCancel.setId("back");
		this.btnInvite = new Button("Convidar amigos");

		this.lblProjectName = new Label();
		this.lblAboutTheProject = new Label();
		this.lblScrumMaster = new Label();

		this.lblProjectName.setText(PROJECT_SESSION.getProject().getProjName());
//	this.lblScrumMaster.setText(PROJECT_SESSION.getProject().getProjCreator());
		this.lblAboutTheProject.setText(PROJECT_SESSION.getProject().getProjDescription());

		/*
		 * see if a member has already in the project
		 * 
		 */

	}
}
