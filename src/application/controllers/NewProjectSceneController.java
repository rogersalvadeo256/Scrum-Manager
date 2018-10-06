package application.controllers;

import java.io.IOException;

import db.pojos.PROJECT;
import project.invitation.TEMP_STORE_INVITATIONS;
import project.querys.InvitationQuerys;
import statics.DB_OPERATION;
import statics.GENERAL_STORE;
import statics.SESSION;
import view.popoups.NewProjectPOPOUP;

public class NewProjectSceneController {
	private InvitationQuerys invitation;

	private NewProjectPOPOUP screen;

	public NewProjectSceneController(NewProjectPOPOUP screen) throws IOException {
		this.screen = screen;
	}
	public void actionBack() {
		this.screen.close();
	}

	public void actionFinish(String projectName, String projectDescription) {

		PROJECT project = new PROJECT();
		project.setProjName(projectName);
		project.setProjDescription(projectDescription);
		project.setProjCreator(SESSION.getProfileLogged().getCod());

		DB_OPERATION.PERSIST(project);
		/*
		 * see if exist a project with the same name
		 */
		if (sendInvites())
			invitation.invite(TEMP_STORE_INVITATIONS.LIST_INVITATION(), project);

		try {
			GENERAL_STORE.updateComponentsHOME();
		} catch (IOException e) {
			e.printStackTrace();
		}
		screen.close();

	}
	private boolean sendInvites() {
		return TEMP_STORE_INVITATIONS.LIST_INVITATION().isEmpty() ? true : false;
	}
}