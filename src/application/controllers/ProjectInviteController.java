package application.controllers;

import java.io.IOException;
import java.util.ArrayList;

import db.pojos.PROJECT;
import db.pojos.PROJECT_MEMBER;
import javafx.scene.layout.VBox;
import project.InvitationQuerys;
import project.QUERY_PROJECT;
import statics.DB_OPERATION;
import statics.ENUMS;
import statics.SESSION;
import view.popoups.ProjectInvitePOPOUP;
import widgets.designComponents.projectContents.HBProjectInvitationComponent;

public class ProjectInviteController {

	public ProjectInviteController(ProjectInvitePOPOUP screen, VBox layout) throws IOException {
		drawInvites(screen, layout);
	}
	public void drawInvites(ProjectInvitePOPOUP screen, VBox layout) throws IOException {
		PROJECT pj = new PROJECT();
		layout.getChildren().clear();
		ArrayList<HBProjectInvitationComponent> x = QUERY_PROJECT.teste();
		
		if (!x.isEmpty()) {
			for (int i = 0; i < x.size(); i++) {
				PROJECT_MEMBER pm = QUERY_PROJECT.LIST_INVITES().get(i);
			
				System.out.println(pm);
				
				InvitationQuerys answer = new InvitationQuerys(pm);
				x.get(i).setAcceptEvent(e -> {
					try {
						answer.answerInvite(ENUMS.REQUEST_STATUS.ACCEPTED);
						
//						pm.setMbrInvitedBy(SESSION.getProfileLogged().getCod());
//						pm.setMbrProfCod(SESSION.getProfileLogged().getCod());
//						pm.setMbrProjectCod(pj.getProjectCod());
//						pm.setMbrInviteStatus(ENUMS.REQUEST_STATUS.ACCEPTED.getValue());
//						pm.setMbrScrumMaster(true);
//						
//						DB_OPERATION.PERSIST(pm);
						
						drawInvites(screen, layout);
						
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				});
				x.get(i).setRefuseEvent(e -> {
					try {
						answer.answerInvite(ENUMS.REQUEST_STATUS.REFUSED);
						drawInvites(screen, layout);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				});
				layout.getChildren().add(x.get(i));
			}
			x.clear();
			return;
		}
		layout.getChildren().clear();
		screen.close();
	}
}
