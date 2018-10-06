package project.querys;

import java.util.ArrayList;
import java.util.List;

import db.pojos.PROJECT;
import db.pojos.PROJECT_MEMBER_INVITATION;
import db.pojos.USER_PROFILE;
import project.invitation.TEMP_STORE_INVITATIONS;
import statics.DB_OPERATION;
import statics.ENUMS;
import statics.ENUMS.REQUEST_STATUS;
import statics.SESSION;

public class InvitationQuerys {

      @SuppressWarnings("unchecked")
      public List<PROJECT_MEMBER_INVITATION> listInvites() {
            List<?> invitations = DB_OPERATION.QUERY(
                        "FROM PROJECT_MEMBER_INVITATION WHERE MBR_PROF_COD = :COD AND MBR_INVITE_STATUS = :STATUS",
                        new String[] { "COD", "STATUS" }, new Object[] { SESSION.getProfileLogged().getCod(),
                                    ENUMS.REQUEST_STATUS.ON_HOLD.getValue().toString() });
            return (List<PROJECT_MEMBER_INVITATION>) invitations;
      }

      public void answerInvite(PROJECT_MEMBER_INVITATION invitation, REQUEST_STATUS answer) {

            invitation.setMbrInviteAnsweredDate();
            switch (answer) {
            case ACCEPTED:
                  invitation.setMbrInviteStatus(ENUMS.REQUEST_STATUS.ACCEPTED.getValue());
                  break;
            case REFUSED:
                  invitation.setMbrInviteStatus(ENUMS.REQUEST_STATUS.REFUSED.getValue());
                  break;
            }


            DB_OPERATION.MERGE(invitation);

      }
      public void invite(USER_PROFILE invited, PROJECT p) {
            PROJECT_MEMBER_INVITATION invite = new PROJECT_MEMBER_INVITATION ();

            invite.setMbrInvitedBy(SESSION.getProfileLogged().getCod());
            invite.setMbrProfCod(invited.getCod());
            invite.setMbrProjectCod(p.getProjectCod());
            invite.setMbrInviteSendedDate();
            invite.setMbrInviteStatus(ENUMS.REQUEST_STATUS.ON_HOLD.getValue());

            DB_OPERATION.PERSIST(invite);
      }
      public void invite(ArrayList<USER_PROFILE> list, PROJECT p) {
            for (USER_PROFILE var : list) {
                  PROJECT_MEMBER_INVITATION  invite = new PROJECT_MEMBER_INVITATION ();

                  invite.setMbrInvitedBy(SESSION.getProfileLogged().getCod());
                  invite.setMbrProfCod(var.getCod());
                  invite.setMbrProjectCod(p.getProjectCod());
                  invite.setMbrInviteSendedDate();
                  invite.setMbrInviteStatus(ENUMS.REQUEST_STATUS.ON_HOLD.getValue());

                  DB_OPERATION.PERSIST(invite);
            }
            TEMP_STORE_INVITATIONS.LIST_INVITATION().clear();
      }
}