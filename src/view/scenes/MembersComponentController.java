package view.scenes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import db.pojos.PROJECT;
import db.pojos.PROJECT_MEMBER;
import friendship.QUERYs_FRIENDSHIP;
import javafx.scene.layout.VBox;
import project.InvitationQuerys;
import project.TEMP_STORE_INVITATIONS;
import statics.DB_OPERATION;
import statics.ENUMS;
import statics.GENERAL_STORE;
import statics.SESSION;

public class MembersComponentController {

	private TeamPOPOUP screen;
	private InvitationQuerys invitation;
	private ArrayList<PROJECT_MEMBER> membersList;

	public MembersComponentController(TeamPOPOUP screen) throws IOException {
		this.screen = screen;
		this.membersList = new ArrayList<>();
		this.invitation  = new InvitationQuerys();
	}
	public void actionBack() {
		this.screen.close();
	}
	
	public void init(VBox layout, TeamPOPOUP teamPOPOUP) {
		layout.getChildren().clear();
		this.loadMembersList();
		if (this.membersList.isEmpty())
			screen.close();
		
	for(int i = 0 ; i < this.friendsList.size() ; i++) {
		HBFriendContent fc = new HBFriendContent(this.membersList.get(i));
		FriendshipActions fr = new FriendshipActions(this.membersList.get(i));

	}
	private void loadMembersList() {
		this.membersList = (ArrayList<PROJECT_MEMBER>) QUERYs_FRIENDSHIP.friendsList();
	}

	public void actionFinish(String projectName, String projectDescription) {

	
		
		PROJECT project = new PROJECT();
		project.setProjName(projectName);
		project.setProjDateStart(Calendar.getInstance().getTime());
		project.setProjDescription(projectDescription);
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
		 * see if a member has already been on the project
		 */
		
		listMembers == LISTA QUE EU VOU FAZER AINDA
		if (!LISTA QUE EU VOU FAZER.isEmpty())
			invitation.invite(TEMP_STORE_INVITATIONS.LIST_INVITATION(), project);

		try {
			GENERAL_STORE.loadComponentsHOME();
		} catch (IOException e) {
			e.printStackTrace();
		}
		screen.close();
	}
}