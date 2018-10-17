package project;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import db.pojos.PROJECT;
import db.pojos.PROJECT_MEMBER;
import db.pojos.USER_PROFILE;
import statics.DB_OPERATION;
import statics.ENUMS;
import statics.SESSION;
import widgets.designComponents.projectContents.HBProjectInvitationComponent;

public class QUERY_PROJECT {

	private static ArrayList<PROJECT_MEMBER> l;

	/**
	 * return the project that the user have created
	 * 
	 * @return
	 * @author jefter66
	 */
	@SuppressWarnings("unchecked")
	public static ArrayList<PROJECT> USER_PROJECTS() {
		List<?> x = DB_OPERATION.QUERY("FROM PROJECT WHERE PROJ_CREATOR = :COD", "COD",
				SESSION.getProfileLogged().getCod());
		return (ArrayList<PROJECT>) x;
	}
	/**
	 * return the list of project that the user is in
	 * 
	 * @return
	 * @author jefter66
	 */
	@SuppressWarnings("unchecked")
	public static ArrayList<PROJECT> USER_PROJECTS_MEMBER() {

		List<?> x = DB_OPERATION.QUERY(
				"FROM PROJECT_MEMBER WHERE MBR_PROF_COD = :COD AND MBR_INVITE_STATUS ='ACCEPTED'", "COD",
				SESSION.getProfileLogged().getCod());

		List<PROJECT_MEMBER> y = (List<PROJECT_MEMBER>) x;
		int project[] = new int[1];

		for (int i = 0; i < y.size(); i++) {
			project[i] = y.get(i).getMbrProjectCod();
		}

		List<?> b = DB_OPERATION.QUERY("FROM PROJECT WHERE PROJ_COD = :COD", "COD", project[0]);

		return (ArrayList<PROJECT>) b;
	}

	/**
	 * return the list of invitations for project that the user have
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static ArrayList<PROJECT_MEMBER> INVITES_PROJECT() {
		List<?> invitations = DB_OPERATION.QUERY(
				"FROM PROJECT_MEMBER WHERE MBR_PROF_COD = :COD AND MBR_INVITE_STATUS = :STATUS",
				new String[] { "COD", "STATUS" }, new Object[] { SESSION.getProfileLogged().getCod(),
						ENUMS.REQUEST_STATUS.ON_HOLD.getValue().toString() });
		return (ArrayList<PROJECT_MEMBER>) invitations;
	}

	@SuppressWarnings("unchecked")
	public static ArrayList<USER_PROFILE> INVITED_BY() {

		List<PROJECT_MEMBER> x = INVITES_PROJECT();

		int cod[] = new int[1];
		for (int i = 0; i < x.size(); i++) {
			cod[i] = x.get(i).getMbrInvitedBy();
		}

		List<?> y = DB_OPERATION.QUERY("FROM USER_PROFILE WHERE PROF_COD = :COD", "COD", cod[1]);

		return (ArrayList<USER_PROFILE>) y;
	}
	public static ArrayList<HBProjectInvitationComponent> teste() throws IOException {

		ArrayList<HBProjectInvitationComponent> list = new ArrayList<HBProjectInvitationComponent>();
		for (PROJECT_MEMBER pm : INVITES_PROJECT()) {
			PROJECT p = (PROJECT) DB_OPERATION
					.QUERY("FROM PROJECT WHERE PROJ_COD = :COD ", "COD", pm.getMbrProjectCod()).get(0);
			USER_PROFILE pr = (USER_PROFILE) DB_OPERATION
					.QUERY("FROM USER_PROFILE WHERE PROF_COD = : COD", "COD", pm.getMbrInvitedBy()).get(0);
			list.add(new HBProjectInvitationComponent(pr, p));
			LIST_INVITES().add(pm);
		}
		return list;
	}
	/*
	 * i hate that i had to do this shit
	 * 
	 */
	public static ArrayList<PROJECT_MEMBER> LIST_INVITES() {
		l = (l == null ? new ArrayList<PROJECT_MEMBER>() : l);
		return l;
	}
}
