package application.controllers;

import java.io.IOException;
import java.util.ArrayList;

import db.pojos.PROJECT;
import db.pojos.PROJECT_MEMBER;
import db.pojos.USER_PROFILE;
import javafx.scene.layout.VBox;
import project.querys.InvitationQuerys;
import statics.DB_OPERATION;
import statics.ENUMS;
import statics.SESSION;
import view.popoups.ProjectInvite;
import widgets.designComponents.projectContents.HBProjectInvitationComponent;

public class ProjectInviteController {
	
	private ArrayList<?> listInvites;
	private ArrayList<?> listProjects;
	private ArrayList<?> listProfiles;
	
	public ProjectInviteController(ProjectInvite screen, VBox layout) throws IOException {
		this.listInvites = new ArrayList<PROJECT_MEMBER>();
		this.listProjects = new ArrayList<PROJECT>();
		this.listProfiles = new ArrayList<USER_PROFILE>();
	}
	
	public void drawInvites(ProjectInvite screen, VBox layout) throws IOException {
		layout.getChildren().clear();
		
		ArrayList<HBProjectInvitationComponent> x = loadComponents();
		
		if (!x.isEmpty()) {
			for (int i = 0; i < x.size(); i++) {
				
				InvitationQuerys answer = new InvitationQuerys();
				PROJECT_MEMBER invite = (PROJECT_MEMBER) this.listInvites.get(i);
				
				x.get(i).setAcceptEvent(e -> {
					answer.answerInvite(invite, ENUMS.REQUEST_STATUS.ACCEPTED);
					try {
						drawInvites(screen, layout);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				});
				x.get(i).setRefuseEvent(e -> {
					answer.answerInvite(invite, ENUMS.REQUEST_STATUS.REFUSED);
					try {
						drawInvites(screen, layout);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				});
				layout.getChildren().add(x.get(i));
			}
		}
		x.clear();
	}
	
	public ArrayList<HBProjectInvitationComponent> loadComponents() throws IOException {
		
		ArrayList<HBProjectInvitationComponent> listComponents = new ArrayList<HBProjectInvitationComponent>();
		if (loadValues()) {
			int i = 0;
			while (i  < listProfiles.size() ) {
				PROJECT p = (PROJECT) this.listProjects.get(i);
				USER_PROFILE up = (USER_PROFILE) this.listProfiles.get(i);
				PROJECT_MEMBER pm = (PROJECT_MEMBER) this.listInvites.get(i);
				
				boolean x = pm.getMbrInvitedBy() == up.getCod();
				boolean y = pm.getMbrProjectCod() == p.getProjectCod();
				
				boolean a = x && y ? true : false;
				
				if (a)
					listComponents.add(new HBProjectInvitationComponent(up, p));
				i++;
				
				if(i > this.listInvites.size() || i > this.listProjects.size() || i > this.listProfiles.size()) break;
			}
		}
		
		
		return listComponents;
	}
	
	public boolean loadValues() {
		boolean y = DB_OPERATION.QUERY("FROM PROJECT_MEMBER WHERE MBR_PROF_COD = :COD AND MBR_INVITE_STATUS='ON_HOLD'", "COD", SESSION.getProfileLogged().getCod()).isEmpty();
		
		if (y)
			return y;
		
		this.listProfiles.clear();
		this.listProjects.clear();
		this.listInvites.clear();
		
		this.listInvites = (ArrayList<?>) DB_OPERATION.QUERY("FROM PROJECT_MEMBER WHERE MBR_PROF_COD = :COD AND MBR_INVITE_STATUS='ON_HOLD'", "COD", SESSION.getProfileLogged().getCod());
		
		int[] profiles = new int[listInvites.size()];
		int[] projects = new int[listInvites.size()];
		
		for (int i = 0; i < listInvites.size(); i++) {
			PROJECT_MEMBER o = (PROJECT_MEMBER) listInvites.get(i);
			profiles[i] = o.getMbrInvitedBy();
			projects[i] = o.getMbrProjectCod();
		}
		
		this.listProjects = (ArrayList<?>) DB_OPERATION.QUERY("FROM PROJECT WHERE PROJ_COD  = :COD ", "COD", projects);
		this.listProfiles = (ArrayList<?>) DB_OPERATION.QUERY("FROM USER_PROFILE WHERE PROF_COD = :COD", "COD", profiles);
		
		return listProfiles.isEmpty() || listProfiles.isEmpty() ? false : true;
		
	}
}

