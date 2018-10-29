package project;

import java.util.ArrayList;
import java.util.List;

import db.pojos.PROJECT;
import db.pojos.PROJECT_MEMBER;
import db.pojos.USER_PROFILE;
import statics.DB_OPERATION;
import statics.ENUMS;
import statics.ENUMS.REQUEST_STATUS;
import statics.SESSION;

public class InvitationQuerys {

	private PROJECT_MEMBER invitation;

	public InvitationQuerys(PROJECT_MEMBER pm) {
		this.invitation = pm;
	}

	public InvitationQuerys() {

	}

	public void answerInvite(REQUEST_STATUS answer) {

		this.invitation.getMbrCod();
		invitation.setMbrInviteAnsweredDate();
		switch (answer) {
		case ACCEPTED:
			invitation.setMbrInviteStatus(ENUMS.REQUEST_STATUS.ACCEPTED.getValue());
			break;
		case REFUSED:
			invitation.setMbrInviteStatus(ENUMS.REQUEST_STATUS.REFUSED.getValue());
			break;
		default:
			break;
		}
		DB_OPERATION.MERGE(invitation);
	}

	public void invite(USER_PROFILE invited, PROJECT p) {
		PROJECT_MEMBER invite = new PROJECT_MEMBER();

		invite.setMbrInvitedBy(SESSION.getProfileLogged().getCod());
		invite.setMbrProfCod(invited.getCod());
		invite.setMbrProjectCod(p.getProjectCod());
		invite.setMbrInviteSendedDate();
		invite.setMbrInviteStatus(ENUMS.REQUEST_STATUS.ON_HOLD.getValue());

		List<?> a = DB_OPERATION.QUERY(
				"FROM PROJECT_MEMBER WHERE MBR_PROJECT = : COD_PROJECT AND MBR_PROF_COD = :PROF_COD",
				new String[] { "COD_PROJECT", "PROF_COD" }, new int[] { p.getProjectCod(), invited.getCod() });

		if (a.isEmpty())
			DB_OPERATION.PERSIST(invite);
	}

	public void invite(ArrayList<USER_PROFILE> list, PROJECT p) {
		List<?> x = DB_OPERATION.QUERY("FROM PROJECT_MEMBER WHERE MBR_PROJECT = :COD", "COD", p.getProjectCod());

		boolean a = x.isEmpty();
		if (!a) {
			int[] y = new int[x.size()];

			for (int i = 0; i < x.size(); i++) {
				PROJECT_MEMBER p1 = (PROJECT_MEMBER) x.get(i);
				y[i] = p1.getMbrProfCod();
			}
			for (int i = 0; i < list.size(); i++) {

				boolean b = list.get(i).getCod() == y[i] ? true : false;

				if (b)
					list.remove(i);
			}
		}
		for (USER_PROFILE var : list) {
			PROJECT_MEMBER invite = new PROJECT_MEMBER();

			invite.setMbrInvitedBy(SESSION.getProfileLogged().getCod());
			invite.setMbrProfCod(var.getCod());
			invite.setMbrProjectCod(p.getProjectCod());
			invite.setMbrInviteSendedDate();
			invite.setMbrScrumMaster(false);
			invite.setMbrInviteStatus(ENUMS.REQUEST_STATUS.ON_HOLD.getValue());

			DB_OPERATION.PERSIST(invite);
		}
		TEMP_STORE_INVITATIONS.LIST_INVITATION().clear();
	}
}
