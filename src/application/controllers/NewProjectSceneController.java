package application.controllers;

import java.io.IOException;
import java.util.Calendar;

import db.pojos.PROJECT;
import db.pojos.PROJECT_MEMBER;
import project.InvitationQuerys;
import project.TEMP_STORE_INVITATIONS;
import statics.DB_OPERATION;
import statics.ENUMS;
import statics.GENERAL_STORE;
import statics.SESSION;
import view.popoups.NewProjectPOPOUP;

public class NewProjectSceneController {
	private InvitationQuerys invitation;

	private NewProjectPOPOUP screen;

	public NewProjectSceneController(NewProjectPOPOUP screen) throws IOException {
		this.screen = screen;
		this.invitation  = new InvitationQuerys();
	}
	public void actionBack() {
		this.screen.close();
	}
	

	public void actionFinish(String projectName, String projectDescription) {

	
		
		PROJECT project = new PROJECT();
		project.setProjName(projectName);
		project.setProjDateStart(Calendar.getInstance().getTime());
		project.setProjDescription(projectDescription);
		project.setProjStatus(ENUMS.PROJECT_WORKING.IN_PROGRESS.getValue());
		project.setProjCreator(SESSION.getProfileLogged().getCod());

		PROJECT_MEMBER pm = new PROJECT_MEMBER();
		
		pm.setMbrInvitedBy(SESSION.getProfileLogged().getCod());
		pm.setMbrProfCod(SESSION.getProfileLogged().getCod());
		pm.setMbrProjectCod(project.getProjectCod());
		pm.setMbrInviteStatus(ENUMS.REQUEST_STATUS.ACCEPTED.getValue());
		pm.setMbrScrumMaster(true);
		
		DB_OPERATION.PERSIST(project);
		DB_OPERATION.PERSIST(pm);
		/*
		 * see if exist a project with the same name
		 */
		
		if (!TEMP_STORE_INVITATIONS.LIST_INVITATION().isEmpty())
			invitation.invite(TEMP_STORE_INVITATIONS.LIST_INVITATION(), project);

		try {
			GENERAL_STORE.loadComponentsHOME();
		} catch (IOException e) {
			e.printStackTrace();
		}
		screen.close();
	}
}