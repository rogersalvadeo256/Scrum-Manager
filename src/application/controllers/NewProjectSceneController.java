package application.controllers;

import db.pojos.PROJECT;
import project.invitation.ProjectInvitation;
import project.invitation.TEMP_STORE_INVITATIONS;
import statics.DB_OPERATION;
import statics.SESSION;
import view.popoups.NewProjectPOPOUP;

public class NewProjectSceneController {
	private ProjectInvitation invitation;

	public void actionBack(NewProjectPOPOUP screen) {
		screen.close();
	}
	public void actionFinish(String projectName, String projectDescription) {

		PROJECT project = new PROJECT();
		project.setProjName(projectName);
		project.setProjDescription(projectDescription);
		project.setProjCreator(SESSION.getProfileLogged().getCod());

		DB_OPERATION.PERSIST(project);

		/*	
			see if exist a project with the same name
		*/


		if(sendInvites()) invitation.invite(TEMP_STORE_INVITATIONS.LIST_INVITATION(), project);
	}
	private boolean sendInvites () { 
	 return  TEMP_STORE_INVITATIONS.LIST_INVITATION().isEmpty() ? true : false ;
	}

}